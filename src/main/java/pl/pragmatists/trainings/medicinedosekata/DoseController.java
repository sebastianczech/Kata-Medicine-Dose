package pl.pragmatists.trainings.medicinedosekata;

import pl.pragmatists.trainings.medicinedosekata.dependencies.*;

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
        doseMedicine(Medicine.PRESSURE_RAISING_MEDICINE);
    }

    private void doseLowPressure() {
        doseMedicine(Medicine.PRESSURE_LOWERING_MEDICINE);
    }

    private void doseMedicine(Medicine medicine) {
        try {
            medicinePump.dose(medicine);
        } catch (DoseUnsuccessfulException doseUnsuccessfulException) {
            medicinePump.dose(medicine);
        }
    }

}
