

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
import javax.swing.Timer;

public class calculateBill extends JFrame implements ActionListener {
    JTextField cajon_nombre,cajon_horas;
    JTextArea cajon_direccion;
    RoundedButton guardar,cancelar,configurar,materiales_agregar;
    Choice ID_choice,parametros, materiales;
    JComboBox<String> nombre_combo;
    String ID_info,materiales_selected,selectedID,NIF,ID_USER;
    JPopupMenu nombre_popup;
    SimpleDateFormat dateFormat;
    String suma_total,total_final,ID_info_update,client_info_update,numero_material,direcion_completa;
    JLabel total_materiales;
    JFormattedTextField dateField;
    double totalSum;
    calculateBill(String ID_info_update, String client_info_update,String NIF, String ID_USER){
        this.ID_info = ID_info;
        this.ID_info_update = ID_info_update;
        this.client_info_update = client_info_update;
        this.NIF = NIF;
        this.ID_USER = ID_USER;
        setContentPane(new BackgroundPanel("images/Fichas2.jpg"));  
        
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
        
        JLabel nombre = new JLabel("Busca y selecciona Nombre en clientes");
        nombre.setForeground(Color.WHITE);
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        nombre.setFont(new Font("Roboto", Font.PLAIN, 19)); 
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
            private Timer timer = new Timer(400, new ActionListener() { 
                @Override
                public void actionPerformed(ActionEvent e) {
                    updatePopup();
                }
            });

            @Override
            public void insertUpdate(DocumentEvent e) {
                resetTimer();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                resetTimer();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                resetTimer();
            }

            private void resetTimer() {
                if (timer.isRunning()) {
                    timer.stop(); 
                }
                timer.start(); 
            }

            private void updatePopup() {
                timer.stop(); 
                String text = cajon_nombre.getText();
                if (text.isEmpty()) {
                    nombre_popup.setVisible(false);
                } else {
                    nombre_popup.removeAll();
                    try {
                        Connect c = new Connect();
                        ResultSet rs = c.s.executeQuery("SELECT DISTINCT NAME FROM client WHERE NAME LIKE '" + text + "%' AND NIF='"+NIF+"'");
                        while (rs.next()) {
                            JMenuItem item = new JMenuItem(rs.getString("NAME"));
                            item.setPreferredSize(new Dimension(200, 28)); 
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

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    if (nombre_popup.getComponentCount() > 0) {
                        nombre_popup.setPreferredSize(new Dimension(307, nombre_popup.getComponentCount() * 30));
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
        
        if(ID_info_update.length()>1){
        updateAddress(ID_info_update);
        }
        
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
            ResultSet rs = c.s.executeQuery("select * from setup_bill WHERE NIF='"+NIF+"'");
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
        
        
        
        guardar  = new RoundedButton("Ver y Guardar");
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
        ResultSet rs = c.s.executeQuery("SELECT ID FROM client WHERE NAME='" + selectedName + "' AND NIF='"+NIF+"'");
        while (rs.next()) {
            ID_choice.add(rs.getString("ID"));
            selectedID = rs.getString("ID"); // Actualizar selectedID
        }
        rs.close();
        c.s.close();
    } catch (Exception ex) {
        ex.printStackTrace();
    }
   
//     Forzar la actualización de la dirección si solo hay un ID
    if (ID_choice.getItemCount() == 1) {
        ID_choice.select(0); // Seleccionar el único ID
        selectedID = ID_choice.getSelectedItem();
        updateAddress(selectedID); 
        update_materiales(selectedID);
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
            cajon_direccion.setText(direccion_completa.trim());
        }
        rs.close();
        c.s.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

public void update_materiales(String seletedID) { 
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
            total_materiales.setText("0€"); 
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
            totalSum = rs.getDouble("TOTAL");
            total_materiales.setText("  " + totalSum + " €");
        }
        rs.close();
        c.s.close();
    } catch (Exception ex) {
        ex.printStackTrace();
    }
}

    public void actionPerformed(ActionEvent ae){
      String client=cajon_nombre.getText();
      String ID =ID_choice.getSelectedItem();
      String bill_true="bill";
      
        if(ae.getSource()==materiales_agregar){
         new MaterialFrame(ID,client,NIF,ID_USER);
         setVisible(false);
        }
        
        if(ae.getSource()==configurar){

         new setup_bill(bill_true,NIF);
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
            
            if(!NAME.isBlank()&& ID_2.length()==9 && !HOUR.isBlank()){
                int h=Integer.parseInt(HOUR);
                String DATE= dateField.getText();
                String NUMBER_MATERIAL=materiales.getSelectedItem();
                String TOTAL_MATERIAL=totalSum+"";
                String PARAMETROS=parametros.getSelectedItem();
                int num_factura=0;
                int IVA_int=0;
                int precio_hora=0;        
                double TOTAL_BILL;
            
            try {        
            Connect c = new Connect();
            ResultSet rs = c.s.executeQuery("SELECT count(*) AS NUMERO_FACTURA FROM bill_standard WHERE ID_CLIENT='" + ID_2 + "'");
            if (rs.next()) {
                num_factura = rs.getInt("NUMERO_FACTURA");
                num_factura+=1;
            }
            ResultSet rs2 = c.s.executeQuery("SELECT IVA,PRICE FROM setup_bill  WHERE NAME='" + PARAMETROS + "'");
            if (rs2.next()) {
                IVA_int = rs2.getInt("IVA");
                precio_hora=rs2.getInt("PRICE");
            }
            rs.close();
            rs2.close();
            c.s.close();
            } catch (Exception ex) {
            ex.printStackTrace();
            }
            String NUMBER_FACTURA=""+num_factura;
            double TOTAL_HORAS=h*precio_hora;
            TOTAL_BILL=TOTAL_HORAS+totalSum;
            double total_iva=TOTAL_BILL*(IVA_int/100.00);
            TOTAL_BILL=TOTAL_BILL+total_iva;      
            String NUMBER_MATERIAL2 =NUMBER_MATERIAL.substring(NUMBER_MATERIAL.indexOf(" ")+1, NUMBER_MATERIAL.indexOf(","));
            if(NUMBER_MATERIAL2.isBlank()){
            JOptionPane.showMessageDialog(null, "Introduce parte de material");
            
            }

            String TOTAL_BILL2=""+TOTAL_BILL;        
            new bill_standard_view(ID_2,NAME,ADDRESS,HOUR,DATE,NUMBER_MATERIAL2,TOTAL_MATERIAL,PARAMETROS,NUMBER_FACTURA,TOTAL_BILL2,NIF,ID_USER);
            setVisible(false);
            
            }else if(NAME.isBlank()){
                JOptionPane.showMessageDialog(null, "Seleciona un nombre");
            }else if( ID_2.length()!=9){
                JOptionPane.showMessageDialog(null, "Seleciona una ID");
            }else if(HOUR.isBlank()){
                JOptionPane.showMessageDialog(null, "Introduce un numero de horas");
            }
            
            
        }
        if(ae.getSource()==cancelar){
                setVisible(false);
            }
    }
    
    public static void main(String[]args){
        new calculateBill("","","","");
    }
    
}

