import java.util.*;
import java.io.*;
public class Functions {
    private String name;
    //private String dateOfBirth;
    private boolean newPatient;
    private String patientFileName;
    public Functions(String name, String dateOfBirth){
        //TO-DO: update to throw illegal argument if name = null or DOB is not in correct format : no longer need to address as it is handled in main
        this.name = name;
        // update dateOfBirth to no longer contain "/"
        dateOfBirth = dateOfBirth.substring(0,2) + dateOfBirth.substring(3,5) + dateOfBirth.substring(6);//mmddyyyy

        this.patientFileName = name + "_" + dateOfBirth + ".txt";
        //System.out.println(patientFileName);
        //check if there is an existing file for the patient
        //if not then newPatient = true
    }

    //in this method used file reading and writing techniques from UW CSE 12x series
    //7-10-23: works almost as expected, some issues if developer is on the file where things get added to the same line instead of next line
    public void prescribe(String newMedication) throws IOException {
        File file = new File(patientFileName);
        //7-8-2023: new patient branch works as expected/anticipated
        if (file.createNewFile()) { //if the patient did not already have a file, creates one
            System.out.println("new file created"); //del
            PrintStream newPatientFile = new PrintStream(file);
            //add the medication to the patients file
            newPatientFile.println(newMedication);
        } else { //file already exists for the patient
            Scanner patientFile = new Scanner(file);
            Scanner database = new Scanner(new File ("Medication Database.txt"));
            //search through database for medication
            String databaseMed;
            String interactions = "";
            //todo: update to consider case
            while (database.hasNextLine()){
                String line = database.nextLine();
                int breakpt = line.indexOf(":");
                databaseMed = line.substring(0,breakpt);
                if (databaseMed.contains(newMedication)) {
                    interactions = line.substring(breakpt);
                    break;
                }
            }
            //maybe add an if for if there are interactions to crosscheck against?
            //make a list of all the medications contained in interactions
            List<String> warning = new ArrayList<>();
            while (patientFile.hasNextLine()){
                String medication = patientFile.nextLine();
                if (interactions.contains(medication)){
                    warning.add(medication);
                }
            }
            if (warning.size() == 0){
                //add the medication to the end of the patients file
                addMedication(newMedication, file);
            } else{
                System.out.println("It appears that " + newMedication + " is not recommended to be taken with: ");
                for (String medication : warning){
                    System.out.println(medication);
                }
                System.out.println("Knowing that there is a risk involved with using " + newMedication +
                        " with current medications would you still like to prescribe it?");
                System.out.println("(Y)es or (N)o");
                Scanner console = new Scanner(System.in);
                String choice = console.next();
                if (choice.equalsIgnoreCase("y")){
                    addMedication(newMedication, file);
                }
                //bad interactions warning with a list of each bad/faulty medication
                //check if they would like to add medication anyway
            }

            //if the list has a size of 0, add the medication to the end of the file
                //loop until !hasnextline then printstream out the new medication? (make this a private method?)
            //if list has a size > 0 print a message warning which existing medications have a bad interaction
            //then check if they still want to perscribe it
                //if yes same loop as when size is 0
                //if no do nothing and allow program to run


            //cross-check medication with existing
            //if it does not conflict then add it to the end of the file
        }

    }

    //add the medication to the end of the patients file
    //append to a file on 7-9-2023
    //TODO: consider using a try and catch
    //TODO: learn the benifits of a try and catch
    //I dont think a try catch would be necessary here as the method this is used in
    //creates a file if one does not yet exist so file should always be an existing file
    //when this method is called
    private void addMedication(String newMedication, File file) throws IOException {
        System.out.println("Adding " + newMedication + " to the patients file.");
        FileWriter fr = new FileWriter(file, true);
        BufferedWriter output = new BufferedWriter(fr);
        output.write(newMedication + "\n");
        output.close();
        fr.close();
        //https://beginnersbook.com/2014/01/how-to-append-to-a-file-in-java/
        //https://www.digitalocean.com/community/tutorials/java-append-to-file
    }

    //7/10/23: Preforms as expected for removing a medication from the patients file
    public void remove(String medication) throws IOException {
        if (newPatient == false){
            List<String> medications = new ArrayList<>();
            File file = new File(patientFileName);
            Scanner patientFile = new Scanner(file);
            //copy all of the medications other than the one to be removed
            while (patientFile.hasNextLine()){
                String currMed = patientFile.nextLine();
                if (!currMed.equalsIgnoreCase(medication)){
                    medications.add(currMed);
                }
            }
            //clear the existing file
            FileWriter fw = new FileWriter(patientFileName, false);
            PrintWriter pw = new PrintWriter(fw, false);
            pw.flush();
            pw.close();
            fw.close();
            //re-write the medications to the file
            PrintStream fileOutput = new PrintStream(file);
            for (String currMed : medications){
                fileOutput.println(currMed);
            }
            System.out.println(medication + " has been removed for the patients prescriptions. ");
            //remove the medication from the patients file
        } //else do nothing
    }

    public void review(){
        review(patientFileName);
    }
    private void review(String patientFile){
        //open the file or display contnets of the file
    }
}
