package es.iespuertodelacruz.cc.institutorest.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.iespuertodelacruz.cc.institutorest.dto.AlumnoDTO;
import es.iespuertodelacruz.cc.institutorest.dto.MatriculaDTO;
import es.iespuertodelacruz.cc.institutorest.dto.ShortMatriculaDTO;
import es.iespuertodelacruz.cc.institutorest.entity.Alumno;
import es.iespuertodelacruz.cc.institutorest.entity.Asignatura;
import es.iespuertodelacruz.cc.institutorest.entity.Matricula;
import es.iespuertodelacruz.cc.institutorest.service.AlumnoService;
import es.iespuertodelacruz.cc.institutorest.service.AsignaturaService;
import es.iespuertodelacruz.cc.institutorest.service.MatriculaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.annotations.*;
@Api
@RestController
@RequestMapping("/api/alumnos")
public class AlumnoREST {

	private Logger log = LoggerFactory.getLogger(AlumnoREST.class);
	
	@Autowired
	private AlumnoService alumnoService;
	
	@Autowired
	private MatriculaService matriculaService;
	
	@Autowired
	private AsignaturaService asignaturaService;
	
	/**
	 * Funcion que devuelve una lista de todos los alumnos
	 * @return
	 * Devuelve ok si todo ha ido bien
	 */
	@Operation(summary="Devuelve la lista de todos los alumnos en la base de datos")
	@ApiResponses(value = { 
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "No esta autorizado"), 
            @ApiResponse(code = 403, message = "Prohibido"),
            @ApiResponse(code = 404, message = "No encontrado") })
	@GetMapping
	public ResponseEntity<?> getAllAlumnos(@RequestParam(required=false, name="nombre")String nombre) {
		if (nombre == null) {
			ArrayList<AlumnoDTO> lista = new ArrayList<AlumnoDTO>();
			alumnoService.findAll().forEach(a -> lista.add(a.toDTO()));
			return ResponseEntity.ok(lista);
		} else {
			ArrayList<AlumnoDTO> lista = new ArrayList<AlumnoDTO>();
			alumnoService.findByNombre("%" + nombre + "%").forEach(a -> lista.add(a.toDTO()));
			return ResponseEntity.ok(lista);
		}
	}
	
	@Operation(summary = "Inserta un nuevo alumno en la base de datos")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "OK. El alumno se añadió a la base de datos"),
			@ApiResponse(code = 400, message = "Bad request. No se indicó el alumno a añadir o dicho alumno no contiene un dni"),
			@ApiResponse(code = 401, message = "No esta autorizado"), 
			@ApiResponse(code = 403, message = "Prohibido"),
			@ApiResponse(code = 404, message = "No encontrado") })
	@PostMapping
	public ResponseEntity<?> insertAlumno(@RequestBody AlumnoDTO alumno) {
		if (alumno == null) return ResponseEntity.badRequest().build();
		if (alumno.getDni() == null) return ResponseEntity.badRequest().build();
		alumnoService.save(alumno.toAlumno());
		return ResponseEntity.ok("Alumno creado correctamente");
	}

	
	@Operation(summary = "Devuelve un alumno dado su dni")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "OK. El alumno se devolvió"),
		@ApiResponse(code = 400, message = "Bad Request."),
		@ApiResponse(code = 404, message = "No se encontro el alumno con el dni proporcionado")
	})
	@GetMapping("/{dni}")
	public ResponseEntity<?> getAlumnoById (@PathVariable("dni") String id) {
		Optional<Alumno> alumno = alumnoService.findById(id);
		if (!alumno.isPresent()) return ResponseEntity.notFound().build();
		return ResponseEntity.ok(alumno.get().toDTO());
	}
	
	@Operation(summary = "Actualiza un alumno dado su dni")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. Alumno actualizado"),
			@ApiResponse(code = 400, message = "Bad Request. No se ha enviado un objeto a actualizar"),
			@ApiResponse(code = 404, message = "No se encontro el alumno con el dni proporcionado")
	})
	@PutMapping("/{dni}")
	public ResponseEntity<?> updateAlumnoById(@PathVariable("dni") String dni, @RequestBody AlumnoDTO alumnoDto) {
		if (alumnoDto == null) return ResponseEntity.badRequest().build();
		Optional<Alumno> alumno = alumnoService.findById(dni);
		if (!alumno.isPresent()) return ResponseEntity.notFound().build();
		alumnoService.save(alumnoDto.toAlumno());
		return ResponseEntity.ok("Alumno actualizado");
	}
	
	@Operation(summary = "Elimina un alumno dado su dni")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. Alumno eliminado"),
			@ApiResponse(code = 404, message = "No se encontro el alumno con el dni proporcionado")
	})
	@DeleteMapping("/{dni}")
	public ResponseEntity<?> deleteAlumnoById(@PathVariable("dni") String dni) {
		Optional<Alumno> alumno = alumnoService.findById(dni);
		if (!alumno.isPresent()) return ResponseEntity.notFound().build();
		alumnoService.deleteById(alumno.get());
		return ResponseEntity.ok("Alumno eliminado correctamente");
	}
	
	@Operation(summary = "Devuelve una lista de matriculas de un alumno especifico")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. Devuelve la lista"),
			@ApiResponse(code = 404, message = "No se encontro el alumno con el dni proporcionado")
	})
	@GetMapping("/{dni}/matriculas")
	public ResponseEntity<?> getMatriculas(@PathVariable("dni") String dni) {
		ArrayList<ShortMatriculaDTO> matriculas = new ArrayList<ShortMatriculaDTO>();
		Optional<Alumno> alumno = alumnoService.findById(dni);
		if (!alumno.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe el alumno con el dni indicado");
		matriculaService.findByAlumno(dni).forEach(m -> matriculas.add(new ShortMatriculaDTO(m)));
		return ResponseEntity.ok(matriculas);
	}
	
	@Operation(summary = "Inserta una matricula a un alumno")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "OK. Matricula creada"),
			@ApiResponse(code = 400, message = "Bad Request. No se proporciono un objeto matricula a añadir"),
			@ApiResponse(code = 404, message = "No se encontro el alumno con el dni proporcionado")
	})
	@PostMapping("/{dni}/matriculas")
	public ResponseEntity<?> insertMatricula(@PathVariable("dni") String dni, @RequestBody MatriculaDTO dto) {
		if (dto.getIdmatricula() != null) return ResponseEntity.badRequest().build();	
		Optional<Alumno> alumno = alumnoService.findById(dni);
		if (!alumno.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe el alumno con el dni indicado");
		ArrayList<Asignatura> asignaturas = new ArrayList<Asignatura>();
		for (Asignatura a : dto.getAsignaturas()) {
			Optional<Asignatura> asignatura = asignaturaService.findById(a.getIdasignatura());
			if (!asignatura.isPresent()) continue;
			asignaturas.add(asignatura.get());
		}
		Matricula newMatricula = new Matricula();
		newMatricula.setAsignaturas(asignaturas);
		newMatricula.setYear(dto.getYear());
		newMatricula.setAlumno(alumno.get());
		matriculaService.save(newMatricula);
		return ResponseEntity.status(HttpStatus.CREATED).body("Matricula creada correctamente");
	}

	@Operation(summary = "Devuelve una matricula especifica de un alumno especifico")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. Matricula devuelta"),
			@ApiResponse(code = 404, message = "No se encontro el alumno o la matricula"),
			@ApiResponse(code = 400, message = "Bad Request. La matricula no pertenece al alumno")
	})
	@GetMapping("/{dni}/matriculas/{id}")
	public ResponseEntity<?> getMatriculaById(@PathVariable("dni") String dni, @PathVariable("id") Integer id) {
		Optional<Alumno> alumno = alumnoService.findById(dni);
		if (!alumno.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Alumno no encontrado");
		Optional<Matricula> matricula = matriculaService.findById(id);
		if (!matricula.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Matricula no encontrada");
		log.debug("\nALUMNO: " + matricula.get().toString() + "\n");
		if (!(matricula.get().getAlumno().getDni().equals(dni))) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La matricula no pertenece al alumno indicado");
		return ResponseEntity.ok(matricula.get().toDTO());
	}
	
	@Operation(summary = "Elimina una matricula especifica de un alumno especifico")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. Matricula eliminada"),
			@ApiResponse(code = 400, message = "La matricula no pertenece al alumno"),
			@ApiResponse(code = 404, message = "No se encontro la matricula")
	})
	@DeleteMapping("/{dni}/matriculas/{id}")
	public ResponseEntity<?> deleteMatricula(@PathVariable("dni") String dni, @PathVariable("id") Integer id) {
		Optional<Matricula> matricula = matriculaService.findById(id);
		if (!matricula.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe ninguna matricula con el identificador indicado");
		if (!matricula.get().getAlumno().getDni().equals(dni)) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La matricula no pertenece al alumno indicado");
		matriculaService.deleteById(matricula.get());
		return ResponseEntity.ok("Eliminado correctamente la matricula");
	}
}
