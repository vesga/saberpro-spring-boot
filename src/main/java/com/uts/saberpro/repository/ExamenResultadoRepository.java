package com.uts.saberpro.repository;

import com.uts.saberpro.model.ExamenResultado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ExamenResultadoRepository extends JpaRepository<ExamenResultado, Long> {

    List<ExamenResultado> findByAlumnoIdOrderByFechaDesc(Long alumnoId);

    // Obtener el último examen del alumno
    ExamenResultado findTopByAlumnoIdOrderByFechaDesc(Long alumnoId);

    List<ExamenResultado> findByTipoExamen(String tipoExamen);

    // Promedio general de puntajes
    @Query("SELECT AVG(e.puntaje) FROM ExamenResultado e")
    Double promedioPuntaje();

    // Mejor puntaje registrado
    @Query("SELECT MAX(e.puntaje) FROM ExamenResultado e")
    Integer mejorPuntaje();

    // Última fecha registrada
    @Query("SELECT MAX(e.fecha) FROM ExamenResultado e")
    LocalDate ultimaFechaResultado();
}
