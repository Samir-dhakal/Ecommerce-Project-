package com.samir.ecom_proj.controller;

import com.samir.ecom_proj.model.Product;
import com.samir.ecom_proj.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.net.ssl.SSLEngineResult;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api")
public class ProductController {


   @Autowired
    ProductService service ;
    @RequestMapping("/")
    @ResponseBody
public String homepage(){

        return " this is home page ";
}
@RequestMapping("/products")
public ResponseEntity<List<Product>> getProducts(){

        return  new ResponseEntity<>(service.getAllProducts(),HttpStatus.OK);
}
@GetMapping("/product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id){
        Product product = service.getProductById(id);
        if(product==null){
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new  ResponseEntity<>( service.getProductById(id), HttpStatus.OK);
}
//    @GetMapping("/product/{id}")
//    public Product getProductById(@PathVariable int id){
//        return service.getProductById(id);
//    }
    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product,@RequestPart MultipartFile imageFile){
try{
    Product product1=service.addProduct(product,imageFile);
    return new  ResponseEntity<>(product1,HttpStatus.OK);
}
catch (Exception e){
    return  new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
}

    }
    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]> getImageById(@PathVariable int productId) {
        Product product = service.getProductById(productId);
        byte[] imageFile=product.getImageData();
      return ResponseEntity.ok()
              .contentType(MediaType.valueOf(product.getImageType()))
              .body(imageFile);
    }
    @PutMapping("/product/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable  int id , @RequestPart Product product,@RequestPart MultipartFile imageFile){
        Product product1=null;
        try{
         product1=service.updateProduct(id,product,imageFile);}
        catch (Exception e){
            return  new ResponseEntity<>("failed to update product ",HttpStatus.BAD_REQUEST);
        }
        if(product1 != null) {
            return new ResponseEntity<>("updated", HttpStatus.OK);
        }
        else{
           ;
            return new ResponseEntity<>("failed to update ",HttpStatus.BAD_REQUEST);
        }





    }
    @DeleteMapping("/product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable int id ){
        Product product1= service.getProductById(id);
        if(product1!=null){
            service.deleteProduct(id);
            return new ResponseEntity<>("deleted", HttpStatus.OK);
        }
        else {
            return  new ResponseEntity<>("couldnot found the product ",HttpStatus.NOT_FOUND);
        }


    }
    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProduct(@RequestParam String keyword){
        System.out.println(" searching with "+keyword);
         List <Product> products =service.searchProduct(keyword);
         return   new ResponseEntity<>(products,HttpStatus.OK);
    }
}
