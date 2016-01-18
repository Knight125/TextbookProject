package TextbookPriceComparison;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *All this class does it add ISBNs from a file into a database and initialize their visited values as 0
 * The visited values tell whether I was able to successfully pull data from amazon and camel
 * That way I don't don't keep visiting the one's that have no page.
 * @author Kara
 */
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import KLibrary.DatabaseOperations;
import KLibrary.ReadFile;
import java.sql.SQLException;
public class addISBNsToDatabase 
{
    public static void main(String[] args) throws SQLException, IOException
    {//this only adds new isbn entries, and is only used the first time
        ReadFile rf = new ReadFile("C:\\Users\\Kara\\Documents\\NetBeansProjects\\GettingPageStuff\\src\\gettingpagestuff\\ISBN10NoRepeats.txt");
        DatabaseOperations db = new DatabaseOperations("amzseller");
        
        String[] colNames = {"ISBN", "retrievedCamel", "retrievedAmazon","Primary Key (ISBN)"};
        String[] colTypes = {"varchar(255) NOT NULL","tinyint(1)","tinyint(1)",""};
        
        db.CreateTable("ISBNs", colNames, colTypes);
        
        String[] lines = rf.ReadingFile();
        String[] names = {"ISBN","retrievedCamel","retrievedAmazon"};
        
        for(String line: lines)
        {
            System.out.println(line);
            String[] values = {line,"0","0"};
            db.InsertInToTable("ISBNs", names, values);
        }
    }
    
}
