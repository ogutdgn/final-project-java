public class Car {
    private String makeModel;
    private int manufactureYear;
    private int price;
    private boolean available;

    public Car(String makeModel, int manufactureYear, int price) {
        this.makeModel = makeModel;
        this.manufactureYear = manufactureYear;
        this.price = price;
        this.available = true;
    }

    public String getMakeModel() {
        return makeModel;
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
        System.out.println(makeModel + " is driving...");
    }

    public String toString() {
        return makeModel + " (" + manufactureYear + ") - $" + price + " | Available: " + available;
    }
}
