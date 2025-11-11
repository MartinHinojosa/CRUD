package com.example.backend.controller;

import com.example.backend.dto.VehiculoDTO;
import com.example.backend.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
@CrossOrigin(origins = "http://localhost:4200")
public class VehiculoController {
    
    @Autowired
    private VehiculoService vehiculoService;
    
    @GetMapping
    public ResponseEntity<List<VehiculoDTO>> getAll() {
        return ResponseEntity.ok(vehiculoService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<VehiculoDTO> getById(@PathVariable Long id) {
        return vehiculoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<VehiculoDTO>> getByUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(vehiculoService.findByUsuario(idUsuario));
    }
    
    @PostMapping
    public ResponseEntity<?> create(@RequestBody VehiculoDTO dto) {
        try {
            VehiculoDTO created = vehiculoService.save(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody VehiculoDTO dto) {
        try {
            VehiculoDTO updated = vehiculoService.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            vehiculoService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}

