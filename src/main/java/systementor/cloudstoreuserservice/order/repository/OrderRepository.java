package systementor.cloudstoreuserservice.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import systementor.cloudstoreuserservice.order.model.CustomerOrder;

import java.util.List;

public interface OrderRepository extends JpaRepository<CustomerOrder, Long> {

    List<CustomerOrder> findByUserEmail(String userEmail);
}
