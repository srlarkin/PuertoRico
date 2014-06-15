/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ClassModules;

import static Modules.Constants.*;
import static Modules.PRGameData.*;
import static Modules.Text1Idx.*;
import static Modules.ResourceIdx.*;
import Modules.BuildingIdx;
import Modules.ImageIdx;
import Modules.MsgBox;
import Modules.NewTilePick;
import Modules.ResourceIdx;
import Modules.PRTileMax;
import Modules.SetupAvailableRoles;
import Modules.SetupChoiceSeat;
import Modules.SetupPlayerNames;
import Modules.TileBagType;
import Modules.TilePickIdx;
import Modules.TilePickRC;
import Modules.Utilities;
import java.awt.Dialog.ModalityType;
import java.util.EnumSet;
import javax.swing.JOptionPane;
import Modules.AIGameData;

/**
 *
 * @author steve
 */
public class Tile {

private static TileBagType TileBag[] = new TileBagType [PRCNumGoods];
private static int TileCount = 0;
private static int DiscardCount = 0;
public static TilePickIdx CurrentTilePick = TilePickIdx.NullTPI;

public static void Renew() {
 Discard();
 PlaceTiles();
};

public static void Discard() {
 int  TileDiscarded;
 for (int i = SetupChoiceSeat.FirstSCS.ordinal(); i <= PRGDActualNumPlayers; i++){
  if ( PRPuertoRico.playermat.Image8[i].isVisible()) {
   DiscardCount++;
   TileDiscarded = (Integer.valueOf(PRPuertoRico.playermat.Image8[i].getName())/10 - 1);
   TileBag[TileDiscarded].Discard = TileBag[TileDiscarded].Discard + 1;
   PRPuertoRico.playermat.Image8[i].setVisible(false);
  };
 }; 
};

public static void ShuffleDiscardsBack() {
 if ( (TileCount == 0) & (DiscardCount > 0) ) {
  TileCount = DiscardCount;
  DiscardCount = 0;
  for (Modules.ResourceIdx i : EnumSet.range( Indigo , Coffee)){
   TileBag[i.ordinal()].Bank = TileBag[i.ordinal()].Discard;
   TileBag[i.ordinal()].Discard = 0;
  }; 
//  result = Inform.Inform["Shuffling Discarded Tiles Back in", JOptionPane.OK_OPTION]
  Utilities.EventAnnounce ( "Shuffling Discarded Tiles Back in");
 };
};

static TilePickIdx FindATilePick (ImageIdx WhichTile ){

 for (int iPS = 0; iPS <= PRGDActualNumPlayers; iPS++){
  if ( PRPuertoRico.playermat.Image8[iPS].isVisible() ) 
   if ( ImageIdx.Lookup(Integer.valueOf(PRPuertoRico.playermat.Image8[iPS].getName())) == WhichTile)
    return TilePickIdx.values()[iPS];
 };
 return TilePickIdx.NullTPI;
}; 

static NewTilePick PickAnyTile(){
// Called by AI
 NewTilePick ntp = new NewTilePick();
 ntp.Tile = TilePickIdx.NullTPI;
 ntp.Resource = NullRIdx;

 for (int iPS = 0 ; iPS <= PRGDActualNumPlayers; iPS++){
  if ( PRPuertoRico.playermat.Image8[iPS].isVisible() ) {
   ImageIdx ii = ImageIdx.Lookup(Integer.valueOf(PRPuertoRico.playermat.Image8[iPS].getName()));
   ntp.Tile = TilePickIdx.values()[iPS];
   ntp.Resource = ImagetoResource(ii);
   return ntp;
  };
 }; 
 //TODO:: Also try adding a quarry here.
 return ntp;
};

public static TilePickRC PlaceATileImage(javax.swing.JLabel TileSite ){
 TilePickRC rc = TilePickRC.TPRCOK;
 int iTileCandidate = NullRIdx.ordinal();
 ImageIdx TileCandidate;

 if ( TileCount == 0 ) 
  ShuffleDiscardsBack();
 
 while (TileCount > 0) {
  //generate new tile
  iTileCandidate = Utilities.RandomPick(Indigo.ordinal(), Coffee.ordinal());
  TileCandidate = ImageIdx.values()[iTileCandidate*2+ImageIdx.IndigoEmpty.ordinal()];
  //assign tile if there is one of that type left
  if ( TileBag[iTileCandidate].Bank > 0 ) {
   TileBag[iTileCandidate].Bank = TileBag[iTileCandidate].Bank - 1;
   TileSite.setIcon(PRFetchResource.FR(TileCandidate.SImgIndex()));
   TileSite.setName( TileCandidate.SImgIndex());
   TileSite.setVisible(true);
   TileCount = TileCount - 1;
   Utilities.LogIt( " Tile Placed: " + PRGDResourceNStr[iTileCandidate]);
   break;
  }; 
 }; //Loop

 if ( (TileCount + DiscardCount) == 0 ) 
  rc = TilePickRC.TPRCTileBagEmpty;
 
 return rc;

};

public static void PlaceTiles() {
 if ( PRGDCurrentRole == SetupAvailableRoles.SettlerSAR ) 
  RoleCard.InfoCard("All players have completed the Settler phase. New plantation tiles will be drawn now.");
 
 for (int i = SetupChoiceSeat.FirstSCS.ordinal(); i <= PRGDActualNumPlayers; i++){ //Really looping thru' tiles not players
  TilePickRC Result = PlaceATileImage(PRPuertoRico.playermat.Image8[i]); // TODO:: do something with rc?
 };

};

public static void PlaceAPick(SetupPlayerNames sPlayer, ImageIdx WhichTile ) {

 int Player = sPlayer.ordinal();
 for (int i = 0; i < PRRMaxPlantations; i++){
  if ( PRFArray[Player].Image2[i].isVisible() ){} 
  else {
   if ( WhichTile == ImageIdx.QuarryEmpty ) {
    PRPuertoRico.playermat.Text1[QuarryBankIdx.ordinal()].setText(Integer.
	    toString(Integer.valueOf(PRPuertoRico.playermat.Text1[QuarryBankIdx.
	    ordinal()].getText()) - 1));
    if ( (Building.Production(PRGDCurrentPlayer, BuildingIdx.ConstructionHutIdx) > 0) ) 
     if ( sPlayer != PRGDPrivilegedPlayer ) 
      Utilities.BldgAnnounce(BuildingIdx.ConstructionHutIdx,
	      "Your manned Construction Hut allowed you to choose a quarry");
   }
   else 
       PRPuertoRico.playermat.Image8[CurrentTilePick.ordinal()].setVisible(false);

   CurrentTilePick = TilePickIdx.NullTPI;

   //could check for settler's privilege and construction hut here
   PRFArray[Player].Image2[i].setIcon(PRFetchResource.FR(WhichTile.SImgIndex()));
  //hospice
   PRFArray[Player].Image2[i].setName(WhichTile.SImgIndex());
   PRFArray[Player].Image2[i].setVisible(true);
   DealWithHospice(PRFArray[Player].Image2[i]);
   break;
  };
 }; 

};

public static TilePickRC TilePick(SetupPlayerNames sPlayer, TilePickIdx PickIdx ){
 int Player = sPlayer.ordinal();
 ImageIdx  TileCandidate;
// int  NextFreePlantation;

 if ( PickIdx == TilePickIdx.NullTPI )  //Only the AI will cause this when no tiles are present
  return TilePickRC.TPRCTileBagEmpty;

 if ( PRFArray[Player].Image2[PRRMaxPlantations - 1].isVisible() ) {
  return TilePickRC.TPRCPlantationsFull; //Player//s last plantation has already been planted.
//  Utilities.LogIt( PRGDPlayerNameStrings(Player) + "//s Plantations are Full"
 };

 TileCandidate = ImageIdx.Lookup(Integer.valueOf(PRPuertoRico.playermat.
	 Image8[PickIdx.ordinal()].getName()));

 if ( TileCandidate == ImageIdx.QuarryEmpty ) 
  if ( (sPlayer == PRGDPrivilegedPlayer) | (Building.Production(PRGDCurrentPlayer,
  BuildingIdx.ConstructionHutIdx) > 0) )
   //could check for settler's privilege and construction hut here
   if ( Integer.valueOf(PRPuertoRico.playermat.Text1[QuarryBankIdx.ordinal()].getText()) > 0 )
//    PRPuertoRico.playermat.Text1[QuarryBankIdx.ordinal()].setText(Integer.toString(Integer.
//	    valueOf(PRPuertoRico.playermat.Text1[QuarryBankIdx.ordinal()].getText()) - 1)}
	  return TilePickRC.TPRCOK;
   else
    //no more quarries!
    return TilePickRC.TPRCTryAgain;
//  }
  else
   return TilePickRC.TPRCTryAgain;
  
 CurrentTilePick = PickIdx;
 return TilePickRC.TPRCOK;
// else
//  PRPuertoRico.playermat.Image8(PickIdx).setVisible(true); = false
// };

// for (i = 0 To PRRMaxPlantations - 1
//  if ( PRFArray[Player).Image2(i).setVisible(true); ) {
//  else
//   PRFArray[Player).Image2(i).Picture = LoadResPicture(TileCandidate, vbResBitmap)
//  //hospice
//   PRFArray[Player).Image2(i).setName( = TileCandidate
//   PRFArray[Player).Image2(i).setVisible(true); = true
//   DealWithHospice PRFArray[Player).Image2(i)
//   break;
//  };
// Next

// ConfirmCard TileCandidate, "Is this the plantation you want to choose?"
// InfoCard TileCandidate, PRGDPlayerNameStrings(PRGDCurrentPlayer) + " Picked " + PRGDResourceNStr((TileCandidate - ImageIdx.IndigoEmpty) \ 2)
// Utilities.LogIt( PRGDPlayerNameStrings(PRGDCurrentPlayer) + " Picked " + PRGDResourceNStr((TileCandidate - ImageIdx.IndigoEmpty) \ 2)
};

public static void ConfirmCard(ImageIdx WhichTile , String LogString ) {

 PRInformation.Image1.setName( WhichTile.SImgIndex());
 PRInformation.Image1.setIcon(PRFetchResource.FR(WhichTile.SImgIndex()));
 PRInformation.Label1.setText(LogString);
// PRInformation.CommandButton1.setVisible(false);
// PRInformation.CommandButton2.setVisible(true);
// PRInformation.CommandButton3.setVisible(true);
 PRInformation.Image1.setSize(TileSize, TileSize);
// PRInformation.Zorder;
// PRInformation.setModalityType(ModalityType.APPLICATION_MODAL);
// PRInformation.setVisible(true);
 if (MsgBox.Confirm(PRInformation.Panel)){
   PlaceAPick(PRGDCurrentPlayer, WhichTile);
   Utilities.LogIt( PRGDPlayerNameStrings[PRGDCurrentPlayer.ordinal()]
	   + " Picked " + PRGDResourceNStr[(WhichTile.ordinal()
	   - ImageIdx.IndigoEmpty.ordinal()) / 2]);
   Tile.InfoCard(WhichTile, PRGDPlayerNameStrings[PRGDCurrentPlayer.ordinal()]
                  + " Picked " + PRGDResourceNStr[(WhichTile.ordinal() - ImageIdx.IndigoEmpty.ordinal()) / 2]);
   PRPuertoRico.GoGoGo();
 }

};

public static void InfoCard(ImageIdx WhichTile , String LogString ) {

 PRInformation.Image1.setIcon(PRFetchResource.FR(WhichTile.SImgIndex()));
 PRInformation.Label1.setText( LogString );
// PRInformation.CommandButton1.setVisible(true);
// PRInformation.CommandButton2.setVisible(false);
// PRInformation.CommandButton3.setVisible(false);
 PRInformation.Image1.setSize( TileSize, TileSize) ; // vb6 1000
// PRInformation.Zorder;
// PRInformation.setModalityType(ModalityType.APPLICATION_MODAL);
// PRInformation.setVisible(true);
 MsgBox.Inform(PRInformation.Panel);
};

public static void Populate(SetupPlayerNames Player, ResourceIdx WhichPltnType, int Count ) {
 for (int iP = 0; iP < PRRMaxPlantations; iP++){
  int rawPlantation;

  if (Count == 0)
   return;

  rawPlantation = Integer.valueOf(PRFArray[Player.ordinal()].Image2[iP].getName());

  // Is Tile visible, the correct type and not already occupied?
  if ( PRFArray[Player.ordinal()].Image2[iP].isVisible() &
      ( ImagetoResource( ImageIdx.Lookup(rawPlantation)) == WhichPltnType) &
      ((rawPlantation % 2) == 0)) {
   Twiddle(iP);
   Count--;
  };
 }; 
};

static void FillUpAllTileType(SetupPlayerNames sPlayer, ResourceIdx WhichPltnType ){
 int Player = sPlayer.ordinal();
 int  NumTiles;
 int NumSJ = Integer.valueOf(PRFArray[Player].Text1[SanJuanIdx.ordinal()].getText());

 if ( NumSJ > 0 ) {
  NumTiles = PlantationCount(sPlayer, ImageIdx.values()[WhichPltnType.ordinal() * 2]);
  if ( NumTiles > 0 ) 
   if ( NumTiles > NumSJ)
    NumTiles = NumSJ;
   Populate(sPlayer, WhichPltnType, NumTiles);
 };
};

public static void Twiddle(int PlantationIdx ) {
 int  WhichPlantation = Integer.valueOf(PRFArray[PRGDCurrentPlayer.ordinal()].Image2[PlantationIdx].getName());

 if ( (WhichPlantation % 2) == 0)  //0 = odd or empty -- vb6 had 1 = odd or empty
  if ( Integer.valueOf(PRFArray[PRGDCurrentPlayer.ordinal()].Text1[SanJuanIdx.ordinal()].getText()) > 0 ) {
   WhichPlantation++;
   PRFArray[PRGDCurrentPlayer.ordinal()].Text1[SanJuanIdx.ordinal()].setText(Integer.
	   toString(Integer.valueOf(PRFArray[PRGDCurrentPlayer.ordinal()].
	   Text1[SanJuanIdx.ordinal()].getText()) - 1)); }
  else
   return;
 else {
  WhichPlantation--;
  PRFArray[PRGDCurrentPlayer.ordinal()].Text1[SanJuanIdx.ordinal()].setText(Integer.
	  toString(Integer.valueOf(PRFArray[PRGDCurrentPlayer.ordinal()].
	  Text1[SanJuanIdx.ordinal()].getText()) + 1));
 };


 PRFArray[PRGDCurrentPlayer.ordinal()].Image2[PlantationIdx].setIcon(PRFetchResource.FR(
	 Integer.toString(WhichPlantation)));

 PRFArray[PRGDCurrentPlayer.ordinal()].Image2[PlantationIdx].setName(Integer.
	 toString(WhichPlantation));
};

public static int Count(){
 TileCount = 0;
 for (Modules.ResourceIdx i : EnumSet.range( Indigo , Coffee)){
  TileCount = TileCount + TileBag[i.ordinal()].Bank;
 }; 

 return TileCount;
};


public static void StartPlantation(Modules.ResourceIdx PlantationIdx ) {
 TileBag[PlantationIdx.ordinal()].Bank = TileBag[PlantationIdx.ordinal()].Bank - 1;
};

static int PlantationCount(SetupPlayerNames sPlayer, ImageIdx WhichPlantation ){
//counts up number of plantations with exact match to iPlayer passed in
 int Player = sPlayer.ordinal();
 int PlantationCount = 0;
 for (int i = 0; i < PRRMaxPlantations; i++){
  if ( PRFArray[Player].Image2[i].isVisible() ) 
   if ( Integer.valueOf(PRFArray[Player].Image2[i].getName()) == WhichPlantation.ImgIndex())
    PlantationCount++;
 }; 
 return PlantationCount;
};

static int PlantationTotal(SetupPlayerNames Player){
//counts up number of plantations
 int PlantationTotal = 0;
 for (int i = 0; i < PRRMaxPlantations; i++){
  if ( PRFArray[Player.ordinal()].Image2[i].isVisible() )
    PlantationTotal++;
 }; 
 return PlantationTotal;
};

static int PlantationWorkforce(SetupPlayerNames Player){
//counts up number of occupied plantations
 int PlantationWorkforce = 0;
 for (int i = 0; i < PRRMaxPlantations; i++){
  if ( PRFArray[Player.ordinal()].Image2[i].isVisible() )
   if ( (Integer.valueOf(PRFArray[Player.ordinal()].Image2[i].getName()) % 2) == 0 ) {} //true > 0 or odd or empty
   else //count up the full ones
    PlantationWorkforce++;
 }; 
 return PlantationWorkforce;
};

static void Evacuate(SetupPlayerNames sPlayer) {
 //AI uses this to move colonists from the land to San Juan so they can be re-distributed
 int Player = sPlayer.ordinal() ;
 int WhichPlantation;

 for (int i = 0; i < PRRMaxPlantations; i++){
  if ( PRFArray[Player].Image2[i].isVisible() ) {
   WhichPlantation = Integer.valueOf(PRFArray[Player].Image2[i].getName());
   if ( (WhichPlantation % 2) == 0 ){}  //true > 0 or odd or empty
   else {//count up the full ones
    WhichPlantation = WhichPlantation - 1;
    PRFArray[Player].Text1[SanJuanIdx.ordinal()].setText(Integer.toString(Integer.
	    valueOf(PRFArray[Player].Text1[SanJuanIdx.ordinal()].getText()) + 1));
    PRFArray[Player].Image2[i].setIcon(PRFetchResource.FR(Integer.toString(WhichPlantation)));
    PRFArray[Player].Image2[i].setName( Integer.toString(WhichPlantation));
   };
  };
 }; 

};

static boolean PlantationsToSettle(){
//See if there are any tiles left to pick
 for (int i = SetupChoiceSeat.FirstSCS.ordinal(); i <= PRGDActualNumPlayers; i++){
  if ( PRPuertoRico.playermat.Image8[i].isVisible() )
   return true;
 }; 

 if ( Integer.valueOf(PRPuertoRico.playermat.Text1[QuarryBankIdx.ordinal()].getText()) > 0 )
  return true;

 return false;
};

public static void DealWithHacienda(SetupPlayerNames sPlayer) {
 int Player = sPlayer.ordinal();
 int  NumPlantations = 0;
 boolean  UseHacienda = false;

 if ( TileCount > 0 ) {
  if ( Building.Production(sPlayer, BuildingIdx.HaciendaIdx) > 0 ) { //Hacienda code
   if ( PRGDAutoPlayer[Player] ) {
    UseHacienda = AutoPlayer.DealWithHacienda(sPlayer);
    if ( UseHacienda ) 
//     result = Inform.Inform(PRGDPlayerNameStrings(PRGDCurrentPlayer) + " Will Use the Hacienda")
     Utilities.BldgAnnounce(BuildingIdx.HaciendaIdx, PRGDPlayerNameStrings[Player] 
	     + " Will Use the Hacienda");}
   else 
    if (MsgBox.Inform(PRGDPlayerNameStrings[Player] + " Do You Want to Use the Hacienda?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION ) {
     Utilities.LogIt( PRGDPlayerNameStrings[Player] + " Uses Hacienda");
     UseHacienda = true; }
    else {
     Utilities.LogIt( PRGDPlayerNameStrings[Player] + " Does not use Hacienda");
     UseHacienda = false;
    };

   if ( UseHacienda ) {
    NumPlantations = Tile.PlantationTotal(sPlayer);
    if ( Tile.PlaceATileImage(PRFArray[Player].Image2[NumPlantations]) == TilePickRC.TPRCTileBagEmpty ) 
//    TODO:: InfoCard on either plantation from Hacienda or tilebag emptiness
     Utilities.EventAnnounce ( "Tilebag is Empty!");
    DealWithHospice(PRFArray[Player].Image2[NumPlantations]);
   };
  }; //No aye hacienda
 }; //tilebag already emptied

};

static void DealWithHospice(javax.swing.JLabel TileSite ) {
 ResourceIdx Good = ResourceIdx.NullRIdx;

 if ( Building.Production(PRGDCurrentPlayer, BuildingIdx.HospiceIdx) > 0 ) 
  if ( Integer.valueOf(PRPuertoRico.playermat.Text1[ColonistsRemainingIdx.ordinal()].getText()) > 0 ) {
   PRPuertoRico.playermat.Text1[ColonistsRemainingIdx.ordinal()].setText(Integer.
	   toString(Integer.valueOf(PRPuertoRico.playermat.Text1[ColonistsRemainingIdx.
	   ordinal()].getText()) - 1));
   TileSite.setName( Integer.toString(Integer.valueOf(TileSite.getName()) + 1));
   TileSite.setIcon(PRFetchResource.FR(TileSite.getName()));
//   result = Inform.Inform("Hospice Grants " + PRGDPlayerNameStrings(PRGDCurrentPlayer) + " a free Colonist")
//   Utilities.BldgAnnounce BuildingIdx.HospiceIdx, true, PRGDPlayerNameStrings(PRGDCurrentPlayer) + ", your Hospice has granted you a colonist for your " + PRGDResourceNStr(TileSite.setName( - ImageIdx.IndigoEmpty) + "."
    Good = ImagetoResource(ImageIdx.Lookup(Integer.valueOf(TileSite.getName())));
    Utilities.BldgAnnounce(BuildingIdx.HospiceIdx, PRGDPlayerNameStrings[PRGDCurrentPlayer.ordinal()]
	    + ", your Hospice has granted you a colonist for your " +
	    PRGDResourceNStr[Good.ordinal()]
	    + ".");
   return;
  };

};

/*
boolean DealWithWharf(){ 
 int ChosenBuilding;

  ChosenBuilding = Integer.valueOf(PRBuildingInfo.Label1[FBldgInfoIdx1.
	  NameFBII1.ordinal()].getName());
  PRInformation.Image1.setIcon(new javax.swing.ImageIcon(getClass().getResource(
	  "/resources/" + ImageIdx.WharfOccupied.SImgIndex() + ".jpg")));
  PRInformation.Label1.setText( "Are you sure you want to use your ImageIdx.Wharf to ship "
	  + LoadResString.ResSs[ChosenBuilding + PRCBldgTextRes]);
 PRInformation.CommandButton1.setVisible(false);
 PRInformation.CommandButton2.setVisible(true);
 PRInformation.CommandButton3.setVisible(true);
// PRInformation.Zorder
 PRInformation.setModalityType(ModalityType.APPLICATION_MODAL);
 PRInformation.setVisible(true);
};
*/

static NewTilePick RefineryCanProcess(SetupPlayerNames Player){
//AI uses this to find a tile to select based on their refinery capacity during Settler phase
 NewTilePick ntp = new NewTilePick();
 int  NumPlants;
 int  NumRefSlots;
 ImageIdx  CandidateResource;
 TilePickIdx  TileOk[] = new TilePickIdx[PRCNumGoods];

 ntp.Resource = NullRIdx;
 ntp.Tile = TilePickIdx.NullTPI;

 for (ResourceIdx iR : EnumSet.range( Indigo , Coffee)){
  TileOk[iR.ordinal()] = TilePickIdx.NullTPI;
 };

 for (int iPS = 0; iPS <= PRGDActualNumPlayers; iPS++){ //i.e. num players = max number of tile picks
  NumRefSlots = 0;
  if ( PRPuertoRico.playermat.Image8[iPS].isVisible() ) {
   switch (ImageIdx.Lookup(Integer.valueOf(PRPuertoRico.playermat.Image8[iPS].getName()))) {
   case IndigoEmpty:
     NumRefSlots = Building.RefineryCapacity(Player, BuildingIdx.IndigoPlantIdx) 
    + Building.RefineryCapacity(Player, BuildingIdx.IndigoPlantIdx);
    break;
   case SugarEmpty:
    NumRefSlots = Building.RefineryCapacity(Player, BuildingIdx.SugarMillIdx) 
    + Building.RefineryCapacity(Player, BuildingIdx.SugarMillIdx);
    break;
   case TobaccoEmpty:
    NumRefSlots = Building.RefineryCapacity(Player, BuildingIdx.TobaccoStorageIdx);
    break;
   case CoffeeEmpty:
    NumRefSlots = Building.RefineryCapacity(Player, BuildingIdx.CoffeeRoasterIdx);
   };

   if ( NumRefSlots > 0 ) {
    CandidateResource = ImageIdx.Lookup(Integer.valueOf(PRPuertoRico.playermat.
	    Image8[iPS].getName()));
    NumPlants = PlantationCount(Player, CandidateResource) + PlantationCount(Player, 
	    CandidateResource.Next()); //empty & manned
    if ( NumPlants < NumRefSlots ) 
     TileOk[(CandidateResource.compareTo(ImageIdx.IndigoEmpty)) / 2] = TilePickIdx.values()[iPS];
   };
  };
 }; 

 //Now Pick the most profitable
 for (int i = PRCNumGoods - 1; i >= 0; i--){
  if ( TileOk[AIGameData.AIGDPriorityGoodShip[i].ordinal()] != TilePickIdx.NullTPI ) {
   ntp.Tile = TileOk[AIGameData.AIGDPriorityGoodShip[i].ordinal()];
   ntp.Resource = ResourceIdx.values()[i];
/*	   TilePickIdx.values()[(Integer.valueOf(PRPuertoRico.playermat.Image8
	   [TileOk[AIGameData.AIGDPriorityGoodShip[i].ordinal()]].getName()) -
	   ImageIdx.IndigoEmpty.ImgIndex()) / 2]; */

   return ntp;
  };
 };
 return ntp;
};

static ResourceIdx ImagetoResource (ImageIdx iDex) {
 if ((iDex.compareTo(ImageIdx.IndigoEmpty)>= 0) & (iDex.compareTo(ImageIdx.QuarryOccupied) <= 0))
  switch (iDex)	{
	  case IndigoEmpty:
	  case IndigoColonised:
		  return ResourceIdx.Indigo;
	  case SugarEmpty:
	  case SugarColonised:
		  return ResourceIdx.Sugar;
	  case CornEmpty:
	  case CornColonised:
		  return ResourceIdx.Corn;
	  case TobaccoEmpty:
	  case TobaccoColonised:
		  return ResourceIdx.Tobacco;
	  case CoffeeEmpty:
	  case CoffeeColonised:
		  return ResourceIdx.Coffee;
	  case QuarryEmpty:
	  case QuarryOccupied:
		  return ResourceIdx.Quarry;

  }
 return ResourceIdx.NullRIdx;
}

public static void Initialise() {

 TileBag[Indigo.ordinal()] = new TileBagType(PRTileMax.PRTMIndigo.GM(),0);
 TileBag[Sugar.ordinal()] = new TileBagType(PRTileMax.PRTMSugar.GM(),0);
 TileBag[Corn.ordinal()] = new TileBagType(PRTileMax.PRTMCorn.GM(),0);
 TileBag[Tobacco.ordinal()] = new TileBagType(PRTileMax.PRTMTobacco.GM(),0);
 TileBag[Coffee.ordinal()] = new TileBagType(PRTileMax.PRTMCoffee.GM(),0);

};
}
