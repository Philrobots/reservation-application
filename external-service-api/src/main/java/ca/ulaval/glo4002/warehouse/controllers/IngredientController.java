package ca.ulaval.glo4002.warehouse.controllers;

import ca.ulaval.glo4002.warehouse.domain.Ingredient;
import ca.ulaval.glo4002.warehouse.repositories.IngredientRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RepositoryRestController
@RequestMapping("/ingredients")
public class IngredientController {

  private final IngredientRepository repository;

  @Autowired
  public IngredientController(IngredientRepository repository) {
    this.repository = repository;
  }

  @GetMapping(value = "", produces = { "application/json" })
  public @ResponseBody List<Ingredient> getIngredients() {
    return repository.findAll();
  }
}
