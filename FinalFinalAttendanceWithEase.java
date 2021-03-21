
// Compile: javac --module-path %PATH_TO_JAVAFX% --add-modules javafx.controls FinalFinalAttendanceWithEase.java
// Run: java --module-path %PATH_TO_JAVAFX% --add-modules javafx.controls FinalFinalAttendanceWithEase
/*
 * Date: January 4th, 2021
 * Author: Mehvish Ali Khan, Selina Selvakumar, Risha Shah, & Michelle Ye
 * Description: Final program that analyzes Thoughtfulness Thursday Data* 
 * Integrators: Mehvish Ali Khan and Michelle Ye
 * NOTE: Mark Risha's, Michelle's, and Selina's individual code
 * */
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ComboBox;
import java.io.IOException;
import java.lang.NumberFormatException;
import java.util.Scanner;
import java.io.FileNotFoundException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import java.io.File;

public class FinalFinalAttendanceWithEase extends Application {
    // Declaring TextField elements
    TextField tfNumber, tfFileName, tfDate;
    // Declaring Button elements
    Button btnEnter, btn1, btn2, btn3;
    // Declaring Label elements
    Label lblWelcome, lblIntro, lblNumber, lblFileName, lblEnterDate, lblChooseStudent;
    // Declaring Combo Box element
    ComboBox<String> combo;
    // Declaring Border Pane element
    BorderPane pane;
    // Declaring VBox element
    static VBox paneCenter;
    // Declaring Scene element
    Scene scene;
    // Declaring Scroll Bar element
    ScrollBar scroll = new ScrollBar();
    // Declaring Group element
    Group root;
    // Declaring String for file name
    String fileName = "";
    // Declaring int for number of students
    int counter = 0;
    // Date user selects, used for x axis in graph
    String date = "";
    // Variable of result of attendance, used for plotting data in graph
    int attendance = 0;

    public static void main(String[] args) {
        // Launching the GUI
        launch(args);
    }

    /*
     * Creates the GUI for the program
     * 
     * @author Mehvish Ali Khan
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Creating group
        root = new Group();
        // Set title of the window for the GUI
        primaryStage.setTitle("Attendance With Ease");
        // Creating Combo Box element
        combo = new ComboBox<String>();
        // Setting the prompt text of the combo box
        combo.setPromptText("Select Student Number");
        // Creating TextField elements
        tfNumber = new TextField();
        tfFileName = new TextField(".csv");
        tfDate = new TextField("mmddyyyy");
        // Creating Button elements
        btnEnter = new Button("Enter Number of Students to generate options");
        btnEnter.setOnAction(new EventHandler<ActionEvent>() { // Add action for when btn1 is pressed
            /*
             * ActionEvent handler that performs specified action when btnEnter is clicked
             * 
             * @author Mehvish Ali Khan
             * 
             * @param btnEnter is clicked
             */
            @Override
            public void handle(ActionEvent event) {
                try {
                    counter = Integer.parseInt(tfNumber.getText());
                    makeOptions(counter);
                    printStatement("Number of Students: " + tfNumber.getText() + "; Values are saved");
                } catch (NumberFormatException e) {
                    Label lblNullError = new Label("Please enter a number for the number of students");
                    paneCenter.getChildren().add(lblNullError);
                }
            }
        });
        btn1 = new Button("Print out attendance records"); // Create button with label
        btn1.setOnAction(new EventHandler<ActionEvent>() { // Add action for when btn1 is pressed
            /*
             * ActionEvent handler that performs specified action when btn1 is clicked
             * 
             * @author Mehvish Ali Khan
             * 
             * @param btn1 is clicked
             */
            @Override
            public void handle(ActionEvent event) {
                try {
                    fileName = tfFileName.getText();
                    counter = Integer.parseInt(tfNumber.getText());
                    makeOptions(counter);
                    printStatement("");
                    printStatement("Print out attendance records for " + fileName);
                    String[] data = new String[counter]; // the data array's size depending on the number of students
                    readFile(fileName, data); // Sends the file name and empty array to method that reads file
                    printArray(data);
                } catch (NumberFormatException e) {
                    Label lblNullError = new Label("Please enter a number for the number of students");
                    paneCenter.getChildren().add(lblNullError);
                } catch (Exception e) {
                    Label lblException = new Label("Please check file name");
                    paneCenter.getChildren().add(lblException);
                }
            }
        });
        btn2 = new Button("Print names from lowest to highest attendance"); // Create button with label
        btn2.setOnAction(new EventHandler<ActionEvent>() { // Add action for when btn2 is pressed
            /*
             * ActionEvent handler that performs specified action when btn2 is clicked
             * 
             * @author Mehvish Ali Khan
             * 
             * @param btn2 is clicked
             */
            @Override
            public void handle(ActionEvent event) {
                try {
                    fileName = tfFileName.getText();
                    counter = Integer.parseInt(tfNumber.getText());
                    makeOptions(counter);
                    printStatement("");
                    printStatement("Print names from lowest to highest attendance for " + fileName);
                    orderingAbsences(fileName, counter);
                } catch (NumberFormatException e) {
                    Label lblNullError = new Label("Please enter a number for the number of students");
                    paneCenter.getChildren().add(lblNullError);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        btn3 = new Button("Create graph for selected student"); // Create button with label
        btn3.setOnAction(new EventHandler<ActionEvent>() { // Add action for when btn3 is pressed
            /*
             * ActionEvent handler that performs specified action when btn3 is clicked
             * 
             * @author Mehvish Ali Khan
             * 
             * @param btn3 is clicked
             */
            @Override
            public void handle(ActionEvent arg0) {
                try {
                    int studentNum = Integer.parseInt(combo.getValue()); // Stores selection from combo box
                    fileName = tfFileName.getText(); // Stores text entered in text field for file name
                    counter = Integer.parseInt(tfNumber.getText()); // Stores number of students entered in text field
                    date = tfDate.getText(); // Stores selection from date text field
                    printStatement("");
                    printStatement("Create graph for student " + combo.getValue() + " on " + date);
                    makeStudentGraph(fileName, counter, studentNum, date, attendance);
                    // graph(primaryStage);
                } catch (NumberFormatException e) {
                    Label lblNullError = new Label(
                            "Please make a selection of the student number, enter File Name, the Number of students, and the date for options to appear");
                    paneCenter.getChildren().add(lblNullError);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        // Creating the Label components and adding their text
        lblWelcome = new Label("Welcome to Attendance With Ease!");
        lblIntro = new Label("This program was made to help teachers analyse data from Thoughtful Thursday Forms.");
        lblNumber = new Label("Please enter the number of students plus one for the top row of file");
        lblFileName = new Label("Please enter data file name");
        lblEnterDate = new Label("Please enter date of interest without slashes");
        lblChooseStudent = new Label("Please use the dropdown menu to select a student number");
        // Create Border Pane element
        pane = new BorderPane();
        // Add borders of empty space around elements
        pane.setPadding(new Insets(40));
        // Create VBox element
        paneCenter = new VBox();
        // Set spacing between components
        paneCenter.setSpacing(10);
        // Center elements in the middle of pane
        pane.setCenter(paneCenter);
        // Add elements to pane in order
        paneCenter.getChildren().add(lblWelcome);
        paneCenter.getChildren().add(lblIntro);
        paneCenter.getChildren().add(lblNumber);
        paneCenter.getChildren().add(tfNumber);
        paneCenter.getChildren().add(lblFileName);
        paneCenter.getChildren().add(tfFileName);
        paneCenter.getChildren().add(btnEnter);
        paneCenter.getChildren().add(btn1);
        paneCenter.getChildren().add(btn2);
        paneCenter.getChildren().add(lblEnterDate);
        paneCenter.getChildren().add(tfDate);
        paneCenter.getChildren().add(lblChooseStudent);
        paneCenter.getChildren().add(combo);
        paneCenter.getChildren().add(btn3);
        // Creating Scene element and size
        scene = new Scene(root, 1200, 700);
        // Set Scene to be visible
        primaryStage.setScene(scene);
        // Adding compoents to group
        root.getChildren().addAll(paneCenter, scroll);
        // Scroll Bar features
        // Setting horizontal position by taking the width of the scene,
        // minus the width of the scroll bar so that it is on the right side
        scroll.setLayoutX(scene.getWidth() - scroll.getWidth());
        // Set minimum value to 0 so they cannot scroll higher than that
        scroll.setMin(0);
        // Set orientation vertical so that the scroll bar is up and down
        scroll.setOrientation(Orientation.VERTICAL);
        // Set position when GUI launches to 200
        scroll.setPrefHeight(150);
        // Set maximum position of scroll bar
        scroll.setMax(4000);
        // Add listener that changes the position when the scroll bar is moved
        scroll.valueProperty().addListener(new ChangeListener<Number>() {
            /*
             * Changes position of the scene when the scroll bar is used
             * 
             * @author Mehvish Ali Khan
             * 
             * @param Change in position for the scroll bar
             * 
             * @return void
             */
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                // Move the pane so that it matches with the scroll bar position
                paneCenter.setLayoutY(-new_val.doubleValue());
            }
        });
        // Set Scene to be shown
        primaryStage.show();
    }

    /*
     * Creates options for Combo Box by assigning a number for each student
     * 
     * @author Mehvish Ali Khan
     * 
     * @param counter, the number of students
     * 
     * @return void
     */
    public void makeOptions(int counter) {
        // Getting the observable list of the combo box
        ObservableList<String> list = combo.getItems();
        // Adding options for the combo box
        for (int i = 1; i <= counter; i++) {
            list.add(String.valueOf(i)); // Add number as an option
        }
    }

    /*
     * Reads file and stores data in an array
     * 
     * @author Risha
     * 
     * @param fileName name of the file being read and sorted
     * 
     * @param data Storing csv file in String array
     * 
     */
    public static void readFile(String fileName, String[] data) { // takes in the file name and empty array
        try {
            File file = new File(fileName);
            Scanner reader = new Scanner(file); // responsible for reading all the lines in the specific file
            for (int i = 0; i < data.length; i++) {
                String line = reader.nextLine(); // line stores each new line of the csv file
                data[i] = line; // each index of data array stores a new line
            }
        } catch (FileNotFoundException e) {
            printStatement("Error: File not found, check file name");
        } catch (Exception ex) {
            ex.printStackTrace(); // to prevent code from crashing in case it doesnt work
        }
    }

    /*
     * Sorts and then prints array of students from lowest attendance to highest
     * Note that this method will not run without other methods created by my group
     * members
     * 
     * @author Mehvish Ali Khan
     * 
     * @param fileName, name of the file being read and sorted
     * 
     * @param counter, number of students being considered
     * 
     * @return void
     */
    public static void orderingAbsences(String fileName, int counter) throws IOException {
        // Declaring array to store data from the file that is the size of the number of
        // students entered
        String[] data = new String[counter];
        // Call the read file method to store data from the file
        readFile(fileName, data);
        // Make new array that is the size of the other array to store the number of
        // times the student was here
        int[] absences = new int[counter];
        int here = 0;
        String[] temp;
        int holder = 0;
        String storage = "";
        // For loop to go through each line to count the attendances for each student
        for (int i = 0; i < data.length; i++) {
            String line = data[i];
            // Split the line for each student by commas to count each instance
            temp = line.split(",");
            // Loop through all the elements for each row (student)
            for (String elements : temp) {
                // If the element is TRUE it means they were here
                if (elements.equals("TRUE")) {
                    // Add all the times they were here
                    here++;
                }
            }
            // Store the number of time they were here after looping through entire row
            absences[i] = here;
            // set counter for 'heres' back to zero to start counting again for next student
            here = 0;
        }
        // Bubble sort
        for (int k = 0; k < data.length - 1; k++) {
            for (int j = 0; j < data.length - k - 1; j++) {
                // Comparing if the next student has more 'presents' than current
                if (absences[j] > absences[j + 1]) {
                    // Switch the absences data
                    holder = absences[j];
                    absences[j] = absences[j + 1];
                    absences[j + 1] = holder;
                    // Also switch the array of the student data so it is also being sorted
                    storage = data[j];
                    data[j] = data[j + 1];
                    data[j + 1] = storage;
                }
            }
        }
        // Call printArray method to print out sorted array
        printArray(data);
    }

    /*
     * Prints statement into the GUI window
     * 
     * @author Mehvish Ali Khan
     * 
     * @param text, the statement to print
     * 
     * @return void
     */
    public static void printStatement(String text) {
        Label words = new Label(text);
        paneCenter.getChildren().add(words);
    }

    /*
     * Prints array by line into the GUI window
     * 
     * @author Mehvish Ali Khan
     * 
     * @param arr, the array with the text to print
     * 
     * @return void
     */
    public static void printArray(String[] arr) {
        for (int i = 0; i < arr.length; i++) {
            Label line = new Label(arr[i]);
            paneCenter.getChildren().add(line);
        }
    }

    /**
     * Method to get selected student data to plot in bar graph
     * 
     * @author Michelle Ye
     * @param fileName   Name of file to send to readFile method
     * @param counter    Counting how many rows the data file inputted consists of:
     *                   to declare the size of the String array
     * @param studentNum Number of the specific student selected
     * @param date       Date selected for data
     * @param attendance Value that represents if the student is present or absent
     */
    public void makeStudentGraph(String fileName, int counter, int studentNum, String date, int attendance)
            throws IOException {
        // Declaring array to store data from the file that is the size of the number of
        // students entered
        String[] data = new String[counter];
        // Call the read file method to store data from the file
        readFile(fileName, data);
        Scanner reader = new Scanner(System.in);
        // Splitting student info to access index from int day
        String[] rowSplit;
        // Array of all dates on attendance
        int[] inputDate = { 9172020, 9242020, 10012020, 1082020, 10152020, 10222020, 10292020, 11052020, 11122020,
                11192020, 11262020, 12102020 };
        // Convert string to int because javafx only allows x value to be a string
        int dateInt = Integer.parseInt(date);
        // If user inputs date included in attendance, it finds it's index
        for (int i = 0; i <= inputDate.length; i++) {
            if (dateInt == inputDate[i]) {
                // Adds 1 because index starts from 0 instead of 1
                printStatement(String.valueOf(i + 1));
                // Isolate a row for the selected student's information
                printStatement(data[studentNum]);
                // Split isolated row by comma
                rowSplit = data[studentNum].split(",");
                // Get index from day wanted in isolated row
                printStatement(rowSplit[i + 4]);
                // If true, student was present and becomes a value of 1
                // Add 4 to account for index starting from 0 and skip to the true false section
                // of csv
                if (rowSplit[i + 4].equals("TRUE")) {
                    attendance = 1;
                    printStatement("Graph has been created.");
                }
                // If NOT true, student was absent and becomes a value of 0
                else {
                    attendance = 0;
                    printStatement("Graph has been created.");
                }
            }
        }
    }
}