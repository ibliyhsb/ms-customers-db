package cl.duoc.ms_customers_db.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.ms_customers_db.model.entities.Customer;
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

    @GetMapping("/authenticate/{username}/{password}")
    public boolean authenticateCustomer(@PathVariable("username") String username, @PathVariable("password") String password) {
        return customerService.authenticateCustomer(username, password);
    }
    

    @GetMapping()
    public List<Customer> selectAllCustomer() {
        return customerService.selectAllCustomer();
    }
    
    @GetMapping("GetCustomerById/{idCustomer}")
    public Customer getCustomerById(@PathVariable("idCustomer") Long idCustomer) {
        return customerService.getCustomerById(idCustomer);
    }

    @PostMapping()
    public Customer insertCustomer(@RequestBody Customer customer) {
        return customerService.insertCustomer(customer);
    }

    @DeleteMapping("DeleteCustomerById/{idCustomer}")
    public String deleteCustomer(@PathVariable("idCustomer") Long idCustomer){
        return customerService.deleteCustomer(idCustomer);
    }

    @PutMapping()
    public Customer updateCustomer(@RequestBody Customer customer) {
        return customerService.updateCustomer(customer);
    }
    
    
    
}
