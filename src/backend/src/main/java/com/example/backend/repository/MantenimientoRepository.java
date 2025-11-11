package com.example.backend.repository;

import com.example.backend.model.Mantenimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MantenimientoRepository extends JpaRepository<Mantenimiento, Long> {
    List<Mantenimiento> findByVehiculoIdVehiculo(Long idVehiculo);
    List<Mantenimiento> findByUsuarioIdUsuario(Long idUsuario);
}

