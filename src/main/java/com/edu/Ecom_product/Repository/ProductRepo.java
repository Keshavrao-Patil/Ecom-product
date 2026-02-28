package com.edu.Ecom_product.Repository;

import com.edu.Ecom_product.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,Integer> {

    @Query("select p from Product p where " +
            "Lower(p.brand) LIKE LOWER(concat('%',:keyword,'%')) OR " +
            "cast(p.Id as string ) LIKE %:keyword% OR " +
            "cast(p.price as string) LIKE %:keyword% OR " +
            "lower(p.productName) like lower(concat('%',:keyword,'%')) ")
    List<Product> searchProductByKeyword(String keyword);
}
