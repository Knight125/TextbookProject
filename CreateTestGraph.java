import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class CreateTestGraph 
{

		BufferedImage testGraph = null;
		Color red = new Color(237,36,28);
		private int redRGB = red.getRGB();
		private int height = 450, width = 360;
		
		public CreateTestGraph(int height, int width)
		{
			this.height = height;
			this.width = width;
			testGraph = new BufferedImage(height, width, BufferedImage.TYPE_INT_RGB);
		}
		void setPixelToRed(int x, int y)
		{
			testGraph.setRGB(x, y, redRGB);
		}
		void saveGraphAsPNG(String PictureNameWithFileTypeAtEnd) throws IOException
		{
			File testGraphPNG = new File(PictureNameWithFileTypeAtEnd);
			ImageIO.write(testGraph, "PNG", testGraphPNG);
		}

}
