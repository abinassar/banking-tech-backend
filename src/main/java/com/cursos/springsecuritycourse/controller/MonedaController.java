package com.cursos.springsecuritycourse.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cursos.springsecuritycourse.dto.MonedaRequest;
import com.cursos.springsecuritycourse.entity.Moneda;
import com.cursos.springsecuritycourse.repository.MonedaRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/moneda")
@RequiredArgsConstructor
public class MonedaController {

    private final MonedaRepository monedaRepository;

    @GetMapping
    public List<Moneda> listarMonedas() {
        return monedaRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Moneda> crearMoneda(@Valid @RequestBody MonedaRequest req) {
        if (monedaRepository.existsByCodigoIgnoreCase(req.codigo())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Moneda moneda = Moneda.builder()
                .codigo(req.codigo())
                .nombre(req.nombre())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(monedaRepository.save(moneda));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Moneda> actualizarMoneda(
            @PathVariable Long id,
            @Valid @RequestBody MonedaRequest req) {

        Moneda moneda = monedaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Moneda no encontrada"));

        moneda.setCodigo(req.codigo());
        moneda.setNombre(req.nombre());

        return ResponseEntity.ok(monedaRepository.save(moneda));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMoneda(@PathVariable Long id) {
        Moneda moneda = monedaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Moneda no encontrada"));

        monedaRepository.delete(moneda);
        return ResponseEntity.noContent().build();
    }

}

