/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sciencefair;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.*;
import java.util.List;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import java.awt.*;
import java.io.*;
import java.awt.geom.*;


public class ScienceFair {

    public static void main(String[] args) {
     
        //DigitalPicture picture = new DigitalPicture();
        //Pixel[][] pixels = this.getPixels2D();
        
    }
    
}


class Pixel {
    private DigitalPicture picture;

    private int x; 

    private int y; 

    public Pixel(DigitalPicture picture, int x, int y) {
      this.picture = picture;
      this.x = x;
      this.y = y;
    }

    public int getX() { return x; }

    public int getY() { return y; }

    public int getRow() { return y; }

    public int getCol() { return x; }

    public int getAlpha() {
      int value = picture.getBasicPixel(x,y);
      int alpha = (value >> 24) & 0xff; 
      return alpha;
    }

    public int getRed() {
      int value = picture.getBasicPixel(x,y);
      int red = (value >> 16) & 0xff;
      return red;
    }

    public static int getRed(int value)
    {
      int red = (value >> 16) & 0xff;
      return red;
    }

    public int getGreen() {
      int value = picture.getBasicPixel(x,y);
      int green = (value >>  8) & 0xff;

      return green;
    }

    public static int getGreen(int value)
    {
      int green = (value >> 8) & 0xff;
      return green;
    }

    public int getBlue() {
      int value = picture.getBasicPixel(x,y);
      int blue = value & 0xff;

      return blue;
    }

    public static int getBlue(int value)
    {
      int blue = value & 0xff;
      return blue;
    }

    public Color getColor() {
      int value = picture.getBasicPixel(x,y);
      int red = (value >> 16) & 0xff;
      int green = (value >>  8) & 0xff;
      int blue = value & 0xff;

      return new Color(red,green,blue);
    }

    public void setColor(Color newColor) {
      int red = newColor.getRed();
      int green = newColor.getGreen();
      int blue = newColor.getBlue();
      updatePicture(this.getAlpha(),red,green,blue);
    }

    public void updatePicture(int alpha, int red, int green, int blue) {
      int value = (alpha << 24) + (red << 16) + (green << 8) + blue;
      picture.setBasicPixel(x,y,value);
    }

    private static int correctValue(int value) {
      if (value < 0)
        value = 0;
      if (value > 255)
        value = 255;
      return value;
    }

    public void setRed(int value) {
      int red = correctValue(value);
      updatePicture(getAlpha(), red, getGreen(), getBlue());
    }

    public void setGreen(int value) {
      int green = correctValue(value);
      updatePicture(getAlpha(), getRed(), green, getBlue());
    } 

    public void setBlue(int value) {
      int blue = correctValue(value);
      updatePicture(getAlpha(), getRed(), getGreen(), blue);
    } 

    public void setAlpha(int value) {
      int alpha = correctValue(value);
      updatePicture(alpha, getRed(), getGreen(), getBlue());
    } 

   public double colorDistance(Color testColor) {
     double redDistance = this.getRed() - testColor.getRed();
     double greenDistance = this.getGreen() - testColor.getGreen();
     double blueDistance = this.getBlue() - testColor.getBlue();
     double distance = Math.sqrt(redDistance * redDistance + 
                                 greenDistance * greenDistance +
                                 blueDistance * blueDistance);
     return distance;
   }

   public static double colorDistance(Color color1,Color color2) {
     double redDistance = color1.getRed() - color2.getRed();
     double greenDistance = color1.getGreen() - color2.getGreen();
     double blueDistance = color1.getBlue() - color2.getBlue();
     double distance = Math.sqrt(redDistance * redDistance + 
                                 greenDistance * greenDistance +
                                 blueDistance * blueDistance);
     return distance;
   }

   public double getAverage() {
     double average = (getRed() + getGreen() + getBlue()) / 3.0;
     return average;
   }

    public String toString() {
      return "Pixel row=" + getRow() + 
        " col=" + getCol() +
        " red=" + getRed() + 
        " green=" + getGreen() + 
        " blue=" + getBlue();
    }

}

interface DigitalPicture {
  public String getFileName(); 
  public String getTitle(); 
  public void setTitle(String title); 
  public int getWidth(); 
  public int getHeight(); 
  public Image getImage(); 
  public BufferedImage getBufferedImage(); 
  public int getBasicPixel(int x, int y);   
  public void setBasicPixel(int x, int y, int rgb);
  public Pixel getPixel(int x, int y); 
  public Pixel[] getPixels(); 
  public Pixel[][] getPixels2D(); 
  public void load(Image image); 
  public boolean load(String fileName); 
  public void show();
  public void explore(); 
  public boolean write(String fileName);
}



/**
 * A class that represents a simple picture.  A simple picture may have
 * an associated file name and a title.  A simple picture has pixels, 
 * width, and height.  A simple picture uses a BufferedImage to 
 * hold the pixels.  You can show a simple picture in a 
 * PictureFrame (a JFrame).  You can also explore a simple picture.
 * 
 * @author Barb Ericson ericson@cc.gatech.edu
 */
class SimplePicture implements DigitalPicture {

  private String fileName;

  private String title;
 
  private BufferedImage bufferedImage;
 
  private String extension;
  
    public SimplePicture() {this(200,100);}
 

 public SimplePicture(String fileName){load(fileName);}
 
 public  SimplePicture(int width, int height) {
   bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
   title = "None";
   fileName = "None";
   extension = "jpg";
   setAllPixelsToAColor(Color.white);
 }

 public  SimplePicture(int width, int height, Color theColor) {
   this(width,height);
   setAllPixelsToAColor(theColor);
 }

 public SimplePicture(SimplePicture copyPicture) {
   if (copyPicture.fileName != null) {
      this.fileName = new String(copyPicture.fileName);
      this.extension = copyPicture.extension;
   }
   if (copyPicture.title != null)
      this.title = new String(copyPicture.title);
   if (copyPicture.bufferedImage != null) {
     this.bufferedImage = new BufferedImage(copyPicture.getWidth(),
                                            copyPicture.getHeight(), BufferedImage.TYPE_INT_RGB);
     this.copyPicture(copyPicture);
   }
 }
 
 public SimplePicture(BufferedImage image) {
   this.bufferedImage = image;
   title = "None";
   fileName = "None";
   extension = "jpg";
 }

 public String getExtension() { return extension; }

 public void copyPicture(SimplePicture sourcePicture) {
   Pixel sourcePixel = null;
   Pixel targetPixel = null;

   for (int sourceX = 0, targetX = 0; sourceX < sourcePicture.getWidth() && targetX < this.getWidth(); sourceX++, targetX++) {
     for (int sourceY = 0, targetY = 0; sourceY < sourcePicture.getHeight() && targetY < this.getHeight(); sourceY++, targetY++) {
       sourcePixel = sourcePicture.getPixel(sourceX,sourceY);
       targetPixel = this.getPixel(targetX,targetY);
       targetPixel.setColor(sourcePixel.getColor());
     }
   }
   
 }

 public void setAllPixelsToAColor(Color color) {
   // loop through all x
   for (int x = 0; x < this.getWidth(); x++) {
     // loop through all y
     for (int y = 0; y < this.getHeight(); y++) {
       getPixel(x,y).setColor(color);
     }
   }
 }

 public BufferedImage getBufferedImage() {
    return bufferedImage;
 }
 
 public Graphics getGraphics() {
   return bufferedImage.getGraphics();
 }

 public Graphics2D createGraphics() {
   return bufferedImage.createGraphics();
 }

 public String getFileName() { return fileName; }
 
 public void setFileName(String name) {
   fileName = name;
 }

 public int getWidth() { return bufferedImage.getWidth(); }

 public int getHeight() { return bufferedImage.getHeight(); }

 public Image getImage() { return bufferedImage; }
 
 public int getBasicPixel(int x, int y) { return bufferedImage.getRGB(x,y); }

 public void setBasicPixel(int x, int y, int rgb) { bufferedImage.setRGB(x,y,rgb); }
  
 public Pixel getPixel(int x, int y) {
   Pixel pixel = new Pixel(this,x,y);
   return pixel;
 }

 public Pixel[] getPixels() {
   int width = getWidth();
   int height = getHeight();
   Pixel[] pixelArray = new Pixel[width * height];
   for (int row = 0; row < height; row++) 
     for (int col = 0; col < width; col++) 
       pixelArray[row * width + col] = new Pixel(this,col,row);
   return pixelArray;
 }

 public Pixel[][] getPixels2D()
 {
   int width = getWidth();
   int height = getHeight();
   Pixel[][] pixelArray = new Pixel[height][width];
   for (int row = 0; row < height; row++) 
     for (int col = 0; col < width; col++) 
       pixelArray[row][col] = new Pixel(this,col,row);
   return pixelArray;
 }

 public void load(Image image) {
   Graphics2D graphics2d = bufferedImage.createGraphics();
   graphics2d.drawImage(image,0,0,null);
   show();
 }
 
 /**
  * Method to show the picture in a picture frame
  */
 public void show()
 {
    // if there is a current picture frame then use it 
   if (pictureFrame != null)
     pictureFrame.updateImageAndShowIt();
   
   // else create a new picture frame with this picture 
   else
     pictureFrame = new PictureFrame(this);
 }
 
 /**
  * Method to hide the picture display
  */
 public void hide()
 {
   if (pictureFrame != null)
     pictureFrame.setVisible(false);
 }
 
 /**
  * Method to make this picture visible or not
  * @param flag true if you want it visible else false
  */
 public void setVisible(boolean flag)
 {
   if (flag)
     this.show();
   else 
     this.hide();
 }

 /**
  * Method to open a picture explorer on a copy (in memory) of this 
  * simple picture
  */
 public void explore()
 {
   // create a copy of the current picture and explore it
   new PictureExplorer(new SimplePicture(this));
 }
 
 /**
  * Method to force the picture to repaint itself.  This is very
  * useful after you have changed the pixels in a picture and
  * you want to see the change.
  */
 public void repaint()
 {
   // if there is a picture frame tell it to repaint
   if (pictureFrame != null)
     pictureFrame.repaint();
   
   // else create a new picture frame
   else
     pictureFrame = new PictureFrame(this);
 }
 
 /**
  * Method to load the picture from the passed file name
  * @param fileName the file name to use to load the picture from
  * @throws IOException if the picture isn't found
  */
 public void loadOrFail(String fileName) throws IOException
 {
    // set the current picture's file name
   this.fileName = fileName;
   
   // set the extension
   int posDot = fileName.indexOf('.');
   if (posDot >= 0)
     this.extension = fileName.substring(posDot + 1);
   
   // if the current title is null use the file name
   if (title == null)
     title = fileName;
   
   File file = new File(this.fileName);

   if (!file.canRead()) 
   {
     // try adding the media path 
     file = new File(FileChooser.getMediaPath(this.fileName));
     if (!file.canRead())
     {
       throw new IOException(this.fileName +
                             " could not be opened. Check that you specified the path");
     }
   }
   
   bufferedImage = ImageIO.read(file);
 }


 /**
  * Method to read the contents of the picture from a filename  
  * without throwing errors
  * @param fileName the name of the file to write the picture to
  * @return true if success else false
  */
 public boolean load(String fileName)
 {
     try {
         this.loadOrFail(fileName);
         return true;

     } catch (Exception ex) {
         System.out.println("There was an error trying to open " + fileName);
         bufferedImage = new BufferedImage(600,200,
                                           BufferedImage.TYPE_INT_RGB);
         addMessage("Couldn't load " + fileName,5,100);
         return false;
     }
         
 }

 /**
  * Method to load the picture from the passed file name
  * this just calls load(fileName) and is for name compatibility
  * @param fileName the file name to use to load the picture from
  * @return true if success else false
  */
 public boolean loadImage(String fileName)
 {
     return load(fileName);
 }
 
 /**
  * Method to draw a message as a string on the buffered image 
  * @param message the message to draw on the buffered image
  * @param xPos  the x coordinate of the leftmost point of the string 
  * @param yPos  the y coordinate of the bottom of the string  
  */
 public void addMessage(String message, int xPos, int yPos)
 {
   // get a graphics context to use to draw on the buffered image
   Graphics2D graphics2d = bufferedImage.createGraphics();
   
   // set the color to white
   graphics2d.setPaint(Color.white);
   
   // set the font to Helvetica bold style and size 16
   graphics2d.setFont(new Font("Helvetica",Font.BOLD,16));
   
   // draw the message
   graphics2d.drawString(message,xPos,yPos);
   
 }
 
 /**
  * Method to draw a string at the given location on the picture
  * @param text the text to draw
  * @param xPos the left x for the text 
  * @param yPos the top y for the text
  */
 public void drawString(String text, int xPos, int yPos)
 {
   addMessage(text,xPos,yPos);
 }
 
 /**
   * Method to create a new picture by scaling the current
   * picture by the given x and y factors
   * @param xFactor the amount to scale in x
   * @param yFactor the amount to scale in y
   * @return the resulting picture
   */
  public Picture scale(double xFactor, double yFactor)
  {
    // set up the scale transform
    AffineTransform scaleTransform = new AffineTransform();
    scaleTransform.scale(xFactor,yFactor);
    
    // create a new picture object that is the right size
    Picture result = new Picture((int) (getWidth() * xFactor),
                                 (int) (getHeight() * yFactor));
    
    // get the graphics 2d object to draw on the result
    Graphics graphics = result.getGraphics();
    Graphics2D g2 = (Graphics2D) graphics;
    
    // draw the current image onto the result image scaled
    g2.drawImage(this.getImage(),scaleTransform,null);
    
    return result;
  }
  
  /**
   * Method to create a new picture of the passed width. 
   * The aspect ratio of the width and height will stay
   * the same.
   * @param width the desired width
   * @return the resulting picture
   */
  public Picture getPictureWithWidth(int width)
  {
    // set up the scale transform
    double xFactor = (double) width / this.getWidth();
    Picture result = scale(xFactor,xFactor);
    return result;
  }
  
  /**
   * Method to create a new picture of the passed height. 
   * The aspect ratio of the width and height will stay
   * the same.
   * @param height the desired height
   * @return the resulting picture
   */
  public Picture getPictureWithHeight(int height)
  {
    // set up the scale transform
    double yFactor = (double) height / this.getHeight();
    Picture result = scale(yFactor,yFactor);
    return result;
  }
 
 /**
  * Method to load a picture from a file name and show it in a picture frame
  * @param fileName the file name to load the picture from
  * @return true if success else false
  */
 public boolean loadPictureAndShowIt(String fileName)
 {
   boolean result = true;  // the default is that it worked
   
   // try to load the picture into the buffered image from the file name
   result = load(fileName);
   
   // show the picture in a picture frame
   show();
   
   return result;
 }
 
 /**
  * Method to write the contents of the picture to a file with 
  * the passed name
  * @param fileName the name of the file to write the picture to
  */
 public void writeOrFail(String fileName) throws IOException
 {
   String extension = this.extension; // the default is current
   
   // create the file object
   File file = new File(fileName);
   File fileLoc = file.getParentFile(); // directory name
   
   // if there is no parent directory use the current media dir
   if (fileLoc == null)
   {
     fileName = FileChooser.getMediaPath(fileName);
     file = new File(fileName);
     fileLoc = file.getParentFile(); 
   }
   
   // check that you can write to the directory 
   if (!fileLoc.canWrite()) {
        throw new IOException(fileName +
        " could not be opened. Check to see if you can write to the directory.");
   }
   
   // get the extension
   int posDot = fileName.indexOf('.');
   if (posDot >= 0)
       extension = fileName.substring(posDot + 1);
   
   // write the contents of the buffered image to the file
   ImageIO.write(bufferedImage, extension, file);
     
 }

 /**
  * Method to write the contents of the picture to a file with 
  * the passed name without throwing errors
  * @param fileName the name of the file to write the picture to
  * @return true if success else false
  */
 public boolean write(String fileName)
 {
     try {
         this.writeOrFail(fileName);
         return true;
     } catch (Exception ex) {
         System.out.println("There was an error trying to write " + fileName);
         ex.printStackTrace();
         return false;
     }
         
 }
 
 /**
  * Method to get the directory for the media
  * @param fileName the base file name to use
  * @return the full path name by appending
  * the file name to the media directory
  */
 public static String getMediaPath(String fileName) {
   return FileChooser.getMediaPath(fileName);
 }
 
  /**
   * Method to get the coordinates of the enclosing rectangle after this
   * transformation is applied to the current picture
   * @return the enclosing rectangle
   */
  public Rectangle2D getTransformEnclosingRect(AffineTransform trans)
  {
    int width = getWidth();
    int height = getHeight();
    double maxX = width - 1;
    double maxY = height - 1;
    double minX, minY;
    Point2D.Double p1 = new Point2D.Double(0,0);
    Point2D.Double p2 = new Point2D.Double(maxX,0);
    Point2D.Double p3 = new Point2D.Double(maxX,maxY);
    Point2D.Double p4 = new Point2D.Double(0,maxY);
    Point2D.Double result = new Point2D.Double(0,0);
    Rectangle2D.Double rect = null;
    
    // get the new points and min x and y and max x and y
    trans.deltaTransform(p1,result);
    minX = result.getX();
    maxX = result.getX();
    minY = result.getY();
    maxY = result.getY();
    trans.deltaTransform(p2,result);
    minX = Math.min(minX,result.getX());
    maxX = Math.max(maxX,result.getX());
    minY = Math.min(minY,result.getY());
    maxY = Math.max(maxY,result.getY());
    trans.deltaTransform(p3,result);
    minX = Math.min(minX,result.getX());
    maxX = Math.max(maxX,result.getX());
    minY = Math.min(minY,result.getY());
    maxY = Math.max(maxY,result.getY());
    trans.deltaTransform(p4,result);
    minX = Math.min(minX,result.getX());
    maxX = Math.max(maxX,result.getX());
    minY = Math.min(minY,result.getY());
    maxY = Math.max(maxY,result.getY());
    
    // create the bounding rectangle to return
    rect = new Rectangle2D.Double(minX,minY,maxX - minX + 1, maxY - minY + 1);
    return rect;
  }
  
  /**
   * Method to get the coordinates of the enclosing rectangle after this
   * transformation is applied to the current picture
   * @return the enclosing rectangle
   */
  public Rectangle2D getTranslationEnclosingRect(AffineTransform trans)
  {
    return getTransformEnclosingRect(trans);
  }
 
 /**
  * Method to return a string with information about this picture
  * @return a string with information about the picture 
  */
 public String toString()
 {
   String output = "Simple Picture, filename " + fileName + 
     " height " + getHeight() + " width " + getWidth();
   return output;
 }

} // end of SimplePicture class


/**
 * A class that represents a picture.  This class inherits from 
 * SimplePicture and allows the student to add functionality to
 * the Picture class.  
 * 
 * @author Barbara Ericson ericson@cc.gatech.edu
 */
class Picture extends SimplePicture 
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
  
  public void keepOnlyBlue(){
      Pixel[][] pixels = this.getPixels2D();
      for(Pixel[] rowArray: pixels){
          for(Pixel pixelObj :rowArray){
              pixelObj.setRed(0);
              pixelObj.setGreen(0);
          }
      }
  }
  
  public void keepOnlyRed(){
      Pixel[][] pixels = this.getPixels2D();
      for(Pixel[] rowArray: pixels){
          for(Pixel pixelObj :rowArray){
              pixelObj.setBlue(0);
              pixelObj.setGreen(0);
          }
      }
  }
  
  
  public void negate(){
      Pixel[][] pixels = this.getPixels2D();
      for(Pixel[] rowArray: pixels){
          for(Pixel pixelObj: rowArray){
              pixelObj.setRed(255-pixelObj.getRed());
              pixelObj.setGreen(255-pixelObj.getGreen());
              pixelObj.setBlue(255-pixelObj.getBlue());
          }
      }
  }
  
  public void grayscale(){
      Pixel[][] pixels = this.getPixels2D();
      int avg;
      for(Pixel[] rowArray: pixels){
          for(Pixel pixelObj: rowArray){
              avg = (pixelObj.getGreen() + pixelObj.getRed() + pixelObj.getBlue())/3;
              pixelObj.setRed(avg);
              pixelObj.setGreen(avg);
              pixelObj.setBlue(avg);
          }
      }
  }
  
  public void fixUnderwater() {
      Pixel[][] pixels = this.getPixels2D();
      for(Pixel[] rowArray: pixels){
          for(Pixel pixelObj: rowArray){
              pixelObj.setRed(pixelObj.getRed()*6);
          }
      }
  }
  
  public void mirrorVerticalRightToLeft(){
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
  
  public void mirrorHorizontal(){
    Pixel[][] pixels = this.getPixels2D();
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int width = pixels[0].length;
    for (int col = 0; col < width; col++){
      for (int row = 0; row < pixels.length/2; row++){
        leftPixel = pixels[row][col];
        rightPixel = pixels[pixels.length - 1 - row][col];
        rightPixel.setColor(leftPixel.getColor());
      }
    } 
  }
  
  public void mirrorHorizontalBotToTop(){
    Pixel[][] pixels = this.getPixels2D();
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int width = pixels[0].length;
    for (int col = 0; col < width; col++){
      for (int row = pixels.length -1; row > pixels.length/2; row--){
        leftPixel = pixels[row][col];
        rightPixel = pixels[pixels.length - 1 - row][col];
        rightPixel.setColor(leftPixel.getColor());
      }
    } 
  }
  
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
  
  /** Mirror just part of a picture of a temple */
  
  public void mirrorDiagonal() {
    Pixel[][] pixels = this.getPixels2D();
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int width = pixels[0].length;
    if(width > pixels.length)
        width = pixels.length;
    for (int col = 0; col < width; col++){
      for (int row = 0; row < width; row++){
        leftPixel = pixels[row][col];
        rightPixel = pixels[col][row];
        rightPixel.setColor(leftPixel.getColor());
      }
    } 
  }
  
  public void mirrorTemple()
  {
    int mirrorPoint = 276;
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int count = 0;
    Pixel[][] pixels = this.getPixels2D();
    
    // loop through the rows
    for (int row = 27; row < 97; row++)
    {
      // loop from 13 to just before the mirror point
      for (int col = 13; col < mirrorPoint; col++)
      {
        count ++;
        leftPixel = pixels[row][col];      
        rightPixel = pixels[row]                       
                         [mirrorPoint - col + mirrorPoint];
        rightPixel.setColor(leftPixel.getColor());
      }
    }
    System.out.println(count);
  }
  /** copy from the passed fromPic to the
    * specified startRow and startCol in the
    * current picture
    * @param fromPic the picture to copy from
    * @param startRow the start row to copy to
    * @param startCol the start col to copy to
    */
  
  public void mirrorArms() {
    
    int mirrorPoint = 197;
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int count = 0;
    Pixel[][] pixels = this.getPixels2D();
    
    // loop through the rows
    for (int col = 119; col < 293; col++)
    {
      // loop from 13 to just before the mirror point
      for (int row = 165; row < mirrorPoint; row++)
      {
        count++;
        leftPixel = pixels[row][col];      
        rightPixel = pixels[mirrorPoint - row + mirrorPoint][col];
        rightPixel.setColor(leftPixel.getColor());
      }
    }
  }
  
  public void mirrorGull(){
    int mirrorPoint = 340;
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int count = 0;
    Pixel[][] pixels = this.getPixels2D();
    
    // loop through the rows
    for (int row = 234; row < 318; row++)
    {
      // loop from 13 to just before the mirror point
      for (int col = 236; col < mirrorPoint; col++)
      {
        count ++;
        leftPixel = pixels[row][col];      
        rightPixel = pixels[row][mirrorPoint - col + mirrorPoint];
        rightPixel.setColor(leftPixel.getColor());
      }
    }
    System.out.println(count);
  }
  
  public void copy(Picture fromPic, 
                 int startRow, int startCol)
  {
    Pixel fromPixel = null;
    Pixel toPixel = null;
    Pixel[][] toPixels = this.getPixels2D();
    Pixel[][] fromPixels = fromPic.getPixels2D();
    for (int fromRow = 0, toRow = startRow; 
         fromRow < fromPixels.length &&
         toRow < toPixels.length; 
         fromRow++, toRow++)
    {
      for (int fromCol = 0, toCol = startCol; 
           fromCol < fromPixels[0].length &&
           toCol < toPixels[0].length;  
           fromCol++, toCol++)
      {
        fromPixel = fromPixels[fromRow][fromCol];
        toPixel = toPixels[toRow][toCol];
        toPixel.setColor(fromPixel.getColor());
      }
    }   
  }
  public void copy(Picture fromPic,int startRow, int startCol, int fromStartRow, int fromStartCol,int fromRowEnd, int fromColEnd)
  {
    Pixel fromPixel = null;
    Pixel toPixel = null;
    Pixel[][] toPixels = this.getPixels2D();
    Pixel[][] fromPixels = fromPic.getPixels2D();
    for (int fromRow = fromStartRow, toRow = startRow;fromRow < fromRowEnd && toRow < toPixels[0].length; fromRow++, toRow++)
    {
      for (int fromCol = fromStartCol, toCol = startCol;fromCol < fromColEnd &&toCol < toPixels[0].length; fromCol++, toCol++)
      {
        fromPixel = fromPixels[fromRow][fromCol];
        toPixel = toPixels[toRow][toCol];
        toPixel.setColor(fromPixel.getColor());
      }
    }   
  }
  

  /** Method to create a collage of several pictures */
  public void createCollage()
  {
    Picture cool1 = new Picture("fox.jpg");
    Picture cool2 = new Picture("fox.jpg");
    Picture cool3 = new Picture("fox.jpg");
    this.copy(cool1,0,320,85,56,185,156);
    cool3.keepOnlyRed();
    this.copy(cool3,150,320,85,56,185,156);
    cool2.keepOnlyBlue();
    this.copy(cool2,300,320,200,200,300,300);
    this.write("640x480.jpg");
  }
  
  
  /** Method to show large changes in color 
    * @param edgeDist the distance for finding edges
    */
  public void edgeDetection(int edgeDist)
  {
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    Pixel topPixel = null;
    Pixel botPixel = null;
    Pixel[][] pixels = this.getPixels2D();
    Color rightColor = null;
    Color botColor = null;
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
    for(int row = 0; row <pixels.length-1;row++){
        for(int col = 0; col < pixels[0].length; col++){
            topPixel = pixels[row][col];
            botPixel = pixels[row+1][col];
            botColor = botPixel.getColor();
            if(topPixel.colorDistance(botColor)>edgeDist)
                topPixel.setColor(Color.BLACK);
            else
                topPixel.setColor(Color.WHITE);
        }
        
    }
  }
  
  
  public void edgeDetection2(int edgeDist){
      Pixel[][] pixels = this.getPixels2D();
      Pixel leftPixel = null;
      Pixel rightPixel = null;
      Pixel topPixel = null;
      Pixel botPixel = null;
      Color rightColor = null;
      Color botColor = null;
      for (int row = 0; row < pixels.length-1; row++){
        for (int col = 0;col < pixels[0].length-1; col++){
          leftPixel = pixels[row][col];
          rightPixel = pixels[row][col+1];
          rightColor = rightPixel.getColor();
          topPixel = pixels[row][col];
          botPixel = pixels[row+1][col];
          botColor = botPixel.getColor();
          rightColor = rightPixel.getColor();
          if (leftPixel.colorDistance(rightColor) >
              edgeDist && topPixel.colorDistance(botColor)>edgeDist)
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
