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
import org.springframework.web.bind.annotation.RestController;

import es.iespuertodelacruz.cc.institutorest.dto.AsignaturaDTO;
import es.iespuertodelacruz.cc.institutorest.entity.Asignatura;
import es.iespuertodelacruz.cc.institutorest.service.AsignaturaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.annotations.*;

@RestController
@RequestMapping("/api/asignaturas")
public class AsignaturaREST {

	private Logger log = LoggerFactory.getLogger(AsignaturaREST.class);
	
	@Autowired
	private AsignaturaService service;
	
	@Operation(summary = "Devuelve todas las asignaturas")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. Se devuelve la lista")
	})
	@GetMapping
	public ResponseEntity<?> getAllAsignaturas() {
		ArrayList<AsignaturaDTO> lista = new ArrayList<AsignaturaDTO>();
		service.findAll().forEach(a -> lista.add(a.toDTO()));
		return ResponseEntity.ok(lista);
	}
	
	@Operation(summary = "Inserta una nueva asignatura")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "OK. Asignatura creada"),
			@ApiResponse(code = 400, message = "Bad Request. No se envió un objeto o este contiene un id")
	})
	@PostMapping
	public ResponseEntity<?> insertAsignatura(@RequestBody AsignaturaDTO dto) {
		if (dto == null) return ResponseEntity.badRequest().body("No se especificó la asignatura a añadir");
		if (dto.getIdasignatura() != null) return ResponseEntity.badRequest().body("No se permite establecer un identificador");
		service.save(dto.toAsignatura());
		return ResponseEntity.status(HttpStatus.CREATED).body("Se ha añadido la asignatura correctamente");
	}
	
	@Operation(summary = "Devuelve un objeto asignatura con un id especifico")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. Se devuelve el objeto"),
			@ApiResponse(code = 404, message = "No se encontro la asignatura")
	})
	@GetMapping("/{id}")
	public ResponseEntity<?> getAsignaturaById (@PathVariable("id") Integer id) {
		Optional<Asignatura> asignatura = service.findById(id);
		if (!asignatura.isPresent()) return ResponseEntity.notFound().build();
		return ResponseEntity.ok(asignatura.get().toDTO());
	}
	
	@Operation(summary = "Actualiza una asignatura")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. Se actualizó la asignatura"),
			@ApiResponse(code = 400, message = "Los identificadores no coinciden"),
			@ApiResponse(code = 404, message = "No se encontró la asignatura")
	})
	@PutMapping("/{id}")
	public ResponseEntity<?> updateAsignaturaById(@PathVariable("id") Integer id, @RequestBody AsignaturaDTO dto) {
		Optional<Asignatura> optionalAsignatura = service.findById(id);
		if (!optionalAsignatura.isPresent()) return ResponseEntity.notFound().build();
		if (id != dto.getIdasignatura()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Los identificadores no son correctos");
		Asignatura asignatura = optionalAsignatura.get();
		asignatura.setCurso(dto.getCurso());
		asignatura.setNombre(dto.getNombre());
		service.save(asignatura);
		return ResponseEntity.ok("Asignatura actualizada");
	}
	
	@Operation(summary = "Elimina una asignatura")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK. Asignatura eliminada"),
			@ApiResponse(code = 404, message = "No se encontro la asignatura")
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteAsignaturaById(@PathVariable("id") Integer id) {
		Optional<Asignatura> asignatura = service.findById(id);
		if (!asignatura.isPresent()) return ResponseEntity.notFound().build();
		service.deleteById(asignatura.get());
		return ResponseEntity.ok("Asignatura eliminada");
	}
}
