package com.example.backend.service;

import com.example.backend.dto.MantenimientoDTO;
import com.example.backend.model.Mantenimiento;
import com.example.backend.model.Usuario;
import com.example.backend.model.Vehiculo;
import com.example.backend.repository.MantenimientoRepository;
import com.example.backend.repository.UsuarioRepository;
import com.example.backend.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MantenimientoService {
    
    @Autowired
    private MantenimientoRepository mantenimientoRepository;
    
    @Autowired
    private VehiculoRepository vehiculoRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public List<MantenimientoDTO> findAll() {
        return mantenimientoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public Optional<MantenimientoDTO> findById(Long id) {
        return mantenimientoRepository.findById(id)
                .map(this::convertToDTO);
    }
    
    public List<MantenimientoDTO> findByVehiculo(Long idVehiculo) {
        return mantenimientoRepository.findByVehiculoIdVehiculo(idVehiculo).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<MantenimientoDTO> findByUsuario(Long idUsuario) {
        return mantenimientoRepository.findByUsuarioIdUsuario(idUsuario).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public MantenimientoDTO save(MantenimientoDTO dto) {
        Vehiculo vehiculo = vehiculoRepository.findById(dto.getIdVehiculo())
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));
        
        Mantenimiento mantenimiento = new Mantenimiento();
        mantenimiento.setVehiculo(vehiculo);
        mantenimiento.setTipoServicio(dto.getTipoServicio());
        mantenimiento.setFechaServicio(dto.getFechaServicio());
        mantenimiento.setCosto(dto.getCosto());
        mantenimiento.setNotas(dto.getNotas());
        
        if (dto.getIdUsuario() != null) {
            Optional<Usuario> usuarioOpt = usuarioRepository.findById(dto.getIdUsuario());
            usuarioOpt.ifPresent(mantenimiento::setUsuario);
        }
        
        Mantenimiento saved = mantenimientoRepository.save(mantenimiento);
        return convertToDTO(saved);
    }
    
    @Transactional
    public MantenimientoDTO update(Long id, MantenimientoDTO dto) {
        Mantenimiento mantenimiento = mantenimientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mantenimiento no encontrado"));
        
        Vehiculo vehiculo = vehiculoRepository.findById(dto.getIdVehiculo())
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));
        
        mantenimiento.setVehiculo(vehiculo);
        mantenimiento.setTipoServicio(dto.getTipoServicio());
        mantenimiento.setFechaServicio(dto.getFechaServicio());
        mantenimiento.setCosto(dto.getCosto());
        mantenimiento.setNotas(dto.getNotas());
        
        if (dto.getIdUsuario() != null) {
            Optional<Usuario> usuarioOpt = usuarioRepository.findById(dto.getIdUsuario());
            usuarioOpt.ifPresent(mantenimiento::setUsuario);
        } else {
            mantenimiento.setUsuario(null);
        }
        
        Mantenimiento updated = mantenimientoRepository.save(mantenimiento);
        return convertToDTO(updated);
    }
    
    @Transactional
    public void delete(Long id) {
        if (!mantenimientoRepository.existsById(id)) {
            throw new RuntimeException("Mantenimiento no encontrado");
        }
        mantenimientoRepository.deleteById(id);
    }
    
    private MantenimientoDTO convertToDTO(Mantenimiento mantenimiento) {
        return new MantenimientoDTO(
                mantenimiento.getIdMantenimiento(),
                mantenimiento.getVehiculo().getIdVehiculo(),
                mantenimiento.getVehiculo().getPlaca(),
                mantenimiento.getTipoServicio(),
                mantenimiento.getFechaServicio(),
                mantenimiento.getCosto(),
                mantenimiento.getNotas(),
                mantenimiento.getUsuario() != null ? mantenimiento.getUsuario().getIdUsuario() : null,
                mantenimiento.getUsuario() != null ? mantenimiento.getUsuario().getNombre() : null
        );
    }
}

