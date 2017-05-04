import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.List;

/* Each table you wish to access in your database requires a model class, like this example: */
public class Author
{
    /* First, map each of the fields (columns) in your table to some public variables. */
    public int AuthorID;
    public String FirstName;
    public String SecondName;

    /* Next, prepare a constructor that takes each of the fields as arguements. */
    public Author(int AuthorID, String FirstName, String SecondName)
    {
        this.AuthorID = AuthorID;
        this.FirstName = FirstName;
        this.SecondName = SecondName;

    }

    /* A toString method is vital so that your model items can be sensibly displayed as text. */
    @Override public String toString()
    {
        return "Author ID: "  + AuthorID +  " First Name: "  + FirstName+ " Second Name: " + SecondName;
    }

    /* Different models will require different read and write methods. Here is an example 'loadAll' method 
     * which is passed the target list object to populate. */
    public static void readAll(List<Author> list)
    {
        list.clear();       // Clear the target list first.

        /* Create a new prepared statement object with the desired SQL query. */
        PreparedStatement statement = Application.database.newStatement("SELECT AuthorID, FirstName, SecondName FROM Authors ORDER BY AuthorID"); 

        if (statement != null)      // Assuming the statement correctly initated...
        {
            ResultSet results = Application.database.runQuery(statement);       // ...run the query!

            if (results != null)        // If some results are returned from the query...
            {
                try {                               // ...add each one to the list.
                    while (results.next()) {                                               
                        list.add( new Author(results.getInt("AuthorID"), results.getString("FirstName"),results.getString("SecondName")));
                    }
                }
                catch (SQLException resultsexception)       // Catch any error processing the results.
                {
                    System.out.println("Database result processing error: " + resultsexception.getMessage());
                }
            }
        }
    }

    public static void deleteById(int BookID)
    {
        try{

            PreparedStatement statement = Application.database.newStatement("DELETE FROM Authors WHERE AuthorID = ?");             
            statement.setInt(1, BookID);

            if (statement != null){
                Application.database.executeUpdate(statement);
            }
        } catch (SQLException resultsexception){
            System.out.println("Database result processing error: " + resultsexception.getMessage());
        }
    }

    public void save()
    {
        PreparedStatement statement;

        try{
            statement = Application.database.newStatement("INSERT INTO Authors (FirstName, SecondName) VALUES (?, ?)");             
            statement.setString(1, FirstName);    
            statement.setString(2, SecondName);    

            if (statement != null) {
                Application.database.executeUpdate(statement);
            }
        }
        catch (SQLException resultsexception) {
            System.out.println("Database result processing error: " + resultsexception.getMessage());
        }
    }
}