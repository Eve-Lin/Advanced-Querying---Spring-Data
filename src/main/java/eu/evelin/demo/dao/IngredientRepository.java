package eu.evelin.demo.dao;

import eu.evelin.demo.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    Ingredient findByName(String name);

    @Modifying
    @Transactional
    @Query("UPDATE Ingredient i set i.price = i.price * 1.10 where i.name in :names")
    int updatePriceIngredientsInListBy10Percent(@Param("names") Iterable<String> names);
}
