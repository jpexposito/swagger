package es.iespuertodelacruz.cc.institutorest.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import es.iespuertodelacruz.cc.institutorest.dto.AsignaturaDTO;

import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the asignaturas database table.
 * 
 */
@Entity
@Table(name="asignaturas")
@NamedQuery(name="Asignatura.findAll", query="SELECT a FROM Asignatura a")
public class Asignatura implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idasignatura;

	private String curso;

	private String nombre;

	//bi-directional many-to-many association to Matricula
	@ManyToMany(mappedBy = "asignaturas",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonIgnore
	private List<Matricula> matriculas;

	public Asignatura() {
	}

	public Asignatura(AsignaturaDTO dto) {
		try {
			idasignatura = dto.getIdasignatura();
		} catch (NullPointerException e) {}
		curso = dto.getCurso();
		nombre = dto.getNombre();
		matriculas = new ArrayList<Matricula>();
		try {
			dto.getMatriculas().forEach(m -> matriculas.add(m.toMatricula()));
		} catch (NullPointerException e) {}
	}
	
	public int getIdasignatura() {
		return this.idasignatura;
	}

	public void setIdasignatura(int idasignatura) {
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

	public List<Matricula> getMatriculas() {
		return this.matriculas;
	}

	public void setMatriculas(List<Matricula> matriculas) {
		this.matriculas = matriculas;
	}
	
	public AsignaturaDTO toDTO() {
		return new AsignaturaDTO(this);
	}

}