package com.app.web.servicio;

import java.util.List;

import com.app.web.entidad.DatosNoCorrectoExcpetion;
import com.app.web.entidad.Empleado;

public interface EmpleadoServicio {
	
	public List<Empleado>listar();
	public Empleado guardarEmpleado(Empleado empleado);
	public Empleado obtenerEmpleadoPorDni(String dni) throws DatosNoCorrectoExcpetion;
    public Double obtenerSalarioEmpleado(String dni) throws DatosNoCorrectoExcpetion;
    void modificarEmpleado(Empleado empleado) throws DatosNoCorrectoExcpetion;
    void eliminarEmpleado(String dni) throws DatosNoCorrectoExcpetion;
    List<Empleado> buscarEmpleadosPorCriterio(String criterio, String valor) throws DatosNoCorrectoExcpetion;

}
