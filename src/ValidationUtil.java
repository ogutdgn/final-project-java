public class ValidationUtil {
    
    public static void validateCarModel(String model) throws InvalidInputException {
        if (model == null || model.trim().isEmpty()) {
            throw new InvalidInputException("Car model cannot be empty");
        }
    }
    
    public static void validateYear(int year) throws InvalidInputException {
        int currentYear = java.time.LocalDate.now().getYear();
        if (year < 1900 || year > currentYear + 1) {
            throw new InvalidInputException("Invalid year: " + year);
        }
    }
    
    public static void validatePrice(int price) throws InvalidInputException {
        if (price <= 0) {
            throw new InvalidInputException("Price must be greater than zero");
        }
    }
    
    public static void validateEmail(String email) throws InvalidInputException {
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new InvalidInputException("Invalid email format");
        }
    }
    
    public static void validatePhone(String phone) throws InvalidInputException {
        if (phone == null || !phone.matches("\\d{10}")) {
            throw new InvalidInputException("Phone must be 10 digits");
        }
    }
    
    public static void validateLicenseNumber(String license) throws InvalidInputException {
        if (license == null || license.trim().isEmpty()) {
            throw new InvalidInputException("License number cannot be empty");
        }
    }
}
