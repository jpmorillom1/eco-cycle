package uce.edu.ec.ecocycle.service;

import uce.edu.ec.ecocycle.model.Transaction;
import uce.edu.ec.ecocycle.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import uce.edu.ec.ecocycle.model.Product;
import uce.edu.ec.ecocycle.model.User;

@Service
public class TransactionService {
    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService; 
    
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }
    
    public Transaction getTransactionById(Long id) {
    return transactionRepository.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
}
    public List<Transaction> getUserTransactions(Long userId) {
        return transactionRepository.findByBuyerId(userId);
    }
    
    public List<Transaction> getTransactionsBySeller(User seller) {
        return transactionRepository.findByProductUser(seller);
    }
    
    public List<Transaction> getSellerTransactions(Long sellerId) {
        return transactionRepository.findByProductUserId(sellerId);
    }
    
      public List<Transaction> getPendingTransactionsBySeller(User seller) {
        return transactionRepository.findByProductUser(seller);
    }
      
      

      public List<Transaction> getTransactionsByBuyer(User buyer) {
        return transactionRepository.findByBuyer(buyer); // Obtener transacciones por comprador
    }
      
 public void confirmTransaction(Long id) {
        Transaction transaction = transactionRepository.findById(id).orElse(null);
        if (transaction != null) {
            // Actualizar el estado de la transacción
            transaction.setStatus("CONFIRMADA");
            transactionRepository.save(transaction);

            // Reducir la cantidad del producto
            Product product = transaction.getProduct();
            product.setQuantity(product.getQuantity() - transaction.getQuantity());
            productService.updateProduct(product);

            // Asignar EcoPoints al vendedor y al comprador
            User seller = product.getUser();
            User buyer = transaction.getBuyer();

            seller.setEcoPoints(seller.getEcoPoints() + 10); // Ejemplo: 10 puntos para el vendedor
            buyer.setEcoPoints(buyer.getEcoPoints() + 5); // Ejemplo: 5 puntos para el comprador

            userService.updateUser(seller);
            userService.updateUser(buyer);
        }
    }
 

    public void rejectTransaction(Long id) {
        Transaction transaction = transactionRepository.findById(id).orElse(null);
        if (transaction != null) {
            transaction.setStatus("RECHAZADA");
            transactionRepository.save(transaction);
        }
    }
}
