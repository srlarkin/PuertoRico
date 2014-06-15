/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Modules;

import javax.swing.JTextField;

/**
 *
 * @author steve
 */
public class ProductionGridArrays {
public JTextField Text1[]; // Indigo column
public JTextField Text2[]; // Corn column
public JTextField Text3[]; // Sugar column
public JTextField Text4[]; // Tobacco column
public JTextField Text5[]; // Coffee column
public ProductionGridArrays (int NumPlayers) {
    Text1 = new JTextField[NumPlayers];
    Text2 = new JTextField[NumPlayers];
    Text3 = new JTextField[NumPlayers];
    Text4 = new JTextField[NumPlayers];
    Text5 = new JTextField[NumPlayers]; //TODO:: init this after setup selects num players
}
}
