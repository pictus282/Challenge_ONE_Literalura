package com.alurachallenge.literalura.services;


import com.alurachallenge.literalura.models.Autor;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ConverteDatos implements IConvierteDatos {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
        try {
            return objectMapper.readValue(json, clase);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}