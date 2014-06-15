/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Modules;

/**
 *
 * @author steve
 */
public enum SetupChoiceSeat {FirstSCS, SecondSCS,
 ThirdSCS, FourthSCS, FifthSCS, RandomSCS, nullSCS;

public SetupChoiceSeat Next (int base) {
  return SetupChoiceSeat.values()[(this.ordinal()+1) % base];
}

}
