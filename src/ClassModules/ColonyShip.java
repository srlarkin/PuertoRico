/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ClassModules;

import Modules.SetupPlayerNames;
import static Modules.PRGameData.*;
import static Modules.Text1Idx.*;

/**
 *
 * @author steve
 */
public class ColonyShip {

public static void Emmigrate(SetupPlayerNames sPlayer){
 int Player = sPlayer.ordinal();
 int p = sPlayer.ordinal();

//Distribute Colonists from the colony ship to each player's San Juan area

while (Integer.valueOf(PRPuertoRico.playermat.Text1[ColonistsInShipIdx.ordinal()].getText()) > 0) {
 PRFArray[p].Text1[SanJuanIdx.ordinal()].setText(Integer.toString(Integer.
	 valueOf(PRFArray[p].Text1[SanJuanIdx.ordinal()].getText()) + 1));
 PRPuertoRico.playermat.Text1[ColonistsInShipIdx.ordinal()].setText(Integer.
	 toString(Integer.valueOf(PRPuertoRico.playermat.Text1[ColonistsInShipIdx.ordinal()]
	 .getText()) - 1));
 p = (p + 1) % PRGDActualNumPlayers;
};

//Mayor's privilege: Calling player gets 1 extra colonist
if ( Integer.valueOf(PRPuertoRico.playermat.Text1[ColonistsRemainingIdx.ordinal()].getText()) > 0 ) {
 PRFArray[Player].Text1[SanJuanIdx.ordinal()].setText(Integer.toString(Integer.
	 valueOf(PRFArray[Player].Text1[SanJuanIdx.ordinal()].getText())+ 1));
 PRPuertoRico.playermat.Text1[ColonistsRemainingIdx.ordinal()].setText(Integer.
	 toString(Integer.valueOf(PRPuertoRico.playermat.Text1[ColonistsRemainingIdx.
	 ordinal()].getText()) - 1));
};

};


}
