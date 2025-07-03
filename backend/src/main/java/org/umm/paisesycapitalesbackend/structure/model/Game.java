package org.umm.paisesycapitalesbackend.structure.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.umm.paisesycapitalesbackend.structure.model.enums.Continent;
import org.umm.paisesycapitalesbackend.structure.model.enums.GameStatus;
import org.umm.paisesycapitalesbackend.structure.model.enums.Language;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Language language;

    @Enumerated(EnumType.STRING)
    private Continent continent;

    private Integer totalRounds;
    private Integer currentRound;
    private Integer correctAnswers;
    private Integer incorrectAnswers;

    @Enumerated(EnumType.STRING)
    private GameStatus status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private List<GameResult> results = new ArrayList<>();
}