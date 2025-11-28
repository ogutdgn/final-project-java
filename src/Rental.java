import java.time.LocalDate;

public class Rental {
    private Car car;
    private Customer customer;
    private LocalDate rentalDate;
    private LocalDate returnDate;
    private double totalCost;
    private boolean isActive;

    public Rental(Car car, Customer customer, LocalDate rentalDate) {
        this.car = car;
        this.customer = customer;
        this.rentalDate = rentalDate;
        this.isActive = true;
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public LocalDate getRentalDate() {
        return rentalDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void completeRental(LocalDate returnDate) {
        this.returnDate = returnDate;
        this.isActive = false;
        calculateTotalCost();
    }

    private void calculateTotalCost() {
        if (returnDate != null) {
            long days = java.time.temporal.ChronoUnit.DAYS.between(rentalDate, returnDate);
            if (days < 1) days = 1; // Minimum 1 day
            this.totalCost = days * car.getPrice();
        }
    }

    @Override
    public String toString() {
        String status = isActive ? "Active" : "Completed";
        return customer.getFullName() + " - " + car.getMake() + " " + car.getModel() +
                " (" + rentalDate + ") - " + status;
    }
}