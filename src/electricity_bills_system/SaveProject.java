
package electricity_bills_system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;


public class SaveProject extends JFrame implements ActionListener {
    RoundedButton volver, crear;
    JTextField cajon_nombre, cajon_precio;
    JComboBox cajon_tipo;
    String NIF,ID_USER,DATE;
    SimpleDateFormat dateFormat;
    JFormattedTextField dateField;
    SaveProject(String NIF,String ID_USER) {
        this.NIF = NIF;
        this.ID_USER = ID_USER;
        setContentPane(new BackgroundPanel("images/fichas3.png"));
        setLayout(new BorderLayout());
        Color gris=new Color(210,210,210);
        setIconImage(new ImageIcon(getClass().getClassLoader().getResource("images/icono_app.png")).getImage());

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(35, 0, 25, 0);
        gbc.ipadx = 80;

        JLabel head = new JLabel("               Agregar Nuevo Projecto");
        head.setForeground(gris);
        head.setFont(new Font("Roboto", Font.PLAIN, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(head, gbc);

        JLabel nombre_parametro = new JLabel("Nombre del Proyecto");
        nombre_parametro.setForeground(gris);
        nombre_parametro.setFont(new Font("Roboto", Font.PLAIN, 20));
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        panel.add(nombre_parametro, gbc);

        cajon_nombre = new JTextField();
        cajon_nombre.setFont(new Font("Roboto", Font.PLAIN, 17));
        cajon_nombre.setHorizontalAlignment(JTextField.CENTER);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0;
        panel.add(cajon_nombre, gbc);

        JLabel nombreusu = new JLabel("Tipo de Proyecto");
        gbc.anchor = GridBagConstraints.WEST;
        nombreusu.setForeground(gris);
        nombreusu.setFont(new Font("Roboto", Font.PLAIN, 20));
        gbc.gridy = 2;
        gbc.gridx = 0;
        panel.add(nombreusu, gbc);

        cajon_tipo = new JComboBox();
        cajon_tipo.setFont(new Font("Roboto", Font.PLAIN, 16));
        cajon_tipo.addItem("Default");
        cajon_tipo.addItem("Solar");
        cajon_tipo.addItem("Reforma");
        cajon_tipo.addItem("Mantenimiento");
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0;
        panel.add(cajon_tipo, gbc);

        JLabel nombrecompleto = new JLabel("Añadir etiqueta");
        nombrecompleto.setForeground(gris);
        nombrecompleto.setFont(new Font("Roboto", Font.PLAIN, 20));
        gbc.gridy = 3;
        gbc.gridx = 0;
        panel.add(nombrecompleto, gbc);

        cajon_precio = new JTextField("");
        cajon_precio.setFont(new Font("Roboto", Font.PLAIN, 17));
        cajon_precio.setHorizontalAlignment(JTextField.CENTER);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0;
        panel.add(cajon_precio, gbc);

        JPanel panelBotones = new JPanel(new BorderLayout());
        panelBotones.setOpaque(false);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(panelBotones, gbc);

        crear = new RoundedButton("   Continuar   ");
        crear.setFont(new Font("Roboto", Font.PLAIN, 16));
        crear.addActionListener(this);
        panelBotones.add(crear, BorderLayout.WEST);

        volver = new RoundedButton("     Volver     ");
        volver.setFont(new Font("Roboto", Font.PLAIN, 16));
        volver.addActionListener(this);
        panelBotones.add(volver, BorderLayout.EAST);

        add(panel, BorderLayout.PAGE_START);

        setSize(700, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

   
    public void actionPerformed(ActionEvent ae) {
        dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        DATE = dateFormat.format(new Date());
        if (ae.getSource() == crear) {
            String name = cajon_nombre.getText().toLowerCase().trim();
            int name_length = name.length();
            String tipo =(String) cajon_tipo.getSelectedItem();
            tipo = tipo.toLowerCase().trim();
            int iva_length = tipo.length();
            String price = cajon_precio.getText().toLowerCase().trim();
            
            boolean action=true;
            if (name_length > 20) {
                JOptionPane.showMessageDialog(null, "Nombre demasiado largo");
                action=false;                
            }          

            int nombre_existente = 0;
            try {
                Connect c = new Connect();
                String query = "SELECT NAME FROM save_project where NIF='"+NIF+"';";
                ResultSet rs2 = c.s.executeQuery(query);
                while (rs2.next()) {
                    String rs3 = rs2.getString("NAME");
                    if (rs3.equals(name)) {
                        nombre_existente = 1;
                        JOptionPane.showMessageDialog(null, "Nombre de parametro ya existente");
                        break;
                    }
                }
                rs2.close();
                c.s.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (action && nombre_existente == 0) {
                try {
                    Connect c = new Connect();
                    String query = "insert into save_project values('"+name+"', '"+tipo+"','"+price+"','"+NIF+"','','"+DATE+"')";
                 
                    c.s.executeUpdate(query);

                    JOptionPane.showMessageDialog(null, "Parámetros guardados con éxito");
                    setVisible(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String[] PROYECTO={name,tipo,price};
            new CalculateProject("","",NIF,ID_USER,PROYECTO);
            setVisible(false);
            }
            
            
        } else if (ae.getSource() == volver) {
            setVisible(false);
        }       
    
    }

    public static void main(String[] args) {
        new SaveProject("","");

    }
}
