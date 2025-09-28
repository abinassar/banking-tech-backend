package com.cursos.springsecuritycourse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cursos.springsecuritycourse.entity.Criptomoneda;

public interface CriptomonedaRepository extends JpaRepository<Criptomoneda, Long> {

    /**
     * Busca todas las criptomonedas asociadas a una moneda específica
     * filtrando por el código de la moneda (ej: USD, EUR).
     */
    List<Criptomoneda> findByMoneda_CodigoIgnoreCase(String codigo);

    /**
     * Verifica si existe una criptomoneda con un símbolo determinado (ej: BTC).
     */
    boolean existsBySimboloIgnoreCase(String simbolo);
}

