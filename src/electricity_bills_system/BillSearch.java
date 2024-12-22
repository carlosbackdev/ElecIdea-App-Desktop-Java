
package electricity_bills_system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class BillSearch extends JFrame implements ActionListener {
    String NIF,ID_USER,selectedID;
    JTextField cajon_nombre;
    RoundedButton buscar,volver;
    JComboBox<String> nombre_combo;
    JPopupMenu nombre_popup;
    Choice ID_choice,status_choice,factura_choice;
BillSearch(String NIF,String ID_USER){
    this.NIF=NIF;
    this.ID_USER=ID_USER;
    
    setContentPane(new BackgroundPanel("images/Fichas.jpg"));
    setLayout(new BorderLayout());
    Font fuente=new Font("Roboto", Font.PLAIN, 20);
    Font fuente2=new Font("Roboto", Font.PLAIN, 15);
    Font fuente3=new Font("Roboto", Font.PLAIN, 12);
     
    JPanel panel = new JPanel(new GridBagLayout());
    panel.setOpaque(false);
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(30, 0, 25, 0);
    gbc.ipadx = 120;

    JLabel head = new JLabel("                BUSCAR Y VER FACTURAS");
    head.setForeground(Color.WHITE);
    head.setFont(fuente);
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    panel.add(head, gbc);

    JLabel nombre_parametro = new JLabel("Busca y Selecciona nombre");
    nombre_parametro.setForeground(Color.WHITE);
    nombre_parametro.setFont(fuente);
    gbc.gridy = 1;
    gbc.gridwidth = 1;
    gbc.gridx = 0;
    panel.add(nombre_parametro, gbc);   

    cajon_nombre = new JTextField();
    cajon_nombre.setFont(fuente2);
    cajon_nombre.setHorizontalAlignment(JTextField.CENTER);
    gbc.gridx = 1;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.weightx = 0;
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
    ID_choice.add("seleciona ID");
    ID_choice.setFont(fuente2);
    gbc.gridx = 1;
    gbc.fill = GridBagConstraints.HORIZONTAL; 
    gbc.weightx = 0;
    panel.add(ID_choice, gbc);   

    JLabel nombreusu = new JLabel("Estado Factura:");
    gbc.anchor = GridBagConstraints.WEST;
    nombreusu.setForeground(Color.WHITE);
    nombreusu.setFont(fuente);
    gbc.gridy = 3;
    gbc.gridx = 0;
    panel.add(nombreusu, gbc);

    status_choice = new Choice();
    status_choice.add("Todos");
    status_choice.add("Pagado");
    status_choice.add("Pendiente");
    status_choice.add("Sin enviar");
    status_choice.setFont(fuente2);
    gbc.gridx = 1;
    gbc.fill = GridBagConstraints.HORIZONTAL; 
    gbc.weightx = 0;
    panel.add(status_choice, gbc);
    status_choice.addItemListener(new ItemListener() {
    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            update_factura(selectedID);
        }
    }
    });

    JLabel nombrecompleto = new JLabel("Selecionar factura:");
    nombrecompleto.setForeground(Color.WHITE);
    nombrecompleto.setFont(fuente);
    gbc.gridy = 4;
    gbc.gridx = 0;
    panel.add(nombrecompleto, gbc);

    factura_choice = new Choice();    
    factura_choice.setFont(fuente3);
    gbc.gridx = 1;
    gbc.fill = GridBagConstraints.HORIZONTAL; 
    gbc.weightx = 0;
    panel.add(factura_choice, gbc);
    
    JLabel margen = new JLabel();
    gbc.gridy = 5;
    gbc.gridx = 0;
    panel.add(margen, gbc);

    JPanel panelBotones = new JPanel(new BorderLayout());
    panelBotones.setOpaque(false);
    gbc.gridx = 0;
    gbc.gridy = 6;
    gbc.gridwidth = 2;
    gbc.fill = GridBagConstraints.NONE;
    gbc.anchor = GridBagConstraints.CENTER;
    panel.add(panelBotones, gbc);

    buscar = new RoundedButton(" Ver Factura ");
    buscar.setBackground(new Color(222, 239, 255));
    buscar.setForeground(Color.BLACK);
    buscar.setFont(new Font("Roboto", Font.PLAIN, 18));
    buscar.addActionListener(this);
    panelBotones.add(buscar, BorderLayout.WEST);

    volver = new RoundedButton("   Volver   ");
    volver.setBackground(new Color(222, 239, 255));
    volver.setForeground(Color.BLACK);
    volver.setFont(new Font("Roboto", Font.PLAIN, 18));
    volver.addActionListener(this);
    panelBotones.add(volver, BorderLayout.EAST);

    add(panel, BorderLayout.PAGE_START);

    setSize(700, 700);
    setLocationRelativeTo(null);
    setVisible(true);
    
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
    if (ID_choice.getItemCount() == 1) {
        ID_choice.select(0); // Seleccionar el único ID
        selectedID = ID_choice.getSelectedItem();  
        update_factura(selectedID);
    } else if (ID_choice.getItemCount() > 1) {        
        ID_choice.select(0);
        selectedID = ID_choice.getSelectedItem();
        update_factura(selectedID);
    }
}
public void update_factura(String selectedID) { 
    factura_choice.removeAll();
    try {
        Connect c = new Connect();
        String estado=status_choice.getSelectedItem();
        estado=estado.toLowerCase().trim();
        System.out.println(estado);
        
        String query="SELECT NUMBER_FACTURA, DAY(STR_TO_DATE(DATE, '%d-%m-%Y')) AS DIA, " +
                                   "YEAR(STR_TO_DATE(DATE, '%d-%m-%Y')) AS ANO, " +
                                   "MONTHNAME(STR_TO_DATE(DATE, '%d-%m-%Y')) AS MES, " +
                                   "STATUS " +
                                   "FROM bill_standard WHERE ID_CLIENT='" + selectedID + "'";
        
        if(!estado.equals("todos")){
            query="SELECT NUMBER_FACTURA, DAY(STR_TO_DATE(DATE, '%d-%m-%Y')) AS DIA, " +
                                   "YEAR(STR_TO_DATE(DATE, '%d-%m-%Y')) AS ANO, " +
                                   "MONTHNAME(STR_TO_DATE(DATE, '%d-%m-%Y')) AS MES, " +
                                   "STATUS " +
                                   "FROM bill_standard WHERE ID_CLIENT='" + selectedID + "' AND STATUS='"+estado+"'";
        }
        c.s.executeUpdate("SET lc_time_names = 'es_ES'");         
        ResultSet rs = c.s.executeQuery(query);
         while (rs.next()) {
            String numberfactura = rs.getString("NUMBER_FACTURA");
            String materialDate = rs.getString("MES");
            String ano = rs.getString("ANO");
            String state = rs.getString("STATUS");
            factura_choice.add("Factura " + numberfactura +", " + materialDate + " de " + ano +", "+state);
        }
        rs.close();
        c.s.close();
        
    } catch (Exception ex) {
        ex.printStackTrace();
        factura_choice.add("Sin registros");
    }
}

public void actionPerformed(ActionEvent ae){
    if(ae.getSource()==volver){
          setVisible(false);
          return;
      }
    String NAME=cajon_nombre.getText();
    String ID_CLIENT=ID_choice.getSelectedItem();
    String STATUS=status_choice.getSelectedItem().toLowerCase();
    String ADDRESS = "", HOUR = "", DATE = "", NUMBER_MATERIAL = "", TOTAL_MATERIAL = "", PARAMETROS = "", TOTAL_BILL = "";
    String NUMBER_FACTURA=factura_choice.getSelectedItem();
    NUMBER_FACTURA=NUMBER_FACTURA.substring(NUMBER_FACTURA.indexOf(" ")+1, NUMBER_FACTURA.indexOf(","));
    String factura_sinregistro=factura_choice.getSelectedItem();    
    
    if(!factura_sinregistro.equals("Sin registros")){
          if(ae.getSource()==buscar){
              try{
                  Connect c=new Connect();
                  String query="select * from bill_standard where ID_CLIENT='" + ID_CLIENT + "' AND NUMBER_FACTURA='"+NUMBER_FACTURA+"'";
                  System.out.println(query);
                  ResultSet rs = c.s.executeQuery(query);
                  if (rs.next()) {
                    ADDRESS = rs.getString("ADDRESS");
                    HOUR = rs.getString("HOUR");
                    DATE = rs.getString("DATE");
                    NUMBER_MATERIAL = rs.getString("NUMBER_MATERIAL");
                    TOTAL_MATERIAL = rs.getString("TOTAL_MATERIAL");
                    PARAMETROS = rs.getString("PARAMETROS");
                    TOTAL_BILL = rs.getString("TOTAL_BILL");
                }
                rs.close();
                c.s.close();
              }catch (Exception e){
                  e.printStackTrace();
              }
              
     new bill_standard_view(ID_CLIENT,NAME,ADDRESS,HOUR,DATE,NUMBER_MATERIAL,TOTAL_MATERIAL,PARAMETROS,NUMBER_FACTURA,TOTAL_BILL,NIF,ID_USER);
     }      
        
    }
    if(ae.getSource()==volver){
          setVisible(false);
      }
    
}
public static void main(String[] args) {
        new BillSearch("","");

    }
}