package Online.Book.Store.orders.service.implementation;

import Online.Book.Store.customer.model.Customer;
import Online.Book.Store.customer.service.CustomerService;
import Online.Book.Store.exception.GeneralException;
import Online.Book.Store.general.enums.ResponseCodeAndMessage;
import Online.Book.Store.orderLine.model.OrderLine;
import Online.Book.Store.orderLine.service.OrderLineService;
import Online.Book.Store.orders.dto.OrderDTO;
import Online.Book.Store.orders.model.OrderListDTO;
import Online.Book.Store.orders.model.Orders;
import Online.Book.Store.orders.repository.OrderRepository;
import Online.Book.Store.orders.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderLineService orderLineService;

    private final CustomerService customerService;


    @Override
    public OrderDTO createOrder(List<OrderLine> itemList, Long customerId) {
        log.info("Request to order with payload {}", itemList);

        //get customer
        Customer customer = customerService.findCustomerById(customerId);

        Orders orders = new Orders();
        orders.setItemList(itemList);
        orders.setCustomer(customer);

        Orders savedOrders = orderRepository.save(orders);

        return getOrderDTO(savedOrders);
    }

    @Override
    public OrderDTO getOrderDTO(Orders orders) {

//        List<OrderLine> itemList = order.getItemList();
//
//        List<OrderLineDTO> orderLineDTOList = new ArrayList<>();
//
//        for (OrderLine orderLine : itemList) {
//            OrderLineDTO orderLineDTO = orderLineService.getItemsDTO(orderLine);
//            orderLineDTOList.add(orderLineDTO);
//        }

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(orders.getId());
//        orderDTO.setItemList(orderLineDTOList);

        return orderDTO;
    }

    @Override
    public Orders findOrderById(Long id) {
        log.info("Request to get an order with Id {}", id);

        return orderRepository.findById(id)
                .orElseThrow(() -> new GeneralException(ResponseCodeAndMessage.RECORD_NOT_FOUND_88.responseMessage, "No Order found with the given ID"));
    }

    @Override
    public OrderListDTO getAllOrders(int pageNumber, int pageSize) {
        log.info("Request to get all orders{} {}", pageNumber, pageSize);

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<Orders> transactionPage = orderRepository.findAll(pageable);

        return getOrderListDTO(transactionPage);
    }

    private OrderListDTO getOrderListDTO(Page<Orders> ordersPage) {
        OrderListDTO listDTO = new OrderListDTO();

        List<Orders> ordersList = ordersPage.getContent();
        if (ordersPage.getContent().size() > 0) {
            listDTO.setHasNextRecord(ordersPage.hasNext());
            listDTO.setTotalCount((int) ordersPage.getTotalElements());
        }

        List<OrderDTO> orderDTOS = convertToPaymentTransactionDTOList(ordersList);
        listDTO.setOrderList(orderDTOS);
        return listDTO;
    }

    private List<OrderDTO> convertToPaymentTransactionDTOList(List<Orders> ordersList) {
        log.info("Converting order List to order DTO List");

        return ordersList.stream().map(Orders::getOrderDTO).collect(Collectors.toList());
    }

}
