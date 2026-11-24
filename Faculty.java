public class Faculty extends Member{
    private static final int MAX_ITEMS = 5;
    private static final int LOAN_PERIOD = 30;

    public Faculty(String id, String name){
        super(id, name);
    }

    @Override
    public int getMaxItems(){
        return MAX_ITEMS;
    }

    @Override
    public int getLoanPeriod(){
        return LOAN_PERIOD;
    }
}
