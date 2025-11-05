package com.crud.crud.controller;

import com.crud.crud.entity.Client;
import com.crud.crud.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/clients")
@CrossOrigin(origins = "*")
public class ClientController {
    
    @Autowired
    private ClientRepository clientRepository;
    
    @GetMapping("/all")
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        return ResponseEntity.ok(clients);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Integer id) {
        return clientRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> registerClient(@RequestBody Map<String, String> clientData) {
        try {
            String name = clientData.get("name");
            String lastName = clientData.get("lastName");
            String email = clientData.get("email");
            String phone = clientData.get("phone");
            String address = clientData.get("address");
            
            // Limpiar espacios en blanco
            if (name != null) name = name.trim();
            if (lastName != null) lastName = lastName.trim();
            if (email != null) email = email.trim();
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
            
            // Verificar si el email ya existe
            Optional<Client> existingClient = clientRepository.findByEmail(email);
            if (existingClient.isPresent()) {
                Map<String, String> error = new HashMap<>();
                error.put("message", "El email ya está registrado");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
            }
            
            // Crear nuevo cliente
            Client newClient = new Client();
            newClient.setNameClient(name);
            newClient.setLastName(lastName);
            newClient.setEmail(email);
            newClient.setTelephone(phone != null && !phone.isEmpty() ? phone : "");
            newClient.setAddress(address != null && !address.isEmpty() ? address : "");
            
            Client savedClient = clientRepository.save(newClient);
            
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
            Map<String, String> error = new HashMap<>();
            error.put("message", "Error interno del servidor: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}

