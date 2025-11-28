import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class CarService {
    private ArrayList<Car> cars = new ArrayList<>();
    private Map<String, Customer> usernameToCustomer = new HashMap<>();

    public void setCustomerMap(Map<String, Customer> customerMap) {
        this.usernameToCustomer = customerMap;
    }

    public void loadCars() {
        cars.clear();
        List<DataManager.CarData> carsData = DataManager.loadCars();

        for (DataManager.CarData carData : carsData) {
            Customer owner = null;
            if (carData.ownerUsername != null && usernameToCustomer.containsKey(carData.ownerUsername)) {
                owner = usernameToCustomer.get(carData.ownerUsername);
            }

            Customer renter = null;
            if (carData.renterUsername != null && usernameToCustomer.containsKey(carData.renterUsername)) {
                renter = usernameToCustomer.get(carData.renterUsername);
            }

            Car car = new Car(
                    carData.make,
                    carData.model,
                    carData.plateNumber,
                    carData.year,
                    carData.price,
                    owner
            );

            car.setAvailable(carData.available);
            car.setRenter(renter);
            cars.add(car);
        }
    }

    private void saveCars() {
        DataManager.saveCars(cars);
    }

    public void addCar(String make, String model, String plateNumber, int year, int price) {
        cars.add(new Car(make, model, plateNumber, year, price));
        saveCars();
    }

    public void addCar(String make, String model, String plateNumber, int year, int price, Customer owner) {
        cars.add(new Car(make, model, plateNumber, year, price, owner));
        saveCars();
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public Car rentCar(int index, Customer renter) throws CarNotAvailableException {
        if (index >= 0 && index < cars.size()) {
            Car car = cars.get(index);
            if (car.isAvailable()) {
                car.setAvailable(false);
                car.setRenter(renter);
                saveCars();
                System.out.println("Car rented: " + car.getMake() + " " + car.getModel());
                return car;
            } else {
                throw new CarNotAvailableException("Car is not available: " + car.getMake() + " " + car.getModel());
            }
        }
        throw new CarNotAvailableException("Invalid car index");
    }

    public void returnCar(int index) {
        if (index >= 0 && index < cars.size()) {
            Car car = cars.get(index);
            car.setAvailable(true);
            car.setRenter(null);
            saveCars();
            System.out.println("Car returned: " + car.getMake() + " " + car.getModel());
        }
    }

    public List<Car> getAllCars() {
        return new ArrayList<>(cars);
    }

    public List<Car> getAvailableCars() {
        List<Car> available = new ArrayList<>();
        for (Car car : cars) {
            if (car.isAvailable()) {
                available.add(car);
            }
        }
        return available;
    }

    public Car getCarByIndex(int index) {
        if (index >= 0 && index < cars.size()) {
            return cars.get(index);
        }
        return null;
    }

    public int getCarCount() {
        return cars.size();
    }

    public List<Car> getCarsByOwner(Customer owner) {
        List<Car> ownedCars = new ArrayList<>();
        for (Car car : cars) {
            if (car.getOwner() != null && car.getOwner().equals(owner)) {
                ownedCars.add(car);
            }
        }
        return ownedCars;
    }

    public void removeCar(Car car) {
        cars.remove(car);
        saveCars();
    }

    public void makeAvailable(Car car) {
        car.setAvailable(true);
        saveCars();
    }

    public void makeNotAvailable(Car car) {
        car.setAvailable(false);
        saveCars();
    }
}