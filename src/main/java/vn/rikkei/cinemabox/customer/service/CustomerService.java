package vn.rikkei.cinemabox.customer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.rikkei.cinemabox.common.PageResponse;
import vn.rikkei.cinemabox.common.ResourceNotFoundException;
import vn.rikkei.cinemabox.customer.Customer;
import vn.rikkei.cinemabox.customer.CustomerRepository;
import vn.rikkei.cinemabox.customer.dto.CustomerRequest;
import vn.rikkei.cinemabox.customer.dto.CustomerResponse;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerResponse getCustomerById(Long id){
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Khách hàng không tồn tại, vui lòng kiểm tra lại"));
        return CustomerResponse.builder()
                .id(customer.getId())
                .fullName(customer.getFullName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .memberLevel(customer.getMemberLevel())
                .createdAt(customer.getCreatedAt())
                .build();
    }

    public PageResponse<CustomerResponse> getCustomers(int page, int size){
        Pageable pageable= PageRequest.of(page, size, Sort.by("id").ascending());
        Page<Customer> customers = customerRepository.findAll(pageable);
        List<CustomerResponse> customerResponses = customers.getContent().stream().map((c) -> {
            return CustomerResponse.builder()
                    .id(c.getId())
                    .fullName(c.getFullName())
                    .email(c.getEmail())
                    .phone(c.getPhone())
                    .memberLevel(c.getMemberLevel())
                    .createdAt(c.getCreatedAt())
                    .build();
        }).toList();

        return new PageResponse<>(
                customerResponses,
                customers.getNumber(),
                customers.getSize(),
                customers.getTotalElements(),
                customers.getTotalPages()
        );
    }

    public CustomerResponse createCustomer(CustomerRequest request){
        if(customerRepository.existsCustomerByEmail(request.getEmail())){
            throw new IllegalArgumentException("Email đã tồn tại, vui lòng kiểm tra lại");
        }
        Customer newCustomer =customerRepository.save(Customer.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .memberLevel(request.getMemberLevel())
                .build());
        return CustomerResponse.builder()
                .id(newCustomer.getId())
                .fullName(newCustomer.getFullName())
                .email(newCustomer.getEmail())
                .phone(newCustomer.getPhone())
                .memberLevel(newCustomer.getMemberLevel())
                .createdAt(newCustomer.getCreatedAt())
                .build();
    }

}
