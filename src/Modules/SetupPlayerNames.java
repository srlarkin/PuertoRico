/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Modules;

/**
 *
 * @author steve
 */
public enum SetupPlayerNames {
HumanPlayerSPN, AIPlayerSPN1, AIPlayerSPN2,
 AIPlayerSPN3, AIPlayerSPN4;
public SetupPlayerNames Next (int base) {
  return SetupPlayerNames.values()[(this.ordinal()+1) % base];
}
}
