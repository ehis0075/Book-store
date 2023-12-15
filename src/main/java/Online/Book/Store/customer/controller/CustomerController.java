package Online.Book.Store.customer.controller;


import Online.Book.Store.customer.dto.CreateCustomerPayload;
import Online.Book.Store.customer.dto.CustomerDTO;
import Online.Book.Store.customer.service.CustomerService;
import Online.Book.Store.general.dto.Response;
import Online.Book.Store.general.enums.ResponseCodeAndMessage;
import Online.Book.Store.general.service.GeneralService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    private final GeneralService generalService;

    @PostMapping("/create")
    public Response createCustomer(@RequestBody CreateCustomerPayload requestDTO) {

        CustomerDTO data = customerService.createCustomer(requestDTO);
        return generalService.prepareResponse(ResponseCodeAndMessage.SUCCESSFUL_0, data);
    }

}
