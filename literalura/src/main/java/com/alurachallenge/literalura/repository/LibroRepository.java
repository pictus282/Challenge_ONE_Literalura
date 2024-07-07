package com.alurachallenge.literalura.repository;


import com.alurachallenge.literalura.models.Autor;
import com.alurachallenge.literalura.models.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    List<String> findDistinctIdiomaBy();

    @Query("SELECT l FROM Libro l ORDER BY l.totalDeDescargas DESC LIMIT 5")
    List<Libro> findTop5Descargados();

    @Query("SELECT l FROM Libro l WHERE l.titulo = :title")
    Optional<Libro> findByTitulo(String title);

    Optional<Libro> findById(long id);
    List<Libro> findByAutorIdAutor(Long idAutor);

    Optional<Libro> findLibroByTitulo(String titulo);

    @Query("SELECT DISTINCT l.idioma FROM Libro l")
    List<String> findDistinctIdiomas();

    @Query("SELECT l FROM Libro l WHERE l.idioma = :language")
    List<Libro> findByIdioma(String language);

    @Query("SELECT l FROM Libro l WHERE l.autor = :autor")
    List<Libro> findLibrosByAutor(Autor autor);
}