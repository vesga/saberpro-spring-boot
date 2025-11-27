package com.uts.saberpro.repository;

import com.uts.saberpro.model.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AlumnoRepository extends JpaRepository<Alumno, Long> {

    Optional<Alumno> findByIdentificacion(String identificacion);

    // 👉 Este método permitirá traer el alumno por el usuario del login
    Optional<Alumno> findByUsername(String username);
}
