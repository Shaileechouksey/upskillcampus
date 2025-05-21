import java.io.*;
import java.util.*;

class Employee {
    private int id;
    private String name;
    private String department;
    private double salary;

    public Employee(int id, String name, String department, double salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public double getSalary() { return salary; }

    @Override
    public String toString() {
        return id + "," + name + "," + department + "," + salary;
    }

    public static Employee fromString(String line) {
        String[] parts = line.split(",");
        return new Employee(
            Integer.parseInt(parts[0]),
            parts[1],
            parts[2],
            Double.parseDouble(parts[3])
        );
    }
}

public class BankingInformationSystem {
    static final String FILE_NAME = "employees.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Human Resource Management System ---");
            System.out.println("1. Add Employee");
            System.out.println("2. View All Employees");
            System.out.println("3. Search by ID");
            System.out.println("4. Delete Employee");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> addEmployee(sc);
                case 2 -> viewEmployees();
                case 3 -> searchEmployee(sc);
                case 4 -> deleteEmployee(sc);
                case 5 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid option");
            }
        }
    }

    public static void addEmployee(Scanner sc) {
        try (FileWriter fw = new FileWriter(FILE_NAME, true)) {
            System.out.print("Enter ID: ");
            int id = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter Name: ");
            String name = sc.nextLine();
            System.out.print("Enter Department: ");
            String dept = sc.nextLine();
            System.out.print("Enter Salary: ");
            double salary = sc.nextDouble();

            Employee emp = new Employee(id, name, dept, salary);
            fw.write(emp + "\n");
            System.out.println("Employee added successfully!");
        } catch (IOException e) {
            System.out.println("Error writing file");
        }
    }

    public static void viewEmployees() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            System.out.println("\nEmployee List:");
            while ((line = br.readLine()) != null) {
                Employee e = Employee.fromString(line);
                System.out.println("ID: " + e.getId() + ", Name: " + e.getName() +
                        ", Dept: " + e.getDepartment() + ", Salary: " + e.getSalary());
            }
        } catch (IOException e) {
            System.out.println("No employees found.");
        }
    }

    public static void searchEmployee(Scanner sc) {
        System.out.print("Enter ID to search: ");
        int id = sc.nextInt();
        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                Employee e = Employee.fromString(line);
                if (e.getId() == id) {
                    System.out.println("Found: " + e.getName() + ", Dept: " + e.getDepartment() +
                            ", Salary: " + e.getSalary());
                    found = true;
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file");
        }

        if (!found) System.out.println("Employee not found.");
    }

    public static void deleteEmployee(Scanner sc) {
        System.out.print("Enter ID to delete: ");
        int id = sc.nextInt();
        List<Employee> employees = new ArrayList<>();
        boolean removed = false;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                Employee e = Employee.fromString(line);
                if (e.getId() != id) {
                    employees.add(e);
                } else {
                    removed = true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file");
            return;
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Employee e : employees) {
                pw.println(e);
            }
        } catch (IOException e) {
            System.out.println("Error writing file");
        }

        if (removed)
            System.out.println("Employee deleted successfully.");
        else
            System.out.println("Employee ID not found.");
    }
}
