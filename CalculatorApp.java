import java.util.Scanner; 

class Calculator{
    int Add(int a, int b){
        return a+b;
    }

    int Subtract(int a, int b){
        return a-b;
    }

    int Multiply(int a, int b){
        return a*b;
    }

    double Division(int a, int b){
        if(b==0){
            System.out.println("Error: Division by Zero.");
            return 0;
        }
        return (double) a/b;
    }
}

public class CalculatorApp {
    public static void main(String[] args) {

        Scanner sc= new Scanner(System.in);
        Calculator calc= new Calculator();

        int choice;

        do{
            System.out.println("-----Menu-----");
            System.out.println("1. Addition");
            System.out.println("2. Subtraction");
            System.out.println("3.Multiply");
            System.out.println("4.Divide");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");
            choice = sc.nextInt(); 

            switch(choice){

                case 1:{
                    System.out.print("Enter the first number:");
                    int a = sc.nextInt();

                    System.out.print("Enter the second number:");
                    int b = sc.nextInt();

                    int result = calc.Add(a, b);
                    System.out.println("Result: " + result);
                    break;
                }

                case 2:{
                    System.out.print("Enter the first number:");
                    int a = sc.nextInt();

                    System.out.print("Enter the second number:");
                    int b = sc.nextInt();

                    int result=calc.Subtract(a, b);
                    System.out.println("Result: " +result);
                    break;        
                }

                case 3:{
                    System.out.print("Enter the first number:");
                    int a = sc.nextInt();

                    System.out.print("Enter the second number:");
                    int b = sc.nextInt();

                    int result = calc.Multiply(a, b);
                    System.out.println("Result: " +result);
                    break;
                }

                case 4:{
                    System.out.print("Enter the first number:");
                    int a = sc.nextInt();

                    System.out.print("Enter the second number:");
                    int b = sc.nextInt();

                    double result=calc.Division(a, b);
                    System.out.println("Result: "+result);
                    break;
                }

                case 5:{
                    System.out.println("Program Ended.");
                    break;
                }
                
                default:
                    System.out.println("Invalid Choice. Try again.");
                
        
            } 
        } while (choice!=5);
                        
        sc.close();
    }
}