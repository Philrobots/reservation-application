package ca.ulaval.glo4002.warehouse.domain;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Ingredient {
  @Id
  @GeneratedValue
  private Integer id;

  @Column
  private String name;

  @Column(name = "price_per_kg")
  private Float pricePerKg;

  @Column
  private String origin;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "ingredient")
  private List<Shipment> shipments;

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Float getPricePerKg() {
    return pricePerKg;
  }

  public String getOrigin() {
    return origin;
  }

  public List<Shipment> getShipments() {
    return shipments;
  }
}
