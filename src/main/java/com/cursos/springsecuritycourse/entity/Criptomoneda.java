package com.cursos.springsecuritycourse.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(
    name = "criptomonedas",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_crypto_simbolo", columnNames = {"simbolo"})
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Criptomoneda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Ej: BTC, ETH, USDT
    @NotBlank
    @Size(max = 20)
    @Column(nullable = false, length = 20, unique = true)
    private String simbolo;

    // Ej: Bitcoin, Ethereum, Tether
    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String nombre;

    // Relación N:1 con Moneda (ej: BTC se asocia a USD)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "moneda_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_crypto_moneda")
    )
    @JsonBackReference
    private Moneda moneda;

}
