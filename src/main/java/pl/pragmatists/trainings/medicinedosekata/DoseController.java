package pl.pragmatists.trainings.medicinedosekata;

import pl.pragmatists.trainings.medicinedosekata.dependencies.AlertService;
import pl.pragmatists.trainings.medicinedosekata.dependencies.HealthMonitor;
import pl.pragmatists.trainings.medicinedosekata.dependencies.MedicinePump;

import static pl.pragmatists.trainings.medicinedosekata.dependencies.Medicine.PRESSURE_LOWERING_MEDICINE;
import static pl.pragmatists.trainings.medicinedosekata.dependencies.Medicine.PRESSURE_RAISING_MEDICINE;

public class DoseController {
    private final HealthMonitor healthMonitor;
    private final MedicinePump medicinePump;
    private final AlertService alertService;

    public DoseController(HealthMonitor healthMonitor, MedicinePump medicinePump, AlertService alertService) {
        this.healthMonitor = healthMonitor;
        this.medicinePump = medicinePump;
        this.alertService = alertService;
    }

    public void checkHealthAndApplyMedicine() {
        if (healthMonitor.getSystolicBloodPressure() < 55) {
            alertService.notifyDoctor();
            dosePressureRaisingMedicine();
        }
        if (healthMonitor.getSystolicBloodPressure() < 90) {
            dosePressureRaisingMedicine();
        }
        if (healthMonitor.getSystolicBloodPressure() < 60) {
            dosePressureRaisingMedicine();
        }
        if (healthMonitor.getSystolicBloodPressure() > 150) {
            medicinePump.dose(PRESSURE_LOWERING_MEDICINE);
        }
    }

    private void dosePressureRaisingMedicine() {
        try {
            if (medicinePump.getTimeSinceLastDoseInMinutes(PRESSURE_RAISING_MEDICINE) >= 30) {
                medicinePump.dose(PRESSURE_RAISING_MEDICINE);
            }
        } catch (Exception e) {
            medicinePump.dose(PRESSURE_RAISING_MEDICINE);
        }

    }
}
