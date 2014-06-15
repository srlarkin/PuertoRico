/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Modules;

/**
 *
 * @author steve
 */
public enum ShipsLeft {SmallShipLeft(0), MediumShipLeft(83), LargeShipLeft(175), NoShipLeft(0);
    private int SLeft; // in Doubloons
    private ShipsLeft(int whatsLeft) { this.SLeft = whatsLeft;};
    public int getX() {return SLeft;};
}
