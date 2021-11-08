package com.test.enrique.alumno.controller;

import com.test.enrique.alumno.entity.Alumno;
import com.test.enrique.alumno.excel.AlumnoExcelExporter;
import com.test.enrique.alumno.service.AlumnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.time.Clock;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/alumnos")
public class AlumnoController {
    @Autowired
    private AlumnoService alumnoService;

    @PostMapping("/")
    public Alumno saveAlumno(@RequestBody Alumno alumno) {
        return alumnoService.saveAlumno(alumno);
    }

    @GetMapping("/{id}")
    public Alumno findById(@PathVariable("id") Long alumnoId) {
        return alumnoService.findAlumnoById(alumnoId);
    }

    @GetMapping("/")
    public List<Alumno> findAll() {
        return alumnoService.findAll();
    }

    @DeleteMapping("/")
    public void deleteAlumno(@RequestBody Alumno alumno) {
        Alumno temp = alumnoService.findAlumnoById(alumno.getId());
        if (null != temp && temp.getId() != null) {
            alumnoService.delete(temp);
        }
    }

    @GetMapping("/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headervalue = "attachment; filename=alumnos_info.xlsx";
        response.setHeader(headerKey, headervalue);
        List<Alumno> listAlumnos = alumnoService.findAll();
        AlumnoExcelExporter exp = new AlumnoExcelExporter(listAlumnos);
        exp.export(response);
    }
}
