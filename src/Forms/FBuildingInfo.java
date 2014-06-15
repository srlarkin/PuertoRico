/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FBuildingInfo.java
 *
 * Created on Sep 20, 2009, 9:16:24 AM
 */

package Forms;

import ClassModules.Building;
import Modules.*;
import static Modules.Constants.*;
import static Modules.PRGameData.*;
import static Modules.ResourceIdx.*;

/**
 *
 * @author steve
 */
public class FBuildingInfo extends javax.swing.JDialog {
//private int PRBCandidateSlot = 0;
//private int PRBCandidateEffectiveCost = 0;
//private BuildingIdx PRBCandidateBldg = BuildingIdx.NullBldgIdx;

void Populate(SetupPlayerNames Player, BuildingIdx WhichBldg ) {
 ImageIdx BldgIdx = ImageIdx.Blank1;

 if ( (PRGDCurrentRole == SetupAvailableRoles.BuilderSAR) & (Player == PRGDPrivilegedPlayer) ) {
  jTextField11.setVisible(true);
  jTextField7.setVisible(true); }
 else {
  jTextField11.setVisible(false);
  jTextField7.setVisible(false);
 };

 BldgIdx = Building.GetBldgPic(WhichBldg);
 jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource( "/resources/" + BldgIdx.SImgIndex() + ".jpg")));
// if ( Building.isLargeBuilding(WhichBldg) )
//  jLabel1.setSize(BldgWidth,BldgSingleHeight * 2);
// else
//  jLabel1.setSize(BldgWidth,BldgSingleHeight);
 

 Building.BldgInfoLabel1( Player, WhichBldg );

};
    /** Creates new form FBuildingInfo */
    public FBuildingInfo(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
	Panel = jPanel1;
	Label1[0] = jTextField1; Label1[1] = jTextField2; Label1[2] = jTextArea1;
	Label1[3] = jTextField4; Label1[4] = jTextField5; Label1[5] = jTextField6;
	Label1[6] = jTextField7;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                jButton1 = new javax.swing.JButton();
                jPanel1 = new javax.swing.JPanel();
                jLabel1 = new javax.swing.JLabel();
                jTextField1 = new javax.swing.JTextField();
                jTextField2 = new javax.swing.JTextField();
                jTextArea1 = new javax.swing.JTextArea();
                jTextField4 = new javax.swing.JTextField();
                jTextField5 = new javax.swing.JTextField();
                jTextField6 = new javax.swing.JTextField();
                jTextField7 = new javax.swing.JTextField();
                jTextField8 = new javax.swing.JTextField();
                jTextField9 = new javax.swing.JTextField();
                jTextField10 = new javax.swing.JTextField();
                jTextField11 = new javax.swing.JTextField();

                setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
                setMinimumSize(new java.awt.Dimension(431, 354));
                setResizable(false);
                getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

                jButton1.setText("OK");
                jButton1.setSelected(true);
                jButton1.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton1ActionPerformed(evt);
                        }
                });
                getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 270, -1, -1));

                jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
                jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 110, 120));

                jTextField1.setEditable(false);
                jTextField1.setFont(new java.awt.Font("Comic Sans MS", 0, 18));
                jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                jTextField1.setText("remaining");
                jPanel1.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, 110, 40));

                jTextField2.setEditable(false);
                jTextField2.setFont(jTextField2.getFont().deriveFont(jTextField2.getFont().getStyle() | java.awt.Font.BOLD, jTextField2.getFont().getSize()+7));
                jTextField2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                jTextField2.setText("Building Name");
                jTextField2.setOpaque(false);
                jPanel1.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 0, 170, 40));

                jTextArea1.setColumns(20);
                jTextArea1.setEditable(false);
                jTextArea1.setFont(new java.awt.Font("Monospaced", 0, 14));
                jTextArea1.setLineWrap(true);
                jTextArea1.setRows(5);
                jTextArea1.setText("Description");
                jTextArea1.setWrapStyleWord(true);
                jTextArea1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
                jTextArea1.setOpaque(false);
                jPanel1.add(jTextArea1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 42, 210, 140));

                jTextField4.setEditable(false);
                jTextField4.setFont(new java.awt.Font("Comic Sans MS", 0, 18));
                jTextField4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                jTextField4.setText("0");
                jPanel1.add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 190, 110, 30));

                jTextField5.setEditable(false);
                jTextField5.setFont(new java.awt.Font("Comic Sans MS", 0, 18));
                jTextField5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                jTextField5.setText("0");
                jPanel1.add(jTextField5, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 225, 110, 30));

                jTextField6.setEditable(false);
                jTextField6.setFont(new java.awt.Font("Comic Sans MS", 0, 18));
                jTextField6.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                jTextField6.setText("0");
                jPanel1.add(jTextField6, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 260, 110, 30));

                jTextField7.setEditable(false);
                jTextField7.setFont(new java.awt.Font("Comic Sans MS", 0, 18));
                jTextField7.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                jTextField7.setText("0");
                jPanel1.add(jTextField7, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 310, 110, 30));

                jTextField8.setEditable(false);
                jTextField8.setFont(new java.awt.Font("Comic Sans MS", 0, 18));
                jTextField8.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                jTextField8.setText("Victory Points");
                jPanel1.add(jTextField8, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 190, 150, 30));

                jTextField9.setEditable(false);
                jTextField9.setFont(new java.awt.Font("Comic Sans MS", 0, 18));
                jTextField9.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                jTextField9.setText("Base Cost");
                jPanel1.add(jTextField9, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 225, 150, 30));

                jTextField10.setEditable(false);
                jTextField10.setFont(new java.awt.Font("Comic Sans MS", 0, 10));
                jTextField10.setText("Cost w/your manned quarries");
                jTextField10.setFocusTraversalPolicyProvider(true);
                jPanel1.add(jTextField10, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 260, 150, 30));

                jTextField11.setEditable(false);
                jTextField11.setFont(new java.awt.Font("Comic Sans MS", 0, 10));
                jTextField11.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                jTextField11.setText("Cost after builder Bonus");
                jPanel1.add(jTextField11, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 310, 150, 30));

                getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 430, 360));

                pack();
        }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
  this.setVisible(false);
  if ( PRGDCurrentRole == SetupAvailableRoles.BuilderSAR )
   Building.ConfirmCard();
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FBuildingInfo dialog = new FBuildingInfo(new javax.swing.JFrame(), true);
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
        private javax.swing.JPanel jPanel1;
        private javax.swing.JTextArea jTextArea1;
        private javax.swing.JTextField jTextField1;
        private javax.swing.JTextField jTextField10;
        private javax.swing.JTextField jTextField11;
        private javax.swing.JTextField jTextField2;
        private javax.swing.JTextField jTextField4;
        private javax.swing.JTextField jTextField5;
        private javax.swing.JTextField jTextField6;
        private javax.swing.JTextField jTextField7;
        private javax.swing.JTextField jTextField8;
        private javax.swing.JTextField jTextField9;
        // End of variables declaration//GEN-END:variables

   // 1 remaining box
   // 2 Building Name
   // 3 Description
   // 4 Vp Count
   // 5 Base Cost
   // 6 Cost w manned quarries
   // 7 Cost after builder bonus
    public javax.swing.JPanel Panel;
    public javax.swing.text.JTextComponent[] Label1 = new javax.swing.text.JTextComponent[7];

}
