package es.iespuertodelacruz.cc.institutorest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.iespuertodelacruz.cc.institutorest.entity.Matricula;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Integer> {

	
	@Query("SELECT m FROM Matricula m INNER JOIN Alumno a ON m.alumno = a WHERE a.dni = :dni")
	List<Matricula> findByAlumno(@Param("dni")String paramDni);
	
}
