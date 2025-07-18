package uce.edu.ec.ecocycle.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uce.edu.ec.ecocycle.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByUserUserID(String userID);
    List<Product> findByNameContainingIgnoreCase(String name);
}



