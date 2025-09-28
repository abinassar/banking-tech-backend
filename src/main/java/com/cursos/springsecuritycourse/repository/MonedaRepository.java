package com.cursos.springsecuritycourse.repository;

import com.cursos.springsecuritycourse.entity.Moneda;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MonedaRepository extends JpaRepository<Moneda, Long> {
    Optional<Moneda> findByNombre(String monedaNombre);
    boolean existsByNombre(String monedaNombre);
    boolean existsByCodigoIgnoreCase(String monedaNombre);
    Optional<Moneda> findByCodigoIgnoreCase(String monedaCodigo);
}
