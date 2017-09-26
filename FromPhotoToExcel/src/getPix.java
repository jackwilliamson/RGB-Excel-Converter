

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import javax.imageio.ImageIO;

public class getPix {

//   public static void main(String[] args) throws IOException {
//
//	   //input the image
//      BufferedImage hugeImage = ImageIO.read(getPix.class.getResource("12000X12000.jpg"));
//         
//      int[][] result = convertTo2DWithoutUsingGetRGB(hugeImage);
//      // or
//      int[][] result2 = convertTo2DUsingGetRGB(hugeImage);
//       
//      
//   }

   static int[][] convertTo2DUsingGetRGB(BufferedImage image) {
      int width = image.getWidth();
      int height = image.getHeight();
      int[][] result = new int[height][width];

      for (int row = 0; row < height; row++) {
         for (int col = 0; col < width; col++) {
            result[row][col] = image.getRGB(row, col);
         }
      }
      
    //  int[][] picture = new int[height][width*3];
      
//      for(int i = 0; i < height; i++){
//    	  for(int j = 0; j < width*3; j+=3){
//    		  picture[i][j] = new Color(result[i][j]).getRed();
//    		  picture[i][j+1] = new Color(result[i][j]).getGreen();
//    		  picture[i][j+2] = new Color(result[i][j]).getBlue();
//    	  }
//      }

      return result;
   }

   static int[][] convertTo2DWithoutUsingGetRGB(BufferedImage image) {

      final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
      final int width = image.getWidth();
      final int height = image.getHeight();
      final boolean hasAlphaChannel = image.getAlphaRaster() != null;

      int[][] result = new int[height][width];
      int[][] picture = new int[height][width*3];
      if (hasAlphaChannel) {
          final int pixelLength = 4;
          for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
             int argb = 0;
             argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
             argb += ((int) pixels[pixel + 1] & 0xff); // blue
             argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
             argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
             result[row][col] = argb;
             col++;
             if (col == width) {
                col = 0;
                row++;
             }
          }
       } else {
          final int pixelLength = 3;
          for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
             int argb = 0;
             argb += -16777216; // 255 alpha
             argb += ((int) pixels[pixel] & 0xff); // blue
             argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
             argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red
             result[row][col] = argb;
             col++;
             if (col == width) {
                col = 0;
                row++;
             }
          }
       }
      
      
      for(int i = 0; i < height; i++){
    	  for(int j = 0, k = 0; j < width; j++, k+=3){
    		  picture[i][k] = new Color(result[i][j]).getRed();
    		  picture[i][k+1] = new Color(result[i][j]).getGreen();
    		  picture[i][k+2] = new Color(result[i][j]).getBlue();
    	  }
      }
      
      //System.out.println(picture[height-1][width*3-1]);

      return picture;
   }


}