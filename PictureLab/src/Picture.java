import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.*;
import java.util.List; // resolves problem with java.awt.List and java.util.List

/**
 * A class that represents a picture.  This class inherits from 
 * SimplePicture and allows the student to add functionality to
 * the Picture class.  
 * 
 * @author Barbara Ericson ericson@cc.gatech.edu
 */
public class Picture extends SimplePicture 
{
  ///////////////////// constructors //////////////////////////////////
  
  /**
   * Constructor that takes no arguments 
   */
  public Picture ()
  {
    /* not needed but use it to show students the implicit call to super()
     * child constructors always call a parent constructor 
     */
    super();  
  }
  
  /**
   * Constructor that takes a file name and creates the picture 
   * @param fileName the name of the file to create the picture from
   */
  public Picture(String fileName)
  {
    // let the parent class handle this fileName
    super(fileName);
  }
  
  /**
   * Constructor that takes the width and height
   * @param height the height of the desired picture
   * @param width the width of the desired picture
   */
  public Picture(int height, int width)
  {
    // let the parent class handle this width and height
    super(width,height);
  }
  
  /**
   * Constructor that takes a picture and creates a 
   * copy of that picture
   * @param copyPicture the picture to copy
   */
  public Picture(Picture copyPicture)
  {
    // let the parent class do the copy
    super(copyPicture);
  }
  
  /**
   * Constructor that takes a buffered image
   * @param image the buffered image to use
   */
  public Picture(BufferedImage image)
  {
    super(image);
  }
  
  ////////////////////// methods ///////////////////////////////////////
  
  /**
   * Method to return a string with information about this picture.
   * @return a string with information about the picture such as fileName,
   * height and width.
   */
  public String toString()
  {
    String output = "Picture, filename " + getFileName() + 
      " height " + getHeight() 
      + " width " + getWidth();
    return output;
    
  }
  
  /** Method to set the blue to 0 */
  public void zeroBlue()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        pixelObj.setBlue(0);
      }
    }
  }
  
  public void keepOnlyBlue()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        pixelObj.setRed(0);
        pixelObj.setGreen(0);
      }
    }
  }
  
  public void negate()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        pixelObj.setRed(255-pixelObj.getRed());
        pixelObj.setGreen(255-pixelObj.getGreen());
        pixelObj.setBlue(255-pixelObj.getBlue());
      }//for pixel pixelObj
    }//for pixel[] rowArray
  }//negate
  
  public void grayscale()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        int gray = ((pixelObj.getRed()+pixelObj.getGreen()+pixelObj.getBlue())/3);
        pixelObj.setRed(gray);
        pixelObj.setGreen(gray);
        pixelObj.setBlue(gray);
      }//for pixel pixelObj
    }//for pixel[] rowArray
  }//grayscale
  
  /** Method that mirrors the picture around a 
    * vertical mirror in the center of the picture
    * from left to right */
  public void mirrorVertical()
  {
    Pixel[][] pixels = this.getPixels2D();
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int width = pixels[0].length;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; col < width / 2; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][width - 1 - col];
        rightPixel.setColor(leftPixel.getColor());
      }
    } 
  }
  
    public void mirrorVerticalRightToLeft()
  {
    Pixel[][] pixels = this.getPixels2D();
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int width = pixels[0].length;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = width-1; col > width / 2; col--)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][width - 1 - col];
        rightPixel.setColor(leftPixel.getColor());
      }
    } 
  }
  
  public void mirrorHorizontal()
  {
       Pixel[][] pixels = this.getPixels2D();
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int width = pixels[0].length;
    for (int col = 0; col < width-1; col++)//int col = width-1; col > width / 2; col--)//int row = 0; row < pixels.length; row++)
    {
      for (int row = 0; row < pixels.length/2; row++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[pixels.length-1-row][col];
        rightPixel.setColor(leftPixel.getColor());
      }
    } 
  }
    
    public void mirrorHorizontalBotToTop()
  {
   /*    Pixel[][] pixels = this.getPixels2D();
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int width;
    for (int row = 0; row < pixels.length; row++)
    {
      width = row;
        for (int col = width-1; col > 0; col--)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row-width + col][width];
        rightPixel.setColor(leftPixel.getColor());
      }
    } */
    Pixel[][] pixels = this.getPixels2D();
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int width = pixels[0].length;
    for (int col = 0; col < width-1; col++)//int col = width-1; col > width / 2; col--)//int row = 0; row < pixels.length; row++)
    {
      for (int row = pixels.length-1; row > pixels.length/2; row--)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[pixels.length-1-row][col];
        rightPixel.setColor(leftPixel.getColor());
      }
    } 
  }
  
  public void mirrorDiagonal()
  {
   Pixel[][] pixels = this.getPixels2D();
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int width;
    for (int row = 0; row < pixels.length; row++)
    {
      width = row;
        for (int col = width-1; col > 0; col--)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row-width + col][width];
        rightPixel.setColor(leftPixel.getColor());
      }
  }
  }



  /** Method to show large changes in color 
    * @param edgeDist the distance for finding edges
    */
  public void edgeDetection(int edgeDist)
  {
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    Pixel[][] pixels = this.getPixels2D();
    Color rightColor = null;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; 
           col < pixels[0].length-1; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][col+1];
        rightColor = rightPixel.getColor();
        if (leftPixel.colorDistance(rightColor) > 
            edgeDist)
          leftPixel.setColor(Color.BLACK);
        else
          leftPixel.setColor(Color.WHITE);
      }
    }
  }
  
  
  /* Main method for testing - each class in Java can have a main 
   * method 
   */
  public static void main(String[] args) 
  {
    Picture beach = new Picture("beach.jpg");
    beach.explore();
    beach.zeroBlue();
    beach.explore();
  }
  
} // this } is the end of class Picture, put all new methods before this
