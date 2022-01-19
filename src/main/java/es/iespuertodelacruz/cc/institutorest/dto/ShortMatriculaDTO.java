package es.iespuertodelacruz.cc.institutorest.dto;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import es.iespuertodelacruz.cc.institutorest.entity.Alumno;
import es.iespuertodelacruz.cc.institutorest.entity.Matricula;


public class ShortMatriculaDTO {
	private int idmatricula;
	private int year;
	@JsonIgnore
	private Alumno alumno;
	private Integer asignaturas;

	public ShortMatriculaDTO() {
		
	}
	
	public ShortMatriculaDTO(Matricula matricula) {
		this.idmatricula = matricula.getIdmatricula();
		this.year = matricula.getYear();
		this.alumno = matricula.getAlumno();
		this.asignaturas = matricula.getAsignaturas().size();
	}

	public int getIdmatricula() {
		return this.idmatricula;
	}

	public void setIdmatricula(int idmatricula) {
		this.idmatricula = idmatricula;
	}

	public int getYear() {
		return this.year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public Alumno getAlumno() {
		return this.alumno;
	}

	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}

	public Integer getAsignaturas() {
		return this.asignaturas;
	}

	public void setAsignaturas(Integer asignaturas) {
		this.asignaturas = asignaturas;
	}

}
