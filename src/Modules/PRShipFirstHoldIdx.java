/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Modules;

/**
 *
 * @author steve
 */
public enum PRShipFirstHoldIdx {ship4First (0), ship5First (4), ship6First(9),
  ship7First(15), ship8First(22), noShipFirst(-2);
    private final int FirstHold; // in Doubloons
    PRShipFirstHoldIdx(int FirstHold) {
        this.FirstHold = FirstHold;};
    public int gi (){return this.FirstHold;}

}
