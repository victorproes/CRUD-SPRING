package com.app.web.controlador;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.web.entidad.DatosNoCorrectoExcpetion;
import com.app.web.entidad.Empleado;
import com.app.web.entidad.User;
import com.app.web.repositorio.UserRepository;
import com.app.web.servicio.EmpleadoServicio;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class EmpleadoControlador {

	 @GetMapping("/")
	    public String index() {
	        return "index";
	    }
	 
//	 @GetMapping("/logout")
//	    public String logout(HttpServletRequest request) {
//	        HttpSession session = request.getSession(false);
//	        if (session != null) {
//	            session.invalidate();
//	        }
//	        return "paginaPrincipal";
//	    }
	@Autowired
	private EmpleadoServicio servicio;
	
	 @Autowired
	 private UserRepository userRepository;
	 
//	 @PostMapping("/login")
//	    public String login(@RequestParam String username, @RequestParam String password, Model model) {
//	        User user = userRepository.findByUsername(username);
//	        if (user != null && user.getPassword().equals(password)) {
//	            // Lógica de inicio de sesión exitosa
//	            return "index";
//	        } else {
//	            model.addAttribute("message", "Credenciales incorrectas. Por favor, inténtelo de nuevo.");
//	            return "login";
//	        }
//	    }
	 
//	 @PostMapping("/registro")
//	    public String registro(@RequestParam String username, @RequestParam String password, Model model) {
//	        User existingUser = userRepository.findByUsername(username);
//	        if (existingUser != null) {
//	            model.addAttribute("message", "El usuario ya existe. Por favor, elija otro nombre de usuario.");
//	            return "registro";
//	        } else {
//	            User newUser = new User();
//	            newUser.setUsername(username);
//	            newUser.setPassword(password);
//	            userRepository.save(newUser);
//	            model.addAttribute("message", "Registro exitoso. Ahora puede iniciar sesión.");
//	            return "login";
//	        }
//	    }
	 
//	  @GetMapping("/registro")
//	    public String showRegistrationForm() {
//	        return "registro";
//	    }
//	  
//	  @GetMapping("/login")
//	    public String showLoginForm() {
//	        return "login";
//	    }
	
	@GetMapping({"/empleados"})
	public String listarEmpleados(Model modelo) {
		modelo.addAttribute("empleados", servicio.listar());
		return "empleados";
	}
	
	@GetMapping("/empleados/nuevo")
	public String crearEmpleadoFormulario(Model modelo) {
		Empleado empleado= new Empleado();
		modelo.addAttribute("empleado", empleado);
		return "crear_empleado";
	}
	
	@PostMapping("/empleados")
	public String guardarEmpleado(@ModelAttribute("empleado")Empleado empleado) {
		servicio.guardarEmpleado(empleado);
		return "redirect:/empleados";
		
	}
	
	 @GetMapping("/buscar_salario")
	    public String buscarSalarioForm() {
	        return "buscar_salario";
	    }
	 
	
	
	
	  @PostMapping("/mostrar_salario")
	    public String mostrarSalario(@RequestParam String dni, Model modelo) {
	        try {
	            Double salario = servicio.obtenerSalarioEmpleado(dni);
	            modelo.addAttribute("dni", dni);
	            modelo.addAttribute("salario", salario);
	            return "mostrar_salario";
	        } catch (DatosNoCorrectoExcpetion e) {
	            // Manejar la excepción y agregar un mensaje de error al modelo
	            modelo.addAttribute("error", "Error al obtener el salario del empleado desde la base de datos");
	            return "mostrar_salario";
	        }
	    }
	  
	  @GetMapping("/editar/{dni}")
	  public String editarEmpleadoForm(@PathVariable String dni, Model modelo) {
	      try {
	          Empleado empleado = servicio.obtenerEmpleadoPorDni(dni);
	          modelo.addAttribute("empleado", empleado);
	          return "editar_empleado";
	      } catch (DatosNoCorrectoExcpetion e) {
	          modelo.addAttribute("error", "Error al obtener los datos del empleado para editar");
	          return "error";
	      }
	  }
	  
	  @PostMapping("/empleados/guardar-edicion")
	  public String guardarEdicion(@ModelAttribute("empleado") Empleado empleado, Model modelo) {
	      try {
	          servicio.modificarEmpleado(empleado);
	          return "redirect:/empleados";
	      } catch (DatosNoCorrectoExcpetion e) {
	          modelo.addAttribute("error", "Error al guardar los cambios del empleado");
	          return "error";
	      }
	  }
	  
	  @PostMapping("/empleados/editar")
	    public String editarEmpleado(@ModelAttribute("empleado") Empleado empleado, Model modelo) {
	        try {
	            servicio.modificarEmpleado(empleado);
	            return "redirect:/empleados";  // Redirige a la lista de empleados después de la modificación
	        } catch (DatosNoCorrectoExcpetion e) {
	            modelo.addAttribute("error", "Error al modificar el empleado y su salario");
	            // Puedes redirigir a una página de error o mostrar un mensaje en la misma página de edición
	            return "error";
	        }
	    }
	  
	  @GetMapping("/eliminar/{dni}")
	    public String eliminarEmpleado(@PathVariable String dni, Model modelo) {
	        try {
	            servicio.eliminarEmpleado(dni);
	            return "redirect:/empleados";
	        } catch (DatosNoCorrectoExcpetion e) {
	            modelo.addAttribute("error", "Error al eliminar el empleado");
	            return "error";
	        }
	    }
	  
	    @ModelAttribute("criterios")
	    public List<String> getCriteriosBusqueda() {
	        // Devuelve la lista de criterios de búsqueda disponibles
	        return Arrays.asList("dni", "nombre", "categoria", "anyos", "sexo");
	    }
	  
	  @GetMapping("/buscar")
	  public String mostrarFormularioBusqueda(Model modelo) {
	      modelo.addAttribute("criterios", getCriteriosBusqueda());
	      return "buscar_empleado";
	  }
	  
	  @PostMapping("empleados/buscar")
	  public String buscarEmpleados(@RequestParam String criterio, @RequestParam String valor, Model modelo) {
	      try {
	          List<Empleado> empleados = servicio.buscarEmpleadosPorCriterio(criterio, valor);
	          modelo.addAttribute("empleados", empleados);
	          return "resultados_busqueda";
	      } catch (DatosNoCorrectoExcpetion e) {
	          modelo.addAttribute("error", "Error al realizar la búsqueda: " + e.getMessage());
	          return "buscar_empleado";
	      }
	  }
	  
	  
	  
	  
	  
	
}
