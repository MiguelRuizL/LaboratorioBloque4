package com.axity.dinosaurpark.model.parkzone;

import com.axity.dinosaurpark.config.ParkConfig;
import com.axity.dinosaurpark.model.tourist.Tourist;
import lombok.Getter;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Getter
public class CentralHub implements ParkZone {
    private final String name;
    private final int maxCapacity;
    private final Set<Tourist> currentTourists;
    private final double souvenirProbability;

    public CentralHub(int maxCapacity) {
        this.name = "Central Hub";
        this.maxCapacity = maxCapacity;
        this.souvenirProbability = ParkConfig.getInstance().getDouble("hub.souvenirPurchaseProbability", 0.4);
        this.currentTourists = new HashSet<>();
    }

    @Override
    public boolean hasCapacity() {
        return currentTourists.size() < maxCapacity;
    }

    @Override
    public int getCurrentOccupancy() {
        return currentTourists.size();
    }

    @Override
    public void enter(Tourist tourist) {
        if (hasCapacity()) {
            currentTourists.add(tourist);
            tourist.recordVisit(this.name);
        }
    }

    @Override
    public void exit(Tourist tourist) {
        currentTourists.remove(tourist);
    }

    // intermedio - venta de souvenir con prob y descuento
    public void visit(Tourist tourist, Random random, double discount, Object databaseService) {
        if (random.nextDouble() < souvenirProbability) {
            double baseSouvenirPrice = ParkConfig.getInstance().getDouble("hub.souvenirPrice", 25);
            double finalPrice = baseSouvenirPrice * (1.0 - discount);

            tourist.spend(finalPrice);
            //registrar evento comercial en databasesrvice
        }
    }
}