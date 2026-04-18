package com.example.resource;

import com.example.dao.MockDatabase;
import com.example.dao.SensorDAO;
import com.example.exception.LinkedResourceNotFoundException;
import com.example.model.Room;
import com.example.model.Sensor;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import javax.ws.rs.core.Response;

@Path("/sensors")
public class SensorResource {

    SensorDAO sensorDAO = new SensorDAO();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addSensor(Sensor sensor) {

        Room room = MockDatabase.ROOMS.stream()
                .filter(r -> r.getId().equals(sensor.getRoomId()))
                .findFirst()
                .orElse(null);

        if (room == null) {
            throw new LinkedResourceNotFoundException(
                    "Cannot create sensor. Referenced room does not exist."
            );
        }

        sensorDAO.add(sensor, MockDatabase.ROOMS);

        return Response.status(Response.Status.CREATED)
                .entity(sensor)
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Sensor> getSensors(@QueryParam("type") String type) {

        if (type == null) {
            return sensorDAO.getAll();
        }

        return sensorDAO.getByType(type);
    }

    @Path("{id}/readings")
    public SensorReadingResource getReadingResource(@PathParam("id") String sensorId) {
        return new SensorReadingResource(sensorId);
    }

}
