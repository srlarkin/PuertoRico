/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ClassModules;

import Modules.SetupAvailableRoles;
import Modules.SetupChoiceSeat;
import Modules.SetupPlayerNames;
import Modules.Utilities;
import java.util.EnumSet;
import static Modules.Text1Idx.*;
import static Modules.PRGameData.*;

/**
 *
 * @author steve
 */
public class MainBoard {
private static SetupChoiceSeat GovernorSeat = SetupChoiceSeat.nullSCS;
private static SetupChoiceSeat PrivilegedSeat = SetupChoiceSeat.nullSCS;

static void HideRole(SetupAvailableRoles sRoleToHide ) {
 int RoleToHide = sRoleToHide.ordinal()	;
 PRPuertoRico.playermat.Image5[RoleToHide].setVisible(false);
 PRPuertoRico.playermat.Text1[RoleToHide + SettlerMoneyIdx.ordinal()].setVisible(false);
};

static void RenewRoles() {
 //Last act of old governor, renew taken roles & add incentive to untaken roles
// for (int i = SetupAvailableRoles.SettlerSAR.ordinal() ; i < iPRGDNumRoles; i++) {
 for ( SetupAvailableRoles Role : EnumSet.range(SetupAvailableRoles.SettlerSAR, PRGDNumRoles)) {
  int i = Role.ordinal();
  if ( PRPuertoRico.playermat.Image5[i].isVisible() )
   PRPuertoRico.playermat.Text1[i + SettlerMoneyIdx.ordinal()].setText(Integer.
	   toString(Integer.valueOf(PRPuertoRico.playermat.Text1[i +
	   SettlerMoneyIdx.ordinal()].getText()) + 1));
  else {
   PRPuertoRico.playermat.Image5[i].setVisible(true);
   PRPuertoRico.playermat.Text1[i + SettlerMoneyIdx.ordinal()].setVisible(true);
  };
 }; 
};

static void NextSeat() {
 SetupChoiceSeat  NSCandidate;
 int iNSCandidate;
 int iGovernorSeat = GovernorSeat.ordinal();

 iNSCandidate = (PRGDCurrentSeat.ordinal() + 1) % (PRGDActualNumPlayers); //Who would be next?
 NSCandidate = SetupChoiceSeat.values()[iNSCandidate]; // TODO:: reverse enum

 if ( NSCandidate == PrivilegedSeat ) { //Has next guy already gone (first)?
  if ( SetupChoiceSeat.values()[(PrivilegedSeat.ordinal() + 1) % PRGDActualNumPlayers] == GovernorSeat ) { //Has everyone gone first?
   iGovernorSeat = (iGovernorSeat + 1) % PRGDActualNumPlayers; //Time for new first guy
   GovernorSeat = SetupChoiceSeat.values()[iGovernorSeat]; 
   PrivilegedSeat = GovernorSeat; //He chooses roles first
   PRGDCurrentSeat = GovernorSeat;
   PRGDCurrentGovernor = PRGDSeatCentric[iGovernorSeat].WhichPlayer; }
  else { //Just need a new role chooser
   PrivilegedSeat = SetupChoiceSeat.values()[(PrivilegedSeat.ordinal() + 1) % PRGDActualNumPlayers];
   PRGDCurrentSeat = PrivilegedSeat;
  };
  PRGDPrivilegedPlayer = PRGDSeatCentric[PrivilegedSeat.ordinal()].WhichPlayer; }
 else //Candidate okay as is
  PRGDCurrentSeat = NSCandidate;
 

 PRGDCurrentPlayer = PRGDSeatCentric[PRGDCurrentSeat.ordinal()].WhichPlayer;

};

static void NextPrivilegedSeat() {
 SetupChoiceSeat  NPSCandidate;

 NPSCandidate = SetupChoiceSeat.values()[(PrivilegedSeat.ordinal() + 1) % PRGDActualNumPlayers]; //Who would be next?

 if ( NPSCandidate == GovernorSeat ) { //Has everyone gone first?
  GovernorSeat = SetupChoiceSeat.values()[(GovernorSeat.ordinal() + 1) % PRGDActualNumPlayers]; //Time for new first guy
  PrivilegedSeat = GovernorSeat; //He chooses roles first
  PRGDCurrentSeat = GovernorSeat;
  PRGDCurrentGovernor = PRGDSeatCentric[GovernorSeat.ordinal()].WhichPlayer;}
 else {//Just need a new role chooser
  PrivilegedSeat = NPSCandidate;
  PRGDCurrentSeat = NPSCandidate;
 };
 PRGDPrivilegedPlayer = PRGDSeatCentric[PrivilegedSeat.ordinal()].WhichPlayer;
 PRGDCurrentPlayer = PRGDSeatCentric[PRGDCurrentSeat.ordinal()].WhichPlayer;
};

static void NextShipper() {

 for (int i = SetupChoiceSeat.FirstSCS.ordinal(); i < PRGDActualNumPlayers; i++){
  if ( PRGDCurrentPlayer == PRGDSeatCentric[i].WhichPlayer ) {
   PRGDCurrentPlayer = PRGDSeatCentric[(i + 1) % PRGDActualNumPlayers].WhichPlayer;
   return;
  };
 }; 

};

public static void FinishPhasing() {
// SetupChoiceSeat  OldSeat = PRGDCurrentSeat;
 SetupPlayerNames  OldPriv = PRGDPrivilegedPlayer;
 SetupPlayerNames  OldGov = PRGDCurrentGovernor;
 SetupPlayerNames  OldPlayer = PRGDCurrentPlayer;
 SetupAvailableRoles  OldRole = PRGDCurrentRole;
 boolean  NextRole = false;

 //Next player
 if ( (PRGDCurrentRole == SetupAvailableRoles.CaptainSAR)  |
    (PRGDCurrentRole == SetupAvailableRoles.GoodsNotShippedSAR) )
  NextShipper();
 else
  NextSeat();
 

 if ( OldPriv == PRGDPrivilegedPlayer )  //Next phasing player, same role

  switch ( PRGDCurrentRole) {
   case SettlerSAR:
    if ( Tile.PlantationsToSettle()) {}
    else {
     NextPrivilegedSeat();
     NextRole = true;
    };
    HideRole(OldRole); //Remove role that has been completed
    break;
   case MayorSAR:
    HideRole(OldRole); //Remove role that has been completed
    break;
   case BuilderSAR:
    HideRole(OldRole); //Remove role that has been completed
    break;
   case CraftsmanSAR:
    NextPrivilegedSeat();
    NextRole = true;
    break;
   case TraderSAR:
    HideRole(OldRole); //Remove role that has been completed
    break;
   case CaptainSAR:
    HideRole(OldRole); //Remove role that has been completed
    break;
   case ProspectorSAR1:
   case ProspectorSAR2:
    NextPrivilegedSeat();
    NextRole = true;
    break;
   case GoodsNotShippedSAR:
    //clean up barrels not stored on compass or in a warehouse
    if ( CargoShip.AllGoodsStoredAfterShipping() ) {
     NextPrivilegedSeat(); // Jump over other players and go to next Role
     CargoShip.FlushBilge();
     NextRole = true;
     OldRole = SetupAvailableRoles.CaptainSAR;
    };
  }

 else { //all players have phased, time to do ending actions.
  NextRole = true;
  switch ( PRGDCurrentRole) {
   case SettlerSAR:
    //When all players done, restock the plantations up for grabs
    if ( Tile.PlantationsToSettle() ) 
     Tile.Renew();
    break;
   case MayorSAR:
    //TODO:: When to say done needs to take into account SJ can't be > 0 if buildings are not full
    //When all players done load up colony ship
    if ( Colonist.LoadColonyShip() ) {
     //End of game?
//    result = MsgBox.MsgBox("Game End has been triggered: Not Enough Colonists to completely fill colony ship", JOptionPane.OK_OPTION)
    Utilities.EventAnnounce ( "Game End has been triggered: Not Enough Colonists to completely fill colony ship");
    PRGDGameExitMet = true;
    };
    VPChit.Tally(OldPlayer);
    break;
   case BuilderSAR:
    VPChit.Tally(OldPlayer);
    PRBuildings.Command2.setVisible(false);
    break;
   case CraftsmanSAR:
//   NextPrivilegedSeat
    break;
   case TraderSAR:
    TradingHouse.Flush();
    break;
   case CaptainSAR:
    break;
   case ProspectorSAR1:
   case ProspectorSAR2:
    break;
   case GoodsNotShippedSAR:
    break;
  };
 };

 if ( NextRole ) {
  HideRole(OldRole); //Remove role that has been completed
  PRGDCurrentRole = SetupAvailableRoles.NullSAR;

  if ( OldGov == PRGDCurrentGovernor ) {}
  else { //New Gov so reveal all role, add incentives
   if ( PRGDGameExitMet ) {
    VPChit.EndTheGame();
    PRGDGameOn = false;
   }
   else {
    //Shift the governor card to the new governor
    PRFArray[OldGov.ordinal()].Image3.setVisible(false);
    PRFArray[PRGDCurrentGovernor.ordinal()].Image3.setVisible(true);
    RenewRoles();
    PRGDCurrentRole = SetupAvailableRoles.NullSAR;
    Utilities.LogIt( PRGDPlayerNameStrings[PRGDCurrentPlayer.ordinal()] + " Becomes Governor");
   };
  };

 };

 };

public static void Initialise() {
// for (int i = SetupAvailableRoles.SettlerSAR.ordinal() ; i < iPRGDNumRoles; i++) {
 for ( SetupAvailableRoles Role : EnumSet.range(SetupAvailableRoles.SettlerSAR, PRGDNumRoles)) {
  int i = Role.ordinal();
  PRPuertoRico.playermat.Image5[i].setVisible(true);
  PRPuertoRico.playermat.Text1[i + SettlerMoneyIdx.ordinal()].setVisible(true);
 };
 // Initialise "tag" field for internal VP count
 for (int i = SetupPlayerNames.HumanPlayerSPN.ordinal(); i < PRGDActualNumPlayers; i++) {
	 PRFArray[i].Text1[VPTotalIdx.ordinal()].setName("0");
 }
 GovernorSeat = PRGDCurrentSeat;
 PrivilegedSeat = PRGDCurrentSeat;
};

}
