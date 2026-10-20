package com.foodie.repository;

import com.foodie.model.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {
    List<FoodItem> findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(String name, String category);
}
