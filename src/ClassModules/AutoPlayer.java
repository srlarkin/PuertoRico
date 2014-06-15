/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ClassModules;

import Modules.AIGameData;
import Modules.AIRoleChoice;
import Modules.BldgType;
import Modules.BuildingIdx;
import Modules.ChooseABuildingData;
import Modules.Constants;
import Modules.ImageIdx;
import Modules.MsgBox;
import Modules.NewTilePick;
import Modules.PRGameData;
import Modules.ResourceIdx;
import Modules.RevBuildingIdx;
import Modules.RevResourceIdx;
import Modules.SettlersBenefitData;
import Modules.SetupAvailableRoles;
import Modules.SetupPlayerNames;
import Modules.ShipIdx;
import Modules.ShippingPick;
import Modules.Text1Idx;
import Modules.TilePickIdx;
import Modules.TilePickRC;
import Modules.Utilities;
import static Modules.PRGameData.*;
import java.util.EnumSet;

/**
 *
 * @author steve
 */



public class AutoPlayer {

private static int GoodsICouldProduce[] = new int[Constants.PRCNumGoods - 1];

private static int RoleSelectionFactor[];

public static void PickProductionPrivilege()
{
 boolean Result;

 GoodsICouldProduce = Barrel.CheckProduction(PRGameData.PRGDCurrentPlayer);

 if (GoodsICouldProduce != null) {
  for  (int iR = Constants.PRCNumGoods - 1; iR >= 0; iR--) {
   if (GoodsICouldProduce[AIGameData.AIGDPriorityGoodShip[iR].ordinal()] > 0) {
    Result = Barrel.PickABarrel(AIGameData.AIGDPriorityGoodShip[iR]);
    break;
   }
  }
 }
};

public static void PickAShip(ResourceIdx CandidateBarrel) { //byVal
};

public static void PickATrade() {
// resultcode = MsgBox(FPuertoRico.Caption)
};

public static void ChooseABuilding() {
 ChooseABuildingData Choice;

// resultcode = MsgBox(FPuertoRico.Caption)
 
 Choice = BuildersChoice(PRGameData.PRGDCurrentPlayer);
// if (Choice.CandidateBuilding != BuildingIdx.NullBldgIdx) {
 if (Choice.CandidateCitySlot < Constants.MaxCitySiteSlots) {
  Building.BuyABuilding( PRGameData.PRGDCurrentPlayer, Choice);
 }
};

public static void ManageColonists(SetupPlayerNames Player) { //ByVal
// resultcode = MsgBox(FPuertoRico.Caption)
 //Move everyone to San Juan
 //first off the plantations
 Tile.Evacuate(Player);
 //then out of buildings
 Building.Evacuate(Player);
 //if VP < NumPlayers*5, populate large Violet Buildings

 if (Integer.valueOf(PRGameData.PRPuertoRico.playermat.Text1[Text1Idx.
	 VPRemainingIdx.ordinal()].getText()) < (5 * PRGameData.PRGDActualNumPlayers)
	 | PRGameData.PRGDGameExitMet) {
  Building.Populate (Player, BldgType.LargeVioletBT);
 };
 //TODO: figure better when to populate Quarries
 if (Building.CountPlayer(PRGameData.PRGDCurrentPlayer) < Constants.MaxCitySiteSlots) {
  Tile.FillUpAllTileType(Player, ResourceIdx.Quarry);
 };
 //Populate Small Violet Buildings
 Building.Populate(Player, BldgType.SmallVioletBT);
 //In Pairs, Populate production + Plantation
 Building.Populate(Player, BldgType.SmallProductionBT);
 Building.Populate(Player, BldgType.LargeProductionBT);
 //Populate Corn
 Tile.FillUpAllTileType(Player, ResourceIdx.Corn);
 //Populate Double Violets with leftovers
 Building.Populate(Player, BldgType.LargeVioletBT);
 //TODO:: At some points certain violet buildings can be left vacant
 // e.g. Hacienda, Hospice, University & Construction Hut
 Building.PopulateLast(Player);
};

public static void ChooseATile(SetupPlayerNames Player) {

 SettlersBenefitData SB;

 SB = SettlersBenefit(Player);

 if (Tile.TilePick(Player, SB.WhichSlot) == TilePickRC.TPRCOK) {
  AIAnnounce(Player, " Picked " + PRGameData.PRGDResourceNStr[SB.WhichGood.ordinal()]);
  Tile.PlaceAPick(Player, ImageIdx.values()[SB.WhichGood.ordinal() * 2 +
          ImageIdx.IndigoEmpty.ordinal()]);
 }
 else
  AIAnnounce (Player, " Cannot Pick a Tile"); 
 };

static AIRoleChoice HobbesChoice() {
// No good choices so just pick one randomly
AIRoleChoice AIRC = new AIRoleChoice();

AIRC.WtArray = new int[PRGDNumRoles.ordinal()+1];
AIRC.CumRoleWt = 0;

for ( SetupAvailableRoles Role : EnumSet.range(SetupAvailableRoles.SettlerSAR, PRGDNumRoles)) {
  int iRole = Role.ordinal();
  if (PRGameData.PRPuertoRico.playermat.Image5[iRole].isVisible()) {
   AIRC.WtArray[iRole] = 1;
   AIRC.CumRoleWt++;
  }
  else 
   AIRC.WtArray[iRole] = 0;
}

return AIRC;

}

public static void ChooseARole(SetupPlayerNames Player) {
//boolean ChooseARole;
//int RoleVisFactor;
SettlersBenefitData sb;
int RoleBenefit = 0;
int RoleRoll = 0;
//boolean HumanOverride();
//boolean AskDone;
//TilePickIdx CandidateSlot;
//ResourceIdx CandidateGood;
//BuildingIdx CandidateBuilding;
//int CandidateCitySlot;
//int EffectiveCost;
String RolePickStr;
AIRoleChoice AIRC = new AIRoleChoice();
AIRC.WtArray = new int[PRGDNumRoles.ordinal()+1];
// int HumanOverride[] = new int[PRGameData.iPRGDNumRoles - 1)]

// for ( int iRole = SetupAvailableRoles.SettlerSAR.ordinal(); iRole < iPRGDNumRoles ; iRole++) {
 for ( SetupAvailableRoles Role : EnumSet.range(SetupAvailableRoles.SettlerSAR,
	 PRGDNumRoles)) {
  int iRole = Role.ordinal();
  AIRC.WtArray[iRole] = 0;
 }
//  HumanOverride[iRole] = False

 AIRC.CumRoleWt = 0;
// ChooseARole = true;

 //Determine reletive benefit of role
//  for ( (int RoleI=SetupAvailableRoles.SettlerSAR.ordinal()),
//  (SetupAvailableRoles iRole = SetupAvailableRoles.SettlerSAR);
//  RoleI == PRGameData.iPRGDNumRoles - 1; RoleI++, iRole++){
//    for (SetupAvailableRoles iRole: EnumSet.range(SetupAvailableRoles.SettlerSAR,
//	    SetupAvailableRoles.ProspectorSAR2)) {
    for ( SetupAvailableRoles Role : EnumSet.range(SetupAvailableRoles.SettlerSAR,
	    PRGDNumRoles)) {
     int iRole = Role.ordinal();

//     RoleVisFactor = 1;
     RoleBenefit = 0;
     if (PRGameData.PRPuertoRico.playermat.Image5[iRole].isVisible()) {}
     else {
//      RoleVisFactor = 0;
      continue;
     };

   switch (Role) {
       case SettlerSAR:
 // What do I need to get production going?
 // -More Plantations (is one available)? -> Settler
 //  -Use Hacienda? 75% if tiles < 6; 50% if tiles < 8; 0% if tiles 8+
 //  -tile preference Corn, Sugar, Tobacco, Indigo, Coffee
        sb = SettlersBenefit(Player);
	 RoleBenefit = sb.SettlersBenefit;
        break;
 //   HumanOverride(iRole) = True
       case MayorSAR:
 // What do I need to get production going?
 // -More Population for my plantations and/or buildings? -> Mayor
 //  - Can I increase production with existing plantations & buildings?
 //    or are there small violet buildings not occupied?
 //    or are VPchits < 5*#Players and large violet buidings are not occupied
 //  - everyone back to San Pedro
 //  - first small violet buildings
 //  - Start with coffee man production&plantation
 //  - on down to Indigo then Corn
         RoleBenefit = MayorsBenefit(Player);
	 break;
       case BuilderSAR:
 // What do I need to get production going?
 // -More Production Buildings (are any available)? -> Builder
 //  -More Money for buildings? -> Trader or factor to other role selection or Prospector (if available)
 //  -Building Preference a list?
 //  -early(VP <20) Construction: Small Sugar, Construction Hut, Hospice,
 //  -mid(VPin 20s-30s): Sugar Mill, Indigo Plant, office, Large building, small warehouse
 //  -late(VPs 30+): Large bldg (if first), Harbour, Wharf,
 //  -opportunity fire: Small Mkt, Small Indigo,
         RoleBenefit = BuildersBenefit(Player);
	 break;
       case CraftsmanSAR:
        RoleBenefit = CraftsmanBenefit(Player);
	break;
 // -Do I have a monopoly?
 // -Do I produce more than others?
       case TraderSAR:
 // What do I need to get production going?
 //  -More Money for buildings? -> Trader or factor to other role selection or Prospector (if available)
        RoleBenefit = TradingHouse.TradersBenefit(Player);
	break;
       case CaptainSAR:
    // Can I ship? -> Captain
        if ( CargoShip.CanIShip(Player, Role) ) {
//        if (CargoShip.CanIShip(Player, iRole)) {
         RoleBenefit = 2*CargoShip.AggShipments(Player);
         };
	 break;
       case ProspectorSAR1:
 // What do I need to get production going?
 //  -More Money for buildings? -> Trader or factor to other role selection or Prospector (if available)

 //   HumanOverride(iRole) = True
       case ProspectorSAR2:
        RoleBenefit = 1;
   };

//   AIRC.WtArray[iRole] = RoleVisFactor * (RoleBenefit + MoneySelectionFactor(Role));
   AIRC.WtArray[iRole] = RoleBenefit + MoneySelectionFactor(Role);

  AIRC.CumRoleWt = AIRC.CumRoleWt + AIRC.WtArray[iRole];
  };

//   AIRC.WtArray of all zeros is a special case
 if (AIRC.CumRoleWt == 0)
  AIRC = HobbesChoice();

  RoleRoll = Utilities.RandomPick(1, AIRC.CumRoleWt);

 for ( SetupAvailableRoles Role : EnumSet.range(SetupAvailableRoles.SettlerSAR,
	 PRGDNumRoles)) {
  int iRole = Role.ordinal();
//  {
  if (AIRC.WtArray[iRole] > 0) {
   if (RoleRoll <= AIRC.WtArray[iRole]) {
     PRGameData.PRGDCurrentRole = Role;
     break;
   }
   else
     RoleRoll = RoleRoll - AIRC.WtArray[iRole];
  };
 };

 RolePickStr = " Has Chosen " + resources.LoadResString.ResSs[PRGameData.PRGDCurrentRole.ordinal()];
 AIAnnounce(Player, RolePickStr);
 Utilities.LogIt (" Chosen");

  switch ( PRGameData.PRGDCurrentRole ){
      case SettlerSAR:
       break;
      case MayorSAR:
       ColonyShip.Emmigrate (Player);
       break;
      case BuilderSAR:
       break;
      case CraftsmanSAR: 
       Barrel.Produce();
       break;
      case TraderSAR:
//       Trade(Player);
       break;
      case CaptainSAR:
    //Pick a ship
//       Ship(Player);
       break;
      case ProspectorSAR1:
      case ProspectorSAR2:
        Integer playercash = Integer.valueOf(PRGameData.
           PRFArray[Player.ordinal()].Text1[Text1Idx.DoubloonsIdx.ordinal()].getText());
          playercash++;
          PRGameData.PRFArray[Player.ordinal()].Text1[Text1Idx.DoubloonsIdx.ordinal()].
                  setText(playercash.toString());
  };

  if (PRGameData.PRGDCurrentRole == SetupAvailableRoles.NullSAR) {
    RoleRoll = RoleRoll - AIRC.WtArray[0];
  };

  RoleCard.TransferCash(Player, PRGameData.PRGDCurrentRole);

};

public static void Ship(SetupPlayerNames Player) {
 ShipIdx SelectedShip;
 ShippingPick sp;
 boolean Result;

  sp = CargoShip.MaxHolds( PRGameData.PRGDCurrentPlayer);

  SelectedShip = CargoShip.ShipForThisGood(sp.SelectedGood);

  if (SelectedShip == ShipIdx.NotAShipSlot) {}
  else {
   CargoShip.SetCandidateBarrel(sp.SelectedGood);
   Result = CargoShip.LoadCargo(SelectedShip);
  }
//   result = MsgBox(PRGameData.PRGDPlayerNameStrings(Player) + " has chosen to ship " 
//             + PRGameData.PRGDResourceNStr(SelectedGood))
//   EventAnnounce PRGameData.PRGDPlayerNameStrings(Player) + " has chosen to ship " 
//             + PRGameData.PRGDResourceNStr(SelectedGood)
  };

public static void Trade(SetupPlayerNames Player) {
 ResourceIdx Barrel;
 boolean Result;

 Barrel = TradingHouse.BestGoodCanTrade(Player);
 if (Barrel == ResourceIdx.NullRIdx) {}
 else
  if (TradingHouse.TradersBenefit(Player) > 0) {
   Result = TradingHouse.Sell(Barrel);
//   resultcode = MsgBox(PRGameData.PRGDPlayerNameStrings(Player) + " has chosen to Trade " 
//              + PRGameData.PRGDResourceNStr(Barrel))
//   EventAnnounce PRGDPlayerNameStrings(Player) + " has chosen to Trade " 
//             + PRGDResourceNStr(Barrel)
  };
 };

public static boolean DealWithHacienda(SetupPlayerNames OnPlayer) {

  int PlantationTotal;

  PlantationTotal = Tile.PlantationTotal(OnPlayer);
 switch ( PlantationTotal )
 {
     case 0:
     case 1:
     case 2:
     case 3:
     case 4:
     case 5:
     case 6:
         return (Utilities.RandomPick(1, 100) < 75);
     case 7:
     case 8:
     case 9:
         return (Utilities.RandomPick(1, 100) < 25);
     case 10:
     case 11:
     case 12:     
 };
  return false;
};

static boolean DealWithWharf(SetupPlayerNames Player) {
//Use the Wharf for this shipment?
 //Save some for selling?
 return true; //ToDo:: is this right?
};

public static void HandleGoodsNotShipped(SetupPlayerNames Player) {
//AI uses this to determine storage for unshipped goods
 int NumWareHouseSlots = 0;
 int NumGoods[] = new int[Constants.PRCNumGoods];
 boolean GoodAvailable[] = new boolean[Constants.PRCNumGoods];
 int ResIdx[] = new int[Constants.PRCNumGoods];
 ResourceIdx GoodToStore = ResourceIdx.NullRIdx;
 int NumStore = 0;
 int NumInBank = 0;
 int OrigPlayerNum = 0;
 NumWareHouseSlots = 0;

 //Account for small warehouse
 if (Building.Production(Player, BuildingIdx.SmallWarehouseIdx) > 0) 
  NumWareHouseSlots = 1;

 //Account for large warehouse
 if (Building.Production(Player, BuildingIdx.LargeWarehouseIdx) > 0) 
  NumWareHouseSlots = NumWareHouseSlots + 2;

 // Load up NumGoods, GoodAvailable arrays
 for (ResourceIdx iR: EnumSet.range(ResourceIdx.Indigo, ResourceIdx.Coffee)) {
  NumGoods[iR.ordinal()] = Integer.valueOf(PRGameData.PRFArray[Player.ordinal()]
          .Text1[iR.ordinal() + Text1Idx.IndigoIdx.ordinal()].getText());
  if (NumGoods[iR.ordinal()] > 0)
   GoodAvailable[iR.ordinal()] = true;
  else
   GoodAvailable[iR.ordinal()] = false;
  };

 if (NumWareHouseSlots > 0) // Create order by volume
  ResIdx = Utilities.SortAscending (Constants.PRCNumGoods, NumGoods);

  //Choose Best Candidate for each Warehouse Slot and the Compass
 for ( int iW = NumWareHouseSlots; iW >= 0; iW--) {
  GoodToStore = ResourceIdx.NullRIdx;
  if (iW > 0)  //Choose by volume for Warehouse Slots
  {
   for ( RevResourceIdx RiR: EnumSet.range(RevResourceIdx.CoffeeR, RevResourceIdx.IndigoR)) {
    ResourceIdx iR = RiR.UnRev();
    if (GoodAvailable[ResIdx[iR.ordinal()]]) {
     GoodToStore = iR;
     GoodAvailable[ResIdx[iR.ordinal()]] = false;
     break;
    };
   };
  }
  else //Compass Slot is chosen by value
   for (RevResourceIdx i : EnumSet.range(RevResourceIdx.CoffeeR, RevResourceIdx.IndigoR))
   {
    ResourceIdx iR = AIGameData.AIGDPriorityGoodShip[i.UnRev().ordinal()]; //go in value order
    if (GoodAvailable[iR.ordinal()]) {
     GoodToStore = iR;
     GoodAvailable[iR.ordinal()] = false;
     break;
    };
   };
  

  if (GoodToStore != ResourceIdx.NullRIdx) {

   NumInBank = Integer.valueOf(PRGameData.PRPuertoRico.playermat.Text1[Text1Idx.
           IndigoBankIdx.ordinal() + GoodToStore.ordinal()].getText());
   OrigPlayerNum = Integer.valueOf(PRGameData.PRFArray[Player.ordinal()].
            Text1[Text1Idx.IndigoIdx.ordinal() + GoodToStore.ordinal()].getText());

   if (iW == 0)
    NumStore = 1;
   else
    NumStore = OrigPlayerNum;

   PRGameData.PRPuertoRico.playermat.Text1[Text1Idx.IndigoBankIdx.ordinal()
           + GoodToStore.ordinal()].setText(
            Integer.toString(NumInBank + OrigPlayerNum - NumStore));
   PRGameData.PRFArray[Player.ordinal()].Text1[Text1Idx.IndigoIdx.ordinal()
           + GoodToStore.ordinal()].setText(Integer.toString(NumStore));

  };

};

 //Now Move Lost Barrels Back to the Bank
 for ( RevResourceIdx RiR: EnumSet.range(RevResourceIdx.CoffeeR, RevResourceIdx.IndigoR)) {
  ResourceIdx iR = RiR.UnRev();
  if (GoodAvailable[iR.ordinal()]) {
   PRGameData.PRPuertoRico.playermat.Text1[Text1Idx.IndigoBankIdx.ordinal() + iR.ordinal()].setText(
    Integer.toString(
    Integer.valueOf(PRGameData.PRPuertoRico.playermat.Text1[Text1Idx.IndigoBankIdx.ordinal() + iR.ordinal()].getText()) +
    Integer.valueOf(PRGameData.PRFArray[Player.ordinal()].Text1[Text1Idx.IndigoIdx.ordinal() + iR.ordinal()].getText())
    )
    );
   PRGameData.PRFArray[Player.ordinal()].Text1[Text1Idx.IndigoIdx.ordinal() + iR.ordinal()].setText("0");
  };
 };

 CargoShip.GoodsHoused(Player);
 
// FPuertoRico.Command2.Visible = True
// FPuertoRico.GoGoGo
};

private static int MoneySelectionFactor(SetupAvailableRoles WhichRole) {
 return Integer.valueOf(PRGameData.PRPuertoRico.playermat.Text1[WhichRole.ordinal()
         + Text1Idx.SettlerMoneyIdx.ordinal()].getText());
};

private static int CraftsmanBenefit(SetupPlayerNames OnPlayer) {

 int CraftsmanBenefit = 0;

 GoodsICouldProduce = Barrel.CheckProduction(OnPlayer);
 if (GoodsICouldProduce != null) {
  for ( ResourceIdx iR: EnumSet.range(ResourceIdx.Indigo, ResourceIdx.Coffee))
   CraftsmanBenefit = CraftsmanBenefit + GoodsICouldProduce[iR.ordinal()];
  
 };
 return CraftsmanBenefit;
};

private static int MayorsBenefit(SetupPlayerNames OnPlayer) {
 //This is based on how many vacant buildings and plantations vs available colonist supply

 int MayorsBenefit = 0;

 //Colonists must be available from the Colony Ship or San Juan
 if ( (Integer.valueOf(PRGameData.PRFArray[OnPlayer.ordinal()].Text1[Text1Idx.SanJuanIdx.ordinal()].getText()) +
      Integer.valueOf(PRGameData.PRPuertoRico.playermat.Text1[Text1Idx.ColonistsInShipIdx.ordinal()].getText())) > 0) {
  //Account for Vacant Buildings
  MayorsBenefit = Building.VacancyCount(OnPlayer);
  //Now throw in empty plantations
  MayorsBenefit = MayorsBenefit + Tile.PlantationTotal(OnPlayer)
                  - Tile.PlantationWorkforce(OnPlayer);
 };
 // Limit Mayor's Benefit to a max so it doesn't over whelm choices
 if (MayorsBenefit > 5)
  return 5;
 else
  return MayorsBenefit;
};

private static SettlersBenefitData SettlersBenefit(SetupPlayerNames OnPlayer) {
 //Calculate basic benefit based on number of empty plantation slots

 SettlersBenefitData SB = new SettlersBenefitData();
 NewTilePick ntp = new NewTilePick();
 int QuarryRoll;
 int QuarryCount;

 SB.WhichGood = ResourceIdx.NullRIdx;
 SB.WhichSlot = TilePickIdx.NullTPI;
 SB.SettlersBenefit = 0;

 switch ( Tile.PlantationTotal(OnPlayer) ){
     case 0:
     case 1:
     case 2:
     case 3:
      SB.SettlersBenefit = 4;
      break;
     case 4:
     case 5:
     case 6:
      SB.SettlersBenefit = 3;
      break;
     case 7:
     case 8:
     case 9:
      SB.SettlersBenefit = 2;
      break;
     case 10:
     case 11:
      SB.SettlersBenefit = 1;
      break;
     case 12:
      SB.SettlersBenefit = 0;
      return SB;
 };

 //Cumulative Bonus for a staffed Hacienda
 if ((Tile.PlantationTotal(OnPlayer) < 6) &
    (Building.Production(OnPlayer, BuildingIdx.HaciendaIdx) > 0)) {
   SB.SettlersBenefit++;
 };

 //Add 1 if Quarry benefit desired
 if ((Integer.valueOf(PRGameData.PRPuertoRico.playermat.
         Text1[Text1Idx.QuarryBankIdx.ordinal()].getText()) > 0) &
    ((OnPlayer == PRGameData.PRGDPrivilegedPlayer)
     || (Building.Production(PRGameData.PRGDCurrentPlayer, BuildingIdx.ConstructionHutIdx) > 0)) )
 {
  QuarryCount = Tile.PlantationCount(OnPlayer, ImageIdx.QuarryEmpty)
                + Tile.PlantationCount(OnPlayer, ImageIdx.QuarryOccupied);

  QuarryRoll = Utilities.RandomPick(1, 100);

  switch ( QuarryCount ){
      case 0:
       if (QuarryRoll < 50)
        SB.WhichSlot = TilePickIdx.QuarryTPI;
       break;
      case 1:
       if (QuarryRoll < 25)
        SB.WhichSlot = TilePickIdx.QuarryTPI;
        break;
      case 2:
       if (QuarryRoll < 15)
        SB.WhichSlot = TilePickIdx.QuarryTPI;
        break;
      case 3:
       if (QuarryRoll < 5)
        SB.WhichSlot = TilePickIdx.QuarryTPI;
   };
  
  if (SB.WhichSlot == TilePickIdx.QuarryTPI) {
   SB.WhichGood = ResourceIdx.Quarry;
   SB.SettlersBenefit++;
   return SB;
  };
 };
 //Or, Add 1 if Production can be increased
 ntp = Tile.RefineryCanProcess(OnPlayer);
 if (ntp.Tile != TilePickIdx.NullTPI) {
   SB.WhichSlot = ntp.Tile;
   SB.WhichGood = ntp.Resource;
   SB.SettlersBenefit++;
   return SB;
 };
 //Or, Add 1 if Corn can be acquired
 SB.WhichSlot = Tile.FindATilePick(ImageIdx.CornEmpty);
 if (SB.WhichSlot != TilePickIdx.NullTPI) {
  SB.WhichGood = ResourceIdx.Corn;
  SB.SettlersBenefit++;
  return SB;
 };

 //Or, Add 1 if diversity can be increased
 for ( RevResourceIdx iR: EnumSet.range(RevResourceIdx.CoffeeR, RevResourceIdx.IndigoR)) {
  int i = iR.UnRev().ordinal();
  int emptycount = Tile.PlantationCount(OnPlayer, ImageIdx.values()[i * 2 + 
	  ImageIdx.IndigoEmpty.ordinal()]) ;
  int fullcount = Tile.PlantationCount(
	  OnPlayer, ImageIdx.values()[i * 2 + ImageIdx.IndigoColonised.ordinal()]);
  if ((emptycount + fullcount) == 0) {
   SB.WhichSlot = Tile.FindATilePick(ImageIdx.values()[i * 2 + ImageIdx.IndigoEmpty.ordinal()]);
   if (SB.WhichSlot != TilePickIdx.NullTPI) {
    SB.WhichGood = iR.UnRev();
    SB.SettlersBenefit++;
    return SB;
   };
  };
 };

 //Or just pick the first available since no choice is desired
 ntp = Tile.PickAnyTile(); //Bear in mind Slot could be "12" after tilebag is empty
 SB.WhichSlot = ntp.Tile;
 SB.WhichGood = ntp.Resource;
 SB.SettlersBenefit = 0;
 return SB;
};

private static ChooseABuildingData BuildersChoice(SetupPlayerNames Player) {
ChooseABuildingData CBD = new ChooseABuildingData();
boolean BuildingPicked;

CBD.EffectiveCost = 0; // aka BuildersChoice
CBD.CandidateBuilding = BuildingIdx.NullBldgIdx;
BuildingPicked = false;

for (RevBuildingIdx RiB: EnumSet.range(RevBuildingIdx.CityHallIdxR,
	RevBuildingIdx.SmallIndigoPlantIdxR))
{
 BuildingIdx iB = RiB.UnRev();
 CBD = Building.VettBuilding(Player, iB);
 if (CBD.CandidateCitySlot != Constants.MaxCitySiteSlots) {
  switch ( iB ) {
      case SmallIndigoPlantIdx:
    //Got unexploited Indigo
    //Indigo Tiles on Offer?
    //Plant is Free
       CBD.CandidateBuilding = iB;
       return CBD;
      case SmallSugarMillIdx:
       CBD.CandidateBuilding = iB;
       return CBD;
      case SmallMarketIdx:
    //Mkt is Free
       CBD.CandidateBuilding = iB;
       return CBD;
      case HaciendaIdx:
    // > 6 Plantations? skip it
       if (Tile.PlantationTotal(Player) <= 6) {
        BuildingPicked = true;
       };
       break;
      case HospiceIdx:
      // > 6 Plantations? skip it
       if (Tile.PlantationTotal(Player) <= 6) {
        BuildingPicked = true;
       };
       break;
      case ConstructionHutIdx:
    // > 6 Plantations? skip it
       if (Tile.PlantationTotal(Player) <= 6) {
        BuildingPicked = true;
       };
       break;
      case SmallWarehouseIdx:
    // Produce < 2 goods? Skip it
    // Have L W.H. and produce < 4 goods? Skip it
       CBD.CandidateBuilding = iB;
       return CBD;
      case IndigoPlantIdx:
       CBD.CandidateBuilding = iB;
       return CBD;
      case SugarMillIdx:
       CBD.CandidateBuilding = iB;
       return CBD;
      case OfficeIdx:
    //Have a Monopoly? Skip it
       CBD.CandidateBuilding = iB;
       return CBD;
      case LargeMarketIdx:
       CBD.CandidateBuilding = iB;
       return CBD;
      case LargeWarehouseIdx:
    //Produce < 2 goods? Skip it
    //Have S W.H. and produce < 3 goods? Skip it
       CBD.CandidateBuilding = iB;
       return CBD;
      case TobaccoStorageIdx:
       CBD.CandidateBuilding = iB;
       return CBD;
      case CoffeeRoasterIdx:
       CBD.CandidateBuilding = iB;
       return CBD;
      case FactoryIdx:
    //Change Benefit to # goods produced.
       CBD.CandidateBuilding = iB;
       return CBD;
      case UniversityIdx:
    // > 6 Buildings? Skip it
       if (Building.CountPlayer(Player) <= 6) {
        BuildingPicked = true;
       };
       break;
      case HarbourIdx:
       CBD.CandidateBuilding = iB;
       return CBD;
      case WharfIdx:
       CBD.CandidateBuilding = iB;
       return CBD;
      case GuildHallIdx:
       CBD.CandidateBuilding = iB;
       return CBD;
      case ResidenceIdx:
       CBD.CandidateBuilding = iB;
       return CBD;
      case FortressIdx:
       CBD.CandidateBuilding = iB;
       return CBD;
      case CustomsHouseIdx:
       CBD.CandidateBuilding = iB;
       return CBD;
      case CityHallIdx:
       CBD.CandidateBuilding = iB;
       return CBD;
  };

  if (BuildingPicked) {
   CBD.CandidateBuilding = iB;
   return CBD;
  };

 };
};

 return CBD;

};

private static int BuildersBenefit(SetupPlayerNames Player) {
//                         ByRef WhichBuilding As BuildingIdx,
//                         ByRef BuilderSlot As Integer,
//                         ByRef EffectiveCost As Integer) As Integer
ChooseABuildingData CBD;
 CBD = BuildersChoice(Player);
 return Building.VP(CBD.CandidateBuilding);
};

public static void AIAnnounce(SetupPlayerNames Player, String AnncStr) {
 String PlayerActionString;

 PlayerActionString = PRGameData.PRGDPlayerNameStrings[Player.ordinal()] + " " + AnncStr;

// PRGameData.PRPuertoRico.setTitle(PRGameData.PRPuertoRico.getTitle() + PlayerActionString);
 PRGameData.PRPuertoRico.setTitle(PlayerActionString);
// result = MsgBox(PlayerActionString)
 RoleCard.InfoCard(PlayerActionString);
// EventAnnounce RoleString

};

public static void Initialise() {
 for ( int i = 0; i < Constants.PRCNumGoods; i++)
  switch ( i ){
      case 0:
       AIGameData.AIGDPriorityGoodShip[i] = ResourceIdx.Corn;
       break;
 //  AIGDBackToGoodOrder(i) = Sugar
      case 1:
       AIGameData.AIGDPriorityGoodShip[i] = ResourceIdx.Indigo;
       break;
 //  AIGDBackToGoodOrder(i) = Corn
      case 2:
       AIGameData.AIGDPriorityGoodShip[i] = ResourceIdx.Sugar;
       break;
 //  AIGDBackToGoodOrder(i) = Indigo
      case 3:
       AIGameData.AIGDPriorityGoodShip[i] = ResourceIdx.Tobacco;
       break;
 //  AIGDBackToGoodOrder(i) = Tobacco
      case 4:
       AIGameData.AIGDPriorityGoodShip[i] = ResourceIdx.Coffee;
 //  AIGDBackToGoodOrder(i) = Coffee
  };

  RoleSelectionFactor = new int[PRGameData.PRGDNumRoles.ordinal()+1];

// for ( int i = SetupAvailableRoles.SettlerSAR.ordinal(); i < PRGameData.iPRGDNumRoles; i++)
 for ( SetupAvailableRoles Role : EnumSet.range(SetupAvailableRoles.SettlerSAR, PRGDNumRoles)) {
  int i = Role.ordinal();
  RoleSelectionFactor[i] = 0;
 };

}

}
