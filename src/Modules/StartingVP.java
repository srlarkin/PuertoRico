/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Modules;

/**
 *
 * @author steve
 */
public enum StartingVP {SVP3Player (75), SVP4Player (100), SVP5Player(122);
    private final int VP; // in Doubloons
    StartingVP(int VP) {
        this.VP = VP;}
    public int SVP() {return VP;}
}
