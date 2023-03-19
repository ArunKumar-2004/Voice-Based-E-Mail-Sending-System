/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Prototyes;

import Warning.InvalidWarning;
import Warning.MessageSent;
import com.mongodb.BasicDBObject;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import java.awt.Color;
import java.awt.HeadlessException;

import java.io.File;
import java.io.IOException;


import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.bson.Document;

/**
 *
 * @author akvnr
 */
public class DashBoard extends javax.swing.JFrame {

    /**
     * Creates new form DashBoard
     * @throws javax.sound.sampled.UnsupportedAudioFileException
     * @throws java.io.IOException
     * @throws javax.sound.sampled.LineUnavailableException
     */
    
        Long currentFrame;
	Clip clip;
	String status;	
	AudioInputStream audioInputStream;
	static String filePath;
        String date="";
    public DashBoard()  {
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        panelRound2.setVisible(false);
        panelRound3.setVisible(false);
        jLabel11.setVisible(false);
        jLabel18.setVisible(false);
        jLabel17.setVisible(false);
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setBackground(new Color(44, 62, 80));
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);
        
            jTable1.getTableHeader().setDefaultRenderer(headerRenderer);
            jTable1.setBackground(new Color(129,207,224));
        jScrollPane2.setVerticalScrollBar(new ScrollBarCustom());
        JPanel panel = new JPanel();
        //panel.setBackground(new Color(30, 30, 30));
        jScrollPane2.setCorner(JScrollPane.UPPER_RIGHT_CORNER, panel);
        jScrollPane2.getViewport().setBackground(new Color(44,62,80));
        jScrollPane2.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 60), 2));
        SimpleDateFormat dFormat=new SimpleDateFormat("dd-MM-yy");
        Date date1=new Date();
        date=dFormat.format(date1);
    }
    int player=0;
    public int count = 0;
    public int count1 = 0;
    public int Recount=0,his;
    public String Uname="";
    public String EAps, Aps,His,PsS;
    private static final String SECRET_KEY = "my_super_secret_key_ho_ho_ho";
    private static final String SALT = "ssshhhhhhhhhhh!!!!";

    public void getAps(String uid) {

        ConnectionString connectionString = new ConnectionString("mongodb+srv://saran2521:Ak2521@cluster0.ohqpxtl.mongodb.net/?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        com.mongodb.client.MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("VoiceMail");
        MongoCollection col = database.getCollection("CustomerLogin");
        BasicDBObject sq = new BasicDBObject();
        sq.put("_id", uid);
        MongoCursor<Document> cursor = col.find(sq).iterator();
        try {
            if (cursor.hasNext()) {
                Document obj = (Document) cursor.next();
                EAps = (String) obj.get("Apaas");
                Uname = (String) obj.get("name");
            }

        } catch (Exception e) {
        }
    }

    public static String decrypt(String strToDecrypt) {
        try {

            byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0};
            IvParameterSpec ivspec
                    = new IvParameterSpec(iv);
            SecretKeyFactory factory
                    = SecretKeyFactory.getInstance(
                            "PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(
                    SECRET_KEY.toCharArray(), SALT.getBytes(),
                    65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(
                    tmp.getEncoded(), "AES");
            Cipher cipher = Cipher.getInstance(
                    "AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey,
                    ivspec);
            return new String(cipher.doFinal(
                    Base64.getDecoder().decode(strToDecrypt)));
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            System.out.println("Error while decrypting: "
                    + e.toString());
        }
        return null;
    }
      SimpleAudioPlayer audioPlayer;
      void Re()
      {
          ConnectionString connectionString = new ConnectionString("mongodb+srv://saran2521:Ak2521@cluster0.ohqpxtl.mongodb.net/?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        com.mongodb.client.MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("VoiceMail");
        MongoCollection col = database.getCollection("History");
        MongoCursor<Document> cursor = col.find().cursor();
        try {
            while (cursor.hasNext()) {
                Document obj = (Document) cursor.next();
                His = (String) obj.get("_id");
                
            }
     
        } catch (Exception e) {
        }
           if(His.equals(""))
        {
            his=0;
        }
        else
        {
            his=Integer.parseInt(His);
        }
        his=his+1;
      }
      void call() 
      {
            try{
ConnectionString connectionString = new ConnectionString("mongodb+srv://saran2521:Ak2521@cluster0.ohqpxtl.mongodb.net/?retryWrites=true&w=majority");
MongoClientSettings settings = MongoClientSettings.builder()
        .applyConnectionString(connectionString)
        .build();
com.mongodb.client.MongoClient mongoClient = MongoClients.create(settings);
MongoDatabase database = mongoClient.getDatabase("VoiceMail");
MongoCollection col =database.getCollection("History");   
    String[] columnNames = {"Date", "To","Content","Audio Path"};
    DefaultTableModel model = new DefaultTableModel(columnNames,0);
    BasicDBObject query =new BasicDBObject();
    query.put("id",jLabel14.getText());
    MongoCursor cursor=null;
            cursor = col.find(query).cursor();
    while(cursor.hasNext()) {
        Document obj =  (Document)  cursor.next();
       String path = (String)obj.get("Path");
        String date=(String)obj.get("Date");
        String name=(String)obj.get("to");
        String cont=(String)obj.get("content");
        
        model.addRow(new Object[] { date, name,cont,path });
    }
    
    jTable1.setModel(model);
    mongoClient.close();
        }
        catch(Exception e )
                {
                JOptionPane.showMessageDialog(null, e);
                }
      }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        panelRound1 = new test.PanelRound();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        panelRound2 = new test.PanelRound();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        panelRound3 = new test.PanelRound();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel18 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(52, 73, 94));
        jPanel2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel2.setFont(new java.awt.Font("Lucida Console", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Express Mail");

        panelRound1.setBackground(new java.awt.Color(255, 255, 255));
        panelRound1.setRoundBottomRight(100);
        panelRound1.setRoundTopLeft(100);

        jLabel3.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        jLabel3.setText("    Compose");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon("C:\\Users\\ZYRUS_AK\\Downloads\\icons8-composing-mail-48.png")); // NOI18N
        jLabel4.setText("       ");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelRound1Layout = new javax.swing.GroupLayout(panelRound1);
        panelRound1.setLayout(panelRound1Layout);
        panelRound1Layout.setHorizontalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound1Layout.createSequentialGroup()
                .addGap(0, 16, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelRound1Layout.setVerticalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel5.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText(" Logout");
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N

        jLabel19.setIcon(new javax.swing.ImageIcon("C:\\Users\\ZYRUS_AK\\Downloads\\icons8-shutdown-48.png")); // NOI18N

        jLabel20.setIcon(new javax.swing.ImageIcon("C:\\Users\\ZYRUS_AK\\Downloads\\icons8-express-vpn-48.png")); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)))
                        .addGap(31, 31, 31))
                    .addComponent(panelRound1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addGap(1, 1, 1)
                .addComponent(panelRound1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 605, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(57, 57, 57))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38))))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 280, 810));

        jLabel1.setBackground(new java.awt.Color(236, 240, 241));
        jLabel1.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jLabel1.setText(" Test User");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 30, 290, 50));

        panelRound2.setBackground(new java.awt.Color(52, 73, 94));
        panelRound2.setRoundBottomRight(100);
        panelRound2.setRoundTopLeft(100);

        jLabel12.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel12.setText("User ID  :");

        jLabel13.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel13.setText("Email ID :");

        jLabel14.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Jackson2521");

        jLabel15.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("akvnr21052002@mepcoeng.ac.in");

        javax.swing.GroupLayout panelRound2Layout = new javax.swing.GroupLayout(panelRound2);
        panelRound2.setLayout(panelRound2Layout);
        panelRound2Layout.setHorizontalGroup(
            panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelRound2Layout.setVerticalGroup(
            panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelRound2Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel15))
                    .addGroup(panelRound2Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel13)))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel1.add(panelRound2, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 90, 430, 110));

        panelRound3.setBackground(new java.awt.Color(52, 73, 94));
        panelRound3.setRoundBottomRight(100);
        panelRound3.setRoundTopLeft(100);
        panelRound3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("To      :");
        panelRound3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(108, 122, -1, -1));

        jLabel8.setFont(new java.awt.Font("Monospaced", 3, 36)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("VOICE MAIL SYSTEM");
        panelRound3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(184, 47, -1, -1));

        jTextField1.setBackground(new java.awt.Color(52, 73, 94));
        jTextField1.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(255, 255, 255));
        jTextField1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 204, 204)));
        panelRound3.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(197, 121, 425, 25));

        jLabel9.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("From    :");
        panelRound3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(108, 175, -1, -1));

        jTextField2.setBackground(new java.awt.Color(52, 73, 94));
        jTextField2.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jTextField2.setForeground(new java.awt.Color(255, 255, 255));
        jTextField2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 204, 204)));
        panelRound3.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(197, 174, 425, 25));

        jLabel10.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Subject :");
        panelRound3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(108, 227, -1, -1));

        jTextField3.setBackground(new java.awt.Color(52, 73, 94));
        jTextField3.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jTextField3.setForeground(new java.awt.Color(255, 255, 255));
        jTextField3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 204, 204)));
        panelRound3.add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(197, 227, 425, -1));

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jTextArea1.setRows(5);
        jTextArea1.setText("Compose Mail");
        jTextArea1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(0, 204, 204)));
        jScrollPane1.setViewportView(jTextArea1);

        panelRound3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(108, 277, 514, 136));

        jLabel16.setBackground(new java.awt.Color(204, 204, 204));
        jLabel16.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setIcon(new javax.swing.ImageIcon("C:\\Users\\ZYRUS_AK\\Downloads\\icons8-microphone-50.png")); // NOI18N
        jLabel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel16MouseClicked(evt);
            }
        });
        panelRound3.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 430, 50, 60));

        jLabel17.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        panelRound3.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 500, 230, 20));

        jLabel11.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setIcon(new javax.swing.ImageIcon("C:\\Users\\ZYRUS_AK\\Downloads\\icons8-email-send-30.png")); // NOI18N
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });
        panelRound3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 440, 60, 50));

        jTable1.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jTable1.setForeground(new java.awt.Color(255, 255, 255));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "To", "Content", "Audio Path"
            }
        ));
        jTable1.setRowHeight(40);
        jTable1.setShowGrid(true);
        jScrollPane2.setViewportView(jTable1);

        panelRound3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(-110, 0, 960, 550));

        jPanel1.add(panelRound3, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 210, 730, 540));

        jLabel18.setIcon(new javax.swing.ImageIcon("C:\\Users\\ZYRUS_AK\\Downloads\\icons8-male-user-48.png")); // NOI18N
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 30, -1, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1289, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 810, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        this.setVisible(false);
        new Login().setVisible(true);
    }//GEN-LAST:event_jLabel5MouseClicked

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked

        if (count == 0) {
            panelRound2.setVisible(true);
            count = 1;
            
        } else if (count == 1) {
            count = 0;
            panelRound2.setVisible(false);
        }
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked

        if (count1 == 0) {
            String ud = jLabel14.getText();
            String uid = ud.substring(1, ud.length());
            String Em = jLabel15.getText();
            panelRound3.setVisible(true);
            count1 = 1;
            getAps(uid);
            Aps = decrypt(EAps);
            System.out.println(Aps);
            jTextField2.setText(Em.substring(1, Em.length()));
            jTextField2.setEditable(false);
            jScrollPane2.setVisible(false);
        } else if (count1 == 1) {
            count1 = 0;
            panelRound3.setVisible(false);
            jTextField1.setText("");
            jTextField3.setText("");
            jTextArea1.setText("");
            jScrollPane2.setVisible(true);
        }
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        if (count1 == 0) {
            String ud = jLabel14.getText();
            String uid = ud.substring(1, ud.length());
            String Em = jLabel15.getText();
            panelRound3.setVisible(true);
            count1 = 1;
            getAps(uid);
            Aps = decrypt(EAps);
            jTextField2.setText(Em.substring(1, Em.length()));
            jTextField2.setEditable(false);
        } else if (count1 == 1) {
            count1 = 0;
            panelRound3.setVisible(false);
            jTextField1.setText("");
            jTextField3.setText("");
            jTextArea1.setText("");
        }
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseClicked
        
        int i=0;
        try
        {
            AudioFormat af =new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,44100,16,2,4,44100,false);
            DataLine.Info datainfo =new DataLine.Info(TargetDataLine.class, af);
            if(AudioSystem.isLineSupported(datainfo))
            {
                //System.out.println("Not Supported");
            }
            TargetDataLine tl =(TargetDataLine)AudioSystem.getLine(datainfo);
            tl.open();
            tl.start();
            i++;
            Thread audio =new Thread()
            {

                @Override
                public void run()
                {
                    AudioInputStream rec =new AudioInputStream(tl);
                    File out =new File(String.valueOf(his)+".mp3");
                    try
                    {
                        AudioSystem.write(rec, AudioFileFormat.Type.WAVE, out);

                    }
                    catch(IOException e1)
                    {

                    }

                }
            };
            audio.start();
            int a = JOptionPane.showConfirmDialog(null,"Click Yes To Stop Recording","Select",JOptionPane.YES_NO_OPTION);
            if(a==0)
            {
                
                jLabel16.setVisible(false);
                tl.stop();
                tl.close();
                jLabel11.setVisible(true);
                jLabel18.setVisible(true);
                
                
            }
        }
        catch(HeadlessException | LineUnavailableException e)
        {

        }

    }//GEN-LAST:event_jLabel16MouseClicked

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
String  to = jTextField1.getText(); // to address. It can be any like gmail, hotmail etc.
String   from = jTextField2.getText(); // from address. As this is using Gmail SMTP.
boolean inc=false;
String sub=jTextField3.getText();
String  content=jTextArea1.getText();
PsS="C:\\Users\\akvnr\\Documents\\NetBeansProjects\\VoiceChat\\"+String.valueOf(his)+".mp3";
 if(content.equals("Compose Mail"))
 {
     content="";
 }
  Properties prop = new Properties();
  prop.put("mail.smtp.host", "smtp.gmail.com");
  prop.put("mail.smtp.port", "465");
  prop.put("mail.smtp.auth", "true");
  prop.put("mail.smtp.socketFactory.port", "465");
  prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
 
  Session session;
        session = Session.getInstance(prop, new javax.mail.Authenticator() {
           
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, Aps);
            }
        });
 
  try{
            MimeMessage m =new MimeMessage(session);
            m.setFrom(from);
            m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            m.setSubject(sub);
            m.setText(content);
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(content, "text/html");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);
    
   MimeBodyPart attachmentBodyPart = new MimeBodyPart();
     try {
         attachmentBodyPart.attachFile(PsS);
     } catch (IOException ex) {
    
     }
            multipart.addBodyPart(attachmentBodyPart);
            m.setContent(multipart);
            Transport.send(m);
           // JOptionPane.showMessageDialog(null, "Successfully");
            
            
        }
        catch(HeadlessException | MessagingException e)
        {
            //JOptionPane.showMessageDialog(null, "unsuccessfull");
        }
          ConnectionString connectionString = new ConnectionString("mongodb+srv://saran2521:Ak2521@cluster0.ohqpxtl.mongodb.net/?retryWrites=true&w=majority");
MongoClientSettings settings = MongoClientSettings.builder()
        .applyConnectionString(connectionString)
        .build();
com.mongodb.client.MongoClient mongoClient = MongoClients.create(settings);
MongoDatabase database = mongoClient.getDatabase("VoiceMail");
MongoCollection col =database.getCollection("History");
try{
Document doc =new Document("_id",String.valueOf(his) ).append("Date", date).append("id",jLabel14.getText()).append("from",from).append("to",to).append("content",content).append("Path",PsS);
col.insertOne(doc);
         //new SuccessMessage().setVisible(true);
}
catch(Exception e)
{
    InvalidWarning iw =new InvalidWarning();
        iw.jLabel3.setText("Unsuccessfull Operation");
        iw.setVisible(true);  
        inc=true;
}

if(inc==false)
{
    jTextField1.setText("");
    jTextField3.setText("");
    jTextArea1.setText("");
    jLabel11.setVisible(false);
                jLabel18.setVisible(false);
                
                jLabel16.setVisible(true);
                panelRound3.setVisible(false);
                
                count1=0;
                his=his+1;
                call();
                jScrollPane2.setVisible(true);
                //jPanel3.setVisible(true);
                MessageSent ms =new MessageSent();
                ms.setVisible(true);
                
    
}
        
    }//GEN-LAST:event_jLabel11MouseClicked

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        ConnectionString connectionString = new ConnectionString("mongodb+srv://saran2521:Ak2521@cluster0.ohqpxtl.mongodb.net/?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        com.mongodb.client.MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("VoiceMail");
        MongoCollection col = database.getCollection("History");
        MongoCursor<Document> cursor = col.find().cursor();
        try {
            while (cursor.hasNext()) {
                Document obj = (Document) cursor.next();
                His = (String) obj.get("_id");
                
            }
     
        } catch (Exception e) {
        }
           if(His.equals(""))
        {
            his=0;
        }
        else
        {
            his=Integer.parseInt(His);
        }
        his=his+1;
        call();
    }//GEN-LAST:event_formComponentShown

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws Exception {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */

        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                 new DashBoard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    public javax.swing.JLabel jLabel14;
    public javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private test.PanelRound panelRound1;
    public test.PanelRound panelRound2;
    private test.PanelRound panelRound3;
    // End of variables declaration//GEN-END:variables
}
