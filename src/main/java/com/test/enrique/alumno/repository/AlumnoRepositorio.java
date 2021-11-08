package com.test.enrique.alumno.repository;

import com.test.enrique.alumno.entity.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlumnoRepositorio extends JpaRepository<Alumno, Long> {

    Alumno findAlumnoById(Long alumnoId);
}
