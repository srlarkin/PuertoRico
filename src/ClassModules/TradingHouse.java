/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ClassModules;

import Modules.AIGameData;
import Modules.BuildingIdx;
import Modules.ImageIdx;
import Modules.PRGoodsValue;
import Modules.ResourceIdx;
import Modules.SetupPlayerNames;
import Modules.Utilities;
import java.util.EnumSet;
import static Modules.Constants.*;
import static Modules.PRGameData.*;
import static Modules.Text1Idx.*;
import static Modules.ResourceIdx.*;

/**
 *
 * @author steve
 */
public class TradingHouse {
private static int ResourceValues[] = new int [PRCMaxResourceIdx+1];
private static int NextTHSlot = 0;

public static void Flush() {
 int BarrelType;

 if ( NextTHSlot == PRCMaxTradeGoods ) {
  for (int i = 0; i < PRCMaxTradeGoods; i++){
   BarrelType = Integer.valueOf(PRPuertoRico.playermat.Image7[i].getName());
   PRPuertoRico.playermat.Text1[IndigoBankIdx.ordinal() + BarrelType].setText(Integer.toString(Integer.valueOf(
                                PRPuertoRico.playermat.Text1[IndigoBankIdx.ordinal() + BarrelType].getText()) + 1));
  }; 
  ResetHouse();
 };

};

public static boolean CanITrade(SetupPlayerNames Player ){
 if (FullHouse())
  return false;

 for (ResourceIdx iR: EnumSet.range(ResourceIdx.Indigo, ResourceIdx.Coffee)) {
  int i = iR.ordinal();
   if ( Integer.valueOf(PRFArray[Player.ordinal()].Text1[i+IndigoIdx.ordinal()].getText()) > 0)
    if ( Building.Production(PRGDCurrentPlayer, BuildingIdx.OfficeIdx) > 0 )
     return true;
    else
     if (GoodInHouse(iR)) {} // try next good
     else
      return true;
  };
  return false;
}

static boolean GoodInHouse (ResourceIdx WhichGood) {
  for (int i = 0; i < PRCMaxTradeGoods; i++){
   if (Integer.valueOf(PRPuertoRico.playermat.Image7[i].getName()) == WhichGood.ordinal())
    return true;
  }
  return false;
}

public static boolean Sell(ResourceIdx rBarrelType ){
 boolean Sell = false;
 int BarrelType = rBarrelType.ordinal();
 int  SellPrice;

 if ( FullHouse() ) {
  return false;
 };

 for (int i = 0; i < PRCMaxTradeGoods; i++){
  if ( PRPuertoRico.playermat.Image7[i].isVisible() ) {
   if ( Integer.valueOf(PRPuertoRico.playermat.Image7[i].getName()) == BarrelType ) 
    if ( Building.Production(PRGDCurrentPlayer, BuildingIdx.OfficeIdx) > 0 ) {//office code
//     result = MsgBox.MsgBox("Office Allows " + PRGDPlayerNameStrings(PRGDCurrentPlayer) + " to Duplicate Goods in ImageIdx.TradingHouse")
     Utilities.BldgAnnounce(BuildingIdx.OfficeIdx, "Office Allows " + 
	     PRGDPlayerNameStrings[PRGDCurrentPlayer.ordinal()] +
	     " to Duplicate Goods in TradingHouse");
     Sell = true; 
    }
    else //already in house
     return false;
  }
  else {
   Sell = true;
   PRFArray[PRGDCurrentPlayer.ordinal()].Text1[BarrelType + IndigoIdx.ordinal()].
	   setText(Integer.toString(Integer.valueOf( PRFArray[PRGDCurrentPlayer.
	   ordinal()].Text1[BarrelType + IndigoIdx.ordinal()].getText()) - 1));
   SellPrice = ResourceValues[BarrelType];
   Utilities.EventAnnounce ( PRGDPlayerNameStrings[PRGDCurrentPlayer.ordinal()]
	   + " Sells " + PRGDResourceNStr[BarrelType]);
   if ( PRGDCurrentPlayer == PRGDPrivilegedPlayer ) {
    SellPrice = SellPrice + 1;
//    result = MsgBox.MsgBox(PRGDPlayerNameStrings(PRGDCurrentPlayer) + " Gets +1 Doubloon as Trader//s Privilege")
    Utilities.EventAnnounce ( PRGDPlayerNameStrings[PRGDCurrentPlayer.ordinal()]
	    + " Gets +1 Doubloon as Trader's Privilege");
   };
   if ( Building.Production(PRGDCurrentPlayer, BuildingIdx.SmallMarketIdx) > 0 ) {
    SellPrice = SellPrice + 1;
//    result = MsgBox.MsgBox(PRGDPlayerNameStrings(PRGDCurrentPlayer) + " Gets +1 Doubloon for a Small Market")
    Utilities.BldgAnnounce ( BuildingIdx.SmallMarketIdx, PRGDPlayerNameStrings
	    [PRGDCurrentPlayer.ordinal()] + " Gets +1 Doubloon for a Small Market");
   };

   if ( Building.Production(PRGDCurrentPlayer, BuildingIdx.LargeMarketIdx) > 0 ) {
    SellPrice = SellPrice + 2;
//    result = MsgBox.MsgBox(PRGDPlayerNameStrings(PRGDCurrentPlayer) + " Gets +2 Doubloons for a Large Market")
    Utilities.BldgAnnounce ( BuildingIdx.LargeMarketIdx, PRGDPlayerNameStrings
	    [PRGDCurrentPlayer.ordinal()] + " Gets +2 Doubloons for a Large Market");
   };

   PRFArray[PRGDCurrentPlayer.ordinal()].Text1[DoubloonsIdx.ordinal()].setText
	   (Integer.toString(Integer.valueOf(PRFArray[PRGDCurrentPlayer.ordinal()].
	   Text1[DoubloonsIdx.ordinal()].getText()) + SellPrice));

   PRPuertoRico.playermat.Image7[i].setName( Integer.toString(BarrelType) );
   PRPuertoRico.playermat.Image7[i].setIcon(PRFetchResource.FR(Integer.
	   toString(BarrelType + ImageIdx.IndigoLetter.ImgIndex())));
   PRPuertoRico.playermat.Image7[i].setVisible(true);
   NextTHSlot++;
   return true;
  };
 }; // for all trade goods 
 return Sell;
};

static boolean FullHouse(){ return PRPuertoRico.playermat.Image7[PRCMaxTradeGoods - 1].isVisible(); };

static ResourceIdx BestGoodCanTrade(SetupPlayerNames sPlayer){
 int Player = sPlayer.ordinal();
 boolean GoodAvailable[] = new boolean [PRCNumGoods];
 boolean NoGoodsAvailable;

 ResourceIdx BestGoodCanTrade = NullRIdx;

 if ( FullHouse() )
  return NullRIdx;

 NoGoodsAvailable = true;

//Determine Which Goods the Player has to trade
 for (ResourceIdx i : EnumSet.range( Indigo , Coffee)){
  int iR = i.ordinal();
  if ( Integer.valueOf(PRFArray[Player].Text1[iR + IndigoIdx.ordinal()].getText()) > 0 ) {
   GoodAvailable[iR] = true;
   NoGoodsAvailable = false; }
  else
   GoodAvailable[iR] = false;
 }; 

 if ( NoGoodsAvailable ) 
  return BestGoodCanTrade;

 if ( Building.Production(PRGDCurrentPlayer, BuildingIdx.OfficeIdx) > 0 ){}
 else
  //No Office, so can't duplicate goods

  for (int i = 0; i < PRCMaxTradeGoods; i++){
   if ( PRPuertoRico.playermat.Image7[i].isVisible() )
    GoodAvailable[Integer.valueOf(PRPuertoRico.playermat.Image7[i].getName())] = false;
  }; 

//Finally, pick the Good from the remaining possible Barrels
 for (int iR = PRCNumGoods - 1; iR >= 0; iR--){
		 
  //Convert order to ascending value (i.e. move Corn before Indigo)
  if ( GoodAvailable[AIGameData.AIGDPriorityGoodShip[iR].ordinal()] ) {
   BestGoodCanTrade = AIGameData.AIGDPriorityGoodShip[iR];
   break;
  };
 }; 
 return BestGoodCanTrade;
};

static int TradersBenefit(SetupPlayerNames Player){
//AI Uses this to give weight to decision to choose the Trader Role
 int TradersBenefit = 0;
 Modules.ResourceIdx Barrel = BestGoodCanTrade(Player);
 int iBarrel = Barrel.ordinal();

if ( Barrel != NullRIdx ) {
 TradersBenefit = ResourceValues[iBarrel];

 if ( Player == PRGDPrivilegedPlayer ) 
  TradersBenefit++;

 if ( Building.Production(PRGDCurrentPlayer, BuildingIdx.SmallMarketIdx) > 0 ) 
  TradersBenefit++;

 if ( Building.Production(PRGDCurrentPlayer, BuildingIdx.LargeMarketIdx) > 0 )
  TradersBenefit = TradersBenefit + 2;
};

return TradersBenefit;

};

static void ResetHouse() {
 for (int i = 0; i < PRCMaxTradeGoods; i++){
  PRPuertoRico.playermat.Image7[i].setName(Integer.toString(ResourceIdx.NullRIdx.ordinal()));
  PRPuertoRico.playermat.Image7[i].setVisible(false);
 }; 
 NextTHSlot = 0;
};

public static void Initialise(){
 ResourceValues[Indigo.ordinal()] = PRGoodsValue.PRRVIndigo.GV();
 ResourceValues[Sugar.ordinal()] = PRGoodsValue.PRRVSugar.GV();
 ResourceValues[Corn.ordinal()] = PRGoodsValue.PRRVCorn.GV();
 ResourceValues[Tobacco.ordinal()] = PRGoodsValue.PRRVTobacco.GV();
 ResourceValues[Coffee.ordinal()] = PRGoodsValue.PRRVCoffee.GV();
 ResetHouse();
};

}
