// A Loan links ONE item to ONE member and remembers WHEN it was borrowed.
public class Loan{
    private LibraryItem item;
    private Member member;
    private int borrowDay;

    public Loan(LibraryItem item, Member member, int borrowDay){
        this.item = item;
        this.member = member;
        this.borrowDay = borrowDay;
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
}
