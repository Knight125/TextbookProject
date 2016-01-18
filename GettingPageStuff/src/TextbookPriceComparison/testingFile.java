/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TextbookPriceComparison;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
/**
 *
 * @author Kara
 */
public class testingFile 
{       
    private static Connection myConn = null;
        
    private static String user = "root";
    private static String pass = "";
        
    public static void main(String[] args) throws SQLException, IOException
    {
        Statement myStat = null;
        ResultSet myRs = null;
        String ISBN = "";
        
        myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/amzseller",user,pass);    
        myStat = myConn.createStatement();
            
       // myRs = myStat.executeQuery("select * from amzseller_data");
            
        if(myRs.next())
        {
            System.out.println(myRs.getString("ISBN10_ID"));
        }
        InputStream input = NewClass.class.getResourceAsStream("ISBN10NoRepeats.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
        
        while((ISBN = bufferedReader.readLine()) != null) 
        {
            
        }
        
    }
    public String A(String fileName)
    {
        return "";
    }
}
