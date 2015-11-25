package gov.idaho.isp.fitness.challenge.service;

import java.math.BigDecimal;

public class Totals {
  private Long totalParticipants;
  private Integer totalSitupsPushups;
  private BigDecimal totalResistance;
  private BigDecimal totalAerobicHours;
  private BigDecimal totalDistance;

  public Long getTotalParticipants() {
    return totalParticipants;
  }

  public void setTotalParticipants(Long totalParticipants) {
    this.totalParticipants = totalParticipants != null ? totalParticipants : 0;;
  }

  public Integer getTotalSitupsPushups() {
    return totalSitupsPushups;
  }

  public void setTotalSitupsPushups(Integer totalSitupsPushups) {
    this.totalSitupsPushups = totalSitupsPushups != null ? totalSitupsPushups : 0;
  }

  public BigDecimal getTotalResistance() {
    return totalResistance;
  }

  public void setTotalResistance(BigDecimal totalResistance) {
    this.totalResistance = totalResistance != null ? totalResistance : BigDecimal.ZERO;
  }

  public BigDecimal getTotalAerobicHours() {
    return totalAerobicHours;
  }

  public void setTotalAerobicHours(BigDecimal totalAerobicHours) {
    this.totalAerobicHours = totalAerobicHours != null ? totalAerobicHours : BigDecimal.ZERO;
  }

  public BigDecimal getTotalDistance() {
    return totalDistance;
  }

  public void setTotalDistance(BigDecimal totalDistance) {
    this.totalDistance = totalDistance != null ? totalDistance : BigDecimal.ZERO;
  }
}