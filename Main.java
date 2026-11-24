public class Main{
    public static void main(String[] args){
        Member student = new Student("S1", "Asha");
        Member staff = new Staff("T1", "Ravi");
        Member faculty = new Faculty("F1", "Dr Mehta");
        System.out.println("Student " + student.getMaxItems() + "/" + student.getLoanPeriod());
        System.out.println("Staff " + staff.getMaxItems() + "/" + staff.getLoanPeriod());
        System.out.println("Faculty " + faculty.getMaxItems() + "/" + faculty.getLoanPeriod());

        Book book = new Book("B1", "Clean Code", "Robert Martin");
        Magazine magazine = new Magazine("M1", "Time", "Jan 2025");
        ReferenceBook reference = new ReferenceBook("R1", "Oxford Dictionary", "Oxford");
        System.out.println("Book fine " + book.getFinePerDay() + ", borrowable " + book.isBorrowable());
        System.out.println("Magazine fine " + magazine.getFinePerDay() + ", borrowable " + magazine.isBorrowable());
        System.out.println("Reference borrowable " + reference.isBorrowable());
    }
}
