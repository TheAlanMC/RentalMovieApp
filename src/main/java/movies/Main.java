package movies;

import lombok.extern.slf4j.Slf4j;
import movies.customer.entity.Customer;
import movies.customer.repository.CustomerRepositoryImpl;
import movies.movie.entity.Movie;
import movies.movie.repository.MovieRepositoryImpl;
import movies.rental.entity.Rental;
import movies.rental.utils.Statement;
import movies.rental.repository.RentalRepositoryImpl;
import movies.rental.service.RentalService;

import java.util.List;

@Slf4j
public class Main {
    public static void main(String[] args) {
        CustomerRepositoryImpl customerRepository = new CustomerRepositoryImpl();
        MovieRepositoryImpl movieRepository = new MovieRepositoryImpl();
        RentalRepositoryImpl rentalRepository = new RentalRepositoryImpl();
        RentalService rentalService = new RentalService(customerRepository, movieRepository, rentalRepository);

        List<Customer> customers = customerRepository.getCustomers();
        List<Movie> movies = movieRepository.getMovies();

        try {
            rentalService.addRentalByCustomerIdAndMovieId(1L, 1L, 5);
            rentalService.addRentalByCustomerIdAndMovieId(1L, 2L, 1);
            rentalService.addRentalByCustomerIdAndMovieId(1L, 3L, 3);
            rentalService.addRentalByCustomerIdAndMovieId(2L, 1L, 5);
            rentalService.addRentalByCustomerIdAndMovieId(2L, 2L, 1);
            rentalService.addRentalByCustomerIdAndMovieId(3L, 3L, 3);

            // Generate statements for customers
            for (Customer customer : customers) {
                List<Rental> customerRentals = rentalRepository.getRentalsByCustomer(customer.getId());
                if (!customerRentals.isEmpty()) {
                    log.info("Generating statement for customer: {}", customer.getName());
                    System.out.println(Statement.generate(customer, customerRentals));
                }
            }
        } catch (Exception e) {
            log.error("An error occurred", e);
        }
    }
}
