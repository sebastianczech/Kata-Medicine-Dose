package pl.pragmatists.trainings.medicinedosekata;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pragmatists.trainings.medicinedosekata.dependencies.AlertService;
import pl.pragmatists.trainings.medicinedosekata.dependencies.HealthMonitor;
import pl.pragmatists.trainings.medicinedosekata.dependencies.MedicinePump;

import static pl.pragmatists.trainings.medicinedosekata.dependencies.Medicine.PRESSURE_LOWERING_MEDICINE;
import static pl.pragmatists.trainings.medicinedosekata.dependencies.Medicine.PRESSURE_RAISING_MEDICINE;

@ExtendWith(MockitoExtension.class)
public class MedicineDosingTest {

    @InjectMocks
    DoseController doseController;

    @Mock
    private HealthMonitor healthMonitor;

    @Mock
    private MedicinePump medicinePump;

    @Mock
    private AlertService alertService;

    // Gdy ciśnienie spadnie poniżej 90, podaj 1 dawkę leku podnoszącego ciśnienie.
    @Test
    public void should_dose_medicine_when_pressure_lower_than_90() {
        Mockito.when(healthMonitor.getSystolicBloodPressure()).thenReturn(80);

        doseController.checkHealthAndApplyMedicine();

        Mockito.verify(medicinePump).dose(PRESSURE_RAISING_MEDICINE);
    }

    // Gdy ciśnienie spadnie poniżej 60, podaj 2 dawki leku podnoszącego ciśnienie.
    @Test
    public void should_two_dose_medicine_when_pressure_lower_than_60() {
        Mockito.when(healthMonitor.getSystolicBloodPressure()).thenReturn(50);

        doseController.checkHealthAndApplyMedicine();

        Mockito.verify(medicinePump,Mockito.times(2)).dose(PRESSURE_RAISING_MEDICINE);
    }

    // Gdy ciśnienie wzrośnie powyżej 150, podaj lek obniżający ciśnienie.
    @Test
    public void should_dose_medicine_when_pressure_higher_than_150() {
        Mockito.when(healthMonitor.getSystolicBloodPressure()).thenReturn(160);

        doseController.checkHealthAndApplyMedicine();

        Mockito.verify(medicinePump).dose(PRESSURE_LOWERING_MEDICINE);
    }
}
