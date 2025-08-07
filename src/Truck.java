/**
 * The Truck class implements the Vehicle and TruckVehicle interfaces.
 * It represents a truck with specific attributes like cargo capacity and transmission type.
 */
public class Truck implements Vehicle, TruckVehicle {
    // Declaring these fields as final makes the core properties of the Truck immutable.
    private final String make;
    private final String model;
    private final int year;

    private double cargoCapacity;
    private String transmissionType;

    /**
     * Constructs a Truck object with basic vehicle details.
     * @param make The make of the truck.
     * @param model The model of the truck.
     * @param year The year of manufacture.
     */
    public Truck(String make, String model, int year) {
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
    public void setCargoCapacity(double capacity) { this.cargoCapacity = capacity; }
    @Override
    public double getCargoCapacity() { return cargoCapacity; }
    @Override
    public void setTransmissionType(String transmissionType) { this.transmissionType = transmissionType; }
    @Override
    public String getTransmissionType() { return transmissionType; }

    public void displayDetails() {
        System.out.println("\n--- Truck Details ---");
        System.out.println("Make: " + getMake());
        System.out.println("Model: " + getModel());
        System.out.println("Year: " + getYear());
        System.out.println("Cargo Capacity (tons): " + getCargoCapacity());
        System.out.println("Transmission Type: " + getTransmissionType());
        System.out.println("---------------------\n");
    }
}