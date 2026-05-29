package systementor.cloudstoreuserservice.order.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import systementor.cloudstoreuserservice.order.model.CreateOrderItemRequest;
import systementor.cloudstoreuserservice.order.model.CreateOrderRequest;
import systementor.cloudstoreuserservice.order.model.CustomerOrder;
import systementor.cloudstoreuserservice.order.model.OrderItem;
import systementor.cloudstoreuserservice.order.repository.OrderRepository;
import systementor.cloudstoreuserservice.product.client.ProductClient;
import systementor.cloudstoreuserservice.product.model.Product;

import java.util.List;

@Service
public class OrderService {




    private final OrderRepository orderRepository;
    private final ProductClient productClient;

    public OrderService(OrderRepository orderRepository, ProductClient productClient) {
        this.orderRepository = orderRepository;
        this.productClient = productClient;
    }


    public CustomerOrder createOrder(String userEmail, CreateOrderRequest request) {
        List<Product> products = productClient.getProducts();

        CustomerOrder order = new CustomerOrder(userEmail);

        for (CreateOrderItemRequest itemRequest : request.items()) {
            Product product = findProduct(products, itemRequest.productId());

            OrderItem item = new OrderItem(
                    product.id(),
                    product.title(),
                    product.price(),
                    itemRequest.quantity()
            );



            order.addItem(item);
        }
        return orderRepository.save(order);

    }


    public List<CustomerOrder> getOrderForUser(String userEmail) {
        return orderRepository.findByUserEmail(userEmail);
    }


    private Product findProduct(List<Product> products, long productId) {
        return products.stream()
                .filter(product -> product.id() == productId)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,"Product not found"
                ));
    }







}
