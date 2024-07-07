package com.alurachallenge.literalura.models;


public enum Idioma {

    ZH("zh", "Chino (simplificado)"),
    ZH_TW("zh-TW", "Chino (tradicional)"),
    EN("en", "Inglés"),
    FR("fr", "Francés"),
    DE("de", "Alemán"),
    HE("he", "Hebreo"),
    IW("iw", "Hebreo"),
    HI("hi", "Hindi"),
    IT("it", "Italiano"),
    JA("ja", "Japonés"),
    KO("ko", "Coreano"),
    PT("pt", "Portugués"),
    RU("ru", "Ruso"),
    ES("es", "Español"),
    CY("cy", "Galés"),
    XH("xh", "Xhosa"),
    YI("yi", "Yidis"),
    YO("yo", "Yoruba"),
    ZU("zu", "Zulú");

    private final String iniciales;
    private final String nombreCompleto;

    Idioma(String iniciales, String nombreCompleto) {
        this.iniciales = iniciales;
        this.nombreCompleto = nombreCompleto;
    }

    public static String from(String iniciales) {
        for (Idioma idioma : values()) {
            if (idioma.iniciales.equalsIgnoreCase(iniciales)) {
                return idioma.nombreCompleto;
            }
        }
        throw new IllegalArgumentException("Idioma no encontrado: " + iniciales);
    }

}
