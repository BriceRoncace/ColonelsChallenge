package gov.idaho.isp.fitness.challenge.task;

import gov.idaho.isp.fitness.challenge.service.EmployeeSyncService;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class EmployeeSycnTask implements Task {
  @Inject private EmployeeSyncService employeeSyncService;

  @Override
  public void executeTask() {
    employeeSyncService.syncEmployees();
  }

  public void setEmployeeSyncService(EmployeeSyncService employeeSyncService) {
    this.employeeSyncService = employeeSyncService;
  }
}