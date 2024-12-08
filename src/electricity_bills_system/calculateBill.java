

package electricity_bills_system;


import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.sql.*;


public class calculateBill extends JFrame implements ActionListener {
    JTextField cajon_direccion,cajon_ciudad,cajon_postal,cajon_mail,cajon_telf;
    RoundedButton guardar,cancelar;
    JLabel numero;
    Choice nombre_choice,ID_choice;
    calculateBill(){
        
        setContentPane(new BackgroundPanel("images/Fichas.jpg"));  
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false); 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 0, 20, 0);
        gbc.weightx = 1.0;
        Font fuente=new Font("Roboto", Font.PLAIN, 20);
        Font fuente2=new Font("Roboto", Font.PLAIN, 13);
        gbc.ipadx = 80;
        
        JLabel head = new JLabel("Calcular Factura Electrica");
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
        
        nombre_choice=new Choice();
        
        nombre_choice = new Choice();
        nombre_choice.setFont(fuente2);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.weightx = 0;
        panel.add(nombre_choice, gbc);
        
        //selecionar nombre de la base de datos
        try{
            Connect c=new Connect();
            ResultSet rs= c.s.executeQuery("select NAME from client");
            while(rs.next()){
                nombre_choice.addItem(rs.getString("NAME"));
                
            }
            
        } catch(Exception e){
            e.printStackTrace();
        }


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
        
        nombre_choice.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedName = nombre_choice.getSelectedItem();
                    ID_choice.removeAll(); // Limpiar las opciones anteriores
                    try {
                        Connect c = new Connect();
                        ResultSet rs = c.s.executeQuery("SELECT ID FROM client WHERE NAME='" + selectedName + "'");
                        while (rs.next()) {
                        ID_choice.addItem(rs.getString("ID"));
                    }
                    rs.close();
                    c.s.close();
                    } catch (Exception ex) {
                    ex.printStackTrace();
                        }
                    }
                }
            });
        
       
        JLabel direccion = new JLabel("Direccion");
        direccion.setForeground(Color.WHITE);
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
        
        JLabel ciudad = new JLabel("Horas de mantenimiento");
        ciudad.setForeground(Color.WHITE);
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
        
        JLabel postal = new JLabel("Month");
        postal.setForeground(Color.WHITE);
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
        mail.setForeground(Color.WHITE);
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
        telefono.setForeground(Color.WHITE);
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
    
    public void actionPerformed(ActionEvent ae){
//    if(ae.getSource()==guardar){
//        String name =nombre_choice.getText();
//        String ID = numero.getText();
//        String address=cajon_direccion.getText();
//        String city=cajon_ciudad.getText();
//        String postal=cajon_postal.getText();
//        String mail=cajon_mail.getText();
//        String phone = cajon_telf.getText();
//        int mailn=mail.length();
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

