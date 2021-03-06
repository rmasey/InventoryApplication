/* Each table you wish to access in your database requires a model class, like this example: */
public class Book
{
    /* First, map each of the fields (columns) in your table to some public variables. */
    public int BookID;
    public String Title;
    public int AuthorID;

    /* Next, prepare a constructor that takes each of the fields as arguements. */
    public Book (int BookID, String Title, int AuthorID)
    {
        this.BookID = BookID;
        this.Title = Title;
        this.AuthorID = AuthorID;
    }

    /* A toString method is vital so that your model items can be sensibly displayed as text. */
    @Override public String toString()
    {
        return "Book ID: " + BookID + " Title: " + Title + " Author ID: " + AuthorID;
    }

}