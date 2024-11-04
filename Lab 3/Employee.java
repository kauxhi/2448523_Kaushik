//base class: employee
class Employee {
    protected int employeeId;
    protected String employeeName;
    protected String designation;

    public Employee(int employeeId, String employeeName, String designation) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.designation = designation;
    }

    public void calculateBonus() {
        System.out.println("General bonus calculation for employee.");
    }

    public void displayInfo() {
        System.out.println("Employee ID: " + employeeId);
        System.out.println("Employee Name: " + employeeName);
        System.out.println("Designation: " + designation);
    }
}

//derived class: HourlyEmployee
class HourlyEmployee extends Employee {
    private double hourlyRate;
    private int hoursWorked;

    public HourlyEmployee(int employeeId, String employeeName, String designation, double hourlyRate, int hoursWorked) {
        super(employeeId, employeeName, designation);
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
    }

    @Override
    public void calculateBonus() {
        System.out.println("Hourly Employee Bonus: " + (0.1 * hourlyRate * hoursWorked));
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.printf("Hourly Rate: %.2f\nHours Worked: %d\nWeekly Salary: %.2f\n", hourlyRate, hoursWorked, hourlyRate * hoursWorked);
        calculateBonus();
    }
}

//derived class: SalariedEmployee
class SalariedEmployee extends Employee {
    protected double monthlySalary;

    public SalariedEmployee(int employeeId, String employeeName, String designation, double monthlySalary) {
        super(employeeId, employeeName, designation);
        this.monthlySalary = monthlySalary;
    }

    @Override
    public void calculateBonus() {
        System.out.println("Salaried Employee Bonus: " + (0.05 * monthlySalary));
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.printf("Monthly Salary: %.2f\nWeekly Salary: %.2f\n", monthlySalary, monthlySalary / 4);
        calculateBonus();
    }
}

//derived class: ExecutiveEmployee
class ExecutiveEmployee extends SalariedEmployee {
    private double bonusPercentage;

    public ExecutiveEmployee(int employeeId, String employeeName, String designation, double monthlySalary, double bonusPercentage) {
        super(employeeId, employeeName, designation, monthlySalary);
        this.bonusPercentage = bonusPercentage;
    }

    @Override
    public void calculateBonus() {
        super.calculateBonus();
        System.out.println("Executive Employee Annual Bonus: " + (monthlySalary * 12 * bonusPercentage / 100));
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.printf("Bonus Percentage: %.2f%%\n", bonusPercentage);
    }
}

//test program
public class PayrollSystemTest {
    public static void main(String[] args) {
        System.out.println("Hourly Employee Details:");
        new HourlyEmployee(1, "Varma", "Teaching Assistant", 20.5, 40).displayInfo(); 
        
        System.out.println("\nSalaried Employee Details:");
        new SalariedEmployee(2, "Dimple", "Lecturer", 4000.0).displayInfo(); 

        System.out.println("\nExecutive Employee Details:");
        new ExecutiveEmployee(3, "Raghav", "Head of Department", 8000.0, 15.0).displayInfo(); 
    }
}