package io.ethertale.reasonanddominationspringdefenseproject.dungeon.repo;

import io.ethertale.reasonanddominationspringdefenseproject.dungeon.model.Dungeon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DungeonRepo extends JpaRepository<Dungeon, Integer> {
}
