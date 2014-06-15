/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Modules;

/**
 *
 * @author steve
 */
public enum PRGoodsMax {PRRMIndigo (11), PRRMSugar (11), PRRMCorn(10),
   PRRMTobacco (9), PRRMCoffee(9);
    private final int MaxGood; // in Doubloons
    PRGoodsMax(int MaxGood) {
        this.MaxGood = MaxGood;}

}
