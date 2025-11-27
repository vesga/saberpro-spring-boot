package com.uts.saberpro.service;

import com.uts.saberpro.model.ExamenResultado;
import com.uts.saberpro.model.Incentivo;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class IncentivoService {

    public Incentivo calcularIncentivo(ExamenResultado r) {

        Incentivo i = new Incentivo();
        LocalDate hoy = LocalDate.now();

        if ("PRO".equalsIgnoreCase(r.getTipoExamen())) {

            int p = r.getPuntaje();

            if (p >= 241) {
                i.setTipoIncentivo("Exención total + 100% derechos");
                i.setExencionInforme(true);
                i.setPorcentajeBeca(100);
                i.setNotaExigida(5.0);
                i.setDescripcion("Exención total del informe final y 100% beca de derechos de grado.");

            } else if (p >= 211) {
                i.setTipoIncentivo("Exención + 50% derechos");
                i.setExencionInforme(true);
                i.setPorcentajeBeca(50);
                i.setNotaExigida(4.7);
                i.setDescripcion("Exención del informe final y 50% beca de derechos de grado.");

            } else if (p >= 180) {
                i.setTipoIncentivo("Exención informe (nota 4.5)");
                i.setExencionInforme(true);
                i.setPorcentajeBeca(0);
                i.setNotaExigida(4.5);
                i.setDescripcion("Exención de entrega de informe final con nota 4.5.");

            } else {
                i.setTipoIncentivo("Ninguno");
                i.setDescripcion("No aplica incentivo.");
            }

        } else {

            int p = r.getPuntaje();

            if (p >= 171) {
                i.setTipoIncentivo("Exención total + 100% derechos (T&T)");
                i.setExencionInforme(true);
                i.setPorcentajeBeca(100);
                i.setNotaExigida(5.0);
                i.setDescripcion("Incentivo T&T superior a 171.");

            } else if (p >= 151) {
                i.setTipoIncentivo("Exención + 50% derechos (T&T)");
                i.setExencionInforme(true);
                i.setPorcentajeBeca(50);
                i.setNotaExigida(4.7);
                i.setDescripcion("Incentivo T&T entre 151 y 170.");

            } else if (p >= 120) {
                i.setTipoIncentivo("Exención informe (nota 4.5) (T&T)");
                i.setExencionInforme(true);
                i.setPorcentajeBeca(0);
                i.setNotaExigida(4.5);
                i.setDescripcion("Incentivo T&T entre 120 y 150.");

            } else {
                i.setTipoIncentivo("Ninguno");
                i.setDescripcion("No aplica incentivo.");
            }
        }

        i.setVigenciaInicio(hoy);
        i.setVigenciaFin(hoy.plusYears(1));

        return i;
    }
}
