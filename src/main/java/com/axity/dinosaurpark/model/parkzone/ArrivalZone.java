package com.axity.dinosaurpark.model.parkzone;

import com.axity.dinosaurpark.config.ParkConfig;
import com.axity.dinosaurpark.model.tourist.Tourist;
import com.axity.dinosaurpark.model.tourist.TouristStatus;
import lombok.Getter;
import java.util.LinkedList;
import java.util.Queue;

@Getter
public class ArrivalZone implements ParkZone {
    private final String name;
    private final int maxCapacity;
    private final Queue<Tourist> waitingQueue;

    public ArrivalZone() {
        this.name = "Arrival Zone";
        this.maxCapacity = ParkConfig.getInstance().getInt("arrival.maxCapacity", 30);
        this.waitingQueue = new LinkedList<>();
    }

    @Override
    public boolean hasCapacity() {
        return waitingQueue.size() < maxCapacity;
    }

    @Override
    public int getCurrentOccupancy() {
        return waitingQueue.size();
    }

    @Override
    public void enter(Tourist tourist) {
        if (hasCapacity() && tourist.getStatus() == TouristStatus.WAITING) {
            waitingQueue.add(tourist);
        }
    }

    @Override
    public void exit(Tourist tourist) {
        waitingQueue.remove(tourist);
    }

    // intermedio - Procesar un lote de entrada aplicando descuento de DealsHour
    public void processBatch(int batchSize, double discount, Object databaseService) {
        int processed = 0;
        double baseTicketPrice = ParkConfig.getInstance().getDouble("arrival.ticketPrice", 20);
        double finalPrice = baseTicketPrice * (1.0 - discount);

        while (processed < batchSize && !waitingQueue.isEmpty()) {
            Tourist tourist = waitingQueue.poll();
            if (tourist != null) {
                tourist.setStatus(TouristStatus.IN_PARK);
                tourist.spend(finalPrice);

                // aqui registrar venta en bd
                processed++;
            }
        }
    }
}
