/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Modules;

/**
 *
 * @author steve
 */
public class CitySiteSlot {
  public boolean Occupied;
  public BuildingIdx Building;
  public int NumColonists;
  public CitySiteSlot() {
	  this.Occupied = false;
	  this.Building = BuildingIdx.NullBldgIdx;
	  this.NumColonists = 0;
  }
};

