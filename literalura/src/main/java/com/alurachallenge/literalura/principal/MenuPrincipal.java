package com.alurachallenge.literalura.principal;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.alurachallenge.literalura.models.Autor;
import com.alurachallenge.literalura.models.DatosAutor;
import com.alurachallenge.literalura.models.DatosLibro;
import com.alurachallenge.literalura.models.Libro;
import com.alurachallenge.literalura.models.Idioma;
import com.alurachallenge.literalura.repository.AutorRepository;
import com.alurachallenge.literalura.repository.LibroRepository;
import com.alurachallenge.literalura.services.AutorServices;
import com.alurachallenge.literalura.services.ConsumoApi;
import com.alurachallenge.literalura.services.ConverteDatos;
import com.alurachallenge.literalura.services.LibroServices;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class MenuPrincipal {
    public LibroRepository libroRepository;
    private AutorRepository autorRepository;
    private Scanner teclado = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDatos convierteDatos = new ConverteDatos();
    private DatosLibro datosLibro;
    private DatosAutor datosAutor;
    private LibroServices libroServices = new LibroServices();
    private AutorServices autorServices = new AutorServices();

    private Autor autorDelLibro;
    private int opciones = -1;


    private List<Autor> autores;
    private List<Libro> libros;


    public MenuPrincipal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
        this.libroRepository = libroRepository;
    }


    public void muestraElMenu() throws Exception {


        var textoMenu = """
                \n ********************************
                \n Menu  Principal - Liter Alura 
                \n ******************************** 
                
                 1 - Buscar libro por titulo
                 2 - Listar libro registrados
                 3 - Listar autor registrados
                 4 - Listar autor vivos en un determinado año
                 5 - Listar libro por idioma
                 6 - Top 5 libros más descargados
                 7 - Listado de libros por autor
                
                       \s
                 0 - Salir
                \n
                Elija un Opción a través de su número correspondiente:
               \s""";

        do {
            System.out.print(textoMenu);
            opciones = teclado.nextInt();
            teclado.nextLine();

            switch (opciones) {
                case 1 ->obtenerLibroPorTitulo();
                case 2 ->listarLibrosRegistrados();
                case 3 ->listarAutoresRegistrados();
                case 4 ->listarAutoresVivosPorAno();
                case 5 ->listarLibrosPorIdioma();
                case 6->top10LiborsDescargados();
                case 7 ->listadoDeLibrosPorAutor();



                case 0-> System.out.println("Hasta Luego");
                default -> System.out.println("Opción Inválida");
            }
        }
        while (opciones != 0);
    }


    private void obtenerLibroPorTitulo() throws Exception {
        // getDatosLibro();
        busquedaDeLibros();
    }
    private void listarLibrosRegistrados() {
        System.out.println("\nListado de libros registrados:");
        libros = libroRepository.findAll();
        libros.forEach(System.out::println);

        hacerPause();

    }
    private void listarAutoresRegistrados() {
        System.out.println("\nListado de autores registrados:");
        autores = autorRepository.findAll();
        autores.forEach(System.out::println);
        hacerPause();
    }
    private void listarAutoresVivosPorAno() {
        System.out.println("\nEscriba el año que desea buscar: ");
        var ano = teclado.nextInt();

        autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("No hay autores vivos en ese año:");
        }else {
            System.out.println(ano);
            System.out.println("\nListado de autores vivos en el año " + ano + ": ");
            autores = autorRepository.findAutorByFecha(ano);

            autores.forEach(System.out::println);

        }
        hacerPause();
    }
    private void listarLibrosPorIdioma() {
        System.out.println("\nListado de idiomas registrados:");
        var listaDeIdiomas = libroRepository.findDistinctIdiomas();
        for (int i = 0; i < listaDeIdiomas.size() ; i++) {
            System.out.println("[" + (i + 1) + "] " + Idioma.from(listaDeIdiomas.get(i)));
        }
        System.out.println("\nSeleccione el numero del idioma que desea buscar: ");
        var eleccion = teclado.nextInt();
        if (eleccion < 1 || eleccion > listaDeIdiomas.size()) {
            System.out.println("Opción Inválida");
            return;
        }
        String idiomaSeleccionado =Idioma.from(listaDeIdiomas.get(eleccion - 1)).toString();
        System.out.println("\nListado de libros en - [ " + idiomaSeleccionado.toUpperCase() + " ]:");
        libros = libroRepository.findByIdioma(listaDeIdiomas.get(eleccion - 1));
        libros.forEach(System.out::println);
        System.out.println();

        hacerPause();
    }
    private void top10LiborsDescargados() {
        System.out.println("\n Top 5 libros más descargados:\n");
        libros = libroRepository.findTop5Descargados();
        libros.forEach(System.out::println);
        hacerPause();
    }
    private void listadoDeLibrosPorAutor() {
        autores = autorRepository.findAll();
        System.out.println("\nListado de autores registrados:");
        for (int i = 0; i <autores.size() ; i++) {
            System.out.println("[" + (i + 1) + "] " + autores.get(i).getNombre());
        }

        System.out.println("\nEscriba el número del autor que desea buscar: ");
        var eleccion = teclado.nextInt();
        if (eleccion < 1 || eleccion > autores.size()) {
            System.out.println("Opción Inválida");
            return;
        }
        System.out.println("\nListado de libros de : " + autores.get(eleccion - 1).getNombre());
        libros = libroRepository.findLibrosByAutor(autores.get(eleccion - 1));
        libros.forEach(System.out::println);
        hacerPause();



    }



    public void hacerPause() {
        System.out.println( );
        Terminal terminal = null;
        try {
            terminal = TerminalBuilder.builder().build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        LineReader lineReader = LineReaderBuilder.builder().terminal(terminal).build();

        System.out.println("Presione cualquier tecla para continuar...");

        // Esperar a que el usuario presione Enter
        lineReader.readLine();

        // Cierre de terminal


    }
    private void busquedaDeLibros() throws Exception {

        System.out.println("Escriba el título del libro que desea buscar: ");
        var tituloLibro = teclado.nextLine();

        var resultadoBusqueda = new ConsumoApi().buscarLibro(tituloLibro);

        JSONObject jsonObject = new JSONObject(resultadoBusqueda);
        JSONArray resultsArray = jsonObject.getJSONArray("results");

        if (resultsArray.isEmpty()) {
            System.out.println("Libro no encontrado con el título: " + tituloLibro);
            return;
        }

        System.out.println("Se encontraron " + resultsArray.length() + " libros: \n");
        for (int i = 0; i < resultsArray.length(); i++) {
            System.out.println("[" + (i + 1) + "] " + resultsArray.getJSONObject(i).getString("title"));
        }

        System.out.println("\nSeleccone el libro deseado indicando su número, o presione 0 para cancelar: ");
        var numeroLibro = teclado.nextInt();
        if (numeroLibro == 0) {
            return;
        }
        numeroLibro = numeroLibro - 1;

        jsonObject = new JSONObject(resultsArray.getJSONObject(numeroLibro).toString());
        datosLibro = convierteDatos.obtenerDatos(jsonObject.toString(), DatosLibro.class);

        //Verificar si el libro ya esta registrado
        Optional<Libro> libro = libroRepository.findById(datosLibro.idLibro());
        if (libro.isPresent()) {
            System.out.println("Libro ya esta  registrado");
            System.out.println();
            System.out.println(libro.get());
            hacerPause();
            return;
        }

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> jsonMap = mapper.readValue(jsonObject.toString(), Map.class);
        String authorsJson = mapper.writeValueAsString((List<Map<String, Object>>) jsonMap.get("authors"));
        String resultado = authorsJson.substring(1, authorsJson.length() - 1);
        datosAutor = convierteDatos.obtenerDatos(resultado, DatosAutor.class);

        String idioma = new ConsumoApi().getIdioma(jsonMap);

        autores = autorRepository.findAll();
        Optional<Autor> autor = autores.stream()
                .filter(a -> a.getNombre().equals(datosAutor.nombre()))
                .findFirst();
        if (autor.isPresent()) {
            autorDelLibro = autor.get();
        } else {
            autorDelLibro = new Autor(datosAutor);
            autorRepository.save(autorDelLibro);
        }
        Libro libroNuevo = new Libro(datosLibro, autorDelLibro, idioma);
        libroRepository.save(libroNuevo);
        System.out.println("Libro registrado exitosamente");
        System.out.println();
        System.out.println(libroNuevo);
        hacerPause();
    }


}
