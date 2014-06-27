/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Modules;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;

/**
 *
 * @author steve
 */
public class FetchResource {

public javax.swing.ImageIcon FR(String nResource) {
  return new javax.swing.ImageIcon(getClass().getResource( "/resources/" + nResource + ".jpg"));
};
public List<Image> FI(String[] nIcon) {
    List<Image> icons = new ArrayList<>();    
    for(String path : nIcon) {
        Image img = new ImageIcon(getClass().getResource( "/resources/icons/" + path + ".png")).getImage();
        icons.add(img);
    }
    return icons;

}

}
