package com.axity.dinosaurpark.model.vehicle;

import lombok.Getter;

@Getter
public class Vehicle {
    private final int id;
    private VehicleStatus status;
    private int repairCountdown;
    private final int totalRepairSteps; // pasos requeridos para repararse, leído desde ParkConfig

    public Vehicle(int id, int totalRepairSteps) {
        this.id = id;
        this.totalRepairSteps = totalRepairSteps;
        this.status = VehicleStatus.AVAILABLE; // estado init
        this.repairCountdown = 0;
    }

    public void use() {
        if (this.status == VehicleStatus.AVAILABLE) {
            this.status = VehicleStatus.IN_USE;
        }
    }

    public void free() {
        if (this.status == VehicleStatus.IN_USE) {
            this.status = VehicleStatus.AVAILABLE;
        }
    }

    public void markBroken() {
        this.status = VehicleStatus.BROKEN;
        this.repairCountdown = this.totalRepairSteps;
    }

    public void tick() {
        if (this.status == VehicleStatus.BROKEN) {
            this.repairCountdown--;
            if (this.repairCountdown <= 0) {
                this.repairCountdown = 0; // no nums negatios
                this.status = VehicleStatus.AVAILABLE;
            }
        }
    }
}
