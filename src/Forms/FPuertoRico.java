/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FPuertoRico.java
 *
 * Created on Sep 14, 2009, 12:35:34 PM
 */

package Forms;

import ClassModules.AutoPlayer;
import ClassModules.Barrel;
import ClassModules.Building;
import ClassModules.CargoShip;
import ClassModules.GovernorCard;
import ClassModules.MainBoard;
import ClassModules.PlayerMat;
import ClassModules.RoleCard;
import ClassModules.Tile;
import ClassModules.TradingHouse;
import ClassModules.VPChit;
import Modules.ImageIdx;
import Modules.MsgBox;
import Modules.PRBasedOnSeat;
import static Modules.Constants.*;
import static Modules.PRGameData.*;
import static Modules.Text1Idx.*;
import Modules.ResourceIdx;
import Modules.PlayerAlertCode;
import Modules.PlayerMatAccess;
import Modules.SetupAvailableRoles;
import Modules.SetupChoiceSeat;
import Modules.SetupNumPlayers;
import Modules.SetupPlayerNames;
import Modules.ShipIdx;
import Modules.ShipTypes;
import Modules.StartingColonistsRemaining;
import Modules.StartingVP;
import Modules.Text1Idx;
import Modules.TilePickIdx;
import Modules.Utilities;
import java.awt.Cursor;
import java.util.EnumSet;
import javax.swing.SwingUtilities;
import resources.LoadResString;

/**
 *
 * @author steve
 */
public class FPuertoRico extends javax.swing.JFrame {
    private static final long serialVersionUID = 1L;
//TODO:: Cursor Management
//TODO:: AI decision making
//TODO:: Popups: e.g. Role cards
//TODO:: Help
//TODO:: Random number generator seems non-random
//TODO:: 2 Player Rules
//TODO:: Internet version
//TODO:: PBEM version
//TODO:: Test Human in other than 1st seat
//TODO:: Event Logging
//TODO:: Quiet mode; few dialogs--player relys on frame titles

    /** Creates new form FPuertoRico */
    public FPuertoRico() {
        super();
        initComponents();
//	this.setIconImage(PRGameData.PRFetchResource.FI("Puerto Rico")); //TODO:: Set Icon
	playermat.iPlayer = SetupPlayerNames.HumanPlayerSPN.ordinal();
	// All number fields
        playermat.Text1[0] = jTextField1; playermat.Text1[1] = jTextField2;
        playermat.Text1[2] = jTextField3; playermat.Text1[3] = jTextField4;
        playermat.Text1[4] = jTextField5; playermat.Text1[5] = jTextField6;
        playermat.Text1[6] = jTextField7; playermat.Text1[7] = jTextField8;
        playermat.Text1[8] = jTextField9; playermat.Text1[9] = jTextField10;
        playermat.Text1[10] = jTextField11; playermat.Text1[11] = jTextField12;
        playermat.Text1[12] = jTextField13; playermat.Text1[13] = jTextField14;
        playermat.Text1[14] = jTextField15; playermat.Text1[15] = jTextField16;
        playermat.Text1[16] = jTextField17; playermat.Text1[17] = jTextField18;
        playermat.Text1[18] = jTextField19; playermat.Text1[19] = jTextField20;
        playermat.Text1[20] = jTextField21; playermat.Text1[21] = jTextField22;
        playermat.Text1[22] = jTextField23; playermat.Text1[23] = jTextField24;
        playermat.Text1[24] = jTextField25; playermat.Text1[25] = jTextField26;
	
	// Plantations
        playermat.Image2 [0] = jLabel2; playermat.Image2 [1] = jLabel3;
        playermat.Image2 [2] = jLabel4; playermat.Image2 [3] = jLabel5;
        playermat.Image2 [4] = jLabel6; playermat.Image2 [5] = jLabel7;
        playermat.Image2 [6] = jLabel8; playermat.Image2 [7] = jLabel9;
        playermat.Image2 [8] = jLabel10; playermat.Image2 [9] = jLabel11;
        playermat.Image2 [10] = jLabel12; playermat.Image2 [11] = jLabel13;

	for (int i = 0; i <= 11; i++){
		playermat.Image2[i].setVisible(false);
	};

	// Governor Card
        playermat.Image3 = jLabel14;
	jLabel14.setVisible(false);
	// Roles
        playermat.Image5 [0] = jLabel16; playermat.Image5 [1] = jLabel17;
        playermat.Image5 [2] = jLabel18; playermat.Image5 [3] = jLabel19;
        playermat.Image5 [4] = jLabel20; playermat.Image5 [5] = jLabel21;
        playermat.Image5 [6] = jLabel22; playermat.Image5 [7] = jLabel23;
	// Ship cargoes
        // access to cargo ships & wharf
	playermat.Picture1[0] = jLayeredPane3;
	playermat.Picture1[1] = jLayeredPane4;
	playermat.Picture1[2] = jLayeredPane5;
	playermat.Picture1[3] = jLayeredPane6;
	playermat.Picture1[4] = jLayeredPane7;
	playermat.Picture1[5] = jLayeredPane8; // Wharf
	LoadImage6(playermat.Picture1[0], ShipTypes.Ship4);
	LoadImage6(playermat.Picture1[1], ShipTypes.Ship5);
	LoadImage6(playermat.Picture1[2], ShipTypes.Ship6);
	LoadImage6(playermat.Picture1[3], ShipTypes.Ship7);
	LoadImage6(playermat.Picture1[4], ShipTypes.Ship8);
	playermat.Picture1[5].setVisible(false); // wharf
	// Tradehouse Goods
        playermat.Image7 [0] = jLabel61; playermat.Image7 [1] = jLabel62;
        playermat.Image7 [2] = jLabel63; playermat.Image7 [3] = jLabel64;
	// Tiles to Choose
	playermat.Image8[0] = jLabel65; playermat.Image8[1] = jLabel66;
	playermat.Image8[2] = jLabel67; playermat.Image8[3] = jLabel68;
	playermat.Image8[4] = jLabel69; playermat.Image8[5] = jLabel70;
	playermat.Image8[6] = jLabel71;
	for (int i = 0; i < 6; i++) {
	playermat.Image8[i].setVisible(false); // Clear the tiles
	};
	playermat.Image8[6].setName(ImageIdx.QuarryEmpty.SImgIndex()); // Init the quarry tile
	// Buildings
        playermat.Image10 [0] = jLabel72;
	playermat.Image10lp = jLayeredPane10;

	playermat.Command1 = Command1; Command1.setVisible(false);
	playermat.Command2 = Command2; Command2.setVisible(false);
//        PRGameData.PRFArray[SetupPlayerNames.HumanPlayerSPN.ordinal()] = playermat;
	jMenu2.setEnabled(false);
    }

private void LoadImage6 (javax.swing.JLayeredPane jlp, ShipTypes sttf){
	  javax.swing.JLabel[] jdl = playermat.Image6;
	  for (java.awt.Component jsl : jlp.getComponents()) {
	   int i = SwingUtilities.getAccessibleIndexInParent(jsl);
	   if (i < (jlp.getComponentCount()-1)) {// exlcude last label which is ship picture
	    jdl[sttf.iidx()+i] = (javax.swing.JLabel)jsl;
	    jdl[sttf.iidx()+i].setVisible(false); //Cargo should not be visible
	   }
	  }
}
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                jLayeredPane1 = new javax.swing.JLayeredPane();
                jTextField1 = new javax.swing.JTextField();
                jTextField2 = new javax.swing.JTextField();
                jTextField3 = new javax.swing.JTextField();
                jTextField4 = new javax.swing.JTextField();
                jLayeredPane12 = new javax.swing.JLayeredPane();
                jTextField5 = new javax.swing.JTextField();
                jTextField6 = new javax.swing.JTextField();
                jTextField7 = new javax.swing.JTextField();
                jTextField8 = new javax.swing.JTextField();
                jTextField9 = new javax.swing.JTextField();
                jTextField10 = new javax.swing.JTextField();
                jTextField11 = new javax.swing.JTextField();
                jTextField12 = new javax.swing.JTextField();
                jLayeredPane11 = new javax.swing.JLayeredPane();
                jTextField13 = new javax.swing.JTextField();
                jTextField14 = new javax.swing.JTextField();
                jTextField15 = new javax.swing.JTextField();
                jTextField16 = new javax.swing.JTextField();
                jTextField17 = new javax.swing.JTextField();
                jTextField18 = new javax.swing.JTextField();
                jTextField19 = new javax.swing.JTextField();
                jTextField20 = new javax.swing.JTextField();
                jTextField21 = new javax.swing.JTextField();
                jTextField22 = new javax.swing.JTextField();
                jTextField23 = new javax.swing.JTextField();
                jTextField24 = new javax.swing.JTextField();
                jTextField25 = new javax.swing.JTextField();
                jTextField26 = new javax.swing.JTextField();
                jLabel1 = new javax.swing.JLabel();
                jLabel2 = new javax.swing.JLabel();
                jLabel3 = new javax.swing.JLabel();
                jLabel4 = new javax.swing.JLabel();
                jLabel5 = new javax.swing.JLabel();
                jLabel6 = new javax.swing.JLabel();
                jLabel7 = new javax.swing.JLabel();
                jLabel8 = new javax.swing.JLabel();
                jLabel9 = new javax.swing.JLabel();
                jLabel10 = new javax.swing.JLabel();
                jLabel11 = new javax.swing.JLabel();
                jLabel12 = new javax.swing.JLabel();
                jLabel13 = new javax.swing.JLabel();
                jLabel14 = new javax.swing.JLabel();
                jLabel15 = new javax.swing.JLabel();
                jLabel16 = new javax.swing.JLabel();
                jLabel17 = new javax.swing.JLabel();
                jLabel18 = new javax.swing.JLabel();
                jLabel19 = new javax.swing.JLabel();
                jLabel20 = new javax.swing.JLabel();
                jLabel21 = new javax.swing.JLabel();
                jLabel22 = new javax.swing.JLabel();
                jLabel23 = new javax.swing.JLabel();
                jLayeredPane2 = new javax.swing.JLayeredPane();
                jLayeredPane3 = new javax.swing.JLayeredPane();
                jLabel30 = new javax.swing.JLabel();
                jLabel32 = new javax.swing.JLabel();
                jLabel33 = new javax.swing.JLabel();
                jLabel24 = new javax.swing.JLabel();
                jLabel31 = new javax.swing.JLabel();
                jLayeredPane4 = new javax.swing.JLayeredPane();
                jLabel34 = new javax.swing.JLabel();
                jLabel35 = new javax.swing.JLabel();
                jLabel36 = new javax.swing.JLabel();
                jLabel37 = new javax.swing.JLabel();
                jLabel38 = new javax.swing.JLabel();
                jLabel25 = new javax.swing.JLabel();
                jLayeredPane5 = new javax.swing.JLayeredPane();
                jLabel39 = new javax.swing.JLabel();
                jLabel40 = new javax.swing.JLabel();
                jLabel41 = new javax.swing.JLabel();
                jLabel42 = new javax.swing.JLabel();
                jLabel43 = new javax.swing.JLabel();
                jLabel44 = new javax.swing.JLabel();
                jLabel26 = new javax.swing.JLabel();
                jLayeredPane6 = new javax.swing.JLayeredPane();
                jLabel45 = new javax.swing.JLabel();
                jLabel46 = new javax.swing.JLabel();
                jLabel47 = new javax.swing.JLabel();
                jLabel48 = new javax.swing.JLabel();
                jLabel49 = new javax.swing.JLabel();
                jLabel50 = new javax.swing.JLabel();
                jLabel51 = new javax.swing.JLabel();
                jLabel27 = new javax.swing.JLabel();
                jLayeredPane7 = new javax.swing.JLayeredPane();
                jLabel52 = new javax.swing.JLabel();
                jLabel53 = new javax.swing.JLabel();
                jLabel54 = new javax.swing.JLabel();
                jLabel55 = new javax.swing.JLabel();
                jLabel56 = new javax.swing.JLabel();
                jLabel57 = new javax.swing.JLabel();
                jLabel58 = new javax.swing.JLabel();
                jLabel59 = new javax.swing.JLabel();
                jLabel28 = new javax.swing.JLabel();
                jLayeredPane8 = new javax.swing.JLayeredPane();
                jLabel29 = new javax.swing.JLabel();
                jLayeredPane9 = new javax.swing.JLayeredPane();
                jLabel65 = new javax.swing.JLabel();
                jLabel66 = new javax.swing.JLabel();
                jLabel67 = new javax.swing.JLabel();
                jLabel68 = new javax.swing.JLabel();
                jLabel69 = new javax.swing.JLabel();
                jLabel70 = new javax.swing.JLabel();
                jLabel71 = new javax.swing.JLabel();
                jLayeredPane10 = new javax.swing.JLayeredPane();
                jLabel72 = new javax.swing.JLabel();
                jLabel61 = new javax.swing.JLabel();
                jLabel62 = new javax.swing.JLabel();
                jLabel63 = new javax.swing.JLabel();
                jLabel64 = new javax.swing.JLabel();
                jLabel60 = new javax.swing.JLabel();
                jTextField27 = new javax.swing.JTextField();
                jTextField28 = new javax.swing.JTextField();
                jTextField29 = new javax.swing.JTextField();
                jTextField30 = new javax.swing.JTextField();
                jTextField31 = new javax.swing.JTextField();
                jTextField32 = new javax.swing.JTextField();
                jTextField33 = new javax.swing.JTextField();
                Command1 = new javax.swing.JButton();
                Command2 = new javax.swing.JButton();
                jMenuBar1 = new javax.swing.JMenuBar();
                jMenu1 = new javax.swing.JMenu();
                MNew = new javax.swing.JMenuItem();
                MSetup = new javax.swing.JMenuItem();
                MExit = new javax.swing.JMenuItem();
                jMenu2 = new javax.swing.JMenu();
                MPlayer2 = new javax.swing.JMenuItem();
                MPlayer3 = new javax.swing.JMenuItem();
                MPlayer4 = new javax.swing.JMenuItem();
                MPlayer5 = new javax.swing.JMenuItem();
                MBuildings = new javax.swing.JMenuItem();
                FewerPopups = new javax.swing.JCheckBoxMenuItem();
                jMenu3 = new javax.swing.JMenu();
                jMenuItem9 = new javax.swing.JMenuItem();
                jMenuItem10 = new javax.swing.JMenuItem();
                jMenuItem11 = new javax.swing.JMenuItem();

                setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
                setResizable(false);

                jTextField1.setBackground(new java.awt.Color(204, 204, 204));
                jTextField1.setEditable(false);
                jTextField1.setFont(new java.awt.Font("Tahoma", 1, 11));
                jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                jTextField1.setText("0");
                jTextField1.setBounds(50, 270, 30, 25);
                jLayeredPane1.add(jTextField1, javax.swing.JLayeredPane.PALETTE_LAYER);

                jTextField2.setBackground(new java.awt.Color(204, 204, 204));
                jTextField2.setEditable(false);
                jTextField2.setFont(new java.awt.Font("Comic Sans MS", 1, 24));
                jTextField2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                jTextField2.setText("0");
                jTextField2.setBounds(30, 360, 40, 40);
                jLayeredPane1.add(jTextField2, javax.swing.JLayeredPane.PALETTE_LAYER);

                jTextField3.setBackground(new java.awt.Color(204, 204, 204));
                jTextField3.setEditable(false);
                jTextField3.setFont(new java.awt.Font("Comic Sans MS", 1, 24));
                jTextField3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                jTextField3.setText("0");
                jTextField3.setBounds(30, 430, 40, 40);
                jLayeredPane1.add(jTextField3, javax.swing.JLayeredPane.PALETTE_LAYER);

                jTextField4.setBackground(new java.awt.Color(204, 204, 204));
                jTextField4.setEditable(false);
                jTextField4.setFont(new java.awt.Font("Comic Sans MS", 1, 24));
                jTextField4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                jTextField4.setText("0");
                jTextField4.setBounds(30, 500, 40, 40);
                jLayeredPane1.add(jTextField4, javax.swing.JLayeredPane.PALETTE_LAYER);

                jTextField5.setBackground(new java.awt.Color(0, 0, 255));
                jTextField5.setEditable(false);
                jTextField5.setFont(new java.awt.Font("Comic Sans MS", 1, 24));
                jTextField5.setForeground(new java.awt.Color(255, 255, 255));
                jTextField5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                jTextField5.setText("0");
                jTextField5.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                jTextField5MouseReleased(evt);
                        }
                });
                jTextField5.setBounds(0, 0, 30, 30);
                jLayeredPane12.add(jTextField5, javax.swing.JLayeredPane.PALETTE_LAYER);

                jTextField6.setEditable(false);
                jTextField6.setFont(new java.awt.Font("Comic Sans MS", 1, 24));
                jTextField6.setForeground(new java.awt.Color(51, 51, 51));
                jTextField6.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                jTextField6.setText("0");
                jTextField6.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                jTextField6MouseReleased(evt);
                        }
                });
                jTextField6.setBounds(60, 0, 30, 30);
                jLayeredPane12.add(jTextField6, javax.swing.JLayeredPane.PALETTE_LAYER);

                jTextField7.setBackground(new java.awt.Color(255, 255, 0));
                jTextField7.setEditable(false);
                jTextField7.setFont(new java.awt.Font("Comic Sans MS", 1, 24));
                jTextField7.setForeground(new java.awt.Color(51, 51, 51));
                jTextField7.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                jTextField7.setText("0");
                jTextField7.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                jTextField7MouseReleased(evt);
                        }
                });
                jTextField7.setBounds(120, 0, 30, 30);
                jLayeredPane12.add(jTextField7, javax.swing.JLayeredPane.PALETTE_LAYER);

                jTextField8.setBackground(new java.awt.Color(204, 153, 0));
                jTextField8.setEditable(false);
                jTextField8.setFont(new java.awt.Font("Comic Sans MS", 1, 24));
                jTextField8.setForeground(new java.awt.Color(51, 51, 51));
                jTextField8.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                jTextField8.setText("0");
                jTextField8.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                jTextField8MouseReleased(evt);
                        }
                });
                jTextField8.setBounds(180, 0, 30, 30);
                jLayeredPane12.add(jTextField8, javax.swing.JLayeredPane.PALETTE_LAYER);

                jTextField9.setBackground(new java.awt.Color(102, 51, 0));
                jTextField9.setEditable(false);
                jTextField9.setFont(new java.awt.Font("Comic Sans MS", 1, 24));
                jTextField9.setForeground(new java.awt.Color(255, 255, 255));
                jTextField9.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                jTextField9.setText("0");
                jTextField9.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                jTextField9MouseReleased(evt);
                        }
                });
                jTextField9.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jTextField9ActionPerformed(evt);
                        }
                });
                jTextField9.setBounds(240, 0, 30, 30);
                jLayeredPane12.add(jTextField9, javax.swing.JLayeredPane.PALETTE_LAYER);

                jLayeredPane12.setBounds(140, 240, 270, 30);
                jLayeredPane1.add(jLayeredPane12, javax.swing.JLayeredPane.DEFAULT_LAYER);

                jTextField10.setBackground(new java.awt.Color(204, 204, 204));
                jTextField10.setEditable(false);
                jTextField10.setFont(new java.awt.Font("Comic Sans MS", 1, 24));
                jTextField10.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                jTextField10.setText("0");
                jTextField10.setBounds(665, 20, 35, 30);
                jLayeredPane1.add(jTextField10, javax.swing.JLayeredPane.PALETTE_LAYER);

                jTextField11.setBackground(new java.awt.Color(204, 204, 204));
                jTextField11.setEditable(false);
                jTextField11.setFont(new java.awt.Font("Comic Sans MS", 1, 24));
                jTextField11.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                jTextField11.setText("0");
                jTextField11.setBounds(730, 40, 35, 30);
                jLayeredPane1.add(jTextField11, javax.swing.JLayeredPane.MODAL_LAYER);

                jTextField12.setBackground(new java.awt.Color(204, 204, 204));
                jTextField12.setEditable(false);
                jTextField12.setFont(new java.awt.Font("Comic Sans MS", 1, 24));
                jTextField12.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                jTextField12.setText("122");
                jTextField12.setBounds(500, 50, 50, 30);
                jLayeredPane1.add(jTextField12, javax.swing.JLayeredPane.PALETTE_LAYER);

                jTextField13.setBackground(new java.awt.Color(0, 0, 255));
                jTextField13.setEditable(false);
                jTextField13.setFont(new java.awt.Font("Comic Sans MS", 1, 24));
                jTextField13.setForeground(new java.awt.Color(255, 255, 255));
                jTextField13.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                jTextField13.setText("11");
                jTextField13.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                jTextField13MouseReleased(evt);
                        }
                });
                jTextField13.setBounds(0, 0, 36, 30);
                jLayeredPane11.add(jTextField13, javax.swing.JLayeredPane.PALETTE_LAYER);

                jTextField14.setEditable(false);
                jTextField14.setFont(new java.awt.Font("Comic Sans MS", 1, 24));
                jTextField14.setForeground(new java.awt.Color(51, 51, 51));
                jTextField14.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                jTextField14.setText("11");
                jTextField14.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                jTextField14MouseReleased(evt);
                        }
                });
                jTextField14.setBounds(0, 40, 36, 30);
                jLayeredPane11.add(jTextField14, javax.swing.JLayeredPane.PALETTE_LAYER);

                jTextField15.setBackground(new java.awt.Color(255, 255, 0));
                jTextField15.setEditable(false);
                jTextField15.setFont(new java.awt.Font("Comic Sans MS", 1, 24));
                jTextField15.setForeground(new java.awt.Color(51, 51, 51));
                jTextField15.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                jTextField15.setText("10");
                jTextField15.setVerifyInputWhenFocusTarget(false);
                jTextField15.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                jTextField15MouseReleased(evt);
                        }
                });
                jTextField15.setBounds(0, 80, 36, 30);
                jLayeredPane11.add(jTextField15, javax.swing.JLayeredPane.PALETTE_LAYER);

                jTextField16.setBackground(new java.awt.Color(204, 153, 0));
                jTextField16.setEditable(false);
                jTextField16.setFont(new java.awt.Font("Comic Sans MS", 1, 24));
                jTextField16.setForeground(new java.awt.Color(51, 51, 51));
                jTextField16.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                jTextField16.setText("9");
                jTextField16.setPreferredSize(new java.awt.Dimension(36, 40));
                jTextField16.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                jTextField16MouseReleased(evt);
                        }
                });
                jTextField16.setBounds(0, 120, 36, 30);
                jLayeredPane11.add(jTextField16, javax.swing.JLayeredPane.PALETTE_LAYER);

                jTextField17.setBackground(new java.awt.Color(102, 51, 0));
                jTextField17.setEditable(false);
                jTextField17.setFont(new java.awt.Font("Comic Sans MS", 1, 24));
                jTextField17.setForeground(new java.awt.Color(255, 255, 255));
                jTextField17.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                jTextField17.setText("9");
                jTextField17.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                jTextField17MouseReleased(evt);
                        }
                });
                jTextField17.setBounds(0, 160, 36, 30);
                jLayeredPane11.add(jTextField17, javax.swing.JLayeredPane.PALETTE_LAYER);

                jLayeredPane11.setBounds(735, 280, 50, 280);
                jLayeredPane1.add(jLayeredPane11, javax.swing.JLayeredPane.DEFAULT_LAYER);

                jTextField18.setBackground(new java.awt.Color(102, 102, 102));
                jTextField18.setEditable(false);
                jTextField18.setFont(new java.awt.Font("Comic Sans MS", 1, 24));
                jTextField18.setForeground(new java.awt.Color(255, 255, 255));
                jTextField18.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                jTextField18.setText("8");
                jTextField18.setBounds(735, 515, 36, 30);
                jLayeredPane1.add(jTextField18, javax.swing.JLayeredPane.PALETTE_LAYER);

                jTextField19.setBackground(new java.awt.Color(204, 204, 204));
                jTextField19.setEditable(false);
                jTextField19.setFont(new java.awt.Font("Tahoma", 1, 11));
                jTextField19.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                jTextField19.setText("0");
                jTextField19.setBounds(500, 250, 30, 20);
                jLayeredPane1.add(jTextField19, javax.swing.JLayeredPane.PALETTE_LAYER);

                jTextField20.setBackground(new java.awt.Color(204, 204, 204));
                jTextField20.setEditable(false);
                jTextField20.setFont(new java.awt.Font("Tahoma", 1, 11));
                jTextField20.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                jTextField20.setText("0");
                jTextField20.setBounds(500, 280, 30, 20);
                jLayeredPane1.add(jTextField20, javax.swing.JLayeredPane.PALETTE_LAYER);

                jTextField21.setBackground(new java.awt.Color(204, 204, 204));
                jTextField21.setEditable(false);
                jTextField21.setFont(new java.awt.Font("Tahoma", 1, 11));
                jTextField21.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                jTextField21.setText("0");
                jTextField21.setBounds(500, 320, 30, 20);
                jLayeredPane1.add(jTextField21, javax.swing.JLayeredPane.PALETTE_LAYER);

                jTextField22.setBackground(new java.awt.Color(204, 204, 204));
                jTextField22.setEditable(false);
                jTextField22.setFont(new java.awt.Font("Tahoma", 1, 11));
                jTextField22.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                jTextField22.setText("0");
                jTextField22.setBounds(500, 360, 30, 20);
                jLayeredPane1.add(jTextField22, javax.swing.JLayeredPane.PALETTE_LAYER);

                jTextField23.setBackground(new java.awt.Color(204, 204, 204));
                jTextField23.setEditable(false);
                jTextField23.setFont(new java.awt.Font("Tahoma", 1, 11));
                jTextField23.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                jTextField23.setText("0");
                jTextField23.setBounds(500, 400, 30, 20);
                jLayeredPane1.add(jTextField23, javax.swing.JLayeredPane.PALETTE_LAYER);

                jTextField24.setBackground(new java.awt.Color(204, 204, 204));
                jTextField24.setEditable(false);
                jTextField24.setFont(new java.awt.Font("Tahoma", 1, 11));
                jTextField24.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                jTextField24.setText("0");
                jTextField24.setBounds(500, 440, 30, 20);
                jLayeredPane1.add(jTextField24, javax.swing.JLayeredPane.PALETTE_LAYER);

                jTextField25.setBackground(new java.awt.Color(204, 204, 204));
                jTextField25.setEditable(false);
                jTextField25.setFont(new java.awt.Font("Tahoma", 1, 11));
                jTextField25.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                jTextField25.setText("0");
                jTextField25.setBounds(500, 480, 30, 20);
                jLayeredPane1.add(jTextField25, javax.swing.JLayeredPane.DRAG_LAYER);

                jTextField26.setBackground(new java.awt.Color(204, 204, 204));
                jTextField26.setEditable(false);
                jTextField26.setFont(new java.awt.Font("Tahoma", 1, 11));
                jTextField26.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                jTextField26.setText("0");
                jTextField26.setBounds(500, 520, 30, 20);
                jLayeredPane1.add(jTextField26, javax.swing.JLayeredPane.PALETTE_LAYER);

                jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/512.jpg"))); // NOI18N
                jLabel1.setBounds(0, 0, 820, 580);
                jLayeredPane1.add(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

                jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/10.jpg"))); // NOI18N
                jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                jLabel2MouseReleased(evt);
                        }
                });
                jLabel2.setBounds(109, 328, 60, 60);
                jLayeredPane1.add(jLabel2, javax.swing.JLayeredPane.PALETTE_LAYER);

                jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/10.jpg"))); // NOI18N
                jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                jLabel3MouseReleased(evt);
                        }
                });
                jLabel3.setBounds(109, 403, 60, 60);
                jLayeredPane1.add(jLabel3, javax.swing.JLayeredPane.PALETTE_LAYER);

                jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/10.jpg"))); // NOI18N
                jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                jLabel4MouseReleased(evt);
                        }
                });
                jLabel4.setBounds(109, 480, 60, 60);
                jLayeredPane1.add(jLabel4, javax.swing.JLayeredPane.PALETTE_LAYER);

                jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/10.jpg"))); // NOI18N
                jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                jLabel5MouseReleased(evt);
                        }
                });
                jLabel5.setBounds(184, 293, 60, 60);
                jLayeredPane1.add(jLabel5, javax.swing.JLayeredPane.PALETTE_LAYER);

                jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/10.jpg"))); // NOI18N
                jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                jLabel6MouseReleased(evt);
                        }
                });
                jLabel6.setBounds(184, 370, 60, 60);
                jLayeredPane1.add(jLabel6, javax.swing.JLayeredPane.PALETTE_LAYER);

                jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/10.jpg"))); // NOI18N
                jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                jLabel7MouseReleased(evt);
                        }
                });
                jLabel7.setBounds(184, 447, 60, 60);
                jLayeredPane1.add(jLabel7, javax.swing.JLayeredPane.PALETTE_LAYER);

                jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/10.jpg"))); // NOI18N
                jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                jLabel8MouseReleased(evt);
                        }
                });
                jLabel8.setBounds(260, 317, 60, 60);
                jLayeredPane1.add(jLabel8, javax.swing.JLayeredPane.PALETTE_LAYER);

                jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/10.jpg"))); // NOI18N
                jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                jLabel9MouseReleased(evt);
                        }
                });
                jLabel9.setBounds(260, 393, 60, 60);
                jLayeredPane1.add(jLabel9, javax.swing.JLayeredPane.PALETTE_LAYER);

                jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/10.jpg"))); // NOI18N
                jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                jLabel10MouseReleased(evt);
                        }
                });
                jLabel10.setBounds(260, 472, 60, 60);
                jLayeredPane1.add(jLabel10, javax.swing.JLayeredPane.PALETTE_LAYER);

                jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/10.jpg"))); // NOI18N
                jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                jLabel11MouseReleased(evt);
                        }
                });
                jLabel11.setBounds(337, 354, 60, 60);
                jLayeredPane1.add(jLabel11, javax.swing.JLayeredPane.PALETTE_LAYER);

                jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/10.jpg"))); // NOI18N
                jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                jLabel12MouseReleased(evt);
                        }
                });
                jLabel12.setBounds(337, 431, 60, 60);
                jLayeredPane1.add(jLabel12, javax.swing.JLayeredPane.PALETTE_LAYER);

                jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/10.jpg"))); // NOI18N
                jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                jLabel13MouseReleased(evt);
                        }
                });
                jLabel13.setBounds(412, 390, 60, 60);
                jLayeredPane1.add(jLabel13, javax.swing.JLayeredPane.PALETTE_LAYER);

                jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/527.jpg"))); // NOI18N
                jLabel14.setBounds(420, 480, 60, 90);
                jLayeredPane1.add(jLabel14, javax.swing.JLayeredPane.PALETTE_LAYER);

                jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/547.jpg"))); // NOI18N
                jLabel15.setBounds(700, 20, 100, 70);
                jLayeredPane1.add(jLabel15, javax.swing.JLayeredPane.PALETTE_LAYER);

                jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/530.jpg"))); // NOI18N
                jLabel16.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                jLabel16MouseReleased(evt);
                        }
                });
                jLabel16.setBounds(540, 230, 60, 40);
                jLayeredPane1.add(jLabel16, javax.swing.JLayeredPane.PALETTE_LAYER);

                jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/531.jpg"))); // NOI18N
                jLabel17.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                jLabel17MouseReleased(evt);
                        }
                });
                jLabel17.setBounds(540, 270, 60, 40);
                jLayeredPane1.add(jLabel17, javax.swing.JLayeredPane.PALETTE_LAYER);

                jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/532.jpg"))); // NOI18N
                jLabel18.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                jLabel18MouseReleased(evt);
                        }
                });
                jLabel18.setBounds(540, 310, 50, 40);
                jLayeredPane1.add(jLabel18, javax.swing.JLayeredPane.PALETTE_LAYER);

                jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/533.jpg"))); // NOI18N
                jLabel19.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                jLabel19MouseReleased(evt);
                        }
                });
                jLabel19.setBounds(550, 350, 40, 40);
                jLayeredPane1.add(jLabel19, javax.swing.JLayeredPane.PALETTE_LAYER);

                jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/534.jpg"))); // NOI18N
                jLabel20.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                jLabel20MouseReleased(evt);
                        }
                });
                jLabel20.setBounds(550, 390, 40, 40);
                jLayeredPane1.add(jLabel20, javax.swing.JLayeredPane.PALETTE_LAYER);

                jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/535.jpg"))); // NOI18N
                jLabel21.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                jLabel21MouseReleased(evt);
                        }
                });
                jLabel21.setBounds(550, 430, 40, 40);
                jLayeredPane1.add(jLabel21, javax.swing.JLayeredPane.PALETTE_LAYER);

                jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/536.jpg"))); // NOI18N
                jLabel22.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                jLabel22MouseReleased(evt);
                        }
                });
                jLabel22.setBounds(550, 470, 40, 40);
                jLayeredPane1.add(jLabel22, javax.swing.JLayeredPane.PALETTE_LAYER);

                jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/537.jpg"))); // NOI18N
                jLabel23.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                jLabel23MouseReleased(evt);
                        }
                });
                jLabel23.setBounds(550, 510, 50, 50);
                jLayeredPane1.add(jLabel23, javax.swing.JLayeredPane.PALETTE_LAYER);

                jLayeredPane3.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                jLayeredPane3MouseReleased(evt);
                        }
                });

                jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/504.jpg"))); // NOI18N
                jLabel30.setOpaque(true);
                jLabel30.setBounds(36, 60, 28, 28);
                jLayeredPane3.add(jLabel30, javax.swing.JLayeredPane.PALETTE_LAYER);
                jLabel30.getAccessibleContext().setAccessibleParent(jLayeredPane3);

                jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/504.jpg"))); // NOI18N
                jLabel32.setOpaque(true);
                jLabel32.setBounds(5, 30, 28, 28);
                jLayeredPane3.add(jLabel32, javax.swing.JLayeredPane.MODAL_LAYER);

                jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/504.jpg"))); // NOI18N
                jLabel33.setOpaque(true);
                jLabel33.setBounds(36, 30, 28, 28);
                jLayeredPane3.add(jLabel33, javax.swing.JLayeredPane.MODAL_LAYER);

                jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/540.jpg"))); // NOI18N
                jLabel24.setOpaque(true);
                jLabel24.setBounds(0, 0, 75, 130);
                jLayeredPane3.add(jLabel24, javax.swing.JLayeredPane.PALETTE_LAYER);

                jLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/504.jpg"))); // NOI18N
                jLabel31.setOpaque(true);
                jLabel31.setBounds(5, 60, 28, 28);
                jLayeredPane3.add(jLabel31, javax.swing.JLayeredPane.MODAL_LAYER);

                jLayeredPane3.setBounds(0, 0, 70, 130);
                jLayeredPane2.add(jLayeredPane3, javax.swing.JLayeredPane.PALETTE_LAYER);

                jLayeredPane4.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                jLayeredPane4MouseReleased(evt);
                        }
                });

                jLabel34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/504.jpg"))); // NOI18N
                jLabel34.setBounds(7, 11, 28, 28);
                jLayeredPane4.add(jLabel34, javax.swing.JLayeredPane.MODAL_LAYER);

                jLabel35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/504.jpg"))); // NOI18N
                jLabel35.setBounds(41, 11, 28, 28);
                jLayeredPane4.add(jLabel35, javax.swing.JLayeredPane.MODAL_LAYER);

                jLabel36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/504.jpg"))); // NOI18N
                jLabel36.setBounds(7, 44, 28, 28);
                jLayeredPane4.add(jLabel36, javax.swing.JLayeredPane.MODAL_LAYER);

                jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/504.jpg"))); // NOI18N
                jLabel37.setBounds(41, 44, 28, 28);
                jLayeredPane4.add(jLabel37, javax.swing.JLayeredPane.MODAL_LAYER);

                jLabel38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/504.jpg"))); // NOI18N
                jLabel38.setBounds(22, 77, 28, 28);
                jLayeredPane4.add(jLabel38, javax.swing.JLayeredPane.MODAL_LAYER);

                jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/541.jpg"))); // NOI18N
                jLabel25.setOpaque(true);
                jLabel25.setBounds(0, 0, 75, 130);
                jLayeredPane4.add(jLabel25, javax.swing.JLayeredPane.PALETTE_LAYER);

                jLayeredPane4.setBounds(83, 0, 80, 130);
                jLayeredPane2.add(jLayeredPane4, javax.swing.JLayeredPane.PALETTE_LAYER);

                jLayeredPane5.setVerifyInputWhenFocusTarget(false);
                jLayeredPane5.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                jLayeredPane5MouseReleased(evt);
                        }
                });

                jLabel39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/504.jpg"))); // NOI18N
                jLabel39.setBounds(8, 17, 28, 28);
                jLayeredPane5.add(jLabel39, javax.swing.JLayeredPane.DRAG_LAYER);

                jLabel40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/504.jpg"))); // NOI18N
                jLabel40.setBounds(41, 17, 28, 28);
                jLayeredPane5.add(jLabel40, javax.swing.JLayeredPane.DRAG_LAYER);

                jLabel41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/504.jpg"))); // NOI18N
                jLabel41.setBounds(8, 50, 28, 28);
                jLayeredPane5.add(jLabel41, javax.swing.JLayeredPane.DRAG_LAYER);

                jLabel42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/504.jpg"))); // NOI18N
                jLabel42.setBounds(41, 50, 28, 28);
                jLayeredPane5.add(jLabel42, javax.swing.JLayeredPane.DRAG_LAYER);

                jLabel43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/504.jpg"))); // NOI18N
                jLabel43.setBounds(8, 83, 28, 28);
                jLayeredPane5.add(jLabel43, javax.swing.JLayeredPane.DRAG_LAYER);

                jLabel44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/504.jpg"))); // NOI18N
                jLabel44.setBounds(41, 83, 28, 28);
                jLayeredPane5.add(jLabel44, javax.swing.JLayeredPane.DRAG_LAYER);

                jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/542.jpg"))); // NOI18N
                jLabel26.setBounds(0, 0, 75, 130);
                jLayeredPane5.add(jLabel26, javax.swing.JLayeredPane.PALETTE_LAYER);

                jLayeredPane5.setBounds(175, 0, 80, 130);
                jLayeredPane2.add(jLayeredPane5, javax.swing.JLayeredPane.PALETTE_LAYER);

                jLayeredPane6.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                jLayeredPane6MouseReleased(evt);
                        }
                });

                jLabel45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/504.jpg"))); // NOI18N
                jLabel45.setBounds(8, 3, 28, 28);
                jLayeredPane6.add(jLabel45, javax.swing.JLayeredPane.DRAG_LAYER);

                jLabel46.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/504.jpg"))); // NOI18N
                jLabel46.setBounds(41, 3, 28, 28);
                jLayeredPane6.add(jLabel46, javax.swing.JLayeredPane.POPUP_LAYER);

                jLabel47.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/504.jpg"))); // NOI18N
                jLabel47.setBounds(8, 35, 28, 28);
                jLayeredPane6.add(jLabel47, javax.swing.JLayeredPane.POPUP_LAYER);

                jLabel48.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/504.jpg"))); // NOI18N
                jLabel48.setBounds(41, 35, 28, 28);
                jLayeredPane6.add(jLabel48, javax.swing.JLayeredPane.POPUP_LAYER);

                jLabel49.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/504.jpg"))); // NOI18N
                jLabel49.setBounds(8, 68, 28, 28);
                jLayeredPane6.add(jLabel49, javax.swing.JLayeredPane.POPUP_LAYER);

                jLabel50.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/504.jpg"))); // NOI18N
                jLabel50.setBounds(41, 68, 28, 28);
                jLayeredPane6.add(jLabel50, javax.swing.JLayeredPane.POPUP_LAYER);

                jLabel51.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/504.jpg"))); // NOI18N
                jLabel51.setBounds(25, 99, 28, 28);
                jLayeredPane6.add(jLabel51, javax.swing.JLayeredPane.POPUP_LAYER);

                jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/543.jpg"))); // NOI18N
                jLabel27.setOpaque(true);
                jLabel27.setBounds(0, 0, 78, 130);
                jLayeredPane6.add(jLabel27, javax.swing.JLayeredPane.PALETTE_LAYER);

                jLayeredPane6.setBounds(83, 0, 78, 130);
                jLayeredPane2.add(jLayeredPane6, javax.swing.JLayeredPane.PALETTE_LAYER);

                jLayeredPane7.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                jLayeredPane7MouseReleased(evt);
                        }
                });

                jLabel52.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/504.jpg"))); // NOI18N
                jLabel52.setBounds(6, 4, 28, 28);
                jLayeredPane7.add(jLabel52, javax.swing.JLayeredPane.MODAL_LAYER);

                jLabel53.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/504.jpg"))); // NOI18N
                jLabel53.setBounds(39, 4, 28, 28);
                jLayeredPane7.add(jLabel53, javax.swing.JLayeredPane.MODAL_LAYER);

                jLabel54.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/504.jpg"))); // NOI18N
                jLabel54.setBounds(6, 36, 28, 28);
                jLayeredPane7.add(jLabel54, javax.swing.JLayeredPane.MODAL_LAYER);

                jLabel55.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/504.jpg"))); // NOI18N
                jLabel55.setBounds(39, 36, 28, 28);
                jLayeredPane7.add(jLabel55, javax.swing.JLayeredPane.MODAL_LAYER);

                jLabel56.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/504.jpg"))); // NOI18N
                jLabel56.setBounds(6, 69, 28, 28);
                jLayeredPane7.add(jLabel56, javax.swing.JLayeredPane.MODAL_LAYER);

                jLabel57.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/504.jpg"))); // NOI18N
                jLabel57.setBounds(39, 69, 28, 28);
                jLayeredPane7.add(jLabel57, javax.swing.JLayeredPane.MODAL_LAYER);

                jLabel58.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/504.jpg"))); // NOI18N
                jLabel58.setBounds(6, 102, 28, 28);
                jLayeredPane7.add(jLabel58, javax.swing.JLayeredPane.MODAL_LAYER);

                jLabel59.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/504.jpg"))); // NOI18N
                jLabel59.setBounds(39, 102, 28, 28);
                jLayeredPane7.add(jLabel59, javax.swing.JLayeredPane.MODAL_LAYER);

                jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/544.jpg"))); // NOI18N
                jLabel28.setOpaque(true);
                jLabel28.setBounds(0, 0, 80, 130);
                jLayeredPane7.add(jLabel28, javax.swing.JLayeredPane.PALETTE_LAYER);

                jLayeredPane7.setBounds(175, 0, 75, 130);
                jLayeredPane2.add(jLayeredPane7, javax.swing.JLayeredPane.PALETTE_LAYER);

                jLayeredPane8.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                jLayeredPane8MouseReleased(evt);
                        }
                });

                jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/545.jpg"))); // NOI18N
                jLabel29.setOpaque(true);
                jLabel29.setBounds(0, 0, 40, 130);
                jLayeredPane8.add(jLabel29, javax.swing.JLayeredPane.PALETTE_LAYER);

                jLayeredPane8.setBounds(260, 0, 40, 130);
                jLayeredPane2.add(jLayeredPane8, javax.swing.JLayeredPane.PALETTE_LAYER);

                jLayeredPane2.setBounds(495, 93, 310, 140);
                jLayeredPane1.add(jLayeredPane2, javax.swing.JLayeredPane.PALETTE_LAYER);

                jLabel65.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/10.jpg"))); // NOI18N
                jLabel65.setPreferredSize(new java.awt.Dimension(40, 40));
                jLabel65.setVerifyInputWhenFocusTarget(false);
                jLabel65.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                jLabel65MouseReleased(evt);
                        }
                });
                jLabel65.setBounds(0, 0, 60, 60);
                jLayeredPane9.add(jLabel65, javax.swing.JLayeredPane.PALETTE_LAYER);

                jLabel66.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/20.jpg"))); // NOI18N
                jLabel66.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                jLabel66MouseReleased(evt);
                        }
                });
                jLabel66.setBounds(67, 0, 60, 60);
                jLayeredPane9.add(jLabel66, javax.swing.JLayeredPane.PALETTE_LAYER);

                jLabel67.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/30.jpg"))); // NOI18N
                jLabel67.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                jLabel67MouseReleased(evt);
                        }
                });
                jLabel67.setBounds(0, 65, 60, 60);
                jLayeredPane9.add(jLabel67, javax.swing.JLayeredPane.PALETTE_LAYER);

                jLabel68.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/40.jpg"))); // NOI18N
                jLabel68.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                jLabel68MouseReleased(evt);
                        }
                });
                jLabel68.setBounds(67, 65, 60, 60);
                jLayeredPane9.add(jLabel68, javax.swing.JLayeredPane.PALETTE_LAYER);

                jLabel69.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/50.jpg"))); // NOI18N
                jLabel69.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                jLabel69MouseReleased(evt);
                        }
                });
                jLabel69.setBounds(0, 130, 60, 60);
                jLayeredPane9.add(jLabel69, javax.swing.JLayeredPane.PALETTE_LAYER);

                jLabel70.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/30.jpg"))); // NOI18N
                jLabel70.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                jLabel70MouseReleased(evt);
                        }
                });
                jLabel70.setBounds(67, 130, 60, 60);
                jLayeredPane9.add(jLabel70, javax.swing.JLayeredPane.PALETTE_LAYER);

                jLabel71.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/60.jpg"))); // NOI18N
                jLabel71.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                jLabel71MouseReleased(evt);
                        }
                });
                jLabel71.setBounds(135, 110, 60, 60);
                jLayeredPane9.add(jLabel71, javax.swing.JLayeredPane.PALETTE_LAYER);

                jLayeredPane9.setBounds(600, 380, 210, 190);
                jLayeredPane1.add(jLayeredPane9, javax.swing.JLayeredPane.PALETTE_LAYER);

                jLayeredPane10.setName("1"); // NOI18N
                jLayeredPane10.setVerifyInputWhenFocusTarget(false);

                jLabel72.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                jLabel72MouseReleased(evt);
                        }
                });
                jLabel72.setBounds(3, 2, 110, 60);
                jLayeredPane10.add(jLabel72, javax.swing.JLayeredPane.PALETTE_LAYER);

                jLayeredPane10.setBounds(22, 28, 465, 200);
                jLayeredPane1.add(jLayeredPane10, javax.swing.JLayeredPane.PALETTE_LAYER);

                jLabel61.setFont(new java.awt.Font("Tahoma", 0, 10));
                jLabel61.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/560.jpg"))); // NOI18N
                jLabel61.setOpaque(true);
                jLabel61.setBounds(637, 269, 19, 19);
                jLayeredPane1.add(jLabel61, javax.swing.JLayeredPane.MODAL_LAYER);

                jLabel62.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/561.jpg"))); // NOI18N
                jLabel62.setOpaque(true);
                jLabel62.setBounds(658, 269, 19, 19);
                jLayeredPane1.add(jLabel62, javax.swing.JLayeredPane.PALETTE_LAYER);

                jLabel63.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/563.jpg"))); // NOI18N
                jLabel63.setOpaque(true);
                jLabel63.setBounds(637, 292, 19, 19);
                jLayeredPane1.add(jLabel63, javax.swing.JLayeredPane.MODAL_LAYER);

                jLabel64.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/562.jpg"))); // NOI18N
                jLabel64.setOpaque(true);
                jLabel64.setBounds(659, 292, 19, 19);
                jLayeredPane1.add(jLabel64, javax.swing.JLayeredPane.PALETTE_LAYER);

                jLabel60.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/550.jpg"))); // NOI18N
                jLabel60.setBounds(620, 250, 70, 120);
                jLayeredPane1.add(jLabel60, javax.swing.JLayeredPane.PALETTE_LAYER);

                jTextField27.setBackground(javax.swing.UIManager.getDefaults().getColor("InternalFrame.minimizeIconBackground"));
                jTextField27.setEditable(false);
                jTextField27.setFont(new java.awt.Font("Comic Sans MS", 0, 12));
                jTextField27.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                jTextField27.setText("Doubloons");
                jTextField27.setVerifyInputWhenFocusTarget(false);
                jTextField27.setBounds(15, 400, 70, 20);
                jLayeredPane1.add(jTextField27, javax.swing.JLayeredPane.PALETTE_LAYER);

                jTextField28.setBackground(javax.swing.UIManager.getDefaults().getColor("InternalFrame.minimizeIconBackground"));
                jTextField28.setEditable(false);
                jTextField28.setFont(new java.awt.Font("Comic Sans MS", 0, 12));
                jTextField28.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                jTextField28.setText("VP Chips");
                jTextField28.setVerifyInputWhenFocusTarget(false);
                jTextField28.setBounds(15, 470, 70, 20);
                jLayeredPane1.add(jTextField28, javax.swing.JLayeredPane.PALETTE_LAYER);

                jTextField29.setBackground(javax.swing.UIManager.getDefaults().getColor("InternalFrame.minimizeIconBackground"));
                jTextField29.setEditable(false);
                jTextField29.setFont(new java.awt.Font("Comic Sans MS", 0, 12));
                jTextField29.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                jTextField29.setText("VP Total");
                jTextField29.setVerifyInputWhenFocusTarget(false);
                jTextField29.setBounds(15, 540, 70, 20);
                jLayeredPane1.add(jTextField29, javax.swing.JLayeredPane.PALETTE_LAYER);

                jTextField30.setBackground(javax.swing.UIManager.getDefaults().getColor("InternalFrame.minimizeIconBackground"));
                jTextField30.setEditable(false);
                jTextField30.setFont(new java.awt.Font("Comic Sans MS", 0, 12));
                jTextField30.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                jTextField30.setText("VP Remaining");
                jTextField30.setBounds(550, 50, 80, 20);
                jLayeredPane1.add(jTextField30, javax.swing.JLayeredPane.PALETTE_LAYER);

                jTextField31.setBackground(javax.swing.UIManager.getDefaults().getColor("InternalFrame.minimizeIconBackground"));
                jTextField31.setEditable(false);
                jTextField31.setFont(new java.awt.Font("Comic Sans MS", 0, 12));
                jTextField31.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                jTextField31.setText("Colonists Remaining");
                jTextField31.setVerifyInputWhenFocusTarget(false);
                jTextField31.setBounds(545, 20, 120, 20);
                jLayeredPane1.add(jTextField31, javax.swing.JLayeredPane.PALETTE_LAYER);

                jTextField32.setBackground(javax.swing.UIManager.getDefaults().getColor("InternalFrame.minimizeIconBackground"));
                jTextField32.setEditable(false);
                jTextField32.setFont(new java.awt.Font("Comic Sans MS", 0, 12));
                jTextField32.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                jTextField32.setText("Trading House");
                jTextField32.setVerifyInputWhenFocusTarget(false);
                jTextField32.setBounds(610, 226, 90, 20);
                jLayeredPane1.add(jTextField32, javax.swing.JLayeredPane.PALETTE_LAYER);

                jTextField33.setBackground(javax.swing.UIManager.getDefaults().getColor("InternalFrame.minimizeIconBackground"));
                jTextField33.setEditable(false);
                jTextField33.setFont(new java.awt.Font("Comic Sans MS", 0, 12));
                jTextField33.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                jTextField33.setText("Resource Bank");
                jTextField33.setVerifyInputWhenFocusTarget(false);
                jTextField33.setBounds(710, 260, 90, 20);
                jLayeredPane1.add(jTextField33, javax.swing.JLayeredPane.PALETTE_LAYER);

                Command1.setText("Done");
                Command1.setSelected(true);
                Command1.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                Command1ActionPerformed(evt);
                        }
                });
                Command1.setBounds(388, 300, 64, 30);
                jLayeredPane1.add(Command1, javax.swing.JLayeredPane.PALETTE_LAYER);

                Command2.setText("Start");
                Command2.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                Command2ActionPerformed(evt);
                        }
                });
                Command2.setBounds(380, 290, 80, 50);
                jLayeredPane1.add(Command2, javax.swing.JLayeredPane.PALETTE_LAYER);

                jMenu1.setText("Game");

                MNew.setText("New");
                MNew.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                MNewMouseReleased(evt);
                        }
                });
                jMenu1.add(MNew);

                MSetup.setText("Setup");
                MSetup.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                MSetupMouseReleased(evt);
                        }
                });
                jMenu1.add(MSetup);

                MExit.setText("Exit");
                MExit.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                MExitMouseReleased(evt);
                        }
                });
                jMenu1.add(MExit);

                jMenuBar1.add(jMenu1);

                jMenu2.setText("View");

                MPlayer2.setText("Player 2");
                MPlayer2.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                MPlayer2MouseReleased(evt);
                        }
                });
                jMenu2.add(MPlayer2);

                MPlayer3.setText("Player 3");
                MPlayer3.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                MPlayer3MouseReleased(evt);
                        }
                });
                jMenu2.add(MPlayer3);

                MPlayer4.setText("Player 4");
                MPlayer4.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                MPlayer4MouseReleased(evt);
                        }
                });
                jMenu2.add(MPlayer4);

                MPlayer5.setText("Player 5");
                MPlayer5.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                MPlayer5MouseReleased(evt);
                        }
                });
                jMenu2.add(MPlayer5);

                MBuildings.setText("Buildings");
                MBuildings.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                MBuildingsMouseReleased(evt);
                        }
                });
                jMenu2.add(MBuildings);

                FewerPopups.setText("Fewer Popups");
                FewerPopups.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                FewerPopupsActionPerformed(evt);
                        }
                });
                jMenu2.add(FewerPopups);

                jMenuBar1.add(jMenu2);

                jMenu3.setText("Help");

                jMenuItem9.setText("Rules");
                jMenuItem9.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                jMenuItem9MouseReleased(evt);
                        }
                });
                jMenu3.add(jMenuItem9);

                jMenuItem10.setText("About Puerto Rico");
                jMenuItem10.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                jMenuItem10MouseReleased(evt);
                        }
                });
                jMenu3.add(jMenuItem10);

                jMenuItem11.setText("Kick");
                jMenuItem11.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                jMenuItem11MouseReleased(evt);
                        }
                });
                jMenu3.add(jMenuItem11);

                jMenuBar1.add(jMenu3);

                setJMenuBar(jMenuBar1);

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 823, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
                layout.setVerticalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 582, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );

                pack();
        }// </editor-fold>//GEN-END:initComponents


    private void MNewMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MNewMouseReleased
     NewMenuSelection(evt);
    }//GEN-LAST:event_MNewMouseReleased

    private void MSetupMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MSetupMouseReleased
      this.setEnabled( false );
      PRSetup.setVisible(true);
    }//GEN-LAST:event_MSetupMouseReleased

    private void MPlayer2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MPlayer2MouseReleased
     PRFArray[SetupPlayerNames.AIPlayerSPN1.ordinal()].form.setVisible(true);
    }//GEN-LAST:event_MPlayer2MouseReleased

    private void MPlayer3MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MPlayer3MouseReleased
     PRFArray[SetupPlayerNames.AIPlayerSPN2.ordinal()].form.setVisible(true);
    }//GEN-LAST:event_MPlayer3MouseReleased

    private void MPlayer4MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MPlayer4MouseReleased
     PRFArray[SetupPlayerNames.AIPlayerSPN3.ordinal()].form.setVisible(true);
    }//GEN-LAST:event_MPlayer4MouseReleased

    private void MPlayer5MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MPlayer5MouseReleased
     PRFArray[SetupPlayerNames.AIPlayerSPN4.ordinal()].form.setVisible(true);
    }//GEN-LAST:event_MPlayer5MouseReleased

    private void MBuildingsMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MBuildingsMouseReleased
     PRBuildings.setVisible(true); // Buildings Selection on View tab
    }//GEN-LAST:event_MBuildingsMouseReleased

    private void jMenuItem9MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem9MouseReleased
// TODO:: About
    }//GEN-LAST:event_jMenuItem9MouseReleased

    private void jMenuItem10MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem10MouseReleased
// TODO:: Rules
    }//GEN-LAST:event_jMenuItem10MouseReleased

    private void jMenuItem11MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem11MouseReleased
//for testing purposes use "Kick" if the games stalls out
//	    PRGDGameExitMet = true; // for testing game end stuff
     Command1.setVisible(true);
    }//GEN-LAST:event_jMenuItem11MouseReleased

    private void MExitMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MExitMouseReleased
     if (PRGDGameExitMet)
      if (VPChit.ExitAtEOG())
       return;
	    
     System.exit(0); // Exit on Game tab
    }//GEN-LAST:event_MExitMouseReleased

    private void jLabel2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseReleased
     PlayerMat.PlantationClick(evt, PlayerMat1Image2Offset); // Twiddle Planation
    }//GEN-LAST:event_jLabel2MouseReleased

    private void jLabel3MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseReleased
     PlayerMat.PlantationClick(evt, PlayerMat1Image2Offset); // Twiddle Planation
    }//GEN-LAST:event_jLabel3MouseReleased

    private void jLabel4MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseReleased
     PlayerMat.PlantationClick(evt, PlayerMat1Image2Offset); // Twiddle Planation
    }//GEN-LAST:event_jLabel4MouseReleased

    private void jLabel5MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseReleased
     PlayerMat.PlantationClick(evt, PlayerMat1Image2Offset); // Twiddle Planation
    }//GEN-LAST:event_jLabel5MouseReleased

    private void jLabel6MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseReleased
     PlayerMat.PlantationClick(evt, PlayerMat1Image2Offset); // Twiddle Planation
    }//GEN-LAST:event_jLabel6MouseReleased

    private void jLabel7MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseReleased
     PlayerMat.PlantationClick(evt, PlayerMat1Image2Offset); // Twiddle Planation
    }//GEN-LAST:event_jLabel7MouseReleased

    private void jLabel8MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseReleased
     PlayerMat.PlantationClick(evt, PlayerMat1Image2Offset); // Twiddle Planation
    }//GEN-LAST:event_jLabel8MouseReleased

    private void jLabel9MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseReleased
     PlayerMat.PlantationClick(evt, PlayerMat1Image2Offset); // Twiddle Planation
    }//GEN-LAST:event_jLabel9MouseReleased

    private void jLabel10MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseReleased
     PlayerMat.PlantationClick(evt, PlayerMat1Image2Offset); // Twiddle Planation
    }//GEN-LAST:event_jLabel10MouseReleased

    private void jLabel11MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseReleased
     PlayerMat.PlantationClick(evt, PlayerMat1Image2Offset); // Twiddle Planation
    }//GEN-LAST:event_jLabel11MouseReleased

    private void jLabel12MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseReleased
     PlayerMat.PlantationClick(evt, PlayerMat1Image2Offset); // Twiddle Planation
    }//GEN-LAST:event_jLabel12MouseReleased

    private void jLabel13MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseReleased
     PlayerMat.PlantationClick(evt, PlayerMat1Image2Offset); // Twiddle Planation
    }//GEN-LAST:event_jLabel13MouseReleased

    private void jLabel16MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseReleased
     RoleClick(evt);
    }//GEN-LAST:event_jLabel16MouseReleased

    private void jLabel17MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel17MouseReleased
     RoleClick(evt);
    }//GEN-LAST:event_jLabel17MouseReleased

    private void jLabel18MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel18MouseReleased
     RoleClick(evt);
    }//GEN-LAST:event_jLabel18MouseReleased

    private void jLabel19MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel19MouseReleased
     RoleClick(evt);
    }//GEN-LAST:event_jLabel19MouseReleased

    private void jLabel20MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel20MouseReleased
     RoleClick(evt);
    }//GEN-LAST:event_jLabel20MouseReleased

    private void jLabel21MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel21MouseReleased
     RoleClick(evt);
    }//GEN-LAST:event_jLabel21MouseReleased

    private void jLabel22MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel22MouseReleased
     RoleClick(evt);
    }//GEN-LAST:event_jLabel22MouseReleased

    private void jLabel23MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel23MouseReleased
     RoleClick(evt);
    }//GEN-LAST:event_jLabel23MouseReleased

    private void jLabel65MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel65MouseReleased
     TileSelect(evt);
    }//GEN-LAST:event_jLabel65MouseReleased

    private void jLabel66MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel66MouseReleased
     TileSelect(evt);
    }//GEN-LAST:event_jLabel66MouseReleased

    private void jLabel67MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel67MouseReleased
     TileSelect(evt);
    }//GEN-LAST:event_jLabel67MouseReleased

    private void jLabel68MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel68MouseReleased
     TileSelect(evt);
    }//GEN-LAST:event_jLabel68MouseReleased

    private void jLabel69MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel69MouseReleased
     TileSelect(evt);
    }//GEN-LAST:event_jLabel69MouseReleased

    private void jLabel70MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel70MouseReleased
     TileSelect(evt);
    }//GEN-LAST:event_jLabel70MouseReleased

    private void jLabel72MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel72MouseReleased
     PlayerMat.BuildingClick(evt); // Twiddle Building
    }//GEN-LAST:event_jLabel72MouseReleased

    private void jLayeredPane3MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLayeredPane3MouseReleased
     ShipClick(evt);
    }//GEN-LAST:event_jLayeredPane3MouseReleased

    private void jLayeredPane4MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLayeredPane4MouseReleased
     ShipClick(evt);
    }//GEN-LAST:event_jLayeredPane4MouseReleased

    private void jLayeredPane5MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLayeredPane5MouseReleased
     ShipClick(evt);
    }//GEN-LAST:event_jLayeredPane5MouseReleased

    private void jLayeredPane6MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLayeredPane6MouseReleased
     ShipClick(evt);
    }//GEN-LAST:event_jLayeredPane6MouseReleased

    private void jLayeredPane7MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLayeredPane7MouseReleased
     ShipClick(evt);
    }//GEN-LAST:event_jLayeredPane7MouseReleased

    private void jLayeredPane8MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLayeredPane8MouseReleased
     ShipClick(evt); //Wharf
    }//GEN-LAST:event_jLayeredPane8MouseReleased

    private void jLabel71MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel71MouseReleased
     TileSelect(evt);
    }//GEN-LAST:event_jLabel71MouseReleased

    private void jTextField13MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField13MouseReleased
      GoodsBankClick(evt);
    }//GEN-LAST:event_jTextField13MouseReleased

    private void jTextField14MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField14MouseReleased
      GoodsBankClick(evt);
    }//GEN-LAST:event_jTextField14MouseReleased

    private void jTextField15MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField15MouseReleased
      GoodsBankClick(evt);
    }//GEN-LAST:event_jTextField15MouseReleased

    private void jTextField17MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField17MouseReleased
      GoodsBankClick(evt);
    }//GEN-LAST:event_jTextField17MouseReleased

    private void jTextField16MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField16MouseReleased
      GoodsBankClick(evt);
    }//GEN-LAST:event_jTextField16MouseReleased

    private void jTextField5MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField5MouseReleased
     PlayerMat.Text1Picked(evt);
    }//GEN-LAST:event_jTextField5MouseReleased

    private void jTextField6MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField6MouseReleased
     PlayerMat.Text1Picked(evt);
    }//GEN-LAST:event_jTextField6MouseReleased

    private void jTextField7MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField7MouseReleased
     PlayerMat.Text1Picked(evt);
    }//GEN-LAST:event_jTextField7MouseReleased

    private void jTextField8MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField8MouseReleased
     PlayerMat.Text1Picked(evt);
    }//GEN-LAST:event_jTextField8MouseReleased

    private void jTextField9MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField9MouseReleased
     PlayerMat.Text1Picked(evt);
    }//GEN-LAST:event_jTextField9MouseReleased

    private void jTextField9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField9ActionPerformed
	    // TODO add your handling code here:
    }//GEN-LAST:event_jTextField9ActionPerformed

    private void Command1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Command1ActionPerformed
     //Player finished with role
     Command1.setVisible(false);
     Command1.setText( "Done");
     //jButton2.setText( "Next");
     //jButton2.setVisible(true);
     GoGoGo();
    }//GEN-LAST:event_Command1ActionPerformed

    private void Command2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Command2ActionPerformed
	Command2.setVisible(false);
	 Utilities.LogIt( PRGDPlayerNameStrings[PRGDCurrentPlayer.ordinal()] + " Becomes Governor");
	 PRGDGameOn = true;
	 boolean Result = false;

	 Result = PlayerAlert(PRGDPrivilegedPlayer, PlayerAlertCode.ChooseARolePAC, " Must Choose a Role");
	 //set roles to Hand Icon
//	 for (int i = SetupAvailableRoles.SettlerSAR.ordinal(); i < iPRGDNumRoles; i++){
         for ( SetupAvailableRoles Role : EnumSet.range(SetupAvailableRoles.SettlerSAR, PRGDNumRoles)) {
          int i = Role.ordinal();
	  this.playermat.Image5[i].setCursor(new Cursor(Cursor.HAND_CURSOR)); // 99 custom
    };
    }//GEN-LAST:event_Command2ActionPerformed

    private void FewerPopupsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FewerPopupsActionPerformed
     GovernorCard.setVerbose(!FewerPopups.isSelected());
    }//GEN-LAST:event_FewerPopupsActionPerformed


void NewMenuSelection(java.awt.event.MouseEvent evt) {
 ShipTypes StartShip = ShipTypes.NoShip;
 StartingVP StartVP = StartingVP.SVP3Player;
 int  StartColonists = 0;
 SetupPlayerNames  SeatChooser;
 int Result = 0;
 FAIPlayer1 playerform;
 SetupChoiceSeat Seat = SetupChoiceSeat.nullSCS;

 if (PRGDGameExitMet)
  if (VPChit.NewAtEOG())
   return;

// Prevent any more setup or new games from being launched
 MNew.setEnabled(false);
 MSetup.setEnabled(false);


 //Deal with Random Number of Players
 if ( PRGDNumPlayers == SetupNumPlayers.SNPlayersRandom )
  PRGDNumPlayers = SetupNumPlayers.values()[Utilities.
	  RandomPick(SetupNumPlayers.SNPlayers3.ordinal(),
	  SetupNumPlayers.SNPlayers5.ordinal())];

 PRGDActualNumPlayers = PRGDNumPlayers.ordinal() + MinPlayers;
 PRGDNumRoles = SetupAvailableRoles.values()[PRGDActualNumPlayers + 2];
// iPRGDNumRoles = PRGDNumRoles.ordinal();

 //Set up the right number of AI Players
 for (int i = SetupPlayerNames.AIPlayerSPN1.ordinal() ;
	i < PRGDActualNumPlayers; i++){
  playerform = new FAIPlayer1(this ,false, SetupPlayerNames.values()[i]);
  PRFArray[i] = playerform.playermat;
  PRFArray[i].form = playerform;
  PRFArray[i].iPlayer = i;
 };

 //Choice of Seat still valid else set it to the first seat?
 if ( !(PRGDChoiceOfSeat == SetupChoiceSeat.RandomSCS) &
	 (PRGDActualNumPlayers < (PRGDChoiceOfSeat.ordinal() + 1)))
  PRGDChoiceOfSeat = SetupChoiceSeat.FirstSCS;

 //Deal with Random Seat
 if ( PRGDChoiceOfSeat == SetupChoiceSeat.RandomSCS )
  PRGDChoiceOfSeat = SetupChoiceSeat.values()[Utilities.RandomPick(
	  SetupChoiceSeat.FirstSCS.ordinal(), PRGDActualNumPlayers - 1)];

 PRGDSeatCentric = (PRBasedOnSeat[])Utilities.resizeArray(PRGDSeatCentric, PRGDActualNumPlayers);
 //Set up Seat Assignments and thus first plantation
 SeatChooser = SetupPlayerNames.HumanPlayerSPN;
 Seat = PRGDChoiceOfSeat;
 for (int i = 0 ; i < PRGDActualNumPlayers; i++) {

  PRGDSeatCentric[Seat.ordinal()].WhichPlayer = SeatChooser;

  SeatChooser = SeatChooser.Next(PRGDActualNumPlayers);
  Seat = Seat.Next(PRGDActualNumPlayers);
 };

 PRGDCurrentSeat = SetupChoiceSeat.FirstSCS;
 PRGDCurrentGovernor = PRGDSeatCentric[PRGDCurrentSeat.ordinal()].WhichPlayer;
 PRFArray[PRGDCurrentGovernor.ordinal()].Image3.setVisible(true); // Show Governor Card

 PRGDPrivilegedPlayer = PRGDCurrentGovernor;
 PRGDCurrentPlayer = PRGDPrivilegedPlayer;
 PRGDPlayerNameStrings = (String[])Utilities.resizeArray(PRGDPlayerNameStrings,
	  PRGDActualNumPlayers);

switch ( PRGDNumPlayers) {
case SNPlayers3:
  StartShip = ShipTypes.Ship4;
  StartVP = StartingVP.SVP3Player;
//  StartColonists = 12
  StartColonists = StartingColonistsRemaining.SCR3Player.SCRIdx();
  playermat.Image5[SetupAvailableRoles.ProspectorSAR1.ordinal()].setVisible(false);
  playermat.Text1[Prospector1MoneyIdx.ordinal()].setVisible(false);
  playermat.Image5[SetupAvailableRoles.ProspectorSAR2.ordinal()].setVisible(false);
  playermat.Text1[Prospector2MoneyIdx.ordinal()].setVisible(false);
  break;
case SNPlayers4:
  StartShip = ShipTypes.Ship5;
  StartVP = StartingVP.SVP4Player;
  StartColonists = StartingColonistsRemaining.SCR4Player.SCRIdx();
  playermat.Image5[SetupAvailableRoles.ProspectorSAR2.ordinal()].setVisible(false);
  playermat.Text1[Prospector2MoneyIdx.ordinal()].setVisible(false);
  break;
case SNPlayers5:
  StartShip = ShipTypes.Ship6;
  StartVP = StartingVP.SVP5Player;
  StartColonists = StartingColonistsRemaining.SCR5Player.SCRIdx();
  PRGDSeatCentric[SetupChoiceSeat.ThirdSCS.ordinal()].StartingPlantation =
	  ResourceIdx.Indigo;
 };

 //Main Map Stuff
 MainBoard.Initialise();
 PRPuertoRico.playermat.Text1[VPRemainingIdx.ordinal()].setText(Integer.toString(
	 StartVP.SVP()));
 PRPuertoRico.playermat.Text1[ColonistsRemainingIdx.ordinal()].setText(Integer.toString(
	 StartColonists));
 PRPuertoRico.playermat.Text1[ColonistsInShipIdx.ordinal()].setText(
	 Integer.toString(PRGDActualNumPlayers));

//Now setup the ships
CargoShip.Initialise (StartShip);

// Setup Player Specific in Seat order
for (int i = SetupChoiceSeat.FirstSCS.ordinal() ; i < PRGDActualNumPlayers; i++){
  PRFArray[PRGDSeatCentric[i].WhichPlayer.ordinal()].Text1[DoubloonsIdx.ordinal()].
	  setText(Integer.toString(PRGDActualNumPlayers - 1));
//	  setText("300"); // For testing Purposes
//  PRFArray[PRGDSeatCentric(i).WhichPlayer).Text1[DoubloonsIdx].getText(90
  PRFArray[PRGDSeatCentric[i].WhichPlayer.ordinal()].Image2[0].setIcon(
	  PRFetchResource.FR(Integer.toString(
	  PRGDSeatCentric[i].StartingPlantation.ordinal() * 10 +
	  ImageIdx.IndigoEmpty.ImgIndex())));
  PRFArray[PRGDSeatCentric[i].WhichPlayer.ordinal()].Image2[0].setName(Integer.toString(
	  PRGDSeatCentric[i].StartingPlantation.ordinal() * 10 + ImageIdx.IndigoEmpty.ImgIndex()));
  Tile.StartPlantation(PRGDSeatCentric[i].StartingPlantation); //tilebag accounting
  PRFArray[PRGDSeatCentric[i].WhichPlayer.ordinal()].Image2[0].setVisible(true);
//  PRFArray[PRGDSeatCentric[i].WhichPlayer].Icon = LoadResPicture(PRIconIdx, vbResIcon)  // TODO:: set Icon

  if (PRGDSeatCentric[i].WhichPlayer == SetupPlayerNames.HumanPlayerSPN) {}
  else {// Main player's form is already visible
   PRFArray[PRGDSeatCentric[i].WhichPlayer.ordinal()].form.setVisible(true);
   PRFArray[PRGDSeatCentric[i].WhichPlayer.ordinal()].form.setTitle(
	  PRSetup.PlayerName[PRGDSeatCentric[i].WhichPlayer.ordinal()].getText());
  };
  PRGDPlayerNameStrings[i] = PRSetup.PlayerName[i].getText(); //TODO:: replace with original text if "" entered.

};

//Find out which players will be run automatically
PRGDAutoPlayer = (boolean[])Utilities.resizeArray(PRGDAutoPlayer, PRGDActualNumPlayers);

PRGDAutoPlayer[SetupPlayerNames.HumanPlayerSPN.ordinal()] = false;//remember Human player will never be automatic!
for (int i = SetupPlayerNames.AIPlayerSPN1.ordinal() ; i < PRGDActualNumPlayers; i++){
 PRGDAutoPlayer[i] = PRSetup.AIPlayer[i].isSelected();
};
AutoPlayer.Initialise();

//Setup the building & barrel databases
Building.Initialise();
Barrel.Initialise();

//Generate First Resources
Result = Tile.Count();
Tile.PlaceTiles();

//Unload Unnecessary forms, update menu controls
PRSetup.dispose();
this.MSetup.setEnabled(false);
PRPuertoRico.MNew.setEnabled(false);
PRPuertoRico.MPlayer2.setEnabled(true);
PRPuertoRico.MPlayer3.setEnabled(true);
PRPuertoRico.MBuildings.setEnabled(true);

if ( PRGDNumPlayers.compareTo(SetupNumPlayers.SNPlayers3) > 0  ) {
 PRPuertoRico.MPlayer4.setEnabled(true);
 if ( PRGDNumPlayers.compareTo(SetupNumPlayers.SNPlayers4) > 0  )
   PRPuertoRico.MPlayer5.setEnabled(true);
 else
  PRPuertoRico.MPlayer5.setVisible(false);
}
else {
 PRPuertoRico.MPlayer4.setVisible(false);
 PRPuertoRico.MPlayer5.setVisible(false);
};

//Kick things off

PRInformation.Label1.setText("This will be a " + Integer.toString(
	PRGDActualNumPlayers) + " player game. " +
	PRGDPlayerNameStrings[PRGDSeatCentric[PRGDChoiceOfSeat.ordinal()].WhichPlayer.ordinal()]
	+ ", you are seat number " + Integer.toString( PRGDChoiceOfSeat.ordinal() + 1)
	+ ". Feel free to place all the windows " +
	"where you would like them, then select START when you are ready.");
// Information.setZorder();
//PRInformation.setModalityType(ModalityType.APPLICATION_MODAL);
//PRInformation.setVisible(true);
MsgBox.Inform(PRInformation.Panel);

jMenu2.setEnabled(true);

this.Command2.setText("START");
Command2.setVisible(true);
};

void RoleClick(java.awt.event.MouseEvent evt) {
 SetupAvailableRoles RoleSelect = SetupAvailableRoles.values()[Utilities.getControlIdx(evt)-PlayerMatImage2RoleOffset];
//ROLE SELECTION
 if ( PRGDGameOn ) {}
 else
  return;
 if (PRGDCurrentRole != SetupAvailableRoles.NullSAR )
  return;
 RoleCard.ConfirmCard(RoleSelect);
};

private void ShipClick(java.awt.event.MouseEvent evt) {

 ShipIdx WhichShipIdx = CargoShip.ShipTypeToIdx(ShipTypes.values()[Utilities.getControlIdx(evt)]);

 if ( PRGDGameOn ) {}
 else
  return;

//can I load the selected Cargo ship?
 if ( PRGDCurrentRole == SetupAvailableRoles.CaptainSAR ) {
//  WhichShipIdx = CargoShip.TypeToIdx(WhichShip);
  if ( CargoShip.LoadCargo(WhichShipIdx) )
//   this.playermat.jButton2.setText( = "Next"
//   this.playermat.jButton2.setVisible(true);
   this.GoGoGo();
 };

};

private void TileSelect(java.awt.event.MouseEvent evt) {
//TILE SELECTION
 TilePickIdx  TileSlot = TilePickIdx.values()[Utilities.getControlIdx(evt)];
 ImageIdx  TileCandidate = ImageIdx.Blank1;
 
 if ( PRGDGameOn ){}
 else
  return;

 TileCandidate = ImageIdx.Lookup(Integer.valueOf(playermat.Image8[TileSlot.ordinal()].getName()));

 if ( PRGDCurrentRole == SetupAvailableRoles.SettlerSAR ) {

  switch ( Tile.TilePick(PRGDCurrentPlayer, TileSlot)){
	  case TPRCOK:
           Tile.ConfirmCard(TileCandidate, "Is this the plantation you want to choose?");
   	   break;
//   Utilities.LogIt( PRGDPlayerNameStrings(PRGDCurrentPlayer.ordinal()) + " Picked " + PRGDResourceNStr(Index)

	  case TPRCTryAgain:
           MsgBox.DoInform("Not a valid selection for you. Try Again!");
   	   break;
	  case TPRCPlantationsFull:
           MsgBox.DoInform("No Room for Additional Plantations!");
           this.GoGoGo();
   	   break;
	  case TPRCTileBagEmpty:
           MsgBox.DoInform("Tile Bag is Empty");
	   GoGoGo();
  };
 };

};

private void GoodsBankClick(java.awt.event.MouseEvent evt) {
 Text1Idx  TextPicked = Text1Idx.values()[Utilities.getControlIdx(evt) + IndigoBankIdx.ordinal()];
// int Result = 0;

 if ( PRGDGameOn ) {} //Don't allow selection if no game has been started!
 else
  return;

// if ( jButton2.setVisible(true); ) {
// if ( PRInformation.isVisible() )
//  return;

 //Extra Pick in Craftsman Phase
 if ( (TextPicked.compareTo(IndigoBankIdx) >= 0  ) & (TextPicked.compareTo(CoffeeBankIdx) <= 0  ) )
  if ( PRGDCurrentRole == SetupAvailableRoles.CraftsmanSAR ) {
   if ( Barrel.PickABarrel(ResourceIdx.values()[TextPicked.ordinal() - IndigoBankIdx.ordinal()]) ) {
//    jButton2.setVisible(true);
    //Reset cursor back to No Drop
    for (Text1Idx i : EnumSet.range( IndigoBankIdx , CoffeeBankIdx)){
     this.playermat.Text1[i.ordinal()].setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // 12 No Drop
    };
    GoGoGo();
   };
  }
//  else
//   Result = Inform.Inform("Not Craftsman Phase!");
};

String RolePlayerString(SetupPlayerNames Player ){

// if ( PRGDCurrentRole == SetupAvailableRoles.NullSAR )
//  RolePlayerString = "";
// else
//  if ( PRGDCurrentRole == SetupAvailableRoles.GoodsNotShippedSAR )
//   RolePlayerString = "Captain Phase: ";
//  else
//   RolePlayerString = LoadResString.ResSs[PRGDCurrentRole.ordinal()] + " Phase: ";

 return (RoleString() + PRGDPlayerNameStrings[Player.ordinal()]);

};

String RoleString(){
 String RoleString = "";

 if ( PRGDCurrentRole == SetupAvailableRoles.NullSAR )
  RoleString = "";
 else
  if ( PRGDCurrentRole == SetupAvailableRoles.GoodsNotShippedSAR )
   RoleString = "Captain Phase: ";
  else
   RoleString = LoadResString.ResSs[PRGDCurrentRole.ordinal()]  + " Phase: ";

 return RoleString;
};

public boolean PlayerAlert(SetupPlayerNames sPlayer  , // TODO:: Find a better home for this method
                PlayerAlertCode AlertCode ,
                String ActionString ){

 int Player = sPlayer.ordinal();
 String PlayerActionString = "";
 boolean PlayerAlert = false;

 if ( PRGDGameOn ) {}
 else
  return false;


 PlayerActionString = PRGDPlayerNameStrings[Player] + " " + ActionString;

 Utilities.LogIt( PlayerActionString);

 this.setTitle(RoleString() + PlayerActionString);

 if ( PRGDAutoPlayer[Player] ) {
  PlayerAlert = true;

  switch ( AlertCode ) {
  case ChooseARolePAC:
   AutoPlayer.ChooseARole(sPlayer);
   break;
//   PlayerAlert = true
  case ChooseATilePAC:
   AutoPlayer.ChooseATile(sPlayer);
   break;
  case ManageColonistsPAC:
   AutoPlayer.ManageColonists(sPlayer);
   break;
  case ChooseABuildingPAC:
   AutoPlayer.ChooseABuilding();
   break;
  case PickATradePAC:
   AutoPlayer.Trade(sPlayer);
   break;
  case PickAGoodToShipPAC:
   AutoPlayer.Ship(PRGDCurrentPlayer);
   break;
//  case PickAShipPAC:
//   PRAutoPlayer.PickAShip();
//   break;
  case PickProductionPrivPAC:
   AutoPlayer.PickProductionPrivilege();
   break;
 // case KickPAC:
 //  switch ( PRGDCurrentRole){
 //  case SettlerSAR:
 //   PRAutoPlayer.ChooseATile(Player);
 //   break;
 //  case BuilderSAR:
 //   PRAutoPlayer.ChooseABuilding();
 //  break;
 //  case CaptainSAR:
 //   PRAutoPlayer.Ship(PRGDCurrentPlayer);
 //  break;
 //  };
  };
 }
 else {
  if ( AlertCode == PlayerAlertCode.ChooseABuildingPAC ) {
   PRBuildings.setVisible(true);
   PRBuildings.Command2.setVisible(true);
  };
  //TODO:: Site for fancy info/confirmation prompts
  RoleCard.InfoCard(PlayerActionString);
//  resultcode = Inform.Inform(PRGDPlayerNameStrings(Player) + " " + ActionString)
 };
 return PlayerAlert;
};

public void EnabledMNew(boolean SetHow) {
 MNew.setEnabled(SetHow);	
}

private int gogogocount = 0;

public void GoGoGo() {
//Continue here until a player (AI or human) requires input from the human
// ResourceIdx  CandidateBarrel;
 boolean  KeepOnGoing = true;
 boolean  AskDone = false;
 // Must be very careful using Global data in this method as it is highly re-entrant!!
 SetupAvailableRoles LocalRole = SetupAvailableRoles.NullSAR;
 SetupPlayerNames LocalPlayer = SetupPlayerNames.HumanPlayerSPN;

// if (gogogocount > 0)
//  return;

 Command2.setVisible(false);
 Command2.setText( "Next");

gogogocount++;

 if ( PRGDGameOn ) {}
 else
  return;

 while (KeepOnGoing) {

  Command2.setVisible(false);
  Command2.setText( "Next");

  MainBoard.FinishPhasing(); //next player
  LocalRole = PRGDCurrentRole;
  LocalPlayer = PRGDCurrentPlayer;
  KeepOnGoing = false;
  AskDone = false;

//  if ( PRGDGameOn ) {}
//  else
//   return;

  if ( LocalRole == SetupAvailableRoles.NullSAR ) { //Need to set the role
   KeepOnGoing = PlayerAlert(LocalPlayer, PlayerAlertCode.ChooseARolePAC, " Must Choose a Role");
   //set roles to Hand Icon
   if ( PRGDAutoPlayer[LocalPlayer.ordinal()] )
     LocalRole = PRGDCurrentRole;
   else
//    for (int i = SetupAvailableRoles.SettlerSAR.ordinal(); i < iPRGDNumRoles; i++){
    for ( SetupAvailableRoles Role : EnumSet.range(SetupAvailableRoles.SettlerSAR, PRGDNumRoles)) {
     int i = Role.ordinal();
     this.playermat.Image5[i].setCursor(new Cursor(Cursor.HAND_CURSOR)); // 99 custom
    };
  };

  switch ( LocalRole) {
   case SettlerSAR:
    Tile.DealWithHacienda(LocalPlayer);
//   PRFArray[LocalPlayer].Zorder;
    KeepOnGoing = PlayerAlert(LocalPlayer, PlayerAlertCode.ChooseATilePAC, " Select a new Plantation");
    break;

   case MayorSAR:
    if  ((Integer.valueOf(PRFArray[LocalPlayer.ordinal()].Text1[SanJuanIdx.ordinal()].getText()) > 0)
       & (Building.VacancyCount(LocalPlayer) > 0) ) {}
    else
     AskDone = true;

//    PRFArray[LocalPlayer.ordinal()].Zorder
    KeepOnGoing = PlayerAlert(LocalPlayer, PlayerAlertCode.ManageColonistsPAC, " Manage Colonists");
    break;

   case BuilderSAR:
//   PRFArray[LocalPlayer.ordinal()].Zorder
   KeepOnGoing = PlayerAlert(LocalPlayer, PlayerAlertCode.ChooseABuildingPAC, " Go ahead & build");

   break;

   case CraftsmanSAR:
   break;

   case TraderSAR:
//   AskDone = true;
//   PRFArray[LocalPlayer.ordinal()].Zorder
    if (TradingHouse.CanITrade(LocalPlayer)) {
     KeepOnGoing = PlayerAlert(LocalPlayer, PlayerAlertCode.PickATradePAC, " Choose a Trade");
     AskDone = true;
    }
    else {
     Utilities.EventAnnounce (PRGDPlayerNameStrings[LocalPlayer.ordinal()] + ", you cannot make a trade!");
//     Inform.Inform(PRGDPlayerNameStrings[LocalPlayer.ordinal()] + ", you have Nothing to Trade!");
     KeepOnGoing = true;
    }
   break;

   case CaptainSAR:
   if (CargoShip.CanIShip(LocalPlayer, LocalRole) ) {
//    PRFArray[LocalPlayer.ordinal()].Zorder
    if (CargoShip.CheckForWharf())
     PRPuertoRico.playermat.Picture1[ShipTypes.PlayerWharf.ordinal()].setVisible(true);
    else
     PRPuertoRico.playermat.Picture1[ShipTypes.PlayerWharf.ordinal()].setVisible(false);
    KeepOnGoing = PlayerAlert(LocalPlayer, PlayerAlertCode.PickAGoodToShipPAC, " Choose a Good to Ship");
   }
   else {
    if ( CargoShip.EveryoneShipped() )
     PRGDCurrentRole = SetupAvailableRoles.GoodsNotShippedSAR;
    KeepOnGoing = true;
    Utilities.EventAnnounce ( PRGDPlayerNameStrings[LocalPlayer.ordinal()] + " Cannot Ship a Good");

   };
   break;

   case ProspectorSAR1:
   case ProspectorSAR2:
   break;

   case GoodsNotShippedSAR:
   if ( CargoShip.AllGoodsStoredAfterShipping() ) {
    KeepOnGoing = true; // All players' goods are stowed.
   }
   else
//    KeepOnGoing = true; // Deal with next player's unstowed goods.
//   CargoShip.GoodsHoused(LocalPlayer);
   if ( PRGDAutoPlayer[LocalPlayer.ordinal()] ) {
     AutoPlayer.HandleGoodsNotShipped(LocalPlayer);
     KeepOnGoing = true;
    }
    else {
     if (Barrel.LeftOverTypesBarrels(LocalPlayer)>0) {
     //display form that allows goods dispersal to compass or warehouse
      PRGoneGoods.Initialise();
      PRGoneGoods.setVisible(true);
      KeepOnGoing = false;
     }
     else {
      CargoShip.GoodsHoused(LocalPlayer);
      KeepOnGoing = true;
     };
    };

   break;

   case NullSAR:
    break;
  };

 }; //Loop

 if ( AskDone & (!PRGDAutoPlayer[LocalPlayer.ordinal()]) ) {
  PRFArray[LocalPlayer.ordinal()].Command1.setText( "Done");
  PRFArray[LocalPlayer.ordinal()].Command1.setVisible(true);
 };

gogogocount--;

};
    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
	    PRCopyright = new FCopyright(PRPuertoRico ,false);
	    PRCopyright.setVisible(true);
//                PRPuertoRico = new FPuertoRico();
//                PRPuertoRico.setVisible(true);
//                PRSetup = new FSetup(new javax.swing.JFrame(), true);
//                PRSetup.setVisible(true);
//                PRGameData.PRSetup.addWindowListener(new java.awt.event.WindowAdapter() {
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                      System.exit(0);
                    }
                });
            }

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JButton Command1;
        private javax.swing.JButton Command2;
        private javax.swing.JCheckBoxMenuItem FewerPopups;
        private javax.swing.JMenuItem MBuildings;
        private javax.swing.JMenuItem MExit;
        private javax.swing.JMenuItem MNew;
        private javax.swing.JMenuItem MPlayer2;
        private javax.swing.JMenuItem MPlayer3;
        private javax.swing.JMenuItem MPlayer4;
        private javax.swing.JMenuItem MPlayer5;
        private javax.swing.JMenuItem MSetup;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabel10;
        private javax.swing.JLabel jLabel11;
        private javax.swing.JLabel jLabel12;
        private javax.swing.JLabel jLabel13;
        private javax.swing.JLabel jLabel14;
        private javax.swing.JLabel jLabel15;
        private javax.swing.JLabel jLabel16;
        private javax.swing.JLabel jLabel17;
        private javax.swing.JLabel jLabel18;
        private javax.swing.JLabel jLabel19;
        private javax.swing.JLabel jLabel2;
        private javax.swing.JLabel jLabel20;
        private javax.swing.JLabel jLabel21;
        private javax.swing.JLabel jLabel22;
        private javax.swing.JLabel jLabel23;
        private javax.swing.JLabel jLabel24;
        private javax.swing.JLabel jLabel25;
        private javax.swing.JLabel jLabel26;
        private javax.swing.JLabel jLabel27;
        private javax.swing.JLabel jLabel28;
        private javax.swing.JLabel jLabel29;
        private javax.swing.JLabel jLabel3;
        private javax.swing.JLabel jLabel30;
        private javax.swing.JLabel jLabel31;
        private javax.swing.JLabel jLabel32;
        private javax.swing.JLabel jLabel33;
        private javax.swing.JLabel jLabel34;
        private javax.swing.JLabel jLabel35;
        private javax.swing.JLabel jLabel36;
        private javax.swing.JLabel jLabel37;
        private javax.swing.JLabel jLabel38;
        private javax.swing.JLabel jLabel39;
        private javax.swing.JLabel jLabel4;
        private javax.swing.JLabel jLabel40;
        private javax.swing.JLabel jLabel41;
        private javax.swing.JLabel jLabel42;
        private javax.swing.JLabel jLabel43;
        private javax.swing.JLabel jLabel44;
        private javax.swing.JLabel jLabel45;
        private javax.swing.JLabel jLabel46;
        private javax.swing.JLabel jLabel47;
        private javax.swing.JLabel jLabel48;
        private javax.swing.JLabel jLabel49;
        private javax.swing.JLabel jLabel5;
        private javax.swing.JLabel jLabel50;
        private javax.swing.JLabel jLabel51;
        private javax.swing.JLabel jLabel52;
        private javax.swing.JLabel jLabel53;
        private javax.swing.JLabel jLabel54;
        private javax.swing.JLabel jLabel55;
        private javax.swing.JLabel jLabel56;
        private javax.swing.JLabel jLabel57;
        private javax.swing.JLabel jLabel58;
        private javax.swing.JLabel jLabel59;
        private javax.swing.JLabel jLabel6;
        private javax.swing.JLabel jLabel60;
        private javax.swing.JLabel jLabel61;
        private javax.swing.JLabel jLabel62;
        private javax.swing.JLabel jLabel63;
        private javax.swing.JLabel jLabel64;
        private javax.swing.JLabel jLabel65;
        private javax.swing.JLabel jLabel66;
        private javax.swing.JLabel jLabel67;
        private javax.swing.JLabel jLabel68;
        private javax.swing.JLabel jLabel69;
        private javax.swing.JLabel jLabel7;
        private javax.swing.JLabel jLabel70;
        private javax.swing.JLabel jLabel71;
        private javax.swing.JLabel jLabel72;
        private javax.swing.JLabel jLabel8;
        private javax.swing.JLabel jLabel9;
        private javax.swing.JLayeredPane jLayeredPane1;
        private javax.swing.JLayeredPane jLayeredPane10;
        private javax.swing.JLayeredPane jLayeredPane11;
        private javax.swing.JLayeredPane jLayeredPane12;
        private javax.swing.JLayeredPane jLayeredPane2;
        private javax.swing.JLayeredPane jLayeredPane3;
        private javax.swing.JLayeredPane jLayeredPane4;
        private javax.swing.JLayeredPane jLayeredPane5;
        private javax.swing.JLayeredPane jLayeredPane6;
        private javax.swing.JLayeredPane jLayeredPane7;
        private javax.swing.JLayeredPane jLayeredPane8;
        private javax.swing.JLayeredPane jLayeredPane9;
        private javax.swing.JMenu jMenu1;
        private javax.swing.JMenu jMenu2;
        private javax.swing.JMenu jMenu3;
        private javax.swing.JMenuBar jMenuBar1;
        private javax.swing.JMenuItem jMenuItem10;
        private javax.swing.JMenuItem jMenuItem11;
        private javax.swing.JMenuItem jMenuItem9;
        private javax.swing.JTextField jTextField1;
        private javax.swing.JTextField jTextField10;
        private javax.swing.JTextField jTextField11;
        private javax.swing.JTextField jTextField12;
        private javax.swing.JTextField jTextField13;
        private javax.swing.JTextField jTextField14;
        private javax.swing.JTextField jTextField15;
        private javax.swing.JTextField jTextField16;
        private javax.swing.JTextField jTextField17;
        private javax.swing.JTextField jTextField18;
        private javax.swing.JTextField jTextField19;
        private javax.swing.JTextField jTextField2;
        private javax.swing.JTextField jTextField20;
        private javax.swing.JTextField jTextField21;
        private javax.swing.JTextField jTextField22;
        private javax.swing.JTextField jTextField23;
        private javax.swing.JTextField jTextField24;
        private javax.swing.JTextField jTextField25;
        private javax.swing.JTextField jTextField26;
        private javax.swing.JTextField jTextField27;
        private javax.swing.JTextField jTextField28;
        private javax.swing.JTextField jTextField29;
        private javax.swing.JTextField jTextField3;
        private javax.swing.JTextField jTextField30;
        private javax.swing.JTextField jTextField31;
        private javax.swing.JTextField jTextField32;
        private javax.swing.JTextField jTextField33;
        private javax.swing.JTextField jTextField4;
        private javax.swing.JTextField jTextField5;
        private javax.swing.JTextField jTextField6;
        private javax.swing.JTextField jTextField7;
        private javax.swing.JTextField jTextField8;
        private javax.swing.JTextField jTextField9;
        // End of variables declaration//GEN-END:variables

   /*
    *  Create some Arrays to allow access to the form. Divisions based on
    * VB6 design.
    */

   public PlayerMatAccess playermat = new PlayerMatAccess(26); // Hardcode number of fields in Text1 catagory from vb6 design
   
}
