package com.axity.dinosaurpark.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ParkConfig {

    private static ParkConfig instance;
    private final Properties props;

    private ParkConfig() {
        this.props = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("park.properties")) {
            if (input == null) {
                System.err.println("Advertencia: No se encontró 'park.properties'. Se usarán valores por defecto.");
            } else {
                this.props.load(input);
            }
        } catch (IOException e) {
            System.err.println("Error al cargar 'park.properties': " + e.getMessage());
        }
    }

    // punto de acceso global
    public static ParkConfig getInstance() {
        if (instance == null) {
            instance = new ParkConfig();
        }
        return instance;
    }

    // lectura cn tipado y manejo de nulos
    public String getString(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }

    public int getInt(String key, int defaultValue) {
        String value = props.getProperty(key);
        if (value == null) return defaultValue;
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public double getDouble(String key, double defaultValue) {
        String value = props.getProperty(key);
        if (value == null) return defaultValue;
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    // Métodos específicos requeridos
    public long getSeed() {
        // long por defecto típico para semillas es 42 o currentTimeMillis
        String value = props.getProperty("simulation.seed");
        if (value == null) return 42L;
        try {
            return Long.parseLong(value.trim());
        } catch (NumberFormatException e) {
            return 42L;
        }
    }

    public int getTotalSteps() {
        return getInt("simulation.totalSteps", 1000); // 1000 pasos por defecto si no existe
    }

    // solo pra tests
    static void resetForTesting() {
        instance = null;
    }
}
