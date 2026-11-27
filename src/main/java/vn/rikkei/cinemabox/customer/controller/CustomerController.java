package vn.rikkei.cinemabox.customer.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.service.annotation.GetExchange;
import vn.rikkei.cinemabox.common.PageResponse;
import vn.rikkei.cinemabox.customer.dto.CustomerRequest;
import vn.rikkei.cinemabox.customer.dto.CustomerResponse;
import vn.rikkei.cinemabox.customer.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

//    Hợp lệ: trả 201  và CustomerResponse của khách vừa tạo.•
//    Dữ liệu sai quy tắc: trả 400 V VALIDATION_FAILED, details
//    từng trường sai(có field  đúng tên trường trong body). Không tạo bản ghi.•
//    Email đã có trong hệ thống: trả 409 CONFLICT. Không tạo bản ghi.
    @GetMapping()
    public PageResponse<CustomerResponse> getCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        // page  đánh số từ 0, mặc định 0.
        // size  từ 1 đến 50, mặc định 10.•
        // Trang vượt quá số trang hiện có: trả 200 với content  rỗng, totalElements  vẫn đúng.•
        // page  âm, s size  ngoài khoảng 1 đến 50, hoặc tham số không phải số: trả 400 BAD_REQUEST.
        if (page < 0 || size < 1 || size > 50) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "page phải >= 0 và size phải từ 1 đến 50"
            );
        }
        return customerService.getCustomers(page, size);

    }


    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getCustomerById(id));
    }

    @PostMapping()
    public ResponseEntity<CustomerResponse> createNewCustomer(@Valid @RequestBody CustomerRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createCustomer(request));
    }
}
