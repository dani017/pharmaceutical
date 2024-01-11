import java.io.*;
import java.util.*;
import java.time.*;
//client class
//prompt and manage user input
public class Main {
    public static void main(String[] args) throws IOException {


        Scanner console = new Scanner(System.in);
        System.out.println("Welcome to MediCheck!");
        String name = "" ;
        while (!valid(name)){
            System.out.println("Please list the patients first and last name:");
            //handle input
            name = console.nextLine(); //store patients first and last name
        }

        //consider form and captialization
        //TODO: temp fix, consider if a do while works better
        System.out.println("What is the patients DOB in the form mm/dd/yyyy:");//eventually make it so "/" are automatic
        String dateOfBirth = console.nextLine();
        //boolean format = dobFormat(dateOfBirth);
        while (!dobFormat(dateOfBirth)){
            System.out.println("What is the patients DOB in the form mm/dd/yyyy:");//eventually make it so "/" are automatic
            //finished TO-DO: figure out how to use name and DOB to access specific patient information: handled this in function by making name and dob part of the file name
            //potentially a file containing patients txt file where file name is patients first and last name followed by DOBz/
            //finished TO-DO: do not allow program to continue unless DOB is in th correct format: handled by adding a while with a dummy dob
            dateOfBirth = console.nextLine();
        }


        //if input is not in the expected form note that it is incorrect and reprompt
        //eventually update so on gui if form does match then it will do the "invalid form" thing and not allow user to move to next step

        //select a function
        //7/13/2003: added a loop around function selection
        //why? so that if they decide against a medication they can test another one instead
        System.out.println("Would you like to prescribe a new medication (1), review the patients existing medication(2), remove a medication(3), or exit the program (4)?");
        //eventually update to be button options on gui?
        int choice = console.nextInt();
        String medication;
        Functions patient = new Functions(name, dateOfBirth);
        while (choice != 4) {
            switch (choice) {
                //Case 1
                case 1:
                    System.out.println("What medication would you like to perscribe:");
                    //handle input
                    //TODO: bug on the medication input, does not allow for more than a word of input
                    medication = console.next(); //.nextLine() for some reason would not accept input (help debug)
                    System.out.println("Thank you!");//+"I will now check this medication for any " +
                    //"potential conflicting interactions with current medications");
                    //prescribe method from the functions class
                    patient.prescribe(medication);
                    break;
                //Case 2
                case 2:
                    //review method from the functions class
                    break;
                case 3://TODO: this has the same nextLine issue
                    System.out.println("What medication would you like to remove:");
                    //handle input
                    medication = console.next();
                    patient.remove(medication);
                    //remove method from the functions class
                    break;
            }
            System.out.println("Would you like to prescribe a new medication (1), review the patients existing medication(2), remove a medication(3), or exit the program (4)?");
            choice = console.nextInt();
        }
    }

        //old bugs (since fixed): currently allows years too far back or that have not yet happened, generally allows for dates that do not exist -- fixed
        //7-13-23 checks to ensure that dob is entered in the correct form and is a valid date.
        private static boolean dobFormat(String dob) {
            if (dob.length() < 10 ){
                return false;
            }
            //desired format: mm/dd/yyyy
            //general formatting check
            if (dob.charAt(2) != '/' || dob.charAt(5) != '/' || dob.substring(6).length() != 4) {
                System.out.println("That is not the correct format.");
                return false;
            }
            boolean correct = validDate(dob);
            if (!correct){
                System.out.println("That is not a valid date.");
            }

           return correct;
        }

        //7-13-2023 checks that date is valid
        private static boolean validDate(String dob){
            int month = Integer.parseInt(dob.substring(0, 2));
            int day = Integer.parseInt(dob.substring(3, 5));
            int year = Integer.parseInt(dob.substring(6));
            int currYear = Calendar.getInstance().get(Calendar.YEAR);
            //valid month and year check, general date check
            if (month > 12 || month < 1 || year > currYear || year < 1900 || day < 1 || day > 31) {
                return false;
            }
            //valid day of month check (day does not exceed num days for the month)
            //TODO: would it be better to do if else or just cases? I am curently liking the simplicity of if else

            //leap year check for feb
            if (month == 2 && (((year % 4 == 0) &&
                    (year % 100 != 0)) ||
                    (year % 400 == 0)) && day > 29) {
                return false;
                //feb leap year
            } else if (month == 2 && day > 28) { //feb not leap year check
                return false;
            } else if ("04 06 09 11".contains(dob.substring(0,2)) && day > 30){//months w/ 30 days: 4, 6, 9, 11 check
                return false;
            }//months w/ 31 days: 1, 3, 5, 7, 8, 10, 12 :this is handled by the general check
            return true;
        }
        private static boolean valid(String name){
            if (name.length() < 2){
                return false;
            }
            if (name.contains("!") || name.contains("@") || name.contains("#") || name.contains("$") || name.contains("%") ||
                name.contains("^") || name.contains("&") || name.contains("*") || name.contains("+") || name.contains("=") ||
                name.contains("?") || name.contains("<") || name.contains(">") || name.contains("{") || name.contains("}") ||
                name.contains("|") || name.contains(";") || name.contains(":") || name.contains("[") || name.contains("]") ||
                name.contains("1") || name.contains("2") || name.contains("3") || name.contains("4") || name.contains("5") ||
                name.contains("6") || name.contains("7") || name.contains("8") || name.contains("9")){
                System.out.println("Valid names cannot contain numbers or characters.");
                return false;

            }
            return true;
        }



/*
        /System.out.println("What medication would you like to perscribe:");
        //handle input
        String medication = console.nextLine();
        System.out.println("Thank you!\n"+"I will now check this medication for any " +
                           "potential conflicting interactions with current medications");
    }*/
}