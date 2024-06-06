package com.aminkbi.learnspring.services;


import com.aminkbi.learnspring.dtos.product.ProductDTO;
import com.aminkbi.learnspring.exceptions.NotFoundException;
import com.aminkbi.learnspring.models.Category;
import com.aminkbi.learnspring.models.Product;
import com.aminkbi.learnspring.models.Supplier;
import com.aminkbi.learnspring.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;

    }

    public Product addProduct(ProductDTO productDTO){
        return productRepository.save(mapToProduct(productDTO));
    }

    public Optional<Product> getProductById(Long id){
        return productRepository.findById(id);
    }

    public Product updateProduct(Long id, ProductDTO productDTO) {
        productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + id));

        return productRepository.save(mapToProduct(productDTO));
    }

    public void deleteProductById(Long id){
        productRepository.deleteById(id);
    }

    public List<Product> getAllProducts(Integer page, Integer pageSize){
        return productRepository.findAll(Pageable.ofSize(pageSize).withPage(page)).stream().collect(Collectors.toList());
    }

    public List<Product> findAllByNameContaining(Integer page, Integer pageSize, String name){
        return productRepository.findAllByNameContaining(Pageable.ofSize(pageSize).withPage(page), name).stream().collect(Collectors.toList());
    }

    private Product mapToProduct(ProductDTO productDTO) {

        Product product = new Product();

        Supplier supplier = new Supplier();
        supplier.setId(productDTO.getSupplierId());

        Category category = new Category();
        category.setId(productDTO.getCategoryId());

        product.setSupplier(supplier);
        product.setCategory(category);
        product.setName(productDTO.getName());
        product.setQuantity(product.getQuantity());
        product.setExpiryDate(productDTO.getExpiryDate());
        product.setDescription(product.getDescription());
        product.setPrice(productDTO.getPrice());

        return product;
    }
}
