/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Modules;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author steve
 */
public class FetchResource {

public javax.swing.ImageIcon FR(String nResource) {
  return new javax.swing.ImageIcon(getClass().getResource( "/resources/" + nResource + ".jpg"));
};
public Image FI(String nIcon) {
 Image img = new ImageIcon(getClass().getResource( "/resources/" + nIcon + ".jpg")).getImage();
  return img;

}

}
