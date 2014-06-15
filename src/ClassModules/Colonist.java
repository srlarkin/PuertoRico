/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ClassModules;
import static Modules.Text1Idx.*;
import static Modules.PRGameData.*;

/**
 *
 * @author steve
 */
public class Colonist {

public static boolean LoadColonyShip(){

 int FreeBuildingSlots = Building.Count();
 int LoadShipWith;
 boolean GameOver = false;
 int ColonistsRemaining = ColonistsRemainingIdx.ordinal();

 //Count up number of empty building spots

 //Are there less free building spots than number of players?
 if ( FreeBuildingSlots > PRGDActualNumPlayers ) 
  LoadShipWith = FreeBuildingSlots;
 else
  LoadShipWith = PRGDActualNumPlayers;
 

 //Are there enough colonists left?
 if ( LoadShipWith > Integer.valueOf(PRPuertoRico.playermat.Text1[ColonistsRemaining].getText())) {
  GameOver = true;
  LoadShipWith = Integer.valueOf(PRPuertoRico.playermat.Text1[ColonistsRemaining].getText());
 };

 PRPuertoRico.playermat.Text1[ColonistsRemaining].setText(Integer.toString(Integer.
	 valueOf(PRPuertoRico.playermat.Text1[ColonistsRemaining].getText()) - LoadShipWith));
 PRPuertoRico.playermat.Text1[ColonistsInShipIdx.ordinal()].setText(Integer.toString(
	 Integer.valueOf(PRPuertoRico.playermat.Text1[ColonistsInShipIdx.ordinal()].getText()) + LoadShipWith));
 return GameOver;

};


}
