package KLibrary;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kara
 */
public class ReadFile
    {
        public String[] lines;
        private String path;
        
        public ReadFile(String filePath)throws IOException
        {
            this.path = filePath;
            
        }
        public String[] ReadingFile()throws IOException
        {
            BufferedReader br = openFile(path);
            int numLines = findNumberOfLines(br);
            br =  openFile(path);//to reset the mark
            lines = fillArrayWithLines(numLines, br);
            return lines;
        }
        
        public BufferedReader openFile(String path) throws IOException
        {
            FileReader readsFileByByteCharacter = new FileReader(path);
            BufferedReader StoresCharactersOnBufferInMemory = new BufferedReader(readsFileByByteCharacter);
            return StoresCharactersOnBufferInMemory;
        }
        /*splitting it up into two methods versus one is a matter of saving space versus saving time
            if I added the lines to something like a static array, it might cost me more space
            versus the cost of going through the document twice, I'm not sure which is more ideal
            so I chose splitting into two, because it makes it easier to read*/
        public int findNumberOfLines(BufferedReader br) throws IOException
        {   
            String aLine = null;
            int numberOfLines = 0;
            while((aLine = br.readLine())!= null)
            {
                numberOfLines++;
            }
            
            return numberOfLines;
        }
        public String[] fillArrayWithLines(int numLines, BufferedReader br) throws IOException
        {
            String[] lines = new String[numLines];
            for(int i = 0; i < numLines;i++)
            {
                lines[i] = br.readLine();
            }
            return lines;
        }
    }
