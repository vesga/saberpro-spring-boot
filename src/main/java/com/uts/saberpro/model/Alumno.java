package com.uts.saberpro.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "alumno")
public class Alumno {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Este campo permitirá vincular el alumno con el usuario que inicia sesión
    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String identificacion;

    @Column(nullable = false)
    private String nombre;

    private String correo;
    private String programa;
    private Integer semestre;

    private LocalDateTime creadoEn;
    private LocalDateTime actualizadoEn;

    @PrePersist 
    public void prePersist(){ 
        creadoEn = LocalDateTime.now(); 
    }

    @PreUpdate 
    public void preUpdate(){ 
        actualizadoEn = LocalDateTime.now(); 
    }

    // Getters y Setters

    public Long getId(){return id;}
    public void setId(Long id){this.id = id;}

    public String getUsername(){return username;}
    public void setUsername(String username){this.username = username;}

    public String getIdentificacion(){return identificacion;}
    public void setIdentificacion(String identificacion){this.identificacion = identificacion;}

    public String getNombre(){return nombre;}
    public void setNombre(String nombre){this.nombre = nombre;}

    public String getCorreo(){return correo;}
    public void setCorreo(String correo){this.correo = correo;}

    public String getPrograma(){return programa;}
    public void setPrograma(String programa){this.programa = programa;}

    public Integer getSemestre(){return semestre;}
    public void setSemestre(Integer semestre){this.semestre = semestre;}

    public LocalDateTime getCreadoEn(){return creadoEn;}
    public LocalDateTime getActualizadoEn(){return actualizadoEn;}
}
