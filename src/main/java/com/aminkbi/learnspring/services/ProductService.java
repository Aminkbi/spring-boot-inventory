package com.aminkbi.learnspring.services;


import com.aminkbi.learnspring.dtos.product.ProductDTO;
import com.aminkbi.learnspring.exceptions.NotFoundException;
import com.aminkbi.learnspring.models.Category;
import com.aminkbi.learnspring.models.Product;
import com.aminkbi.learnspring.models.Supplier;
import com.aminkbi.learnspring.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;




    @Autowired
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;

    }

    public Product addProduct(ProductDTO productDTO){
        var product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());

        var category = new Category();
        category.setId(productDTO.getId());
        product.setCategory(category);

        var supplier = new Supplier();
        supplier.setId(productDTO.getId());
        product.setSupplier(supplier);

        return productRepository.save(product);
    }

    public Optional<Product> getProductById(Long id){
        return productRepository.findById(id);
    }

    public Product updateProduct(Long id, ProductDTO productDTO) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            product.setName(productDTO.getName());

            Category category = new Category();
            category.setId(productDTO.getId());
            product.setCategory(category);

            Supplier supplier = new Supplier();
            supplier.setId(productDTO.getId());
            product.setSupplier(supplier);

            product.setDescription(productDTO.getDescription());
            product.setPrice(productDTO.getPrice());
            product.setQuantity(productDTO.getQuantity());
            return productRepository.save(existingProduct.get());
        } else {
            throw new NotFoundException("Product not found with id: " + id);
        }
    }

    public void deleteProductById(Long id){
        productRepository.deleteById(id);
    }

    public Page<Product> getAllCategories(Integer page, Integer pageSize){
        return productRepository.findAll(Pageable.ofSize(pageSize).withPage(page));
    }
}
