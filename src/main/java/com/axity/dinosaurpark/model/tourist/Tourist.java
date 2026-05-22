package com.axity.dinosaurpark.model.tourist;

import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Tourist {
    private final int id;
    private final String name;

    @Setter // solo este es modificable desde afuera
    private TouristStatus status;

    private double moneySpent;
    private final List<String> visitedZones;

    public Tourist(int id, String name) {
        this.id = id;
        this.name = name;
        this.status = TouristStatus.WAITING;
        this.moneySpent = 0.0;
        this.visitedZones = new ArrayList<>();
    }

    public void spend(double amount) {
        if (amount > 0) this.moneySpent += amount;
    }

    public void recordVisit(String zoneId) {
        if (zoneId != null && !zoneId.isBlank()) this.visitedZones.add(zoneId);
    }
}
