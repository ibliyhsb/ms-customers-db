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
        customerDto.setUsername(customer.getUsername());
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
            customerDto.setUsername(cust.getUsername());
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
        customer.setUsername(customerDto.getUsername());
        customer.setPassword(customerDto.getPassword());
        customer.setName(customerDto.getName());
        customer.setLastName(customerDto.getLastName());
        customer.setEmail(customerDto.getEmail());

        return customer;
    }


    public boolean authenticateCustomer(String username, String password){
        Optional<Customer> customer = customerRepository.findByUsername(username);
        if(customer.isPresent() && customer.get().getPassword().equals(password))
            return true;
        else
            return false;
    }


    public ResponseEntity<String> insertCustomer(CustomerDto customerDto){

        Optional<Customer> customerUsername = customerRepository.findByUsername(customerDto.getUsername());
        Optional<Customer> customerEmail = customerRepository.findByEmail(customerDto.getEmail());
        
        if(customerUsername.isPresent() && customerEmail.isPresent()){

            return ResponseEntity.status(HttpStatus.CONFLICT).body("The username and the email do already exists.");
        }
        
        else if(customerUsername.isPresent()){

            return ResponseEntity.status(HttpStatus.CONFLICT).body("This username does already exists.");
        }

        else if(customerEmail.isPresent()){

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

        Optional<Customer> customer = customerRepository.findById(customerDto.getIdCustomer());
        if (customer.isPresent()){
            Customer updatedCustomer = customer.get();
            if(updatedCustomer.getUsername().equals(customerDto.getUsername())){
                updatedCustomer.setPassword(customerDto.getPassword());
                updatedCustomer.setName(customerDto.getName());
                updatedCustomer.setLastName(customerDto.getLastName());
                updatedCustomer.setEmail(customerDto.getEmail());
                customerRepository.save(updatedCustomer);
                return ResponseEntity.status(HttpStatus.CONFLICT).body("This username does already exists, updated data: \n"
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
                                                                                                         + updatedCustomer.getEmail());
                                                                                                                                        }  
            else {
                updatedCustomer.setUsername(customerDto.getUsername());
                updatedCustomer.setPassword(customerDto.getPassword());
                updatedCustomer.setName(customerDto.getName());
                updatedCustomer.setLastName(customerDto.getLastName());
                updatedCustomer.setEmail(customerDto.getEmail());
                customerRepository.save(updatedCustomer);
                return ResponseEntity.ok("Customer updated: \n"
                                         + "Username: " 
                                         + updatedCustomer.getUsername() 
                                         + "\n" 
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
            else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The customer cannot be updated because it does not exist.");
        }
    }
}
