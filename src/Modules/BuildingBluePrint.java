/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Modules;

/**
 *
 * @author steve
 */
public class BuildingBluePrint {
 public int BCount;                 //For accounting
 public BldgType BType;
 public int NumPosts;
 public ImageIdx PicIdx;
 public int Cost;
 public int MaxQuarry;
 public int VP;
 public BuildingBluePrint (){
	 this.BCount = 0;
	 this.BType = BldgType.NullBT;
	 this.NumPosts = 0;
	 this.PicIdx = ImageIdx.Blank1;
	 this.Cost = 0;
	 this.MaxQuarry = 0;
	 this.VP = 0;
 }
};
