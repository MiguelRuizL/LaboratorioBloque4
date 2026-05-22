package com.axity.dinosaurpark.model.worker;

import com.axity.dinosaurpark.model.dinosaur.Dinosaur;
import com.axity.dinosaurpark.model.dinosaur.DinosaurStatus;

import java.util.List;

public class Guard extends Worker {

    public Guard(int id, String name, double dailySalary) {
        super(id, name, dailySalary);
    }

    @Override
    public String getRole() {
        return "GUARD";
    }

    public void recaptureEscapedDinosaurs(List<Dinosaur> dinosaurs) {
        if (dinosaurs == null) return;

        // api streams
        dinosaurs.stream()
                .filter(dino -> dino.getStatus() == DinosaurStatus.ESCAPED)
                .forEach(Dinosaur::returnToEnclosure);
    }
}