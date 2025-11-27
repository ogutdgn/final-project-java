public class Car {
    private String make;
    private String model;
    private int manufactureYear;
    private int price;
    private boolean available;

    public Car(String make, String model, int manufactureYear, int price) {
        this.make = make;
        this.model = model;
        this.manufactureYear = manufactureYear;
        this.price = price;
        this.available = true;
    }

    public String getMake() {
        return make;
    }
    public String getModel() {
        return model;
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

    public void pushHorn() {
        System.out.println("Beep beep!");
    }

    public void drive() {
        System.out.println(make + model + " is driving...");
    }

    public String toString() {
        return make + " " + model + " (" + manufactureYear + ") - $" + price + " | Available: " + available;
    }
}