import java.util.*;

class InvalidBookException extends Exception{
    public InvalidBookException(String message){
        super(message);
    }
}

class Book{
    private String title;
    private String author;
    private double price;
    private int stockCount;
    private String ISBN;

   
    public Book() {
        this.title = "Unknown";
        this.author = "Unknown";
        this.price = 0.0;
        this.stockCount = 0;
        this.ISBN = "N/A";
    }

  
    public Book(String title, String author, double price,int stockCount, String ISBN) throws InvalidBookException {

        if (price < 0)
            throw new InvalidBookException("Price cannot be negative.");

        if (stockCount < 0)
            throw new InvalidBookException("Stock count cannot be negative.");

        if (ISBN == null || ISBN.length() < 5)
            throw new InvalidBookException("Invalid ISBN.");

        this.title = title;
        this.author = author;
        this.price = price;
        this.stockCount = stockCount;
        this.ISBN = ISBN;
    }

    public Book(Book b) {
        this.title = b.title;
        this.author = b.author;
        this.price = b.price;
        this.stockCount = b.stockCount;
        this.ISBN = b.ISBN;
    }

    public void display() {
        System.out.println("Title      : " + title);
        System.out.println("Author     : " + author);
        System.out.println("Price      : " + price);
        System.out.println("StockCount : " + stockCount);
        System.out.println("ISBN       : " + ISBN);
        System.out.println("---------------------------");
    }
}

public class BookStoreDemo {
 public static void main(String[] args) {

  
        Scanner sc=new Scanner(System.in);
        ArrayList<Book> bookList=new ArrayList<>();

        while(true){

            System.out.println("\n1 Add Book");
            System.out.println("2 Display Books");
            System.out.println("3 Exit");
            System.out.print("Enter choice: ");

            int ch=sc.nextInt();
            sc.nextLine();

            switch(ch){

                case 1:
                    try{
                        System.out.print("Title: ");
                        String title=sc.nextLine();

                        System.out.print("Author: ");
                        String author=sc.nextLine();

                        System.out.print("Price: ");
                        double price=sc.nextDouble();

                        System.out.print("Stock: ");
                        int stock=sc.nextInt();
                        sc.nextLine();

                        System.out.print("ISBN: ");
                        String isbn=sc.nextLine();

                        Book b=new Book(title,author,price,stock,isbn);
                        bookList.add(b);

                        System.out.println("Book Added Successfully!");

                    }
                    catch(InputMismatchException e){
                    System.out.println("Invalid input type! Enter numbers correctly.");
                    sc.nextLine();
                    }
                    catch(InvalidBookException e){
                        System.out.println("Error: "+e.getMessage());
                        sc.nextLine(); 
                    }
                    break;

                case 2:
                    if(bookList.isEmpty())
                        System.out.println("No books in inventory.");
                    else{
                        System.out.println("\n--- BOOK LIST ---");
                        for(Book b:bookList)
                            b.display();
                    }
                    break;

                case 3:
                    System.out.println("Exiting...");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}