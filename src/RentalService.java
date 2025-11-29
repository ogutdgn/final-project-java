import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RentalService {
    private ArrayList<Rental> rentals = new ArrayList<>();
    private CarService carService;

    public RentalService(CarService carService) {
        this.carService = carService;
    }


    public void loadRentals(Map<String, Customer> customerMap) {
        rentals.clear();
        List<DataManager.RentalData> dataList = DataManager.loadRentals();
        List<Car> allCars = carService.getAllCars();

        for (DataManager.RentalData data : dataList) {
            // 1. Find Customer
            Customer customer = customerMap.get(data.username);

            // 2. Find Car
            Car car = null;
            for (Car c : allCars) {
                if (c.getPlateNumber().equalsIgnoreCase(data.plateNumber)) {
                    car = c;
                    break;
                }
            }

            // 3. Reconstruct Object
            if (customer != null && car != null) {
                LocalDate rentDate = DateUtil.parseDate(data.rentalDate);
                Rental rental = new Rental(car, customer, rentDate);

                rental.setActive(data.isActive);
                rental.setTotalCost(data.totalCost);

                if (!data.returnDate.equals("NONE")) {
                    rental.setReturnDate(DateUtil.parseDate(data.returnDate));
                }

                rentals.add(rental);
            }
        }
    }

    private void saveRentals() {
        DataManager.saveRentals(rentals);
    }


    public Rental createRental(Car car, Customer customer) {

        car.setAvailable(false);
        car.setRenter(customer);


        Rental rental = new Rental(car, customer, LocalDate.now());
        rentals.add(rental);

        saveRentals();

        System.out.println("Rental created: " + customer.getFullName() + " rented " + car.getMake() + " " + car.getModel());
        return rental;
    }

    public void completeRental(Rental rental) {
        rental.completeRental(LocalDate.now());
        rental.getCar().setAvailable(true);

        Car car = rental.getCar();
        car.setAvailable(true);
        car.setRenter(null);

        saveRentals();
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