package com.edu.Ecom_product.Controller;

import com.edu.Ecom_product.Model.Product;
import com.edu.Ecom_product.Service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@RestController
@RequestMapping("/api")
@XmlRootElement
public class ProductController
{
    @Autowired
    private ProductService productService;


   // @GetMapping("/getAllProducts")
    @RequestMapping(value = "/getAllProducts",method = RequestMethod.GET,produces={MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<Product>> getAllProducts(){
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

   // @GetMapping("/getProduct/{id}")
    @RequestMapping(value = "/getProduct/{id}",method = RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
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
   // @PostMapping(value = "/addProduct")
    @RequestMapping(value = "/addProduct",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addProduct( @RequestBody Product product)
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

   // @DeleteMapping("/delProduct/{id}")
    @RequestMapping(value = "/delProduct/{id}",method = RequestMethod.DELETE)
    public void deleteProduct(@PathVariable int id){
        productService.deleteById(id);
    }

    //@PutMapping("/updateProduct")
    @RequestMapping(value = "/updateProduct",method = RequestMethod.PUT,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> update(@RequestBody Product product){
        return new ResponseEntity<>(productService.updateProduct(product),HttpStatus.ACCEPTED);
    }

  //@GetMapping("/searchByKeyword/{keyword}")
    @RequestMapping(value = "/searchByKeyword/{keyword}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchByKeyword(@PathVariable String keyword){
        List<Product> p=productService.searchByKeyword(keyword);
        if(p!=null)
            return new ResponseEntity<>(p,HttpStatus.FOUND);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
