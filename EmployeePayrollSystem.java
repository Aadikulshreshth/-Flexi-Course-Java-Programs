import java.util.ArrayList;
import java.util.Scanner;

class Employee {
    protected final int empId;
    protected final String empName;
    protected double basicSalary;

    public Employee(int id, String name, double basic) {
        if (id <= 0) throw new IllegalArgumentException("Employee ID must be positive.");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Employee name cannot be empty.");
        if (basic < 0) throw new IllegalArgumentException("Basic salary cannot be negative.");

        this.empId = id;
        this.empName = name;
        this.basicSalary = basic;
    }

    public double calculateGross() {
        return basicSalary;
    }

        public void printComponents() {
        System.out.printf("  Basic           : INR %.2f%n", basicSalary);
    }

    public void printHeader() {
        System.out.println("  EmpID           : " + empId);
        System.out.println("  Name            : " + empName);
        System.out.println("  Type            : " + getEmployeeType());
    }

    public String getEmployeeType() {
        return "EMPLOYEE";
    }
}

class PermanentEmployee extends Employee {
    protected final double hra;  
    protected final double da;   

    public PermanentEmployee(int id, String name, double basic) {
        super(id, name, basic);
        this.hra = basic * 0.20;
        this.da  = basic * 0.15;
    }

    public String getEmployeeType() {
        return "PERMANENT";
    }

    public double calculateGross() {
        return basicSalary + hra + da;
    }

    public void printComponents() {
        System.out.printf("  Basic           : INR %.2f%n", basicSalary);
        System.out.printf("  HRA (20%%)       : INR %.2f%n", hra);
        System.out.printf("  DA  (15%%)       : INR %.2f%n", da);
    }
}

class ContractEmployee extends Employee {
    private final int hoursWorked;
    private final double hourlyRate;

    public ContractEmployee(int id, String name, int hoursWorked, double hourlyRate) {

        super(id, name, hoursWorked * hourlyRate);

        if (hoursWorked < 0) throw new IllegalArgumentException("Hours worked cannot be negative.");
        if (hourlyRate < 0) throw new IllegalArgumentException("Hourly rate cannot be negative.");

        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }

    public String getEmployeeType() {
        return "CONTRACT";
    }

    public double calculateGross() {

        return basicSalary;
    }

    public void printComponents() {
        System.out.printf("  Hours Worked    : %d%n", hoursWorked);
        System.out.printf("  Hourly Rate     : INR %.2f/hr%n", hourlyRate);
        System.out.printf("  Earned (Hours×Rate): INR %.2f%n", basicSalary);
    }
}

class Manager extends PermanentEmployee {
    private final double bonus;
    private final double travelAllowance; 

    public Manager(int id, String name, double basic, double bonus) {
        super(id, name, basic);

        if (bonus < 0) throw new IllegalArgumentException("Bonus cannot be negative.");

        this.bonus = bonus;
        this.travelAllowance = basic * 0.10;
    }

    public String getEmployeeType() {
        return "MANAGER";
    }

    public double calculateGross() {
        return super.calculateGross() + bonus + travelAllowance;
    }

    public void printComponents() {
        super.printComponents();
        System.out.printf("  Bonus           : INR %.2f%n", bonus);
        System.out.printf("  Travel (10%%)    : INR %.2f%n", travelAllowance);
    }
}

public class EmployeePayrollSystem {

    public static double calculateTax(double gross) {
        if (gross <= 20000)      return 0.0;
        else if (gross <= 50000) return gross * 0.10;
        else                     return gross * 0.20;
    }

    public static void printPayslip(Employee emp) {
        System.out.println("\n====== PAYSLIP ======");
        emp.printHeader();

        System.out.println("---- Salary Breakdown ----");
        emp.printComponents();

        double gross = emp.calculateGross();
        double tax   = calculateTax(gross);
        double net   = gross - tax;

        System.out.println("---- Summary ----");
        System.out.printf("Gross : INR %.2f%n", gross);
        System.out.printf("Tax   : INR %.2f%n", tax);
        System.out.printf("Net   : INR %.2f%n", net);
        System.out.println("=========================");
    }

    public static void main(String[] args) 
    {
        Scanner sc = new Scanner(System.in);
        ArrayList<Employee> staff = new ArrayList<>();

        System.out.println("=== Employee Payroll System ===");

        System.out.print("Enter number of employees: ");
        int n = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < n; i++) {

            System.out.println("\nChoose Employee Type:");
            System.out.println("1. Permanent");
            System.out.println("2. Contract");
            System.out.println("3. Manager");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); 

            System.out.print("Enter Employee ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Employee Name: ");
            String name = sc.nextLine();

            Employee emp = null;

            switch (choice) {

                case 1:
                    System.out.print("Enter Basic Salary: ");
                    double basic = sc.nextDouble();
                    emp = new PermanentEmployee(id, name, basic);
                    break;

                case 2:
                    System.out.print("Enter Hours Worked: ");
                    int hours = sc.nextInt();

                    System.out.print("Enter Hourly Rate: ");
                    double rate = sc.nextDouble();

                    emp = new ContractEmployee(id, name, hours, rate);
                    break;

                case 3:
                    System.out.print("Enter Basic Salary: ");
                    double mBasic = sc.nextDouble();

                    System.out.print("Enter Bonus: ");
                    double bonus = sc.nextDouble();

                    emp = new Manager(id, name, mBasic, bonus);
                    break;

                default:
                    System.out.println("Invalid choice! Try again.");
                    i--;
                    continue;
            }
            staff.add(emp);
        }
        System.out.println("\n====== ALL PAYSLIPS ======");

        for (Employee e : staff) {
            printPayslip(e);
        }
        sc.close();
    }
}