/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

// @version 1.0

/**
 *
 * @author omarbakry
 */
public class StudentRegistration {

    private static List<Student> students = new ArrayList<>();
    private static final String BATCH_DATA_FOLDER = "/app/data/batch/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        do {
            System.out.println("MENU");
            System.out.println("1. Add student data");
            System.out.println("2. Add batch students data");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addStudentData(scanner);
                    break;
                case 2:
                    addBatchStudentsData(scanner);
                    break;
                case 3:
                    for (int i = 0; i < students.size(); i++) {
                        System.out.print(students.get(i).toString());
                    }
                    break;
                case 0:
                    System.out.println("Exiting the program...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            System.out.println();
        } while (choice != 0);

        scanner.close();
    }

    private static void addStudentData(Scanner scanner) {
        scanner.nextLine();
        System.out.println("Enter the students data in the following order (NAME, ID, COURSES).");
        String name = scanner.nextLine();
        int id = scanner.nextInt();
        scanner.nextLine();
        String coursesInput = scanner.nextLine();
        String[] courses = coursesInput.split(",");
        Student student = new Student(name, id, courses);

        // Write student data to the CSV file
        writeStudentDataToCSV(student);

        System.out.println("Student data added successfully.");
    }

    private static void writeStudentDataToCSV(Student student) {
        try ( FileWriter writer = new FileWriter("/app/data/batch/data.csv", true)) {
            // Append the student data to the CSV file
            writer.append(student.getName())
                    .append(";")
                    .append(String.valueOf(student.getId()))
                    .append(";");

            String[] courses = student.getCourses();
            for (int i = 0; i < courses.length; i++) {
                writer.append(courses[i]);
                if (i < courses.length - 1) {
                    writer.append(",");
                }
            }

            writer.append("\n");
        } catch (IOException e) {
            System.out.println("Error writing student data to CSV file: " + e.getMessage());
        }
    }

    private static void addBatchStudentsData(Scanner scanner) {
        File[] batchFiles = getEligibleBatchFiles();
        int numFiles = batchFiles.length;

        if (numFiles == 0) {
            System.out.println("No eligible batch files found.");
            return;
        }

        System.out.println("Available batch files:");
        for (int i = 0; i < numFiles; i++) {
            System.out.println((i + 1) + ". " + batchFiles[i].getName());
        }

        scanner.nextLine();
        System.out.print("Enter the name of the file to insert the students' info: ");
        String fileName = scanner.nextLine();

        // Find the selected file based on the entered file name
        File selectedFile = null;
        for (File file : batchFiles) {
            if (file.getName().equals(fileName)) {
                selectedFile = file;
                break;
            }
        }

        if (selectedFile == null) {
            System.out.println("File not found. Aborting batch insertion.");
            return;
        }

        List<Student> batchStudents = readBatchStudentsData(selectedFile);

        if (batchStudents.isEmpty()) {
            System.out.println("No student data found in the selected file.");
            return;
        }

        // Write batch students' data to the CSV file
        writeBatchStudentsDataToCSV(batchStudents);

        System.out.println("Batch student data added successfully.");
    }

    private static void writeBatchStudentsDataToCSV(List<Student> students) {
        try ( FileWriter writer = new FileWriter("data.csv", true)) {
            for (Student student : students) {
                writer.append(student.getName())
                        .append(";")
                        .append(String.valueOf(student.getId()))
                        .append(";");

                String[] courses = student.getCourses();
                for (int i = 0; i < courses.length; i++) {
                    writer.append(courses[i]);
                    if (i < courses.length - 1) {
                        writer.append(",");
                    }
                }

                writer.append("\n");
            }
        } catch (IOException e) {
            System.out.println("Error writing batch students' data to CSV file: " + e.getMessage());
        }
    }

    private static File[] getEligibleBatchFiles() {
        File folder = new File(BATCH_DATA_FOLDER);
        File[] allFiles = folder.listFiles();
        List<File> eligibleFiles = new ArrayList<>();

        if (allFiles != null) {
            for (File file : allFiles) {
                if (file.isFile() && file.getName().contains("verified")) {
                    eligibleFiles.add(file);
                }
            }
        }

        return eligibleFiles.toArray(new File[0]);
    }

    private static List<Student> readBatchStudentsData(File file) {
        List<Student> batchStudents = new ArrayList<>();

        try ( Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] studentData = line.split(";");

                if (studentData.length == 3) {
                    String name = studentData[0].trim();
                    int id = Integer.parseInt(studentData[1].trim());
                    String[] courses = studentData[2].trim().split(",");

                    Student student = new Student(name, id, courses);
                    batchStudents.add(student);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error reading batch file: " + e.getMessage());
        }

        return batchStudents;

    }
public static class Student {

    private String name;
    private int id;
    private String[] courses;

    public  Student(String name, int id, String[] courses) {
        this.name = name;
        this.id = id;
        this.courses = courses;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String[] getCourses() {
        return courses;
    }

    public void setCourses(String[] courses) {
        this.courses = courses;
    }
}

}

