package com.crud.crud.repository;

import com.crud.crud.entity.Repair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RepairRepository extends JpaRepository<Repair, Integer> {
    
    List<Repair> findByIdCar(Integer idCar);
    
    List<Repair> findByCreatedBy(Integer createdBy);
    
    @Query("SELECT r FROM Repair r JOIN r.car c WHERE c.idCliente = :clientId")
    List<Repair> findByClientId(@Param("clientId") Integer clientId);
    
    List<Repair> findByStatus(String status);
}

