package pl.pragmatists.trainings.medicinedosekata.dependencies;

public interface MedicinePump {
    void dose(Medicine pressureRaisingMedicine);

    int getTimeSinceLastDoseInMinutes(Medicine medicine);
}
