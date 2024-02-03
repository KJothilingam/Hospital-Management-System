package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.mysql.cj.jdbc.Driver;

public class HospitalManagementSystem {
    private static final String url="jdbc:mysql://localhost:3306/hospital";
    private static final String username="root";
    private static final String password="root";

    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        Scanner sc=new Scanner(System.in) ;
        try{
            Connection connection=DriverManager.getConnection(url,username,password);
            Patient patient=new Patient(connection,sc);
            Doctor doctor=new Doctor(connection);

            while (true) {
                System.out.println("HOSPITAL MANAGEMENT SYSTEM ");

                System.out.println("1 : Add Patient");
                System.out.println("2 : View Patient");
                System.out.println("3 : View Doctors");
                System.out.println("4 : Book Appoinment"); 
                System.out.println("5 : Administration "); 
                System.out.println("6 : Exit"); 
                
                System.out.println("Enter Your Choice : "); 

                int choice=sc.nextInt();
                switch (choice) {
                    case 1:
                    //addp
                    patient.addPatient();
                    System.out.println();
                    break;
                    case 2:
                    //viewp
                    patient.viewPatients();
                    System.out.println();
                    break;
                    case 3:
                    //viewD
                    doctor.viewDoctors();
                    System.out.println();
                    break;
                    case 4:
                    //bookapp
                    bookappointment(patient, doctor, connection, sc);
                    System.out.println();
                    break;

                    case 5:
                    //admin
                    checkadmin();
                    break;

                    
                    case 6:
                    System.out.println("THANK YOU ! FOR USING HOSPITAL MANAGEMENT SYSTEM ");
                    System.out.println();
                    return; 

                    
                    default :
                    System.out.println("Enter Valis Choice !!! ");
                    System.out.println();
                    break;
                }

            }
            
        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    public static void bookappointment(Patient patient,Doctor doctor,Connection connection,Scanner sc){
        System.out.println("Enter Patient ID : ");
        int patient_id=sc.nextInt();
        System.out.println("Enter Doctor ID : ");
        int doctor_id =sc.nextInt();
        System.out.println("Enter Appointment Date int this Format :- (YYYY-MM-DD)");
        String AppoinmentDate=sc.next();

        if(patient.getPatientByid(patient_id) && (doctor.getDoctorByid(doctor_id))){
            if(checkDoctorAvailabilty(doctor_id,AppoinmentDate,connection)){
                String AppointmentQuery="INSERT INTO appointments(patient_id,doctor_id,appointment_date) VALUES(?,?,?)";
                try{
                  PreparedStatement preparedStatement =connection.prepareStatement(AppointmentQuery);
                  preparedStatement.setInt(1,patient_id);
                  preparedStatement.setInt(2,doctor_id);
                  preparedStatement.setString(3, AppoinmentDate);
                  int affectedRow=preparedStatement.executeUpdate();
                  if(affectedRow>0){
                    System.out.println("Appointment Booked");
                  }
                  else{
                    System.out.println("Failed To Book Appointment !!! ");
                  }

                }catch(SQLException e){
                    e.printStackTrace();
                }
            }else{
                System.out.println("Doctor Not Available on this Date !!!");
            }
        }else{
            System.out.println("Either Doctor or Patients Does't Exist");
        }

    }

    public static boolean checkDoctorAvailabilty(int doctor_id,String AppoinmentDate,Connection connection){
        String query="Select COUNT(*) from appointments where doctor_id=? AND  appointment_date=?";
        try{

            PreparedStatement preparedStatement=connection.prepareStatement(query);

            preparedStatement.setInt(1, doctor_id);
            preparedStatement.setString(2, AppoinmentDate);

            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                int count=resultSet.getInt(1);
                if(count==0){
                    return true;
                }
                else{
                    return false;
                }
            }


        }catch(SQLException e){
            e.printStackTrace();
        }

        return false;
    }
    public static void checkadmin(){
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter Admin Access-Code");
        String acs=sc.next();
        if(acs.equals("admin@1234")){
            Admin ad=new Admin();
            ad.start();
        }
        else{
            System.out.println("Invalid Access-Code !!! ");
            System.out.println();
        }
    }

}
