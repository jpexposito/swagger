package es.iespuertodelacruz.cc.institutorest.dto;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import es.iespuertodelacruz.cc.institutorest.entity.Alumno;
import es.iespuertodelacruz.cc.institutorest.entity.Matricula;

public class AlumnoDTO {

	private String dni;
	private String apellidos;
	private BigInteger fechanacimiento;
	private String nombre;
	@JsonIgnore
	private List<Matricula> matriculas;

	public AlumnoDTO(){
		
	}
	
	public AlumnoDTO(Alumno alumno) {
		this.dni = alumno.getDni();
		this.apellidos = alumno.getApellidos();
		this.fechanacimiento = alumno.getFechanacimiento();
		this.nombre = alumno.getNombre();
		this.matriculas = alumno.getMatriculas();
	}

	public String getDni() {
		return this.dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public BigInteger getFechanacimiento() {
		return this.fechanacimiento;
	}

	public void setFechanacimiento(BigInteger fechanacimiento) {
		this.fechanacimiento = fechanacimiento;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Matricula> getMatriculas() {
		return this.matriculas;
	}

	public void setMatriculas(List<Matricula> matriculas) {
		this.matriculas = matriculas;
	}
	
	public Alumno toAlumno() {
		return new Alumno(this);
	}
	
}
