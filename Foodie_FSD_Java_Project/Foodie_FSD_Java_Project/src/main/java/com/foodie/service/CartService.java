package com.foodie.service;

import com.foodie.model.FoodItem;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class CartService {
    private final Map<Long, Integer> items = new LinkedHashMap<>();

    public void add(FoodItem food) {
        items.merge(food.getId(), 1, Integer::sum);
    }
    public void change(Long id, int delta) {
        int updated = items.getOrDefault(id, 0) + delta;
        if (updated <= 0) items.remove(id); else items.put(id, updated);
    }
    public void remove(Long id) { items.remove(id); }
    public void clear() { items.clear(); }
    public Map<Long, Integer> getItems() { return Collections.unmodifiableMap(items); }
}
