import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RentalService {
    private ArrayList<Rental> rentals = new ArrayList<>();
    private CarService carService;

    public RentalService(CarService carService) {
        this.carService = carService;
    }

    public void reconstructRentals() {
        rentals.clear();
        for (Car car : carService.getAllCars()) {
            // If the car is NOT available and has a renter, it means it's currently rented
            if (!car.isAvailable() && car.getRenter() != null) {
                // We create a new Rental object to track it.
                // Note: Since we don't save the exact start date in this minimal version,
                // we default to today (LocalDate.now()).
                Rental rental = new Rental(car, car.getRenter(), LocalDate.now());
                rentals.add(rental);
            }
        }
    }




    public Rental createRental(Car car, Customer customer) {

        car.setAvailable(false);
        car.setRenter(customer);


        Rental rental = new Rental(car, customer, LocalDate.now());
        rentals.add(rental);

        System.out.println("Rental created: " + customer.getFullName() + " rented " + car.getMake() + " " + car.getModel());
        return rental;
    }

    public void completeRental(Rental rental) {
        rental.completeRental(LocalDate.now());
        rental.getCar().setAvailable(true);

        Car car = rental.getCar();
        car.setAvailable(true);
        car.setRenter(null);
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