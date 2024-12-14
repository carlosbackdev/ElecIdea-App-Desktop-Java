

package electricity_bills_system;


import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class calculateBill extends JFrame implements ActionListener {
    JTextField cajon_nombre,cajon_horas;
    JTextArea cajon_direccion;
    RoundedButton guardar,cancelar,configurar,materiales_agregar;
    Choice ID_choice,parametros, materiales;
    JComboBox<String> nombre_combo;
    String ID_info,materiales_selected,selectedID;
    JPopupMenu nombre_popup;
    SimpleDateFormat dateFormat;
    String suma_total,total_final,ID_info_update,client_info_update,numero_material,direcion_completa;
    JLabel total_materiales;
    JFormattedTextField dateField;
    calculateBill(String ID_info_update, String client_info_update){
        this.ID_info = ID_info;
        this.ID_info_update = ID_info_update;
        this.client_info_update = client_info_update;
        setContentPane(new BackgroundPanel("images/Fichas.jpg"));  
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false); 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 0, 20, 0);
        gbc.weightx = 1.0;
        Font fuente=new Font("Roboto", Font.PLAIN, 20);
        Font fuente2=new Font("Roboto", Font.PLAIN, 15);
        Font fuente3=new Font("Roboto", Font.PLAIN, 18);
        Font fuente4=new Font("Roboto", Font.PLAIN, 14);
        gbc.ipadx = 100;
        
        JLabel head = new JLabel("                Calcular Factura Eléctrica");
        head.setForeground(Color.WHITE);
        head.setFont(new Font("Roboto", Font.PLAIN, 28));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(head, gbc);
        
        JLabel nombre = new JLabel("Nombre del Cliente");
        nombre.setForeground(Color.WHITE);
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        nombre.setFont(fuente); 
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        panel.add(nombre, gbc);
        
        // elegir nombre mientras se escribe
        
        cajon_nombre = new JTextField(client_info_update);
        cajon_nombre.setFont(fuente2);
        cajon_nombre.setHorizontalAlignment(JTextField.CENTER);
        cajon_nombre.setPreferredSize(new Dimension(200, 28));
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.weightx = 1.0;
        panel.add(cajon_nombre, gbc);
        
        nombre_combo = new JComboBox<>();
        nombre_combo.setFont(fuente2);
        nombre_combo.setEditable(true);
        nombre_combo.setVisible(false);
        gbc.gridy = 2;
        panel.add(nombre_combo, gbc);
        
         nombre_popup = new JPopupMenu();
        nombre_popup.setFocusable(false);

        cajon_nombre.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updatePopup();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updatePopup();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updatePopup();
            }

            private void updatePopup() {
                String text = cajon_nombre.getText();
                if (text.isEmpty()) {
                    nombre_popup.setVisible(false);
                } else {
                    nombre_popup.removeAll();
                    try {
                        Connect c = new Connect();
                        ResultSet rs = c.s.executeQuery("SELECT DISTINCT NAME FROM client WHERE NAME LIKE '" + text + "%'");
                        while (rs.next()) {
                            JMenuItem item = new JMenuItem(rs.getString("NAME"));
                            item.setPreferredSize(new Dimension(280, 28)); // Establecer tamaño preferido para cada item
                            item.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    cajon_nombre.setText(item.getText());
                                    nombre_popup.setVisible(false);
                                    updateID_choice(item.getText());
                                }
                            });
                            nombre_popup.add(item);
                        }
                        rs.close();
                        c.s.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    if (nombre_popup.getComponentCount() > 0) {
                        nombre_popup.setPreferredSize(new Dimension(307, nombre_popup.getComponentCount() * 30)); // Establecer tamaño preferido del popup
                        nombre_popup.show(cajon_nombre, 0, cajon_nombre.getHeight());
                    } else {
                        nombre_popup.setVisible(false);
                    }
                }
            }
        });
        

        JLabel numeroid = new JLabel("Numero Identificacion");
        numeroid.setForeground(Color.WHITE);
        numeroid.setFont(fuente); 
        gbc.gridy = 2;
        gbc.gridx = 0;
        panel.add(numeroid, gbc);
        

        ID_choice = new Choice();
        if(ID_info_update.length()<1){
        ID_choice.add("seleciona ID");}
        ID_choice.add(ID_info_update);
        ID_choice.setFont(fuente2);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.weightx = 0;
        panel.add(ID_choice, gbc);
        
       
        JLabel direccion = new JLabel("Direccion");
        direccion.setForeground(Color.WHITE);
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        direccion.setFont(fuente); 
        gbc.gridy = 3;
        gbc.gridx = 0;
        panel.add(direccion, gbc);

        cajon_direccion = new JTextArea("SELECCIONA NUMERO DE ID");
        cajon_direccion.setFont(fuente2);
        cajon_direccion.setOpaque(false);
        cajon_direccion.setWrapStyleWord(true);
        cajon_direccion.setEditable(false);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0;
        gbc.ipady = -3;
        panel.add(new JScrollPane(cajon_direccion), gbc);
         
        JScrollPane scrollPane = new JScrollPane(cajon_direccion);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        panel.add(scrollPane, gbc); 
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Sin borde
        
        
        
        ID_choice.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent ie){
                if (ie.getStateChange() == ItemEvent.SELECTED) {
                    selectedID = ID_choice.getSelectedItem();
                    update_materiales(ID_choice.getSelectedItem());
                }
                
                try{
            Connect c= new Connect();
            ResultSet rs=c.s.executeQuery("select * from client where ID='"+ID_choice.getSelectedItem()+"'");
            while(rs.next()){
                String direccion=rs.getString("ADDRESS");
                String postal=rs.getString("POSTAL");
                String ciudad=rs.getString("CITY");
                
                direcion_completa=direccion+", "+postal+", "+ciudad;
                cajon_direccion.setText(direcion_completa);
            }
            rs.close();
            c.s.close();
            
        }catch (Exception e){
            e.printStackTrace();
        }
            }
        });
        
        JLabel horas = new JLabel("Horas de mantenimiento");
        horas.setForeground(Color.WHITE);
        horas.setFont(fuente); 
        gbc.gridy = 4;
        gbc.gridx = 0;
        panel.add(horas, gbc);

        cajon_horas= new JTextField();
        cajon_horas.setFont(fuente2);
        cajon_horas.setHorizontalAlignment(JTextField.CENTER);
        gbc.ipady = 0;
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.weightx = 0;
        panel.add(cajon_horas, gbc);
        
        JLabel fechala = new JLabel("Fecha");
        fechala.setForeground(Color.WHITE);
        fechala.setFont(fuente); 
        gbc.gridy = 5;
        gbc.gridx = 0;
        panel.add(fechala, gbc);

        dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateField = new JFormattedTextField(dateFormat);
        dateField.setValue(new Date());
        dateField.setFont(fuente2);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0;
        panel.add(dateField, gbc);
        
        JPanel panelBotones3 = new JPanel(new BorderLayout());
        panelBotones3.setOpaque(false); 
        gbc.gridx = 0;
        gbc.gridy = 6; 
        gbc.gridwidth = 2; 
        gbc.fill = GridBagConstraints.NONE; 
        gbc.anchor = GridBagConstraints.WEST; 
        panel.add(panelBotones3, gbc);
        
        materiales_agregar  = new RoundedButton("Agregar Materiales");
        materiales_agregar.setBackground(new Color(222, 239, 255));
        materiales_agregar.setForeground(Color.BLACK);
        materiales_agregar.setFont(fuente2); 
        materiales_agregar.addActionListener(this);
        panelBotones3.add(materiales_agregar);
        panelBotones3.add(materiales_agregar, BorderLayout.WEST);
        
        materiales=new Choice();
        materiales.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedMaterial = (String) e.getItem();
                    String materialNumber = extractMaterialNumber(selectedMaterial);
                    updateTotal(materialNumber);
                }
            }
        });
        if (materiales.getItemCount() > 0) {
            materiales.select(materiales.getItemCount() - 1);
            String selectedMaterial = materiales.getSelectedItem();
            String materialNumber = extractMaterialNumber(selectedMaterial);
            updateTotal(materialNumber);
        }
        materiales.setFont(fuente4);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.weightx = 1.0;
        panel.add(materiales, gbc);
        
        JLabel totall = new JLabel("Total Materiales");
        totall.setForeground(Color.WHITE);
        totall.setFont(fuente); 
        gbc.gridy = 7;
        gbc.gridx = 0;
        panel.add(totall, gbc);
        
        total_materiales = new JLabel("");
        total_materiales.setForeground(Color.WHITE);
        total_materiales.setFont(fuente3); 
        total_materiales.setText(total_final);
        gbc.weightx = 1.0;
        gbc.gridx = 1;
        panel.add(total_materiales, gbc);
        
  
        JPanel panelBotones2 = new JPanel(new BorderLayout());
        panelBotones2.setOpaque(false); 
        gbc.gridx = 0;
        gbc.gridy = 8; 
        gbc.gridwidth = 2; 
        gbc.fill = GridBagConstraints.NONE; 
        gbc.anchor = GridBagConstraints.WEST; 
        panel.add(panelBotones2, gbc);
        
        configurar  = new RoundedButton("Agregar Parametros");
        configurar.setBackground(new Color(222, 239, 255));
        configurar.setForeground(Color.BLACK);
        configurar.setFont(fuente2); 
        configurar.addActionListener(this);
        panelBotones2.add(configurar);
        panelBotones2.add(configurar, BorderLayout.WEST);
        
        parametros=new Choice();
        parametros.add("Añadir Primero Parametros");
         try {
            int count;
            Connect c = new Connect();
            
            // Ejecutar la consulta de count
            ResultSet rs2 = c.s.executeQuery("select count(*) from setup_bill");
            if (rs2.next()) {
                count = rs2.getInt(1); // Obtener el valor de count
                if (count > 0) {
                    parametros.removeAll();
                }
            }
            rs2.close();

            // Ejecutar la consulta para obtener los nombres
            ResultSet rs = c.s.executeQuery("select * from setup_bill");
            while (rs.next()) {
                parametros.add(rs.getString("NAME"));
            }
            rs.close();
            c.s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        parametros.setFont(fuente2);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.weightx = 0;
        panel.add(parametros, gbc);
        
        
        
        JLabel margen = new JLabel();
        gbc.gridy = 9;
        gbc.gridx = 0;
        panel.add(margen, gbc);
        
        
        JPanel panelBotones = new JPanel(new BorderLayout());
        panelBotones.setOpaque(false); 
        gbc.gridx = 0;
        gbc.gridy = 10; 
        gbc.gridwidth = 2; 
        gbc.fill = GridBagConstraints.NONE; 
        gbc.anchor = GridBagConstraints.CENTER; 
        panel.add(panelBotones, gbc);
        
        
        
        guardar  = new RoundedButton("Guardar");
        guardar.setBackground(new Color(222, 239, 255));
        guardar.setForeground(Color.BLACK);
        guardar.setFont(fuente); 
        guardar.addActionListener(this);
        panelBotones.add(guardar);
        panelBotones.add(guardar, BorderLayout.WEST);

        cancelar = new RoundedButton("Cancelar");
        cancelar.setBackground(new Color(222, 239, 255));
        cancelar.setForeground(Color.BLACK);
        cancelar.setFont(fuente); 
        cancelar.addActionListener(this);
        panelBotones.add(cancelar);
        panelBotones.add(cancelar, BorderLayout.EAST);

        add(panel, BorderLayout.PAGE_START);
        
        setSize(900, 800);
        setLocationRelativeTo(null);
        setVisible(true);
        if(ID_info_update.length()>1){
        update_materiales(ID_info_update);
        }
    
    }
    public void updateID_choice(String selectedName) {
    ID_choice.removeAll();
    try {
        Connect c = new Connect();
        ResultSet rs = c.s.executeQuery("SELECT ID FROM client WHERE NAME='" + selectedName + "'");
        while (rs.next()) {
            ID_choice.add(rs.getString("ID"));
            selectedID = rs.getString("ID"); // Actualizar selectedID
        }
        rs.close();
        c.s.close();
    } catch (Exception ex) {
        ex.printStackTrace();
    }

    // Forzar la actualización de la dirección si solo hay un ID
    if (ID_choice.getItemCount() == 1) {
        ID_choice.select(0); // Seleccionar el único ID
        selectedID = ID_choice.getSelectedItem(); // Actualizar selectedID
        updateAddress(selectedID); // Actualizar la dirección
        update_materiales(selectedID); // Actualizar materiales
    } else if (ID_choice.getItemCount() > 1) {
        // Si hay más de un ID, seleccionar el primero por defecto
        ID_choice.select(0);
        selectedID = ID_choice.getSelectedItem();
        updateAddress(selectedID);
        update_materiales(selectedID);
    }
}

public void updateAddress(String selectedID) {
    try {
        Connect c = new Connect();
        ResultSet rs = c.s.executeQuery("select * from client where ID='" + selectedID + "'");
        if (rs.next()) {
            String direccion = rs.getString("ADDRESS");
            String postal = rs.getString("POSTAL");
            String ciudad = rs.getString("CITY");
            String direccion_completa = direccion + ", " + postal + ", " + ciudad;
            cajon_direccion.setText(direccion_completa);
        }
        rs.close();
        c.s.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

public void update_materiales(String selectedID) { 
    materiales.removeAll();
    try {
        Connect c = new Connect();
        c.s.executeUpdate("SET lc_time_names = 'es_ES'");         
        ResultSet rs = c.s.executeQuery("SELECT DISTINCT NUMBER, DAY(STR_TO_DATE(DATE, '%d-%m-%Y')) AS DIA, " +
                                   "YEAR(STR_TO_DATE(DATE, '%d-%m-%Y')) AS ANO, " +
                                   "MONTHNAME(STR_TO_DATE(DATE, '%d-%m-%Y')) AS MES " +
                                   "FROM material_bill WHERE ID_CLIENT='" + selectedID + "'");
         while (rs.next()) {
            String materialNumber = rs.getString("NUMBER");
            String materialDate = rs.getString("MES");
            String dia = rs.getString("DIA");
            String ano = rs.getString("ANO");
            materiales.add("Parte " + materialNumber + ", del " + dia + " de " + materialDate + " de " + ano);
        }
        rs.close();
        // Actualizar la suma total para el primer material seleccionado
        if (materiales.getItemCount() > 0) {
            String firstMaterialNumber = extractMaterialNumber(materiales.getItem(0)); // Extraer el número de material
            updateTotal(firstMaterialNumber);
        } else {
            total_materiales.setText("0$"); // Si no hay materiales, establecer total a 0
        }

        rs.close();
    } catch (Exception ex) {
        ex.printStackTrace();
    }
}

public String extractMaterialNumber(String materialString) {
    Pattern pattern = Pattern.compile("Parte (\\d+)");
    Matcher matcher = pattern.matcher(materialString);
    if (matcher.find()) {
        return matcher.group(1);
    }
    return "";
}

public void updateTotal(String materialNumber) {
    try {
        if(ID_info_update.length()>1){
        selectedID=ID_info_update;
        }
        Connect c = new Connect();
        ResultSet rs = c.s.executeQuery("SELECT SUM(TOTAL_PRICE) AS TOTAL FROM material_bill WHERE NUMBER='" + materialNumber + "' AND ID_CLIENT='"+selectedID+"'");
        if (rs.next()) {
            double totalSum = rs.getDouble("TOTAL");
            total_materiales.setText("    " + totalSum + "$");
        }
        rs.close();
        c.s.close();
    } catch (Exception ex) {
        ex.printStackTrace();
    }
}




    

    //al guardar el dinero del material si vino de material frame hay que hacerlo con
// id_info_update creo, hay que probar
    public void actionPerformed(ActionEvent ae){
        String client=cajon_nombre.getText();
        String ID =ID_choice.getSelectedItem();
        String bill_true="bill";
       if(ae.getSource()==materiales_agregar){
        new MaterialFrame(ID,client);
        setVisible(false);
       }
       if(ae.getSource()==configurar){
           
        new setup_bill(bill_true);
        setVisible(false);
       }
    if(ae.getSource()==guardar){
        String ID_2 =ID_choice.getSelectedItem();
        if (ID_info_update.length()>1){
            ID_2=ID_info_update;
        }
        String NAME=cajon_nombre.getText();
        if(client_info_update.length()>1){
            NAME=client_info_update;
        }
        String ADDRESS = cajon_direccion.getText();
        String HOUR = cajon_horas.getText();
        String DATE= dateField.getText();
        String NUMBER_MATERIAL=materiales.getSelectedItem();
        String TOTAL_MATERIAL=total_materiales.getText();
        String PARAMETROS=parametros.getSelectedItem();
        int num_factura=0;
        String NUMBER_FACTURA=""+num_factura;        
        
        try {        
        Connect c = new Connect();
        ResultSet rs = c.s.executeQuery("SELECT count(*) AS NUMERO_FACTURA FROM bill_standard WHERE NUMBER='" + ID_2 + "'");
        if (rs.next()) {
            num_factura = rs.getInt("NUMERO_FACTURA");
            num_factura+=1;
        }
        rs.close();
        c.s.close();
        } catch (Exception ex) {
        ex.printStackTrace();
        }
    }
//        String time=cajon_horas.getText();
//        String month=mes.getSelectedItem();
//        
//        int condicion=0;
//        int tf=0;
//        int ml=0;
//        try{ Connect c = new Connect();
//            String query="select PHONE,EMAIL,POSTAL from client;";                
//              ResultSet rs=c.s.executeQuery(query);   
//               while(rs.next()){
//                   String rs2=rs.getString("PHONE");
//                   String rs3=rs.getString("EMAIL");
//                   if(rs2.equals(phone)){                      
//                       condicion=1;
//                       tf=1;
//                       
//                   }
//                   if(rs3.equals(mail)){                       
//                       condicion=1;
//                       ml=1;
//                       
//                   }
//                   
//               }
//                rs.close();
//                c.s.close();
//                if(tf>0){
//                JOptionPane.showMessageDialog(null,"numero de telefono ya existente");
//                }
//                if(ml>0){
//                JOptionPane.showMessageDialog(null,"email ya existente");
//                }
//               
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        if(postal.length()>5 || postal.length()<5){
//        condicion=1;
//        JOptionPane.showMessageDialog(null,"numero de postal incorrecto");
//        }
//        if(phone.length()<9 || phone.length()>9 ){
//        condicion=1;
//        JOptionPane.showMessageDialog(null,"numero de telefono incorrecto");
//        }
//        
//        if(address.length()<5){
//        condicion=1;
//        JOptionPane.showMessageDialog(null,"direccion incorrecta");
//        }
//        if(city.length()<4){
//        condicion=1;
//        JOptionPane.showMessageDialog(null,"ciudad incorrecta");
//        }
//        if(name.length()<3){
//        condicion=1;
//        JOptionPane.showMessageDialog(null,"nombre incorrecta");
//        }
//        int condicion2=0;
//        for(int i=0;i<mailn;i++){
//        String a =""+ mail.charAt(i);
//        if(a.equals("@")){
//            condicion2=1;
//        }
//        }
//        if(condicion2==0){
//        condicion=1;
//        JOptionPane.showMessageDialog(null,"email incorrecto");
//        }
//       
//        if(condicion==0){
//            try {
//                Connect c= new Connect();
//                String query ="insert into client values('"+name+"', '"+ID+"', '"+address+"', '"+city+"', '"+postal+"', '"+mail+"', '"+phone+"')";
//                String query2 ="insert into login values('"+ID+"', '', '"+name+"', '','')";
//                c.s.executeUpdate(query);
//                c.s.executeUpdate(query2);
//            
//                JOptionPane.showMessageDialog(null,"Cliente Añadido Correctamente");
//                setVisible(false);
//                
//                new meterinfo(ID);
//            
//            }catch(Exception e){
//                e.printStackTrace();
//                }
//        
//            }else if(condicion == 0){
//            setVisible(false);
//            }
//        }else if(ae.getSource() == cancelar){
//            setVisible(false);}
    }
    
    public static void main(String[]args){
        new calculateBill("","");
    }
    
}

