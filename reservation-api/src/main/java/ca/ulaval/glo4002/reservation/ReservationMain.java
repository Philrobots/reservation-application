package ca.ulaval.glo4002.reservation;

public class ReservationMain implements Runnable {

  private ReservationContext context;

  public static void main(String[] args) {
    ReservationMain main = new ReservationMain();
    main.run();
  }

  public void run() {
    context = new ReservationContext();
    context.start();
  }
}
