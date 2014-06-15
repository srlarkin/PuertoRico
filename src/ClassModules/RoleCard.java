/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ClassModules;

import Modules.ImageIdx;
import Modules.MsgBox;
import Modules.PlayerAlertCode;
import Modules.SetupAvailableRoles;
import Modules.SetupPlayerNames;
import Modules.ShipTypes;
import Modules.Utilities;
import java.awt.Cursor;
import java.awt.Dialog.ModalityType;
import java.util.EnumSet;
import resources.LoadResString;
import static Modules.Constants.*;
import static Modules.PRGameData.*;
import static Modules.Text1Idx.*;

/**
 *
 * @author steve
 */
public class RoleCard {

private static SetupAvailableRoles PRRCSelectedRole = SetupAvailableRoles.NullSAR;

public static void PickARole() {
//Role Selection
 SetupAvailableRoles CandidateRole = PRRCSelectedRole;
 boolean AskDone = false;
 boolean TellDone = false;
 String PlayerMatButtonString = "Done";
 boolean bResult = false;

if ( PRGDGameOn ) {} //Don't allow role selection if no game has been started!
else
 return;


if ( PRGDCurrentRole == SetupAvailableRoles.NullSAR ) {}
else {
 MsgBox.Inform("Role Already Selected");
 return;
};

//if ( Inform.Inform(LoadResString.ResSs[(CandidateRole.ordinal() + PRStringTableBase] + ": are you sure?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION ) {
// PRFArray[PRGDCurrentPlayer.ordinal()].Zorder; //Relevent player form to the front
 PRGDCurrentRole = CandidateRole;
 Utilities.LogIt( " Chosen");
 //Transfer cash from role
 RoleCard.TransferCash(PRGDCurrentPlayer, CandidateRole);

 //Reset cursor back to No Drop
// for (int i = SetupAvailableRoles.SettlerSAR.ordinal(); i < iPRGDNumRoles; i++){
 for ( SetupAvailableRoles Role : EnumSet.range(SetupAvailableRoles.SettlerSAR, PRGDNumRoles)) {
  int i = Role.ordinal();
  PRPuertoRico.playermat.Image5[i].setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // No Drop
 }; 

  switch ( PRGDCurrentRole) {

   case SettlerSAR:
    if ( PRFArray[PRGDCurrentPlayer.ordinal()].Image2[PRRMaxPlantations - 1].isVisible() ) {
     Utilities.EventAnnounce ( "Already Has 12 Plantations Placed");
     TellDone = true; }
   else { //not full
    Tile.DealWithHacienda(PRGDCurrentPlayer);
    if ( Tile.PlantationsToSettle() )
      //Ask Player to select a plantation
     bResult = PRPuertoRico.PlayerAlert(PRGDCurrentPlayer, PlayerAlertCode.ChooseATilePAC, " Select a new Plantation");
    else
     TellDone = true;
     };
     break;

   case MayorSAR:
    //Divvy up Colonist in ship
    //Put new Colonist in each player's San Juan
    ColonyShip.Emmigrate (PRGDCurrentPlayer);
    //Ask Player to allocate colonists
    bResult = PRPuertoRico.PlayerAlert(PRGDCurrentPlayer, PlayerAlertCode.ManageColonistsPAC, " Manage Colonists");
    if ((Integer.valueOf(PRFArray[PRGDCurrentPlayer.ordinal()].Text1[SanJuanIdx.
	    ordinal()].getText()) > 0)
           & (Building.VacancyCount(PRGDCurrentPlayer) > 0) ) {}
    else
     AskDone = true;
    break;

   case BuilderSAR:
    PRBuildings.Command2.setVisible(true);
    PRBuildings.setVisible(true);
    bResult = PRPuertoRico.PlayerAlert(PRGDCurrentPlayer, PlayerAlertCode.ChooseABuildingPAC, " Go ahead & build");
    break;

   case CraftsmanSAR:
    Barrel.Produce();
    break;

   case TraderSAR:
    if (TradingHouse.CanITrade(PRGDCurrentPlayer))
     bResult = PRPuertoRico.PlayerAlert(PRGDCurrentPlayer, PlayerAlertCode.PickATradePAC, " Choose a Trade");
    else {
//     Inform.Inform(PRGDPlayerNameStrings[PRGDCurrentPlayer.ordinal()] + ", you have Nothing to Trade!");
     Utilities.EventAnnounce (PRGDPlayerNameStrings[PRGDCurrentPlayer.ordinal()] + ", you cannot make a trade!");
     PRPuertoRico.GoGoGo();
    };
     AskDone = false;
     break;

   case CaptainSAR:
    if (CargoShip.CanIShip(PRGDCurrentPlayer, PRGDCurrentRole) ) {
//    if ( CargoShip.CanIShip(PRGDCurrentPlayer, PRGDCurrentRole) )
      if (CargoShip.CheckForWharf())
       PRPuertoRico.playermat.Picture1[ShipTypes.PlayerWharf.ordinal()].setVisible(true);
      else
       PRPuertoRico.playermat.Picture1[ShipTypes.PlayerWharf.ordinal()].setVisible(false);
     bResult = PRPuertoRico.PlayerAlert(PRGDCurrentPlayer, PlayerAlertCode.PickAGoodToShipPAC, " Choose a Good to Ship");
    }
    else {
     Utilities.EventAnnounce ( PRGDPlayerNameStrings[PRGDCurrentPlayer.ordinal()] + " Cannot Ship a Good");
     TellDone = true; };
    break;

   case ProspectorSAR1:
   case ProspectorSAR2:
    PRFArray[PRGDCurrentPlayer.ordinal()].Text1[DoubloonsIdx.ordinal()].setText(
    Integer.toString(Integer.valueOf(PRFArray[PRGDCurrentPlayer.ordinal()].Text1[DoubloonsIdx.ordinal()].getText()) + 1));
    TellDone = true;
       // case SetupAvailableRoles.ProspectorSAR2:
       //  AskDone = true;
  };

 if ( AskDone & (!PRGDAutoPlayer[PRGDCurrentPlayer.ordinal()]) ) {
  PRFArray[PRGDCurrentPlayer.ordinal()].Command1.setText( PlayerMatButtonString);
  PRFArray[PRGDCurrentPlayer.ordinal()].Command1.setVisible(true);
 };

 if ( TellDone & (!PRGDAutoPlayer[PRGDCurrentPlayer.ordinal()]) ) {
  PRPuertoRico.GoGoGo();
 };

};

public static void TransferCash(SetupPlayerNames Player, SetupAvailableRoles Role ) {
 int  TotalCash = Integer.valueOf(PRFArray[Player.ordinal()].Text1[DoubloonsIdx.
	 ordinal()].getText());
 PRFArray[Player.ordinal()].Text1[DoubloonsIdx.ordinal()].setText(Integer.
	 toString(TotalCash + Integer.valueOf(PRPuertoRico.playermat.
	 Text1[Role.ordinal() + SettlerMoneyIdx.ordinal()].getText())));
 PRPuertoRico.playermat.Text1[Role.ordinal() + SettlerMoneyIdx.ordinal()].setText("0");
};

static ImageIdx RoleCardIdx(SetupAvailableRoles WhichRole ){

 switch ( WhichRole ) {
  case SettlerSAR:
   return ImageIdx.SettlerCard;
  case MayorSAR:
   return ImageIdx.MayorCard;
  case BuilderSAR:
   return ImageIdx.BuilderCard;
  case CraftsmanSAR:
   return ImageIdx.CraftsmanCard;
  case TraderSAR:
   return ImageIdx.TraderCard;
  case CaptainSAR:
  case GoodsNotShippedSAR:
   return ImageIdx.CaptainCard;
  case ProspectorSAR1:
  case ProspectorSAR2:
   return ImageIdx.ProspectorCard;
  case NullSAR:
   return ImageIdx.GovernorCard;
 };
  return ImageIdx.Blank1;
};

public static void InfoCard(String LogString ) {
 ImageIdx  RoleCard = RoleCardIdx(PRGDCurrentRole);
// PRInformation.CommandButton1.setVisible(true);
// PRInformation.CommandButton2.setVisible(false);
// PRInformation.CommandButton3.setVisible(false);
 PRInformation.Image1.setIcon(PRFetchResource.FR(RoleCard.SImgIndex()));
 PRInformation.Label1.setText(LogString);
// PRRoleInformation.Zorder;
// PRRoleInformation.setModalityType(ModalityType.APPLICATION_MODAL);
// PRInformation.setVisible(true);
 MsgBox.Inform(PRInformation.Panel);
};

public static void ConfirmCard(SetupAvailableRoles CandidateRole ) {
 ImageIdx  RoleCard = RoleCardIdx(CandidateRole);
// boolean result;
 PRRCSelectedRole = CandidateRole;
 PRInformation.Image1.setIcon(PRFetchResource.FR((RoleCard.SImgIndex())));
 PRInformation.Label1.setText( "You Chose the " + LoadResString.ResSs[CandidateRole.ordinal()] + "-" +
                               LoadResString.ResSs[CandidateRole.ordinal() + RoleConfBase] + " Is this the role you want to choose?");
 PRInformation.Image1.setSize(InfoCardWidth, InfoCardHeight);
// PRInformation.CommandButton1.setVisible(false);
// PRInformation.CommandButton2.setVisible(true);
// PRInformation.CommandButton3.setVisible(true);

// PRRoleInformation.Zorder
// PRRoleInformation.setModalityType(ModalityType.APPLICATION_MODAL);
// PRInformation.setVisible(true);
 if ( MsgBox.Confirm(PRInformation.Panel))
  PickARole();
};

}
