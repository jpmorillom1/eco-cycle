
package uce.edu.ec.ecocycle.repository;



import uce.edu.ec.ecocycle.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUserID(String userID);
    
}