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
        if(isPressureLess90()) {
            doseRaisePressure();
        }
        if(isPressureLess60()) {
            doseRaisePressure();
        }
        if(isPressureOver150()) {
            doseLowPressure();
        }
    }

    private boolean isPressureLess60() {
        return healthMonitor.getSystolicBloodPressure()<60;
    }

    private boolean isPressureLess90() {
        return healthMonitor.getSystolicBloodPressure()<90;
    }

    private boolean isPressureOver150() {
        return healthMonitor.getSystolicBloodPressure()>150;
    }

    private void doseRaisePressure() {
        medicinePump.dose(Medicine.PRESSURE_RAISING_MEDICINE);
    }

    private void doseLowPressure() {
        medicinePump.dose(Medicine.PRESSURE_LOWERING_MEDICINE);
    }


}
