	package com.app.web.servicio;
	
	import java.util.List;
	
	import java.util.Optional;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Service;
	
	import com.app.web.entidad.DatosNoCorrectoExcpetion;
	import com.app.web.entidad.Empleado;
	import com.app.web.entidad.Nomina;
	import com.app.web.repositorio.EmpleadoRepositorio;
	import com.app.web.repositorio.NominaRepositorio;
	
	@Service
	public class EmpleadoServicioImpl implements EmpleadoServicio {
	
		@Autowired
		private EmpleadoRepositorio empleadorepositorio;
		@Autowired
	    private NominaRepositorio nominaRepositorio;
		@Override
		public List<Empleado> listar() {
			return empleadorepositorio.findAll();
		}
		@Override
		public Empleado guardarEmpleado(Empleado empleado) {
			Empleado empleadoGuardado = empleadorepositorio.save(empleado);
	
	        // Crear una nueva entrada en la tabla "nomina" usando la lógica existente
	        Nomina nomina = new Nomina(empleadoGuardado);
	        nominaRepositorio.save(nomina);
	
	        return empleadoGuardado;
		}
		@Override
		public Empleado obtenerEmpleadoPorDni(String dni) {
	        return empleadorepositorio.findById(dni).orElse(null);
		}
		@Override
		public Double obtenerSalarioEmpleado(String dni) throws DatosNoCorrectoExcpetion {
		    try {
		        Optional<Nomina> nominaOptional = nominaRepositorio.findByEmpleado_Dni(dni);
	
		        if (nominaOptional.isPresent()) {
		            return nominaOptional.get().getSueldo();
		        } else {
		            // Si no se encuentra una nómina para el empleado, lanzar una excepción
		            throw new DatosNoCorrectoExcpetion("No se encontró salario para el empleado con DNI: " + dni);
		        }
		    } catch (Exception e) {
		        // Cualquier excepción no controlada se convierte en DatosNoCorrectoExcpetion
		        throw new DatosNoCorrectoExcpetion("Error al obtener el salario del empleado desde la base de datos");
		    }
		}
		@Override
		public void modificarEmpleado(Empleado empleado) throws DatosNoCorrectoExcpetion {
			try {
		        // Lógica para modificar el empleado y su salario
		        Optional<Empleado> empleadoOptional = empleadorepositorio.findById(empleado.getDni());
	
		        if (empleadoOptional.isPresent()) {
		            Empleado empleadoExistente = empleadoOptional.get();
	
		            // Actualizar los campos del empleado
		            empleadoExistente.setNombre(empleado.getNombre());
		            empleadoExistente.setSexo(empleado.getSexo());
		            empleadoExistente.setCategoria(empleado.getCategoria());
		            empleadoExistente.setAnyos(empleado.getAnyos());
	
		            // Guardar el empleado actualizado
		            empleadorepositorio.save(empleadoExistente);
	
		            // Actualizar el salario en la tabla "nomina"
		            Optional<Nomina> nominaOptional = nominaRepositorio.findByEmpleado_Dni(empleado.getDni());
		            if (nominaOptional.isPresent()) {
		                Nomina nomina = nominaOptional.get();
		                nomina.setSueldo(Nomina.sueldo(empleado));
		                nominaRepositorio.save(nomina);
		            } else {
		                // Manejar el caso en que no se encuentre la entrada en la tabla "nomina"
		                throw new DatosNoCorrectoExcpetion("No se encontró la entrada en la tabla 'nomina' para el empleado con DNI: " + empleado.getDni());
		            }
		        } else {
		            // Manejar el caso en que no se encuentre el empleado
		            throw new DatosNoCorrectoExcpetion("No se encontró el empleado con DNI: " + empleado.getDni());
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		        throw new DatosNoCorrectoExcpetion("Error al modificar el empleado y su salario en el servicio");
		    }
			
		}
		@Override
	    public void eliminarEmpleado(String dni) throws DatosNoCorrectoExcpetion {
	        try {
	            empleadorepositorio.deleteById(dni);
	            nominaRepositorio.deleteByEmpleadoDni(dni);
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new DatosNoCorrectoExcpetion("Error al eliminar el empleado y su salario en el servicio");
	        }
	    }
		@Override
		public List<Empleado> buscarEmpleadosPorCriterio(String criterio, String valor) throws DatosNoCorrectoExcpetion {
		    try {
		        switch (criterio) {
		            case "dni":
		                return empleadorepositorio.findByDni(valor);
		            case "nombre":
		                return empleadorepositorio.findByNombre(valor);
		            case "categoria":
		                int categoria = Integer.parseInt(valor);
		                return empleadorepositorio.findByCategoria(categoria);
		            case "anyos":
		                int anyos = Integer.parseInt(valor);
		                return empleadorepositorio.findByAnyos(anyos);
		            case "sexo":
		                return empleadorepositorio.findBySexo(valor);
		            default:
		                throw new DatosNoCorrectoExcpetion("Criterio de búsqueda no válido: " + criterio);
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		        throw new DatosNoCorrectoExcpetion("Error al buscar empleados por criterio en el servicio");
		    }
		}
	
		
		
		
	
	}
