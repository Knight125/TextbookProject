package gettingpagestuff;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kara
 */
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.sql.SQLException;
public class addISBNsToDatabase 
{
    public static void main(String[] args) throws SQLException, IOException
    {
        ReadFile rf = new ReadFile("C:\\Users\\Kara\\Documents\\NetBeansProjects\\GettingPageStuff\\src\\gettingpagestuff\\ISBN10NoRepeats.txt");
        KLibrary.DatabaseOperations db = new KLibrary.DatabaseOperations("amzseller");
        String[] colNamesAndTypes = {"ISBN varchar(255) NOT NULL", "retrievedCamel tinyint(1)", "retrievedAmazon tinyint(1)",
                                     "Primary Key (ISBN)"};
        db.CreateTable("ISBNs", colNamesAndTypes);
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
