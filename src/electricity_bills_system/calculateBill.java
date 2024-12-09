

package electricity_bills_system;


import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class calculateBill extends JFrame implements ActionListener {
    JTextField cajon_nombre,cajon_horas,cajon_postal,cajon_mail,cajon_telf;
    JTextArea cajon_direccion;
    RoundedButton guardar,cancelar,configurar;
    JLabel numero;
    Choice ID_choice,mes,parametros;
    JComboBox<String> nombre_combo;
    String ID_info;
    JPopupMenu nombre_popup;
    calculateBill(){
        this.ID_info = ID_info;
        setContentPane(new BackgroundPanel("images/Fichas.jpg"));  
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false); 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 0, 20, 0);
        gbc.weightx = 1.0;
        Font fuente=new Font("Roboto", Font.PLAIN, 20);
        Font fuente2=new Font("Roboto", Font.PLAIN, 15);
        gbc.ipadx = 119;
        
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
        
        cajon_nombre = new JTextField();
        cajon_nombre.setFont(fuente2);
        cajon_nombre.setHorizontalAlignment(JTextField.CENTER);
        cajon_nombre.setPreferredSize(new Dimension(150, 32));
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
                        ResultSet rs = c.s.executeQuery("SELECT NAME FROM client WHERE NAME LIKE '" + text + "%'");
                        while (rs.next()) {
                            JMenuItem item = new JMenuItem(rs.getString("NAME"));
                            item.setPreferredSize(new Dimension(200, 30)); // Establecer tamaño preferido para cada item
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
                        nombre_popup.setPreferredSize(new Dimension(270, nombre_popup.getComponentCount() * 30)); // Establecer tamaño preferido del popup
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
        
        ID_choice=new Choice();

        ID_choice = new Choice();
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
        
        panel.add(scrollPane, gbc); 
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Sin borde
        
        
        
        ID_choice.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent ie){
                try{
            Connect c= new Connect();
            ResultSet rs=c.s.executeQuery("select * from client where ID='"+ID_choice.getSelectedItem()+"'");
            while(rs.next()){
                cajon_direccion.setText(rs.getString("ADDRESS"));
                
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
        
        JLabel postal = new JLabel("Mes");
        postal.setForeground(Color.WHITE);
        postal.setFont(fuente); 
        gbc.gridy = 5;
        gbc.gridx = 0;
        panel.add(postal, gbc);

        mes = new Choice();
        mes.add("Enero");
        mes.add("Febrero");
        mes.add("Marzo");
        mes.add("Abril");
        mes.add("Mayo");
        mes.add("Junio");
        mes.add("Julio");
        mes.add("Agosto");
        mes.add("Septiembre");
        mes.add("Octubre");
        mes.add("Novimbre");
        mes.add("Diciembre");
        mes.setFont(fuente2);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.weightx = 0;
        panel.add(mes, gbc);
  
        JPanel panelBotones2 = new JPanel(new BorderLayout());
        panelBotones2.setOpaque(false); 
        gbc.gridx = 0;
        gbc.gridy = 6; 
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
        try{
            Connect c=new Connect();
            ResultSet rs= c.s.executeQuery("select * from setup_bill");
            while(rs.next()){
                parametros.add(rs.getString("NAME"));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        
        
        parametros.setFont(fuente2);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.weightx = 0;
        panel.add(parametros, gbc);
        
        
        
        JLabel margen = new JLabel();
        gbc.gridy = 7;
        gbc.gridx = 0;
        panel.add(margen, gbc);
        
        
        JPanel panelBotones = new JPanel(new BorderLayout());
        panelBotones.setOpaque(false); 
        gbc.gridx = 0;
        gbc.gridy = 8; 
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
    
    }
    public void updateID_choice(String selectedName) {
        ID_choice.removeAll();
    try {
        Connect c = new Connect();
        ResultSet rs = c.s.executeQuery("SELECT ID FROM client WHERE NAME='" + selectedName + "'");
        while (rs.next()) {
            ID_choice.add(rs.getString("ID"));
        }
        rs.close();
        c.s.close();
    } catch (Exception ex) {
        ex.printStackTrace();
    }

    // Forzar la actualización de la dirección si solo hay un ID
    if (ID_choice.getItemCount() == 1) {
        ID_choice.select(0); // Seleccionar el único ID
        updateAddress(ID_choice.getSelectedItem()); // Actualizar la dirección
    }
}
    public void updateAddress(String selectedID) {
    try {
        Connect c = new Connect();
        ResultSet rs = c.s.executeQuery("select * from client where ID='" + selectedID + "'");
        if (rs.next()) {
            cajon_nombre.setText(rs.getString("NAME"));
            cajon_direccion.setText(rs.getString("ADDRESS"));
        }
        rs.close();
        c.s.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    
    public void actionPerformed(ActionEvent ae){
        //PARA CALCULAR LAS FACTURTAS SE DEBE HACER OTR TABLA DONDE SE PIDA COMO HACER LA FACTURA DE MANTENIMEINTO PIDIENDO LOS DATOS NECESARIOS 
        //COMO EL COSTE POR HORA, IVA... Y TODO ESO PONER UN NOMBRE UNICO PARA SELECIONAR LUEGO EL TIPO DE FACTURA
//    if(ae.getSource()==guardar){
//        String ID =ID_choice.getSelectedItem();
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
        new calculateBill();
    }
    
}

