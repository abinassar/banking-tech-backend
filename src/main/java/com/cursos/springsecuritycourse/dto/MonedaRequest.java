package com.cursos.springsecuritycourse.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO usado para crear o actualizar una Moneda.
 */
public record MonedaRequest(

    @NotBlank
    @Size(max = 10)
    String codigo,   // Ejemplo: USD, EUR, VES

    @NotBlank
    @Size(max = 100)
    String nombre,    // Ejemplo: Dólar Estadounidense, Euro, Bolívar

    @Valid
    List<CriptomonedaRequest> criptomonedas

) {}

