package com.axity.dinosaurpark.model.worker;

import com.axity.dinosaurpark.model.parkzone.PowerPlant;
import com.axity.dinosaurpark.model.vehicle.Vehicle;
import com.axity.dinosaurpark.model.vehicle.VehicleStatus;

import java.util.List;
import java.util.Optional;

public class Technician extends Worker {

    public Technician(int id, String name, double dailySalary) {
        super(id, name, dailySalary);
    }

    @Override
    public String getRole() {
        return "TECHNICIAN";
    }

    // Nivel Intermedio: Requiere vehículo para reparar
    public void repairIfNeeded(PowerPlant plant, List<Vehicle> vehicles) {
        if (plant == null || plant.isOperational()) {
            return;
        }

        if (vehicles == null) {
            return;
        }

        // search primer carro disponible
        Optional<Vehicle> availableVehicle = vehicles.stream()
                .filter(v -> v.getStatus() == VehicleStatus.AVAILABLE)
                .findFirst();

        if (availableVehicle.isPresent()) {
            Vehicle vehicle = availableVehicle.get();

            vehicle.use();  // marca como IN_USE
            plant.repair();
            vehicle.free(); // devuelve a AVAILABLE
        }
    }
}
