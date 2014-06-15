/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Modules;

import ClassModules.GovernorCard;
import javax.swing.JOptionPane;

/**
 *
 * @author steve
 */
public class MsgBox {
//private static JOptionPane x = new JOptionPane();

public static void Inform (String s){

  JOptionPane.showMessageDialog(null, s);

}
public static int Inform (String s, int Option) {
	return JOptionPane.showConfirmDialog(null, s, "Message", Option, JOptionPane.INFORMATION_MESSAGE);
}

public static void Inform (Object WhatToShow) {
 if (GovernorCard.isVerbose())
  JOptionPane.showMessageDialog(null, WhatToShow);
}

public static void DoInform (Object WhatToShow) {
  JOptionPane.showMessageDialog(null, WhatToShow);
}

public static boolean Confirm (Object WhatToShow) {
int Choice = JOptionPane.showConfirmDialog(null, WhatToShow);
 return  Choice == JOptionPane.YES_OPTION;
}
}
