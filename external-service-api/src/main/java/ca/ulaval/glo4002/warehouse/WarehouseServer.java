package ca.ulaval.glo4002.warehouse;

import org.springframework.boot.SpringApplication;

public class WarehouseServer implements Runnable {
  private String[] args;

  public static void main(String[] args) {
    new WarehouseServer(args).run();
  }

  public WarehouseServer(String[] args) {
    this.args = args;
  }

  @Override
  public void run() {
    SpringApplication.run(WarehouseSpringApplication.class, args);
  }
}
