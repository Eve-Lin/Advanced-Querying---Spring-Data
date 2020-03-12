package eu.evelin.demo;

import eu.evelin.demo.dao.IngredientRepository;
import eu.evelin.demo.dao.LabelRepository;
import eu.evelin.demo.dao.ShampooRepository;
import eu.evelin.demo.entities.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

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
        .forEach(sh -> System.out.printf("%s %s %s %.2f%n",sh.getBrand(), sh.getLabel().getTitle(), sh.getSize(),sh.getPrice()));
    }
}
