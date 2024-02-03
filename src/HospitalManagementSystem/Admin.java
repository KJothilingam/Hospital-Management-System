package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Admin {

    public void start() {

        while(true){

            System.out.println();
            System.out.println(" Administration ");
            System.out.println();
            
            System.out.println("1: Add Doctors");
            System.out.println("2: Remove Doctors");
            System.out.println("3: Exit  Admin-Page");
            
            Scanner sc=new Scanner(System.in);
            int ip=sc.nextInt();
            
            switch (ip) {
                case 1:
                newDoc();
                break;

                case 2:
                deleteDoc();
                break;

                case 3:
                System.out.println("Logout-Sucessesful");
                System.out.println();
                return;

                default:
                System.out.println("Enter Valid Option ");
                break;
            }
            
        }
            
            
            
        }

        public static void newDoc(){
            Doctor dc=new Doctor();
            dc.addDoc();
        }

        public void deleteDoc(){
                    Scanner scanner=new Scanner(System.in);
                    System.out.println("Enter Doctor Name : ");
                    String name=scanner.next();
                    try{
                        Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital","root","root");
                        String query="DELETE FROM doctors WHERE name='"+name+"'";
                        Statement statement = conn.createStatement();
                            statement.executeUpdate(query);
                            conn.close();
                    }catch(SQLException e){
                        e.printStackTrace();
                    }
          }
      

 
}
