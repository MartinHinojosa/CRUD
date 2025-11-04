package com.crud.crud.controller;

import com.crud.crud.entity.Repair;
import com.crud.crud.entity.Car;
import com.crud.crud.repository.RepairRepository;
import com.crud.crud.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/repairs")
@CrossOrigin(origins = "*")
public class RepairController {
    
    @Autowired
    private RepairRepository repairRepository;
    
    @Autowired
    private CarRepository carRepository;
    
    // Obtener todas las reparaciones de un cliente
    @GetMapping("/client/{clientId}")
    public ResponseEntity<?> getRepairsByClient(@PathVariable Integer clientId) {
        List<Repair> repairs = repairRepository.findByClientId(clientId);
        return ResponseEntity.ok(repairs);
    }
    
    // Obtener todas las reparaciones de un carro
    @GetMapping("/car/{carId}")
    public ResponseEntity<?> getRepairsByCar(@PathVariable Integer carId) {
        List<Repair> repairs = repairRepository.findByIdCar(carId);
        return ResponseEntity.ok(repairs);
    }
    
    // Obtener todas las reparaciones (para admin/recepcionista)
    @GetMapping("/all")
    public ResponseEntity<?> getAllRepairs() {
        List<Repair> repairs = repairRepository.findAll();
        return ResponseEntity.ok(repairs);
    }
    
    // Obtener una reparación por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getRepairById(@PathVariable Integer id) {
        Optional<Repair> repairOpt = repairRepository.findById(id);
        if (repairOpt.isPresent()) {
            return ResponseEntity.ok(repairOpt.get());
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Reparación no encontrada");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
    // Crear nueva reparación/cita
    @PostMapping("/create")
    public ResponseEntity<?> createRepair(@RequestBody Map<String, Object> repairData) {
        try {
            Repair repair = new Repair();
            
            // Descripción
            if (repairData.get("description") != null) {
                repair.setDescription(repairData.get("description").toString());
            }
            
            // Estado (por defecto "Pendiente")
            String status = repairData.get("status") != null ? 
                repairData.get("status").toString() : "Pendiente";
            repair.setStatus(status);
            
            // Fecha de entrada
            if (repairData.get("entryDate") != null) {
                String entryDateStr = repairData.get("entryDate").toString();
                repair.setEntryDate(LocalDateTime.parse(entryDateStr, 
                    DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            } else {
                repair.setEntryDate(LocalDateTime.now());
            }
            
            // Fecha estimada
            if (repairData.get("estimatedDate") != null) {
                String estimatedDateStr = repairData.get("estimatedDate").toString();
                repair.setEstimatedDate(LocalDateTime.parse(estimatedDateStr, 
                    DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            }
            
            // ID del carro
            Integer carId = repairData.get("carId") != null ? 
                Integer.parseInt(repairData.get("carId").toString()) : null;
            
            if (carId == null) {
                Map<String, String> error = new HashMap<>();
                error.put("message", "ID del carro es requerido");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
            
            // Verificar que el carro existe
            Optional<Car> carOpt = carRepository.findById(carId);
            if (!carOpt.isPresent()) {
                Map<String, String> error = new HashMap<>();
                error.put("message", "El carro especificado no existe");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
            
            repair.setIdCar(carId);
            
            // Usuario que crea la reparación
            Integer createdBy = repairData.get("createdBy") != null ? 
                Integer.parseInt(repairData.get("createdBy").toString()) : null;
            repair.setCreatedBy(createdBy);
            
            // Costo (opcional)
            if (repairData.get("cost") != null) {
                repair.setCost(Double.parseDouble(repairData.get("cost").toString()));
            }
            
            Repair savedRepair = repairRepository.save(repair);
            
            Map<String, Object> response = new HashMap<>();
            response.put("id", savedRepair.getIdRepair());
            response.put("message", "Reparación creada exitosamente");
            response.put("success", true);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Error al crear la reparación: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    // Actualizar estado de una reparación
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateRepairStatus(@PathVariable Integer id, 
                                                @RequestBody Map<String, String> statusData) {
        Optional<Repair> repairOpt = repairRepository.findById(id);
        
        if (!repairOpt.isPresent()) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Reparación no encontrada");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
        
        Repair repair = repairOpt.get();
        String newStatus = statusData.get("status");
        
        if (newStatus != null) {
            repair.setStatus(newStatus);
            
            // Si el estado es "Completada", establecer fecha de finalización
            if ("Completada".equals(newStatus)) {
                repair.setCompletionDate(LocalDateTime.now());
            }
            
            repairRepository.save(repair);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Estado actualizado exitosamente");
            return ResponseEntity.ok(response);
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Estado es requerido");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
}

