package com.crud.crud.controller;

import com.crud.crud.entity.User;
import com.crud.crud.entity.Client;
import com.crud.crud.repository.UserRepository;
import com.crud.crud.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ClientRepository clientRepository;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");
        
        if (email == null || password == null) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Email y contraseña son requeridos");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
        
        Optional<User> userOpt = userRepository.findByEmailAndPasswordUser(email, password);
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // No enviar la contraseña en la respuesta
            Map<String, Object> response = new HashMap<>();
            response.put("id", user.getIdUser());
            response.put("name", user.getNameUser());
            response.put("lastName", user.getLastName());
            response.put("email", user.getEmail());
            response.put("rol", user.getRol());
            response.put("success", true);
            return ResponseEntity.ok(response);
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Credenciales inválidas");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserProfile(@PathVariable Integer id) {
        Optional<User> userOpt = userRepository.findById(id);
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            Map<String, Object> response = new HashMap<>();
            response.put("id", user.getIdUser());
            response.put("name", user.getNameUser());
            response.put("lastName", user.getLastName());
            response.put("email", user.getEmail());
            response.put("rol", user.getRol());
            return ResponseEntity.ok(response);
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Usuario no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> userData) {
        try {
            String name = userData.get("name");
            String lastName = userData.get("lastName");
            String email = userData.get("email");
            String password = userData.get("password");
            String phone = userData.get("phone");
            String address = userData.get("address");
            
            System.out.println("Intento de registro - Email: " + email);
            System.out.println("Nombre: " + name + ", Apellido: " + lastName);
            
            // Limpiar espacios en blanco
            if (name != null) name = name.trim();
            if (lastName != null) lastName = lastName.trim();
            if (email != null) email = email.trim();
            if (password != null) password = password.trim();
            if (phone != null) phone = phone.trim();
            if (address != null) address = address.trim();
            
            // Validar campos requeridos
            if (name == null || name.isEmpty()) {
                Map<String, String> error = new HashMap<>();
                error.put("message", "El nombre es requerido");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
            
            if (lastName == null || lastName.isEmpty()) {
                // Si no hay apellido, usar el nombre como apellido
                lastName = name;
            }
            
            if (email == null || email.isEmpty()) {
                Map<String, String> error = new HashMap<>();
                error.put("message", "El email es requerido");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
            
            if (password == null || password.isEmpty()) {
                Map<String, String> error = new HashMap<>();
                error.put("message", "La contraseña es requerida");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
            
            // Verificar si el email ya existe
            Optional<User> existingUser = userRepository.findByEmail(email);
            if (existingUser.isPresent()) {
                System.out.println("Email ya existe: " + email);
                Map<String, String> error = new HashMap<>();
                error.put("message", "El email ya está registrado");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
            }
            
            // Crear nuevo usuario con rol "cliente"
            User newUser = new User();
            newUser.setNameUser(name);
            newUser.setLastName(lastName);
            newUser.setEmail(email);
            newUser.setPasswordUser(password);
            newUser.setRol("cliente");
            
            User savedUser = userRepository.save(newUser);
            System.out.println("Usuario creado con ID: " + savedUser.getIdUser());
            
            // Crear registro en la tabla clients
            Client newClient = new Client();
            newClient.setNameClient(name);
            newClient.setLastName(lastName);
            newClient.setEmail(email);
            newClient.setTelephone(phone != null && !phone.isEmpty() ? phone : "");
            newClient.setAddress(address != null && !address.isEmpty() ? address : "");
            
            clientRepository.save(newClient);
            System.out.println("Cliente creado exitosamente");
            
            // Respuesta exitosa
            Map<String, Object> response = new HashMap<>();
            response.put("id", savedUser.getIdUser());
            response.put("name", savedUser.getNameUser());
            response.put("lastName", savedUser.getLastName());
            response.put("email", savedUser.getEmail());
            response.put("rol", savedUser.getRol());
            response.put("message", "Usuario registrado exitosamente");
            response.put("success", true);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (Exception e) {
            System.out.println("Error al registrar usuario: " + e.getMessage());
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("message", "Error interno del servidor: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}

