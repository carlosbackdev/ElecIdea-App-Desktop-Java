
package electricity_bills_system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;


public class setup_bill extends JFrame implements ActionListener {
    RoundedButton volver, crear;
    JTextField cajon_nombre, cajon_iva, cajon_precio;
    String bill_true;
    setup_bill(String bill_true) {
        this.bill_true = bill_true;
        setContentPane(new BackgroundPanel("images/Fichas.jpg"));
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(45, 15, 25, 15);
        gbc.ipadx = 40;

        JLabel head = new JLabel("Configurar Nuevo Parametro de Facturas");
        head.setForeground(Color.WHITE);
        head.setFont(new Font("Roboto", Font.PLAIN, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(head, gbc);

        JLabel nombre_parametro = new JLabel("Nombre del Parametro");
        nombre_parametro.setForeground(Color.WHITE);
        nombre_parametro.setFont(new Font("Roboto", Font.PLAIN, 24));
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

        JLabel nombreusu = new JLabel("IVA (en unidad)");
        gbc.anchor = GridBagConstraints.WEST;
        nombreusu.setForeground(Color.WHITE);
        nombreusu.setFont(new Font("Roboto", Font.PLAIN, 24));
        gbc.gridy = 2;
        gbc.gridx = 0;
        panel.add(nombreusu, gbc);

        cajon_iva = new JTextField();
        cajon_iva.setFont(new Font("Roboto", Font.PLAIN, 17));
        cajon_iva.setHorizontalAlignment(JTextField.CENTER);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0;
        panel.add(cajon_iva, gbc);

        JLabel nombrecompleto = new JLabel("Precio Por Hora");
        nombrecompleto.setForeground(Color.WHITE);
        nombrecompleto.setFont(new Font("Roboto", Font.PLAIN, 24));
        gbc.gridy = 3;
        gbc.gridx = 0;
        panel.add(nombrecompleto, gbc);

        cajon_precio = new JTextField();
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

        crear = new RoundedButton("Guardar");
        crear.setBackground(new Color(222, 239, 255));
        crear.setForeground(Color.BLACK);
        crear.setFont(new Font("Roboto", Font.PLAIN, 18));
        crear.addActionListener(this);
        panelBotones.add(crear, BorderLayout.WEST);

        volver = new RoundedButton("Volver");
        volver.setBackground(new Color(222, 239, 255));
        volver.setForeground(Color.BLACK);
        volver.setFont(new Font("Roboto", Font.PLAIN, 18));
        volver.addActionListener(this);
        panelBotones.add(volver, BorderLayout.EAST);

        add(panel, BorderLayout.PAGE_START);

        setSize(700, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

   
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == crear) {
            String name = cajon_nombre.getText().toLowerCase();
            int name_length = name.length();
            String iva = cajon_iva.getText().toLowerCase();
            int iva_length = iva.length();
            String price = cajon_precio.getText().toLowerCase();
            int price_length = price.length();
            

            boolean action = !name.isEmpty() && !iva.isEmpty() && !price.isEmpty();

            if (name_length > 20) {
                JOptionPane.showMessageDialog(null, "Nombre demasiado largo");
            }
            if (iva_length > 2) {
                JOptionPane.showMessageDialog(null, "IVA incorrecto");
            }
            if (price_length > 5) {
                JOptionPane.showMessageDialog(null, "Unidad de precio demasiado grande");
            }

            int nombre_existente = 0;
            try {
                Connect c = new Connect();
                String query = "SELECT NAME FROM setup_bill;";
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

            if (name_length <= 20 && iva_length <= 2 && price_length <= 5 && action && nombre_existente == 0) {
                try {
                    Connect c = new Connect();
                    String query = "insert into setup_bill values('"+name+"', '"+iva+"','"+price+"')";
                 
                    c.s.executeUpdate(query);

                    JOptionPane.showMessageDialog(null, "Parámetros guardados con éxito");
                    setVisible(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (ae.getSource() == volver) {
            setVisible(false);
        }
        String billt=bill_true;
        if ("bill".equals(billt)) {            
                new calculateBill("","");
            }
    
    }

    public static void main(String[] args) {
        new setup_bill("");

    }
}
