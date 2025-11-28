import java.time.LocalDate;

public class Car {
    private final String make;
    private final String model;
    private String plateNumber;
    private final int manufactureYear;
    private final int price;
    private boolean available;
    private Customer owner;
    private Customer renter;
    private LocalDate rentDate;

    public LocalDate getRentDate() {
        return rentDate;
    }
    public void setRentDate(LocalDate rentDate) {
        this.rentDate = rentDate;
    }
    public Car(String make, String model, String plateNumber, int manufactureYear, int price) {
        this.make = make;
        this.model = model;
        this.plateNumber = plateNumber;
        this.manufactureYear = manufactureYear;
        this.price = price;
        this.available = true;
        this.owner = null;
        this.renter = null;
    }

    public Car(String make, String model, String plateNumber, int manufactureYear, int price, Customer owner) {
        this.make = make;
        this.model = model;
        this.plateNumber = plateNumber;
        this.manufactureYear = manufactureYear;
        this.price = price;
        this.available = true;
        this.owner = owner;
        this.renter = null;
    }

    public String getMake() {
        return make;
    }
    public String getModel() {
        return model;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public int getManufactureYear() {
        return manufactureYear;
    }

    public int getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    public Customer getRenter() {
        return renter;
    }

    public void setRenter(Customer renter) {
        this.renter = renter;
    }

    public void pushHorn() {
        System.out.println("Beep beep!");
    }

    public void drive() {
        System.out.println(make + " " + model + " is driving...");
    }

    @Override
    public String toString() {
        String status;
        if (available) {
            status = "✓ Available";
        } else if (renter != null) {
            status = "✗ Rented";
        } else {
            status = "⊘ Not Available";
        }
        return make + " " + model + " [" + plateNumber + "] (" + manufactureYear + ") - $" + price + "/day | " + status;
    }
}
