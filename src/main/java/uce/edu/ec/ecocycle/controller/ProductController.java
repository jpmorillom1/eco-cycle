package uce.edu.ec.ecocycle.controller;

import uce.edu.ec.ecocycle.model.Product;
import uce.edu.ec.ecocycle.model.User;
import uce.edu.ec.ecocycle.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import uce.edu.ec.ecocycle.model.Transaction;
import uce.edu.ec.ecocycle.service.TransactionService;



@Controller
public class ProductController {

    @Autowired
    private ProductService productService;
    
    @Autowired
    private TransactionService transactionService;

    private static final String UPLOAD_DIR = "C:/EcoCycleUploads/";

@GetMapping("/products")
public String showProducts(
        @RequestParam(value = "query", required = false) String query,
        Model model,
        HttpSession session) {

    User loggedUser = (User) session.getAttribute("loggedUser");
    if (loggedUser == null) {
        return "redirect:/"; // Redirigir si no está logueado
    }

    List<Product> products;
    if (query != null && !query.trim().isEmpty()) {
        products = productService.searchProductsByName(query);
    } else {
        products = productService.getAllProducts();
    }

    model.addAttribute("products", products);
    model.addAttribute("user", loggedUser);
    return "products"; // Nombre del archivo HTML de la vista
}
    
    
    
    
    

    @GetMapping("/addProduct")
    public String showAddProductForm(Model model, HttpSession session) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            return "redirect:/"; 
        }

        model.addAttribute("product", new Product());
        return "addProduct"; 
    }

   @PostMapping("/addProduct")
public String addProduct(@ModelAttribute Product product,
                         @RequestParam("image") MultipartFile image,
                         HttpSession session) throws IOException {

    User loggedUser = (User) session.getAttribute("loggedUser");
    if (loggedUser == null) {
        return "redirect:/";
    }

    // 📌 Asegurar que la carpeta de uploads exista
    File uploadDir = new File(UPLOAD_DIR);
    if (!uploadDir.exists()) {
        uploadDir.mkdirs(); // Crea la carpeta si no existe
    }

    // 📌 Guardar la imagen si el usuario subió una
    if (!image.isEmpty()) {
        System.out.println("📷 Subiendo imagen...");
        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR, fileName);
        image.transferTo(filePath.toFile());
        product.setImageUrl(fileName); // Solo almacenar el nombre del archivo
    } else {
        product.setImageUrl("default.png"); // Imagen por defecto
    }

    product.setUser(loggedUser);
    productService.saveProduct(product);

    return "redirect:/products";
}
//======
    @GetMapping("details/{id}")
    public String showProductDetails(@PathVariable Long id, Model model) {
        System.out.println("Intentando mostrar producto con ID: " + id);
        Product product = productService.getProductById(id);
        if (product == null) {
            System.out.println("Producto no encontrado");
            return "redirect:/products";
        }
        
        System.out.println("Producto encontrado: " + product.getName());
        model.addAttribute("product", product);
        return "product-details"; // Debe coincidir con el nombre del HTML
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

    // Guardar la transacción
    Transaction transaction = new Transaction();
    transaction.setBuyer(loggedUser);
    transaction.setProduct(product);
    transaction.setQuantity(quantity);
    transaction.setTransactionDate(LocalDateTime.now());
    transactionService.saveTransaction(transaction);

    // Reducir la cantidad disponible del producto
    product.setQuantity(product.getQuantity() - quantity);
    productService.updateProduct(product);

    // Mostrar los datos del vendedor
    model.addAttribute("seller", product.getUser());
    model.addAttribute("quantity", quantity);
    model.addAttribute("product", product);
    return "contact-seller"; // Vista con los datos de contacto del vendedor
}    
    



@GetMapping("/edit-product/{id}")
public String showEditProductForm(@PathVariable Long id, Model model, HttpSession session) {
    User loggedUser = (User) session.getAttribute("loggedUser");
    if (loggedUser == null) {
        return "redirect:/"; // Redirigir si el usuario no está logueado
    }

    // Obtener el producto por ID
    Product product = productService.getProductById(id);
    if (product == null || !product.getUser().getUserID().equals(loggedUser.getUserID())) {
        return "redirect:/my-products"; // Redirigir si el producto no existe o no pertenece al usuario
    }

    model.addAttribute("product", product);
    return "edit-product"; // Vista para editar el producto
}

// Guardar los cambios del producto editado
@PostMapping("/edit-product/{id}")
public String updateProduct(@PathVariable Long id, @ModelAttribute Product product, HttpSession session) {
    User loggedUser = (User) session.getAttribute("loggedUser");
    if (loggedUser == null) {
        return "redirect:/";
    }

    // Verificar que el producto exista y pertenezca al usuario
    Product existingProduct = productService.getProductById(id);
    if (existingProduct == null || !existingProduct.getUser().getUserID().equals(loggedUser.getUserID())) {
        return "redirect:/my-products"; // Redirigir si el producto no existe o no pertenece al usuario
    }

    // Actualizar los datos del producto
    existingProduct.setName(product.getName());
    existingProduct.setQuantity(product.getQuantity());
    existingProduct.setStatus(product.getStatus());
    existingProduct.setExpirationDate(product.getExpirationDate());
    existingProduct.setDescription(product.getDescription());

    productService.updateProduct(existingProduct);
    return "redirect:/my-products";
}

@GetMapping("/delete-product/{id}")
public String deleteProduct(@PathVariable Long id, HttpSession session) {
    User loggedUser = (User) session.getAttribute("loggedUser");
    if (loggedUser == null) {
        return "redirect:/";
    }

    // Verificar que el producto exista y pertenezca al usuario
    Product product = productService.getProductById(id);
    if (product != null && product.getUser().getUserID().equals(loggedUser.getUserID())) {
        productService.deleteProduct(id); // Eliminar el producto si pertenece al usuario
    }

    return "redirect:/my-products";
}
    
    
        @GetMapping("/my-products")
        public String showMyProducts(Model model, HttpSession session) {
            User loggedUser = (User) session.getAttribute("loggedUser");
            if (loggedUser == null) {
                return "redirect:/"; // Redirigir si el usuario no está logueado
            }

            // Obtener los productos del usuario logueado usando su userID
            List<Product> userProducts = productService.getProductByUser(loggedUser.getUserID());
            model.addAttribute("products", userProducts);
            model.addAttribute("user", loggedUser);

            return "my-products"; // Vista para mostrar los productos del usuario
        }
    
@RestController
@RequestMapping("/uploads")
public class ImageController {

    private static final String UPLOAD_DIR = "C:/EcoCycleUploads/";

    @GetMapping("/{filename:.+}")
    public Resource getImage(@PathVariable String filename) throws MalformedURLException {
        Path filePath = Paths.get(UPLOAD_DIR).resolve(filename);
        Resource resource = new UrlResource(filePath.toUri());
        if (resource.exists() || resource.isReadable()) {
            return resource;
        } else {
            throw new RuntimeException("No se pudo leer el archivo: " + filename);
        }
    }
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

@GetMapping("/my-purchases")
public String showMyPurchases(Model model, HttpSession session) {
    User loggedUser = (User) session.getAttribute("loggedUser");
    if (loggedUser == null) {
        return "redirect:/"; // Redirigir si el usuario no está logueado
    }

    // Obtener las transacciones del comprador
    List<Transaction> buyerTransactions = transactionService.getTransactionsByBuyer(loggedUser);
    model.addAttribute("transactions", buyerTransactions);
    return "my-purchases"; // Vista para mostrar las compras del usuario
}





@PostMapping("/confirm-transaction/{id}")
public String confirmTransaction(@PathVariable Long id, HttpSession session) {
    User loggedUser = (User) session.getAttribute("loggedUser");
    if (loggedUser == null) {
        return "redirect:/";
    }

    transactionService.confirmTransaction(id);
    return "redirect:/transactions";
}

@PostMapping("/reject-transaction/{id}")
public String rejectTransaction(@PathVariable Long id, HttpSession session) {
    User loggedUser = (User) session.getAttribute("loggedUser");
    if (loggedUser == null) {
        return "redirect:/";
    }

    transactionService.rejectTransaction(id);
    return "redirect:/transactions";
}

}
