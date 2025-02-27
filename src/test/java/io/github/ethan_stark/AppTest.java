package io.github.ethan_stark;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import io.github.ethan_stark.input.JavaInp;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    
    public static void main(String[] args){
        // examples of how to use
        JavaInp e = new JavaInp();
        String Name = e.inputln("Hello HUMAN, what's your name?");
        System.out.println("Welcome " + Name);

        
        ArrayList<String> validResps = new ArrayList<String>();
        validResps.add("2.718281828");
        validResps.add("2.718281828459045");
        validResps.add("5");

        ArrayList<String> invalidResps = new ArrayList<String>();
        invalidResps.add("6");
        invalidResps.add("yes");
        invalidResps.add("no");
        invalidResps.add("No");
        invalidResps.add("5"); // this is fine, valid responses are prioritized

        double UserDouble = e.validateInput("Hello, enter a number\n>", "There has been an error", JavaInp.DoubleConvert, validResps, invalidResps);
        System.out.println("You chose " + UserDouble);

        int UserInt = e.validateInput("What's your favorite Int?\n", "That's not an Int", JavaInp.IntegerConvert, 3, 5);
        System.out.println("Your int is " + UserInt);

        String FavNum = e.validateInput("Enter your favorite number\n", null, validResps, null);
        System.out.println("That's right! It's " + FavNum);

        String THEANSWER = e.validateInput("What is the answer to everything?\n", "You can't say that :/", new ArrayList<>(), invalidResps, 5, "42");
        System.out.println("I guess '" + THEANSWER + "' is right. I don't acutally know.");

        boolean THEBEST = e.validateInput("AM I THE BEST?\n'true' or 'false'\nyou have 5 attempts\n> ", null, JavaInp.BooleanConvert, 5, true);
        System.out.println("YOU THINK IT'S " + THEBEST + "!\n I don't know what to think.");
        e.close();
    }
}

