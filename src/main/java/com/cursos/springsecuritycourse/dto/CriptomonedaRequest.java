package com.cursos.springsecuritycourse.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO usado para crear o actualizar una Criptomoneda.
 */
public record CriptomonedaRequest(

    @NotBlank
    @Size(max = 20)
    String simbolo,   // Ejemplo: BTC, ETH, USDT

    @NotBlank
    @Size(max = 100)
    String nombre,    // Ejemplo: Bitcoin, Ethereum, Tether

    @NotBlank
    @Size(max = 10)
    String monedaCodigo // Ejemplo: USD, EUR (código de la Moneda asociada)
) {}
