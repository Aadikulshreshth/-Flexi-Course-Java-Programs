import java.util.Scanner;
import java.util.Vector;

class VectorException extends Exception {
    public VectorException(String msg) {
        super(msg);
    }
}

class Vector3D {
    private double x, y, z;
    public Vector3D(double x, double y, double z) throws VectorException {
        if (Double.isNaN(x) || Double.isNaN(y) || Double.isNaN(z))
            throw new VectorException("Vector components cannot be NaN");
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Vector3D add(Vector3D v) throws VectorException {
        if (v == null)
            throw new VectorException("Null vector cannot be added");

        return new Vector3D(x + v.x, y + v.y, z + v.z);
    }
    public Vector3D subtract(Vector3D v) throws VectorException {
        if (v == null)
            throw new VectorException("Null vector cannot be subtracted");

        return new Vector3D(x - v.x, y - v.y, z - v.z);
    }

    public double dotProduct(Vector3D v) throws VectorException {
        if (v == null)
            throw new VectorException("Null vector for dot product");

        return (x * v.x) + (y * v.y) + (z * v.z);
    }
    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }
}

public class VectorDemo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Vector<Vector3D> vectorList = new Vector<>();
        try {
            System.out.print("Enter number of vectors: ");
            int n = sc.nextInt();
            for (int i = 0; i < n; i++) {
                System.out.println("\nEnter values for Vector " + (i + 1));
                System.out.print("x: ");
                double x = sc.nextDouble();
                System.out.print("y: ");
                double y = sc.nextDouble();
                System.out.print("z: ");
                double z = sc.nextDouble();
                Vector3D v = new Vector3D(x, y, z);
                vectorList.add(v);
            }

            System.out.println("\nStored Vectors:");
            for (int i = 0; i < vectorList.size(); i++) {
                System.out.println("Vector " + (i + 1) + ": " + vectorList.get(i));
            }
            if (vectorList.size() >= 2) {
                Vector3D v1 = vectorList.get(0);
                Vector3D v2 = vectorList.get(1);
                System.out.println("\nChoose Operation:");
                System.out.println("1. Addition");
                System.out.println("2. Subtraction");
                System.out.println("3. Dot Product");
                System.out.print("Enter choice: ");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        System.out.println("Result: " + v1.add(v2));
                        break;
                    case 2:
                        System.out.println("Result: " + v1.subtract(v2));
                        break;
                    case 3:
                        System.out.println("Result: " + v1.dotProduct(v2));
                        break;
                    default:
                        System.out.println("Invalid choice!");
                }
            } else {
                System.out.println("At least 2 vectors required for operations.");
            }

        } catch (VectorException e) {
            System.out.println("Vector Exception: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Invalid Input! Please enter numeric values only.");
        }

        sc.close();
    }
}