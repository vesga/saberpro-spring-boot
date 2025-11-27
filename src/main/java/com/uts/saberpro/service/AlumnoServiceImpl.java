package com.uts.saberpro.service;

import com.uts.saberpro.model.Alumno;
import com.uts.saberpro.repository.AlumnoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlumnoServiceImpl implements AlumnoService {

    private final AlumnoRepository repo;

    public AlumnoServiceImpl(AlumnoRepository repo) {
        this.repo = repo;
    }

    @Override
    public Alumno guardar(Alumno alumno) {
        return repo.save(alumno);
    }

    @Override
    public List<Alumno> listar() {
        return repo.findAll();
    }

    @Override
    public Alumno buscarPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Alumno buscarPorUsername(String username) {
        return repo.findByUsername(username).orElse(null);
    }

    @Override
    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}
