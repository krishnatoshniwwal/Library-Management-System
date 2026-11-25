// A Loan links ONE item to ONE member and remembers WHEN it was borrowed.
public class Loan{
    private LibraryItem item;
    private Member member;
    private int borrowDay;
    private int dueDay;

    public Loan(LibraryItem item, Member member, int borrowDay){
        this.item = item;
        this.member = member;
        this.borrowDay = borrowDay;
        this.dueDay = borrowDay + member.getLoanPeriod();
    }

    public LibraryItem getItem(){
        return item;
    }

    public Member getMember(){
        return member;
    }

    public int getBorrowDay(){
        return borrowDay;
    }

    public int getDueDay(){
        return dueDay;
    }

    public int getDaysLate(int today){
        int late = today - dueDay;
        if (late < 0){
            return 0;
        }
        return late;
    }

    public boolean isOverdue(int today){
        return today > dueDay;
    }

    public int calculateFine(int today){
        Borrowable borrowableItem = (Borrowable) item;
        return getDaysLate(today) * borrowableItem.getFinePerDay();
    }
}
