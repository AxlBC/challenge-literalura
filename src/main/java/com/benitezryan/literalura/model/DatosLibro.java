package com.benitezryan.literalura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
        String titulo,
        String autor,
        List<String> temas,
        List<String> estanterias,
        Boolean copyright,
        Integer descargas
) {
}
