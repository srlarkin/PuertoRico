/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Modules;

import static Modules.BuildingIdx.*;

/**
 *
 * @author steve
 */
public enum RevBuildingIdx {
CityHallIdxR (CityHallIdx),
CustomsHouseIdxR (CustomsHouseIdx),
FortressIdxR(FortressIdx),
ResidenceIdxR(ResidenceIdx),
GuildHallIdxR(GuildHallIdx),
WharfIdxR(WharfIdx),
HarbourIdxR(HarbourIdx),
UniversityIdxR(UniversityIdx),
FactoryIdxR(FactoryIdx),
CoffeeRoasterIdxR(CoffeeRoasterIdx),
TobaccoStorageIdxR(TobaccoStorageIdx),
LargeWarehouseIdxR(LargeWarehouseIdx),
LargeMarketIdxR(LargeMarketIdx),
OfficeIdxR(OfficeIdx),
HospiceIdxR(HospiceIdx),
SugarMillIdxR(SugarMillIdx),
IndigoPlantIdxR(IndigoPlantIdx),
SmallWarehouseIdxR(SmallWarehouseIdx),
ConstructionHutIdxR(ConstructionHutIdx),
HaciendaIdxR(HaciendaIdx),
SmallMarketIdxR(SmallMarketIdx),
SmallSugarMillIdxR(SmallSugarMillIdx),
SmallIndigoPlantIdxR(SmallIndigoPlantIdx),
NullBldgIdxR(NullBldgIdx);
private BuildingIdx rbi;

private RevBuildingIdx(BuildingIdx rbi) {this.rbi = rbi;};

public BuildingIdx UnRev() { return rbi;};
}
