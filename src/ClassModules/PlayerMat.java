/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ClassModules;

import static Modules.PRGameData.*;
import static Modules.Text1Idx.*;
import Modules.MsgBox;
import Modules.ResourceIdx;
import Modules.PlayerAlertCode;
import Modules.SetupAvailableRoles;
import Modules.SetupPlayerNames;
import Modules.ShipIdx;
import Modules.ShipTypes;
import Modules.Text1Idx;
import Modules.Utilities;
import java.awt.Point;

/**
 *
 * @author steve
 */
public class PlayerMat {

static boolean TradeClicked = false;

 public static void PlantationClick(java.awt.event.MouseEvent evt,int Offset) { // offset accounts for different form layouts
  int WhichButton = Utilities.getControlIdx(evt) - Offset; //Image1 is the backdrop so -1 skips
  Point x = new Point(0,0); // Mouse Coords not needed for Plantation twiddles
      MayorClick(Utilities.GetPlayerFromJLabel(evt), true, WhichButton, x); // Building Slot or Plantation ImageIdx
 };

 public static void BuildingClick(java.awt.event.MouseEvent evt) {
  int WhichButton = Utilities.getControlIdx(evt);
  Point MouseLocn = evt.getPoint();
      MayorClick(Utilities.GetPlayerFromJLabel(evt), false, WhichButton, MouseLocn); // Building Slot or Plantation ImageIdx
 };

public static void Text1Picked (java.awt.event.MouseEvent evt) {
 Text1Idx TextPicked = Text1Idx.values()[Utilities.getControlIdx(evt) + IndigoIdx.ordinal()];
 if ( PRGDGameOn ) {} //Don't allow selections if no game has been started!
 else
  return;

 CaptainTraderText1Click(Utilities.GetPlayerFromJTextField(evt), TextPicked);

};

static void MayorClick (SetupPlayerNames WhichPlayer, boolean TileNotBuilding ,
                    int WhichTileBuilding, Point mouselocn ) {
 if ( PRGDCurrentPlayer == WhichPlayer ){}
 else {
  MsgBox.Inform("Not your turn!");
  return;
 };

// if ( PRPuertoRico.playermat.Command2.setVisible(true); ) {
// if ( PRInformation.isVisible() )
//  return;
 

 if ( PRGDCurrentRole == SetupAvailableRoles.MayorSAR ) {
  if ( TileNotBuilding ) 
   Tile.Twiddle( WhichTileBuilding);
  else
   Building.Twiddle(WhichTileBuilding, mouselocn);
  
  if ( Integer.valueOf(PRFArray[WhichPlayer.ordinal()].Text1[SanJuanIdx.ordinal()]
	  .getText()) > 0 )
   if ( Building.VacancyCount(WhichPlayer) > 0 ) {
    PRFArray[WhichPlayer.ordinal()].Command1.setVisible(false);
    return;
   };
  
  PRFArray[WhichPlayer.ordinal()].Command1.setVisible(true); }
 else
  MsgBox.Inform("Not Mayor Phase!");
 

};

public static void CaptainTraderText1Click(SetupPlayerNames WhichPlayer , Text1Idx TextPicked ) {
 ResourceIdx  CandidateBarrel;
 ShipIdx  WhichShip;
 boolean Result = false;

 if ( PRFArray[PRGDCurrentPlayer.ordinal()].iPlayer == WhichPlayer.ordinal()) {}
 else
  return;
 
if (TradeClicked)
 return;

 if ( (TextPicked.compareTo(IndigoIdx) >= 0) & (TextPicked.compareTo(  CoffeeIdx) <= 0  ) ) {
  if ( Integer.valueOf(PRFArray[WhichPlayer.ordinal()].Text1[TextPicked.ordinal()].getText()) > 0 ) {
   CandidateBarrel = Modules.ResourceIdx.values()[TextPicked.ordinal() - IndigoIdx.ordinal()];
   if ( PRGDCurrentRole == SetupAvailableRoles.TraderSAR ) {//Trade Pick in Trader Phase
    if ( TradingHouse.Sell(CandidateBarrel) ) {
     PRFArray[WhichPlayer.ordinal()].Command1.setVisible(false);
     PRPuertoRico.GoGoGo();
    }
   }
   else {
    if ( PRGDCurrentRole == SetupAvailableRoles.CaptainSAR ) {
     ShipIdx CandidateShip = CargoShip.ShipForThisGood(CandidateBarrel);
     if (CandidateShip == ShipIdx.NotAShipSlot)
       return;
     CargoShip.SetCandidateBarrel(CandidateBarrel);
     if (CargoShip.CheckForWharf()) {
       PRPuertoRico.playermat.Picture1[ShipTypes.PlayerWharf.ordinal()].setVisible(true);
       Result = PRPuertoRico.PlayerAlert(WhichPlayer, PlayerAlertCode.PickAShipPAC, " Pick a Ship");
     }
     else {
       PRPuertoRico.playermat.Picture1[ShipTypes.PlayerWharf.ordinal()].setVisible(false);
      if (CargoShip.IsShipEmpty(CandidateShip))
       Result = PRPuertoRico.PlayerAlert(WhichPlayer, PlayerAlertCode.PickAShipPAC, " Pick a Ship");
      else {
       CargoShip.LoadCargo(CandidateShip);
       PRPuertoRico.GoGoGo();
      }
     }
     
    }; //Captain
   }; // Trader/Captain
  }; //resource > 0
 }; //Is a playermat resource
//   for (i = IndigoIdx To CoffeeIdx
//    FPRAIPlayer(PRGDCurrentPlayer).Text1[i).MousePointer = 12 // No Drop
//   Next



};



//-------------------------------------
// Old inactive code below here
//-------------------------------------
//void PickATile()
//AIs use this to pick a tile for the settler phase
// Dim Choice String
// Dim TilePick ResourceIdx

// TilePick = PRRNull

// Choice = ""

// Do While Choice = ""
//  Choice = LTrim(Lcase(InputBox("pick a Plantation for " + 
//        PRFArray[PRGDCurrentPlayer).setText(, 
//        "AI Helper", "")))
//  switch ( Left(Choice, 1)
//  case "i"
//   TilePick = PRRIndigo
//  case "s"
//   TilePick = PRRSugar
//  case "c"
//   if ( Right(Left(Choice, 3), 1) = "f" ) { //coffee
//    TilePick = PRRCoffee
//   else //corn
//    TilePick = PRRCorn
//   };
//  case "t"
//   TilePick = PRRTobacco
//  case "q"
//   TilePick = PRRQuarry
//  case ""
//  case else
//   Choice = ""
//  };
// }; //Loop

 //for (i = SetupChoiceSeat.FirstSCS To PRGDActualNumPlayers //Infer which tile was picked
 // if ( PRPuertoRico.playermat.Image8(i).setVisible(true); ) {
 //  if ( PRPuertoRico.playermat.Image8(i).setName( = (TilePick * 2 + ImageIdx.IndigoEmpty) ) {
 //   result = PRTile.TilePick(PRGDCurrentPlayer, i)
 //  break;
//   };
//  };
// Next

//};

//void ManageColonists()
////AIs use this to manage their colonists during the Mayor phase
// Dim ManageType String
// Dim PlantationChosen boolean
// Dim Pick int

// ManageType = ""

// PRFArray[PRGDCurrentPlayer).Z|der //bring AI form to the front so I can work on it.

// Do While ManageType = ""
//  ManageType = InputBox(PRGDPlayerNameStrings(PRGDCurrentPlayer), 
//               " Twiddle a (b)uilding or (p)lantation?", "AI Helper", "0")
//  ManageType = Lcase(Left(LTrim(ManageType), 1))
//  if ( ManageType = "b" | ManageType = "p" ) {
//   PlantationChosen = (ManageType = "p")
//   Pick = LTrim(InputBox("Twiddle a Colonist for " + 
//        PRGDPlayerNameStrings(PRGDCurrentPlayer), 
//        "AI Helper", "0"))
//
//   MayorClick PRFArray[PRGDCurrentPlayer).setName(, PlantationChosen, 
//                       Pick
//   if ( Inform.Inform("Finished?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION ) {
//    Exit Do
//   else
//    ManageType = ""
//   };
//  };
// }; //Loop

//};

//void PickABuilding()
////AIs use this to pick a building (or not)
// Dim Choice String
// Dim BldgPick BuildingIdx

// Choice = ""

// Do While Choice = ""
//  Choice = LTrim(Lcase(InputBox("pick a Building for " + 
//        PRGDPlayerNameStrings(PRGDCurrentPlayer), 
//        "AI Helper", "")))
//  switch ( Left(Choice, 1)

//  case "c"
//   if ( Right(Left(Choice, 2), 1) = "o" ) {
//    if ( Right(Left(Choice, 3), 1) = "f" ) { //coffee
//     BldgPick = BuildingIdx.CoffeeRoasterIdx
//    else
//     BldgPick = BuildingIdx.ConstructionHutIdx
//    };
//   else
//    if ( Right(Left(Choice, 2), 1) = "i" ) {
//     BldgPick = BuildingIdx.CityHallIdx
//    else
//     BldgPick = BuildingIdx.CustomsHouseIdx
//    };
//   };

//  case "f"
//   if ( Right(Left(Choice, 2), 1) = "o" ) {
//    BldgPick = BuildingIdx.FortressIdx
//   else
//    BldgPick = BuildingIdx.FactoryIdx
//   };

//  case "g"
//   BldgPick = BuildingIdx.GuildHallIdx

//  case "h"
//   if ( Right(Left(Choice, 2), 1) = "a" ) {
//    if ( Right(Left(Choice, 3), 1) = "c" ) {
//     BldgPick = BuildingIdx.HaciendaIdx
//    else
//     BldgPick = BuildingIdx.HarbourIdx
//    };
//   else
//    BldgPick = BuildingIdx.HospiceIdx
//   };

//  case "i"
//   BldgPick = BuildingIdx.IndigoPlantIdx

//  case "l"
//   if ( Right(Left(Choice, 6), 1) = "m" ) {
//    BldgPick = BuildingIdx.LargeMarketIdx
//   else
//    BldgPick = BuildingIdx.LargeWarehouseIdx
//   };

//  case "o"
//   BldgPick = BuildingIdx.OfficeIdx

//  case "r"
//   BldgPick = BuildingIdx.ResidenceIdx

//  case "s"
//   if ( Right(Left(Choice, 2), 1) = "m" ) {
//    if ( Right(Left(Choice, 6), 1) = "i" ) {
//     BldgPick = BuildingIdx.SmallBuildingIdx.IndigoPlantIdx
//    else
//     if ( Right(Left(Choice, 6), 1) = "s" ) {
//      BldgPick = BuildingIdx.SmallBuildingIdx.SugarMillIdx
//     else
//      if ( Right(Left(Choice, 6), 1) = "m" ) {
//       BldgPick = BuildingIdx.SmallMarketIdx
//      else
//       BldgPick = BuildingIdx.SmallWarehouseIdx
//      };
//     };
//    };
//   else
//    BldgPick = BuildingIdx.SugarMillIdx
//   };

//  case "t"
//   BldgPick = BuildingIdx.TobaccoStorageIdx

//  case "u"
//   BldgPick = BuildingIdx.UniversityIdx

//  case "w"
//   BldgPick = BuildingIdx.WharfIdx

//  case ""
//  case else
//   Choice = ""
//  };

//  if ( Choice = "" ) {
//  else
//   if ( PRBuilding.Purchase(BldgPick) ) {
//    Exit Do
//   else
//    if ( Inform.Inform("Finished?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION ) {
//     Exit Do
//    else
//     Choice = ""
//    };
//   };
//  };
// }; //Loop


//};
//void PickABarrel()
////AIs use this for Craftsman//s privilege
//};

//void ShipSomeBarrels(CandidateBarrel ResourceIdx, SelectShip ShipTypes)
////AIs use this to select a commodity to ship and,
////if necessary, a ship to ship it.
//};

//Function TradeABarrel() ResourceIdx
////AIs use this to select a barrel for trade (or not)
//};


}
