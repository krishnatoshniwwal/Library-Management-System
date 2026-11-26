import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommandProcessor{
    private Library library;

    public CommandProcessor(){
        library = new Library();
    }

    public void run(Scanner in){
        while (in.hasNextLine()){
            String line = in.nextLine().trim();
            if (line.isEmpty()){
                continue;
            }
            try{
                System.out.println(process(line));
            } catch (LibraryException e){
                System.out.println("ERROR " + e.getMessage());
            }
        }
    }

    private String process(String line) throws LibraryException{
        String[] parts = line.split("\\|");
        String command = parts[0].trim();

        switch (command){
            case "ADD_BOOK":
                library.addItem(new Book(parts[1].trim(), parts[2].trim(), parts[3].trim()));
                return "OK";
            case "ADD_MAGAZINE":
                library.addItem(new Magazine(parts[1].trim(), parts[2].trim(), parts[3].trim()));
                return "OK";
            case "ADD_REFERENCE":
                library.addItem(new ReferenceBook(parts[1].trim(), parts[2].trim(), parts[3].trim()));
                return "OK";
            case "ADD_MEMBER": {
                Member member = createMember(parts[1].trim(), parts[2].trim(), parts[3].trim());
                if (member == null){
                    return "ERROR unknown member type " + parts[3].trim();
                }
                library.addMember(member);
                return "OK";
            }
            case "BORROW": {
                Loan loan = library.borrow(parts[1].trim(), parts[2].trim());
                return "OK due on day " + loan.getDueDay();
            }
            case "RETURN": {
                int fine = library.returnItem(parts[1].trim());
                return "OK fine " + fine;
            }
            case "ADVANCE": {
                int day = library.advance(Integer.parseInt(parts[1].trim()));
                return "OK today is day " + day;
            }
            case "SEARCH": {
                List<String> lines = new ArrayList<>();
                for (LibraryItem item : library.search(parts[1].trim())){
                    lines.add(item.getId() + "|" + item.getType() + "|" + item.getTitle());
                }
                return formatReport(lines);
            }
            case "LOANS": {
                List<String> lines = new ArrayList<>();
                for (Loan loan : library.getLoansOf(parts[1].trim())){
                    lines.add(loan.getItem().getId() + "|" + loan.getItem().getTitle() + "|" + loan.getDueDay());
                }
                return formatReport(lines);
            }
            case "OVERDUE": {
                int today = library.getToday();
                List<String> lines = new ArrayList<>();
                for (Loan loan : library.getOverdueLoans()){
                    lines.add(loan.getItem().getId() + "|" + loan.getMember().getId() + "|"
                            + loan.getDaysLate(today) + "|" + loan.calculateFine(today));
                }
                return formatReport(lines);
            }
            case "INVENTORY": {
                List<String> lines = new ArrayList<>();
                for (LibraryItem item : library.getInventory()){
                    lines.add(item.getId() + "|" + item.getType() + "|" + library.getStatus(item));
                }
                return formatReport(lines);
            }
            default:
                return "ERROR unknown command " + command;
        }
    }

    private Member createMember(String id, String name, String type){
        switch (type.toUpperCase()){
            case "STUDENT":
                return new Student(id, name);
            case "STAFF":
                return new Staff(id, name);
            case "FACULTY":
                return new Faculty(id, name);
            default:
                return null;
        }
    }

    private String formatReport(List<String> lines){
        if (lines.isEmpty()){
            return "NONE";
        }
        return String.join("\n", lines);
    }
}
