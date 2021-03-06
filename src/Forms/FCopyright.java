/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FCopyright.java
 *
 * Created on Sep 20, 2009, 11:24:57 AM
 */

package Forms;

import ClassModules.Tile;
import ClassModules.TradingHouse;
import Modules.Constants;
import Modules.PRBasedOnSeat;
import Modules.PlayerMatAccess;
import Modules.SetupAvailableRoles;
import Modules.SetupChoiceSeat;
import Modules.SetupNumPlayers;
import Modules.SetupPlayerNames;
import Modules.Utilities;
import static Modules.PRGameData.*;
import static Modules.Constants.*;
import static Modules.ResourceIdx.*;

/**
 *
 * @author steve
 */
public class FCopyright extends javax.swing.JDialog {
    private static final long serialVersionUID = 1L;

    /** Creates new form FCopyright */
    public FCopyright(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/513.jpg"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setText("copyright yadayadayada");
        jTextArea1.setWrapStyleWord(true);
        jTextArea1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextArea1.setOpaque(false);
        getContentPane().add(jTextArea1, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 10, 280, 530));

        jButton1.setText("OK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 560, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
     OKButton(evt);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void OKButton (java.awt.event.ActionEvent evt) {
 PRGDNumPlayers = SetupNumPlayers.SNPlayers3;
 PRGDActualNumPlayers = PRGDNumPlayers.ordinal() + MinPlayers;
 PRGDChoiceOfSeat = SetupChoiceSeat.FirstSCS;
 PRGDCurrentRole = SetupAvailableRoles.NullSAR;
 if (PRGDGameExitMet) {
  PRGDSeatCentric = (PRBasedOnSeat[])Utilities.resizeArray(PRGDSeatCentric, Constants.MaxPlayers);
//  PRFArray = (PlayerMatAccess[]) Utilities.resizeArray(PRFArray, Constants.MaxPlayers);
 }
 PRGDGameExitMet = false;
 PRGDGameOn = false;

 PRPuertoRico = new FPuertoRico();
 PRSetup = new FSetup(PRPuertoRico,false);
 PRProductionGrid = new FProductionGrid();
 PRBuildings = new FBuildings(PRPuertoRico, false);
 PRBuildingInfo = new FBuildingInfo(PRPuertoRico,false);
 PRInformation = new FInformation(PRPuertoRico, false);
 PRGameOver = new FGameOver();
 PRGoneGoods = new FGoneGoods(PRPuertoRico,false);
 
 PRFArray[SetupPlayerNames.HumanPlayerSPN.ordinal()] = PRPuertoRico.playermat;
 PRFArray[SetupPlayerNames.HumanPlayerSPN.ordinal()].iPlayer = SetupPlayerNames.HumanPlayerSPN.ordinal();
 PRGDSeatCentric[SetupChoiceSeat.FirstSCS.ordinal()] =
	 new Modules.PRBasedOnSeat(SetupPlayerNames.HumanPlayerSPN,Indigo);
 PRGDSeatCentric[SetupChoiceSeat.SecondSCS.ordinal()] =
	 new Modules.PRBasedOnSeat(SetupPlayerNames.AIPlayerSPN1,Indigo);
 PRGDSeatCentric[SetupChoiceSeat.ThirdSCS.ordinal()] =
	 new Modules.PRBasedOnSeat(SetupPlayerNames.AIPlayerSPN2,Corn);
 PRGDSeatCentric[SetupChoiceSeat.FourthSCS.ordinal()] =
	 new Modules.PRBasedOnSeat(SetupPlayerNames.AIPlayerSPN3,Corn);
 PRGDSeatCentric[SetupChoiceSeat.FifthSCS.ordinal()] =
	 new Modules.PRBasedOnSeat(SetupPlayerNames.AIPlayerSPN4,Corn);

 Tile.Initialise();
 TradingHouse.Initialise();
 PRInformation.Initialise();
 PRPuertoRico.setVisible(true);
 this.dispose();	    
    }
    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FCopyright dialog = new FCopyright(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables

}
