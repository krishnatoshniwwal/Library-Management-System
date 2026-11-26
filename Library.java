import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Library{
    private Map<String, LibraryItem> items;
    private Map<String, Member> members;
    private Map<String, Loan> activeLoans;
    private int today;

    private static final Comparator<LibraryItem> BY_TITLE = (a, b) -> {
        int result = a.getTitle().compareTo(b.getTitle());
        if (result != 0){
            return result;
        }
        return a.getId().compareTo(b.getId());
    };

    private static final Comparator<Loan> BY_DUE_DAY_THEN_ID = (a, b) -> {
        if (a.getDueDay() != b.getDueDay()){
            return Integer.compare(a.getDueDay(), b.getDueDay());
        }
        return a.getItem().getId().compareTo(b.getItem().getId());
    };

    private static final Comparator<Loan> BY_DAYS_LATE_THEN_ID = (a, b) -> {
        if (a.getDueDay() != b.getDueDay()){
            return Integer.compare(a.getDueDay(), b.getDueDay());
        }
        return a.getItem().getId().compareTo(b.getItem().getId());
    };

    public Library(){
        items = new TreeMap<>();
        members = new HashMap<>();
        activeLoans = new HashMap<>();
        today = 0;
    }

    public void addItem(LibraryItem item){
        items.put(item.getId(), item);
    }

    public void addMember(Member member){
        members.put(member.getId(), member);
    }

    public Loan borrow(String memberId, String itemId) throws LibraryException{
        Member member = findMember(memberId);
        LibraryItem item = findItem(itemId);

        if (!item.isBorrowable()){
            throw new ItemNotAvailableException(itemId + " cannot be borrowed");
        }
        if (activeLoans.containsKey(itemId)){
            throw new ItemNotAvailableException(itemId + " is on loan");
        }
        if (!member.canBorrow()){
            throw new BorrowLimitExceededException(memberId, member.getMaxItems());
        }

        Loan loan = new Loan(item, member, today);
        activeLoans.put(itemId, loan);
        member.addLoan(loan);
        return loan;
    }

    public int returnItem(String itemId) throws LibraryException{
        findItem(itemId);

        Loan loan = activeLoans.get(itemId);
        if (loan == null){
            throw new ItemNotOnLoanException(itemId);
        }

        int fine = loan.calculateFine(today);

        activeLoans.remove(itemId);
        loan.getMember().removeLoan(loan);
        return fine;
    }

    public int advance(int days){
        today = today + days;
        return today;
    }

    public List<LibraryItem> search(String text){
        String lowerText = text.toLowerCase();
        List<LibraryItem> result = new ArrayList<>();
        for (LibraryItem item : items.values()){
            if (item.getTitle().toLowerCase().contains(lowerText)){
                result.add(item);
            }
        }
        result.sort(BY_TITLE);
        return result;
    }

    public List<Loan> getLoansOf(String memberId) throws MemberNotFoundException{
        Member member = findMember(memberId);
        List<Loan> result = new ArrayList<>(member.getLoans());
        result.sort(BY_DUE_DAY_THEN_ID);
        return result;
    }

    public List<Loan> getOverdueLoans(){
        List<Loan> result = new ArrayList<>();
        for (Loan loan : activeLoans.values()){
            if (loan.isOverdue(today)){
                result.add(loan);
            }
        }
        result.sort(BY_DAYS_LATE_THEN_ID);
        return result;
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

    public int getToday(){
        return today;
    }

    private LibraryItem findItem(String id) throws ItemNotFoundException{
        LibraryItem item = items.get(id);
        if (item == null){
            throw new ItemNotFoundException(id);
        }
        return item;
    }

    private Member findMember(String id) throws MemberNotFoundException{
        Member member = members.get(id);
        if (member == null){
            throw new MemberNotFoundException(id);
        }
        return member;
    }
}
