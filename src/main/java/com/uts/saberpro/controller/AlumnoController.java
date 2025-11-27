package com.uts.saberpro.controller;

import com.uts.saberpro.model.Alumno;
import com.uts.saberpro.repository.AlumnoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/alumnos")
public class AlumnoController {

    private final AlumnoRepository alumnoRepository;

    public AlumnoController(AlumnoRepository alumnoRepository){
        this.alumnoRepository = alumnoRepository;
    }

    // LISTAR
    @GetMapping
    public String list(Model model,
                       @RequestParam(required = false) String success,
                       @RequestParam(required = false) String error) {

        model.addAttribute("alumnos", alumnoRepository.findAll());
        model.addAttribute("success", success);
        model.addAttribute("error", error);
        return "alumnos/list";
    }

    // FORMULARIO NUEVO
    @GetMapping("/nuevo")
    public String nuevoForm(Model model){
        model.addAttribute("alumno", new Alumno());
        return "alumnos/form";
    }

    // GUARDAR NUEVO
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Alumno alumno, Model model){

        // Validar identificación duplicada
        var existing = alumnoRepository.findByIdentificacion(alumno.getIdentificacion());
        if (existing.isPresent()) {
            model.addAttribute("error", "La identificación ya existe");
            return "alumnos/form";
        }

        alumnoRepository.save(alumno);
        return "redirect:/alumnos?success=Alumno creado correctamente";
    }

    // FORMULARIO EDITAR
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model){
        var alumno = alumnoRepository.findById(id).orElse(null);

        if (alumno == null) {
            return "redirect:/alumnos?error=No existe el alumno";
        }

        model.addAttribute("alumno", alumno);
        return "alumnos/form";
    }

    // GUARDAR EDICIÓN
    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable Long id, @ModelAttribute Alumno alumno, Model model){

        var existente = alumnoRepository.findByIdentificacion(alumno.getIdentificacion());

        // Si existe, y NO es el mismo ID → error
        if (existente.isPresent() && !existente.get().getId().equals(id)) {
            model.addAttribute("error", "La identificación ya está registrada");
            return "alumnos/form";
        }

        alumno.setId(id); // importante
        alumnoRepository.save(alumno);

        return "redirect:/alumnos?success=Alumno actualizado correctamente";
    }

    // ELIMINAR
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id){
        alumnoRepository.deleteById(id);
        return "redirect:/alumnos?success=Alumno eliminado";
    }
}
