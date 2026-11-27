public class DuplicateIdException extends LibraryException{
    public DuplicateIdException(String id){
        super("id " + id + " already exists");
    }
}
