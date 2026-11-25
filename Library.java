import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Library{
    private Map<String, LibraryItem> items;
    private Map<String, Member> members;
    private Map<String, Loan> activeLoans;

    public Library(){
        items = new TreeMap<>();
        members = new HashMap<>();
        activeLoans = new HashMap<>();
    }

    public void addItem(LibraryItem item){
        items.put(item.getId(), item);
    }

    public void addMember(Member member){
        members.put(member.getId(), member);
    }

    public Loan borrow(String memberId, String itemId){
        Member member = members.get(memberId);
        if (member == null){
            System.out.println("ERROR member " + memberId + " not found");
            return null;
        }
        LibraryItem item = items.get(itemId);
        if (item == null){
            System.out.println("ERROR item " + itemId + " not found");
            return null;
        }
        if (!item.isBorrowable()){
            System.out.println("ERROR " + itemId + " cannot be borrowed");
            return null;
        }
        if (activeLoans.containsKey(itemId)){
            System.out.println("ERROR " + itemId + " is on loan");
            return null;
        }
        if (!member.canBorrow()){
            System.out.println("ERROR " + memberId + " has reached the limit of " + member.getMaxItems());
            return null;
        }

        Loan loan = new Loan(item, member, 0);
        activeLoans.put(itemId, loan);
        member.addLoan(loan);
        return loan;
    }

    public boolean returnItem(String itemId){
        if (!items.containsKey(itemId)){
            System.out.println("ERROR item " + itemId + " not found");
            return false;
        }
        Loan loan = activeLoans.get(itemId);
        if (loan == null){
            System.out.println("ERROR " + itemId + " is not on loan");
            return false;
        }
        activeLoans.remove(itemId);
        loan.getMember().removeLoan(loan);
        return true;
    }

    public List<LibraryItem> getInventory(){
        return new ArrayList<>(items.values());
    }

    public String getStatus(LibraryItem item){
        if (!item.isBorrowable()){
            return "NOT_BORROWABLE";
        }
        Loan loan = activeLoans.get(item.getId());
        if (loan == null){
            return "AVAILABLE";
        }
        return "ON_LOAN|" + loan.getMember().getId();
    }
}
