package com.uts.saberpro.controller;

import com.uts.saberpro.model.ExamenResultado;
import com.uts.saberpro.model.Incentivo;
import com.uts.saberpro.service.IncentivoService;
import com.uts.saberpro.repository.ExamenResultadoRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class IncentivoController {

    private final IncentivoService incentivoService;
    private final ExamenResultadoRepository resultadoRepository;

    public IncentivoController(IncentivoService incentivoService,
                               ExamenResultadoRepository resultadoRepository) {
        this.incentivoService = incentivoService;
        this.resultadoRepository = resultadoRepository;
    }

    @GetMapping("/incentivo/{alumnoId}")
    public String verIncentivo(@PathVariable Long alumnoId, Model model) {

        ExamenResultado resultado =
                resultadoRepository.findTopByAlumnoIdOrderByFechaDesc(alumnoId);

        if (resultado == null) {
            model.addAttribute("error", "El alumno no tiene examen registrado.");
            return "incentivo";
        }

        Incentivo incentivo = incentivoService.calcularIncentivo(resultado);

        model.addAttribute("incentivo", incentivo);
        model.addAttribute("resultado", resultado);

        return "incentivo";
    }
}
