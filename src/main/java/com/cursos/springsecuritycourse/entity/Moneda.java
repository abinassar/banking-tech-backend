package com.cursos.springsecuritycourse.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(
    name = "monedas",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_moneda_codigo", columnNames = {"codigo"})
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Moneda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Ej: USD, EUR, VES
    @NotBlank
    @Size(max = 10)
    @Column(nullable = false, length = 10, unique = true)
    private String codigo;

    // Ej: Dólar Estadounidense, Euro, Bolívar
    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String nombre; 
    
    @OneToMany(mappedBy = "moneda", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Criptomoneda> criptomonedas = new ArrayList<>();

}

