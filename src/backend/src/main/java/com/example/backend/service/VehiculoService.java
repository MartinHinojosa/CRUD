package com.example.backend.service;

import com.example.backend.dto.VehiculoDTO;
import com.example.backend.model.Usuario;
import com.example.backend.model.Vehiculo;
import com.example.backend.repository.UsuarioRepository;
import com.example.backend.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehiculoService {
    
    @Autowired
    private VehiculoRepository vehiculoRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public List<VehiculoDTO> findAll() {
        return vehiculoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public Optional<VehiculoDTO> findById(Long id) {
        return vehiculoRepository.findById(id)
                .map(this::convertToDTO);
    }
    
    public List<VehiculoDTO> findByUsuario(Long idUsuario) {
        return vehiculoRepository.findByUsuarioIdUsuario(idUsuario).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public VehiculoDTO save(VehiculoDTO dto) {
        if (vehiculoRepository.existsByPlaca(dto.getPlaca())) {
            throw new RuntimeException("La placa ya existe");
        }
        
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setPlaca(dto.getPlaca());
        vehiculo.setMarca(dto.getMarca());
        vehiculo.setModelo(dto.getModelo());
        vehiculo.setAño(dto.getAño());
        
        if (dto.getIdUsuario() != null) {
            Optional<Usuario> usuarioOpt = usuarioRepository.findById(dto.getIdUsuario());
            usuarioOpt.ifPresent(vehiculo::setUsuario);
        }
        
        Vehiculo saved = vehiculoRepository.save(vehiculo);
        return convertToDTO(saved);
    }
    
    @Transactional
    public VehiculoDTO update(Long id, VehiculoDTO dto) {
        Vehiculo vehiculo = vehiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));
        
        // Verificar si la placa cambió y si ya existe
        if (!vehiculo.getPlaca().equals(dto.getPlaca()) && 
            vehiculoRepository.existsByPlaca(dto.getPlaca())) {
            throw new RuntimeException("La placa ya existe");
        }
        
        vehiculo.setPlaca(dto.getPlaca());
        vehiculo.setMarca(dto.getMarca());
        vehiculo.setModelo(dto.getModelo());
        vehiculo.setAño(dto.getAño());
        
        if (dto.getIdUsuario() != null) {
            Optional<Usuario> usuarioOpt = usuarioRepository.findById(dto.getIdUsuario());
            usuarioOpt.ifPresent(vehiculo::setUsuario);
        } else {
            vehiculo.setUsuario(null);
        }
        
        Vehiculo updated = vehiculoRepository.save(vehiculo);
        return convertToDTO(updated);
    }
    
    @Transactional
    public void delete(Long id) {
        if (!vehiculoRepository.existsById(id)) {
            throw new RuntimeException("Vehículo no encontrado");
        }
        vehiculoRepository.deleteById(id);
    }
    
    private VehiculoDTO convertToDTO(Vehiculo vehiculo) {
        return new VehiculoDTO(
                vehiculo.getIdVehiculo(),
                vehiculo.getPlaca(),
                vehiculo.getMarca(),
                vehiculo.getModelo(),
                vehiculo.getAño(),
                vehiculo.getUsuario() != null ? vehiculo.getUsuario().getIdUsuario() : null,
                vehiculo.getUsuario() != null ? vehiculo.getUsuario().getNombre() : null
        );
    }
}

