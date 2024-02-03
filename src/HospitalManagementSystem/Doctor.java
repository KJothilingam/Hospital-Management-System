package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor {
   private Connection connection;
   public Doctor(){}
  public Doctor(Connection connection){
    this.connection=connection;
  }

  public void addDoc(){
    
      Scanner scanner=new Scanner(System.in);
      System.out.println("Enter Doctor Name : ");
      String name=scanner.next();
      System.out.println("Enter Specialization : ");
      String spec=scanner.next();
      try{
        Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital","root","root");
        String query="INSERT INTO doctors(name,specialization) VALUES(?,?)";
        PreparedStatement preparedStatement=conn.prepareStatement(query);
        preparedStatement.setString(1,name);
        preparedStatement.setString(2, spec);
        int affectedRow=preparedStatement.executeUpdate();
        if(affectedRow>0){
            System.out.println("Doctor Added Successfully !!! ");
        }
        else{
            System.out.println("Failed to Add Doctor !!! ");
        }
    }catch(SQLException e){
        e.printStackTrace();
    }
  }



  public void viewDoctors(){
    String query="Select * from doctors";
    try{

        PreparedStatement preparedStatement=connection.prepareStatement(query);

        ResultSet resultSet=preparedStatement.executeQuery();

        System.out.println("Doctors: ");
        System.out.println("+------------+--------------------+------------ ---------+");
        System.out.println("| Doctor  ID | Name               | Specialization       |");
        System.out.println("+------------+--------------------+----------------------+");
        
        while (resultSet.next()) {
            int id=resultSet.getInt("id");
            String name=resultSet.getString("name");
            String specialization=resultSet.getString("specialization");
            System.out.printf("| %-10s | %-18s | %-20s |\n",id,name,specialization);
            System.out.println("+------------+--------------------+----------------------+");
        }


    }catch(SQLException e){
        e.printStackTrace();
    }
  }

  public boolean getDoctorByid(int id){
    String query="select * from doctors where id=? ";
    try{
        PreparedStatement preparedStatement=connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet resultSet=preparedStatement.executeQuery();
        if(resultSet.next()){
            return true;
        }
        else{
            return false;
        }
    }catch(SQLException e){
        e.printStackTrace(); 
    }
    return false;
  }
   
}
