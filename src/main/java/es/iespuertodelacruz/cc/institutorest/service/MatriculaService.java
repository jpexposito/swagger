package es.iespuertodelacruz.cc.institutorest.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.iespuertodelacruz.cc.institutorest.entity.Matricula;
import es.iespuertodelacruz.cc.institutorest.repository.MatriculaRepository;
@Service
public class MatriculaService implements GenericService<Matricula, Integer>{

	@Autowired
	private MatriculaRepository repository;
	
	@Override
	@Transactional(readOnly=true)
	public Iterable<Matricula> findAll() {
		return repository.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Matricula> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
	@Transactional(readOnly=true)
	public Optional<Matricula> findById(Integer id) {
		return repository.findById(id);
	}

	public Iterable<Matricula> findByAlumno(String dni) {
		return repository.findByAlumno(dni);
		//return repository.findAll().stream().filter(m -> m.getAlumno().getDni().equals(dni)).collect(Collectors.toList());
	}
	
	@Override
	@Transactional
	public Matricula save(Matricula producto) {
		return repository.save(producto);
	}

	@Override
	@Transactional
	public void deleteById(Matricula entity) {
		repository.delete(entity);
	}

}
