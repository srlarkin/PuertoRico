/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Modules;

/**
 *
 * @author steve
 */
public enum StartingColonistsRemaining {SCR3Player (55), SCR4Player (75), SCR5Player(95);
    private int Colonists; // in Doubloons
    StartingColonistsRemaining (int Colonists) {
        this.Colonists = Colonists;}
    public int SCRIdx() {return Colonists;}
}
