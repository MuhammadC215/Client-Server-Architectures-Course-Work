package com.example.dao;

import com.example.model.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MockDatabase {

    public static final List<Room> ROOMS = new ArrayList<>();

    static {
        ROOMS.add(new Room("LIB-301", "Library Quiet Study", 50, List.of("S1", "S2")));
        ROOMS.add(new Room("REC-302", "Reception Entrance", 20, List.of("S3")));
        ROOMS.add(new Room("ASS-303", "Assembly Hall", 200, List.of("S4, S5")));

    }

    public static final List<Sensor> SENSORS = new ArrayList<>();

    static {
        SENSORS.add(new Sensor("S1", "Temperature", "ACTIVE", 40, "LIB-301"));
        SENSORS.add(new Sensor("S2", "Occupancy", "ACTIVE", 30, "REC-302"));
        SENSORS.add(new Sensor("S3","Temperature", "MAINTENANCE", 40, "REC-302"));
        SENSORS.add(new Sensor("S4", "Occupancy", "MAINTENANCE", 30, "ASS-303"));
        SENSORS.add(new Sensor("S5", "CO2", "OFFLINE", 20, "ASS-303"));
    }

    public static final Map<String, List<SensorReading>> READINGS = new HashMap<>();

    static {
        
        READINGS.put("S1", new ArrayList<>());
        READINGS.put("S2", new ArrayList<>());
        READINGS.put("S3", new ArrayList<>());
        READINGS.put("S4", new ArrayList<>());
        READINGS.put("S5", new ArrayList<>());


        READINGS.get("S1").add(
            new SensorReading(UUID.randomUUID().toString(),
            System.currentTimeMillis(),
            40)
        );

        READINGS.get("S2").add(
            new SensorReading(UUID.randomUUID().toString(),
            System.currentTimeMillis(),
            30)
        );
        
        READINGS.get("S4").add(
            new SensorReading(UUID.randomUUID().toString(),
                    System.currentTimeMillis(),
                30)
                );
        
        READINGS.get("S3").add(
            new SensorReading(UUID.randomUUID().toString(),
                    System.currentTimeMillis(),
                30)
                );
        
        READINGS.get("S5").add(
            new SensorReading(UUID.randomUUID().toString(),
                    System.currentTimeMillis(),
                20)
                );
    }
}
