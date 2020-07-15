package pl.pragmatists.trainings.medicinedosekata;

import pl.pragmatists.trainings.medicinedosekata.dependencies.AlertService;
import pl.pragmatists.trainings.medicinedosekata.dependencies.HealthMonitor;
import pl.pragmatists.trainings.medicinedosekata.dependencies.Medicine;
import pl.pragmatists.trainings.medicinedosekata.dependencies.MedicinePump;

public class DoseController {

    private HealthMonitor healthMonitor;
    private MedicinePump medicinePump;
    private AlertService alertService;

    public DoseController(HealthMonitor healthMonitor, MedicinePump medicinePump, AlertService alertService) {
        this.healthMonitor = healthMonitor;
        this.medicinePump = medicinePump;
        this.alertService = alertService;
    }

    public void checkHealthAndApplyMedicine() {
        if(healthMonitor.getSystolicBloodPressure()<90) {
            medicinePump.dose(Medicine.PRESSURE_RAISING_MEDICINE);
        }
        if(healthMonitor.getSystolicBloodPressure()<60) {
            medicinePump.dose(Medicine.PRESSURE_RAISING_MEDICINE);
        }
    }

}
