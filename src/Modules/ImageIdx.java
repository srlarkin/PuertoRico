/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Modules;

import java.util.EnumSet;

/**
 *
 * @author steve
 */
public enum ImageIdx {IndigoEmpty(10), IndigoColonised(11), SugarEmpty(20),
  SugarColonised(21), CornEmpty(30), CornColonised(31), TobaccoEmpty(40),
  TobaccoColonised(41), CoffeeEmpty(50), CoffeeColonised(51), QuarryEmpty(60),
  QuarryOccupied(61), SmallIndigoPlantEmpty(110), SmallIndigoPlantOccupied(111),
  SmallSugarMillEmpty(120), SmallSugarMillOccupied(121), SmallMarketEmpty(130),
  SmallMarketFull(131), HaciendaEmpty(140), HaciendaOccupied(141),
  ConstructionHutEmpty(150), ConstructionHutOccupied(151),
  SmallWarehouseEmpty(160), SmallWarehouseOccupied(161), IndigoPlantEmpty (210),
  IndigoPlantOccupied1(211), IndigoPlantOccupied2(212), IndigoPlantOccupied3 (213),
  SugarMillEmpty(220), SugarMillOccupied1(221), SugarMillOccupied2(222),
  SugarMillOccupied3(223), HospiceEmpty(230), HospiceOccupied(231),
  OfficeEmpty(240), OfficeOccupied(241), LargeMarketEmpty(250),
  LargeMarketOccupied(251), LargeWarehouseEmpty(260), LargeWarehouseOccupied(261),
  TobaccoStorageEmpty(310), TobaccoStorageOccupied1(311),
  TobaccoStorageOccupied2(312), TobaccoStorageOccupied3(313),
  CoffeeRoasterEmpty(320), CoffeeRoasterOccupied1(321),
  CoffeeRoasterOccupied2(322), FactoryEmpty(330), FactoryOccupied(331),
  UniversityEmpty(340), UniversityOccupied(341), HarbourEmpty(350),
  HarbourOccupied(351), WharfEmpty(360), WharfOccupied(361), GuildHallEmpty(410),
  GuildHallOccupied(411), ResidenceEmpty(420), ResidenceOccupied(421),
  FortressEmpty(430), FortressOccupied(431), CustomsHouseEmpty(440),
  CustomsHouseOccupied(441), CityHallEmpty(450), CityHallOccupied(451),
  Blank1(500) , Blank2(501), IndigoLetter(502), SugarLetter(503), CornLetter(504),
  TobaccoLetter(505), CoffeeLetter(506), BuildingHelpBoard(510), 
  BuildingBuildBoard(511), PuertoRicoBoard(512), PRCover(513), SettlerCard(520),
  MayorCard(521), BuilderCard(522), CraftsmanCard(523), TraderCard(524),
  CaptainCard(525), ProspectorCard(526), GovernorCard(527), SettlerIcon(530),
  MayorIcon(531), BuilderIcon(532), CraftsmanIcon(533), TraderIcon(534), 
  CaptainIcon(535), ProspectorIcon1(536), ProspectorIcon2(537), ShipSize4(540),
  ShipSize5(541), ShipSize6(542), ShipSize7(543), ShipSize8(544), Wharf(545),
  ColonistShipCard(546), GenericShip(547), TradingHouse(550), thIndigoLetter(560),
  thSugarLetter(561), thCornLetter(562), thTobaccoLetter(563), thCoffeeLetter(564);
    private int ImIdx; // in Doubloons
    private ImageIdx(int ImIdx) { this.ImIdx = ImIdx;};
    public int ImgIndex() {return ImIdx;}
    public String SImgIndex() {return Integer.toString(ImIdx);}
    public ImageIdx Next () {
     return ImageIdx.values()[Integer.valueOf(this.ImIdx) + 1];};
    public static ImageIdx Lookup (int idx) {
    
    for (ImageIdx i : EnumSet.range(IndigoEmpty , thCoffeeLetter)){
      if (i.ImgIndex() == idx) 
	      return i;
    }
    return Blank1;
}
}