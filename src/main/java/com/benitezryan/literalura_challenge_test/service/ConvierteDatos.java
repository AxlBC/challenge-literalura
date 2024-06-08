package com.benitezryan.literalura_challenge_test.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvierteDatos implements IConvierteDatos{
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
        try {
//            JsonNode rootNode = objectMapper.readTree(json);
//            JsonNode resultsNode = rootNode.get("results");
//            FiltroInfo info = new FiltroInfo(rootNode, resultsNode);
//            System.out.println(rootNode + "\n" + resultsNode);
//            System.out.println(resultsNode + "\nTipo de datos: " + resultsNode.getClass().getSimpleName());
//            System.out.println((resultsNode.get(0)).get("title"));


            // deserializa
//            System.out.println("intentando deserializar");
            return objectMapper.readValue(json, clase);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
