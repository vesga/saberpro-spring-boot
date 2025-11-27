package com.uts.saberpro.controller;

import com.uts.saberpro.model.Alumno;
import com.uts.saberpro.model.ExamenResultado;
import com.uts.saberpro.model.Incentivo;
import com.uts.saberpro.repository.AlumnoRepository;
import com.uts.saberpro.repository.ExamenResultadoRepository;
import com.uts.saberpro.repository.IncentivoRepository;
import com.uts.saberpro.service.IncentivoService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.time.LocalDate;

@Controller
public class DashboardController {

    private final AlumnoRepository alumnoRepository;
    private final ExamenResultadoRepository resultadoRepository;
    private final IncentivoRepository incentivoRepository;
    private final IncentivoService incentivoService;

    public DashboardController(
            AlumnoRepository alumnoRepository,
            ExamenResultadoRepository resultadoRepository,
            IncentivoRepository incentivoRepository,
            IncentivoService incentivoService
    ) {
        this.alumnoRepository = alumnoRepository;
        this.resultadoRepository = resultadoRepository;
        this.incentivoRepository = incentivoRepository;
        this.incentivoService = incentivoService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, @AuthenticationPrincipal Alumno alumno) {

        long totalAlumnos = alumnoRepository.count();
        long totalResultados = resultadoRepository.count();
        long totalIncentivos = incentivoRepository.count();

        Double promedioPuntaje = resultadoRepository.promedioPuntaje();
        Integer mejorPuntaje = resultadoRepository.mejorPuntaje();
        LocalDate fechaUltimo = resultadoRepository.ultimaFechaResultado();

        model.addAttribute("totalAlumnos", totalAlumnos);
        model.addAttribute("totalResultados", totalResultados);
        model.addAttribute("totalIncentivos", totalIncentivos);
        model.addAttribute("promedioPuntaje", promedioPuntaje != null ? promedioPuntaje : 0);
        model.addAttribute("mejorPuntaje", mejorPuntaje != null ? mejorPuntaje : 0);
        model.addAttribute("fechaUltimo",
                fechaUltimo != null ? fechaUltimo.toString() : "Sin registros"
        );

        // INCENTIVO DEL ALUMNO LOGUEADO
        Incentivo incentivo = null;

        if (alumno != null) {

            ExamenResultado ultimoResultado =
                    resultadoRepository.findTopByAlumnoIdOrderByFechaDesc(alumno.getId());

            if (ultimoResultado != null) {

                // 👉 Convertir Optional → objeto o null
                incentivo = incentivoRepository
                        .findByExamenResultadoId(ultimoResultado.getId())
                        .orElse(null);

                if (incentivo == null) {
                    incentivo = incentivoService.calcularIncentivo(ultimoResultado);
                    incentivo.setExamenResultado(ultimoResultado);
                    incentivoRepository.save(incentivo);
                }
            }
        }

        model.addAttribute("incentivo", incentivo);

        return "dashboard";
    }
}
