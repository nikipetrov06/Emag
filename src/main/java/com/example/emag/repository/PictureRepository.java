package com.example.emag.repository;

import com.example.emag.entity.Picture;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Definition of PictureRepository methods
 */
@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {

  List<Picture> findAllByProductId(Long productId);
}
