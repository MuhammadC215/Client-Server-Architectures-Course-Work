package com.example.model;

import java.util.List;

public interface BaseModel {
    
    String getId();
    void setId(String id);
    
    String getName();
    void setName(String name);
    
    int getCapacity();
    void setCapacity(int capacity);
    
    List<String> getSensorIds();
    void setSensorIds(List<String> sensorIds);
}
