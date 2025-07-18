/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uce.edu.ec.ecocycle.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uce.edu.ec.ecocycle.model.Reputation;
import uce.edu.ec.ecocycle.model.Transaction;
import uce.edu.ec.ecocycle.model.User;
import uce.edu.ec.ecocycle.service.ReputationService;
import uce.edu.ec.ecocycle.service.TransactionService;

@Controller
public class ReputationController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ReputationService reputationService;

    @GetMapping("/rate-transaction/{transactionId}")
    public String rateTransaction(@PathVariable Long transactionId, Model model, HttpSession session) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            return "redirect:/";
        }

        // Obtener la transacción
        Transaction transaction = transactionService.getTransactionById(transactionId);
        if (transaction == null || (!transaction.getStatus().equals("CONFIRMADA") && !transaction.getStatus().equals("RECHAZADA"))) {
            return "redirect:/my-purchases";
        }

        model.addAttribute("transaction", transaction);
        model.addAttribute("reputation", new Reputation());
        return "rate-transaction";
    }

    @PostMapping("/submit-rating")
    public String submitRating(@RequestParam Long transactionId, @RequestParam int rating, @RequestParam String comment, HttpSession session) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            return "redirect:/";
        }

        Transaction transaction = transactionService.getTransactionById(transactionId);
        if (transaction == null || (!transaction.getStatus().equals("CONFIRMADA") && !transaction.getStatus().equals("RECHAZADA"))) {
            return "redirect:/my-purchases";
        }

        Reputation reputation = new Reputation();
        reputation.setRatedUser(transaction.getProduct().getUser()); // Calificamos al vendedor
        reputation.setRaterUser(loggedUser); // Quien deja la calificación es el comprador
        reputation.setRating(rating);
        reputation.setComment(comment);

        reputationService.saveReputation(reputation);

        return "redirect:/my-purchases";
    }
}
