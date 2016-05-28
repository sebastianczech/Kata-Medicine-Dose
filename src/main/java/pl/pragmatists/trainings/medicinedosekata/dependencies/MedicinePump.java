package pl.pragmatists.trainings.medicinedosekata.dependencies;

public interface MedicinePump {
    void dose(Medicine medicine);

    int getTimeSinceLastDoseInMinutes(Medicine medicine);
}
