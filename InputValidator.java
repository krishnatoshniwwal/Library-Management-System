public class InputValidator{
    public static void checkFieldCount(String[] parts, int expected) throws InvalidInputException{
        if (parts.length != expected){
            throw new InvalidInputException(parts[0] + " needs " + (expected - 1) + " field(s)");
        }
    }

    public static String requireNonBlank(String value, String field) throws InvalidInputException{
        if (value.trim().isEmpty()){
            throw new InvalidInputException(field + " cannot be blank");
        }
        return value.trim();
    }

    public static int parsePositiveInt(String value) throws InvalidInputException{
        int number;
        try{
            number = Integer.parseInt(value.trim());
        } catch (NumberFormatException e){
            throw new InvalidInputException(value + " is not a valid number");
        }
        if (number <= 0){
            throw new InvalidInputException("number must be positive: " + value);
        }
        return number;
    }
}
