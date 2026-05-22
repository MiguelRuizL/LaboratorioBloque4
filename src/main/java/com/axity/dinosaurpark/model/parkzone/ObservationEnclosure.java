package com.axity.dinosaurpark.model.parkzone;
import com.axity.dinosaurpark.model.SatisfactionSurvey;
import com.axity.dinosaurpark.model.tourist.Tourist;
import lombok.Getter;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Getter
public class ObservationEnclosure implements ParkZone {
    private final String name;
    private final int maxCapacity;
    private final ExperienceType experienceType;
    private final Set<Tourist> currentTourists;

    public ObservationEnclosure(String name, int maxCapacity, ExperienceType experienceType) {
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.experienceType = experienceType;
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

    // turista visita encierro, paga según la categoría y genera una encuesta
    public void visit(Tourist tourist, Random random, Object databaseService) {
        if (!hasCapacity()) return;

        enter(tourist);

        // Determinación de precio por tipo de experiencia
        double cost = switch (experienceType) {
            case BASIC -> 10.0;
            case PREMIUM -> 25.0;
            case VIP -> 50.0;
        };

        tourist.spend(cost);
        // Registrar el gasto/ticket en databaseService

        // realiza encuesta
        SatisfactionSurvey survey = conductSurvey(tourist, random);
        // registrar en bdserv con record
    }

    public SatisfactionSurvey conductSurvey(Tourist tourist, Random random) {
        int score = switch (experienceType) {
            case BASIC -> random.nextInt(3) + 1;    // 1 a 3 (0,1,2 + 1)
            case PREMIUM -> random.nextInt(3) + 2;  // 2 a 4 (0,1,2 + 2)
            case VIP -> random.nextInt(3) + 3;      // 3 a 5 (0,1,2 + 3)
        };

        return new SatisfactionSurvey(tourist.getId(), this.name, score);
    }
}
