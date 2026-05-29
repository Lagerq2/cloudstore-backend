package systementor.cloudstoreuserservice.order.controller;

import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import systementor.cloudstoreuserservice.order.model.CreateOrderRequest;
import systementor.cloudstoreuserservice.order.model.CustomerOrder;
import systementor.cloudstoreuserservice.order.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {



    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }



    /** TODO FIX ISSUE WITH ORDERS, IT SAYS NOT AUTHENTICATED ATM*/
    @PostMapping
    public CustomerOrder createOrder(Authentication authentication, @RequestBody @Valid  CreateOrderRequest request) {

        if (authentication == null){
            throw new RuntimeException("User is not authenticated");
        }

        String userEmail = authentication.getName();
        return orderService.createOrder(userEmail, request);
    }


    @GetMapping
    public List<CustomerOrder> getOrders(Authentication authentication) {
        String userEmail = authentication.getName();
        return orderService.getOrderForUser(userEmail);
    }

}
