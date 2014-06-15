/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package Modules;

import Forms.*;

/**
 *
 * @author steve
 */

public class PRGameData {
public static SetupNumPlayers PRGDNumPlayers = SetupNumPlayers.SNPlayers3;
public static SetupChoiceSeat PRGDChoiceOfSeat = SetupChoiceSeat.FirstSCS;
public static int PRGDActualNumPlayers = 3;
public static SetupAvailableRoles PRGDNumRoles = SetupAvailableRoles.CaptainSAR;
//public static int iPRGDNumRoles = PRGDNumRoles.ordinal();

public static boolean PRGDGameOn = false;

// Global Data TODO:: eliminate!
public static SetupAvailableRoles PRGDCurrentRole = SetupAvailableRoles.NullSAR;
public static SetupPlayerNames PRGDCurrentGovernor = SetupPlayerNames.HumanPlayerSPN;
public static SetupPlayerNames PRGDPrivilegedPlayer = SetupPlayerNames.HumanPlayerSPN;
public static SetupPlayerNames PRGDCurrentPlayer = SetupPlayerNames.HumanPlayerSPN;
public static SetupChoiceSeat PRGDCurrentSeat = SetupChoiceSeat.FirstSCS;
public static boolean PRGDHumanPlayerDone = false;
public static boolean PRGDGameExitMet = false;

public static PlayerMatAccess PRFArray[] = new PlayerMatAccess[Constants.MaxPlayers];
public static String PRGDPlayerNameStrings[] = new String[1];
public static boolean PRGDAutoPlayer[] = new boolean[1];
public static PRBasedOnSeat PRGDSeatCentric[] = new PRBasedOnSeat[Constants.MaxPlayers];
public static String PRGDResourceNStr[] = new String[Constants.PRCNumGoods+1]; // +1 for Quarry
public static ProductionGridArrays PRProductionGridArray; // Num of fields = num players: resolved during setup.

public static FetchResource PRFetchResource = new FetchResource();

// Delcare Form instances
public static FCopyright PRCopyright; // Init'd by FPuertoRico Main
  // The rest are Init'd by FCopyRight
public static FPuertoRico PRPuertoRico;
public static FSetup PRSetup;
public static FProductionGrid PRProductionGrid;
public static FBuildings PRBuildings;
public static FBuildingInfo PRBuildingInfo;
public static FInformation PRInformation;
public static FGameOver PRGameOver;
public static FGoneGoods PRGoneGoods;
};