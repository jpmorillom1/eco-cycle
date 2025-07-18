
package uce.edu.ec.ecocycle.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uce.edu.ec.ecocycle.model.Product;
import uce.edu.ec.ecocycle.model.User;
import uce.edu.ec.ecocycle.repository.ProductRepository;
import uce.edu.ec.ecocycle.repository.TransactionRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
        @Autowired
    private TransactionRepository transactionRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }
    
    public List<Product> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Product> getProductByUser(String user_id) {
        return productRepository.findByUserUserID(user_id);
}
    
    public Product getProductById(Long id) {
    return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
}

    
    
    
    @Transactional
    public void deleteProduct(Long productId) {
        // Eliminar todas las transacciones asociadas al producto
        transactionRepository.deleteByProductId(productId);

        // Ahora sí, eliminar el producto
        productRepository.deleteById(productId);
    }
    
    
    public Product updateProduct(Product product) {
        return productRepository.save(product); // Actualizar producto
    }
}