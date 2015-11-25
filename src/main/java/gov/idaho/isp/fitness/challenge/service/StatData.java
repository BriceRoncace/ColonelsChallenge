package gov.idaho.isp.fitness.challenge.service;

import gov.idaho.isp.fitness.challenge.ChallengeYear;
import java.math.BigDecimal;

public class StatData {
  private ChallengeYear challengeYear;
  private String label;
  private Long participantCount;
  private Long totalCount;
  private BigDecimal participationRate;
  private BigDecimal meanSitupsPushups;
  private BigDecimal medianSitupsPushups;
  private BigDecimal meanResistance;
  private BigDecimal medianResistance;
  private BigDecimal meanAerobic;
  private BigDecimal medianAerobic;
  private BigDecimal meanDistance;
  private BigDecimal medianDistance;

  public StatData() {}

  public StatData(String label) {
    this.label = label;
  }

  public ChallengeYear getChallengeYear() {
    return challengeYear;
  }

  public void setChallengeYear(ChallengeYear challengeYear) {
    this.challengeYear = challengeYear;
  }

  public Long getParticipantCount() {
    return participantCount;
  }

  public void setParticipantCount(Long participantCount) {
    this.participantCount = participantCount;
  }

  public Long getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(Long totalCount) {
    this.totalCount = totalCount;
  }

  public BigDecimal getParticipationRate() {
    return participationRate;
  }

  public void setParticipationRate(BigDecimal participationRate) {
    this.participationRate = participationRate;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public BigDecimal getMeanSitupsPushups() {
    return meanSitupsPushups;
  }

  public void setMeanSitupsPushups(BigDecimal meanSitupsPushups) {
    this.meanSitupsPushups = meanSitupsPushups;
  }

  public BigDecimal getMedianSitupsPushups() {
    return medianSitupsPushups;
  }

  public void setMedianSitupsPushups(BigDecimal medianSitupsPushups) {
    this.medianSitupsPushups = medianSitupsPushups;
  }

  public BigDecimal getMeanResistance() {
    return meanResistance;
  }

  public void setMeanResistance(BigDecimal meanResistance) {
    this.meanResistance = meanResistance;
  }

  public BigDecimal getMedianResistance() {
    return medianResistance;
  }

  public void setMedianResistance(BigDecimal medianResistance) {
    this.medianResistance = medianResistance;
  }

  public BigDecimal getMeanAerobic() {
    return meanAerobic;
  }

  public void setMeanAerobic(BigDecimal meanAerobic) {
    this.meanAerobic = meanAerobic;
  }

  public BigDecimal getMedianAerobic() {
    return medianAerobic;
  }

  public void setMedianAerobic(BigDecimal medianAerobic) {
    this.medianAerobic = medianAerobic;
  }

  public BigDecimal getMeanDistance() {
    return meanDistance;
  }

  public void setMeanDistance(BigDecimal meanDistance) {
    this.meanDistance = meanDistance;
  }

  public BigDecimal getMedianDistance() {
    return medianDistance;
  }

  public void setMedianDistance(BigDecimal medianDistance) {
    this.medianDistance = medianDistance;
  }
}