
package electricity_bills_system;


import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;


public class newClient extends JFrame implements ActionListener {
    JTextField cajon_nombre,cajon_direccion,cajon_ciudad,cajon_postal,cajon_mail,cajon_telf;
    RoundedButton guardar,cancelar;
    JLabel numero;
    String NIF,ID_USER,date;
    SimpleDateFormat dateFormat;
    JFormattedTextField dateField;
    
    newClient(String NIF, String ID_USER){
        this.NIF=NIF;
        this.ID_USER=ID_USER;

        setContentPane(new BackgroundPanel("images/fichas3.jpg"));  
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false); 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 0, 20, 0);
        gbc.weightx = 1.0;
        Font fuente=new Font("Roboto", Font.PLAIN, 20);
        Font fuente2=new Font("Roboto", Font.PLAIN, 13);
        Font fuente3=new Font("Roboto", Font.PLAIN, 16);
        gbc.ipadx = 80;
        Color gris=new Color(210,210,210);

        
        JLabel head = new JLabel("          Nuevo Cliente");
        head.setForeground(gris);
        head.setFont(new Font("Roboto", Font.PLAIN, 28));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(head, gbc);
        
        JLabel nombre = new JLabel("Nombre");
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
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.weightx = 0;
        panel.add(cajon_nombre, gbc);

        JLabel numeroid = new JLabel("Numero ID generado");
        numeroid.setForeground(gris);
        numeroid.setFont(fuente); 
        gbc.gridy = 2;
        gbc.gridx = 0;
        panel.add(numeroid, gbc);
        

        numero = new JLabel("");
        numero.setFont(fuente);
        numero.setForeground(gris);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.CENTER;  
        gbc.weightx = 0;        
        panel.add(numero, gbc);
        
        int ID =IDgenerador.generadorId();
        numero.setText(""+ID);


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
        
        guardar  = new RoundedButton("    Guardar    ");
        guardar.setFont(fuente3); 
        guardar.addActionListener(this);
        panelBotones.add(guardar);
        panelBotones.add(guardar, BorderLayout.WEST);

        cancelar = new RoundedButton("    Cancelar    ");
        cancelar.setFont(fuente3); 
        cancelar.addActionListener(this);
        panelBotones.add(cancelar);
        panelBotones.add(cancelar, BorderLayout.EAST);

        add(panel, BorderLayout.PAGE_START);
        
        setSize(900, 800);
        setLocationRelativeTo(null);
        setVisible(true);
    
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
        }else{            
            setVisible(false);}
    }
    public static void main(String[]args){
        new newClient("","");
    }
    
}
