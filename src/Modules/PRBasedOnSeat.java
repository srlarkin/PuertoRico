/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Modules;

/**
 *
 * @author steve
 */
public class PRBasedOnSeat {
 public SetupPlayerNames WhichPlayer;
 public ResourceIdx StartingPlantation;
 public PRBasedOnSeat(SetupPlayerNames spn, ResourceIdx sp) {
	 this.WhichPlayer=spn;
	 this.StartingPlantation=sp;
 }
 public PRBasedOnSeat(PRBasedOnSeat bos){
	 this.WhichPlayer=bos.WhichPlayer;
	 this.StartingPlantation = bos.StartingPlantation;
 }
};
