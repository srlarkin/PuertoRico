/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Modules;

import Forms.FAIPlayer1;
import javax.swing.JTextField;
import static Modules.Constants.*;

/**
 *
 * @author steve
 */
public class PlayerMatAccess {
 public FAIPlayer1 form;
 public int iPlayer;
 public JTextField Text1[];
// public javax.swing.JLabel Image1; // This is the main background map
 public javax.swing.JLabel Image2[] = new javax.swing.JLabel[MaxCitySiteSlots]; // These are the 12 plantation Tiles
 public javax.swing.JLabel Image3; // This is the Governor Card
// public javax.swing.JLabel Image4; // This is the Colony Ship
 public javax.swing.JLabel Image5[] = new javax.swing.JLabel[8]; // These are the 8 potential roles
 public javax.swing.JLabel Image6[] = new javax.swing.JLabel[30]; // These are the 30 goods in ships
 public javax.swing.JLabel Image7[] = new javax.swing.JLabel[PRCMaxTradeGoods]; // These are the 4 TradeHouse goods
 public javax.swing.JLabel Image8[] = new javax.swing.JLabel[7]; // These are the 7 plantation tiles to choose
 public javax.swing.JLayeredPane Image10lp = new javax.swing.JLayeredPane();
 public javax.swing.JLabel Image10[] = new javax.swing.JLabel[1]; // These are for buildings and are created dynamically
 public javax.swing.JLayeredPane Picture1[] = new javax.swing.JLayeredPane[6]; // These are the 5 Cargo ships + Wharf
 public javax.swing.JButton Command1;
 public javax.swing.JButton Command2;


 public PlayerMatAccess(int text1Size){
  Text1 = new JTextField [text1Size]; };
}
