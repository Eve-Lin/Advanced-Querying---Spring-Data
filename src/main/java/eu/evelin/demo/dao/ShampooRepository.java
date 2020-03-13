package eu.evelin.demo.dao;

import eu.evelin.demo.entities.Label;
import eu.evelin.demo.entities.Shampoo;
import eu.evelin.demo.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShampooRepository extends JpaRepository<Shampoo, Long> {
List<Shampoo>  findBySize(Size size);
List<Shampoo> findAllBySizeOrLabelOrderByPriceAsc(Size size, Label label);
List<Shampoo> findByPriceGreaterThanOrderByPriceDesc(double price);
}
