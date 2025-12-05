package cl.duoc.ms_customers_db.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import cl.duoc.ms_customers_db.model.dto.CustomerDto;
import cl.duoc.ms_customers_db.model.entities.Customer;
import cl.duoc.ms_customers_db.model.repository.CustomerRepository;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    
    public CustomerDto translateEntityToDto(Customer customer){

        CustomerDto customerDto = new CustomerDto();
        customerDto.setIdCustomer(customer.getIdCustomer());
        customerDto.setPassword(customer.getPassword());
        customerDto.setName(customer.getName());
        customerDto.setLastName(customer.getLastName());
        customerDto.setEmail(customer.getEmail());

        return customerDto;
    }

    public CustomerDto getCustomerByEmail(String email) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found with email: " + email));

        CustomerDto customerDto = new CustomerDto();
        customerDto.setIdCustomer(customer.getIdCustomer());
        customerDto.setPassword(customer.getPassword());
        customerDto.setName(customer.getName());
        customerDto.setLastName(customer.getLastName());
        customerDto.setEmail(customer.getEmail());

        return customerDto;
    }

    public CustomerDto getCustomerById(Long idCustomer){

        Optional<Customer> customer = customerRepository.findById(idCustomer);

        CustomerDto customerDto = null;

        if(customer.isPresent())
            return customerDto = translateEntityToDto(customer.get());
        else
            return customerDto;
    }

    public List<CustomerDto> translateListEntityToDto(List<Customer> customer){

        List<CustomerDto> listaDto = new ArrayList<>();
        CustomerDto customerDto = null;
        for(Customer cust: customer){
            customerDto = new CustomerDto();
            customerDto.setIdCustomer(cust.getIdCustomer());
            customerDto.setPassword(cust.getPassword());
            customerDto.setName(cust.getName());
            customerDto.setLastName(cust.getLastName());
            customerDto.setEmail(cust.getEmail());

            listaDto.add(customerDto);
        }
        return listaDto;     

    }

    public List<CustomerDto> selectAllCustomer(){ 

        List<Customer> listaCustomers = customerRepository.findAll();
        List<CustomerDto> listaCustomersDto = translateListEntityToDto(listaCustomers);
        return listaCustomersDto;
    }
    
    public Customer translateDtoToEntity(CustomerDto customerDto){

        Customer customer = new Customer();
        customer.setIdCustomer(customerDto.getIdCustomer());
        customer.setPassword(customerDto.getPassword());
        customer.setName(customerDto.getName());
        customer.setLastName(customerDto.getLastName());
        customer.setEmail(customerDto.getEmail());

        return customer;
    }


    public boolean authenticateCustomer(String email, String password){
        Optional<Customer> customer = customerRepository.findByEmail(email);
        if(customer.isPresent() && customer.get().getPassword().equals(password))
            return true;
        else
            return false;
    }


    public ResponseEntity<String> insertCustomer(CustomerDto customerDto){

        Optional<Customer> customerEmail = customerRepository.findByEmail(customerDto.getEmail());
        
        if(customerEmail.isPresent()){

            return ResponseEntity.status(HttpStatus.CONFLICT).body("This email does already exists.");
        }

        else{

            Customer newCustomer = translateDtoToEntity(customerDto);
            customerRepository.save(newCustomer);

            return ResponseEntity.ok("Customer created.");}
    }

    public ResponseEntity<String> deleteCustomer(Long idCustomer){
        
        Optional<Customer> customer = customerRepository.findById(idCustomer);
        if(customer.isPresent()){
        customerRepository.deleteById(idCustomer);
        return ResponseEntity.ok("Customer with ID: "+ idCustomer + " was deleted.");}
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The customer with ID: " + idCustomer + " does not exist.");
        }
    }

    public ResponseEntity<String> updateCustomer(CustomerDto customerDto){

        Optional<Customer> customerId = customerRepository.findById(customerDto.getIdCustomer());
        Optional<Customer> customerEmail = customerRepository.findByEmail(customerDto.getEmail());

        if(!customerId.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The customer cannot be updated because it does not exist.");
        }

        Customer updatedCustomer = customerId.get();

        if (customerEmail.isPresent() && !customerEmail.get().getIdCustomer().equals(customerDto.getIdCustomer())){
                updatedCustomer.setPassword(customerDto.getPassword());
                updatedCustomer.setName(customerDto.getName());
                updatedCustomer.setLastName(customerDto.getLastName());
                customerRepository.save(updatedCustomer);
                return ResponseEntity.status(HttpStatus.CONFLICT).body("This email already exists, updated data: \n"
                                                                                                         + "Password: " 
                                                                                                         + updatedCustomer.getPassword() 
                                                                                                         + "\n" 
                                                                                                         + "Name: "
                                                                                                         +  updatedCustomer.getName() 
                                                                                                         + "\n" 
                                                                                                         + "Last name: "
                                                                                                         + updatedCustomer.getLastName());
            }

        else{
                updatedCustomer.setPassword(customerDto.getPassword());
                updatedCustomer.setName(customerDto.getName());
                updatedCustomer.setLastName(customerDto.getLastName());
                updatedCustomer.setEmail(customerDto.getEmail());
                customerRepository.save(updatedCustomer);
                return ResponseEntity.ok("Customer updated: \n"
                                         + "Password: " 
                                         + updatedCustomer.getPassword() 
                                         + "\n" 
                                         + "Name: " 
                                         +  updatedCustomer.getName() 
                                         + "\n" 
                                         + "Last name: " 
                                         + updatedCustomer.getLastName() 
                                         +  "\n" 
                                         + "Email: " 
                                         + updatedCustomer.getEmail());}
        
    }
}
