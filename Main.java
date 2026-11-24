import java.util.Scanner;

public class Main{
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        CommandProcessor processor = new CommandProcessor();
        processor.run(in);
    }
}
