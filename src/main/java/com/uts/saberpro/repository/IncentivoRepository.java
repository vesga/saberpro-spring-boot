package com.uts.saberpro.repository;

import com.uts.saberpro.model.Incentivo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface IncentivoRepository extends JpaRepository<Incentivo, Long> {

    // Busca el incentivo asociado al último resultado del estudiante
    Optional<Incentivo> findByExamenResultadoId(Long examenResultadoId);
}
