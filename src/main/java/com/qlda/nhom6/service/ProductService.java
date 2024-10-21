package com.qlda.nhom6.service;

import com.NgocHieu.Buoi22.model.Product;
import com.NgocHieu.Buoi22.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }
    public void updateProduct(Long id, Product updatedProduct) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setName(updatedProduct.getName());
            product.setCategory(updatedProduct.getCategory());
            product.setNums(updatedProduct.getNums());
            product.setPrice(updatedProduct.getPrice());
            product.setDetail(updatedProduct.getDetail());
            product.setMeta(updatedProduct.getMeta());
            product.setThumnail(updatedProduct.getThumnail());
            product.setOrder(updatedProduct.getOrder());
            product.setLink(updatedProduct.getLink());
            product.setHide(updatedProduct.isHide());
            productRepository.save(product);
        }
    }

    public void deleteProductById(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalStateException("Product with ID " + id + " does not exist.");
        }
        productRepository.deleteById(id);
    }

    public List<Product> getProductsByPage(int page, int pageSize) {
        // Tính toán vị trí bắt đầu của danh sách sản phẩm
        int startIndex = (page - 1) * pageSize;

        // Lấy danh sách sản phẩm từ vị trí bắt đầu và kích thước trang
        List<Product> allProducts = getAllProducts();
        List<Product> products = new ArrayList<>();

        for (int i = startIndex; i < startIndex + pageSize && i < allProducts.size(); i++) {
            products.add(allProducts.get(i));
        }

        return products;
    }

    public int getTotalPages(int pageSize) {
        List<Product> allProducts = getAllProducts();
        return (int) Math.ceil((double) allProducts.size() / pageSize);
    }
}

