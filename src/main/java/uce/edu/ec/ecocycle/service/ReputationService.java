/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uce.edu.ec.ecocycle.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uce.edu.ec.ecocycle.model.Reputation;
import uce.edu.ec.ecocycle.model.User;
import uce.edu.ec.ecocycle.repository.ReputationRepository;


@Service
public class ReputationService {

    @Autowired
    private ReputationRepository reputationRepository;

    public void saveReputation(Reputation reputation) {
        reputationRepository.save(reputation);
    }

    public List<Reputation> getReputationByUser(User user) {
        return reputationRepository.findByRatedUser(user);
    }
}
