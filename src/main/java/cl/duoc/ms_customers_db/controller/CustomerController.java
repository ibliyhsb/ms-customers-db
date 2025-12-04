package cl.duoc.ms_customers_db.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.ms_customers_db.model.dto.CustomerDto;
import cl.duoc.ms_customers_db.model.dto.LoginDto;

import cl.duoc.ms_customers_db.service.CustomerService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping("/authenticate")
    public ResponseEntity<Boolean> authenticateCustomer(@RequestBody LoginDto loginDto) {
        boolean isAuthenticated = customerService.authenticateCustomer(loginDto.getEmail(), loginDto.getPassword());
        return ResponseEntity.ok(isAuthenticated);
    }
    

    @GetMapping()
    public List<CustomerDto> selectAllCustomer() {
        return customerService.selectAllCustomer();
    }
    
    @GetMapping("/GetCustomerById/{idCustomer}")
    public CustomerDto getCustomerById(@PathVariable("idCustomer") Long idCustomer) {
        return customerService.getCustomerById(idCustomer);
    }

    @PostMapping()
    public ResponseEntity<String> insertCustomer(@RequestBody CustomerDto customerDto) {
        return customerService.insertCustomer(customerDto);
    }

    @DeleteMapping("/DeleteCustomerById/{idCustomer}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("idCustomer") Long idCustomer){
        return customerService.deleteCustomer(idCustomer);
    }

    @PutMapping("/UpdateCustomer")
    public ResponseEntity<String> updateCustomer(@RequestBody CustomerDto customerDto) {
      return customerService.updateCustomer(customerDto);
    }
    
    
}
