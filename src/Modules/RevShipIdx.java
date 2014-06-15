/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Modules;

import static Modules.ShipIdx.*;

/**
 *
 * @author steve
 */
public enum RevShipIdx {
WharfSlotR(WharfSlot),
LargeShipR(LargeShip),
MedShipR(MedShip),
SmallShipR(SmallShip),
 NotAShipSlotR(NotAShipSlot);
private ShipIdx rsi;

private RevShipIdx (ShipIdx rsi) {this.rsi = rsi;};

public ShipIdx UnRev () { return rsi;}
}
