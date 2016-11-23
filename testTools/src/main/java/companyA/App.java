package companyA;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {

    static Scanner input = new Scanner(System.in);

    public static void main( String[] args ) {



        mainOption();

        //createEmployeeData("src\\main\\resources\\testEmpFile", 100);
        //createUserData("src\\main\\resources\\testUserFile", 100);
        //createInventoryData("src\\main\\resources\\testInvFile", 100);

    }


    // Interactive test data file generation.
    public static void mainOption() {
        while(true) {

            // try-catch to handle input exceptions
            try {
                System.out.println("Please select an option: ");
                System.out.println("1: Create a templated data file");
                //System.out.println("2: Create a custom data file");
                System.out.println("0: Exit");
                String in = input.next();

                int choice = Integer.parseInt(in);


                switch(choice) {
                    case 1: {
                        System.out.println("Create a templated data file...\n");
                        templateOption();
                        break;
                    }
                    /*case 2: {
                        System.out.println("Create a custom data file...\n");

                        break;
                    }
                    */
                    case 0: {
                        System.out.println("Thanks for using Primrose software, goodbye!");
                        System.exit(0);
                    }
                    default: {
                        System.out.println("Invalid input");
                        break;
                    }
                }
            } catch (Exception ex) {
                System.out.println("Invalid input. Please input a number.\n");


            }




        }
    }

    public static void templateOption() {
        boolean flag = true;
        while(flag) {

            // try-catch to handle input exceptions
            try {
                System.out.println("Please select a data template: ");
                System.out.println("1: Create an Employee data file");
                System.out.println("2: Create an Inventory data file");
                System.out.println("3: Create a User data file");
                System.out.println("0: Back");
                String in = input.next();

                int choice = Integer.parseInt(in);


                switch(choice) {
                    case 1: {
                        System.out.println("Create an Employee data file...\n");

                        System.out.println("Input the number of entries for test file: ");

                        String countStr = input.next();

                        int countChoice = Integer.parseInt(countStr);

                        // files are output to directory with pom.xml file.
                        createEmployeeData("testEmpFile", countChoice);
                        System.out.println("Employee file created.");
                        System.out.println();

                        break;
                    }
                    case 2: {
                        System.out.println("Create an Inventory data file...\n");

                        System.out.println("Input the number of entries for test file: ");

                        String countStr = input.next();

                        int countChoice = Integer.parseInt(countStr);

                        // files are output to directory with pom.xml file.
                        createInventoryData("testInvFile", countChoice);
                        System.out.println("Inventory file created.");
                        System.out.println();

                        break;
                    }
                    case 3: {
                        System.out.println("Create a User data file...\n");

                        System.out.println("Input the number of entries for test file: ");

                        String countStr = input.next();

                        int countChoice = Integer.parseInt(countStr);

                        // files are output to directory with pom.xml file.
                        createUserData("testUserFile", countChoice);
                        System.out.println("User file created.");
                        System.out.println();

                        break;
                    }

                    case 0: {
                        System.out.println();
                        flag = false;
                        break;
                    }
                    default: {
                        System.out.println("Invalid input");
                        break;
                    }
                }
            } catch (Exception ex) {
                System.out.println("Invalid input. Please input a number.\n");


            }




        }
    }

    public static void customOption() {
        boolean flag = true;
        while(flag) {

            // try-catch to handle input exceptions
            try {
                System.out.println("Please select the type of file to create: ");
                System.out.println("1: .csv [Comma-separated] or other column delimited file");
                System.out.println("2: .txt [Text file] one entry per row");
                System.out.println("0: Back");
                String in = input.next();

                int choice = Integer.parseInt(in);


                switch(choice) {
                    case 1: {
                        System.out.println("Delimited file...\n");
                        boolean option = true;
                        while (option) {

                            System.out.println("Number of rows: ");
                            String rowIn = input.next();

                            System.out.println("Number of columns: ");
                            String colIn = input.next();

                            System.out.println("Include a header row? 1 for Yes. 2 for No.");
                            String headerIn = input.next();


                            int rowCount = Integer.parseInt(rowIn);
                            int colCount = Integer.parseInt(colIn);
                            int headerCount = Integer.parseInt(headerIn);


                            String[] colNameArray = new String[colCount];

                            for (int i = 0; i < colNameArray.length; i++) {
                                System.out.println(String.format("Column %s name: ", i));
                                colNameArray[i] = input.next();
                            }

                            System.out.println("Select delimiter: ");
                            System.out.println("1: ','  Comma delimited");
                            System.out.println("2: ',' Comma separated with double quote surrounded fields (ex. \"This, field\")");
                            System.out.println("3: ';' Semi-colon delimited");
                            System.out.println("4: ' ' Space delimited");
                            System.out.println("5: Tab delimited");
                            System.out.println("6: Input other single character as delimiter");
                            String delimiterIn = input.next();  // stopped here,


                        }

                        break;
                    }
                    case 2: {
                        System.out.println("Text file...\n");

                        break;
                    }
                    case 0: {
                        System.out.println();
                        flag = false;
                        break;
                    }
                    default: {
                        System.out.println("Invalid input");
                        break;
                    }
                }
            } catch (Exception ex) {
                System.out.println("Invalid input. Please input a number.\n");


            }




        }
    }




    /*  Generate a list from an input file of one column.  */
    public static ArrayList<String> createList(String fileLocation, String delimiter) {

        ArrayList<String> arrList = new ArrayList<>();

        try {


            BufferedReader reader = new BufferedReader(new FileReader(fileLocation));
            String record = null;


            while ((record = reader.readLine()) != null) {
                String[] parts = record.split(delimiter);

                for (int i = 0; i < parts.length; i++) {
                    arrList.add(parts[i]);
                }

            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return arrList;
    }


    public static void outputCSV(ArrayList<String> arrList, String fileLocation) {

        try {

            FileWriter writer = new FileWriter(fileLocation + ".csv");

            for (String list : arrList) {
                String line = list;

                writer.append(line);

            }


            writer.close();
        }
        catch (Exception ex) {
            throw new RuntimeException("I/O failed!");
        }


    }



    /*  Data Fields needed:
        firstName: a string as a name from firstnames.csv
        middleName: a string as a name from firstnames.csv
        lastName: a string as a name from lastnames.csv
        socialSecurityNumber: an integer 9 digits long between 100000000-999999999
        dob: a date between the reasonable working ages 18-67
        street: from NUMBER + " " + STREET of austin.csv
        city: from City of free-zipcode-database-edited.csv
        state: from State of free-zipcode-database-edited.csv
        zip: from Zipcode of free-zipcode-database-edited.csv
        hireDate: a date randomly assigned 18-49 years after the dob
        phoneNumber: an integer 9 digits long between 100000000-999999999
        contactName: a given name in three parts first, middle, and last
        relation: one of the following: spouse, friend, parent, child, extended family, friend, or other
        emergencyPhoneNumber: an integer 9 digits long between 100000000-999999999
    */
    public static void createEmployeeData(String outputFileName, int numberOfRows) {

        String headerString = "firstName,middleName,lastName,socialSecurityNumber,dob,street,city,state,zip,hireDate,phoneNumber,contactName,relation,emergencyPhoneNumber\n";

        ArrayList<String> firstNamesList = createList("src\\main\\resources\\firstnames.csv", ",");
        ArrayList<String> lastNamesList = createList("src\\main\\resources\\lastnames.csv", ",");

        Data addressData = populateData("src\\main\\resources\\austin.csv", ",");

        ArrayList<String> streetAddressNumberList = addressData.getData().get("NUMBER");
        ArrayList<String> streetAddressList = addressData.getData().get("STREET");

        Data zipData = populateData("src\\main\\resources\\free-zipcode-database-edited.csv", ",");
        ArrayList<String> zipcodeList = zipData.getData().get("Zipcode");
        ArrayList<String> cityZipList = zipData.getData().get("City");
        ArrayList<String> stateZipList = zipData.getData().get("State");

        String[] relationArr = {"spouse", "friend", "parent", "sibling", "child", "extended family", "friend", "other"};

        ArrayList<String> outArr = new ArrayList<String>();

        outArr.add(headerString);  // add headerString to the array

        for (int i = 0; i < numberOfRows; i++) {

            int addNum = intInRange(0, streetAddressList.size()-1);
            int zipNum = intInRange(0, zipcodeList.size()-1);

            String lineString = firstNamesList.get(intInRange(0, firstNamesList.size()-1)).toString() + "," +
                    firstNamesList.get(intInRange(0, firstNamesList.size()-1)).toString() + "," +
                    lastNamesList.get(intInRange(0, lastNamesList.size()-1)).toString() + "," +
                    Integer.toString(ssnGenerator()) + "," + dobGenerator() + "," +
                    streetAddressNumberList.get(addNum) + " " + streetAddressList.get(addNum) + "," +
                    cityZipList.get(zipNum) + "," + stateZipList.get(zipNum) + "," + zipcodeList.get(zipNum) + "," +
                    hiredGenerator(1985, 2016) + "," + Integer.toString(ssnGenerator()) + "," +
                    firstNamesList.get(intInRange(0, firstNamesList.size()-1)).toString() + " " +
                    lastNamesList.get(intInRange(0, lastNamesList.size()-1)).toString() + "," +
                    relationArr[intInRange(0, relationArr.length-1)] + "," +
                    Integer.toString(ssnGenerator()) + "\n"
                    ;
            outArr.add(lineString);
        }

        outputCSV(outArr, outputFileName);

    }



    /*  Data Fields needed:
        givenName: a string composed of a name from firstnames.csv + " " + name from firstnames.csv + " " + name from lastnames.csv
        temporaryPassword: TeamPrimrose!1
    */
    public static void createUserData(String outputFileName, int numberOfRows) {

        String headerString = "givenName,temporaryPassword\n";
        String tempPassword = "TeamPrimrose!1";

        ArrayList<String> firstNamesList = createList("src\\main\\resources\\firstnames.csv", ",");
        ArrayList<String> lastNamesList = createList("src\\main\\resources\\lastnames.csv", ",");


        ArrayList<String> outArr = new ArrayList<String>();

        outArr.add(headerString);  // add headerString to the array

        for (int i = 0; i < numberOfRows; i++) {

            String lineString = firstNamesList.get(intInRange(0, firstNamesList.size()-1)).toString() + " " +
                    firstNamesList.get(intInRange(0, firstNamesList.size()-1)).toString() + " " +
                    lastNamesList.get(intInRange(0, lastNamesList.size()-1)).toString() + "," +
                    tempPassword + "\n";

            outArr.add(lineString);
        }

        outputCSV(outArr, outputFileName);

    }


    /* Data Fields needed:
        id: an 8 digit number between 10000000-99999999
        productNumber: a 10 character combination of capital letters [A-Z] and numbers [0-9], alphaNumericStringGenerator(10)
        itemDescription: string containing 20 to 100 random words from 10kwords.txt
        itemCost: a double greater than 0 with two decimals
        itemPrice: a double that is itemCost times random from 1.15 to 5.00 with two decimals
        itemCount: a number greater than zero
     */
    public static void createInventoryData(String outputFileName, int numberOfRows) {

        String headerString = "id,productNumber,itemDescription,itemCost,itemPrice,itemCount\n";

        ArrayList<String> wordList = createList("src\\main\\resources\\10kwords.txt", ",");


        ArrayList<String> outArr = new ArrayList<String>();

        outArr.add(headerString);  // add headerString to the array

        for (int i = 0; i < numberOfRows; i++) {

            String descString = "";
            int randWordCount = intInRange(20,100);
            for (int j = 0; j < randWordCount; j++) {

                descString += wordList.get(intInRange(0, wordList.size()-1)) + " ";
            }

            descString = descString.trim();

            double itemCost = doubleInRange(0,1000);
            double itemFact = itemCost * (intInRange(115,500) / 100);

            String lineString = Integer.toString(intInRange(10000000,99999999)) + "," +
                    alphaNumericStringGenerator(10) + "," +
                    descString + "," +
                    String.format("%.2f", itemCost) + "," +
                    String.format("%.2f", itemFact) + "," +
                    Integer.toString(intInRange(1,99999999)) + "\n";

            outArr.add(lineString);
        }

        outputCSV(outArr, outputFileName);

    }



    /*  Create a data object from a csv file. maintain header, allows dictionary access of columns.  */
    public static Data populateData(String fileName, String delimiter) {
        Data d = new Data();
        HashMap<String, ArrayList<String>> hm = new HashMap<>();
        ArrayList<String> arr = d.generateHeader(fileName, delimiter);

        ArrayList<ArrayList<String>> data = d.generateData(arr, fileName, delimiter);


        // populate the data HashMap in Data object
        for (int i = 0; i < data.size(); i++) {
            d.addToHashMap(arr.get(i), data.get(i));
        }

        return d;
    }

    public static String alphaNumericStringGenerator(int length) {
        String outputString = "";

        for (int i = 0; i < length; i++) {

            // letter or number
            if (intInRange(1,2) == 1) {

                // get number 48 thru 57

                char c = (char) intInRange(48,57);

                outputString += c;

            }
            else {

                char c = (char) intInRange(65,90);
                // get letter 65 thru 90
                outputString += c;
            }
        }

        return outputString;
    }



    /*  Generate an integer within the range input.  */
    public static int intInRange(int min, int max) {
        int outInt = 0;

        outInt = min + (int)(Math.random() * ((max - min) + 1));

        return outInt;
    }

    public static double doubleInRange(int min, int max) {
        double outDouble = 0;

        outDouble = min + (double)(Math.random() * ((max - min) + 1));

        return outDouble;
    }

    public static int zipCodeGenerator() {
        return intInRange(10000, 99999);
    }

    public static int ssnGenerator() {

        return intInRange(100000000, 999999999);

    }

    public static String dobGenerator() {
        DateGenerator rd = new DateGenerator(LocalDate.of(1950, 1, 1), LocalDate.of(1997, 12, 31));

        return rd.generateRandomDate();
    }

    public static String hiredGenerator(int earliest, int latest) {
        DateGenerator rd = new DateGenerator(LocalDate.of(earliest, 1, 1), LocalDate.of(latest, 12, 31));

        return rd.generateRandomDate();
    }

}
