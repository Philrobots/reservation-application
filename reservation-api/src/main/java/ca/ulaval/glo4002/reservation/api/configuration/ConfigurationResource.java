package ca.ulaval.glo4002.reservation.api.configuration;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ca.ulaval.glo4002.reservation.api.configuration.dto.CreateConfigurationRequestDto;
import ca.ulaval.glo4002.reservation.api.configuration.validator.ConfigurationDateFormatValidator;
import ca.ulaval.glo4002.reservation.service.RestaurantService;

@Path("/configuration")
public class ConfigurationResource {
  private final RestaurantService restaurantService;
  private final ConfigurationDateFormatValidator configurationDateFormatValidator;

  public ConfigurationResource(RestaurantService restaurantService,
                               ConfigurationDateFormatValidator configurationDateFormatValidator)
  {
    this.restaurantService = restaurantService;
    this.configurationDateFormatValidator = configurationDateFormatValidator;
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response createConfiguration(@Valid CreateConfigurationRequestDto createConfigurationRequestDto) {
    configurationDateFormatValidator.validateFormat(createConfigurationRequestDto.getDates());
    restaurantService.configureHoppeningEvent(createConfigurationRequestDto);
    return Response.ok().build();
  }
}
