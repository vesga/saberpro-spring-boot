package com.uts.saberpro.service;

import com.uts.saberpro.model.Alumno;

import java.util.List;

public interface AlumnoService {
    Alumno guardar(Alumno alumno);
    List<Alumno> listar();
    Alumno buscarPorId(Long id);
    Alumno buscarPorUsername(String username);
    void eliminar(Long id);
}
