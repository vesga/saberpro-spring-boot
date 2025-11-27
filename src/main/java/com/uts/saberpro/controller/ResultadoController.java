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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/resultados")
public class ResultadoController {

    private final ExamenResultadoRepository resultadoRepository;
    private final AlumnoRepository alumnoRepository;
    private final IncentivoService incentivoService;
    private final IncentivoRepository incentivoRepository;

    public ResultadoController(
            ExamenResultadoRepository resultadoRepository,
            AlumnoRepository alumnoRepository,
            IncentivoService incentivoService,
            IncentivoRepository incentivoRepository
    ) {
        this.resultadoRepository = resultadoRepository;
        this.alumnoRepository = alumnoRepository;
        this.incentivoService = incentivoService;
        this.incentivoRepository = incentivoRepository;
    }

    @GetMapping("/nuevo")
    public String nuevoForm(Model model) {
        model.addAttribute("resultado", new ExamenResultado());
        model.addAttribute("alumnos", alumnoRepository.findAll());
        return "resultados/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute ExamenResultado resultado, Model model) {

        Optional<Alumno> a = alumnoRepository.findById(resultado.getAlumno().getId());

        if (a.isEmpty()) {
            model.addAttribute("error", "El alumno seleccionado no existe.");
            model.addAttribute("alumnos", alumnoRepository.findAll());
            return "resultados/form";
        }

        resultado.setAlumno(a.get());
        if (resultado.getPuntaje() == null) resultado.setPuntaje(0);

        resultadoRepository.save(resultado);

        // Incentivo automático
        Incentivo inc = incentivoService.calcularIncentivo(resultado);
        inc.setExamenResultado(resultado);
        incentivoRepository.save(inc);

        return "redirect:/resultados/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<ExamenResultado> res = resultadoRepository.findAll();
        model.addAttribute("resultados", res);
        return "resultados/list";
    }
}
