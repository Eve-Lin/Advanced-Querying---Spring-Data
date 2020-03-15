package eu.evelin.demo.dao;

import eu.evelin.demo.entities.Ingredient;
import eu.evelin.demo.entities.Label;
import eu.evelin.demo.entities.Shampoo;
import eu.evelin.demo.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShampooRepository extends JpaRepository<Shampoo, Long> {
List<Shampoo>  findBySize(Size size);
List<Shampoo> findAllBySizeOrLabelOrderByPriceAsc(Size size, Label label);
List<Shampoo> findByPriceGreaterThanOrderByPriceDesc(double price);
@Query("SELECT s FROM Shampoo s JOIN s.ingredients i WHERE i in :ingredients")
List<Shampoo> findWithIngredientsInList(@Param(value="ingredients") Iterable<Ingredient> ingredients);

@Query("SELECT s FROM Shampoo  s WHERE s.ingredients.size <= :count")
List<Shampoo> findByCountOfIngredientsLowerThan(@Param("count") int count);

}
