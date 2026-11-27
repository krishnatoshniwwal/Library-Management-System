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
        String[] parts = line.split("\\|", -1);
        String command = parts[0].trim();

        switch (command){
            case "ADD_BOOK": {
                InputValidator.checkFieldCount(parts, 4);
                String id = InputValidator.requireNonBlank(parts[1], "id");
                String title = InputValidator.requireNonBlank(parts[2], "title");
                String author = InputValidator.requireNonBlank(parts[3], "author");
                library.addItem(new Book(id, title, author));
                return "OK";
            }
            case "ADD_MAGAZINE": {
                InputValidator.checkFieldCount(parts, 4);
                String id = InputValidator.requireNonBlank(parts[1], "id");
                String title = InputValidator.requireNonBlank(parts[2], "title");
                String issue = InputValidator.requireNonBlank(parts[3], "issue");
                library.addItem(new Magazine(id, title, issue));
                return "OK";
            }
            case "ADD_REFERENCE": {
                InputValidator.checkFieldCount(parts, 4);
                String id = InputValidator.requireNonBlank(parts[1], "id");
                String title = InputValidator.requireNonBlank(parts[2], "title");
                String author = InputValidator.requireNonBlank(parts[3], "author");
                library.addItem(new ReferenceBook(id, title, author));
                return "OK";
            }
            case "ADD_MEMBER": {
                InputValidator.checkFieldCount(parts, 4);
                String id = InputValidator.requireNonBlank(parts[1], "id");
                String name = InputValidator.requireNonBlank(parts[2], "name");
                String type = InputValidator.requireNonBlank(parts[3], "type");
                library.addMember(createMember(id, name, type));
                return "OK";
            }
            case "BORROW": {
                InputValidator.checkFieldCount(parts, 3);
                String memberId = InputValidator.requireNonBlank(parts[1], "memberId");
                String itemId = InputValidator.requireNonBlank(parts[2], "itemId");
                Loan loan = library.borrow(memberId, itemId);
                return "OK due on day " + loan.getDueDay();
            }
            case "RETURN": {
                InputValidator.checkFieldCount(parts, 2);
                String itemId = InputValidator.requireNonBlank(parts[1], "itemId");
                int fine = library.returnItem(itemId);
                Loan next = library.getActiveLoan(itemId);
                if (next != null){
                    return "OK fine " + fine + ", issued to " + next.getMember().getId()
                            + " due on day " + next.getDueDay();
                }
                return "OK fine " + fine;
            }
            case "ADVANCE": {
                InputValidator.checkFieldCount(parts, 2);
                int days = InputValidator.parsePositiveInt(parts[1]);
                int day = library.advance(days);
                return "OK today is day " + day;
            }
            case "SEARCH": {
                InputValidator.checkFieldCount(parts, 2);
                String text = InputValidator.requireNonBlank(parts[1], "search text");
                List<String> lines = new ArrayList<>();
                for (LibraryItem item : library.search(text)){
                    lines.add(item.getId() + "|" + item.getType() + "|" + item.getTitle());
                }
                return formatReport(lines);
            }
            case "LOANS": {
                InputValidator.checkFieldCount(parts, 2);
                String memberId = InputValidator.requireNonBlank(parts[1], "memberId");
                List<String> lines = new ArrayList<>();
                for (Loan loan : library.getLoansOf(memberId)){
                    lines.add(loan.getItem().getId() + "|" + loan.getItem().getTitle() + "|" + loan.getDueDay());
                }
                return formatReport(lines);
            }
            case "OVERDUE": {
                InputValidator.checkFieldCount(parts, 1);
                int today = library.getToday();
                List<String> lines = new ArrayList<>();
                for (Loan loan : library.getOverdueLoans()){
                    lines.add(loan.getItem().getId() + "|" + loan.getMember().getId() + "|"
                            + loan.getDaysLate(today) + "|" + loan.calculateFine(today));
                }
                return formatReport(lines);
            }
            case "INVENTORY": {
                InputValidator.checkFieldCount(parts, 1);
                List<String> lines = new ArrayList<>();
                for (LibraryItem item : library.getInventory()){
                    lines.add(item.getId() + "|" + item.getType() + "|" + library.getStatus(item));
                }
                return formatReport(lines);
            }
            case "RESERVE": {
                InputValidator.checkFieldCount(parts, 3);
                String memberId = InputValidator.requireNonBlank(parts[1], "memberId");
                String itemId = InputValidator.requireNonBlank(parts[2], "itemId");
                library.reserve(memberId, itemId);
                return "OK reserved";
            }
            default:
                throw new InvalidInputException("unknown command " + command);
        }
    }

    private Member createMember(String id, String name, String type) throws InvalidInputException{
        switch (type.toUpperCase()){
            case "STUDENT":
                return new Student(id, name);
            case "STAFF":
                return new Staff(id, name);
            case "FACULTY":
                return new Faculty(id, name);
            default:
                throw new InvalidInputException("unknown member type " + type);
        }
    }

    private String formatReport(List<String> lines){
        if (lines.isEmpty()){
            return "NONE";
        }
        return String.join("\n", lines);
    }
}
