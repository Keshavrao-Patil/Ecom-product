package com.edu.Ecom_product.Controller;

import com.edu.Ecom_product.Model.Product;
import com.edu.Ecom_product.Service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController
{
    @Autowired
    private ProductService productService;


    @GetMapping("/getAllProducts")
    public ResponseEntity<List<Product>> getAllProducts(){
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/getProduct/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id){
        Product p=productService.getProductById(id);
        if (p!=null)
            return new ResponseEntity<>(p,HttpStatus.FOUND);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
/*
    @PostMapping(value = "/addProduct",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addProduct(@RequestPart("product") Product product, @RequestPart MultipartFile imageFile)
    {
        try{
            Product product1=productService.saveProduct(product,imageFile);
            return new ResponseEntity<>(product1,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
*/
    @PostMapping(value = "/addProduct")
    public ResponseEntity<?> addProduct(@RequestBody Product product)
    {
        try{
            Product product1=productService.saveProduct(product);
            return new ResponseEntity<>(product1,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
/*    @GetMapping("/getProductByImageId/{id}/image")
    public ResponseEntity<byte[]> getProductByImageId(@PathVariable int id) {

        Product product = productService.getProductById(id);

        if (product == null || product.getImageData() == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, product.getImageType())
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + product.getImageName() + "\"")
                .body(product.getImageData());
    }*/

    @DeleteMapping("/delProduct/{id}")
    public void deleteProduct(@PathVariable int id){
        productService.deleteById(id);
    }

    @PutMapping("/updateProduct")
    public ResponseEntity<Product> update(@RequestBody Product product){
        return new ResponseEntity<>(productService.updateProduct(product),HttpStatus.ACCEPTED);
    }

    @GetMapping("/searchByKeyword/{keyword}")
    public ResponseEntity<?> searchByKeyword(@PathVariable String keyword){
        List<Product> p=productService.searchByKeyword(keyword);
        if(p!=null)
            return new ResponseEntity<>(p,HttpStatus.FOUND);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
