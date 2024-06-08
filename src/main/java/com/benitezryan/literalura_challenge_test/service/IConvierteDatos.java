package com.benitezryan.literalura_challenge_test.service;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
