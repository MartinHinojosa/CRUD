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
            
            System.out.println("Intento de registro de cliente - Email: " + email);
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
                lastName = name;
            }
            
            if (email == null || email.isEmpty()) {
                Map<String, String> error = new HashMap<>();
                error.put("message", "El email es requerido");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
            
            // Verificar si el email ya existe en clients
            Optional<Client> existingClient = clientRepository.findByEmail(email);
            if (existingClient.isPresent()) {
                System.out.println("Email ya existe en clients: " + email);
                Map<String, String> error = new HashMap<>();
                error.put("message", "El email ya está registrado");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
            }
            
            // Solo crear registro en la tabla clients (NO en users)
            Client newClient = new Client();
            newClient.setNameClient(name);
            newClient.setLastName(lastName);
            newClient.setEmail(email);
            newClient.setTelephone(phone != null && !phone.isEmpty() ? phone : "");
            newClient.setAddress(address != null && !address.isEmpty() ? address : "");
            
            Client savedClient = clientRepository.save(newClient);
            System.out.println("Cliente creado exitosamente con ID: " + savedClient.getIdClient());
            
            // Respuesta exitosa
            Map<String, Object> response = new HashMap<>();
            response.put("id", savedClient.getIdClient());
            response.put("name", savedClient.getNameClient());
            response.put("lastName", savedClient.getLastName());
            response.put("email", savedClient.getEmail());
            response.put("message", "Cliente registrado exitosamente");
            response.put("success", true);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (Exception e) {
            System.out.println("Error al registrar cliente: " + e.getMessage());
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("message", "Error interno del servidor: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    @PostMapping("/register-user")
    public ResponseEntity<?> registerUser(@RequestBody Map<String, String> userData) {
        try {
            String name = userData.get("name");
            String lastName = userData.get("lastName");
            String email = userData.get("email");
            String password = userData.get("password");
            String rol = userData.get("rol");
            
            System.out.println("Intento de registro de usuario - Email: " + email + ", Rol: " + rol);
            
            // Limpiar espacios en blanco
            if (name != null) name = name.trim();
            if (lastName != null) lastName = lastName.trim();
            if (email != null) email = email.trim();
            if (password != null) password = password.trim();
            if (rol != null) rol = rol.trim();
            
            // Validar campos requeridos
            if (name == null || name.isEmpty()) {
                Map<String, String> error = new HashMap<>();
                error.put("message", "El nombre es requerido");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
            
            if (lastName == null || lastName.isEmpty()) {
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
            
            // Validar rol
            if (rol == null || rol.isEmpty()) {
                Map<String, String> error = new HashMap<>();
                error.put("message", "El rol es requerido");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
            
            // Validar que el rol sea uno de los permitidos
            String rolLower = rol.toLowerCase();
            if (!rolLower.equals("administrador") && !rolLower.equals("admin") && 
                !rolLower.equals("recepcionista") && !rolLower.equals("recepcion") &&
                !rolLower.equals("mecanico") && !rolLower.equals("mecánico")) {
                Map<String, String> error = new HashMap<>();
                error.put("message", "El rol debe ser: administrador, recepcionista o mecanico");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
            
            // Normalizar el rol
            if (rolLower.equals("admin")) {
                rol = "administrador";
            } else if (rolLower.equals("recepcion")) {
                rol = "recepcionista";
            } else if (rolLower.equals("mecánico")) {
                rol = "mecanico";
            }
            
            // Verificar si el email ya existe en users
            Optional<User> existingUser = userRepository.findByEmail(email);
            if (existingUser.isPresent()) {
                System.out.println("Email ya existe en users: " + email);
                Map<String, String> error = new HashMap<>();
                error.put("message", "El email ya está registrado");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
            }
            
            // Crear nuevo usuario en la tabla users
            User newUser = new User();
            newUser.setNameUser(name);
            newUser.setLastName(lastName);
            newUser.setEmail(email);
            newUser.setPasswordUser(password);
            newUser.setRol(rol);
            
            User savedUser = userRepository.save(newUser);
            System.out.println("Usuario creado exitosamente con ID: " + savedUser.getIdUser());
            
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

