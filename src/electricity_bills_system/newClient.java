
package electricity_bills_system;


import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;


public class newClient extends JFrame implements ActionListener {
    JTextField cajon_nombre,cajon_direccion,cajon_ciudad,cajon_postal,cajon_mail,cajon_telf;
    RoundedButton guardar,cancelar;
    newClient(){
        
        setContentPane(new BackgroundPanel("images/Fichas.jpg"));  
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false); 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 0, 20, 0);
        gbc.weightx = 1.0;
        Font fuente=new Font("Roboto", Font.PLAIN, 20);
        Font fuente2=new Font("Roboto", Font.PLAIN, 13);
        gbc.ipadx = 80;
        
        JLabel head = new JLabel("Nuevo Cliente");
        head.setForeground(Color.WHITE);
        head.setFont(new Font("Roboto", Font.PLAIN, 28));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(head, gbc);
        
        JLabel nombre = new JLabel("Nombre");
        nombre.setForeground(Color.WHITE);
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
        numeroid.setForeground(Color.WHITE);
        numeroid.setFont(fuente); 
        gbc.gridy = 2;
        gbc.gridx = 0;
        panel.add(numeroid, gbc);
        

        JLabel numero = new JLabel("");
        numero.setFont(fuente);
        numero.setForeground(Color.WHITE);
        gbc.gridx = 1;
        gbc.weightx = 0;        
        panel.add(numero, gbc);
        
        int ID =IDgenerador.generadorId();
        numero.setText(""+ID);


        JLabel direccion = new JLabel("Direccion");
        direccion.setForeground(Color.WHITE);
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
        
        JLabel postal = new JLabel("Codigo Postal");
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
    if(ae.getSource()==guardar){
        
        }else{
            setVisible(false);
        }
    }
    
    public static void main(String[]args){
        new newClient();
    }
    
}
