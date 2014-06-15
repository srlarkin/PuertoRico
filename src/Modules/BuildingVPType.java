/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Modules;

/**
 *
 * @author steve
 */
public class BuildingVPType {
 public int SmallProduction;
 public int LargeProduction;
 public int Violet;
// NumPlantations As Integer
 public int Staff;              // just building population
 public BuildingVPType(int sp, int lp, int violet, int staff) {
	 SmallProduction = sp;
	 LargeProduction = lp;
	 Violet = violet;
	 Staff = staff;
 };
 public BuildingVPType(){
	 SmallProduction = 0;
	 LargeProduction = 0;
	 Violet = 0;
	 Staff = 0;
 }
};
