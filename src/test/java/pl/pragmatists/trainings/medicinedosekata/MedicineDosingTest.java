package pl.pragmatists.trainings.medicinedosekata;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import pl.pragmatists.trainings.medicinedosekata.dependencies.*;

import static org.mockito.Mockito.*;
import static pl.pragmatists.trainings.medicinedosekata.dependencies.Medicine.PRESSURE_LOWERING_MEDICINE;
import static pl.pragmatists.trainings.medicinedosekata.dependencies.Medicine.PRESSURE_RAISING_MEDICINE;

public class MedicineDosingTest {

    private HealthMonitor healthMonitor = mock(HealthMonitor.class);
    private MedicinePump medicinePump = mock(MedicinePump.class);
    private AlertService alertService = mock(AlertService.class);
    private DoseController doseController;

    @Before
    public void setUp() {
        doseController = new DoseController(healthMonitor, medicinePump, alertService);
        when(medicinePump.getTimeSinceLastDoseInMinutes(any(Medicine.class))).thenReturn(Integer.MAX_VALUE);
    }

    @Test
    public void oneDoseOnLowBloodPressure() {
        when(healthMonitor.getSystolicBloodPressure()).thenReturn(89);

        doseController.checkHealthAndApplyMedicine();

        verify(medicinePump).dose(PRESSURE_RAISING_MEDICINE);
    }

    @Test
    public void twoDosesOnVeryLowBloodPressure() {
        when(healthMonitor.getSystolicBloodPressure()).thenReturn(59);

        doseController.checkHealthAndApplyMedicine();

        verify(medicinePump, times(2)).dose(PRESSURE_RAISING_MEDICINE);
    }

    @Test
    public void lowerHighBloodPressure() {
        when(healthMonitor.getSystolicBloodPressure()).thenReturn(151);

        doseController.checkHealthAndApplyMedicine();

        verify(medicinePump, times(1)).dose(PRESSURE_LOWERING_MEDICINE);
    }

    @Test
    public void noDoseOnNormalBloodPressure() {
        when(healthMonitor.getSystolicBloodPressure()).thenReturn(90);

        doseController.checkHealthAndApplyMedicine();

        verify(medicinePump, never()).dose(any(Medicine.class));
    }

    @Test
    public void retryDose() {
        when(healthMonitor.getSystolicBloodPressure()).thenReturn(89);
        doThrow(DoseUnsuccessfulException.class).doNothing().when(medicinePump).dose(any(Medicine.class));

        doseController.checkHealthAndApplyMedicine();

        verify(medicinePump, times(2)).dose(PRESSURE_RAISING_MEDICINE);
    }

    @Test
    public void doNotDoseTwoOften() {
        when(healthMonitor.getSystolicBloodPressure()).thenReturn(89);
        when(medicinePump.getTimeSinceLastDoseInMinutes(PRESSURE_RAISING_MEDICINE)).thenReturn(29);

        doseController.checkHealthAndApplyMedicine();

        verify(medicinePump, never()).dose(any(Medicine.class));
    }

    @Test
    public void alertTheDoctor() {
        when(healthMonitor.getSystolicBloodPressure()).thenReturn(54);

        doseController.checkHealthAndApplyMedicine();

        InOrder inOrder = inOrder(alertService, medicinePump);
        inOrder.verify(alertService).notifyDoctor();
        inOrder.verify(medicinePump, times(3)).dose(PRESSURE_RAISING_MEDICINE);
    }

}
