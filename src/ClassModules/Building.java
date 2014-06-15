/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ClassModules;

import Forms.FBuildingInfo;
import Modules.AIGameData;
import Modules.BldgType;
import Modules.BuildingBluePrint;
import Modules.BuildingIdx;
import Modules.BuildingVPType;
import Modules.ChooseABuildingData;
import Modules.Constants;
import Modules.CitySiteSlot;
import Modules.FBldgInfoIdx1;
import Modules.ImageIdx;
import static Modules.PRGameData.*;
import Modules.SetupPlayerNames;
import Modules.MsgBox;
import Modules.ResourceIdx;
import Modules.Text1Idx;
import Modules.Utilities;
import java.awt.Dialog.ModalityType;
import java.awt.Point;
import java.util.EnumSet;
import javax.swing.SwingUtilities;
import resources.LoadResString;

/**
 *
 * @author steve
 */
public class Building {
private static BuildingBluePrint BuildingInventory[];
private static CitySiteSlot BuildingInCitySlot[][];
private static BuildingVPType BuildingVP[];

public static void Twiddle(int FormSlot, Point mouselocn ) {
// Form Slot is the index given to the JLabel upon addition to the JLayeredPane
// BuildingSlot is the index into Image10
// CitySlot is one of 12 slots used to index BuildingCitySlot private class data
 int BuildingSlot = FormSlotToBuildingSlot(FormSlot);
 int CitySlot = BuildingSlotToCitySlot(BuildingSlot);
 int KindOfBuilding = 0;
 int SlotPicked = 0;
 Point ClickWhere = new Point(0,0);
 int ImageIncr = 0;
 int NumColonistsInSlot = 0;

 //find mouse position
 ClickWhere = mouselocn;

 KindOfBuilding = BuildingInCitySlot[PRGDCurrentPlayer.ordinal()][CitySlot].Building.ordinal();

 //account for large buildings
 if ( BuildingInventory[KindOfBuilding].BType == BldgType.LargeVioletBT )
  ClickWhere.y = ClickWhere.y - Constants.BldgSingleHeight;
 
 //validate 'top' and 'left'
 if ( (ClickWhere.y < Constants.PRCBldgMaxTop) | (ClickWhere.y > Constants.PRCBldgMinTop)
    | (ClickWhere.x < Constants.PRCBldgMinL1) | (ClickWhere.x > Constants.PRCBldgMaxL3) ) {
 //remember lower on screen = higher coord number
  return;
 };

 //slot 'left' into which colonist clicked
 if ( ClickWhere.x <= Constants.PRCBldgMaxL1 )
  SlotPicked = 1;
 else
  if ( (ClickWhere.x > Constants.PRCBldgMinL2) & (  ClickWhere.x < Constants.PRCBldgMaxL2 ) )
   SlotPicked = 2;
  else
   if (ClickWhere.x > Constants.PRCBldgMinL3)
    SlotPicked = 3;
   else
    return;

 //no post where clicked
 if ( SlotPicked > BuildingInventory[KindOfBuilding].NumPosts ) {
  return;
 };

 ImageIncr = 0;
 NumColonistsInSlot = BuildingInCitySlot[PRGDCurrentPlayer.ordinal()][CitySlot].NumColonists;
 //for single person buildings twiddle a colonist between it & San Juan
 //for multi person buildings confirm
 //if number clicked is less than number in building: ignore
 if (  SlotPicked < NumColonistsInSlot ) {
  return;
 };
 //if ( ( number clicked is more than one greater: ignore
 if (  SlotPicked > NumColonistsInSlot + 1 ) {
  return;
 };

 //if ( ( number clicked is equal to number in building: twiddle rightmost to San Juan
 if ( SlotPicked == NumColonistsInSlot ) {
  ImageIncr = -1;
 };
 //if number clicked is one greater than number in building: twiddle from San Juan to picked slot
 if (  SlotPicked == NumColonistsInSlot + 1 ) 
  if (  Integer.valueOf(PRFArray[PRGDCurrentPlayer.ordinal()].
  Text1[Text1Idx.SanJuanIdx.ordinal()].getText()) > 0 )
   ImageIncr = 1;
  else //San Juan is empty
   return;
  
 ReStaff( PRGDCurrentPlayer, BuildingSlot, ImageIncr);

 if (  ImageIncr != 0 ) 
    VPChit.Tally(PRGDCurrentPlayer);
};

static ChooseABuildingData VettBuilding(SetupPlayerNames sPlayer , BuildingIdx bWhichBuilding) {
 ChooseABuildingData vb = new ChooseABuildingData();

 int Player = sPlayer.ordinal();
 int WhichBuilding = bWhichBuilding.ordinal();

 vb.CandidateBuilding = bWhichBuilding;
 vb.EffectiveCost = 0;
 vb.CandidateCitySlot = Constants.MaxCitySiteSlots;

 //Find next free slot for a building
 if (  BuildingInventory[WhichBuilding].BCount > 0 ) {}
 else {
  if  ( PRGDAutoPlayer[Player] ) {}
  else {
   MsgBox.Inform ("No buildings of that type are left");
  }
  return vb; //no buildings of that type are left
 };

 //check money too
 //check builders privilege
 //check number of quarries present & applicable
 vb.EffectiveCost = BuildingInventory[WhichBuilding].Cost;
 if (  sPlayer == PRGDPrivilegedPlayer ) {
  vb.EffectiveCost = vb.EffectiveCost - 1;
 };

 vb.EffectiveCost = vb.EffectiveCost - QuarryFactor(sPlayer, bWhichBuilding);

 if (vb.EffectiveCost > Integer.valueOf(PRFArray[Player].Text1[Text1Idx.DoubloonsIdx
         .ordinal()].getText()) ) {
  if (  PRGDAutoPlayer[Player] ) {}
  else {
   MsgBox.Inform ("Not enough funds for purchase!");
  }
  return vb; //not enough funds for purchase!
  }
 else
  if  ( vb.EffectiveCost < 0 ) {
   vb.EffectiveCost = 0;
  };

 for ( int i = 0; i < Constants.MaxCitySiteSlots; i++) {
  if (  BuildingInCitySlot[Player][i].Occupied ) {
   if (  BuildingInCitySlot[Player][i].Building == bWhichBuilding ) { //already built one
    if (  PRGDAutoPlayer[Player] ) {}
    else {
     MsgBox.Inform ("One is already built.");}
    return vb;
    }
   }
  else // Slot is not occupied

   if ( BuildingInventory[WhichBuilding].BType == BldgType.LargeVioletBT )

//    if (  (i >= 2) & ((i - 2) % 3 == 0) ) {}
    if ( (((i + 1) % 3) == 0) ) {}
    else {//valid slot for big buildings
     vb.CandidateCitySlot = i;
     return vb;
    }
   else {
    vb.CandidateCitySlot = i;
    return vb;
   };
   };
   return vb;
  };
 
public static boolean Purchase(BuildingIdx WhichBuilding ) {
 boolean Purchase = false;
 ChooseABuildingData pb;


 pb = VettBuilding(PRGDCurrentPlayer, WhichBuilding);

 if (  pb.CandidateCitySlot == Constants.MaxCitySiteSlots )  //no more room to build
  return false;
 else
  Purchase =  true;
 

 //Congratulations: you just purchased a building!
 BuyABuilding(PRGDCurrentPlayer, pb);

 return Purchase;

};

static void BuyABuilding(SetupPlayerNames sPlayer , ChooseABuildingData cabd) {

int Player = sPlayer.ordinal();
int WhichBuilding = cabd.CandidateBuilding.ordinal();
int NumberBuildings = 0;
int BuildingIndex = 0;

 if ( PRGDAutoPlayer[Player] )
  Utilities.EventAnnounce (PRGDPlayerNameStrings[Player] + " Buys a "
          + LoadResString.ResSs[WhichBuilding + Constants.PRCBldgTextRes]);
 else
  Utilities.LogIt ( PRGDPlayerNameStrings[Player] + " Buys a "
          + LoadResString.ResSs[WhichBuilding + Constants.PRCBldgTextRes]);
 

 BuildingInCitySlot[Player][cabd.CandidateCitySlot].Occupied = true;
 BuildingInCitySlot[Player][cabd.CandidateCitySlot].Building = cabd.CandidateBuilding;
 BuildingInventory[WhichBuilding].BCount = BuildingInventory[WhichBuilding].BCount - 1;

 if  ( BuildingInventory[WhichBuilding].BCount == 0 )
  PRBuildings.Image1[WhichBuilding].setVisible(false);

 switch ( BuildingInventory[WhichBuilding].BType) {
  case SmallProductionBT:
   BuildingVP[Player].SmallProduction = BuildingVP[Player].SmallProduction + 1;
   break;
  case LargeProductionBT:
   BuildingVP[Player].LargeProduction = BuildingVP[Player].LargeProduction + 1;
   break;
  case SmallVioletBT:
  case  LargeVioletBT:
   BuildingVP[Player].Violet = BuildingVP[Player].Violet + 1;
 };

 PRFArray[Player].Text1[Text1Idx.DoubloonsIdx.ordinal()].setText(
         Integer.toString(Integer.valueOf(PRFArray[Player].Text1
         [Text1Idx.DoubloonsIdx.ordinal()].getText())- cabd.EffectiveCost));

 NumberBuildings = PRFArray[Player].Image10.length;
 BuildingIndex = NumberBuildings-1;

 if ( CountPlayer(sPlayer) > 1) { // New building is needed except first time
  BuildingIndex++;
  // first resize the array to include the new building
  PRFArray[Player].Image10 = (javax.swing.JLabel[])Utilities.
          resizeArray(PRFArray[Player].Image10, NumberBuildings+1);

  // next create the new image, a JLabel
  PRFArray[Player].Image10[BuildingIndex] = new javax.swing.JLabel();
  NewBuilding (PRFArray[Player].Image10lp,
	  PRFArray[Player].Image10[BuildingIndex]);
 }

 //With PRFArray[Player].Image10(cabd.CandidateCitySlot)
 {
  javax.swing.JLabel jl = PRFArray[Player].Image10[BuildingIndex];
  int BldgPicIdx = 0;

  if ( BuildingInventory[WhichBuilding].BType == BldgType.LargeVioletBT ) {
   BuildingInCitySlot[Player][cabd.CandidateCitySlot + 1].Occupied = true;
   jl.setSize(Constants.BldgWidth, Constants.BldgSingleHeight*2);
  }
  else
   jl.setSize(Constants.BldgWidth, Constants.BldgSingleHeight);

  if (  Building.Production(sPlayer, BuildingIdx.UniversityIdx) > 0 )
   if (Integer.valueOf(PRPuertoRico.playermat.Text1[Text1Idx.
    ColonistsRemainingIdx.ordinal()].getText()) > 0 ) {
    BldgPicIdx = 1;
    PRPuertoRico.playermat.Text1[Text1Idx.ColonistsRemainingIdx.ordinal()].setText
      (Integer.toString(Integer.valueOf(PRPuertoRico.playermat.Text1
      [Text1Idx.ColonistsRemainingIdx.ordinal()].getText()) - 1));
    BuildingInCitySlot[Player][cabd.CandidateCitySlot].NumColonists = 1; //first colonist is free
//    result = Utilities.("University Grants " + PRGDPlayerNameStrings(Player) + " a free Colonist")
    Utilities.BldgAnnounce (BuildingIdx.UniversityIdx, "University Grants " +
            PRGDPlayerNameStrings[Player] + " a free Colonist");
   }
  else
    BuildingInCitySlot[Player][cabd.CandidateCitySlot].NumColonists = 0;
  
   jl.setIcon(PRFetchResource.FR((Integer.toString(BuildingInventory[WhichBuilding].
          PicIdx.ImgIndex() + BldgPicIdx))));
//  .Tag = BuildingInventory[WhichBuilding].PicIdx + BldgPicIdx; //TODO:: Tag functionality must be replaced...
  jl.setName(Integer.toString(BuildingInventory[WhichBuilding].PicIdx.ImgIndex()
          + BldgPicIdx));

  jl.setLocation(Constants.BldgLeftOrigin + (cabd.CandidateCitySlot/3 * Constants.BldgLeftIncr),
                 Constants.BldgTopOrigin + (cabd.CandidateCitySlot % 3) * Constants.BldgTopIncr);
//  .Left = BldgLeftorigin + (cabd.CandidateCitySlot / 3) * BldgLeftIncr;
//  .Top = BldgToporigin + (cabd.CandidateCitySlot % 3) * BldgTopIncr;
//  .Zorder;
  jl.setVisible(true);
 };
 //End With;

//  ReStaff PRGDCurrentPlayer, FirstFreeSlot, BldgPicIdx

 //adjust VP for buildings
 VPChit.Tally(sPlayer);

 if (((cabd.CandidateCitySlot + 1) == Constants.MaxCitySiteSlots) || ((BuildingInventory[WhichBuilding].
         BType == BldgType.LargeVioletBT)
      & ((cabd.CandidateCitySlot + 2) == Constants.MaxCitySiteSlots)) ) { //game ending criteria met
//  result = Utilities.("Game End has been triggered: " + PRGDPlayerNameStrings(Player)
//                    + " has filled all city slots", vbOKOnly)
  Utilities.EventAnnounce ( "Game End has been triggered: " + 
          PRGDPlayerNameStrings[Player]
                    + " has filled all city slots");
  PRGDGameExitMet = true;
 };

};

public static void NewBuilding (javax.swing.JLayeredPane jlp, javax.swing.JLabel jl) {
 jl.addMouseListener(new java.awt.event.MouseAdapter() {

 public void mouseReleased(java.awt.event.MouseEvent evt) {
PlayerMat.BuildingClick(evt);
  }
 });
 jl.setBounds(3, 2, 110, 60);
 jlp.add(jl, javax.swing.JLayeredPane.PALETTE_LAYER);

}

public static ImageIdx GetBldgPic(BuildingIdx WhichBuilding) {

 return BuildingInventory[WhichBuilding.ordinal()].PicIdx;

};

public static boolean isLargeBuilding (BuildingIdx WhichBuilding) {
   return BuildingInventory[WhichBuilding.ordinal()].BType == BldgType.LargeVioletBT;
};

public static void BldgInfoLabel1(SetupPlayerNames Player , BuildingIdx bWhichBuilding ) {
 int EffectiveCost;
 int WhichBuilding = bWhichBuilding.ordinal();

 PRBuildingInfo.Label1[FBldgInfoIdx1.RemainingFBII1.ordinal()].
         setName(Integer.toString(BuildingInventory[WhichBuilding].BCount)); // TODO:: name used as iPlayer
 PRBuildingInfo.Label1[FBldgInfoIdx1.RemainingFBII1.ordinal()].setText
         (Integer.toString(BuildingInventory[WhichBuilding].BCount) + " Remaining");
 PRBuildingInfo.Label1[FBldgInfoIdx1.NameFBII1.ordinal()].setName(
         Integer.toString(WhichBuilding));
 PRBuildingInfo.Label1[FBldgInfoIdx1.NameFBII1.ordinal()].setText(
         LoadResString.ResSs[WhichBuilding + Constants.PRCBldgTextRes]);
 PRBuildingInfo.Label1[FBldgInfoIdx1.DescFBII1.ordinal()].setText(
         LoadResString.ResSs[WhichBuilding + Constants.BldgHelpBase]);
 PRBuildingInfo.Label1[FBldgInfoIdx1.VPFBII1.ordinal()].setText(
         Integer.toString(BuildingInventory[WhichBuilding].VP));
 EffectiveCost = BuildingInventory[WhichBuilding].Cost;
 PRBuildingInfo.Label1[FBldgInfoIdx1.CostBaseFBII1.ordinal()].setText(
         Integer.toString(EffectiveCost));
 EffectiveCost = EffectiveCost - QuarryFactor(Player, bWhichBuilding);
 if ( EffectiveCost < 0 )
  EffectiveCost = 0;
 
 PRBuildingInfo.Label1[FBldgInfoIdx1.CostLessQuarryFBII1.ordinal()].
         setText(Integer.toString(EffectiveCost));
 if ( Player == PRGDPrivilegedPlayer )
  EffectiveCost--;
 
 if (  EffectiveCost < 0 )
  EffectiveCost = 0;
 
 PRBuildingInfo.Label1[FBldgInfoIdx1.CostLessBonusFBII1.ordinal()].
         setName( Integer.toString(EffectiveCost));
 PRBuildingInfo.Label1[FBldgInfoIdx1.CostLessBonusFBII1.ordinal()].
         setText(Integer.toString(EffectiveCost));

};

public static void InfoCard(BuildingIdx WhichBldg ,boolean IsManned , String LogString ){
 ImageIdx PixIndex;

 PixIndex = GetBldgPic(WhichBldg);

 if ( IsManned ) 
  PixIndex =  ImageIdx.values()[PixIndex.ordinal() + 1]; //TODO:: reverse ENUM
  PRInformation.Image1.setIcon(PRFetchResource.FR(PixIndex.SImgIndex()));
 
 if (  isLargeBuilding(WhichBldg) )
  PRInformation.Image1.setSize(Constants.BldgWidth, Constants.
          BldgSingleHeight * 2);
 else
  PRInformation.Image1.setSize(Constants.BldgWidth, Constants.
          BldgSingleHeight);

 PRInformation.Label1.setText(LogString);
 MsgBox.Inform(PRInformation.Panel);

};

public static void ConfirmCard() {
 FBuildingInfo fbi = PRBuildingInfo;
 BuildingIdx ChosenBuilding = BuildingIdx.values()[Integer.valueOf(fbi.Label1
	 [FBldgInfoIdx1.NameFBII1.ordinal()].getName())];
 int iChosenBuilding = ChosenBuilding.ordinal();

 PRInformation.Image1.setIcon(PRFetchResource.FR(BuildingInventory[iChosenBuilding].PicIdx
          .SImgIndex()));

 PRInformation.Label1.setText("Do you want to build the " +
          LoadResString.ResSs[iChosenBuilding + Constants.PRCBldgTextRes]
        + " for a final cost of " + fbi.Label1[FBldgInfoIdx1.CostLessBonusFBII1
        .ordinal()].getText() + "? There are currently " + fbi.Label1
        [FBldgInfoIdx1.RemainingFBII1.ordinal()].getText() + ".");
if (MsgBox.Confirm(PRInformation.Panel)) {
  if ( Purchase(ChosenBuilding) ) {
   PRBuildings.setVisible(false);
   Building.InfoCard(ChosenBuilding, false, "You Built the "
	   + PRBuildingInfo.Label1[FBldgInfoIdx1.NameFBII1.ordinal()].getText());
   PRPuertoRico.GoGoGo();
 };
}
else
 PRBuildings.setVisible(true);

};

static int CountPlayer(SetupPlayerNames sPlayer )  {
 //Count number of buildings built by given player
 int Player = sPlayer.ordinal();
 int NumVacants = 0;
 int CountPlayer = 0;

 for ( int ibs = 0; ibs < Constants.MaxCitySiteSlots; ibs++){
  if ( BuildingInCitySlot[Player][ibs].Occupied ) {
   NumVacants = 0;
   if ( BuildingInCitySlot[Player][ibs].Building == BuildingIdx.NullBldgIdx ) {}
   else
    CountPlayer = CountPlayer + 1;
  }
  else {
   NumVacants = NumVacants++;
   if ( NumVacants > 1 )
    break;
   };
  };
  return CountPlayer;
 };

public static int Count() {
//Returns number of free spots in all players// buildings.
 int NumVacants = 0;
 int Count = 0;

 for ( int iP = SetupPlayerNames.HumanPlayerSPN.ordinal();
       iP < PRGDActualNumPlayers; iP++){
  for ( int ibs = 0; ibs < Constants.MaxCitySiteSlots; ibs++) {
   if  ( BuildingInCitySlot[iP][ibs].Occupied ) {
    NumVacants = 0;
    if  ( BuildingInCitySlot[iP][ibs].Building == BuildingIdx.NullBldgIdx ) {}
    else
     Count = Count + BuildingInventory[BuildingInCitySlot[iP][ibs].Building.ordinal()].NumPosts
             - BuildingInCitySlot[iP][ibs].NumColonists;
   }
   else {
    NumVacants++;
    if ( NumVacants > 1)
     break;
    };
   };
  };
  return Count;
 };

public static int StaffCount(SetupPlayerNames sPlayer )  {
//Returns number of colonists staffing players// buildings.
 int Player = sPlayer.ordinal();
 int NumVacantSlot = 0;
 int StaffCount = 0;

 for ( int ibs = 0; ibs < Constants.MaxCitySiteSlots; ibs++){
  if (  BuildingInCitySlot[Player][ibs].Occupied ) {
   NumVacantSlot = 0;
   if (  BuildingInCitySlot[Player][ibs].Building == BuildingIdx.NullBldgIdx ) {}
   else
    StaffCount = StaffCount + BuildingInCitySlot[Player][ibs].NumColonists;
   }
  else {
   NumVacantSlot++;
   if (  NumVacantSlot > 1 )
    return StaffCount;
   };
  };
  return StaffCount;
 };

public static int VacancyCount(SetupPlayerNames sPlayer ) {
//Returns number of vacant spots in players// buildings.
int Player = sPlayer.ordinal();
int NumVacantSlot = 0;
int VacancyCount = 0;

for ( int ibs = 0; ibs < Constants.MaxCitySiteSlots; ibs++){
  if (  BuildingInCitySlot[Player][ibs].Occupied ) {
   NumVacantSlot = 0;
   if (  BuildingInCitySlot[Player][ibs].Building == BuildingIdx.NullBldgIdx ) {}
   else
    VacancyCount = VacancyCount + BuildingInventory[BuildingInCitySlot[Player][ibs].
            Building.ordinal()].NumPosts - BuildingInCitySlot[Player][ibs].NumColonists;
   }
  else {
   NumVacantSlot++;
   if (  NumVacantSlot > 1 )
    return VacancyCount;
   
  };
 };
 return VacancyCount;
};

static void Evacuate(SetupPlayerNames sPlayer ) {
// The AI calls this to bring everything back to SJ
 int Player = sPlayer.ordinal();
 int NumVacantSlot = 0;

 for (int ics = 0; ics < Constants.MaxCitySiteSlots; ics++){
   if ( BuildingInCitySlot[Player][ics].Occupied ) {
    NumVacantSlot = 0;
    if (  BuildingInCitySlot[Player][ics].Building == BuildingIdx.NullBldgIdx ) {}
    else
     ReStaff(sPlayer, CitySlotToBuildingSlot(ics), -BuildingInCitySlot[Player][ics].NumColonists);
   }
   else {
    NumVacantSlot++;
    if (  NumVacantSlot > 1 )
     return;
    
   };
  };

};

static void Populate(SetupPlayerNames Player , BldgType BT ) {
// AI Calls this to manage colonists during the mayor phase

 switch ( BT ) {

  case SmallProductionBT:
  case LargeProductionBT:
    ProdPop (Player, BT);
    break;
   case SmallVioletBT:
   case LargeVioletBT:
    SlotPop (Player, BT);
 };

};

static void SlotPop(SetupPlayerNames sPlayer , BldgType BT ) {
//AI Populates all Buildings of a specific Building Type
// ImageIdx BldgPicIdx ;
 int Player = sPlayer.ordinal();
 int NumVacantSlot = 0;

for (int ics = 0; ics < Constants.MaxCitySiteSlots; ics++){

  if (Integer.valueOf(PRFArray[Player].Text1[Text1Idx.SanJuanIdx.ordinal()].
          getText()) == 0)
   return; //San Juan is too empty to populate further
  
  if (  BuildingInCitySlot[Player][ics].Occupied ) {
   NumVacantSlot = 0;
   if  ( (BuildingInCitySlot[Player][ics].Building == BuildingIdx.NullBldgIdx) ) {}
   else
    if ( (BuildingInventory[BuildingInCitySlot[Player][ics].Building.ordinal()].BType == BT) )
     if  ( BuildingInCitySlot[Player][ics].NumColonists == 0 )
      ReStaff(sPlayer, CitySlotToBuildingSlot(ics), 1);
  }
  else {
    NumVacantSlot++;
    if (  NumVacantSlot > 1 )
     return;
  };
  
 }; //Slot

};

static void ProdPop(SetupPlayerNames sPlayer , BldgType BT ) {
//AI Populates Buildings and the plantation that goes with it
 int Player = sPlayer.ordinal();
 // ImageIdx BldgPicIdx ;
 ResourceIdx TileToCheck;
 BuildingIdx BldgToCheck = BuildingIdx.NullBldgIdx;
 int TileCount = 0;
 int PostCount;
 int CitySlot = Constants.MaxCitySiteSlots;
 boolean DoPopulate ;

 for (int iRes = Constants.PRCNumGoods - 1;iRes > 0; iRes--){
  if  ( Integer.valueOf(PRFArray[Player].Text1[Text1Idx.SanJuanIdx.
          ordinal()].getText()) < 2)
          return; //San Juan too empty to populate Bldg/Plantation Pair
  
  TileToCheck = AIGameData.AIGDPriorityGoodShip[iRes];

  DoPopulate = true;

  switch ( TileToCheck ){
   case Indigo:
    if (  BT == BldgType.SmallProductionBT )
     BldgToCheck = BuildingIdx.SmallIndigoPlantIdx;
    else {
        if ( BT == BldgType.LargeProductionBT )
         BldgToCheck = BuildingIdx.IndigoPlantIdx;
        else 
         DoPopulate = false;
    };
    break;
   case Sugar:
    if ( BT == BldgType.SmallProductionBT )
     BldgToCheck = BuildingIdx.SmallSugarMillIdx;
    else{
     if ( BT == BldgType.LargeProductionBT )
      BldgToCheck = BuildingIdx.SugarMillIdx;
     else
      DoPopulate = false;
     };
     break;
    case Tobacco:
     if (  BT == BldgType.LargeProductionBT )
      BldgToCheck = BuildingIdx.TobaccoStorageIdx;
     else
      DoPopulate = false;
     break;
    case Coffee:
     if (  BT == BldgType.LargeProductionBT )
      BldgToCheck = BuildingIdx.CoffeeRoasterIdx;
     else
      DoPopulate = false;
     break;
    case Quarry: // anything above Coffee doesn't apply
    case NullRIdx:
      DoPopulate = false;
   };

  if (  DoPopulate ) {
   int ttc = TileToCheck.ordinal() * 2 + ImageIdx.IndigoEmpty.ordinal();
   TileCount = Tile.PlantationCount(sPlayer, ImageIdx.values()[ttc]); // TODO:: reverse ENUM
   CitySlot = FindSlot(sPlayer, BldgToCheck);

   if (  (TileCount > 0) & (CitySlot < Constants.MaxCitySiteSlots) ) { //Need more than 1 colonist to pair-populate production
    PostCount = BuildingInventory[BuildingInCitySlot[Player][CitySlot].Building.
            ordinal()].NumPosts;
    if (  TileCount > PostCount ) {
     TileCount = PostCount;
    };
    if (  TileCount > (Integer.valueOf(PRFArray[Player].Text1[Text1Idx.SanJuanIdx.
            ordinal()].getText()) / 2) ) { 
     TileCount = Integer.valueOf(PRFArray[Player].Text1[Text1Idx.SanJuanIdx.ordinal()].
             getText()) / 2;
    };

    Tile.Populate(sPlayer, TileToCheck, TileCount);
   }
   else //No Plantations and/or Bldg of chosen type
    DoPopulate = false;
  };

  if ( DoPopulate )
   ReStaff(sPlayer, CitySlotToBuildingSlot(CitySlot), TileCount);
  

 }; //Resource
};

static void PopulateLast(SetupPlayerNames sPlayer ) {
 //This is called to conclude AI mayor phase
 int Player = sPlayer.ordinal();
 int NumVacants;
 int NumFreePosts ;

 //if empty plantations exist, populate them
 for (ResourceIdx iR: EnumSet.range(ResourceIdx.Indigo, ResourceIdx.Coffee))
  Tile.FillUpAllTileType(sPlayer, iR);
 
 //if there is still population in S.J., populate buildings that are empty

 NumVacants = 0;

 for (int ics = 0; ics < Constants.MaxCitySiteSlots; ics++){
  int sji =  Integer.valueOf(PRFArray[Player].Text1[Text1Idx.SanJuanIdx.
             ordinal()].getText());
  if (  BuildingInCitySlot[Player][ics].Occupied ) {
   NumVacants = 0;
   if ( BuildingInCitySlot[Player][ics].Building == BuildingIdx.NullBldgIdx ) {}
   else {
    NumFreePosts = BuildingInventory[BuildingInCitySlot[Player][ics].Building.ordinal()].
            NumPosts - BuildingInCitySlot[Player][ics].NumColonists;
    if  ( NumFreePosts > 0 ) {
     if  ( NumFreePosts > sji )
      NumFreePosts = sji;
     
     ReStaff(sPlayer, CitySlotToBuildingSlot(ics), NumFreePosts);
    };
   };
  }
  else {
   NumVacants++;
   if ( NumVacants > 1 )
    break;
  };

  if (Integer.valueOf(PRFArray[Player].Text1[Text1Idx.SanJuanIdx.
             ordinal()].getText()) == 0 )
   return;

};
};

public static int ToteBuildingVP(SetupPlayerNames sPlayer)  {
 int Player = sPlayer.ordinal();
 int NumPlantations;
 int NumVacantSlot = 0;
 int ToteBuildingVP = 0;

 for (int ics = 0; ics < Constants.MaxCitySiteSlots; ics++){
  if (  BuildingInCitySlot[Player][ics].Occupied ) {
   NumVacantSlot = 0;
   if (  BuildingInCitySlot[Player][ics].Building == BuildingIdx.NullBldgIdx ) {}
   else {
    ToteBuildingVP = ToteBuildingVP + 
        BuildingInventory[BuildingInCitySlot[Player][ics].Building.ordinal()].VP;

    if ( BuildingInventory[BuildingInCitySlot[Player][ics].Building.ordinal()].BType
            == BldgType.LargeVioletBT ) {
     if (  BuildingInCitySlot[Player][ics].NumColonists > 0 ) {
      switch ( BuildingInCitySlot[Player][ics].Building ) {
          case GuildHallIdx:
           ToteBuildingVP = ToteBuildingVP + BuildingVP[Player].SmallProduction
                         + BuildingVP[Player].LargeProduction * 2;
           break;
          case ResidenceIdx:
           NumPlantations = Tile.PlantationTotal(sPlayer);
           if (  NumPlantations > 9 ) 
            ToteBuildingVP = ToteBuildingVP + (NumPlantations - 5);
           else
            ToteBuildingVP = ToteBuildingVP + 4;
           break;
          case FortressIdx:
           ToteBuildingVP = ToteBuildingVP + (StaffCount(sPlayer) + Integer.valueOf
            (PRFArray[Player].Text1[Text1Idx.SanJuanIdx.
                   ordinal()].getText()) + Tile.PlantationWorkforce(sPlayer)) / 3;
            break;
          case CustomsHouseIdx:
           ToteBuildingVP = ToteBuildingVP + Integer.valueOf(PRFArray[Player].
                   Text1[Text1Idx.VPChipsIdx.ordinal()].getText()) / 4;
           break;
          case CityHallIdx:
           ToteBuildingVP = ToteBuildingVP + BuildingVP[Player].Violet;
      };
     }; //big building occupied
    }; //big building
   }; //null building
  }
  else { //building not occupied
   NumVacantSlot++;
   if (  NumVacantSlot > 1 ) {
    return ToteBuildingVP;
   };
  };
 };
 return ToteBuildingVP;
};

public static int Production(SetupPlayerNames sPlayer , BuildingIdx WhichBuilding )  {
//counts up number of staff in buildings that match the building index passed in
 int Player = sPlayer.ordinal();
 int NumVacants = 0;

 for (int i = 0; i < Constants.MaxCitySiteSlots; i++) {
  if (BuildingInCitySlot[Player][i].Occupied ) {
   NumVacants = 0;
   if (  BuildingInCitySlot[Player][i].Building == WhichBuilding )
    return  BuildingInCitySlot[Player][i].NumColonists;
  }
  else {
   NumVacants++;
   if (  NumVacants > 1 )
    return 0;
   };
 };
 return 0;
};

static int VP(BuildingIdx WhichBuilding )  {
 if (  WhichBuilding == BuildingIdx.NullBldgIdx )
  return 0;
  
 return BuildingInventory[WhichBuilding.ordinal()].VP;

};

static int Cost(BuildingIdx WhichBuilding)  {

 if (  WhichBuilding == BuildingIdx.NullBldgIdx )
  return 0;
 
 return BuildingInventory[WhichBuilding.ordinal()].Cost;

};

static int FormSlotToBuildingSlot(int FormSlot) {
 for (int i = 0; i < Constants.MaxCitySiteSlots; i++) {	
 javax.swing.JLabel jl = PRFArray[PRGDCurrentPlayer.ordinal()].Image10[i];
 if (SwingUtilities.getAccessibleIndexInParent(jl) == FormSlot)
  return i;
 };
 return Constants.MaxCitySiteSlots;
}

static int BuildingSlotToCitySlot(int BuildingSlot) {
// Converts Form Slot (index given by swing upon new building creation)
// into City Slot (each player has 12 of these)
ImageIdx iBuildingIndex = ImageIdx.Blank1;
// Building Slot does not correspond to parent index, so do lookup

 iBuildingIndex = ImageIdx.Lookup(Integer.valueOf(PRFArray[
	PRGDCurrentPlayer.ordinal()].Image10[BuildingSlot].getName()));

if (iBuildingIndex == ImageIdx.Blank1)
 return Constants.MaxCitySiteSlots;

return FindSlot(PRGDCurrentPlayer, ImagetoBuilding(iBuildingIndex));

}

static int CitySlotToBuildingSlot(int CitySlot) {

 for (int i = 0; i < PRFArray[PRGDCurrentPlayer.ordinal()].Image10.length; i++) {
  if (BuildingInCitySlot[PRGDCurrentPlayer.ordinal()][CitySlot].Building ==
		ImagetoBuilding(ImageIdx.Lookup(Integer.valueOf(
		PRFArray[PRGDCurrentPlayer.ordinal()].Image10[i].getName()))))
   return i;
 }
 return Constants.MaxCitySiteSlots; 
}

static BuildingIdx ImagetoBuilding (ImageIdx iDex) {
 if ((iDex.compareTo(ImageIdx.SmallIndigoPlantEmpty)>= 0) & (iDex.compareTo(ImageIdx.CityHallOccupied) <= 0))
  switch (iDex)	{
	  case SmallIndigoPlantEmpty:
	  case SmallIndigoPlantOccupied:
		  return BuildingIdx.SmallIndigoPlantIdx;
	  case SmallSugarMillEmpty:
	  case SmallSugarMillOccupied:
		  return BuildingIdx.SmallSugarMillIdx;
	  case SmallMarketEmpty:
	  case SmallMarketFull:
		  return BuildingIdx.SmallMarketIdx;
	  case HaciendaEmpty:
	  case HaciendaOccupied:
		  return BuildingIdx.HaciendaIdx;
	  case ConstructionHutEmpty:
	  case ConstructionHutOccupied:
		  return BuildingIdx.ConstructionHutIdx;
	  case SmallWarehouseEmpty:
	  case SmallWarehouseOccupied:
		  return BuildingIdx.SmallWarehouseIdx;
	  case IndigoPlantEmpty:
	  case IndigoPlantOccupied1:
	  case IndigoPlantOccupied2:
	  case IndigoPlantOccupied3:
		  return BuildingIdx.IndigoPlantIdx;
	  case SugarMillEmpty:
	  case SugarMillOccupied1:
	  case SugarMillOccupied2:
	  case SugarMillOccupied3:
		  return BuildingIdx.SugarMillIdx;
	  case HospiceEmpty:
	  case HospiceOccupied:
		  return BuildingIdx.HospiceIdx;
	  case OfficeEmpty:
	  case OfficeOccupied:
		  return BuildingIdx.OfficeIdx;
	  case LargeMarketEmpty:
	  case LargeMarketOccupied:
		  return BuildingIdx.LargeMarketIdx;
	  case LargeWarehouseEmpty:
	  case LargeWarehouseOccupied:
		  return BuildingIdx.LargeWarehouseIdx;
	  case TobaccoStorageEmpty:
	  case TobaccoStorageOccupied1:
	  case TobaccoStorageOccupied2:
	  case TobaccoStorageOccupied3:
		  return BuildingIdx.TobaccoStorageIdx;
	  case CoffeeRoasterEmpty:
	  case CoffeeRoasterOccupied1:
	  case CoffeeRoasterOccupied2:
		  return BuildingIdx.CoffeeRoasterIdx;
	  case FactoryEmpty:
	  case FactoryOccupied:
		  return BuildingIdx.FactoryIdx;
	  case UniversityEmpty:
	  case UniversityOccupied:
		  return BuildingIdx.UniversityIdx;
	  case HarbourEmpty:
	  case HarbourOccupied:
		  return BuildingIdx.HarbourIdx;
	  case WharfEmpty:
	  case WharfOccupied:
		  return BuildingIdx.WharfIdx;
	  case GuildHallEmpty:
	  case GuildHallOccupied:
		  return BuildingIdx.GuildHallIdx;
	  case ResidenceEmpty:
	  case ResidenceOccupied:
		  return BuildingIdx.ResidenceIdx;
	  case FortressEmpty:
	  case FortressOccupied:
		  return BuildingIdx.FortressIdx;
	  case CustomsHouseEmpty:
	  case CustomsHouseOccupied:
		  return BuildingIdx.CustomsHouseIdx;
	  case CityHallEmpty:
	  case CityHallOccupied:
		  return BuildingIdx.CityHallIdx;
  }
 return BuildingIdx.NullBldgIdx;
}

static int FindSlot(SetupPlayerNames sPlayer , BuildingIdx WhichBuilding )  {
//Returns City Slot of Building Found; returns MaxCitySiteSlots if no building found
 int Player = sPlayer.ordinal();
 int NumVacants = 0;

 for (int i = 0; i < Constants.MaxCitySiteSlots; i++){
  if (  BuildingInCitySlot[Player][i].Occupied ) {
   NumVacants = 0;
   if (  BuildingInCitySlot[Player][i].Building == WhichBuilding )
    return i;
  }
  else {
   NumVacants++;
   if (  NumVacants > 1 )
    return Constants.MaxCitySiteSlots;
  };
 };
 return Constants.MaxCitySiteSlots;
};

static int RefineryCapacity(SetupPlayerNames Player , BuildingIdx rWhichRefinery ) {
//Meant for multi-slot buildings to show staffing
 int RefSlot = FindSlot(Player, rWhichRefinery);
 int WhichRefinery = rWhichRefinery.ordinal();

 if (  RefSlot < Constants.PRRMaxPlantations )
  return BuildingInventory[WhichRefinery].NumPosts;
 else
  return 0;
 
};

static void ReStaff(SetupPlayerNames sPlayer, int BuildingSlot , int Delta ){
 //Moves multiple colonists between San Juan and a building
 int Player = sPlayer.ordinal();
 ImageIdx BldgPicIdx;
 int ibuilding = Integer.valueOf(PRFArray[Player].Image10[BuildingSlot].getName());
 javax.swing.JLabel jl[] = PRFArray[Player].Image10;
 BldgPicIdx = ImageIdx.Lookup(ibuilding + Delta);
 jl[BuildingSlot].setIcon(PRFetchResource.FR( BldgPicIdx.SImgIndex()));
 //PRFArray[Player].Image10[WhichSlot].Picture = LoadResPicture(BldgPicIdx);
 PRFArray[Player].Image10[BuildingSlot].setName(BldgPicIdx.SImgIndex());
 PRFArray[Player].Text1[Text1Idx.SanJuanIdx.ordinal()].setText(Integer.toString(
         Integer.valueOf(PRFArray[Player].Text1[Text1Idx.SanJuanIdx.
         ordinal()].getText()) - Delta));
 BuildingInCitySlot[Player][BuildingSlotToCitySlot(BuildingSlot)].NumColonists =
  BuildingInCitySlot[Player][BuildingSlotToCitySlot(BuildingSlot)].NumColonists + Delta;

};

static int QuarryFactor(SetupPlayerNames Player, BuildingIdx bWhichBuilding )  {
 int QuarryFactor = 0;
 int WhichBuilding = bWhichBuilding.ordinal();

 QuarryFactor = Tile.PlantationCount(Player, ImageIdx.QuarryOccupied);
 if (  BuildingInventory[WhichBuilding].MaxQuarry < QuarryFactor )
  QuarryFactor = BuildingInventory[WhichBuilding].MaxQuarry;
 return QuarryFactor;
};

public static void Initialise() {
 int iBuildingCounter = 0;
 ImageIdx BaseIdx;
 int CtrOffset = 0;

 BuildingInCitySlot = new CitySiteSlot[PRGDActualNumPlayers]
         [Constants.MaxCitySiteSlots];
 BuildingInventory = new BuildingBluePrint[Constants.MaxBuildingIdx+1];
 BuildingVP = new BuildingVPType[PRGDActualNumPlayers];

 //First, load up inventory

 //start with defaults
 for (BuildingIdx BuildingCounter: EnumSet.range(BuildingIdx.SmallIndigoPlantIdx,
         BuildingIdx.WharfIdx)) {
  iBuildingCounter = BuildingCounter.ordinal();
  BuildingInventory[iBuildingCounter] = new BuildingBluePrint();
  BuildingInventory[iBuildingCounter].BCount = 2;
  BuildingInventory[iBuildingCounter].NumPosts = 1;
  BuildingInventory[iBuildingCounter].BType = BldgType.SmallVioletBT;
 };

 for (BuildingIdx BuildingCounter : EnumSet.range(BuildingIdx.GuildHallIdx, BuildingIdx.
         CityHallIdx)){
  iBuildingCounter = BuildingCounter.ordinal();
  BuildingInventory[iBuildingCounter] = new BuildingBluePrint();
  BuildingInventory[iBuildingCounter].BCount = 1;
  BuildingInventory[iBuildingCounter].NumPosts = 1;
  BuildingInventory[iBuildingCounter].Cost = 10;
  BuildingInventory[iBuildingCounter].MaxQuarry = 4;
  BuildingInventory[iBuildingCounter].VP = 4;
  BuildingInventory[iBuildingCounter].BType = BldgType.LargeVioletBT;
 };

 //Now handle exceptions

 //the ugly, ugly init code for image index (better at init time...)
 for ( BuildingIdx BuildingCounter : EnumSet.range(BuildingIdx.SmallIndigoPlantIdx,
         BuildingIdx.CityHallIdx)) {

  if (  BuildingCounter.compareTo(BuildingIdx.SugarMillIdx) < 0 ) { //Indigo Plant causes gap b4 sugar mill
   BaseIdx = ImageIdx.SmallIndigoPlantEmpty;
   CtrOffset = 0; }
  else
   if (  BuildingCounter == BuildingIdx.SugarMillIdx ) {
     BaseIdx = ImageIdx.SugarMillEmpty;
     CtrOffset = BuildingIdx.SugarMillIdx.ordinal();}
   else //Sugar Mill causes gap before Hospice
    if (  BuildingCounter.compareTo(BuildingIdx.CoffeeRoasterIdx) < 0 ) {
     BaseIdx = ImageIdx.HospiceEmpty;
     CtrOffset = BuildingIdx.HospiceIdx.ordinal();}
    else //Tobacco Storage causes gap before coffee roaster
     if (  BuildingCounter == BuildingIdx.CoffeeRoasterIdx ) {
      BaseIdx = ImageIdx.CoffeeRoasterEmpty;
      CtrOffset = BuildingIdx.CoffeeRoasterIdx.ordinal();}
     else {//Coffee Roaster cause gap before factory
      BaseIdx = ImageIdx.FactoryEmpty;
      CtrOffset = BuildingIdx.FactoryIdx.ordinal();
     };

  BuildingInventory[BuildingCounter.ordinal()].PicIdx =
          ImageIdx.values()[BaseIdx.ordinal() + (BuildingCounter.ordinal() -
          CtrOffset) * 2];

 };

 BuildingInventory[BuildingIdx.SmallIndigoPlantIdx.ordinal()].BType = BldgType.SmallProductionBT;
 BuildingInventory[BuildingIdx.SmallSugarMillIdx.ordinal()].BType = BldgType.SmallProductionBT;
 BuildingInventory[BuildingIdx.IndigoPlantIdx.ordinal()].BType = BldgType.LargeProductionBT;
 BuildingInventory[BuildingIdx.SugarMillIdx.ordinal()].BType = BldgType.LargeProductionBT;
 BuildingInventory[BuildingIdx.TobaccoStorageIdx.ordinal()].BType = BldgType.LargeProductionBT;
 BuildingInventory[BuildingIdx.CoffeeRoasterIdx.ordinal()].BType = BldgType.LargeProductionBT;

 BuildingInventory[BuildingIdx.SmallIndigoPlantIdx.ordinal()].BCount = 4;
 BuildingInventory[BuildingIdx.SmallSugarMillIdx.ordinal()].BCount = 4;

 BuildingInventory[BuildingIdx.IndigoPlantIdx.ordinal()].BCount = 3;
 BuildingInventory[BuildingIdx.SugarMillIdx.ordinal()].BCount = 3;
 BuildingInventory[BuildingIdx.TobaccoStorageIdx.ordinal()].BCount = 3;
 BuildingInventory[BuildingIdx.CoffeeRoasterIdx.ordinal()].BCount = 3;

 BuildingInventory[BuildingIdx.IndigoPlantIdx.ordinal()].NumPosts = 3;
 BuildingInventory[BuildingIdx.SugarMillIdx.ordinal()].NumPosts = 3;
 BuildingInventory[BuildingIdx.TobaccoStorageIdx.ordinal()].NumPosts = 3;
 BuildingInventory[BuildingIdx.CoffeeRoasterIdx.ordinal()].NumPosts = 2;

 //Now boring accounting building costs
 BuildingInventory[BuildingIdx.SmallIndigoPlantIdx.ordinal()].Cost = 1;
 BuildingInventory[BuildingIdx.SmallSugarMillIdx.ordinal()].Cost = 2;
 BuildingInventory[BuildingIdx.SmallMarketIdx.ordinal()].Cost = 1;
 BuildingInventory[BuildingIdx.HaciendaIdx.ordinal()].Cost = 2;
 BuildingInventory[BuildingIdx.ConstructionHutIdx.ordinal()].Cost = 2;
 BuildingInventory[BuildingIdx.SmallWarehouseIdx.ordinal()].Cost = 3;
 BuildingInventory[BuildingIdx.IndigoPlantIdx.ordinal()].Cost = 3;
 BuildingInventory[BuildingIdx.SugarMillIdx.ordinal()].Cost = 4;
 BuildingInventory[BuildingIdx.HospiceIdx.ordinal()].Cost = 4;
 BuildingInventory[BuildingIdx.OfficeIdx.ordinal()].Cost = 5;
 BuildingInventory[BuildingIdx.LargeMarketIdx.ordinal()].Cost = 5;
 BuildingInventory[BuildingIdx.LargeWarehouseIdx.ordinal()].Cost = 6;
 BuildingInventory[BuildingIdx.TobaccoStorageIdx.ordinal()].Cost = 5;
 BuildingInventory[BuildingIdx.CoffeeRoasterIdx.ordinal()].Cost = 6;
 BuildingInventory[BuildingIdx.FactoryIdx.ordinal()].Cost = 7;
 BuildingInventory[BuildingIdx.UniversityIdx.ordinal()].Cost = 8;
 BuildingInventory[BuildingIdx.HarbourIdx.ordinal()].Cost = 8;
 BuildingInventory[BuildingIdx.WharfIdx.ordinal()].Cost = 9;
for (ResourceIdx iR: EnumSet.range(ResourceIdx.Indigo, ResourceIdx.Coffee)) {

 for ( BuildingIdx BuildingCounter : EnumSet.range(BuildingIdx.SmallIndigoPlantIdx,
         BuildingIdx.SmallWarehouseIdx)) {
  iBuildingCounter = BuildingCounter.ordinal();
  BuildingInventory[iBuildingCounter].MaxQuarry = 1;
  BuildingInventory[iBuildingCounter].VP = 1;
 };

 for ( BuildingIdx BuildingCounter : EnumSet.range(BuildingIdx.IndigoPlantIdx,
         BuildingIdx.LargeWarehouseIdx)){
  iBuildingCounter = BuildingCounter.ordinal();
  BuildingInventory[iBuildingCounter].MaxQuarry = 2;
  BuildingInventory[iBuildingCounter].VP = 2;
 };

 for ( BuildingIdx BuildingCounter : EnumSet.range(BuildingIdx.TobaccoStorageIdx,
         BuildingIdx.WharfIdx)){
  iBuildingCounter = BuildingCounter.ordinal();
  BuildingInventory[iBuildingCounter].MaxQuarry = 3;
  BuildingInventory[iBuildingCounter].VP = 3;
 };

 //Done with inventory
 //Now handle Buildings in each player's slots
 for ( int iP = SetupPlayerNames.HumanPlayerSPN.ordinal(); iP <
         PRGDActualNumPlayers; iP++){
  for (int ics = 0; ics < Constants.MaxCitySiteSlots; ics++){
   BuildingInCitySlot[iP][ics] = new CitySiteSlot();
  };
  BuildingVP[iP] = new BuildingVPType();
 };

};

}
}