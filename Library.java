import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Library{
    private Map<String, LibraryItem> items;
    private Map<String, Member> members;

    public Library(){
        items = new TreeMap<>();
        members = new HashMap<>();
    }

    public void addItem(LibraryItem item){
        items.put(item.getId(), item);
    }

    public void addMember(Member member){
        members.put(member.getId(), member);
    }

    public List<LibraryItem> getInventory(){
        return new ArrayList<>(items.values());
    }

    public String getStatus(LibraryItem item){
        if (!item.isBorrowable()){
            return "NOT_BORROWABLE";
        }
        return "AVAILABLE";
    }
}
