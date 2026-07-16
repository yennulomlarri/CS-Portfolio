import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("=== Vehicle Information System Menu ===");
            System.out.println("1. Create a Car");
            System.out.println("2. Create a Motorcycle");
            System.out.println("3. Create a Truck");
            System.out.println("4. Exit");
            System.out.print("Please choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    createCar(scanner);
                    break;
                case "2":
                    createMotorcycle(scanner);
                    break;
                case "3":
                    createTruck(scanner);
                    break;
                case "4":
                    exit = true;
                    System.out.println("Exiting the application. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
        scanner.close();
    }

    private static void createCar(Scanner scanner) {
        try {
            System.out.print("Enter car make: ");
            String make = scanner.nextLine();
            System.out.print("Enter car model: ");
            String model = scanner.nextLine();
            System.out.print("Enter year of manufacture: ");
            int year = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter number of doors: ");
            int numDoors = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter fuel type (Petrol, Diesel, Electric): ");
            String fuelType = scanner.nextLine();

            Car car = new Car(make, model, year);
            car.setNumDoors(numDoors);
            car.setFuelType(fuelType);
            car.displayDetails();
        } catch (NumberFormatException e) {
            System.out.println("\nError: Invalid input. Year and number of doors must be numbers. Please try again.\n");
        }
    }

    private static void createMotorcycle(Scanner scanner) {
        try {
            System.out.print("Enter motorcycle make: ");
            String make = scanner.nextLine();
            System.out.print("Enter motorcycle model: ");
            String model = scanner.nextLine();
            System.out.print("Enter year of manufacture: ");
            int year = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter number of wheels: ");
            int numWheels = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter motorcycle type (Sport, Cruiser, Off-road): ");
            String type = scanner.nextLine();

            Motorcycle motorcycle = new Motorcycle(make, model, year);
            motorcycle.setNumWheels(numWheels);
            motorcycle.setMotorcycleType(type);
            motorcycle.displayDetails();
        } catch (NumberFormatException e) {
            System.out.println("\nError: Invalid input. Year and number of wheels must be numbers. Please try again.\n");
        }
    }

    private static void createTruck(Scanner scanner) {
        try {
            System.out.print("Enter truck make: ");
            String make = scanner.nextLine();
            System.out.print("Enter truck model: ");
            String model = scanner.nextLine();
            System.out.print("Enter year of manufacture: ");
            int year = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter cargo capacity (in tons): ");
            double cargoCapacity = Double.parseDouble(scanner.nextLine());
            System.out.print("Enter transmission type (Manual, Automatic): ");
            String transmission = scanner.nextLine();

            Truck truck = new Truck(make, model, year);
            truck.setCargoCapacity(cargoCapacity);
            truck.setTransmissionType(transmission);
            truck.displayDetails();
        } catch (NumberFormatException e) {
            System.out.println("\nError: Invalid input. Year and cargo capacity must be numbers. Please try again.\n");
        }
    }
}