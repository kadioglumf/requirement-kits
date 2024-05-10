package com.specification.demo.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transportation_request")
@ToString(callSuper = true)
public class TransportationRequestModel extends AbstractModel {

  @Column(nullable = false, unique = true)
  private String transportationRequestId;

  private int numberOfSales;
  private Boolean isApproved;

  @OneToMany(mappedBy = "transportationRequest", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<TransportationOrderModel> orders = new ArrayList<>();
}
