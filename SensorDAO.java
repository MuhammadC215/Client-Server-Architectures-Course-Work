package com.example.dao;

import com.example.model.Room;
import com.example.model.Sensor;
import java.util.List;

public class SensorDAO {

    private final List<Sensor> items = MockDatabase.SENSORS;

    public void add(Sensor sensor, List<Room> rooms) {

        boolean roomExists = rooms.stream()
                .anyMatch(r -> r.getId().equals(sensor.getRoomId()));

        if (!roomExists) {
            throw new IllegalArgumentException("Room does not exist");
        }

        int maxId = 0;

        for (Sensor s : items) {

            String id = s.getId();

            if (id != null && id.startsWith("s")) {
                try {
                    int num = Integer.parseInt(id.substring(1));
                    maxId = Math.max(maxId, num);
                } catch (Exception ignored) {
                }
            }
        }

        sensor.setId("s" + (maxId + 1));

        items.add(sensor);
    }

    public List<Sensor> getAll() {
        return items;
    }

    public List<Sensor> getByType(String type) {

        List<Sensor> result = new java.util.ArrayList<>();

        for (Sensor s : items) {

            if (s.getType() != null && type != null
                    && s.getType().equalsIgnoreCase(type)) {
                result.add(s);
            }
        }

        return result;
    }
}
