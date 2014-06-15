/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Modules;

/**
 *
 * @author steve
 */
public enum PRGoodsValue {PRRVIndigo (1), PRRVSugar (2), PRRVCorn(0),
  PRRVTobacco(3), PRRVCoffee(4);
    private int GoodsValue; // in Doubloons
    PRGoodsValue(int GoodsValue) { this.GoodsValue = GoodsValue;};
    public int GV() { return GoodsValue;}
}
