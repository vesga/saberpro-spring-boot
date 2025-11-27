package com.uts.saberpro.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "examen_resultado")
public class ExamenResultado {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)   // 🔥 EVITA LazyInitializationException
    @JoinColumn(name = "alumno_id")
    private Alumno alumno;

    @Column(name = "tipo_examen")
    private String tipoExamen; // "PRO" or "TT"

    private LocalDate fecha;
    private Integer puntaje;

    private LocalDateTime creadoEn;

    @PrePersist 
    public void prePersist(){ 
        creadoEn = LocalDateTime.now(); 
    }

    // -------- GETTERS y SETTERS --------

    public Long getId(){ return id; }
    public void setId(Long id){ this.id = id; }

    public Alumno getAlumno(){ return alumno; }
    public void setAlumno(Alumno alumno){ this.alumno = alumno; }

    public String getTipoExamen(){ return tipoExamen; }
    public void setTipoExamen(String tipoExamen){ this.tipoExamen = tipoExamen; }

    public LocalDate getFecha(){ return fecha; }
    public void setFecha(LocalDate fecha){ this.fecha = fecha; }

    public Integer getPuntaje(){ return puntaje; }
    public void setPuntaje(Integer puntaje){ this.puntaje = puntaje; }

    public LocalDateTime getCreadoEn(){ return creadoEn; }
    public void setCreadoEn(LocalDateTime creadoEn){ this.creadoEn = creadoEn; }
}
