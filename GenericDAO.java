package com.example.dao;

import com.example.model.BaseModel;
import java.util.List;

public class GenericDAO<T extends BaseModel> {

    private final List<T> items;

    public GenericDAO(List<T> items) {
        this.items = items;
    }

    public T getById(String id) {
        for (T item : items) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    public List<T> getAll() {
        return items;
    }

    public void add(T item) {
        items.add(item);
    }

    public void update(T updatedItem) {
        for (int i = 0; i < items.size(); i++) {
            T item = items.get(i);
            if (item.getId().equals(updatedItem.getId())) {
                items.set(i, updatedItem);
                return;
            }
        }
    }
    
    public boolean delete(String id) {
        return items.removeIf(item -> item.getId().equals(id));
    }

}
