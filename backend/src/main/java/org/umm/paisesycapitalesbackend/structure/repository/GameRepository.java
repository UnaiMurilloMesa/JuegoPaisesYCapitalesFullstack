package org.umm.paisesycapitalesbackend.structure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.umm.paisesycapitalesbackend.structure.model.Game;

public interface GameRepository extends JpaRepository<Game, Long> {
}
