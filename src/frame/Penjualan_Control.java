/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package frame;

import db.Koneksi;
import entitiy.KeyPesawat;
import entitiy.KeyTujuan;
import entitiy.Penjualan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import entitiy.Pesawat;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Rifqi
 */
public class Penjualan_Control extends javax.swing.JFrame {

    /**
     * Creates new form Penjualan_Control
     */
    Penjualan penjualan;
    BufferedImage bImage;
    Statement st;
    ResultSet rs;
    PreparedStatement ps;
    String qryPesawat = "SELECT * FROM pesawat";
    String qryTujuan = "SELECT * FROM tujuan";
    
    
    public Penjualan_Control() {
        initComponents();
        resetTable("");
        setLocationRelativeTo(null);
        Tfidpenjul.setVisible(false);
        
        cbSetModel1(qryPesawat, "id", "nama","harga_pesawat","foto_pesawat", Cbpesawat);
        cbSetModel2(qryTujuan, "id_tujuan", "nama_tujuan","harga_tujuan", Cbtujuan);
    }
    //combo box pesawat start
      public Vector getCbData1(String qry, String key, String value, String harga ,String foto) {
        Vector v = new Vector();
        try {
            Koneksi koneksi = new Koneksi();
            Connection connection = koneksi.getConnection();
            
            st = connection.createStatement();
            rs = st.executeQuery(qry);
            while(rs.next()) {
                v.addElement(new KeyPesawat(rs.getInt(key),
                                          rs.getString(value),
                                          rs.getFloat(harga),
                                          rs.getBlob(foto)
                ));
                
            }
        } catch (SQLException ex) {
            System.err.println("Error getData() : "+ex);
        }
        return v;
    }
       public void cbSetModel1 (String qry, String key, String value,String harga,String foto,  JComboBox<String> job) {
        Vector v = getCbData1(qry, key, value,harga ,foto);
        DefaultComboBoxModel model;
        model = new DefaultComboBoxModel (v);
        job.setModel(model);
    }
    
    public void cbSetSelected1(String data, JComboBox<String> cb) {
        KeyPesawat item = new KeyPesawat();
        for (int i = 0; i < cb.getItemCount(); i++)
        {
            cb.setSelectedIndex(i);
            item.setValueP(((KeyPesawat)cb.getSelectedItem()).getValueP());
            if (item.getValueP().equalsIgnoreCase(data))
            {
                cb.setSelectedIndex(i);
                break;
            }
        }
    }
    //combo box pesawat end
    
    //combo box tujuan start
    public Vector getCbData2(String qry, String key, String value, String harga) {
        Vector v = new Vector();
        try {
            Koneksi koneksi = new Koneksi();
            Connection connection = koneksi.getConnection();
            st = connection.createStatement();
            rs = st.executeQuery(qry);
            while(rs.next()) {
                v.addElement(new KeyTujuan(rs.getInt(key),
                                          rs.getString(value),
                                          rs.getFloat(harga)
                                          
                ));
                
            }
        } catch (SQLException ex) {
            System.err.println("Error getData() : "+ex);
        }
        return v;
    }
       public void cbSetModel2 (String qry, String key, String value,String harga, JComboBox<String> job) {
        Vector v = getCbData2(qry, key, value,harga);
        DefaultComboBoxModel model;
        model = new DefaultComboBoxModel (v);
        job.setModel(model);
    }
    
    public void cbSetSelected2(String data, JComboBox<String> cb) {
        KeyTujuan item = new KeyTujuan();
        for (int i = 0; i < cb.getItemCount(); i++)
        {
            cb.setSelectedIndex(i);
            item.setValueT(((KeyTujuan)cb.getSelectedItem()).getValueT());
            if (item.getValueT().equalsIgnoreCase(data))
            {
                cb.setSelectedIndex(i);
                break;
            }
        }
    }
     //combo box tujuan end
     public BufferedImage getBufferedImage(Blob imageBlob) {
        InputStream binaryStream = null;
        BufferedImage b = null;
        try {
            binaryStream = imageBlob.getBinaryStream();
            b = ImageIO.read(binaryStream);
        } catch (SQLException | IOException ex) {
            System.err.println("Error getBufferedImage : "+ex);
        }
        return b;
    }
     
     public ArrayList<Penjualan> getPenjualanList (String keyword) {
        ArrayList<Penjualan> penjualanList = new ArrayList<Penjualan>();
        Koneksi koneksi = new Koneksi();
        Connection connection = koneksi.getConnection();
        
        PreparedStatement ps;
        ResultSet rs;
        String query = "SELECT penjualan.*, pesawat.*,tujuan.* FROM penjualan "
                    + "INNER JOIN pesawat ON penjualan.id_pesawat = pesawat.id INNER JOIN tujuan ON penjualan.id_tujuan = tujuan.id_tujuan";
        String order = " ORDER BY penjualan.id_penjualan";
        if(!keyword.equals(""))
            query = query+ " WHERE nama_penumpang like ? OR nama like ?";
//        like ? OR nama like ? OR nama_tujuan like 
        query = query+order;
        try{
            ps = connection.prepareStatement(query);
            if(!keyword.equals("")){
                ps.setString(1,"%"+Tfcari.getText()+"%");
                ps.setString(2, "%"+Tfcari.getText()+"%");
            }
            rs = ps.executeQuery();
            while(rs.next()){
                penjualan = new Penjualan(
                        rs.getInt("penjualan.id_penjualan"),
                        rs.getString("tanggal"),
                        rs.getString("nama_penumpang"),
                        rs.getString("asal"),
                        rs.getInt("jumlah_tiket"),
                        rs.getString("jenis_tiket"),
                        rs.getFloat("total_harga"),
                        rs.getInt("id_pesawat"),
                        rs.getString("pesawat.nama"),
                        rs.getFloat("pesawat.harga_pesawat"),
                        rs.getBlob("pesawat.foto_pesawat"),
                        rs.getInt("id_tujuan"),
                        rs.getString("tujuan.nama_tujuan"),
                        rs.getFloat("tujuan.harga_tujuan")
                        );
                penjualanList.add(penjualan);
            }
        } catch(SQLException ex){
            System.err.println("ERROR getPenjualanList : "+ex);
            System.err.println(ex.getMessage());
        }
        return penjualanList;
     }
      public void selectPenjualan(String keyword){
        ArrayList<Penjualan> list;
        list = getPenjualanList(keyword);
        DefaultTableModel model = (DefaultTableModel)Tpenjualan.getModel();
        Object[] row =  new Object[14];
        
        for (int i=0; i<list.size(); i++){
            row[0] = list.get(i).getId();
            row[1] = list.get(i).getTanggal();
            row[2] = list.get(i).getNamaPenumpang();
            row[3] = list.get(i).getAsal();
            row[4] = list.get(i).getJumlahTiket();
            row[5] = list.get(i).getJenisTiket();
            row[6] = list.get(i).getTotal();
            row[7] = list.get(i).getPesawat().getId();
            row[8] = list.get(i).getPesawat().getNamaPesawat();
            row[9] = list.get(i).getPesawat().getHargaPesawat();
            row[10] = list.get(i).getPesawat().getFotoPesawat();
             row[11] = list.get(i).getTujuan().getId();
             row[12] = list.get(i).getTujuan().getNamaTujuan();
             row[13] = list.get(i).getTujuan().getHargaTujuan();
            model.addRow(row);
        }
    }
    
    public final void resetTable (String keyword){
        DefaultTableModel model = (DefaultTableModel)Tpenjualan.getModel();
        model.setRowCount(0);
        selectPenjualan(keyword);
    }
    
    public void RbJenisTiketSetSelected(String jenisTiket){
        if(jenisTiket.equals("Ekonomi")){
            Rbekonomi.setSelected(true);
            Tfhj.setText("40000");
        }else {
            Rbbisnis.setSelected(true); 
          Tfhj.setText("70000");
        }
    }
    
    public String RbJenisTiketGetSelected(){
        if(Rbekonomi.isSelected())
              
            return "Ekonomi";
        else if (Rbbisnis.isSelected())
                 
            return "Bisnis";
        else
        return"";
    }
     public Date getFormattedDate(String tanggal){
          SimpleDateFormat tglf = new SimpleDateFormat("yyyy-MM-dd");
        try{
            Date tanggalLahir = (Date) tglf.parse(tanggal);
            return tanggalLahir;
        }catch (ParseException ex){
            System.err.println("Error Tanggal : "+ex);
            return new Date(System.currentTimeMillis());
        }
    }
     public void reset(){
        Tfidpenjul.setText("");
        Tfnama.setText("");
        Tfjumlah.setText("");
        Tfhj.setText("");
        Tfhp.setText("");
        Tfht.setText("");
        Tftotal.setText("");
        jDateChooser1.setDate(null);
        Lgambar.setIcon(new ImageIcon(""));
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        Cbpesawat = new javax.swing.JComboBox<>();
        Cbtujuan = new javax.swing.JComboBox<>();
        Rbekonomi = new javax.swing.JRadioButton();
        Rbbisnis = new javax.swing.JRadioButton();
        jPanel6 = new javax.swing.JPanel();
        Lgambar = new javax.swing.JLabel();
        Tfidpenjul = new javax.swing.JTextField();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        Tfnama = new javax.swing.JTextField();
        Tfjumlah = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        Tfhp = new javax.swing.JTextField();
        Tfht = new javax.swing.JTextField();
        Tfhj = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        Tftotal = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tpenjualan = new javax.swing.JTable();
        Tfcari = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        Bcetak = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        Bkembali = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 204, 153));

        jPanel2.setBackground(new java.awt.Color(255, 255, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Input Tiket"));

        Cbpesawat.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        Cbpesawat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CbpesawatActionPerformed(evt);
            }
        });

        Cbtujuan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        Cbtujuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CbtujuanActionPerformed(evt);
            }
        });

        buttonGroup1.add(Rbekonomi);
        Rbekonomi.setText("Ekonomi");
        Rbekonomi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RbekonomiActionPerformed(evt);
            }
        });

        buttonGroup1.add(Rbbisnis);
        Rbbisnis.setText("Bisnis");
        Rbbisnis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RbbisnisActionPerformed(evt);
            }
        });

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Lgambar, javax.swing.GroupLayout.DEFAULT_SIZE, 507, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Lgambar, javax.swing.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
                .addContainerGap())
        );

        Tfjumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TfjumlahActionPerformed(evt);
            }
        });

        jLabel2.setText("TANGGAL BERANGKAT");

        jLabel3.setText("NAMA PENUMPANG");

        jLabel4.setText("JUMLAH TIKET");

        jLabel5.setText("JENIS KURSI");

        jLabel6.setText("LIST PESAWAT");

        jLabel7.setText("LIST TUJUAN");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(Tfjumlah, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                                        .addComponent(Rbekonomi)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Rbbisnis)
                                .addGap(56, 56, 56))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                                    .addComponent(Tfnama))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(Tfidpenjul, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Cbpesawat, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Cbtujuan, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(Tfidpenjul, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Cbpesawat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))))
                .addGap(4, 4, 4)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Cbtujuan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Tfnama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Tfjumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Rbekonomi)
                            .addComponent(Rbbisnis)
                            .addComponent(jLabel5)))
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 204));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Verifikasi Pembayaran"));

        jButton2.setText("Hitung");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Simpan");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Edit");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Hapus");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("RESET");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel8.setText("HARGA PESAWAT");

        jLabel9.setText("HARGA TUJUAN");

        jLabel10.setText("HARGA KURSI");

        jLabel11.setText("TOTAL");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel8))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel11))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Tftotal, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton6)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(Tfhp, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                            .addComponent(Tfht, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                            .addComponent(Tfhj, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Tfhp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Tfht, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Tfhj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(12, 12, 12)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Tftotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(50, 50, 50)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 204));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Data Penjualan"));

        Tpenjualan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "id_penjualan", "Tanggal Berangkat", "Nama Penumpang", "Asal", "Jumlah Tiket", "Jenis Tiket", "Total", "id_pesawat", "Pesawat", "harga pesawat", "foto_pesawat", "id tujuan", "Tujuan", "harga tujuan"
            }
        ));
        Tpenjualan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TpenjualanMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Tpenjualan);
        if (Tpenjualan.getColumnModel().getColumnCount() > 0) {
            Tpenjualan.getColumnModel().getColumn(0).setMinWidth(0);
            Tpenjualan.getColumnModel().getColumn(0).setPreferredWidth(0);
            Tpenjualan.getColumnModel().getColumn(0).setMaxWidth(0);
            Tpenjualan.getColumnModel().getColumn(7).setMinWidth(0);
            Tpenjualan.getColumnModel().getColumn(7).setPreferredWidth(0);
            Tpenjualan.getColumnModel().getColumn(7).setMaxWidth(0);
            Tpenjualan.getColumnModel().getColumn(9).setMinWidth(0);
            Tpenjualan.getColumnModel().getColumn(9).setPreferredWidth(0);
            Tpenjualan.getColumnModel().getColumn(9).setMaxWidth(0);
            Tpenjualan.getColumnModel().getColumn(10).setMinWidth(0);
            Tpenjualan.getColumnModel().getColumn(10).setPreferredWidth(0);
            Tpenjualan.getColumnModel().getColumn(10).setMaxWidth(0);
            Tpenjualan.getColumnModel().getColumn(11).setMinWidth(0);
            Tpenjualan.getColumnModel().getColumn(11).setPreferredWidth(0);
            Tpenjualan.getColumnModel().getColumn(11).setMaxWidth(0);
            Tpenjualan.getColumnModel().getColumn(13).setMinWidth(0);
            Tpenjualan.getColumnModel().getColumn(13).setPreferredWidth(0);
            Tpenjualan.getColumnModel().getColumn(13).setMaxWidth(0);
        }

        Tfcari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TfcariKeyTyped(evt);
            }
        });

        jLabel12.setText("CARI");

        Bcetak.setText("Cetak");
        Bcetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BcetakActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1158, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(Bcetak, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Tfcari, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Tfcari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(Bcetak))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 204));
        jLabel1.setText("Kelola Penjualan");

        Bkembali.setText("Kembali");
        Bkembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BkembaliActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(Bkembali)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1)))
                        .addGap(6, 6, 6)))
                .addGap(14, 14, 14))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(Bkembali)))
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void RbekonomiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RbekonomiActionPerformed
     Tfhj.setText("40000");
    }//GEN-LAST:event_RbekonomiActionPerformed

    private void CbpesawatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CbpesawatActionPerformed
       Tfhp.setText(String.valueOf(((KeyPesawat)Cbpesawat.getSelectedItem()).getHargaP()));
       Blob blob = (Blob) ((KeyPesawat)Cbpesawat.getSelectedItem()).getFotoP();  
           bImage = getBufferedImage(blob);
           Lgambar.setIcon(new ImageIcon(bImage));
    }//GEN-LAST:event_CbpesawatActionPerformed

    private void CbtujuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CbtujuanActionPerformed
        Tfht.setText(String.valueOf(((KeyTujuan)Cbtujuan.getSelectedItem()).getHargaT()));
    }//GEN-LAST:event_CbtujuanActionPerformed

    private void TpenjualanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TpenjualanMouseClicked
        reset();
        int i = Tpenjualan.getSelectedRow();
            DefaultTableModel model  = (DefaultTableModel)Tpenjualan.getModel();
            Tfidpenjul.setText(model.getValueAt(i, 0).toString());
            jDateChooser1.setDate(getFormattedDate(model.getValueAt(i, 1).toString()));
            Tfnama.setText(model.getValueAt(i, 2).toString());
            Tfjumlah.setText(model.getValueAt(i, 4).toString());
            RbJenisTiketSetSelected(model.getValueAt(i, 5).toString());
            cbSetModel1(qryPesawat, "id", "nama", "harga_pesawat", "foto_pesawat", Cbpesawat);
            cbSetSelected1(model.getValueAt(i, 8).toString(), Cbpesawat);
            cbSetModel2(qryTujuan, "id_tujuan", "nama_tujuan", "harga_tujuan", Cbtujuan);
            cbSetSelected2(model.getValueAt(i, 12).toString(), Cbtujuan);
//        Blob blob = (Blob) model.getValueAt(i, 10);  
//           bImage = getBufferedImage(blob);
//           Lgambar.setIcon(new ImageIcon(bImage));
    }//GEN-LAST:event_TpenjualanMouseClicked

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        resetTable("");
    }//GEN-LAST:event_formWindowActivated

    private void TfcariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TfcariKeyTyped
        resetTable(Tfcari.getText());
    }//GEN-LAST:event_TfcariKeyTyped

    private void RbbisnisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RbbisnisActionPerformed
        Tfhj.setText("70000");
    }//GEN-LAST:event_RbbisnisActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        float hargaP =Float.parseFloat(Tfhp.getText());
         float hargaT =Float.parseFloat(Tfht.getText());
          int jumlah =Integer.parseInt(Tfjumlah.getText());
          float hargaJ =Float.parseFloat(Tfhj.getText());
          float total = (hargaP + hargaT+hargaJ) *jumlah;
          Tftotal.setText(String.valueOf(total));
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        SimpleDateFormat tglf = new SimpleDateFormat("yyyy-MM-dd") ;
        try{  
        Koneksi koneksi = new Koneksi();
            Connection con = koneksi.getConnection();
            PreparedStatement ps;
            String executeQuery = "insert into penjualan "+ "(tanggal, nama_penumpang, asal,jumlah_tiket,jenis_tiket,total_harga,id_pesawat,id_tujuan) values (?,?,?,?,?,?,?,?)";
                ps = con.prepareStatement (executeQuery) ;
                ps.setString (1, tglf.format(jDateChooser1.getDate()));
                ps.setString (2, Tfnama.getText());
                ps.setString (3, "Kalimantan");
                ps.setInt(4, Integer.parseInt(Tfjumlah.getText()));
                ps.setString(5, RbJenisTiketGetSelected());
                ps.setFloat(6, Float.parseFloat(Tftotal.getText()));
                ps.setInt(7, ((KeyPesawat)Cbpesawat.getSelectedItem()).getKeyP());
                ps.setInt(8, ((KeyTujuan)Cbtujuan.getSelectedItem()).getKeyT());
                
                 ps.executeUpdate();
                 JOptionPane.showMessageDialog(null, "Data Berhasil disimpan");
          }catch(SQLException ex){
            System.err.println(ex);
        }
           resetTable("");
           reset();
                  
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if (Tfidpenjul.getText().equals("")){
            JOptionPane.showMessageDialog(
                    null,
                    "Silahkan Klik dulu tablenya", "Pesan Kesalahan",
                    JOptionPane.ERROR_MESSAGE
            );
        }else{ 
       
        int result= JOptionPane.showConfirmDialog(this,"Apakah Anda Yakin Ingin mengubah Data ","Edit",
                JOptionPane.YES_NO_OPTION);
        if (result==0){
        SimpleDateFormat tglf = new SimpleDateFormat("yyyy-MM-dd") ;
        try{  
        Koneksi koneksi = new Koneksi();
            Connection con = koneksi.getConnection();
            PreparedStatement ps;
            String executeQuery = "update penjualan set "+ "tanggal=?, nama_penumpang=?, asal=?,jumlah_tiket=?,jenis_tiket=?,total_harga=?,id_pesawat=?,id_tujuan=? where id_penjualan=?";
                ps = con.prepareStatement (executeQuery) ;
                ps.setString (1, tglf.format(jDateChooser1.getDate()));
                ps.setString (2, Tfnama.getText());
                ps.setString (3, "Kalimantan");
                ps.setInt(4, Integer.parseInt(Tfjumlah.getText()));
                ps.setString(5, RbJenisTiketGetSelected());
                ps.setFloat(6, Float.parseFloat(Tftotal.getText()));
                ps.setInt(7, ((KeyPesawat)Cbpesawat.getSelectedItem()).getKeyP());
                ps.setInt(8, ((KeyTujuan)Cbtujuan.getSelectedItem()).getKeyT());
                
                 ps.setInt(9, (Integer.parseInt(Tfidpenjul.getText())));
                 JOptionPane.showMessageDialog(null, "Data Berhasil diubah");
                 ps.executeUpdate();
          }catch(SQLException ex){
            System.err.println(ex);
        }
        }
           resetTable("");
           reset();
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
              int i = Tpenjualan.getSelectedRow();
         if (Tfidpenjul.getText().equals("")){
            JOptionPane.showMessageDialog(
                    null,
                    "Silahkan Klik dulu tablenya", "Pesan Kesalahan",
                    JOptionPane.ERROR_MESSAGE
            );
        }else{ 
       
        int result= JOptionPane.showConfirmDialog(this,"Apakah Anda Yakin Ingin Menghapus Data ","Hapus",
                JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
        if (result==0){
            try{
                    TableModel model = Tpenjualan.getModel();
                    Koneksi koneksi = new Koneksi();
                    Connection con = koneksi.getConnection();
                    String executeQuery = "delete from penjualan where id_penjualan =?";
                    PreparedStatement ps = con.prepareStatement(executeQuery);
                    ps.setString(1, model.getValueAt(i, 0).toString());
                    ps.executeUpdate();
                } catch (SQLException ex){
                    System.err.println(ex);
                }
          }
        resetTable("");
        reset();
  }
        
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        reset();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void TfjumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TfjumlahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TfjumlahActionPerformed

    private void BkembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BkembaliActionPerformed
         new Menu().setVisible(true);
      this.dispose();
    }//GEN-LAST:event_BkembaliActionPerformed

    private void BcetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BcetakActionPerformed
        try {
          Koneksi koneksi = new Koneksi(); 
         String path = "src/report/penjualanReport.jasper";
         Connection con = koneksi.getConnection();
            HashMap<String, Object> parameter = new HashMap();
            JasperPrint jp = JasperFillManager.fillReport(path, parameter, con);
            JasperViewer viewer = new JasperViewer(jp, false);
        viewer.setVisible(true);
        } catch(Exception e) {
        }
    }//GEN-LAST:event_BcetakActionPerformed

   
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Penjualan_Control.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Penjualan_Control.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Penjualan_Control.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Penjualan_Control.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Penjualan_Control().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Bcetak;
    private javax.swing.JButton Bkembali;
    private javax.swing.JComboBox<String> Cbpesawat;
    private javax.swing.JComboBox<String> Cbtujuan;
    private javax.swing.JLabel Lgambar;
    private javax.swing.JRadioButton Rbbisnis;
    private javax.swing.JRadioButton Rbekonomi;
    private javax.swing.JTextField Tfcari;
    private javax.swing.JTextField Tfhj;
    private javax.swing.JTextField Tfhp;
    private javax.swing.JTextField Tfht;
    private javax.swing.JTextField Tfidpenjul;
    private javax.swing.JTextField Tfjumlah;
    private javax.swing.JTextField Tfnama;
    private javax.swing.JTextField Tftotal;
    private javax.swing.JTable Tpenjualan;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
