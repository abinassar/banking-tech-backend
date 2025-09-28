package com.cursos.springsecuritycourse.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cursos.springsecuritycourse.dto.CriptomonedaRequest;
import com.cursos.springsecuritycourse.entity.Criptomoneda;
import com.cursos.springsecuritycourse.repository.CriptomonedaRepository;
import com.cursos.springsecuritycourse.repository.MonedaRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/criptomoneda")
@RequiredArgsConstructor
public class CriptomonedaController {

    private final CriptomonedaRepository criptomonedaRepository;
    private final MonedaRepository monedaRepository;

    // GET /criptomoneda
    @GetMapping
    public List<Criptomoneda> listarCriptomonedas(@RequestParam(required = false) String moneda) {
        if (moneda != null) {
            return criptomonedaRepository.findByMoneda_CodigoIgnoreCase(moneda);
        }
        return criptomonedaRepository.findAll();
    }

    // POST /criptomonedas
    @PostMapping
    public ResponseEntity<Criptomoneda> crearCriptomoneda(@Valid @RequestBody CriptomonedaRequest req) {
        var moneda = monedaRepository.findByCodigoIgnoreCase(req.monedaCodigo())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Moneda no encontrada"));
        var crypto = Criptomoneda.builder()
                .simbolo(req.simbolo())
                .nombre(req.nombre())
                .moneda(moneda)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(criptomonedaRepository.save(crypto));
    }

    // PUT /criptomonedas/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Criptomoneda> actualizarCriptomoneda(
            @PathVariable Long id, @Valid @RequestBody CriptomonedaRequest req) {

        var crypto = criptomonedaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Criptomoneda no encontrada"));
        var moneda = monedaRepository.findByCodigoIgnoreCase(req.monedaCodigo())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Moneda no encontrada"));

        crypto.setSimbolo(req.simbolo());
        crypto.setNombre(req.nombre());
        crypto.setMoneda(moneda);

        return ResponseEntity.ok(criptomonedaRepository.save(crypto));
    }
}
