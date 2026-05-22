package com.axity.dinosaurpark.model.worker;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class Worker {
    private final int id;
    private final String name;
    private final double dailySalary;

    public abstract String getRole(); // "GUARD" o "TECHNICIAN"
}
