/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ClassModules;

import Modules.SetupPlayerNames;

/**
 *
 * @author steve
 */
public class GovernorCard {

// Verbose is used to indicate whether additional popups should happen
private static boolean Verbose = true;

public static void setVerbose(boolean verbose) {
	Verbose = verbose;
}

public static boolean isVerbose() {
	return Verbose;
}

public static void IDGovernor() {};

public static void SetGovernor(SetupPlayerNames Player ) {};

}
