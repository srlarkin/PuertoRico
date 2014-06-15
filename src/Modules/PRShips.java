/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Modules;

/**
 *
 * @author steve
 */
public class PRShips {
 public ShipTypes Ship;
 public ShipsLeft Left;
 public PRShipFirstHoldIdx FirstHold;
 public PRShipLastHoldIdx LastHold;
 public ResourceIdx Good;
 public int Capacity;
 public int NumHoldsFull;
 public String Name;
 public PRShips(){
  this.Ship = ShipTypes.NoShip;
  this.Left = ShipsLeft.NoShipLeft;
  this.FirstHold = PRShipFirstHoldIdx.noShipFirst;
  this.LastHold = PRShipLastHoldIdx.noShipLast;
  this.Good = ResourceIdx.NullRIdx;
  this.Capacity = 0;
  this.NumHoldsFull = 0;
  this.Name = "";
 }
};
