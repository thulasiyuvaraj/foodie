package com.foodie.controller;

import com.foodie.model.CustomerOrder;
import com.foodie.model.FoodItem;
import com.foodie.repository.CustomerOrderRepository;
import com.foodie.repository.FoodItemRepository;
import com.foodie.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.View;

import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class StoreControllerValidationTest {

    @Test
    void checkoutRejectsBlankCustomerName() throws Exception {
        FoodItemRepository foodRepo = Mockito.mock(FoodItemRepository.class);
        CustomerOrderRepository orderRepo = Mockito.mock(CustomerOrderRepository.class);
        CartService cart = new CartService();
        FoodItem food = new FoodItem("Classic Burger", "Burgers", "Juicy burger", 149, "image.png");
        food.setId(10L);
        when(foodRepo.findById(10L)).thenReturn(Optional.of(food));
        cart.add(food);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new StoreController(foodRepo, orderRepo, cart))
                .setSingleView(new NoOpView())
                .build();

        mockMvc.perform(post("/checkout")
                        .param("customerName", " ")
                        .param("phone", "1234567")
                        .param("address", "Main Street"))
                .andExpect(status().isOk())
                .andExpect(view().name("checkout"));

        verify(orderRepo, never()).save(any(CustomerOrder.class));
    }

    @Test
    void checkoutAcceptsValidDetails() throws Exception {
        FoodItemRepository foodRepo = Mockito.mock(FoodItemRepository.class);
        CustomerOrderRepository orderRepo = Mockito.mock(CustomerOrderRepository.class);
        CartService cart = new CartService();
        FoodItem food = new FoodItem("Classic Burger", "Burgers", "Juicy burger", 149, "image.png");
        food.setId(10L);
        when(foodRepo.findById(10L)).thenReturn(Optional.of(food));
        cart.add(food);

        CustomerOrder expected = new CustomerOrder("Alice", "9876543210", "Main Street", 149, java.util.List.of(
                new CustomerOrder.OrderLine("Classic Burger", 1, 149)
        ));
        when(orderRepo.save(any(CustomerOrder.class))).thenReturn(expected);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new StoreController(foodRepo, orderRepo, cart))
                .setSingleView(new NoOpView())
                .build();

        mockMvc.perform(post("/checkout")
                        .param("customerName", "Alice")
                        .param("phone", "9876543210")
                        .param("address", "Main Street"))
                .andExpect(status().isOk())
                .andExpect(view().name("success"));

        verify(orderRepo).save(any(CustomerOrder.class));
    }

    private static class NoOpView implements View {
        @Override
        public String getContentType() {
            return "text/html";
        }

        @Override
        public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) {
        }
    }
}
