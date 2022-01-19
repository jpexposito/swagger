package es.iespuertodelacruz.cc.institutorest.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.iespuertodelacruz.cc.institutorest.entity.Asignatura;
import es.iespuertodelacruz.cc.institutorest.repository.AsignaturaRepository;
@Service
public class AsignaturaService implements GenericService<Asignatura, Integer> {

	@Autowired
	private AsignaturaRepository repository;
	
	@Override
	@Transactional(readOnly=true)
	public Iterable<Asignatura> findAll() {
		return repository.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Asignatura> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
	@Transactional(readOnly=true)
	public Optional<Asignatura> findById(Integer id) {
		return repository.findById(id);
	}

	@Override
	@Transactional
	public Asignatura save(Asignatura producto) {
		return repository.save(producto);
	}

	@Override
	@Transactional
	public void deleteById(Asignatura entity) {
		repository.delete(entity);
	}


}
