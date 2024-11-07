package movies.customer.service;

import movies.customer.entity.Customer;
import movies.customer.exception.CustomerException;
import movies.customer.repository.CustomerRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author Chris Alan Apaza Aguilar
 */

class CustomerServiceTest {

    @Mock
    private CustomerRepositoryImpl customerRepository;

    private CustomerService customerService;

    private List<Customer> mockCustomers;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Arrange
        mockCustomers = new ArrayList<>();
        mockCustomers.add(new Customer(1L, "Chris"));
        CustomerService.resetInstance();
        customerService = CustomerService.getInstance(customerRepository);
    }

    @Test
    public void getCustomersShouldReturnCustomers() {
        // Arrange
        when(customerRepository.getCustomers()).thenReturn(mockCustomers);
        // Act
        List<Customer> customers = customerService.getCustomers();
        // Assert
        assertEquals(1, customers.size());
        assertEquals(1L, customers.get(0).getId());
        assertEquals("Chris", customers.get(0).getName());
    }

    @Test
    void addCustomerShouldAddCustomer() {
        // Arrange
        doNothing().when(customerRepository).addCustomer(any(Customer.class));
        // Act
        customerService.addCustomer(2L, "Alan");
        // Assert
        verify(customerRepository, times(1)).addCustomer(any(Customer.class));
    }

    @Test
    void getCustomerByIdShouldReturnCustomer() {
        // Arrange
        when(customerRepository.getCustomerById(1L)).thenReturn(mockCustomers.get(0));
        // Act
        Customer customer = customerService.getCustomerById(1L);
        // Assert
        assertEquals(1L, customer.getId());
        assertEquals("Chris", customer.getName());
    }

    @Test
    void getCustomerByIdShouldThrowCustomerException() {
        // Arrange
        when(customerRepository.getCustomerById(1L)).thenReturn(null);
        // Act and Assert
        assertThrows(CustomerException.class, () -> customerService.getCustomerById(1L));
    }
}