package ca.ulaval.glo4002.warehouse.domain;

import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
public class Shipment {
  @Id
  @GeneratedValue
  private Integer id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "ingredient_id", nullable = false)
  private Ingredient ingredient;

  @Column
  private String quantity;

  @Column
  private LocalDateTime date;

  public Integer getId() {
    return id;
  }

  public String getQuantity() {
    return quantity;
  }

  public LocalDateTime getDate() {
    return date;
  }
}
