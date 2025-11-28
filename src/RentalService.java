import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RentalService {
    private ArrayList<Rental> rentals = new ArrayList<>();

    public Rental createRental(Car car, Customer customer) {
        Rental rental = new Rental(car, customer, LocalDate.now());
        rentals.add(rental);
        System.out.println("Rental created: " + customer.getFullName() + " rented " + car.getMake() + " " + car.getModel());
        return rental;
    }

    public void completeRental(Rental rental) {
        rental.completeRental(LocalDate.now());
        rental.getCar().setAvailable(true);
    }

    public List<Rental> getAllRentals() {
        return new ArrayList<>(rentals);
    }

    public List<Rental> getActiveRentals() {
        List<Rental> active = new ArrayList<>();
        for (Rental rental : rentals) {
            if (rental.isActive()) {
                active.add(rental);
            }
        }
        return active;
    }

    public List<Rental> getRentalsByCustomer(Customer customer) {
        List<Rental> customerRentals = new ArrayList<>();
        for (Rental rental : rentals) {
            if (rental.getCustomer().equals(customer)) {
                customerRentals.add(rental);
            }
        }
        return customerRentals;
    }

    public List<Rental> getActiveRentalsByCustomer(Customer customer) {
        List<Rental> activeCustomerRentals = new ArrayList<>();
        for (Rental rental : rentals) {
            if (rental.isActive() && rental.getCustomer().equals(customer)) {
                activeCustomerRentals.add(rental);
            }
        }
        return activeCustomerRentals;
    }

    public Rental findActiveRentalByCar(Car car) {
        for (Rental rental : rentals) {
            if (rental.isActive() && rental.getCar().equals(car)) {
                return rental;
            }
        }
        return null;
    }
}