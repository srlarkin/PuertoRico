/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Modules;

/**
 *
 * @author steve
 */
public enum PRShipLastHoldIdx {ship4Last(3), ship5Last(8), ship6Last(14),
 ship7Last(21), ship8Last(29), noShipLast(-1);
 private final int LastHold; // in Doubloons
    PRShipLastHoldIdx(int LastHold) {
        this.LastHold = LastHold;};
public int gi (){return this.LastHold;}

}
