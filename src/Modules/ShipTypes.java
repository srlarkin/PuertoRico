/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Modules;

/**
 *
 * @author steve
 */
public enum ShipTypes {Ship4(0), Ship5(4), Ship6(9), Ship7(15), Ship8(22), PlayerWharf(0),
 NoShip(0);
private int Image6Idx; // Index into ship hold array
private ShipTypes(int Image6Idx){ this.Image6Idx = Image6Idx; };
public int iidx(){ return this.Image6Idx; }
}
