/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ClassModules;

import Modules.BuildingIdx;
import Modules.Constants;
import Modules.ImageIdx;
import Modules.MsgBox;
import Modules.PlayerAlertCode;
import Modules.ResourceIdx;
import static Modules.PRGameData.*;
import static Modules.ResourceIdx.*;
import Modules.SetupAvailableRoles;
import Modules.SetupChoiceSeat;
import Modules.SetupPlayerNames;
import Modules.Text1Idx;
import static Modules.Text1Idx.*;
import Modules.Utilities;
import java.awt.Component;
import java.awt.Cursor;
import java.util.EnumSet;
import javax.swing.JTextField;

/**
 *
 * @author steve
 */
public class Barrel {

private static boolean ProductionGrid[] = new boolean[Constants.PRCNumGoods];
private static ResourceIdx CraftmansChoice[];

public static int LeftOverTypesBarrels(SetupPlayerNames Player  ) {
// returns number of types of barrels player has left
int LeftOverTypesBarrels = 0;

for (ResourceIdx BarrelType: EnumSet.range(ResourceIdx.Indigo, ResourceIdx.Coffee)) {
  int numbarrels = Integer.valueOf(PRFArray[Player.ordinal()].Text1[BarrelType.ordinal() +
          Text1Idx.IndigoIdx.ordinal()].getText());
  if ( numbarrels > 0) {
    LeftOverTypesBarrels++;
  };
 };
  return LeftOverTypesBarrels;
};

public static int[] CheckProduction(SetupPlayerNames OnPlayer) {
//Check how much production a Player has
int ResourceArray[] = new int[Constants.PRCNumGoods];
int NumPlant= 0;
int NumBank = 0;
int NumBldg = 0;
int NumGoods = 0;
boolean CheckProduction = false;

 for (ResourceIdx OnGood: EnumSet.range(ResourceIdx.Indigo, ResourceIdx.Coffee)) {
  NumPlant = Tile.PlantationCount(OnPlayer, ImageIdx.values()[ImageIdx.IndigoColonised.
	  ordinal() + OnGood.ordinal() * 2]);
  NumBank = Integer.valueOf(PRPuertoRico.playermat.
         Text1[Text1Idx.IndigoBankIdx.ordinal() + OnGood.ordinal()].getText());

  switch ( OnGood ) {
      case Indigo:
       NumBldg = Building.Production(OnPlayer, BuildingIdx.SmallIndigoPlantIdx);
       NumBldg = NumBldg + Building.Production(OnPlayer, BuildingIdx.IndigoPlantIdx);
       break;
      case Sugar:
       NumBldg = Building.Production(OnPlayer, BuildingIdx.SmallSugarMillIdx);
       NumBldg = NumBldg + Building.Production(OnPlayer, BuildingIdx.SugarMillIdx);
       break;
      case Corn:
       NumBldg = NumPlant;
       break;
      case Tobacco:
       NumBldg = Building.Production(OnPlayer, BuildingIdx.TobaccoStorageIdx);
       break;
      case Coffee:
       NumBldg = Building.Production(OnPlayer, BuildingIdx.CoffeeRoasterIdx);
  };

  if ( NumBldg > NumPlant )
   NumGoods = NumPlant;
  else
   NumGoods = NumBldg;
  

  if ( NumGoods > NumBank ){
   NumGoods = NumBank;
  };

  ResourceArray[OnGood.ordinal()] = NumGoods;

  if ( NumGoods > 0) {
   CheckProduction = true;
  };

 };
 if (CheckProduction)
     return ResourceArray;
 else
     return null;
};

public static void Produce() {
SetupPlayerNames OnPlayer ;
SetupChoiceSeat OnSeat = PRGDCurrentSeat;
//ResourceIdx OnGood ;
int NumKindGoods ;
int GoodsProduced[] = new int[Constants.PRCNumGoods] ;
int iOnSeat;
int numplayers = PRGDActualNumPlayers;
int Player;
boolean bResult = false;

iOnSeat = OnSeat.ordinal();

for ( int i = 0; i < PRGDActualNumPlayers; i++) {

 OnSeat = SetupChoiceSeat.values()[iOnSeat]; // TODO:: reverse ENUM lookup not good

 OnPlayer = PRGDSeatCentric[iOnSeat].WhichPlayer;
 Player = OnPlayer.ordinal();
 NumKindGoods = 0;

 if ( OnPlayer == PRGDPrivilegedPlayer)  // Move Craftsmans label to privileged player
  PRProductionGrid.setCraftsmanLabel(Player);
 
 GoodsProduced = CheckProduction(OnPlayer);
 if ( GoodsProduced != null) {
  Utilities.LogIt( (PRGDPlayerNameStrings[Player] +
      " Produces: I:" + GoodsProduced[ResourceIdx.Indigo.ordinal()] + " S:"
        + GoodsProduced[ResourceIdx.Sugar.ordinal()] + " C:"
        + GoodsProduced[ResourceIdx.Corn.ordinal()]) + " T:"
        + GoodsProduced[ResourceIdx.Tobacco.ordinal()] + " F:"
        + GoodsProduced[ResourceIdx.Coffee.ordinal()]);

  for (ResourceIdx OnGood: EnumSet.range(ResourceIdx.Indigo, ResourceIdx.
          Coffee)) {

   if ( GoodsProduced[OnGood.ordinal()] > 0) {
    int locGoodBank;
    int locGoodPlayer;

    NumKindGoods++;

    // Update playermat including AI mats
    locGoodBank = Integer.valueOf(PRPuertoRico.playermat.
            Text1[Text1Idx.IndigoBankIdx.ordinal() + OnGood.ordinal()].getText());
    locGoodPlayer = Integer.valueOf(PRFArray[Player]
            .Text1[Text1Idx.IndigoIdx.ordinal() + OnGood.ordinal()].getText());

    locGoodBank = locGoodBank - GoodsProduced[OnGood.ordinal()];
    locGoodPlayer = locGoodPlayer + GoodsProduced[OnGood.ordinal()];

    PRPuertoRico.playermat.
            Text1[Text1Idx.IndigoBankIdx.ordinal() + OnGood.ordinal()].
            setText(Integer.toString(locGoodBank));
    PRFArray[Player]
            .Text1[Text1Idx.IndigoIdx.ordinal() + OnGood.ordinal()].
            setText(Integer.toString(locGoodPlayer));

    if ( OnPlayer == PRGDPrivilegedPlayer) 
     ProductionGrid[OnGood.ordinal()] = true;

    // Update production grid
    int iP = 0; // iP is the integer counter of the JTextFields
    for (Component p :  PRProductionGrid.jpanels[Player].getComponents()) {
     if (p instanceof javax.swing.JTextField) {
      // Found a resource field, but must wait for the right good
      if (iP == OnGood.ordinal()) {
       javax.swing.JTextField jtf = (javax.swing.JTextField)p;
          // loop over each JTextField
//      ((JTextField)p).setText(Integer.toString(GoodsProduced[iP]));
       jtf.setText(Integer.toString(GoodsProduced[iP]));
      };
      iP++;
     }
    } 
  
  } // if GoodsProduced...

 // TODO:: Come back & fix this!
 //   for ( Each Element In FProductionGrid)
 //    if ( Left(Element.Name, 4) = "Text" {
 //     ResourceOffset = Right(Left(Element.Name, 5), 1) - 1;
 //     if ( ResourceOffset == OnGood {
 //      if ( Element.Index == OnSeat {
 //       Element.Text = GoodsProduced(OnGood);
 //       break;
 //      };
 //     };
 //    };
  

   

  }; //Loop over each type of Goods
 }// if GoodsProduced != null...
 else // GoodsProduced are null...
  Utilities.LogIt( PRGDPlayerNameStrings[Player]
          + " Produces: I:0 S:0 C:0 T:0 F:0");

 if ( NumKindGoods > 1 ) {
  if ( Building.Production(OnPlayer, BuildingIdx.FactoryIdx) > 0 ) { //Factory code
   if ( NumKindGoods == Constants.PRCNumGoods ) 
    NumKindGoods = Constants.PRCNumGoods + 1; //You get 5 not 4 Doubloons for making all 5 goods
   
   PRFArray[Player].Text1[Text1Idx.DoubloonsIdx.ordinal()].
           setText( Integer.toString(Integer.valueOf(PRFArray[Player].
           Text1[Text1Idx.DoubloonsIdx.ordinal()].getText())
           + NumKindGoods - 1));
//   result = MsgBox("Factory Grants " + PRGDPlayerNameStrings(OnPlayer) + ": " + CStr(NumKindGoods - 1) + " Doubloon(s)")
   Utilities.BldgAnnounce (BuildingIdx.FactoryIdx, "Factory Grants "
           + PRGDPlayerNameStrings[Player] + ": "
           + Integer.toString(NumKindGoods - 1) + " Doubloon(s)");
  }; // Player has a factory
 }; // NumKindGoods > 1

 iOnSeat = (PRGDCurrentSeat.ordinal() + i + 1) % numplayers;

 }; //player

if ( PRGDCurrentRole == SetupAvailableRoles.CraftsmanSAR ) {
 PRProductionGrid.setCraftsmanLabel(PRGDPrivilegedPlayer.ordinal());
// PRProductionGrid.setVisible(true);
 MsgBox.Inform(PRProductionGrid.Panel);

 if ( Barrel.FreePick() ) {
  //Set up cursor
  for (Modules.Text1Idx i : EnumSet.range( IndigoBankIdx , CoffeeBankIdx)){
   PRPuertoRico.playermat.Text1[i.ordinal()].setCursor(new Cursor(Cursor.HAND_CURSOR)); // custom 99
  };
//  PRFArray[PRGDPrivilegedPlayer].Zorder;
  bResult =  PRPuertoRico.PlayerAlert(PRGDPrivilegedPlayer, PlayerAlertCode.
             PickProductionPrivPAC, " May Choose a Privilege Good");
  }
  else {
   Utilities.EventAnnounce ( " No Privilege Good for (" + PRGDPlayerNameStrings[PRGDPrivilegedPlayer.ordinal()] + " to Choose");
   PRPuertoRico.GoGoGo();
//  PRPuertoRico.playermat.Zorder;
  };
};
};

public static void InitProdGrid(SetupChoiceSeat MaxSeatIdx  ) {
 // Proc is intended to setup player names and make (in)visible production arrays
    int ResourceOffset ;
 JTextField ProdGridBox[]= new JTextField[Constants.PRCNumResources] ;

 if (MaxSeatIdx.ordinal() < SetupChoiceSeat.FifthSCS.ordinal())

 // Make sure non-playing panels are invisible.
 for (int i = PRGDActualNumPlayers; i < Constants.MaxPlayers; i++ ) {
  PRProductionGrid.jpanels[i].setVisible(false);
};

 // For each seat in the game:
 // Set player name by seat, reset goods values to 0, and make panels visible
 for (SetupChoiceSeat SeatIdx: EnumSet.range(SetupChoiceSeat.FirstSCS, MaxSeatIdx))
 {
  for (Component p :  PRProductionGrid.jpanels[SeatIdx.ordinal()].getComponents()) {
    String x = PRGDPlayerNameStrings [0];
    if (p instanceof javax.swing.JLabel) {
     javax.swing.JLabel t = (javax.swing.JLabel) p;
      t.setText(PRGDPlayerNameStrings[
              PRGDSeatCentric[SeatIdx.ordinal()].WhichPlayer.ordinal()]);
    }
     else
        if (p instanceof javax.swing.JPanel) {
         javax.swing.JPanel t = (javax.swing.JPanel) p;
         t.setVisible(true);
          // loop over each JPanel
         for (Component p2 : t.getComponents()) {
           if (p2 instanceof javax.swing.JTextField) {
              javax.swing.JTextField t2 = (javax.swing.JTextField) p2;
              t2.setText("0");
           } // if p2...
         } // for component p2
      } // if p instanceof
   }; // for Component
 }; // For seat

};

public static void ResetGrid() {

 for (SetupChoiceSeat SeatIdx: EnumSet.range(SetupChoiceSeat.FirstSCS, SetupChoiceSeat.FifthSCS))
  for (Component p :  PRProductionGrid.jpanels[SeatIdx.ordinal()].getComponents())
   if (p instanceof javax.swing.JTextField) {
       javax.swing.JTextField t = (javax.swing.JTextField) p;
       t.setText("0");
    }

};

public static boolean FreePick() {
 for (ResourceIdx iR: EnumSet.range(ResourceIdx.Indigo, ResourceIdx.Coffee))
  if  ( ProductionGrid[iR.ordinal()] & ( Integer.valueOf(PRPuertoRico.playermat.Text1[Text1Idx
          .IndigoBankIdx.ordinal() + iR.ordinal()].getText()) > 0))
   return true; 
 return false;
};

public static boolean PickABarrel(ResourceIdx CandidateBarrel  ) {

 boolean PickABarrel = false;

 if  ( ProductionGrid[CandidateBarrel.ordinal()] )
 {
  int locbank = Integer.valueOf(PRPuertoRico.playermat.Text1[Text1Idx.
           IndigoBankIdx.ordinal() + CandidateBarrel.ordinal()].getText());

  if ( locbank > 0)
  {
   int locplayer = Integer.valueOf(PRFArray[PRGDCurrentPlayer.
           ordinal()].Text1[Text1Idx.IndigoIdx.ordinal() + CandidateBarrel.
           ordinal()].getText());

   PRPuertoRico.playermat.Text1[Text1Idx.IndigoBankIdx.ordinal()
           + CandidateBarrel.ordinal()].setText(Integer.toString(locbank - 1));
   PRFArray[PRGDCurrentPlayer.ordinal()].Text1[Text1Idx.
           IndigoIdx.ordinal() + CandidateBarrel.ordinal()].setText(Integer.
           toString(locplayer +1));

   PickABarrel = true;
   Utilities.EventAnnounce(PRGDPlayerNameStrings[
           PRGDCurrentPlayer.ordinal()] + " has chosen privilege good: "
                           + PRGDResourceNStr[CandidateBarrel.ordinal()]);
   ResetGrid();
  
  };
 };
 return PickABarrel;
};

public static void Initialise() {
 InitProdGrid ( SetupChoiceSeat.values()[PRGDActualNumPlayers-1]);  // TODO:: revise reverse ENUM
 CraftmansChoice = new ResourceIdx[PRGDActualNumPlayers];

 for ( int i = SetupPlayerNames.HumanPlayerSPN.ordinal() ;
      i < PRGDActualNumPlayers; i++)
     CraftmansChoice[i] = ResourceIdx.NullRIdx;

 PRGDResourceNStr[ResourceIdx.Indigo.ordinal()] = "Indigo";
 PRGDResourceNStr[ResourceIdx.Sugar.ordinal()] = "Sugar";
 PRGDResourceNStr[ResourceIdx.Corn.ordinal()] = "Corn";
 PRGDResourceNStr[ResourceIdx.Coffee.ordinal()] = "Coffee";
 PRGDResourceNStr[ResourceIdx.Tobacco.ordinal()] = "Tobacco";
 PRGDResourceNStr[ResourceIdx.Quarry.ordinal()] = "Quarry";

};
};

