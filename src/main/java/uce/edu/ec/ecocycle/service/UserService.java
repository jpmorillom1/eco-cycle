
package uce.edu.ec.ecocycle.service;

import java.util.List;
import uce.edu.ec.ecocycle.model.User;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uce.edu.ec.ecocycle.model.Reputation;
import uce.edu.ec.ecocycle.repository.ReputationRepository;
import uce.edu.ec.ecocycle.repository.UserRepository;






@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
@Autowired
    private ReputationRepository reputationRepository;

    public Optional<User> encontrarUsuario(String userID) {
        return userRepository.findByUserID(userID);
    }
    
    public User getUserById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<Reputation> getReputationsByUser(User user) {
        return reputationRepository.findByRatedUser(user); // Obtener comentarios del usuario
    }
    
    
    public void registrarUsuario(User user) {
        userRepository.save(user);
    }
    
     public void updateUser(User user) {
    userRepository.save(user);
    }
    

    
        public void updateUserRank(User user) {
        int points = user.getEcoPoints();
        if (points >= 151) {
            user.setRank("EcoHéroe");
        } else if (points >= 51) {
            user.setRank("EcoAliado");
        } else {
            user.setRank("EcoIniciado");
        }
        userRepository.save(user);
    }
        



        


}