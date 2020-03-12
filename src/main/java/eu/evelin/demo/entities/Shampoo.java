package eu.evelin.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

import java.util.Set;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REFRESH;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="shampoos")
public class Shampoo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String brand;
    private double price;
    @Enumerated(EnumType.ORDINAL)
    private Size size;
    @ManyToOne(optional = true, cascade = {PERSIST, REFRESH})
            @JoinColumn(name = "label", referencedColumnName = "id")
    private Label label;
    @ManyToMany(cascade={PERSIST, REFRESH},fetch = FetchType.EAGER)
    @JoinTable(
            joinColumns =  @JoinColumn(name="shampoo_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id", referencedColumnName = "id"))
            private Set<Ingredient> ingredients;



}
