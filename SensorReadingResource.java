package com.example.resource;

import com.example.dao.MockDatabase;
import com.example.exception.LinkedResourceNotFoundException;
import com.example.exception.SensorUnavailableException;
import com.example.model.Sensor;
import com.example.model.SensorReading;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorReadingResource {

    private final String sensorId;

    public SensorReadingResource(String sensorId) {
        this.sensorId = sensorId;
    }

    @GET
    public List<SensorReading> getAll() {

        Sensor sensor = MockDatabase.SENSORS.stream()
                .filter(s -> s.getId().equals(sensorId))
                .findFirst()
                .orElse(null);

        if (sensor == null) {
            throw new LinkedResourceNotFoundException(
                    "Sensor does not exist"
            );
        }

        if ("MAINTENANCE".equalsIgnoreCase(sensor.getStatus())) {
            throw new SensorUnavailableException(
                    "Sensor is currently in MAINTENANCE and cannot return readings"
            );
        }

        return MockDatabase.READINGS
                .getOrDefault(sensorId, new ArrayList<>());
    }

    @POST
    public Response addReading(SensorReading reading) {

        Sensor sensor = MockDatabase.SENSORS.stream()
                .filter(s -> s.getId().equals(sensorId))
                .findFirst()
                .orElse(null);

        if (sensor == null) {
            throw new LinkedResourceNotFoundException(
                    "Sensor does not exist"
            );
        }

        if ("MAINTENANCE".equalsIgnoreCase(sensor.getStatus())) {
            throw new SensorUnavailableException(
                    "Sensor is currently in MAINTENANCE and cannot accept readings"
            );
        }

        reading.setId(UUID.randomUUID().toString());
        reading.setTimestamp(System.currentTimeMillis());

        MockDatabase.READINGS
                .computeIfAbsent(sensorId, k -> new ArrayList<>())
                .add(reading);

        return Response.status(Response.Status.CREATED)
                .entity(reading)
                .build();
    }

}
