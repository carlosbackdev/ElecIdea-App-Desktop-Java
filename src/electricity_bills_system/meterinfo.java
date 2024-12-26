package electricity_bills_system;


import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.sql.*;


public class meterinfo extends JFrame implements ActionListener {
    
    RoundedButton guardar;
    JComboBox tipo,tipo_proyecto,tipo_cliente;
    String ID_info;
    JTextArea codigo_s;
    meterinfo(String ID_info){
        this.ID_info= ID_info;
        
        setContentPane(new BackgroundPanel("images/fichas3.jpg"));  
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false); 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 0, 15, 0);
        gbc.weightx = 1.0;
        Font fuente=new Font("Roboto", Font.PLAIN, 20);
        Font fuente2=new Font("Roboto", Font.PLAIN, 13);
        gbc.ipadx = 40;
        
        JLabel head = new JLabel("      Informacion Proyecto del Cliente");
        head.setForeground(Color.WHITE);
        head.setFont(new Font("Roboto", Font.PLAIN, 28));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(head, gbc);
        
        JLabel nombre = new JLabel("Nº Identficacion del Cliente");
        nombre.setForeground(Color.WHITE);
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        nombre.setFont(fuente); 
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        panel.add(nombre, gbc);

        JLabel numero = new JLabel(ID_info);
        numero.setFont(fuente);
        numero.setForeground(Color.WHITE);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.CENTER; 
        gbc.weightx = 0;        
        panel.add(numero, gbc);
        
        


        JLabel direccion = new JLabel("Tipo de Cliente");
        direccion.setForeground(Color.WHITE);
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        direccion.setFont(fuente); 
        gbc.gridy = 3;
        gbc.gridx = 0;
        panel.add(direccion, gbc);

        tipo = new JComboBox();
        tipo.addItem("Interno");
        tipo.addItem("Externo");
        tipo.setFont(fuente2);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.weightx = 0;
        panel.add(tipo, gbc);
        
        JLabel proyecto = new JLabel("Tipo de Proyecto");
        proyecto.setForeground(Color.WHITE);
        proyecto.setFont(fuente); 
        gbc.gridy = 4;
        gbc.gridx = 0;
        panel.add(proyecto, gbc);

        tipo_proyecto = new JComboBox();
        tipo_proyecto.addItem("Defecto");
        tipo_proyecto.addItem("Mantenimiento Electrico");
        tipo_proyecto.addItem("Proyecto Solar");
        tipo_proyecto.setFont(fuente2);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.weightx = 0;
        panel.add(tipo_proyecto, gbc);
        
        JLabel codigo1 = new JLabel("Etiqueta");
        codigo1.setForeground(Color.WHITE);
        codigo1.setFont(fuente); 
        gbc.gridy = 5;
        gbc.gridx = 0;
        panel.add(codigo1, gbc);
        
        codigo_s = new JTextArea("Defecto");
        codigo_s.setFont(fuente2);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.weightx = 0;
        panel.add(codigo_s, gbc);
      
        
        JLabel cliente_tipo = new JLabel("Tipo de Cliente");
        cliente_tipo.setForeground(Color.WHITE);
        cliente_tipo.setFont(fuente); 
        gbc.gridy = 6;
        gbc.gridx = 0;
        panel.add(cliente_tipo, gbc);

        tipo_cliente=new JComboBox();
        tipo_cliente.setFont(fuente2);
        tipo_cliente.addItem("Emperesa");
        tipo_cliente.addItem("Particular");
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.weightx = 0;
        panel.add(tipo_cliente, gbc);        
              
        JLabel margen = new JLabel();
        gbc.gridy = 8;
        gbc.gridx = 0;
        panel.add(margen, gbc);             
        
        JLabel margen2 = new JLabel();
        gbc.gridy = 10;
        gbc.gridx = 0;
        panel.add(margen2, gbc);
        
        JPanel panelBotones = new JPanel(new BorderLayout());
        panelBotones.setOpaque(false); 
        gbc.gridx = 0;
        gbc.gridy = 11; 
        gbc.gridwidth = 2; 
        gbc.fill = GridBagConstraints.NONE; 
        gbc.anchor = GridBagConstraints.CENTER; 
        panel.add(panelBotones, gbc);
        
        guardar  = new RoundedButton("Guardar y Enviar");
        guardar.setBackground(new Color(222, 239, 255));
        guardar.setForeground(Color.BLACK);
        guardar.setFont(fuente); 
        guardar.addActionListener(this);
        panelBotones.add(guardar);
        panelBotones.add(guardar, BorderLayout.WEST);
        
        add(panel, BorderLayout.PAGE_START);
        
        setSize(770, 625);
        setLocationRelativeTo(null);
        setVisible(true);
    
    }
    
    public void actionPerformed(ActionEvent ae){
    if(ae.getSource()==guardar){
        String ID = ID_info;
        String info_type=(String) tipo.getSelectedItem();
        String project_type=(String) tipo_proyecto.getSelectedItem();
        project_type = project_type.toLowerCase();
        String codec=codigo_s.getText();
        String client_type=(String) tipo_cliente.getSelectedItem();
        client_type = client_type.toLowerCase();
        
       
        
            try {
                Connect c= new Connect();
                String query ="insert into meter_info values('"+ID+"', '"+info_type+"', '"+project_type+"', '"+codec+"', '"+client_type+"', '')";
                c.s.executeUpdate(query);
            
                JOptionPane.showMessageDialog(null,"Información de Cliente Y Proyecto añadida correctamente");
                setVisible(false);
            
            }catch(Exception e){
                e.printStackTrace();
                }
       
        }else {
            setVisible(false);}
    }
    public static void main(String[]args){
        new meterinfo("");
    }
    
}
