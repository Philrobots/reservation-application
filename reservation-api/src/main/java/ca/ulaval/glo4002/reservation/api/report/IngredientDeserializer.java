package ca.ulaval.glo4002.reservation.api.report;

import java.io.IOException;
import java.math.BigDecimal;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import ca.ulaval.glo4002.reservation.infra.report.IngredientPriceDto;

public class IngredientDeserializer extends StdDeserializer<IngredientPriceDto> {
  private static final String NAME = "name";
  private static final String PRICE_PER_KG = "pricePerKg";

  public IngredientDeserializer(Class ingredientPriceDtoClass) {
    super(ingredientPriceDtoClass);
  }

  @Override
  public IngredientPriceDto deserialize(JsonParser jsonParser,
                                        DeserializationContext deserializationContext)
    throws IOException
  {
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);
    String ingredientName = node.get(NAME).asText();
    BigDecimal pricePerKg = BigDecimal.valueOf((double) node.get(PRICE_PER_KG).numberValue());

    return new IngredientPriceDto(ingredientName, pricePerKg);
  }
}
