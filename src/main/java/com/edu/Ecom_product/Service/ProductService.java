package com.edu.Ecom_product.Service;

import com.edu.Ecom_product.Model.Product;
import com.edu.Ecom_product.Repository.ProductRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class ProductService
{
    @Autowired
    private ProductRepo productRepo;

    public List<Product> getAllProducts(){
        return productRepo.findAll();
    }

    public Product getProductById(int id){
        Product p=productRepo.findById(id).get();
        log.info(p.toString());
        return productRepo.findById(id).get();
    }

    public void deleteById(int id){
        productRepo.deleteById(id);
    }

/*
    public Product saveProduct(Product product, MultipartFile multipartFile) throws IOException {
        product.setImageName(multipartFile.getOriginalFilename());
        product.setImageType(multipartFile.getContentType());
        product.setImageData(multipartFile.getBytes());
        return productRepo.save(product);
    }
*/

    public Product saveProduct(Product product) throws IOException {
        return productRepo.save(product);
    }
    public Product updateProduct(Product product){
       Product p=productRepo.findById(product.getId()).get();
       return productRepo.save(product);
    }

    public List<Product> searchByKeyword(String keyword) {
        return productRepo.searchProductByKeyword(keyword);
    }
}
