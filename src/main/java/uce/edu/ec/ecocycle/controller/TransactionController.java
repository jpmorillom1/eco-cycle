package uce.edu.ec.ecocycle.controller;

import uce.edu.ec.ecocycle.model.Transaction;
import uce.edu.ec.ecocycle.model.User;
import uce.edu.ec.ecocycle.model.Product;
import uce.edu.ec.ecocycle.service.TransactionService;
import uce.edu.ec.ecocycle.service.ProductService;
import uce.edu.ec.ecocycle.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final ProductService productService;
    private final UserService userService;

    public TransactionController(TransactionService transactionService, ProductService productService, UserService userService) {
        this.transactionService = transactionService;
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping("/buy/{productId}")
    public String showBuyProductForm(@PathVariable Long productId, Model model) {
        Product product = productService.getProductById(productId);
        model.addAttribute("product", product);
        return "buyProduct";
    }
    
 @GetMapping("/my-purchases")
public String showMyPurchases(Model model, HttpSession session) {
    User loggedUser = (User) session.getAttribute("loggedUser");
    if (loggedUser == null) {
        return "redirect:/";
    }
    // Obtener las compras del usuario logueado
    List<Transaction> purchases = transactionService.getTransactionsByBuyer(loggedUser);
    model.addAttribute("purchases", purchases);

    return "purchases"; // Vista donde se mostrarán las compras
}   
    
    @GetMapping("/transactions")
public String showTransactions(Model model, HttpSession session) {
    User loggedUser = (User) session.getAttribute("loggedUser");
    if (loggedUser == null) {
        return "redirect:/";
    }

    System.out.println("Usuario logueado: " + loggedUser.getName());

    List<Transaction> pendingTransactions = transactionService.getPendingTransactionsBySeller(loggedUser);
    
    System.out.println("Transacciones encontradas: " + pendingTransactions.size());

    model.addAttribute("transactions", pendingTransactions);
    return "transactions";
}

@PostMapping("/buy-product/{id}")
public String buyProduct(@PathVariable Long id, @RequestParam int quantity, HttpSession session, Model model) {
    User loggedUser = (User) session.getAttribute("loggedUser");
    if (loggedUser == null) {
        return "redirect:/"; // Redirigir si el usuario no está logueado
    }

    // Obtener el producto por ID
    Product product = productService.getProductById(id);
    if (product == null || quantity > product.getQuantity()) {
        return "redirect:/products"; // Redirigir si el producto no existe o la cantidad no es válida
    }

    // Crear una transacción pendiente
    Transaction transaction = new Transaction();
    transaction.setBuyer(loggedUser);
    transaction.setProduct(product);
    transaction.setQuantity(quantity);
    transaction.setTransactionDate(LocalDateTime.now());
    transaction.setStatus("PENDIENTE"); // Establecer el estado inicial
    transactionService.saveTransaction(transaction);

    // Mostrar los datos del vendedor
    model.addAttribute("seller", product.getUser());
    model.addAttribute("quantity", quantity);
    model.addAttribute("product", product);
    return "contact-seller"; // Vista con los datos de contacto del vendedor
}
}