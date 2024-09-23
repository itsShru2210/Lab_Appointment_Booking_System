package LabBookingSystem;

import java.sql.*;
import java.util.Scanner;

public class AppointmentSystem {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Connection connection = null;

        try {
            // Get the database connection
            connection = DatabaseConnection.createC();

            if (connection == null) {
                System.out.println("Failed to establish database connection. Exiting...");
                return;
            }

            while (true) {
                System.out.println("\n*** Appointment System ***");
                System.out.println("1. Signup");
                System.out.println("2. Login");
                System.out.println("3. Book Appointment");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();
                sc.nextLine(); // consume newline character

                switch (choice) {
                    case 1:
                        signup(sc, connection);
                        break;
                    case 2:
                        login(sc, connection);
                        break;
                   
                    case 4:
                        System.out.println("Exiting system. Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid choice, please try again.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        } finally {
            // Close the connection and Scanner in the finally block
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
            sc.close();
        }
    }

    // User signup
    private static void signup(Scanner sc, Connection connection) {
        try {
            System.out.print("Enter Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Email: ");
            String email = sc.nextLine();

            System.out.print("Enter Password: ");
            String password = sc.nextLine();

            System.out.print("Enter Phone: ");
            long phone = sc.nextLong();

            // Validate and insert user details
            String query = "INSERT INTO UserDetails (name, email, password, phone) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setLong(4, phone);

            stmt.executeUpdate();
            System.out.println("User signed up successfully!");

        } catch (SQLException e) {
            System.out.println("Error during signup: " + e.getMessage());
        }
    }

    // User login
    private static void login(Scanner sc, Connection connection) {
        try {
            System.out.print("Enter Email: ");
            String email = sc.nextLine();

            System.out.print("Enter Password: ");
            String password = sc.nextLine();

            String query = "SELECT * FROM UserDetails WHERE email = ? AND password = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("Login successful! Welcome " + rs.getString("name"));
            
                bookAppointment(sc, connection);
                
                
            } else {
                System.out.println("Invalid email or password.");
            }

        } catch (SQLException e) {
            System.out.println("Error during login: " + e.getMessage());
        }
    }

    // Book appointment
    private static void bookAppointment(Scanner sc, Connection connection) {
        try {
        	 System.out.println("To Book The Appointment: ");
     
            System.out.println("Enter Patient Name: ");
            String patientName = sc.nextLine();

            System.out.print("Enter Patient Phone: ");
            long patientPhone = sc.nextLong();

            System.out.print("Enter Gender: ");
            String patientGender = sc.next();

            System.out.print("Enter Age: ");
            int patientAge = sc.nextInt();
            if (patientAge < 0) {
                System.out.println("Age cannot be negative.");
                return;
            }

            sc.nextLine(); // consume newline
            System.out.print("Enter Blood Group (optional): ");
            String bloodGroup = sc.nextLine();

            System.out.print("Enter Appointment Date (YYYY-MM-DD): ");
            String appointmentDateStr = sc.nextLine();
            java.sql.Date appointmentDate = java.sql.Date.valueOf(appointmentDateStr);

            System.out.print("Enter Appointment Time (HH:MM:SS) (optional): ");
            String appointmentTime = sc.nextLine();

            System.out.print("Enter Address: ");
            String address = sc.nextLine();

            System.out.print("Enter Patient Email: ");
            String patientEmail = sc.nextLine();

            System.out.println("Available Doctors: ");
            System.out.println("1 .Dr.Sham Kumar, Mobile:-1234567895");
            System.out.println("2 .Dr.Asha, Mobile:-78965384565 ");
            System.out.println("3 .Dr.Patil ,Mobile:-18530403972");
            System.out.println("4 .Dr.Sumit Mane, Mobile:-5789204682");
            System.out.println("Enter Doctor Name: ");
            String doctorName = sc.nextLine();

            System.out.print("Enter Doctor Phone: ");
            long doctorPhone = sc.nextLong();

            sc.nextLine(); // consume newline
            System.out.println("Available Tests: ");
            System.out.println("1 .X-Ray ");
            System.out.println("2 .Blood Test ");
            System.out.println("3 .Urine Test ");
            System.out.println("4 .Sugar Test ");
            System.out.println("Enter Test Name: ");
            String testName = sc.nextLine();

            // Insert appointment details
            String query = "INSERT INTO appointment (patientName, patientPhone, patientGender, patientAge, BloodGroup, " +
                    "appointmentDate, appontmentTime, addr, patientEmail, doctorName, doctorPhone, testName) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, patientName);
            stmt.setLong(2, patientPhone);
            stmt.setString(3, patientGender);
            stmt.setInt(4, patientAge);
            stmt.setString(5, bloodGroup);
            stmt.setDate(6, appointmentDate);
            stmt.setTime(7, appointmentTime.isEmpty() ? null : Time.valueOf(appointmentTime));
            stmt.setString(8, address);
            stmt.setString(9, patientEmail);
            stmt.setString(10, doctorName);
            stmt.setLong(11, doctorPhone);
            stmt.setString(12, testName);

            stmt.executeUpdate();
            System.out.println("Appointment booked successfully!");

        } catch (SQLException e) {
            System.out.println("Error while booking appointment: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid date or time format: " + e.getMessage());
        }
    }
}
