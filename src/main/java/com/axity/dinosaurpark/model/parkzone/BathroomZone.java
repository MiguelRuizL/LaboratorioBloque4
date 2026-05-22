package com.axity.dinosaurpark.model.parkzone;

import com.axity.dinosaurpark.config.ParkConfig;
import com.axity.dinosaurpark.model.tourist.Tourist;
import lombok.Getter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Getter
public class BathroomZone implements ParkZone {
    private final String name;
    private final int maxCapacity;
    private final int useDurationSteps;
    private final double spaProbability;

    // q turistas están dentro y cuántos pasos les quedan
    private final Map<Tourist, Integer> slots;

    public BathroomZone() {
        this.name = "Bathroom & Spa Zone";
        this.maxCapacity = ParkConfig.getInstance().getInt("bathroom.maxCapacity", 10);
        this.useDurationSteps = ParkConfig.getInstance().getInt("bathroom.useDurationSteps", 3);
        this.spaProbability = ParkConfig.getInstance().getDouble("bathroom.spaPurchaseProbability", 0.2);
        this.slots = new HashMap<>();
    }

    @Override
    public boolean hasCapacity() {
        return slots.size() < maxCapacity;
    }

    @Override
    public int getCurrentOccupancy() {
        return slots.size();
    }

    @Override
    public void enter(Tourist tourist) {
        if (hasCapacity()) {
            slots.put(tourist, useDurationSteps);
            tourist.recordVisit(this.name);
        }
    }

    @Override
    public void exit(Tourist tourist) {
        slots.remove(tourist);
    }

    // true si el turista pudo entrar e intenta vender el servicio VIP de SPA
    public boolean tryEnter(Tourist tourist, Random random, Object databaseService) {
        if (!hasCapacity()) return false;
        double spaCost = ParkConfig.getInstance().getDouble("bathroom.spaPrice", 20.0);

        enter(tourist);

        //servicio spa
        if (random.nextDouble() < spaProbability) {
            tourist.spend(spaCost);
            // registra servicio en dbserv
        }
        return true;
    }

    // tiempo-reduce los turnos de todos los que están adentro
    public void tick(Object databaseService) {
        slots.replaceAll((tourist, stepsLeft) -> stepsLeft - 1);

        // expulsa turistas de tiempo0
        slots.entrySet().removeIf(entry -> {
            if (entry.getValue() <= 0) {
                // guardarlog de salida en dbserv
                return true;
            }
            return false;
        });
    }
}
