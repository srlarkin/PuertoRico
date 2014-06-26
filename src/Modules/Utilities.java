/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Modules;

import ClassModules.Building;
import ClassModules.RoleCard;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

/**
 *
 * @author steve
 */
public class Utilities {
/* Utilities Module */

//static Point GetCursorPos () {return null;}; // TODO::

//static long ScreenToClient (long hwnd, Point lpPoint) {return 0;}; // TODO::

public static int RandomPick(int setup_low_rand, int setup_high_rand) {
 /* Randomize */
 Random rnd = new Random(); // TODO:: Use date as seed?
 
 float low = (float) setup_low_rand;
 float high = (float) setup_high_rand;

 return (int) ((high - low + 1) * rnd.nextFloat() + low);
};

//public static Point MyMouseCoords() {
//  Point TypPt = new Point(0,0);
//
// TypPt = GetCursorPos();
// ScreenToClient (PRGameData.PRFArray[PRGameData.PRGDCurrentPlayer.ordinal()].hashCode(), TypPt); //TODO:: Hashcode???!!!
// /* convert pixels to twips */
// TypPt.x = TypPt.x * 15;
// TypPt.y = TypPt.y * 15;
// return TypPt;
//};

public static int[] SortAscending(int ArraySize, int ArraytoSort[]) {
// Returns an array of indices into ArraytoSort. The first index points to
// the highest value in ArraytoSort, the second index to the second highest &c
int sortascending[] = new int[ArraySize];
int HolderIdx;
int iA;

for (int i = 0; i < ArraySize; i++) 
 sortascending[i] = i;

iA = 0;

while (iA < (ArraySize - 1)) {
 if (ArraytoSort[sortascending[iA]] > ArraytoSort[sortascending[iA + 1]])
 {
  HolderIdx = sortascending[iA];
  sortascending[iA] = sortascending[iA + 1];
  sortascending[iA + 1] = HolderIdx;

  if (iA > 0) {
   iA = iA - 1;
  }
  else
   iA = iA + 1;
 }
 else
  iA = iA + 1;
 
};

return sortascending;

};

public static void EventAnnounce(String LogString) {
 RoleCard.InfoCard(LogString);
 LogIt(LogString);
};

public static void BldgAnnounce(BuildingIdx WhichBldg, String LogString) {
 Building.InfoCard(WhichBldg, true, LogString);
 LogIt(LogString);
};

public static void LogIt(String LogString) {
 String DATE_FORMAT_NOW = "yyy-dd-MM HH:mm:ss";
 Calendar cal = Calendar.getInstance();
 SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);

 String outstring;

 if (PRGameData.PRPuertoRico.getTitle().equals(""))
 {
  outstring = sdf.format(cal.getTime()) + LogString;
 }
 else
  outstring = sdf.format(cal.getTime()) + " " + PRGameData.PRPuertoRico.getTitle() + " " + LogString;

 boolean exists = (new File("J:\\Puerto Rico\\PR\\PuertoRico\\Logs\\PRLogs.txt")).exists();

 if (exists)
 {
  try {
        BufferedWriter out = new BufferedWriter(new FileWriter("J:\\Puerto Rico\\PR\\PuertoRico\\Logs\\PRLogs.txt", true));
        out.write(outstring);
	out.newLine();
        out.close();
    } catch (IOException e) {
    }
 }
 else
 {
  try {
      File file = new File("J:\\Puerto Rico\\PR\\PuertoRico\\Logs\\PRLogs.txt");
      // Create the log file since it does not exist
      boolean success = file.createNewFile();
  } catch (IOException e) {}
  }
   try {
        BufferedWriter out = new BufferedWriter(new FileWriter("J:\\Puerto Rico\\PR\\PuertoRico\\Logs\\PRLogs.txt"));
        out.write(outstring);
        out.close();
    } catch (IOException e) {}
 

};

public static Object resizeArray (Object oldArray, int newSize) {
   int oldSize = 0;
   Class<?> elementType = oldArray.getClass().getComponentType();
   Object newArray = java.lang.reflect.Array.newInstance(
         elementType,newSize);
   int preserveLength = 0;

   if (oldArray == null)
     return newArray;

   oldSize = java.lang.reflect.Array.getLength(oldArray);
   preserveLength = Math.min(oldSize,newSize);

   if (preserveLength > 0)
      System.arraycopy (oldArray,0,newArray,0,preserveLength);
   return newArray; }

public static SetupPlayerNames GetPlayerFromJLabel ( java.awt.event.MouseEvent evt) {
     javax.swing.JLabel jl = (javax.swing.JLabel) evt.getSource(); // TODO:: merge GetPlayer & GetPlayer2
     for (int i = 1; i < PRGameData.PRGDActualNumPlayers; i++){ //don't use form for entry 0; it's null
      if (PRGameData.PRFArray[i].form == null)
	 return SetupPlayerNames.HumanPlayerSPN;
      else
       if (PRGameData.PRFArray[i].form.isAncestorOf(jl))
        return SetupPlayerNames.values()[PRGameData.PRFArray[i].iPlayer];
     };
     // if you didn't find it in the array then it's the human player main board
     return SetupPlayerNames.HumanPlayerSPN;
}
public static SetupPlayerNames GetPlayerFromJTextField ( java.awt.event.MouseEvent evt) {
     javax.swing.JTextField jtf = (javax.swing.JTextField) evt.getSource(); // TODO:: merge GetPlayer & GetPlayer2
     for (int i = 1; i < PRGameData.PRGDActualNumPlayers; i++){ //don't use form for entry 0; it's null
      if (PRGameData.PRFArray[i].form == null)
	 return SetupPlayerNames.HumanPlayerSPN;
      else
       if (PRGameData.PRFArray[i].form.isAncestorOf(jtf))
        return SetupPlayerNames.values()[PRGameData.PRFArray[i].iPlayer];
     };
     // if you didn't find it in the array then it's the human player main board
     return SetupPlayerNames.HumanPlayerSPN;
}

public static int getControlIdx(java.awt.event.MouseEvent evt) {
return SwingUtilities.getAccessibleIndexInParent((java.awt.Component)evt.getSource());
};

/*
public  void scaleImage(javax.swing.JLabel whereDrawn, Image source, int width, int height)
     throws IOException {
   BufferedImage bsrc = null;
   bsrc = new BufferedImage(source.getWidth(null), source.getHeight(null),
	   BufferedImage.TYPE_INT_RGB);
   // Copy image to buffered image
   Graphics gsrc = bsrc.createGraphics();
   source.getS
   // Paint the image onto the buffered image
   gsrc.drawImage(source, 0, 0, null);
   gsrc.dispose();

   BufferedImage bdest =
      new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
   Graphics2D gdst = bdest.createGraphics();
   AffineTransform at =
      AffineTransform.getScaleInstance((double)width/bsrc.getWidth(),
          (double)height/bsrc.getHeight());
   gdst.drawRenderedImage(bsrc,at);
//   ImageIO.write(bdest,"JPG",new File(dest));

}
*/

};