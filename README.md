# Enrique Poveda
![](https://avatars.githubusercontent.com/u/39422466?s=200&u=9ae32dc64d9ec00f3b36f0bb0412af0f61f05289&v=4)

**Table of Contents**

[TOCM]

[TOC]

### Features *alumno-service*
- Developed in Java with Spring Boot
- CREATE, UPDATE, DELETE, SELECT
- Postgres latest database
- Deploy in Docker with Docker Compose

### Dockerfile at the root of the project
	FROM adoptopenjdk:11-jre-hotspot
	MAINTAINER enriquepoveda.com
	ARG JAR_FILE=target/*.jar
	COPY ${JAR_FILE} alumno-service-0.0.1-SNAPSHOT.jar
	ENTRYPOINT ["java","-jar","/alumno-service-0.0.1-SNAPSHOT.jar"]

### docker-compose.yml at the root of the project
	version: '2'

	services:
	  app:
		image: 'alumno-service:latest'
		build:
		  context: .
		container_name: app
		depends_on:
		  - db
		environment:
		  - SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/compose-postgres
		  - SPRING_DATASOURCE_USERNAME=compose-postgres
		  - SPRING_DATASOURCE_PASSWORD=compose-postgres
		  - SPRING_JPA_HIBERNATE_DDL_AUTO=update
		  - SPRING_JPA_SHOW_SQL=false
		ports:
		  - 8087:8087

	  db:
		image: 'postgres:latest'
		container_name: db
		environment:
		  - POSTGRES_USER=compose-postgres
		  - POSTGRES_PASSWORD=compose-postgres

### Entity
    @Entity
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

### Controller
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

### Service
	@Service
	public class AlumnoService {

		@Autowired
		private AlumnoRepositorio alumnoRepositorio;

		public Alumno saveAlumno(Alumno alumno) {
			return alumnoRepositorio.save(alumno);
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

### Deploy
- You must have  [Docker](https://www.docker.com/products/docker-desktop) installed.
- Run  command (**docker login**), not necessarily, in my case when using the latest version of docker it threw an error when pulling images but when logging in I solved it.
- Enter your docker hub credentials
- Enter the root directory of the project
- Run command (**docker-compose up**)

That will download and run in docker the images from both the database and the java version 11.

### Documentation
- Once the services are lifted, the documentation of the Apis will be available at http://localhost:8087/swagger-ui.html#/ and from there you can run each service.
