package com.example.resource;

import com.example.dao.GenericDAO;
import com.example.dao.MockDatabase;
import com.example.exception.RoomNotEmptyException;
import com.example.model.Room;
import java.net.URI;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import javax.ws.rs.core.Response;

@Path("/rooms")
public class SensorRoom {

    private GenericDAO<Room> roomDAO = new GenericDAO<>(MockDatabase.ROOMS);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Room> getAllRooms() {
        return roomDAO.getAll();
    }

    @GET
    @Path("/{roomId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Room getRoomById(@PathParam("roomId") String roomId) {
        return roomDAO.getById(roomId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addRoom(Room room) {
        roomDAO.add(room);

        return Response
                .created(URI.create("/api/v1/rooms/" + room.getId()))
                .entity(room)
                .build();
    }

    @PUT
    @Path("/{roomId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateRoom(@PathParam("roomId") String roomId, Room updateRoom) {
        Room existingRoom = roomDAO.getById(roomId);

        if (existingRoom != null) {
            updateRoom.setId(roomId);
            roomDAO.update(updateRoom);
        }
    }

    @DELETE
    @Path("/{roomId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteRoom(@PathParam("roomId") String roomId) {

        Room room = roomDAO.getById(roomId);

        if (room.getSensorIds() != null && !room.getSensorIds().isEmpty()) {
            throw new RoomNotEmptyException(
                    "Room is currently occupied with active sensors"
            );
        }

        roomDAO.delete(roomId);

        return Response.noContent().build();
    }

}
