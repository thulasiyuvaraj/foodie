package com.foodie.controller;

import com.foodie.model.CustomerOrder;
import com.foodie.model.FoodItem;
import com.foodie.repository.CustomerOrderRepository;
import com.foodie.repository.FoodItemRepository;
import com.foodie.service.CartService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@Validated
public class StoreController {
    private final FoodItemRepository foodRepo;
    private final CustomerOrderRepository orderRepo;
    private final CartService cart;

    public StoreController(FoodItemRepository foodRepo, CustomerOrderRepository orderRepo, CartService cart) {
        this.foodRepo = foodRepo;
        this.orderRepo = orderRepo;
        this.cart = cart;
    }

    @GetMapping("/")
    public String home(@RequestParam(defaultValue = "") String q, Model model) {
        List<FoodItem> foods = q.isBlank()
                ? foodRepo.findAll()
                : foodRepo.findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(q, q);
        model.addAttribute("foods", foods);
        model.addAttribute("q", q);
        model.addAttribute("cartCount", cart.getItems().values().stream().mapToInt(Integer::intValue).sum());
        return "index";
    }

    @PostMapping("/cart/add/{id}")
    public String add(@PathVariable Long id, RedirectAttributes redirect) {
        foodRepo.findById(id).ifPresent(cart::add);
        redirect.addFlashAttribute("message", "Added to your cart!");
        return "redirect:/";
    }

    @GetMapping("/cart")
    public String showCart(Model model) {
        List<CartEntry> entries = cartEntries();
        model.addAttribute("entries", entries);
        model.addAttribute("total", entries.stream().mapToDouble(CartEntry::getLineTotal).sum());
        return "cart";
    }

    @PostMapping("/cart/change/{id}")
    public String change(@PathVariable Long id, @RequestParam int delta) {
        cart.change(id, delta);
        return "redirect:/cart";
    }

    @PostMapping("/cart/remove/{id}")
    public String remove(@PathVariable Long id) {
        cart.remove(id);
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String checkout(Model model) {
        List<CartEntry> entries = cartEntries();
        if (entries.isEmpty()) return "redirect:/";
        model.addAttribute("entries", entries);
        model.addAttribute("total", entries.stream().mapToDouble(CartEntry::getLineTotal).sum());
        return "checkout";
    }

    @PostMapping("/checkout")
    public String placeOrder(@Valid @ModelAttribute CheckoutForm form,
                             BindingResult bindingResult,
                             Model model) {
        List<CartEntry> entries = cartEntries();
        if (entries.isEmpty()) return "redirect:/";
        if (bindingResult.hasErrors()) {
            model.addAttribute("entries", entries);
            model.addAttribute("total", entries.stream().mapToDouble(CartEntry::getLineTotal).sum());
            return "checkout";
        }
        List<CustomerOrder.OrderLine> lines = entries.stream()
                .map(e -> new CustomerOrder.OrderLine(e.getFood().getName(), e.getQuantity(), e.getFood().getPrice()))
                .collect(Collectors.toList());
        double total = entries.stream().mapToDouble(CartEntry::getLineTotal).sum();
        CustomerOrder order = orderRepo.save(new CustomerOrder(form.getCustomerName().trim(), form.getPhone().trim(), form.getAddress().trim(), total, lines));
        cart.clear();
        model.addAttribute("order", order);
        return "success";
    }

    @GetMapping("/orders")
    public String orders(Model model) {
        List<CustomerOrder> orders = orderRepo.findAll();
        orders.sort(Comparator.comparing(CustomerOrder::getCreatedAt, Comparator.nullsLast(Comparator.reverseOrder())));
        model.addAttribute("orders", orders);
        return "orders";
    }

    private List<CartEntry> cartEntries() {
        List<CartEntry> result = new ArrayList<>();
        cart.getItems().forEach((id, quantity) -> foodRepo.findById(id)
                .ifPresent(food -> result.add(new CartEntry(food, quantity))));
        return result;
    }

    public static class CheckoutForm {
        @NotBlank(message = "Name is required")
        private String customerName;
        @NotBlank(message = "Phone number is required")
        @Pattern(regexp = "[0-9+() -]{7,20}", message = "Enter a valid phone number")
        private String phone;
        @NotBlank(message = "Address is required")
        private String address;

        public String getCustomerName() { return customerName; }
        public void setCustomerName(String customerName) { this.customerName = customerName; }
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
    }

    public static class CartEntry {
        private final FoodItem food;
        private final int quantity;
        public CartEntry(FoodItem food, int quantity) { this.food = food; this.quantity = quantity; }
        public FoodItem getFood() { return food; }
        public int getQuantity() { return quantity; }
        public double getLineTotal() { return food.getPrice() * quantity; }
    }
}
