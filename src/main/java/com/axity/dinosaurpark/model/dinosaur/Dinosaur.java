package com.axity.dinosaurpark.model.dinosaur;

import lombok.Getter;

@Getter
public abstract class Dinosaur {
    private final int id;
    private final String name, species;
    private DinosaurStatus status;
    private final double feedingCostPerDay;

    protected Dinosaur(int id, String name, String species, double feedingCostPerDay) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.feedingCostPerDay = feedingCostPerDay;
        this.status = DinosaurStatus.IN_ENCLOSURE;
    }

    public void escape()            { this.status = DinosaurStatus.ESCAPED; }
    public void recapture()         { this.status = DinosaurStatus.RECAPTURED; }
    public void returnToEnclosure() { this.status = DinosaurStatus.IN_ENCLOSURE; }

    public abstract String getDiet();
    public abstract double getDangerLevel();
}
