import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.List;

/* Each table you wish to access in your database requires a model class, like this example: */
public class Book
{
    /* First, map each of the fields (columns) in your table to some public variables. */
    public String Title;
    public int AuthorID;

    /* Next, prepare a constructor that takes each of the fields as arguements. */
    public Book (String Title, int AuthorID)
    {
        this.Title = Title;
        this.AuthorID = AuthorID;
    }

    /* A toString method is vital so that your model items can be sensibly displayed as text. */
    @Override public String toString()
    {
        return "Title:" + Title + "   Author ID:" + AuthorID;
    }

    /* Different models will require different read and write methods. Here is an example 'loadAll' method 
     * which is passed the target list object to populate. */
    public static void readAll(List<Book> list)
    {
        list.clear();       // Clear the target list first.

        /* Create a new prepared statement object with the desired SQL query. */
        PreparedStatement statement = Application.database.newStatement("SELECT BookID, Title, AuthorID FROM Books ORDER BY BookID"); 

        if (statement != null)      // Assuming the statement correctly initated...
        {
            ResultSet results = Application.database.runQuery(statement);       // ...run the query!

            if (results != null)        // If some results are returned from the query...
            {
                try {                               // ...add each one to the list.
                    while (results.next()) {                                               
                        list.add( new Book(results.getString("Title"), results.getInt("AuthorID")));
                    }
                }
                catch (SQLException resultsexception)       // Catch any error processing the results.
                {
                    System.out.println("Database result processing error: " + resultsexception.getMessage());
                }
            }
        }

    }

    public static Book getById(int BookID)
    {
        Book book = null;

        PreparedStatement statement = Application.database.newStatement("SELECT BookID, Title, AuthorID FROM Books WHERE BookID = ?"); 

        try 
        {
            if (statement != null)
            {
                statement.setInt(1, BookID);
                ResultSet results = Application.database.runQuery(statement);

                if (results != null)
                {
                    book = new Book(results.getString("Title"), results.getInt("AuthorID"));
                }
            }
        }
        catch (SQLException resultsexception)
        {
            System.out.println("Database result processing error: " + resultsexception.getMessage());
        }

        return book;
    }

    public static void deleteById(int BookID)
    {
        try{

            PreparedStatement statement = Application.database.newStatement("DELETE FROM Books WHERE BookID = ?");             
            statement.setInt(1, BookID);

            if (statement != null){
                Application.database.executeUpdate(statement);
            }
        } catch (SQLException resultsexception){
            System.out.println("Database result processing error: " + resultsexception.getMessage());
        }
    }

    public void save(){
        PreparedStatement statement;

        try{
            statement = Application.database.newStatement("INSERT INTO Books (Title, AuthorID) VALUES (?, ?)");             
            statement.setString(1, Title);
            statement.setInt(2, AuthorID);         

            if (statement != null) {
                Application.database.executeUpdate(statement);
            }
        }
        catch (SQLException resultsexception) {
            System.out.println("Database result processing error: " + resultsexception.getMessage());
        }
    }
}