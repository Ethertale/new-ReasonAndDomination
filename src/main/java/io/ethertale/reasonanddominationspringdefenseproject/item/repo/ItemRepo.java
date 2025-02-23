package io.ethertale.reasonanddominationspringdefenseproject.item.repo;

import io.ethertale.reasonanddominationspringdefenseproject.item.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepo extends JpaRepository<Item, Long> {
    boolean existsByName(String name);

    Item findByName(String title);

    Item findBySlug(String title);
}
