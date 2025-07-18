package uce.edu.ec.ecocycle.repository;

import uce.edu.ec.ecocycle.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uce.edu.ec.ecocycle.model.Reputation;
import uce.edu.ec.ecocycle.model.User;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByBuyerId(Long userId);
    List<Transaction> findByProductUserId(Long sellerId); // Transacciones donde el usuario es vendedor
    List<Transaction> findByProductUser(User seller); // Obtener transacciones por vendedor
List<Transaction> findByProductUserAndStatus(User seller, String status);
    List<Transaction> findByBuyer(User buyer); // Transacciones por comprador

    
    @Modifying
@Query("DELETE FROM Transaction t WHERE t.product.id = :productId")
void deleteByProductId(@Param("productId") Long productId);
}
