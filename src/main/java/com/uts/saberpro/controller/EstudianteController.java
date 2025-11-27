package com.uts.saberpro.controller;

import com.uts.saberpro.model.Alumno;
import com.uts.saberpro.model.ExamenResultado;
import com.uts.saberpro.model.Incentivo;
import com.uts.saberpro.repository.AlumnoRepository;
import com.uts.saberpro.repository.ExamenResultadoRepository;
import com.uts.saberpro.service.IncentivoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class EstudianteController {

    private final AlumnoRepository alumnoRepository;
    private final ExamenResultadoRepository resultadoRepository;
    private final IncentivoService incentivoService;

    public EstudianteController(
            AlumnoRepository alumnoRepository,
            ExamenResultadoRepository resultadoRepository,
            IncentivoService incentivoService
    ) {
        this.alumnoRepository = alumnoRepository;
        this.resultadoRepository = resultadoRepository;
        this.incentivoService = incentivoService;
    }

    @GetMapping("/estudiante")
    public String dashboardEstudiante(Model model, Principal principal) {

        // usuario del login
        String username = principal.getName();

        // buscar alumno por username
        Alumno alumno = alumnoRepository.findByUsername(username)
                .orElse(null);

        if (alumno == null) {
            model.addAttribute("error", "No hay un alumno registrado con el usuario: " + username);
            return "error";
        }

        model.addAttribute("alumno", alumno);

        // Obtener todos los resultados por ID de Alumno
        List<ExamenResultado> resultados =
                resultadoRepository.findByAlumnoIdOrderByFechaDesc(alumno.getId());

        model.addAttribute("resultados", resultados);

        // Último resultado
        ExamenResultado ultimo = resultados.isEmpty() ? null : resultados.get(0);
        model.addAttribute("resultadoUnico", ultimo);

        // Incentivo del último resultado
        if (ultimo != null) {
            Incentivo incentivo = incentivoService.calcularIncentivo(ultimo);
            model.addAttribute("incentivo", incentivo);
        } else {
            model.addAttribute("incentivo", null);
        }

        // 🚀 Aquí va tu plantilla REAL
        return "estudiante/dashboard";
    }
}
