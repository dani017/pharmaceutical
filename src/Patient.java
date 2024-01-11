import java.io.*;
import java.util.*;

public class Patient { //information populated from patients file, used in functions
    private String patientFileName;
    private String name;
    public static Set<String> medications;
    private static String dateOfBirth;
    public Patient(String name, String dob) throws IOException {
        this.name = name;
        dateOfBirth = dob.substring(0,2) + dob.substring(3,5) + dob.substring(6);//mmddyyyy
        this.patientFileName = name + "_" + dateOfBirth + ".txt";
        medications = new HashSet<>();
        generateMedications();
    }
    public String getDOB(){
        return dateOfBirth;
    }
    public String getPatientFileName(){
        return patientFileName;
    }
    public File getPatientFile() throws IOException {
        File file = new File(patientFileName);
        if (file.createNewFile()) { //if the patient did not already have a file, creates one
            return file;
        }
        return file;
    }
    private void generateMedications() throws IOException {
        Scanner patientFile = new Scanner(this.getPatientFile());
        while (patientFile.hasNextLine()){
            String line = patientFile.nextLine();
            medications.add(line);
            System.out.println("In patient medication added: " + line);
        }
    }
    public Set<String> getMedications(){
        return medications;
    }

}
