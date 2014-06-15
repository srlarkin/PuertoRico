/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ClassModules;
import Forms.FCopyright;
import Forms.FGameOver;
import Modules.MsgBox;
import Modules.PlayerMatAccess;
import static Modules.Constants.*;
import Modules.SetupPlayerNames;
import Modules.Text1Idx;
import Modules.Utilities;
import java.util.EnumSet;
import javax.swing.JOptionPane;
import static Modules.PRGameData.*;
import static Modules.Text1Idx.*;

/**
 *
 * @author steve
 */
public class VPChit {

public static void Tally(SetupPlayerNames sPlayer ){
 int Player = sPlayer.ordinal();
//Should be called after buildings are bought or twiddled
 int  VPsForBuildings = Building.ToteBuildingVP(sPlayer);
// if ( PRGDCurrentRole = SetupAvailableRoles.BuilderSAR ) {
  PRFArray[Player].Text1[VPTotalIdx.ordinal()].setName( Integer.toString(VPsForBuildings));
  PRFArray[Player].Text1[VPTotalIdx.ordinal()].setText(Integer.toString(Integer.valueOf(
	  PRFArray[Player].Text1[VPChipsIdx.ordinal()].getText()) + 
	  VPsForBuildings));
// };
};

public static void Garner(int NumVP) {
//Called after each successful shipment in the Captains phase
 int playerVP = 0;
 int VPTotal = 0;
 if ( Integer.valueOf(PRPuertoRico.playermat.Text1[VPRemainingIdx.ordinal()].getText()) > 0 ) {
  if ( NumVP >= Integer.valueOf(PRPuertoRico.playermat.Text1[VPRemainingIdx.ordinal()].getText())) {
    //trigger endgame
//   result = MsgBox.MsgBox("Game End has been triggered: the last VP chit has been taken", JOptionPane.OK_OPTION)
   Utilities.EventAnnounce ( "Game End has been triggered: the last VP chit has been taken");
   PRGDGameExitMet = true;
   PRPuertoRico.playermat.Text1[VPRemainingIdx.ordinal()].setText("0"); }
  else
   PRPuertoRico.playermat.Text1[VPRemainingIdx.ordinal()].setText(Integer.toString(Integer.valueOf(PRPuertoRico.playermat.Text1[VPRemainingIdx.ordinal()].getText()) - NumVP));
//  };
 };
 playerVP  = Integer.valueOf( PRFArray[PRGDCurrentPlayer.ordinal()].Text1[VPChipsIdx.ordinal()].getText()) ;
 PRFArray[PRGDCurrentPlayer.ordinal()].Text1[VPChipsIdx.ordinal()].setText(Integer.toString(playerVP + NumVP));
 //Bug:: Need to accomodate Custom House after each shipment.
 PRFArray[PRGDCurrentPlayer.ordinal()].Text1[VPTotalIdx.ordinal()].setName(Integer.toString(Integer.valueOf(
   PRFArray[PRGDCurrentPlayer.ordinal()].Text1[VPTotalIdx.ordinal()].getName())
   + playerVP + NumVP));
};

public static void EndTheGame() {
 int  MaxVP[] = new int[PRGDActualNumPlayers] ; 
 int  TieBreaker[] = new int [PRGDActualNumPlayers];
 int  Vidx[] = new int [PRGDActualNumPlayers];
 int  Tidx[] = new int [PRGDActualNumPlayers];
 boolean[] inRunning = new boolean [PRGDActualNumPlayers];
 int HiCount = 0;
 int WinningCandidate = 0;

 //SetupPlayerNames  i;
// int  iFrame;

 Utilities.LogIt( "Game Over");

 for (int i = PRGDActualNumPlayers; i < MaxPlayers; i++) {
  PRGameOver.Frame1[i].setVisible(false);
 }

 for (int i = 0; i < PRGDActualNumPlayers; i++){
  SetupPlayerNames si = SetupPlayerNames.values()[i];
  int chips = 0;
  int buildings = 0;
//  int place = 0;

  TieBreaker[i] = TieBreakerTally(i);
//  iFrame = i * NumEOGStats;
  PRGameOver.PlayerName[i].setText (PRGDPlayerNameStrings[i]);
  PRGameOver.TieBreaker[i].setText(Integer.toString(TieBreaker[i]));
  buildings = Building.ToteBuildingVP(si);
  PRGameOver.Buildings[i].setText(Integer.toString(buildings));
  chips = Integer.valueOf(PRFArray[i].Text1[VPChipsIdx.ordinal()].getText());
  PRGameOver.Chips[i].setText(PRFArray[i].Text1[VPChipsIdx.ordinal()].getText());
  PRGameOver.Total[i].setText(Integer.toString(buildings + chips));
  MaxVP[i] = buildings + chips;
 }; 

 Vidx = Utilities.SortAscending(PRGDActualNumPlayers, MaxVP);

 for (int i = 0; i < PRGDActualNumPlayers; i++){
  if  (MaxVP[i] == MaxVP[Vidx[PRGDActualNumPlayers - 1]]) {
   inRunning[i] = true;
   WinningCandidate = i;
   HiCount++;
  }
  else
   inRunning[i] = false;
 }

 if (HiCount > 1) {
  Tidx = Utilities.SortAscending(PRGDActualNumPlayers, TieBreaker);
  for (int i = 0; i < PRGDActualNumPlayers; i++){
   if ( inRunning[i] & (TieBreaker[i] == TieBreaker[Tidx[PRGDActualNumPlayers - 1]]) )
    WinningCandidate = i;
   else
    inRunning[i] = false;
  };
 };

 for (int i = 0; i < PRGDActualNumPlayers; i++){
  if (i == WinningCandidate) {
	      PRGameOver.setWinnerLabel(SetupPlayerNames.values()[i]);
    Utilities.LogIt( PRGDPlayerNameStrings[i] + " Wins! Stats: VPt: " +
	   Integer.toString(MaxVP[i]) + " VPc: " + PRGameOver.Chips[i].getText()
	   + " VPb: " + PRGameOver.Buildings[i].getText()+ " TB: "
	   + Integer.toString(TieBreaker[i]));
  }
  else
   Utilities.LogIt( PRGDPlayerNameStrings[i] + "Stats: VPt: " + Integer.toString(MaxVP[i])
	   + " VPc: " + PRGameOver.Chips[i].getText()
	   + " VPb: " + PRGameOver.Buildings[i].getText()
	   + " TB: " + Integer.toString(TieBreaker[i]));
 };
 PRGDGameOn = false;
// PRGameOver.setVisible(true);
MsgBox.Inform(PRGameOver);
PRPuertoRico.EnabledMNew(true);
};

public static boolean ExitAtEOG () {
 if ( MsgBox.Inform("Are You Sure You Want to Quit?", JOptionPane.OK_CANCEL_OPTION)
     == JOptionPane.OK_OPTION ) {
  Utilities.LogIt( "Game Over");
  System.exit(0);
  }

 return false;
 
}

public static boolean NewAtEOG () {
 if ( MsgBox.Inform("Are You Sure You Want to Play Again?", JOptionPane.OK_CANCEL_OPTION)
    == JOptionPane.OK_OPTION ) {
  Utilities.LogIt( "New Game Starting...");
//  this.setVisible(false);

 PRPuertoRico.dispose();
 for (int i = 1; i < PRGDActualNumPlayers; i++) {
  PRFArray[i].form.dispose();
 }

// PRFArray = (PlayerMatAccess[]) Utilities.resizeArray(PRFArray, 1);
// PRProductionGrid.dispose();
 PRBuildings.dispose();
 PRBuildingInfo.dispose();
 PRInformation.dispose();
 PRGoneGoods.dispose();
 PRCopyright = new FCopyright(PRPuertoRico ,false);
 PRCopyright.setVisible(true);
//  PRCopyright.set
//  this.dispose();
  return true;
 };
 return false;
}

static int TieBreakerTally(int Player ){

 int TieBreakerTally = Integer.valueOf(PRFArray[Player].Text1[DoubloonsIdx.ordinal()].getText());

 for (Text1Idx iR : EnumSet.range( IndigoIdx , CoffeeIdx)){
  TieBreakerTally = TieBreakerTally + Integer.valueOf(PRFArray[Player].Text1[iR.ordinal()].getText());
 };
 return TieBreakerTally;
};


}
