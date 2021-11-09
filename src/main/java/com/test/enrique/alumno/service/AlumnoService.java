package com.test.enrique.alumno.service;

import com.test.enrique.alumno.entity.Alumno;
import com.test.enrique.alumno.repository.AlumnoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlumnoService {

    @Autowired
    private AlumnoRepositorio alumnoRepositorio;

    public Alumno saveAlumno(Alumno alumno) {
        return alumnoRepositorio.save(alumno);
    }

    public Alumno editAlumno(Alumno alumno) {
        Alumno temp = alumnoRepositorio.findAlumnoById(alumno.getId());
        if (temp != null && temp.getId() != null) {
            temp.setAlumnoNombre(alumno.getAlumnoNombre());
            temp.setAlumnoApellido(alumno.getAlumnoApellido());
            temp.setAlumnoGrado(alumno.getAlumnoGrado());
            temp.setAlumnoFecNaci(alumno.getAlumnoFecNaci());
            return saveAlumno(alumno);
        }
        return null;
    }

    public Alumno findAlumnoById(Long alumnoId) {
        return alumnoRepositorio.findAlumnoById(alumnoId);
    }

    public List<Alumno> findAll() {
        return alumnoRepositorio.findAll();
    }

    public void delete(Alumno alumno) {
        alumnoRepositorio.delete(alumno);
    }
}
