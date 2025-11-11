package com.example.backend.controller;

import com.example.backend.dto.MantenimientoDTO;
import com.example.backend.service.MantenimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mantenimientos")
@CrossOrigin(origins = "http://localhost:4200")
public class MantenimientoController {
    
    @Autowired
    private MantenimientoService mantenimientoService;
    
    @GetMapping
    public ResponseEntity<List<MantenimientoDTO>> getAll() {
        return ResponseEntity.ok(mantenimientoService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<MantenimientoDTO> getById(@PathVariable Long id) {
        return mantenimientoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/vehiculo/{idVehiculo}")
    public ResponseEntity<List<MantenimientoDTO>> getByVehiculo(@PathVariable Long idVehiculo) {
        return ResponseEntity.ok(mantenimientoService.findByVehiculo(idVehiculo));
    }
    
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<MantenimientoDTO>> getByUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(mantenimientoService.findByUsuario(idUsuario));
    }
    
    @PostMapping
    public ResponseEntity<?> create(@RequestBody MantenimientoDTO dto) {
        try {
            MantenimientoDTO created = mantenimientoService.save(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody MantenimientoDTO dto) {
        try {
            MantenimientoDTO updated = mantenimientoService.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            mantenimientoService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}

