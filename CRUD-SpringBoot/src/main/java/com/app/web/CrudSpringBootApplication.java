package com.app.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.app.web.entidad.Empleado;
import com.app.web.repositorio.EmpleadoRepositorio;

@SpringBootApplication
public class CrudSpringBootApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CrudSpringBootApplication.class, args);
	}
	
	@Autowired
	private EmpleadoRepositorio repositorio;

	@Override
	public void run(String... args) throws Exception {
//		Empleado e1 = new Empleado("47266493K","Victor","M",1,1);
//		repositorio.save(e1);
//		Empleado e2= new Empleado("56456785F","Pepe","M",1,1);
//		repositorio.save(e2);

		
	}

}
