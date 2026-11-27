import java.util.ArrayList;
import java.util.List;

public abstract class Member{
    private String id;
    private String name;
    private List<Loan> loans;

    public Member(String id, String name){
        this.id = id;
        this.name = name;
        this.loans = new ArrayList<>();
    }

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public abstract int getMaxItems();
    public abstract int getLoanPeriod();

    public boolean canBorrow(){
        return loans.size() < getMaxItems();
    }

    public void addLoan(Loan loan){
        loans.add(loan);
    }

    public void removeLoan(Loan loan){
        loans.remove(loan);
    }

    public List<Loan> getLoans(){
        return loans;
    }

    public boolean holds(String itemId){
        for (Loan loan : loans){
            if (loan.getItem().getId().equals(itemId)){
                return true;
            }
        }
        return false;
    }
}
