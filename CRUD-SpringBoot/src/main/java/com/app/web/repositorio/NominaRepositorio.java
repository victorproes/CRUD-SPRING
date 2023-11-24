package com.app.web.repositorio;

import com.app.web.entidad.Nomina;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface NominaRepositorio extends JpaRepository<Nomina, Long> {
    Optional<Nomina> findByEmpleado_Dni(String dni);
    @Transactional
    @Modifying
    @Query("DELETE FROM Nomina n WHERE n.empleado.dni = :dni")
    void deleteByEmpleadoDni(@Param("dni") String dni);

}
