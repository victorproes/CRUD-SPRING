package com.app.web.repositorio;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.web.entidad.Empleado;

@Repository
public interface EmpleadoRepositorio extends JpaRepository<Empleado,String> {
	
	List<Empleado> findByDni(String dni);
    List<Empleado> findByNombre(String nombre);
    List<Empleado> findByCategoria(int categoria);
    List<Empleado> findByAnyos(int anyos);
    List<Empleado> findBySexo(String sexo);

}
