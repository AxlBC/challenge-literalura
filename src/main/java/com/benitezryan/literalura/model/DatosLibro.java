package com.benitezryan.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
        @JsonAlias("title") String titulo,
//        @JsonAlias("authors") ArrayNode autor,
//        @JsonAlias("subjects") List<String> temas,
        @JsonAlias("languages") List<String> idiomas,
//        @JsonAlias("bookshelves") List<String> estanterias,
        @JsonAlias("copyright") Boolean copyright,
        @JsonAlias("download_count") Integer descargas
) {
}
