import java.util.ArrayList;

public class CarManagerSystem {
    private ArrayList<Car> cars = new ArrayList<>();

    public void addCar(String make, int year, int price) {
        cars.add(new Car(make, year, price));
    }

    public void rentCar(int index) {
        if (index >= 0 && index < cars.size()) {
            Car c = cars.get(index);
            if (c.isAvailable()) {
                c.setAvailable(false);
                System.out.println("Car rented: " + c.getMakeModel());
            } else {
                System.out.println("Car is not available.");
            }
        }
    }

    public void returnCar(int index) {
        if (index >= 0 && index < cars.size()) {
            Car c = cars.get(index);
            c.setAvailable(true);
            System.out.println("Car returned: " + c.getMakeModel());
        }
    }

    public ArrayList<Car> getCars() {
        return cars;
    }
}
