package com.app.web.entidad;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "nomina")
public class Nomina {

    private static final int[] SUELDO_BASE = { 50000, 70000, 90000, 110000, 130000, 150000, 170000, 190000, 210000, 230000 };

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_empleado")
    private Empleado empleado;

    @Column(name = "sueldo")
    private double sueldo;

    public Nomina() {
    }

    public Nomina(Empleado empleado) {
        this.empleado = empleado;
        this.sueldo = calcularSueldo();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
        this.sueldo = calcularSueldo();
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(int sueldo) {
        this.sueldo = sueldo;
    }

    // Método para calcular el sueldo
    private int calcularSueldo() {
        return Nomina.sueldo(empleado);
    }

    // Método estático para calcular el sueldo 
    public static int sueldo(Empleado empleado) {
        
        return SUELDO_BASE[empleado.getCategoria() - 1] + 5000 * empleado.getAnyos();
    }
}
