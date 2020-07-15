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
        doseController.checkHealthAndApplyMedicine();

        Mockito.verify(medicinePump).dose(PRESSURE_RAISING_MEDICINE);
    }

}
