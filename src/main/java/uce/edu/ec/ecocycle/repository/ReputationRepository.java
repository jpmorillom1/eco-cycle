
package uce.edu.ec.ecocycle.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uce.edu.ec.ecocycle.model.Reputation;
import uce.edu.ec.ecocycle.model.User;



@Repository
public interface ReputationRepository extends JpaRepository<Reputation, Long> {
    List<Reputation> findByRatedUser(User ratedUser); // Obtener reputación de un usuario
    
}

