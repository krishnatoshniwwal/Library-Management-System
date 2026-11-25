public class ItemNotOnLoanException extends LibraryException{
    public ItemNotOnLoanException(String itemId){
        super(itemId + " is not on loan");
    }
}
