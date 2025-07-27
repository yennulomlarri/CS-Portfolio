/**
 * The Car class implements the Vehicle and CarVehicle interfaces.
 * It represents a car with specific attributes like number of doors and fuel type.
 */
public class Car implements Vehicle, CarVehicle {
    // Declaring these fields as final makes the core properties of the Car immutable.
    private final String make;
    private final String model;
    private final int year;

    private int numDoors;
    private String fuelType;

    /**
     * Constructs a Car object with basic vehicle details.
     * @param make The make of the car.
     * @param model The model of the car.
     * @param year The year of manufacture.
     */
    public Car(String make, String model, int year) {
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
    public void setNumDoors(int doors) { this.numDoors = doors; }
    @Override
    public int getNumDoors() { return numDoors; }
    @Override
    public void setFuelType(String fuelType) { this.fuelType = fuelType; }
    @Override
    public String getFuelType() { return fuelType; }

    public void displayDetails() {
        System.out.println("\n--- Car Details ---");
        System.out.println("Make: " + getMake());
        System.out.println("Model: " + getModel());
        System.out.println("Year: " + getYear());
        System.out.println("Number of Doors: " + getNumDoors());
        System.out.println("Fuel Type: " + getFuelType());
        System.out.println("-------------------\n");
    }
}