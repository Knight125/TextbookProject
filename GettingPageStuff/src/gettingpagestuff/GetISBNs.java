import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Scanner;


public class GetISBNs 
{
		public void ExtractISBNsFromFile(String fileName) throws IOException
		{
			
			File UnsearchedHTMlFile = new File(fileName);
			
			SearchForISBNAndWriteToFile(UnsearchedHTMlFile);
			
		}
		
		public void SearchForISBNAndWriteToFile(File UnsearchedHTMlFile) throws IOException
		{
			Scanner ScanningUnsearchedHTMLFile = new Scanner(UnsearchedHTMlFile);
			File OnlyISBNs = new File("OnlyISBNs.txt");
			FileOutputStream is = new FileOutputStream(OnlyISBNs);
                        OutputStreamWriter osw = new OutputStreamWriter(is);    
                        Writer w = new BufferedWriter(osw);
			
			while (ScanningUnsearchedHTMLFile.hasNextLine()) 
			{
				String ISBN = "error";
				String lineFromFile = ScanningUnsearchedHTMLFile.nextLine();
				if(lineFromFile.contains("padleft\">978")) 
				{ 
					ISBN = IsolateISBN(lineFromFile);
					WriteISBNToFile(ISBN, OnlyISBNs, w);
					break;
				}
			}
			w.close();
		}
		
		public String IsolateISBN(String lineFromFile)
		{
		
			int  beginningIndexOfISBN = lineFromFile.indexOf("978");
			
			String ISBN = lineFromFile.substring(beginningIndexOfISBN,beginningIndexOfISBN+13);
			
			return ISBN;
		}
		public void WriteISBNToFile(String ISBN, File OnlyISBNs, Writer w)
		{
			try 
			{
				w.write(ISBN+" ");
				ISBN = convertToTenDigitISBN(ISBN);
	            w.write(ISBN);
				w.write(System.getProperty( "line.separator" ));
	        } 
			catch (IOException e) 
			{
	            System.err.println("Problem writing to the file statsTest.txt");
	        }
		}
		public void getImages(String ISBN)
		{
			String url = "http://charts.camelcamelcamel.com/us/"+ISBN+"/amazon-used.png?force=1&zero=0&w=725&h=440&desired=false&legend=1&ilt=1&tp=all&fo=0&lang=en";
			
		}
		public String convertToTenDigitISBN(String ISBN)
		{
			String ISBNStripped = ISBN.substring(3,12);
			String checkDigit = "X";
			int checkDigitInt = calculateISBN10CheckDigit(ISBNStripped);
			System.out.println("checkDigit "+checkDigitInt);
			System.out.println("char at 0 " + ISBNStripped.charAt(0));
			System.out.println("char at 1 " + ISBNStripped.charAt(1));
			if(checkDigitInt != 10)
			{
				checkDigit = Integer.toString(checkDigitInt);
			}
			
			ISBN = ISBNStripped + checkDigit;
			return ISBN;
		}
		public int calculateISBN10CheckDigit(String ISBNStripped)
		{
			return (11-((10*(ISBNStripped.charAt(0)-'0')) + (9*(ISBNStripped.charAt(1)-'0')) + ((8*ISBNStripped.charAt(2)-'0')) + 
						(7*(ISBNStripped.charAt(3)-'0'))  + (6*(ISBNStripped.charAt(4)-'0')) + (5*(ISBNStripped.charAt(5)-'0')) + 
						(4*(ISBNStripped.charAt(6)-'0'))  + (3*(ISBNStripped.charAt(7)-'0')) + (2*(ISBNStripped.charAt(8)-'0'))%11)); 
		}
		
		public static void main(String[] args) throws IOException
		{
			GetISBNs InstanceOne = new GetISBNs();
			InstanceOne.ExtractISBNsFromFile("ListOfISBNsAndTextbookNames.txt");
		}
	

}
