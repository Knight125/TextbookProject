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
import java.text.ParseException;
/**
 *
 * @author Kara
 * Remember, if reading a result set, must do if(--.next())
 */
public class AddingToDataBase 
{       
    private static Connection myConn = null;
    private static String user = "root";
    private static String pass = "";
        
    public static void main(String[] args) throws SQLException, IOException, ParseException
    {
        //add while true loop, but need to make a table for each ISBN to track databases, 
        //with the primary key being the timestamp each datapoint is collected.
        //if not exists statement for initial add, in case I need to turn off my pi for some reason
        //I also need to create a webpage take is passed the info from the database
        Statement myStat = null;
        ResultSet myRs = null;
        String ISBN = "";
        int countWhatISBNProgramISOn = 1;
        //"truncate tablename;" gets rid of all the results in it
        
        myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/amzseller",user,pass);    
        myStat = myConn.createStatement();
      
        myStat.execute("Truncate amzseller_data;");
        
        InputStream input = AddingToDataBase.class.getResourceAsStream("ISBN10NoRepeats.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
      
        while((ISBN = bufferedReader.readLine()) != null) 
        {
            
            addISBNInfoToDatabase(ISBN, myStat);
                
            System.out.println(countWhatISBNProgramISOn+": "+ISBN);
            
            countWhatISBNProgramISOn++;
        }
        
        
    }
    public static void addISBNInfoToDatabase(String ISBN, Statement myStat)throws IOException, SQLException, ParseException
    {
        GettingPageStuff ISBNInfo = new GettingPageStuff(ISBN);
        
        insertIntoDatabase(ISBNInfo, myStat);
    }
    
    public static void insertIntoDatabase(GettingPageStuff ISBNInfo,  Statement myStat) 
            throws SQLException
    {
        double tradeInAmazonDifference = setDifferenceValue(ISBNInfo);
        
        //debugging print statements
        System.out.println("ISBNInfo.ISBN, "+ISBNInfo.ISBN+ " ISBNInfo.highPrice, "+ISBNInfo.highPrice+ " ISBNInfo.highPriceDate, "+ISBNInfo.highPriceDate);
        System.out.println(" ISBNInfo.lowPrice, " +ISBNInfo.lowPrice + " ISBNInfo.lowPriceDate, " +ISBNInfo.lowPriceDate+" ISBNInfo.amzCurrentPrice, "+ISBNInfo.amzCurrentPrice);
        System.out.println(" ISBNInfo.currentPrice, "+ISBNInfo.currentPrice + " ISBNInfo.currentDate, " +ISBNInfo.currentDate+" ISBNInfo.tradeInValue "+ISBNInfo.tradeInValue);
        //
        myStat.executeUpdate("INSERT INTO amzseller_data(ISBN10_ID, high_AMT, high_DT, "
                             + "low_AMT, low_DT, current_amz_AMT, current_camel_AMT, "
                             + "current_camel_DT,tradein_AMT, diff_tradein_amz )VALUES"
                             + "(\""+ISBNInfo.ISBN+"\", "+ISBNInfo.highPrice+", \""
                             +ISBNInfo.highPriceDate+"\"," + ISBNInfo.lowPrice+",\"" 
                             + ISBNInfo.lowPriceDate+"\","+ISBNInfo.amzCurrentPrice+","
                             +ISBNInfo.currentPrice+",\"" + ISBNInfo.currentDate+"\","
                             +ISBNInfo.tradeInValue+","+tradeInAmazonDifference+")");
    }
    public static double setDifferenceValue(GettingPageStuff ISBNInfo)
    {
        if(ISBNInfo.amzCurrentPrice != 0)
            return ISBNInfo.tradeInValue-ISBNInfo.amzCurrentPrice;
        else
            return 0;
        
    }
}
