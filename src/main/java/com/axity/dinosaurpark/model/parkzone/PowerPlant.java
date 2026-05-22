package com.axity.dinosaurpark.model.parkzone;

import com.axity.dinosaurpark.config.ParkConfig;
import com.axity.dinosaurpark.model.tourist.Tourist;
import lombok.Getter;
import java.util.Random;

@Getter
public class PowerPlant implements ParkZone {
    private final String name;
    private final int maxCapacity; // Usualmente 0 para turistas, pero requerido por la interfaz
    private boolean operational;
    private final double failureProbability;
    private int stepsSinceFailure;

    private final ParkConfig parkConfig = ParkConfig.getInstance();

    public PowerPlant() {
        this.name = "Power Plant";
        this.maxCapacity = 0;
        this.operational = true;
        this.failureProbability = parkConfig.getDouble("powerplant.failureProbability", 0.05);
        this.stepsSinceFailure = 0;
    }

    @Override public boolean hasCapacity() { return false; }
    @Override public int getCurrentOccupancy() { return 0; }
    @Override public void enter(Tourist tourist) { /* no se permite entrada */ }
    @Override public void exit(Tourist tourist) { /* no se permite salida */ }

    // simula tiempo y ida de luz
    public void tick(Random random, Object databaseService) {
        if (operational) {
            if (random.nextDouble() < failureProbability) {
                triggerFailure(databaseService);
            }
        } else {
            stepsSinceFailure++;
            // agregar en dbserv log de q se cayó la planta
        }
    }

    public void triggerFailure(Object databaseService) {
        this.operational = false;
        this.stepsSinceFailure = 0;
        System.err.println("¡ALERTA!: La Planta Eléctrica ha fallado.");
        // log en bd
    }

    public void repair() {
        this.operational = true;
        this.stepsSinceFailure = 0;
        System.out.println("SISTEMA: La Planta Eléctrica ha sido reparada con éxito.");
    }
}
