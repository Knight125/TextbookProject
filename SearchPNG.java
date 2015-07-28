import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SearchPNG 
{
		
		static int highy, highx = 0;
		static int lowy = 10000, lowx = 1000;
	    static List<Rect> rects = new LinkedList<Rect>();
		static boolean EndOfGraph = false, firstPoint = true;
		// http://stackoverflow.com/questions/1050877/reading-in-a-jpeg-image-and-calculate-image-coordinates?rq=1
		
	    public static class Rect 
	    {
	        int x;//begin width
	        int y;//begin height
	    }
	   
	   static void readImage(String imageName) throws Exception
	   {
		   BufferedImage image = ImageIO.read(new File(imageName));
		   checkAllPixels(image);
	   }
	   
	   static void checkAllPixels(BufferedImage image) throws IOException
	   {
		   CreateTestGraph testGraph = new CreateTestGraph(450,360);//comment out if not testing
		   for (int x = 0; x < image.getWidth(); x++) //checks each pixel row by row
	        {
	            for (int y = 0; y < image.getHeight(); y++) 
	            {
	            	int imageHexRGB_AtPoint= image.getRGB(x,y);
					//TODO decide if I need to add || if a gap pixel
	            	if(IsRedPixel(imageHexRGB_AtPoint)&& (partOfGraph(image, x, y)||firstPoint))//the 2nd part of && is 4 getting rid of a extra pixels
	            	{
						firstPoint = false;
	            		addPoint(x,y);
						testGraph.setPixelToRed(x,y);//comment out if not testing
					}	            		            
	            }
	        }
			testGraph.saveGraphAsPNG("testGraph.png");//comment out if not testing
	   }
	 
	   static boolean IsRedPixel(int imageHexRGB_AtPoint) 
	   {
		   int[] RGB = getDecimalRGB(imageHexRGB_AtPoint);
		   int red = RGB[0], green = RGB[1], blue = RGB[2];
		   
		   if (red > 180 && green < 100 && blue < 100) 
			   return true;
		   return false;
	   } 
	   static boolean partOfGraph(BufferedImage image, int x, int y)
	   {
		   //if 5 or more pixels to the left of it are white, don't add
		   if(EndOfGraph)
			   return false;
		   else
		   {
			   if(numberOfRedPixelsSurrounding(image, x, y) <= 14)
			   {
				   EndOfGraph = true;
				   System.out.println("numSurrounding: "+numberOfRedPixelsSurrounding(image, x, y));
				   return false;
			   }
		   }
		   return true;		   		   
	   }
	   static int numberOfRedPixelsSurrounding(BufferedImage image, int x, int y)
	   {
		   int numberOfRedPixelsSurrounding = 0;
		   for(int offsetY = 0; offsetY <= 2; offsetY++)
		   {
			   for(int offsetX = 0; offsetX <=2;offsetX++)
			   {
				    if(x != 0 && y != 0)
					{
						int imageHexRGB_AtPointBehind = image.getRGB(x-offsetX,y);
						int imageHexRGB_AtPointBelow = image.getRGB(x,y-offsetY);
						int imageHexRGB_AtPointAbove = image.getRGB(x,y+offsetY);
						
						if(IsRedPixel(imageHexRGB_AtPointBehind))
						{
							numberOfRedPixelsSurrounding++;
						}
						if(IsRedPixel(imageHexRGB_AtPointBelow))
						{
							numberOfRedPixelsSurrounding++;
						}
						if(IsRedPixel(imageHexRGB_AtPointAbove))
						{
							numberOfRedPixelsSurrounding++;
						}
					}
			   }
		   }
		   return numberOfRedPixelsSurrounding;	
	   }
	   static int[] getDecimalRGB(int imageHexRGB_AtPoint)
	   {
		   int  red = (imageHexRGB_AtPoint & 0x00ff0000) >> 16;
           int  green = (imageHexRGB_AtPoint & 0x0000ff00) >> 8;
           int  blue = imageHexRGB_AtPoint & 0x000000ff;
           int[] RGB = {red,blue,green};
           return RGB;
	   }
	   static boolean IsBlackPixel(int red, int green, int blue) 
	   {
		   if (red < 5 && green < 5 && blue < 5) 
			   return true;
		   return false;
	   } 
	   static void addPoint(int x, int y)
	   {
		   Rect r = new Rect();
           r.x = x;
           r.y = y;
           rects.add(r);
	   }
	   
	 
	    public static void main(String[] args) throws Exception 
	    {
	    	readImage("YookosoTest.png");
	    }
	 }
