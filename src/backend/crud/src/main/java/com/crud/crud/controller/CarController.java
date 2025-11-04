package com.crud.crud.controller;

import com.crud.crud.entity.Car;
import com.crud.crud.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cars")
@CrossOrigin(origins = "*")
public class CarController {
    
    @Autowired
    private CarRepository carRepository;
    
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Car>> getCarsByClientId(@PathVariable Integer clientId) {
        List<Car> cars = carRepository.findByIdCliente(clientId);
        return ResponseEntity.ok(cars);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Integer id) {
        return carRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<Car>> getAllCars() {
        List<Car> cars = carRepository.findAll();
        return ResponseEntity.ok(cars);
    }
    
    @PostMapping("/create")
    public ResponseEntity<?> createCar(@RequestBody Map<String, Object> carData) {
        try {
            Car newCar = new Car();
            
            String brand = carData.get("brand") != null ? carData.get("brand").toString() : null;
            String model = carData.get("model") != null ? carData.get("model").toString() : null;
            Integer yearCar = carData.get("yearCar") != null ? 
                Integer.parseInt(carData.get("yearCar").toString()) : null;
            String plate = carData.get("plate") != null ? carData.get("plate").toString() : null;
            Integer idCliente = carData.get("idCliente") != null ? 
                Integer.parseInt(carData.get("idCliente").toString()) : null;
            
            // Validar campos requeridos
            if (brand == null || brand.trim().isEmpty()) {
                Map<String, String> error = new HashMap<>();
                error.put("message", "La marca es requerida");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
            
            if (model == null || model.trim().isEmpty()) {
                Map<String, String> error = new HashMap<>();
                error.put("message", "El modelo es requerido");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
            
            if (idCliente == null) {
                Map<String, String> error = new HashMap<>();
                error.put("message", "El ID del cliente es requerido");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
            
            newCar.setBrand(brand.trim());
            newCar.setModel(model.trim());
            newCar.setYearCar(yearCar);
            newCar.setPlate(plate != null ? plate.trim() : null);
            newCar.setIdCliente(idCliente);
            
            Car savedCar = carRepository.save(newCar);
            
            Map<String, Object> response = new HashMap<>();
            response.put("id", savedCar.getIdCar());
            response.put("message", "Carro creado exitosamente");
            response.put("success", true);
            response.put("car", savedCar);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (Exception e) {
            System.out.println("Error al crear carro: " + e.getMessage());
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("message", "Error al crear el carro: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}

