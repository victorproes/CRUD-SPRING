package com.app.web.entidad;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;


@Entity
@Table(name="empleados")
public class Empleado {
	
	@Id
	private String dni;
	
    @Pattern(regexp = "^[a-zA-Z]+$", message = "El nombre solo puede contener letras")
	@Column(name="nombre",nullable=false,length=50)
	private String nombre;
	
    @Pattern(regexp = "[MF]", message = "El sexo debe ser 'M' o 'F'")
	@Column(name="sexo",nullable=false,length=50)
	private String sexo;
	
    @Min(value = 1, message = "La categoría debe ser igual o mayor que 1")
    @Max(value = 10, message = "La categoría debe ser igual o menor que 10")
	@Column(name="categoria",nullable=false,length=50)
	private int categoria;
	
    @Min(value = 1, message = "Los años deben ser igual o mayores que 1")
    @Max(value = 10, message = "Los años deben ser igual o menores que 10")
	@Column(name="anyos",nullable=false,length=50)
	private int anyos;
	
	@OneToOne(mappedBy = "empleado", cascade = CascadeType.ALL)
	private Nomina nomina;


	public Empleado() {
		
	}
	public Empleado(String dni, String nombre,  String sexo, int categoria, int anyos) {
		super();
		this.dni = dni;
		this.nombre = nombre;
		
		this.sexo = sexo;
		this.categoria = categoria;
		this.anyos = anyos;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public int getCategoria() {
		return categoria;
	}
	public void setCategoria(int categoria) {
		this.categoria = categoria;
	}
	public int getAnyos() {
		return anyos;
	}
	public void setAnyos(int anyos) {
		this.anyos = anyos;
	}
	@Override
	public String toString() {
		return "Empleado [dni=" + dni + ", nombre=" + nombre + ", sexo=" + sexo + ", categoria=" + categoria
				+ ", anyos=" + anyos + "]";
	}

}
