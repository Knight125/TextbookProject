/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JunitTests;

import KLibrary.DatabaseOperations;
import KLibrary.ReadFile;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Kara
 */
public class NewEmptyJUnitTest {
    
    public NewEmptyJUnitTest() throws SQLException 
    {
        
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
     public void hello() throws SQLException 
     {
        DatabaseOperations test = new DatabaseOperations("amzseller");
        String[] colNamesAndTypes = {"price double","AMzdate timestamp"};
        String[] names = {"price"};
        String[] values = {"50.09"};
        test.CreateTable("testTable", colNamesAndTypes);
        test.InsertInToTable("testTable", names, values);
     }
     @Test
     public void readFileTest() throws IOException
     {
         String path = "C:\\Users\\Kara\\Documents\\NetBeansProjects\\GettingPageStuff\\test\\JunitTests\\testFile.txt";
         ReadFile test = new ReadFile(path);
         String[] lines = test.ReadingFile();
         System.out.println("here");
         for(String line: lines)
         {
             System.out.println(line);
         }
         
         System.out.println("here2");
     }
}
