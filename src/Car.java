public class Car {
    private String makeModel;
    private String plateNumber;
    private int manufactureYear;
    private int price;
    private boolean available;
    private Customer owner;
    private Customer renter;

    public Car(String makeModel, String plateNumber, int manufactureYear, int price) {
        this.makeModel = makeModel;
        this.plateNumber = plateNumber;
        this.manufactureYear = manufactureYear;
        this.price = price;
        this.available = true;
        this.owner = null;
        this.renter = null;
    }
    
    public Car(String makeModel, String plateNumber, int manufactureYear, int price, Customer owner) {
        this.makeModel = makeModel;
        this.plateNumber = plateNumber;
        this.manufactureYear = manufactureYear;
        this.price = price;
        this.available = true;
        this.owner = owner;
        this.renter = null;
    }

    public String getMakeModel() {
        return makeModel;
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
        System.out.println(makeModel + " is driving...");
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
        return makeModel + " [" + plateNumber + "] (" + manufactureYear + ") - $" + price + "/day | " + status;
    }
}
