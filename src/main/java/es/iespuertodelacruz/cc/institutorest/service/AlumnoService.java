package es.iespuertodelacruz.cc.institutorest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.iespuertodelacruz.cc.institutorest.entity.Alumno;
import es.iespuertodelacruz.cc.institutorest.repository.AlumnoRepository;

@Service
public class AlumnoService implements GenericService<Alumno, String>{

	@Autowired
	private AlumnoRepository repository;
	
	@Override
	@Transactional(readOnly=true)
	public Iterable<Alumno> findAll() {
		return repository.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Alumno> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
	@Transactional(readOnly=true)
	public Optional<Alumno> findById(String id) {
		return repository.findById(id);
	}

	@Override
	@Transactional
	public Alumno save(Alumno producto) {
		return repository.save(producto);
	}

	@Override
	@Transactional
	public void deleteById(Alumno entity) {
		repository.delete(entity);
	}
	
	@Transactional(readOnly=true)
	public List<Alumno> findByNombre(String nombre) {
		return repository.findByNombre(nombre);
	}

}
