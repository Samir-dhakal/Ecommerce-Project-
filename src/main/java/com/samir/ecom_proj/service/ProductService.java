package com.samir.ecom_proj.service;

import com.samir.ecom_proj.reop.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.samir.ecom_proj.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService  {
//    public List<Product> Products;

//    @Autowired
    private final  ProductRepo repository;
@Autowired
public ProductService(ProductRepo repository) {
    this.repository = repository;
}
    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public Product getProductById(int id) {
    return repository.findById(id).orElse(null);
    }

    public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
    product.setImageName(imageFile.getOriginalFilename());
    product.setImageType(imageFile.getContentType());
    product.setImageData(imageFile.getBytes());
    return repository.save(product);
    }


    public Product updateProduct(int id, Product product, MultipartFile imageFile) throws   IOException {

     product.setImageData(imageFile.getBytes());

    product.setImageName(imageFile.getOriginalFilename());
    product.setImageType(imageFile.getContentType());
//    repository.save(getProductById(id));
    return repository.save(product);
    }
    // In your Service class (image_cd16bd.png)

//    public Product updateProduct(int id, Product productDetails, MultipartFile imageFile) throws IOException {
//
//        // 1. Find the *EXISTING* product from the database
//        Product existingProduct = repository.findById(id).orElse(null);
//
//        // 2. Check if it exists
//        if (existingProduct == null) {
//            // Product not found, return null so the controller can handle it
//            return null;
//        }
//
//        // 3. Update the fields of the *EXISTING* product
//        //    with data from the 'productDetails' object
//        existingProduct.setName(productDetails.getName());
//        existingProduct.setDescription(productDetails.getDescription());
//        existingProduct.setPrice(productDetails.getPrice());
//        // (add any other fields you have...)
//
//        // 4. Now, set the new image properties
//        existingProduct.setImageData(imageFile.getBytes());
//        existingProduct.setImageName(imageFile.getOriginalFilename());
//        existingProduct.setImageType(imageFile.getContentType());
//
//        // 5. Save the *EXISTING* product.
//        //    Because it has an ID, JPA will perform an UPDATE.
//        return repository.save(existingProduct);
//    }

    public void deleteProduct(int id) {
//        Product product = repository.findById(id).orElse(null);
       repository.deleteById(id);
    }

    public List<Product> searchProduct(String keyword ) {
    return repository.searchProduct(keyword);
    }
}



