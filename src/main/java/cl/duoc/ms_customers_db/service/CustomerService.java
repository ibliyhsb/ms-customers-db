package cl.duoc.ms_customers_db.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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


    public boolean authenticateCustomer(CustomerDto customerDto){//TRAER PARAMETROS O EL DTO?

        Customer customer = new Customer();
        customer = translateDtoToEntity(customerDto);

        Optional<Customer> customerOp = customerRepository.findByUsername(customer.getUsername());
        if(customerOp.isPresent() && customerOp.get().getPassword().equals(customer.getPassword()))
            return true;
        else
            return false;
    }


    public Customer insertCustomer(CustomerDto customerDto){

        Customer newCustomer = translateDtoToEntity(customerDto);
        customerRepository.save(newCustomer);

        return newCustomer;
    }

    public String deleteCustomer(Long idCustomer){//TRAER PARAMETROS O EL DTO?

        customerRepository.deleteById(idCustomer);
        return "Customer deleted";
    }

    public Customer updateCustomer(CustomerDto customerDto){ //TRAER PARAMETROS O EL DTO?

        Customer newCustomer = translateDtoToEntity(customerDto);
        customerRepository.save(newCustomer);

        return newCustomer;
    }


    
}
