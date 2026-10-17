package com.foodie.config;

import com.foodie.model.FoodItem;
import com.foodie.repository.FoodItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {
    @Bean
    CommandLineRunner seedFoods(FoodItemRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new FoodItem("Classic Burger", "Burgers", "Juicy veggie burger with fresh lettuce and house sauce.", 149, "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?auto=format&fit=crop&w=900&q=80"));
                repository.save(new FoodItem("Margherita Pizza", "Pizza", "Classic cheese pizza with basil and tomato.", 249, "https://images.unsplash.com/photo-1579751626657-72bc17010498?auto=format&fit=crop&w=900&q=80"));
                repository.save(new FoodItem("Chicken Biryani", "Indian", "Fragrant basmati rice with aromatic spices.", 219, "https://images.unsplash.com/photo-1563379091339-03246963d96c?auto=format&fit=crop&w=900&q=80"));
                repository.save(new FoodItem("Masala Dosa", "South Indian", "Crispy dosa served with chutney and sambar.", 99, "https://images.unsplash.com/photo-1668236543090-82eba5ee5976?auto=format&fit=crop&w=900&q=80"));
                repository.save(new FoodItem("Pasta Alfredo", "Pasta", "Creamy white sauce pasta with herbs.", 189, "https://images.unsplash.com/photo-1645112411341-6c4fd023714a?auto=format&fit=crop&w=900&q=80"));
                repository.save(new FoodItem("French Fries", "Sides", "Golden crispy fries with a pinch of seasoning.", 89, "https://images.unsplash.com/photo-1573080496219-bb080dd4f877?auto=format&fit=crop&w=900&q=80"));
                repository.save(new FoodItem("Chocolate Brownie", "Desserts", "Rich chocolate brownie for a sweet finish.", 119, "https://images.unsplash.com/photo-1606313564200-e75d5e30476c?auto=format&fit=crop&w=900&q=80"));
                repository.save(new FoodItem("Fresh Lemonade", "Drinks", "Fresh lemon drink, chilled and refreshing.", 69, "https://images.unsplash.com/photo-1513558161293-cdaf765edfd4?auto=format&fit=crop&w=900&q=80"));
            }
        };
    }
}
