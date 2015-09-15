/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gettingpagestuff;
//will need to go through 1,034 ISBNS

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Kara
 * Maybe pull average from camel, and then compare
 * Maybe do a thing where it takes it out or starts tracking data when there is no camel data
 * Maybe start a data tracking thing from amazon on my own
 * 
 * Future plans for this:
 * -Edit this program to create a table for every ISBN that we can successfully pull a price from
 * -While creating that table, add the first price point for today's date
 * -Create a program that is running continously on the raspberry pi and every day at a certain minute,
 * pulls the price from amazon and adds it to the table. 
 * -Remember to add an exception in case the item is taken off of amazon.
 * -Have the program generate a file in case of an error and give an error log
 * 
 */
public class GettingPageStuff {
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1";
    public String ISBN = "", urlCamel = "", urlAmazon = "";
    public double highPrice, lowPrice,  currentPrice, tradeInValue, amzCurrentPrice;
    public String  highPriceDate, lowPriceDate, camelDate, currentDate;
    Connection connectionCamel = null, connectionAmazon = null;

    
    
    public GettingPageStuff(String ISBN) throws ParseException
    {
        
            this.ISBN = ISBN;
            this.urlCamel  = "http://www.camelcamelcamel.com/product/"+ISBN;
            this.urlAmazon = "http://www.amazon.com/dp/"+ISBN;
            
            connectionCamel = Jsoup.connect(urlCamel).userAgent(USER_AGENT);
            connectionAmazon = Jsoup.connect(urlAmazon).userAgent(USER_AGENT);
            
           try
           { 
            Document htmlDocumentCamel = connectionCamel.timeout(10*1000).get();
            this.highPrice          = getHighPrice(htmlDocumentCamel);
            this.highPriceDate      = getHighPriceDate(htmlDocumentCamel);
            this.lowPrice           = getLowPrice(htmlDocumentCamel);
            this.lowPriceDate       = getLowPriceDate(htmlDocumentCamel);
            this.currentPrice       = getCurrentPrice(htmlDocumentCamel);
            this.camelDate          = getCamelDate(htmlDocumentCamel); 
            System.out.println("Received web page at " + urlCamel);
           } 
           catch (IOException ex) 
           {System.out.println("Connection timeout for "+ISBN+" on camel");
            System.out.println(urlCamel);}
           try
           {
            Document htmlDocumentAmazon = connectionAmazon.timeout(10*1000).get();
            this.amzCurrentPrice    = getAmzCurrentPrice(htmlDocumentAmazon);
            this.tradeInValue       = getTradeInValue(htmlDocumentAmazon);
            System.out.println("Received web page at " + urlAmazon);
           }
           catch (IOException ex2) 
           {System.out.println("Connection timeout for "+ISBN+" on amazon");
            System.out.println(urlAmazon);}
            
           
            
            
        
        
    }
    public double getAmzCurrentPrice(Document htmlDocument) throws ParseException
    {
        //can't throw exception, because constructer would throw it
        //span.a-color-base:nth-child(3)
        
        String cssQueryAmzCurrentPrice = "#singleLineOlp > span:nth-child(1) > span:nth-child(2)";
        String cssQueryAmzCurrentPriceAlt = "#usedBuySection > a:nth-child(2) > h5:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > span:nth-child(2)";
        String cssQueryAmzCurrentPriceAltTwo = ".olp-used > a:nth-child(1)";
     
        double amzCurrentPrice = 0;
        Elements amzCurrentPriceE = htmlDocument.select(cssQueryAmzCurrentPrice);
        if(amzCurrentPriceE.isEmpty())//this is going through the alternatives
        {
            amzCurrentPriceE = htmlDocument.select(cssQueryAmzCurrentPriceAlt);
            if(amzCurrentPriceE.isEmpty())
            {
                amzCurrentPriceE = htmlDocument.select(cssQueryAmzCurrentPriceAltTwo);
                if(amzCurrentPriceE.isEmpty())
                    return 0;
                amzCurrentPrice = cleanDollarAmount(amzCurrentPriceE);
            }
            else
            {
                amzCurrentPrice = cleanDollarAmount(amzCurrentPriceE);
            }
        }
        else
        {
            amzCurrentPrice = cleanDollarAmount(amzCurrentPriceE);
        }
        return amzCurrentPrice;
    }
 
    public double getHighPrice(Document htmlDocument) throws ParseException
    {
        double highPriceDouble = 0;           
        String cssQueryHighPrice = "#section_used > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > table:nth-child(2) > tbody:nth-child(2) > tr:nth-child(2) > td:nth-child(2)";
        Elements highPrice = htmlDocument.select(cssQueryHighPrice); 
           if(highPrice.isEmpty())
                return 0;
        highPriceDouble = cleanDollarAmount(highPrice);
        
        return highPriceDouble;
    }

    public String getHighPriceDate(Document htmlDocument)
    {
        String highPriceString = null; 
        String cssQueryHighPriceDate = "#section_used > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > table:nth-child(2) > tbody:nth-child(2) > tr:nth-child(2) > td:nth-child(3)";
        Elements highPriceDate = htmlDocument.select(cssQueryHighPriceDate); 
        if(highPriceDate.isEmpty())
                return null;
        
        highPriceString = highPriceDate.get(0).text();
        
        return highPriceString;
    }
    public double getLowPrice(Document htmlDocument) throws ParseException
    {
        double lowPriceDouble = 0;
       
        String cssQueryLowPrice = "#section_used > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > table:nth-child(2) > tbody:nth-child(2) > tr:nth-child(3) > td:nth-child(2)";
        Elements lowPrice = htmlDocument.select(cssQueryLowPrice); 
        if(lowPrice.isEmpty())
            return 0;
        lowPriceDouble = cleanDollarAmount(lowPrice);
       
        return lowPriceDouble;
    }
    public String getLowPriceDate(Document htmlDocument)
    {
        String lowPriceDateString = null;
        String cssQueryLowPriceDate = "#section_used > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > table:nth-child(2) > tbody:nth-child(2) > tr:nth-child(3) > td:nth-child(3)";
        Elements lowPriceDate = htmlDocument.select(cssQueryLowPriceDate); 
        if(lowPriceDate.isEmpty())
            return null;
        lowPriceDateString = lowPriceDate.get(0).text();
      
        return lowPriceDateString;
    }
    public double getCurrentPrice(Document htmlDocument) throws ParseException
    {
        double currentPriceDouble = 0;
        String cssQueryCurrentPrice = "#section_used > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > table:nth-child(2) > tbody:nth-child(2) > tr:nth-child(1) > td:nth-child(2)";
        Elements currentPrice = htmlDocument.select(cssQueryCurrentPrice); 
        if(currentPrice.isEmpty())
            return 0;
        currentPriceDouble = cleanDollarAmount(currentPrice);
   
        return currentPriceDouble;
    }
    public String getCamelDate(Document htmlDocument)
    {
        String camelDateString = null;
        String cssQueryCamelDate = "#section_used > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > table:nth-child(2) > tbody:nth-child(2) > tr:nth-child(1) > td:nth-child(3)";
        Elements camelDate = htmlDocument.select(cssQueryCamelDate); 
        if(camelDate.isEmpty())
            return null;
        camelDateString = camelDate.get(0).text();
        
        return camelDateString;
    }
       public double cleanDollarAmount(Elements price) throws ParseException
    {
        NumberFormat format = NumberFormat.getCurrencyInstance();
        Number number = format.parse(price.get(0).text());
        return Double.parseDouble(number.toString()); 
    }
    
    public double getTradeInValue(Document htmlDocument) throws ParseException
    {
         String cssQueryTradeInPrice = "#tradeInButton_tradeInValue";
         String url = "http://amazon.com/dp/"+ISBN;
         double tradeInPrice = -1000000000;
         Elements tradeInPriceE = htmlDocument.select(cssQueryTradeInPrice);
         if(tradeInPriceE.isEmpty())
            return 0;
         tradeInPrice = cleanDollarAmount(tradeInPriceE);
         
         return tradeInValue;
    }
    
}
