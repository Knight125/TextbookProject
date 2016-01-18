/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TextbookPriceComparison;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author Kara
 */
public class NewClass 
{
    
//need to read in isbn file line by line and then add everything to a set, to get rid of duplicates, and then
//rewrite to another file
       public static void main(String [] args) 
       {
        // The name of the file to open.
        String fileName = "OnlyISBNs.txt";

        // This will reference one line at a time
        String line = null;
        Set ISBNs = new HashSet();
        System.out.println("here");
        try {
            // FileReader reads text files in the default encoding.
            FileOutputStream outputStream = new FileOutputStream("OnlyISBNs2.txt");
            
            // Always wrap FileReader in BufferedReader.
            
            InputStream input = NewClass.class.getResourceAsStream("OnlyISBNs.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));

            while((line = bufferedReader.readLine()) != null) 
            {
                ISBNs.add(line);
            }    
            
            bufferedReader.close(); 
            Iterator ISBNsIterator = ISBNs.iterator();
            while(ISBNsIterator.hasNext())
            {
                String ISBN = (String) ISBNsIterator.next();
                byte[] buffer = ISBN.getBytes();
                outputStream.write(buffer);
            }
            outputStream.close();
        }
        catch(FileNotFoundException ex) 
        {
            System.out.println("Unable to open file '" + fileName + "'");                
        }
        catch(IOException ex) 
        {
            ex.printStackTrace();
        }
    }
}
