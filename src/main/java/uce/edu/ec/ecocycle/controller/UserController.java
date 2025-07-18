package uce.edu.ec.ecocycle.controller;

import uce.edu.ec.ecocycle.model.User;
import uce.edu.ec.ecocycle.service.UserService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import uce.edu.ec.ecocycle.model.Reputation;
import uce.edu.ec.ecocycle.model.UserType;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String login(Model model) {
        model.addAttribute("user", new User());
        return "login"; 
    }

    @PostMapping("/userLogin")
    public String loginUsuario(@ModelAttribute("user") User user, HttpSession session, Model model) {
        String userId = user.getUserID();
        Optional<User> userdata = userService.encontrarUsuario(userId);
        
        if (userdata.isPresent() && user.getPassword().equals(userdata.get().getPassword())) {
            session.setAttribute("loggedUser", userdata.get());
            System.out.println("============");
            System.out.println("hola");
            return "redirect:/home";
            
        } else {
            model.addAttribute("error", "Usuario o contraseña incorrectos.");
            System.out.println("chao");
            return "login";
        }
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

@PostMapping("/register")
public String registerUser(@ModelAttribute("user") User user, Model model) {
    System.out.println("Intentando registrar usuario: " + user.getUserID());

    // Verificar si el usuario ya existe
    if (userService.encontrarUsuario(user.getUserID()).isPresent()) {
        System.out.println("❌ ERROR: El usuario ya existe en la BD.");
        model.addAttribute("error", "El usuario ya existe.");
        return "register";
    }

    // Verificar que el campo name no sea nulo o vacío
    if (user.getName() == null || user.getName().trim().isEmpty()) {
        System.out.println("❌ ERROR: El nombre no puede estar vacío.");
        model.addAttribute("error", "El nombre es obligatorio.");
        return "register";
    }

    // Asignar valores predeterminados si no se enviaron
    user.setEcoPoints(0);
    user.setRank("EcoIniciado");
    user.setStatus("activo");

    // Asignar un tipo de usuario por defecto si no se selecciona
    if (user.getUserType() == null) {
        user.setUserType(UserType.particular);
    }

    userService.registrarUsuario(user);
    System.out.println("✅ Usuario registrado correctamente: " + user.getUserID());
    return "redirect:/";
}

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
    
    @GetMapping("/home")
    public String homePage(HttpSession session, Model model) {
        User loggedUser = (User) session.getAttribute("loggedUser");

        if (loggedUser == null) {
            return "redirect:/"; // 🔹 Si no hay usuario en sesión, vuelve al login
        }

        model.addAttribute("user", loggedUser);
        return "home"; 
}

    

    // Mostrar perfil de usuario
    @GetMapping("/profile")
    public String userProfile(HttpSession session, Model model) {
        User loggedUser = (User) session.getAttribute("loggedUser");

        if (loggedUser == null) {
            return "redirect:/"; // Redirige al login si no hay sesión activa
        }

        model.addAttribute("user", loggedUser);
        return "profile"; // Vista Thymeleaf para mostrar datos del perfil
    }


    
    @GetMapping("/profile/{id}")
    public String showUserProfile(@PathVariable String id, Model model, HttpSession session) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            return "redirect:/"; // Redirigir si el usuario no está logueado
        }

        // Obtener el usuario cuyo perfil se está viendo
        User user = userService.getUserById(id);
        if (user == null) {
            return "redirect:/"; // Redirigir si el usuario no existe
        }

        // Obtener los comentarios (reputación) del usuario
        List<Reputation> reputations = userService.getReputationsByUser(user);

        model.addAttribute("user", user);
        model.addAttribute("reputations", reputations);
        return "profile"; // Vista del perfil del usuario
    }
    
@Controller
public class RedirectController {
    @GetMapping("/ir-a-tips")
    public String redirectToTips() {
        return "redirect:/tips.html";
    }
}


}





