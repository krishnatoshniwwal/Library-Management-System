public class Magazine extends LibraryItem implements Borrowable {
    private static final int FINE_PER_DAY = 1;
    private String issue;

    public Magazine(String id, String title, String issue){
        super(id, title);
        this.issue = issue;
    }

    public String getIssue(){
        return issue;
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
        return "MAGAZINE";
    }
}
