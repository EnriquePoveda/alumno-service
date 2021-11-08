package com.test.enrique.alumno.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "alumno")
public class Alumno {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "cedula")
    private String alumnoCedula;
    @Column(name = "nombre")
    private String alumnoNombre;
    @Column(name = "apellido")
    private String alumnoApellido;
    @Column(name = "grado")
    private String alumnoGrado;
    @Column(name = "fecha_nacimiento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date alumnoFecNaci;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date alumnoFecReg;
}
