package com.example.emag.controller;

import com.example.emag.entity.User;
import com.example.emag.services.RenderService;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import static com.example.emag.utils.Constants.SESSION_KEY_LOGGED_USER;

@Slf4j
@RestController
public class RenderController {

  @Autowired
  private RenderService renderService;

  @GetMapping(value = "/render/{productId}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public void renderImage(@PathVariable(name = "productId") long productId, HttpServletResponse response) throws IOException, SQLException {
    response.setContentType(MediaType.IMAGE_JPEG_VALUE);
    ServletOutputStream outputStream = response.getOutputStream();
    ClassPathResource img = renderService.renderImage(productId);
    StreamUtils.copy(img.getInputStream(), outputStream);
  }

  @PostMapping("/products/uploadImg")
  public void uploadImage(@RequestParam("file") MultipartFile file,
      @RequestParam("productId") Long productId,
      HttpSession session) {
    User user = (User) session.getAttribute(SESSION_KEY_LOGGED_USER);
    renderService.uploadImage(file, productId, user);
  }
}
