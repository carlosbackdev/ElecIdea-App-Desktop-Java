
package electricity_bills_system;

import javax.swing.*;
import java.awt.*;
import java.awt.color.*;
import java.util.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.event.*;
import com.formdev.flatlaf.FlatDarkLaf;

public class ClientUpdate extends JFrame implements ActionListener{
    JTextField cajon_nombre,cajon_direccion,cajon_ciudad,cajon_postal,cajon_mail,cajon_telf;
    RoundedButton guardar,cancelar,eliminar;
    JLabel numero;
    String NIF,ID_USER,date,selectedID;
    SimpleDateFormat dateFormat;
    JFormattedTextField dateField;
    JComboBox<String> nombre_combo;
    JPopupMenu nombre_popup;
    Choice ID_choice;
    
    ClientUpdate(String NIF, String ID_USER){
        this.NIF=NIF;
        this.ID_USER=ID_USER;
        
        setContentPane(new BackgroundPanel("images/Fichas3.jpg"));  
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false); 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 0, 20, 0);
        gbc.weightx = 1.0;
        Font fuente=new Font("Roboto", Font.PLAIN, 18);
        Font fuente2=new Font("Roboto", Font.PLAIN, 13);
        Color gris=new Color(210,210,210);
        gbc.ipadx = 20;
        
        JLabel head = new JLabel("      MODIFICAR DATOS");
        head.setForeground(gris);
        head.setFont(new Font("Roboto", Font.PLAIN, 28));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(head, gbc);
        
        JLabel nombre = new JLabel("Buscar Nombre");
        nombre.setForeground(gris);
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        nombre.setFont(fuente); 
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        panel.add(nombre, gbc);

        cajon_nombre = new JTextField();
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
            private javax.swing.Timer timer = new javax.swing.Timer(400, new ActionListener() { 
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
                        ResultSet rs = c.s.executeQuery("SELECT DISTINCT NAME FROM client WHERE NAME LIKE '" + text + "%'");
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
        numeroid.setForeground(gris);
        numeroid.setFont(fuente); 
        gbc.gridy = 2;
        gbc.gridx = 0;
        panel.add(numeroid, gbc);
        

        ID_choice = new Choice();
        ID_choice.setFont(fuente2);
        ID_choice.add("Seleciona ID");
        ID_choice.setBackground(new Color(70, 73, 75));
        ID_choice.setForeground(new Color(165, 166, 167));
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.weightx = 0;
        panel.add(ID_choice, gbc);


        JLabel direccion = new JLabel("Direccion");
        direccion.setForeground(gris);
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        direccion.setFont(fuente); 
        gbc.gridy = 3;
        gbc.gridx = 0;
        panel.add(direccion, gbc);

        cajon_direccion = new JTextField();
        cajon_direccion.setFont(fuente2);
        cajon_direccion.setHorizontalAlignment(JTextField.CENTER);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.weightx = 0;
        panel.add(cajon_direccion, gbc);
        
        JLabel ciudad = new JLabel("Ciudad");
        ciudad.setForeground(gris);
        ciudad.setFont(fuente); 
        gbc.gridy = 4;
        gbc.gridx = 0;
        panel.add(ciudad, gbc);

        cajon_ciudad = new JTextField();
        cajon_ciudad.setFont(fuente2);
        cajon_ciudad.setHorizontalAlignment(JTextField.CENTER);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.weightx = 0;
        panel.add(cajon_ciudad, gbc);
        
        JLabel postal = new JLabel("Codigo Postal");
        postal.setForeground(gris);
        postal.setFont(fuente); 
        gbc.gridy = 5;
        gbc.gridx = 0;
        panel.add(postal, gbc);

        cajon_postal = new JTextField();
        cajon_postal.setFont(fuente2);
        cajon_postal.setHorizontalAlignment(JTextField.CENTER);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.weightx = 0;
        panel.add(cajon_postal, gbc);
        
        JLabel mail = new JLabel("Email");
        mail.setForeground(gris);
        mail.setFont(fuente); 
        gbc.gridy = 6;
        gbc.gridx = 0;
        panel.add(mail, gbc);

        cajon_mail = new JTextField();
        cajon_mail.setFont(fuente2);
        cajon_mail.setHorizontalAlignment(JTextField.CENTER);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.weightx = 0;
        panel.add(cajon_mail, gbc);
        
        JLabel telefono = new JLabel("Telefono");
        telefono.setForeground(gris);
        telefono.setFont(fuente); 
        gbc.gridy = 7;
        gbc.gridx = 0;
        panel.add(telefono, gbc);

        cajon_telf = new JTextField();
        cajon_telf.setFont(fuente2);
        cajon_telf.setHorizontalAlignment(JTextField.CENTER);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.weightx = 0;
        panel.add(cajon_telf, gbc);   
        
        JLabel margen = new JLabel();
        gbc.gridy = 8;
        gbc.gridx = 0;
        panel.add(margen, gbc);
        
        JPanel panelBotones = new JPanel(new BorderLayout());
        panelBotones.setOpaque(false); 
        gbc.gridx = 0;
        gbc.gridy = 9; 
        gbc.gridwidth = 2; 
        gbc.fill = GridBagConstraints.NONE; 
        gbc.anchor = GridBagConstraints.CENTER; 
        panel.add(panelBotones, gbc);
        
        dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        date = dateFormat.format(new Date());
        
        guardar  = new RoundedButton("     Modificar     ");  
        guardar.setFont(fuente); 
        guardar.addActionListener(this);
        panelBotones.add(guardar);
        panelBotones.add(guardar, BorderLayout.WEST);

        eliminar = new RoundedButton("Eliminar Cliente");
        eliminar.setFont(fuente); 
        eliminar.addActionListener(this);
        panelBotones.add(eliminar);
        panelBotones.add(eliminar, BorderLayout.EAST);
        
        JPanel panelBotones2 = new JPanel(new BorderLayout());
        panelBotones2.setOpaque(false); 
        gbc.gridx = 0;
        gbc.gridy = 10; 
        gbc.gridwidth = 2; 
        gbc.fill = GridBagConstraints.NONE; 
        gbc.anchor = GridBagConstraints.CENTER; 
        panel.add(panelBotones2, gbc);
        
        cancelar = new RoundedButton("  Cancelar  ");
        cancelar.setFont(fuente); 
        cancelar.addActionListener(this);
        panelBotones2.add(cancelar);
        panelBotones2.add(cancelar, BorderLayout.EAST);
        
         try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

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
            selectedID = rs.getString("ID");
        }
        rs.close();
        c.s.close();
    } catch (Exception ex) {
        ex.printStackTrace();
    }  

    if (ID_choice.getItemCount() == 1) {
        ID_choice.select(0); 
        selectedID = ID_choice.getSelectedItem();
        update(selectedID);
    } else if (ID_choice.getItemCount() > 1) {
        ID_choice.select(0);
        selectedID = ID_choice.getSelectedItem();
        update(selectedID);
    }
}   
     public void update(String selectedID) {
    try {
        Connect c = new Connect();        
        ResultSet rs = c.s.executeQuery("select * from client where ID='" + selectedID + "'");
        if (rs.next()) {
            String direccion = rs.getString("ADDRESS");
            String postal = rs.getString("POSTAL");
            String ciudad = rs.getString("CITY");
            String mail = rs.getString("EMAIL");
            String telef= rs.getString("PHONE");
            cajon_direccion.setText(direccion);
            cajon_ciudad.setText(ciudad);
            cajon_postal.setText(postal);
            cajon_mail.setText(mail);
            cajon_telf.setText(telef);
        }
        rs.close();
        c.s.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    
    public void actionPerformed(ActionEvent ae){
    if(ae.getSource()==guardar){
        String name =cajon_nombre.getText().toLowerCase().trim();
        String ID = numero.getText();
        String address=cajon_direccion.getText().toLowerCase().trim();
        String city=cajon_ciudad.getText().toLowerCase().trim();
        String postal=cajon_postal.getText().trim();
        String mail=cajon_mail.getText().toLowerCase().trim();
        String phone = cajon_telf.getText().trim();
        int mailn=mail.length();
        
        int condicion=0;
        int tf=0;
        int ml=0;
        try{ Connect c = new Connect();
            String query="select PHONE,EMAIL,POSTAL from client;";                
              ResultSet rs=c.s.executeQuery(query);   
               while(rs.next()){
                   String rs2=rs.getString("PHONE");
                   String rs3=rs.getString("EMAIL");
                   if(rs2.equals(phone)){                      
                       condicion=1;
                       tf=1;                      
                   }
                   if(rs3.equals(mail)){                       
                       condicion=1;
                       ml=1;                      
                   }                  
               }
                rs.close();
                c.s.close();
                if(tf>0){
                JOptionPane.showMessageDialog(null,"numero de telefono ya existente");
                }
                if(ml>0){
                JOptionPane.showMessageDialog(null,"email ya existente");
                }
               
            }catch (Exception e){
                e.printStackTrace();
            }
        if(postal.length()>5 || postal.length()<5){
        condicion=1;
        JOptionPane.showMessageDialog(null,"numero de postal incorrecto");
        }
        if(phone.length()<9 || phone.length()>9 ){
        condicion=1;
        JOptionPane.showMessageDialog(null,"numero de telefono incorrecto");
        }
        
        if(address.length()<5){
        condicion=1;
        JOptionPane.showMessageDialog(null,"direccion incorrecta");
        }
        if(city.length()<4){
        condicion=1;
        JOptionPane.showMessageDialog(null,"ciudad incorrecta");
        }
        if(name.length()<3){
        condicion=1;
        JOptionPane.showMessageDialog(null,"nombre incorrecta");
        }
        int condicion2=0;
        for(int i=0;i<mailn;i++){
        String a =""+ mail.charAt(i);
        if(a.equals("@")){
            condicion2=1;
        }
        if(a.equals(".")){
            condicion2=1;
        }
        }
        if(condicion2==0){
        condicion=1;
        JOptionPane.showMessageDialog(null,"email incorrecto");
        }
       
        if(condicion==0){
            try {
                Connect c= new Connect();
                String query ="insert into client values('"+name+"', '"+ID+"', '"+address+"', '"+city+"', '"+postal+"', '"+mail+"', '"+phone+"','"+NIF+"','"+date+"')";
                String query2 ="insert into login values('"+ID+"', '"+name+"_"+(ID)+"','"+name+"','','cliente','"+NIF+"')";
                c.s.executeUpdate(query);
                c.s.executeUpdate(query2);
            
                JOptionPane.showMessageDialog(null,"Cliente Añadido Correctamente");
                setVisible(false);
                
                new meterinfo(ID);
            
            }catch(Exception e){
                e.printStackTrace();
                }
        
            }else if(condicion == 0){
            setVisible(false);
            }
        }else if(ae.getSource() == eliminar){
            int opcion = JOptionPane.showConfirmDialog(null, 
                "¿Seguro que desea eliminar los datos del cliente?", 
                "Confirmación de eliminación", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.QUESTION_MESSAGE);

            if (opcion == JOptionPane.YES_OPTION) {
                
            } else if (opcion == JOptionPane.NO_OPTION) {
                
            }
            
        
        }else{
            setVisible(false);}
    }
    public static void main(String[]args){
        new ClientUpdate("","");
    }
    
}
    

