package com.example.emag.services;

import com.example.emag.entity.Picture;
import com.example.emag.entity.Product;
import com.example.emag.entity.User;
import com.example.emag.exceptions.AuthorizationException;
import com.example.emag.exceptions.BadRequestException;
import com.example.emag.exceptions.NotFoundException;
import com.example.emag.repository.PictureRepository;
import com.example.emag.repository.ProductRepository;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class RenderService extends AbstractService {

  @Autowired
  private PictureRepository pictureRepository;

  @Autowired
  private ProductRepository productRepository;

  @Value("${image.path}")
  private String pathDir;

  public ClassPathResource renderImage(long productId) throws IOException, SQLException {
    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new NotFoundException("No such Product"));
    if (product.getDeleted()) {
      throw new BadRequestException("Product is inactive!");
    }
    List<Picture> pictures = pictureRepository.findAllByProductId(productId);
    if (pictures.isEmpty() || pictures == null){
      throw new BadRequestException("No picture found");
    }
    if (pictures.get(0).getUrl() == null) {
      throw new BadRequestException("No picture found!");
    }
    ClassPathResource imgFile = new ClassPathResource("static" + File.separator + pictures.get(0).getUrl());
    return imgFile;
  }

  public void uploadImage(MultipartFile file,
      Long productId,
      User user) {
    checkForAdminRights(user);
    Optional<Product> optional = productRepository.findById(productId);
    Product product = optional.get();
    if (product == null) {
      throw new BadRequestException("You cannot upload picture for product, which don't exist!");
    }
    if (product.getDeleted()) {
      throw new BadRequestException("You cannot upload picture for inactive product!");
    }
    String uploadDir = System.getProperty("user.dir") + pathDir;
    Path copyLocation = null;
    try {
      copyLocation = Paths
          .get(uploadDir + File.separator + StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename())));
      Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
    } catch (Exception exception) {
      log.error("Something went wrong with the location", exception);
    }
    Picture picture = new Picture();
    picture.setProduct(product);
    picture.setUrl(file.getOriginalFilename());
    pictureRepository.save(picture);
  }

  private void checkForAdminRights(User user) throws AuthorizationException {
    if (user == null) {
      throw new AuthorizationException();
    }
    if (!user.getAdmin()) {
      throw new AuthorizationException("You need to be admin to perform this!");
    }
  }
}
