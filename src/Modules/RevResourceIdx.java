/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Modules;

import static Modules.ResourceIdx.*;
/**
 *
 * @author steve
 */
public enum RevResourceIdx {
CoffeeR(Coffee), TobaccoR(Tobacco), CornR(Corn), SugarR(Sugar), IndigoR(Indigo),
 QuarryR(Quarry), NullRIdxR(NullRIdx);
private ResourceIdx ri;

private RevResourceIdx(ResourceIdx ri){this.ri = ri;};

public ResourceIdx UnRev() { return ri;}
}
