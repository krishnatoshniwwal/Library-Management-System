public class MemberNotFoundException extends LibraryException{
    public MemberNotFoundException(String memberId){
        super("member " + memberId + " not found");
    }
}
