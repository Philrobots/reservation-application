package ca.ulaval.glo4002.reservation.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.servlet.ServletContainer;

import ca.ulaval.glo4002.reservation.api.mappers.*;

public class ReservationServer {
  private Server server;

  public ReservationServer(int port, Object[] resources) {
    server = buildServer(port, resources);
  }

  private Server buildServer(int port, Object[] resources) {
    server = new Server(port);
    ServletContextHandler contextHandler = new ServletContextHandler(server, "/");
    ResourceConfig packageConfig = createResourceConfig(resources);
    ServletContainer container = new ServletContainer(packageConfig);
    ServletHolder servletHolder = new ServletHolder(container);

    contextHandler.addServlet(servletHolder, "/*");

    return server;
  }

  private ResourceConfig createResourceConfig(Object[] resources) {
    ResourceConfig packageConfig = new ResourceConfig();
    for (Object resource : resources) {
      packageConfig.register(resource);
    }
    packageConfig.register(new CatchInvalidRequestFormatMapper());
    packageConfig.register(new CatchInvalidReservationRequestMapper());
    packageConfig.register(new CatchJsonMappingExceptionMapper());
    packageConfig.register(new CatchNotFoundExceptionMapper());
    packageConfig.register(new CatchInvalidConfigurationRequestMapper());
    packageConfig.register(new CatchInvalidFormatExceptionMapper());
    packageConfig.register(new CatchInvalidRestrictionTypeExceptionMapper());
    packageConfig.property(ServerProperties.LOCATION_HEADER_RELATIVE_URI_RESOLUTION_DISABLED, true);
    return packageConfig;
  }

  public void start() {
    try {
      server.start();
      server.join();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (server.isRunning()) {
        server.destroy();
      }
    }
  }
}
