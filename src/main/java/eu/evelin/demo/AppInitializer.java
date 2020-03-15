package eu.evelin.demo;

import eu.evelin.demo.dao.IngredientRepository;
import eu.evelin.demo.dao.LabelRepository;
import eu.evelin.demo.dao.ShampooRepository;
import eu.evelin.demo.entities.Ingredient;
import eu.evelin.demo.entities.Label;
import eu.evelin.demo.entities.Shampoo;
import eu.evelin.demo.entities.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AppInitializer implements ApplicationRunner {

    private final ShampooRepository shampooRepo;
    private final LabelRepository labelRepo;
    private final IngredientRepository ingredientRepo;

    @Autowired
    public AppInitializer(ShampooRepository shampooRepo, LabelRepository labelRepo, IngredientRepository ingredientRepo) {
        this.shampooRepo = shampooRepo;
        this.labelRepo = labelRepo;
        this.ingredientRepo = ingredientRepo;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        shampooRepo.findBySize(Size.MEDIUM)
                .forEach(sh -> System.out.printf("%s %s %s %.2f%n", sh.getBrand(), sh.getLabel().getTitle(), sh.getSize(), sh.getPrice()));

        Label label = labelRepo.findOneById(1);
        shampooRepo.findAllBySizeOrLabelOrderByPriceAsc(Size.MEDIUM, label)
                .forEach(sh -> System.out.printf("%s %s %s %.2f%n", sh.getBrand(), sh.getLabel().getTitle(), sh.getSize(), sh.getPrice()));

        System.out.println("A list of the shampoos with price greater than 5");
        shampooRepo.findByPriceGreaterThanOrderByPriceDesc(5)
                .forEach(sh -> System.out.printf("%s %s %s %.2f%n", sh.getBrand(), sh.getLabel().getTitle(), sh.getSize(), sh.getPrice()));

        System.out.println("A list of the shampoos by ingredients");
        shampooRepo.findWithIngredientsInList(
                List.of(ingredientRepo.findByName("Berry"), ingredientRepo.findByName("Mineral-Collagen")))
                .forEach(sh -> System.out.printf("%s %s %s %.2f %s%n",
                        sh.getBrand(), sh.getSize(), sh.getLabel().getTitle(), sh.getPrice(),
                        sh.getIngredients().stream().map(Ingredient::getName).collect(Collectors.toList())));

        System.out.println("A list of the shampoos with less than 2 ingredients");
        shampooRepo.findByCountOfIngredientsLowerThan(2)
                .forEach(sh -> System.out.printf("%s %s %s %.2f %s%n",
                        sh.getBrand(), sh.getSize(), sh.getLabel().getTitle(), sh.getPrice(),
                        sh.getIngredients().stream().map(Ingredient::getName).collect(Collectors.toList())));

        System.out.println("Update prices");
        ingredientRepo.updatePriceIngredientsInListBy10Percent(List.of("Lavender", "Herbs", "Apple"));


        Page<Shampoo> page;
        Pageable pageable = PageRequest.of(0, 5);
        do {
            page = shampooRepo.findAll(pageable);
            System.out.printf("Page %d of %d:%n------------------%n", page.getNumber() + 1, page.getTotalPages());
            page.forEach(s -> System.out.printf("%s %s %s %.2f %s%n",
                    s.getBrand(), s.getSize(), s.getLabel().getTitle(), s.getPrice(),
                    s.getIngredients().stream().map(Ingredient::getName).collect(Collectors.toList())));
            pageable = pageable.next();
        } while (page.hasNext());
    }
}

