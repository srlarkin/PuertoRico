/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Modules;

/**
 *
 * @author steve
 */
public enum InfoCardState {inActiveICS, ActiveICS, Waiting1ICS, Waiting2Ics, ConfirmISC, AckICS, NullICS;
public InfoCardState next(){
	return InfoCardState.values()[(this.ordinal()+1)% NullICS.ordinal()];
}

}
