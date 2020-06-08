package com.example.emag.repository;

import com.example.emag.entity.Product;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Definition of ProductRepository methods
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

  String FIND_BY_TEXT_QUERY = "SELECT p.*FROM products AS p "
      + "JOIN sub_categories AS sc ON p.sub_category_id = sc.id "
      + "JOIN categories AS c ON sc.category_id = c.id "
      + "WHERE p.name LIKE %?1% AND p.deleted = 0 AND p.price BETWEEN ?2 AND ?3 ORDER BY ?4 ?5";

  String FIND_BY_SUB_CATEGORY_QUERY = "SELECT p.*FROM products AS p "
      + "JOIN sub_categories AS sc ON p.sub_category_id = sc.id "
      + "JOIN categories AS c ON sc.category_id = c.id "
      + "WHERE p.sub_category_id = ?1 AND p.deleted = 0 AND p.price BETWEEN ?2 AND ?3 ORDER BY ?4 ?5";

  String USER_FAVOURITE_PRODUCTS_QUERY = "SELECT p.* FROM products AS p "
      + "JOIN sub_categories AS sc ON p.sub_category_id = sc.id "
      + "JOIN categories AS c ON sc.category_id = c.id "
      + " JOIN users_have_favourite_products AS uhfp ON p.id = uhfp.product_id "
      + "JOIN users AS u ON uhfp.user_id = u.id WHERE u.id = ?1";

  @Query(
      value = FIND_BY_SUB_CATEGORY_QUERY,
      nativeQuery = true
  )
  List<Product> findAllBySubCategoryIdAndPriceBetweenAndOrderBy(Long subCategoryId, BigDecimal min,
      BigDecimal max, String column, String orderBy);

  @Query(
      value = FIND_BY_TEXT_QUERY,
      nativeQuery = true
  )
  List<Product> findByNameContainingAAndPriceBetweenAndOrderBy(String text, BigDecimal min,
      BigDecimal max, String column, String orderBy);

  @Query(
      value = USER_FAVOURITE_PRODUCTS_QUERY,
      nativeQuery = true
  )
  List<Product> findUserFavouriteProducts(Long userId);
}
