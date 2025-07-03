package org.umm.paisesycapitalesbackend.structure.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "game_results")
public class GameResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    private String selectedCountryCode;

    private Boolean isCorrect;

    private Integer roundNumber;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
