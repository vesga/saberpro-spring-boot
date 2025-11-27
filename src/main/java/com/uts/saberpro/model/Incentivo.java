package com.uts.saberpro.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "incentivos")
public class Incentivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación: un incentivo pertenece a un resultado de examen
    @OneToOne
    @JoinColumn(name = "examen_resultado_id", referencedColumnName = "id")
    private ExamenResultado examenResultado;

    private String tipoIncentivo;

    private boolean exencionInforme;

    private Integer porcentajeBeca;

    private Double notaExigida;

    private String descripcion;

    private LocalDate vigenciaInicio;

    private LocalDate vigenciaFin;

    public Incentivo() {}

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ExamenResultado getExamenResultado() {
        return examenResultado;
    }

    public void setExamenResultado(ExamenResultado examenResultado) {
        this.examenResultado = examenResultado;
    }

    public String getTipoIncentivo() {
        return tipoIncentivo;
    }

    public void setTipoIncentivo(String tipoIncentivo) {
        this.tipoIncentivo = tipoIncentivo;
    }

    public boolean isExencionInforme() {
        return exencionInforme;
    }

    public void setExencionInforme(boolean exencionInforme) {
        this.exencionInforme = exencionInforme;
    }

    public Integer getPorcentajeBeca() {
        return porcentajeBeca;
    }

    public void setPorcentajeBeca(Integer porcentajeBeca) {
        this.porcentajeBeca = porcentajeBeca;
    }

    public Double getNotaExigida() {
        return notaExigida;
    }

    public void setNotaExigida(Double notaExigida) {
        this.notaExigida = notaExigida;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getVigenciaInicio() {
        return vigenciaInicio;
    }

    public void setVigenciaInicio(LocalDate vigenciaInicio) {
        this.vigenciaInicio = vigenciaInicio;
    }

    public LocalDate getVigenciaFin() {
        return vigenciaFin;
    }

    public void setVigenciaFin(LocalDate vigenciaFin) {
        this.vigenciaFin = vigenciaFin;
    }
}
