package com.alurachallenge.literalura.DTO;


import com.alurachallenge.literalura.models.Autor;


public record LibroDTO(int idLibro,
                       String titulo,
                       Autor autor,
                       String idioma,
                       int numeroDeDescargas
) {
}