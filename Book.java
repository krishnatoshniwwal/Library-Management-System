public class Book extends LibraryItem implements Borrowable{
    private static final int FINE_PER_DAY = 2;
    private String author;

    public Book(String id, String title, String author){
        super(id, title);          
        this.author = author;
    }

    public String getAuthor(){
        return author;
    }

    @Override
    public boolean isBorrowable(){
        return true;
    }

    @Override
    public int getFinePerDay(){
        return FINE_PER_DAY;
    }

    @Override
    public String getType(){
        return "BOOK";
    }
}
