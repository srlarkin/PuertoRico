/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Modules;

/**
 *
 * @author steve
 */
public enum PRTileMax {PRTMIndigo (11), PRTMSugar (11), PRTMCorn(10),
  PRTMTobacco(9), PRTMCoffee(9), PRTMQuarry(8);
    private int TileMax; // in Doubloons
    PRTileMax(int TileMax) {
        this.TileMax = TileMax;};
    public int GM () {
     return TileMax;
    }
}
