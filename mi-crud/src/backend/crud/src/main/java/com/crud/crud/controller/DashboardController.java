package com.crud.crud.controller;

import com.crud.crud.entity.Car;
import com.crud.crud.entity.Client;
import com.crud.crud.entity.User;
import com.crud.crud.repository.CarRepository;
import com.crud.crud.repository.ClientRepository;
import com.crud.crud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ClientRepository clientRepository;
    
    @Autowired
    private CarRepository carRepository;
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserDashboard(@PathVariable Integer userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        
        if (!userOpt.isPresent()) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Usuario no encontrado");
            return ResponseEntity.status(404).body(error);
        }
        
        User user = userOpt.get();
        
        // Buscar cliente asociado por email (asumiendo que el email del usuario coincide con el del cliente)
        Optional<Client> clientOpt = clientRepository.findAll().stream()
            .filter(c -> c.getEmail() != null && c.getEmail().equals(user.getEmail()))
            .findFirst();
        
        Map<String, Object> dashboard = new HashMap<>();
        
        // Información del perfil
        Map<String, Object> profile = new HashMap<>();
        profile.put("id", user.getIdUser());
        profile.put("name", user.getNameUser());
        profile.put("lastName", user.getLastName());
        profile.put("email", user.getEmail());
        profile.put("rol", user.getRol());
        dashboard.put("profile", profile);
        
        // Carros del usuario
        List<Car> cars = List.of(); // Inicializar lista vacía
        
        // Información del cliente (si existe)
        if (clientOpt.isPresent()) {
            Client client = clientOpt.get();
            cars = carRepository.findByIdCliente(client.getIdClient());
            
            Map<String, Object> clientInfoMap = new HashMap<>();
            clientInfoMap.put("id", client.getIdClient());
            clientInfoMap.put("name", client.getNameClient());
            clientInfoMap.put("lastName", client.getLastName());
            clientInfoMap.put("telephone", client.getTelephone());
            clientInfoMap.put("address", client.getAddress());
            dashboard.put("clientInfo", clientInfoMap);
        }
        
        dashboard.put("cars", cars);
        dashboard.put("totalCars", cars.size());
        
        return ResponseEntity.ok(dashboard);
    }
}

