/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Modules;

/**
 *
 * @author steve
 */
public class Constants {

 public static final int MaxPlayers = 5;
 public static final int MaxAIPlayers = 4;
 public static final int MinPlayers = 3;
 public static final int MinPlayerIdx = 0;
 public static final int MaxPlayerIdx = MaxPlayers-1;
 public static final int PRCNumShips = 3;
// public static final int NumLandUseTypes = 6;
 public static final int MinNumRoles = 6;
 public static final int PRCNumResourceTypes = 6;
 public static final int PRCNumGoods = 5;

public static final int PRCMaxResourceIdx = ResourceIdx.Coffee.ordinal();

public static final int PRCNumResources = ResourceIdx.Coffee.ordinal() + 1;

public static final int PRRMaxPlantations = 12;

public static final int PRIconIdx = 10;

public static final int PRCurHandIdx = 101;

public static final int PRStringTableBase = 101;

public static final int PRCBldgTextRes = 8; // vb6 109;

public static final int MaxBuildingIdx = BuildingIdx.CityHallIdx.ordinal();
        
public static final int MaxCitySiteSlots = 12;

public static final int PlayerMatNImage2Offset = 4;
public static final int PlayerMat1Image2Offset = 18;
public static final int PlayerMatImage2RoleOffset = 32;

public static final int BldgLeftOrigin = 3; //vb6 345;
public static final int BldgTopOrigin = 2; // vb6 360; y factor is 12
public static final int BldgLeftIncr = 116; // vb6 1740; x factor is 15
public static final int BldgTopIncr = 68; // vb6 1030;

public static final int PRCBldgMaxTop = 28; // vb6was 420;
public static final int PRCBldgMinTop = 48; // vb6 840;
public static final int PRCBldgMinL1 = 14; // vb6 165;
public static final int PRCBldgMaxL1 = 34; // vb6 165;
public static final int PRCBldgMinL2 = 42; // vb6 600;
public static final int PRCBldgMaxL2 = 62; // vb6 600;
public static final int PRCBldgMinL3 = 70; // vb6 1050;
public static final int PRCBldgMaxL3 = 90; // vb6 1455;

public static final int BldgWidth = 110; // new to java
public static final int BldgSingleHeight = 60; // vb6 1005;

//public static final int GPGridFrameOriginTop = 0; // vb6 1200;
public static final int GPGridCraftsmanOriginLeft = 20; //vb6 120;
public static final int GPGridCraftsmanOriginTop = 70;
//public static final int GPGridPlayerOriginLeft = 120; // vb6 2100;
//public static final int GPGridIndigoOriginTop = 87; // vb6 1300;
//public static final int GPGridIndigoOriginLeft = 227; // vb6 3400;
//public static final int GPGridTextTopIncr = 80; // vb6 225;
//public static final int GPGridTextLeftIncr = 10; // vb6 150;
public static final int GPGridGoodTopIncr = 80; // vb6 700;
//public static final int GPGridGoodLeftIncr = 64; // vb6 1000;

public static final int PRCShipTop = 0; // vb6 90;

public static final int PRCMaxTradeGoods = 4;

public static final int NumEOGStats = 4;
//public static final int EOGLoserTOP = 100; // vb6 1500;
public static final int EOGWinnerLeft = 170;
public static final int EOGLeftIncr = 150;
public static final int EOGWinnerTop = 40;


public static final int RoleConfBase = 32; // 25 in vb6
public static final int BldgHelpBase = 40; // 31 in vb6

public static final int InfoCardHeight = 130;
public static final int InfoCardWidth = 110;

public static final int TileSize = 60;
/*
public enum FBldgInfoIdx2
 VPFBII2
 CostBaseFBII2
 CostLessQuarryFBII2
public static final int CostLessBonusFBII2 = 3;

*/

};
