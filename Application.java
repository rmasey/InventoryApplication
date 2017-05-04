import java.util.Scanner;
import java.util.ArrayList;

public class Application
{
    /* Our app needs a connection to the database, and this is all handled by the DatabaseConnection 
     * class. A public static variable, called database, points to the DatabaseConnection object. */
    public static DatabaseConnection database;

    public static void main(String args[])
    { 
        database = new DatabaseConnection("Inventory.db");        // Initiate the database connection.

        String cont = "Yes";

        while (cont == "Yes") {
            Scanner input = new Scanner(System.in);
            System.out.println("1. View all Books");
            System.out.println("2. View all Authors");
            System.out.println("3. Add Book");
            System.out.println("4. Add Author");
            System.out.println("5. Delete Book");
            System.out.println("6. Delete Author");
            System.out.println("7. Terminate");
            System.out.println("Key in choice");
            int choice = input.nextInt();
            input.nextLine();

            if (choice==1){
                viewallBooks();
            }
            else if (choice==2){
                viewallAuthors();
            }
            else if (choice==3){
                addBook();
            }
            else if (choice==4){
                addAuthor();
            }
            else if (choice==5){
                deleteBook();
            }
            else if (choice==6){
                deleteAuthor();
            }
            else {
                System.out.println("Bye!");
                terminate();
            }
        }
    }

    public static void viewallBooks()
    {
        ArrayList<Book> allTheBooks = new ArrayList<>();

        Book.readAll(allTheBooks);

        for(Book b : allTheBooks) {
            System.out.println(b);
        }
    }

    public static void viewallAuthors()
    {
        ArrayList<Author> allTheAuthors = new ArrayList<>();

        Author.readAll(allTheAuthors);

        for(Author a : allTheAuthors) {
            System.out.println(a);
        }
    }

    public static void addBook(){
        Scanner input = new Scanner(System.in);
        int BookID=0;
        System.out.println("Enter the name of the book:");
        String BookName = input.nextLine();
        input.nextLine();
        System.out.println("Enter the Author ID");
        int AuthorID = input.nextInt();
        input.nextLine();

        Book newBook = new Book(BookID, BookName, AuthorID);
        newBook.save();
    }

    public static void addAuthor(){
        Scanner input = new Scanner(System.in);
        int AuthorID =0;
        System.out.println("Enter author First Name:");
        String FirstName = input.nextLine();
        System.out.println("Enter author Second Name:");
        String SecondName = input.nextLine();

        Author newAuthor = new Author(AuthorID, FirstName, SecondName);
        newAuthor.save();
    }

    public static void deleteBook(){
        Scanner input = new Scanner(System.in);
        System.out.println("Enter book ID to delete:");
        int BookID = input.nextInt();

        Book.deleteById(BookID);
    }

    public static void deleteAuthor(){
        Scanner input = new Scanner(System.in);
        System.out.println("Enter author ID to delete:");
        int AuthorID = input.nextInt();
        
        Author.deleteById(AuthorID);
    }

    public static void terminate()
    {
        System.out.println("Closing database connection and terminating application...");                                
        if (database != null){ 
            database.disconnect(); 
        }
        System.exit(0);                                 // Finally, terminate the entire application.
    }
}
