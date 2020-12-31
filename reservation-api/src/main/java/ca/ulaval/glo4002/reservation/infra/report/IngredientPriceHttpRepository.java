package ca.ulaval.glo4002.reservation.infra.report;

import java.io.IOException;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import ca.ulaval.glo4002.reservation.api.report.IngredientDeserializer;
import ca.ulaval.glo4002.reservation.domain.report.IngredientPriceRepository;

public class IngredientPriceHttpRepository implements IngredientPriceRepository {
  private static final String INGREDIENTS_URL = "http://localhost:8080/ingredients";
  private static final URI INGREDIENTS_URI = URI.create(INGREDIENTS_URL);

  private final ObjectMapper mapper;

  public IngredientPriceHttpRepository() {
    mapper = new ObjectMapper();
    SimpleModule simpleModule = new SimpleModule();
    simpleModule.addDeserializer(IngredientPriceDto.class,
                                 new IngredientDeserializer(IngredientPriceDto.class));
    mapper.registerModule(simpleModule);
  }

  public List<IngredientPriceDto> getIngredientsPrice() {
    try {
      String ingredientsJson = getIngredientsStringifiedJson();
      return mapper.readValue(ingredientsJson,
                              mapper.getTypeFactory()
                                    .constructCollectionType(List.class, IngredientPriceDto.class));
    } catch (Exception e) {
      System.out.println("error while getting ingredients information from external service api");
      return List.of();
    }
  }

  private String getIngredientsStringifiedJson() throws IOException, InterruptedException {
    HttpRequest request = HttpRequest.newBuilder().uri(INGREDIENTS_URI).GET().build();

    HttpResponse<String> response = HttpClient.newBuilder()
                                              .proxy(ProxySelector.getDefault())
                                              .build()
                                              .send(request, HttpResponse.BodyHandlers.ofString());
    return response.body();
  }

}
