package electricity_bills_system;


import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.sql.*;


public class meterinfo extends JFrame implements ActionListener {
    
    RoundedButton guardar;
    Choice tipo,tipo_proyecto,codigo_s,tipo_cliente;
    String ID_info;
    meterinfo(String ID_info){
        this.ID_info= ID_info;
        
        setContentPane(new BackgroundPanel("images/Fichas.jpg"));  
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false); 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 0, 15, 0);
        gbc.weightx = 1.0;
        Font fuente=new Font("Roboto", Font.PLAIN, 20);
        Font fuente2=new Font("Roboto", Font.PLAIN, 13);
        gbc.ipadx = 40;
        
        JLabel head = new JLabel("Informacion Proyecto del Cliente");
        head.setForeground(Color.WHITE);
        head.setFont(new Font("Roboto", Font.PLAIN, 28));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(head, gbc);
        
        JLabel nombre = new JLabel("Numero de Identficacion del Cliente");
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

        tipo = new Choice();
        tipo.add("Interno");
        tipo.add("Externo");
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

        tipo_proyecto = new Choice();
        tipo_proyecto.add("Defecto");
        tipo_proyecto.add("Mantenimiento Electrico");
        tipo_proyecto.add("Proyecto Solar");
        tipo_proyecto.setFont(fuente2);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.weightx = 0;
        panel.add(tipo_proyecto, gbc);
        
        JLabel codigo1 = new JLabel("Codigo");
        codigo1.setForeground(Color.WHITE);
        codigo1.setFont(fuente); 
        gbc.gridy = 5;
        gbc.gridx = 0;
        panel.add(codigo1, gbc);
        
        codigo_s = new Choice();
        codigo_s.setFont(fuente2);
        codigo_s.add("01");
        codigo_s.add("02");
        codigo_s.add("03");
        codigo_s.add("04");
        codigo_s.add("05");
        codigo_s.add("06");
        codigo_s.add("07");
        codigo_s.add("08");
        codigo_s.add("09");
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

        tipo_cliente=new Choice();
        tipo_cliente.setFont(fuente2);
        tipo_cliente.add("Emperesa");
        tipo_cliente.add("Particular");
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.weightx = 0;
        panel.add(tipo_cliente, gbc);
        
        JLabel dias = new JLabel("Dias");
        dias.setForeground(Color.WHITE);
        dias.setFont(fuente); 
        gbc.gridy = 7;
        gbc.gridx = 0;
        panel.add(dias, gbc);

        JLabel dias_restantes = new JLabel("30 Dias");
        dias_restantes.setForeground(Color.WHITE);
        dias_restantes.setFont(fuente); 
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.CENTER; 
        gbc.weightx = 0;
        panel.add(dias_restantes, gbc); 
        
        JLabel margen = new JLabel();
        gbc.gridy = 8;
        gbc.gridx = 0;
        panel.add(margen, gbc);
        
        JLabel dias_nota = new JLabel("Por defecto se calcula unicamente dentro de los utlimos 30 dias");
        dias_nota.setForeground(Color.WHITE);
        dias_nota.setFont(fuente); 
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        panel.add(dias_nota, gbc);
        
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
        
        setSize(900, 800);
        setLocationRelativeTo(null);
        setVisible(true);
    
    }
    
    public void actionPerformed(ActionEvent ae){
    if(ae.getSource()==guardar){
        String ID = ID_info;
        String info_type=tipo.getSelectedItem();
        String project_type=tipo_proyecto.getSelectedItem().toLowerCase();
        String codec=codigo_s.getSelectedItem();
        String client_type=tipo_cliente.getSelectedItem().toLowerCase();
        String days="30";
        
       
        
            try {
                Connect c= new Connect();
                String query ="insert into meter_info values('"+ID+"', '"+info_type+"', '"+project_type+"', '"+codec+"', '"+client_type+"', '"+days+"')";
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
