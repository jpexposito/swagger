package es.iespuertodelacruz.cc.institutorest.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import es.iespuertodelacruz.cc.institutorest.entity.Asignatura;

public class AsignaturaDTO {

	private Integer idasignatura;
	private String curso;
	private String nombre;
	@JsonIgnore
	private List<MatriculaDTO> matriculas;

	public AsignaturaDTO() {
		
	}
	
	public AsignaturaDTO(Asignatura asignatura) {
		this.idasignatura = asignatura.getIdasignatura();
		this.curso = asignatura.getCurso();
		this.nombre = asignatura.getNombre();
		this.matriculas = new ArrayList<MatriculaDTO>();
		asignatura.getMatriculas().forEach(m -> matriculas.add(new MatriculaDTO(m)));
	}

	public Integer getIdasignatura() {
		return this.idasignatura;
	}

	public void setIdasignatura(Integer idasignatura) {
		this.idasignatura = idasignatura;
	}

	public String getCurso() {
		return this.curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<MatriculaDTO> getMatriculas() {
		return this.matriculas;
	}

	public void setMatriculas(List<MatriculaDTO> matriculas) {
		this.matriculas = matriculas;
	}
	
	public Asignatura toAsignatura() {
		return new Asignatura(this);
	}
}
