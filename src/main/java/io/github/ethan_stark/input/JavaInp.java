/**
 * Contains a class to aid with getting inputs.
  */
package io.github.ethan_stark.input;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * <p>Methods to better get java input. </p>
 * <p>Automatically deals with possible type errors, </p>
 * <p>Program dictates how input gets transformed into desired type to avoid errors. </p>
 *  @author Ethan Stark-Hrynkiw
 *  @hidden 𝔈𝔱𝔥𝔞𝔫 𝔖𝔱𝔞𝔯𝔨-ℌ𝔯𝔶𝔫𝔨𝔦𝔴
 *  @version 0.1
  */
public class JavaInp implements AutoCloseable{

    /**
     * Dictates how to transform user input to desired type. 
     * @param <T> The resulting type of the conversion.
     * @author Ethan Stark-Hrynkiw
     * @version 0.1
      */
    public static interface ConvertToType<T> {
    
        /**
         * Converts the given string into an instace of the wanted type.
         * @param UserInp The users input
         * @return an instance of wanted type
         * @throws InvalidInputException If UserInp was not a valid input
          */
        public T Transform(String UserInp) throws InvalidInputException;

        /**
         * Invalidates a user input for use in {@link JavaInp#validateInput(String, String, ConvertToType, List, List , int , Object)}
         * <br>
         * Throws error that will be caught by validateInput method, don't use exept when creating {@code ConvertToType}.
         * 
         * @see JavaInp#invalidateInput(String)
         * @param input the invalid input.
         * @throws InvalidInputException Using provided input
         * 
         */
        default void invalidateInput(String input) throws InvalidInputException{
            throw new InvalidInputException(input);
        }

        /**
         * Invalidates a user input for use in {@link JavaInp#validateInput(String, String, ConvertToType, List, List, int , Object)}
         * <br>
         * Throws error that will be caught by validateInput method, don't use exept when creating {@code ConvertToType}.
         * @see JavaInp#invalidateInput(String, Throwable)
         * @param input the invalid input.
         * @param thrown the thrown error for error chaining.
         * @throws InvalidInputException Chains throwable an uses provided input
         */
        default void invalidateInput(String input, Throwable thrown) throws InvalidInputException{
            throw new InvalidInputException(input, thrown);
        }
    }

    /**
     * Thrown when an invalid input was given. 
     * Stores the orignial input at {@link InvalidInputException#erroredInput}.
     * @author Ethan Stark-Hrynkiw
     * @version 0.1
      */
    public static class InvalidInputException extends Exception{
       /** The error causing input.  */
        String erroredInput;
        /**
         * Chains Throwable with errored input.
         * @param input The errored input
         * @param thrown The throwable to chain
          */
        public InvalidInputException(String input, Throwable thrown){
            super("Invalid input\"" + input + "\" given.", thrown);
            erroredInput = input;
        }
        /**
         * Uses given input.
         * @param input The errored input.
          */
        public InvalidInputException(String input){
            super("Invalid input\"" + input + "\" given.");
            erroredInput = input;
        }
    }

    private Scanner inpScanner;
    private final String StandardErrorMsg = "Invalid response. Please try again.";
    
    /** Default way to convert doubles  */
    public final static ConvertToType<Double> DoubleConvert = new ConvertToType<Double>() {
        @Override
        public Double Transform(String UserInp) throws InvalidInputException {
            try {
                return Double.valueOf(UserInp);
            } catch (NumberFormatException e) {
                invalidateInput(UserInp, e);
                return 0.0;
            }
        }
    };

    /** Default way to convert ints  */
    public final static ConvertToType<Integer> IntegerConvert = new ConvertToType<Integer>() {

        @Override
        public Integer Transform(String UserInp) throws InvalidInputException {
            try {
                return Integer.valueOf(UserInp);
            } catch (NumberFormatException e) {
                invalidateInput(UserInp, e);
                return 0;
            }
        }
        
    };

    /** Default way to convert strings  */
    public final static ConvertToType<String> StringConvert = new ConvertToType<String>() {
        @Override
        public String Transform(String UserInp) {
            return UserInp;
        }
    };

    /** Default way to convert booleans  */
    public final static ConvertToType<Boolean> BooleanConvert = new ConvertToType<Boolean>() {
        @Override
        public Boolean Transform(String UserInp) throws InvalidInputException {
            Boolean IsBool = null;
            if (UserInp.equals("true") || UserInp.equals("True")) {
                IsBool = true;
            }
            else if (UserInp.equals("false") || UserInp.equals("False")) {
                IsBool = false;
            }
            else{
                // invalidate input
                invalidateInput(UserInp);
            }
            return IsBool;
        }
    };

    /**
     * Uses the given input stream for inputs.
     * @param stream The input stream to use.
      */
    public JavaInp(InputStream stream){
        inpScanner = new Scanner(stream);
    }
    /**
     * Uses {@code System.in} to get inputs.
      */
    public JavaInp(){
        inpScanner = new Scanner(System.in);
    }
    /**
     *  <p>Gets the scanners next line as string </p>
     * @return User inputed line
      */
    public String getNextLine(){
        String response = inpScanner.nextLine();
        return response;
    }

    @Override
    public void close(){
        inpScanner.close();
    }

    /**
     * <p>Prints the wanted message with new line, then gets the input </p>
     * @param Message for the user
     * @return User inputed line
      */
    public String inputln(String Message){
        System.out.println(Message);
        return getNextLine();
    }
    /**
     * <p>Gets the input, prints no message </p>
     * 
     * @return User inputed line
      */
    public String inputln(){
        return inputln("");
    }
    /**
     * <p>Prints the wanted message, then gets the input </p>
     * @param Message for the user
     * @return User inputed line
      */
    public String input(String Message){
        System.out.print(Message);
        return getNextLine();
    }
    /**
     * <p>Gets the input, prints no message </p>
     * 
     * @return User inputed line
      */
    public String input(){
        return input("");
    }

    /**
     * <p> Checks if {@code ob} is within the array list {@code Arr }.</p>
     * <p> assumes {@code defaultValue} if the array list is empty.</p>
     * @param Arr array list to check within
     * @param ob object to find
     * @param defaultValue boolean to assume if empty
     * @return whether or not it was {@code ob} was found
      */
    private boolean foundInList(List<?> Arr, Object ob, boolean defaultValue){
        boolean found = defaultValue;
        if(!Arr.isEmpty()) found = Arr.contains(ob);
        return found;
    }
    
    /**
     * Invalidates a user input for use in {@link JavaInp#validateInput(String, String, ConvertToType, List, List , int , T)}}
     * <br>
     * Throws error that will be caught by validateInput method, don't use exept when creating {@code ConvertToType}.
     * @param input The invalid input
     * @throws InvalidInputException The invalid input exception
      */
    public static void invalidateInput(String input) throws InvalidInputException{
        throw new InvalidInputException(input);
    }

     /**
     * Invalidates a user input for use in {@link JavaInp#validateInput(String)}
     * <br>
     * Throws error that will be caught by validateInput method, don't use exept when creating {@code ConvertToType}.
     * @param input The invalid input
     * @param thrown Throwable for chaining
     * @throws InvalidInputException The invalid input exception
      */
      public static void invalidateInput(String input, Throwable thrown) throws InvalidInputException{
        throw new InvalidInputException(input, thrown);
    }

    /**
     * Runs when {@link #validateInput(String, String, ConvertToType, List, List , int , T)}
     * catches an invalid input.
     * @param e the error thrown
      */
    protected void onInvalidInput(InvalidInputException e){}

    /**
     *  <p> Automatically validates users input</p>
     * @param <T> Type of input to return
     * @param Message message shown to the user before input
     * @param errorMessage message shown if invalid input
     * @param convert determins how to transform user input to type
     * @param validResponses array list of valid responses for the user
     * @param invalidResponses array list of invalid responses for the user
     * @param AskLimit number of times to ask for input,
     *  limits number of times to ask for imput when above 0
     * @param defaultInp This value will be used if the ask limit is surpased
     *  
     * @return the validated user input
      */
    public <T> T validateInput(String Message, String errorMessage, ConvertToType<T> convert, 
                                            List<String> validResponses, List<String> invalidResponses, int AskLimit, T defaultInp){
        
        // no null pointer exections in my house!
        Message = ( (Message == null )?   "" : Message);
        errorMessage = ( (errorMessage == null )?   "" : errorMessage);
        ArrayList<String> emptyList = new ArrayList<String>();
        validResponses = ( (validResponses == null )?   emptyList : validResponses);
        invalidResponses = ( (invalidResponses == null )?   emptyList : invalidResponses);

        T FinalAns = defaultInp;
       
        int runs = 0;
        boolean isValid = false;
        while ( (runs < AskLimit || AskLimit <= 0) && isValid == false) {
            String inp = input(Message);
            
            // assume it's fine unless proven false
            isValid = true;
            
            isValid = !foundInList(invalidResponses, inp, false); // check if in array, return false if empty or not contained
            isValid = foundInList(validResponses, inp, isValid); // check if in array, return true if empty or contained

            // try to convert
            try{
                
                FinalAns = convert.Transform(inp);
                
            }
            catch( InvalidInputException e){
                onInvalidInput(e);
                isValid = false;
            }

            if (!isValid) {
                System.out.println(errorMessage);
                FinalAns = defaultInp;
            }
            
            runs ++;
        }

        return FinalAns;
    }

    /**
     *  <p> Automatically validates users input</p>
     * @param <T> Type of input to return
     * @param Message message shown to the user before input
     * @param errorMessage message shown if invalid input
     * @param convert determins how to transform user input to type
     * @param validResponses array list of valid responses for the user
     * @param invalidResponses array list of invalid responses for the user
     * 
     * @return the validated user input
      */
    public <T> T validateInput(String Message, String errorMessage,  ConvertToType<T> convert, List<String> validResponses, List<String> invalidResponses){
        int AskLimit = 0;
         
        return validateInput(Message, errorMessage, convert, validResponses, invalidResponses, AskLimit, null);
    }

    /**
     *  <p> Automatically validates users input</p>
     * @param <T> Type of input to return
     * @param Message message shown to the user before input
     * @param errorMessage message shown if invalid input
     * @param convert determins how to transform user input to type
     * @param validResp array list of valid responses for the user
     * @param AskLimit number of times to ask for input
     *  limits number of times to ask for imput when above 0
     *  @param defaultInp This value will be used if the ask limit is surpased
     * 
     * @return the validated user input
      */
    public <T> T validateInput(String Message, String errorMessage, ConvertToType<T> convert, List<String> validResp, int AskLimit, T defaultInp){
        ArrayList<String> validInps = new ArrayList<String>();
        
        return validateInput(Message, errorMessage, convert, validResp, validInps, AskLimit, defaultInp);
    }

    /**
     *  <p> Automatically validates users input</p>
     * @param <T> Type of input to return
     * @param Message message shown to the user before input
     * @param errorMessage message shown if invalid input
     * @param convert determins how to transform user input to type
     * @param validResp array list of valid responses for the user
     * 
     * @return the validated user input
      */
    public <T> T validateInput(String Message, String errorMessage, ConvertToType<T> convert, List<String> validResp){
        ArrayList<String> validInps = new ArrayList<String>();
    
        
        return validateInput(Message, errorMessage, convert, validResp, validInps, 0, null);
    }
    /**
     *  <p> Automatically validates users input</p>
     * @param <T> Type of input to return
     * @param Message message shown to the user before input
     * @param errorMessage message shown if invalid input
     * @param convert determins how to transform user input to type
     * @param AskLimit number of times to ask for input
     *  limits number of times to ask for imput when above 0
     *  @param defaultInp This value will be used if the ask limit is surpased
     * 
     * @return the validated user input
      */
    public <T> T validateInput(String Message, String errorMessage, ConvertToType<T> convert, int AskLimit, T defaultInp){
        ArrayList<String> validInps = new ArrayList<String>();
        
        return validateInput(Message, errorMessage, convert, validInps, AskLimit, defaultInp);
    }
    
    /**
     *  <p> Automatically validates users input</p>
     * @param <T> Type of input to return
     * @param Message message shown to the user before input
     * @param errorMessage message shown if invalid input
     * @param convert determins how to transform user input to type
     *  
     * @return the validated user input
      */
    public <T> T validateInput(String Message, String errorMessage,  ConvertToType<T> convert){
        int AskLimit = 0;
         
        return validateInput(Message, errorMessage, convert, AskLimit, null);
    }

    /**
     *  <p> Automatically validates users input</p>
     * @param <T> Type of input to return
     * @param Message message shown to the user before input
     * @param convert determins how to transform user input to type
     *  
     * @return the validated user input
      */
    public <T> T validateInput(String Message, ConvertToType<T> convert){
        
        return validateInput(Message, StandardErrorMsg, convert);
    }

    /**
     *  <p> Automatically validates users input when that input is string</p>
     * @param Message message shown to the user before input
     * @param errorMessage message shown if invalid input
     * @param validResponses array list of valid responses for the user
     * @param invalidResponses array list of invalid responses for the user
     * @param AskLimit number of times to ask for input
     *  limits number of times to ask for imput when above 0
     *  @param defaultInp This value will be used if the ask limit is surpased
     * 
     * @return the validated user input
      */
    public String validateInput(String Message, String errorMessage, 
                                            List<String> validResponses, List<String> invalidResponses, int AskLimit, String defaultInp){

        String inp = validateInput(Message, errorMessage, StringConvert, validResponses, invalidResponses, AskLimit, defaultInp);

        return inp;
    }

    /**
     *  <p> Automatically validates users input when that input is string</p>
     * @param Message message shown to the user before input
     * @param errorMessage message shown if invalid input
     * @param validResp array list of valid responses for the user
     * @param invalidResp array list of invalid responses for the user
     * 
     * @return the validated user input
      */
    public String validateInput(String Message, String errorMessage, List<String> validResp, List<String> invalidResp){
        
        return validateInput(Message, errorMessage, validResp, invalidResp, 0, null);
    }

    /**
     *  <p> Automatically validates users input when that input is string</p>
     * @param Message message shown to the user before input
     * @param errorMessage message shown if invalid input
     * @param validResp array list of valid responses for the user
     * @param AskLimit number of times to ask for input
     *  limits number of times to ask for imput when above 0
     *  @param defaultInp This value will be used if the ask limit is surpased
     * 
     * @return the validated user input
      */
    public String validateInput(String Message, String errorMessage, List<String> validResp, int AskLimit, String defaultInp){
        ArrayList<String> validInps = new ArrayList<String>();
        
        return validateInput(Message, errorMessage, validResp, validInps, AskLimit, defaultInp);
    }

    /**
     *  <p> Automatically validates users input when that input is string</p>
     * @param Message message shown to the user before input
     * @param errorMessage message shown if invalid input
     * @param AskLimit number of times to ask for input
     *  limits number of times to ask for imput when above 0
     *  @param defaultInp This value will be used if the ask limit is surpased
     * 
     * @return the validated user input
      */
    public String validateInput(String Message, String errorMessage, int AskLimit, String defaultInp){
        ArrayList<String> validInps = new ArrayList<String>();
        
        return validateInput(Message, errorMessage, validInps, validInps, AskLimit, defaultInp);
    }

    /**
     *  <p> Automatically validates users input when that input is string</p>
     * @param Message message shown to the user before input
     * @param errorMessage message shown if invalid input
     *  
     * @return the validated user input
      */
    public String validateInput(String Message, String errorMessage){
        int AskLimit = 0;
         
        return validateInput(Message, errorMessage, AskLimit, null);
    }
    /**
     *  <p> Automatically validates users input when that input is string</p>
     * @param Message message shown to the user before input
     *  
     * @return the validated user input
      */
    public String validateInput(String Message){
         
        return validateInput(Message, StandardErrorMsg);
    }
}
