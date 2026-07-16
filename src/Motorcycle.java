/**
 * The Motorcycle class implements the Vehicle and MotorVehicle interfaces.
 * It represents a motorcycle with specific attributes like number of wheels and type.
 */
public class Motorcycle implements Vehicle, MotorVehicle {
    // Declaring these fields as final makes the core properties of the Motorcycle immutable.
    private final String make;
    private final String model;
    private final int year;

    private int numWheels;
    private String motorcycleType;

    /**
     * Constructs a Motorcycle object with basic vehicle details.
     * @param make The make of the motorcycle.
     * @param model The model of the motorcycle.
     * @param year The year of manufacture.
     */
    public Motorcycle(String make, String model, int year) {
        this.make = make;
        this.model = model;
        this.year = year;
    }

    @Override
    public String getMake() { return make; }
    @Override
    public String getModel() { return model; }
    @Override
    public int getYear() { return year; }
    @Override
    public void setNumWheels(int wheels) { this.numWheels = wheels; }
    @Override
    public int getNumWheels() { return numWheels; }
    @Override
    public void setMotorcycleType(String type) { this.motorcycleType = type; }
    @Override
    public String getMotorcycleType() { return motorcycleType; }

    public void displayDetails() {
        System.out.println("\n--- Motorcycle Details ---");
        System.out.println("Make: " + getMake());
        System.out.println("Model: " + getModel());
        System.out.println("Year: " + getYear());
        System.out.println("Number of Wheels: " + getNumWheels());
        System.out.println("Motorcycle Type: " + getMotorcycleType());
        System.out.println("--------------------------\n");
    }
}