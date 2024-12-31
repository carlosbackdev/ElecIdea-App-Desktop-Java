
package electricity_bills_system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class ProjectSearch extends JFrame implements ActionListener {
    String[] meses = {"Todos Meses","Enero","Febrero","Marzo","Aril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
    String NIF,ID_USER,selectedID;
    JTextField cajon_nombre;
    RoundedButton buscar,volver;
    JComboBox<String> nombre_combo;
    JPopupMenu nombre_popup;
    JComboBox ID_choice,status_choice,factura_choice,fecha_choice_mes,fecha_choice_year;
    String[] PROYECTO = new String[5];
ProjectSearch(String NIF,String ID_USER){
    this.NIF=NIF;
    this.ID_USER=ID_USER;
    
    setContentPane(new BackgroundPanel("images/Fichas3.png"));
    setLayout(new BorderLayout());
    Font fuente=new Font("Roboto", Font.PLAIN, 20);
    Font fuente2=new Font("Roboto", Font.PLAIN, 15);
    Font fuente3=new Font("Roboto", Font.PLAIN, 12);
    Color gris=new Color(210,210,210);
     
    JPanel panel = new JPanel(new GridBagLayout());
    panel.setOpaque(false);
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(25, 0, 25, 0);
    gbc.ipadx = 10;

    JLabel head = new JLabel("        BUSCAR Y VER PROYECTOS");
    head.setForeground(gris);
    head.setFont(fuente);
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    panel.add(head, gbc);

    JLabel nombre_parametro = new JLabel("Buscar por Cliente");
    gbc.anchor = GridBagConstraints.WEST;
    nombre_parametro.setForeground(gris);
    nombre_parametro.setFont(fuente);
    gbc.gridy = 1;
    gbc.gridwidth = 1;
    gbc.gridx = 0;
    panel.add(nombre_parametro, gbc);   

    cajon_nombre = new JTextField();
    cajon_nombre.setFont(fuente2);
    cajon_nombre.setHorizontalAlignment(JTextField.CENTER);
    cajon_nombre.setPreferredSize(new Dimension(215, 25));
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
                            item.setPreferredSize(new Dimension(215, 25)); 
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
                        int width = cajon_nombre.getWidth();
                        nombre_popup.setPreferredSize(new Dimension(width, nombre_popup.getComponentCount() * 30));
                        nombre_popup.show(cajon_nombre, 0, cajon_nombre.getHeight());
                    } else {
                        nombre_popup.setVisible(false);
                    }
                }
            }
        });
            cajon_nombre.addFocusListener(new FocusListener() {
        @Override
        public void focusGained(FocusEvent e) {
            // Nada que hacer aquí
        }

        @Override
        public void focusLost(FocusEvent e) {
            // Reafirmar el tamaño del JTextField cuando se pierde el foco (después de seleccionar el nombre)
            cajon_nombre.setPreferredSize(new Dimension(215, 25)); // Tamaño fijo
            cajon_nombre.revalidate();
        }
    });
    
    JLabel numeroid = new JLabel("Numero Identificacion");
    numeroid.setForeground(gris);
    numeroid.setFont(fuente); 
    gbc.gridy = 2;
    gbc.gridx = 0;
    panel.add(numeroid, gbc);
        

    ID_choice = new JComboBox();
    ID_choice.addItem("seleciona ID");
    ID_choice.setFont(fuente2);
    ID_choice.setEnabled(false);
    gbc.gridx = 1;
    gbc.fill = GridBagConstraints.HORIZONTAL; 
    gbc.weightx = 0;
    panel.add(ID_choice, gbc);
    ID_choice.addItemListener(new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                selectedID =(String) ID_choice.getSelectedItem();
                update_factura(selectedID);
            }
        }
    });

    JLabel nombreusu = new JLabel("Tipo de Proyecto:");
    gbc.anchor = GridBagConstraints.WEST;
    nombreusu.setForeground(gris);
    nombreusu.setFont(fuente);
    gbc.gridy = 3;
    gbc.gridx = 0;
    panel.add(nombreusu, gbc);

    status_choice = new JComboBox();
    status_choice.addItem("Todos");
    status_choice.addItem("Default");
    status_choice.addItem("Solar");
    status_choice.addItem("Reforma");
    status_choice.addItem("Mantenimiento");
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
    
    
    JLabel nombrecompleto = new JLabel("Selecionar Proyecto:");
    nombrecompleto.setForeground(gris);
    nombrecompleto.setFont(fuente);
    gbc.gridy = 5;
    gbc.gridx = 0;
    panel.add(nombrecompleto, gbc);

    factura_choice = new JComboBox();    
    factura_choice.setFont(fuente3);
    factura_choice.setEnabled(false);
    gbc.gridx = 1;
    gbc.fill = GridBagConstraints.HORIZONTAL; 
    gbc.weightx = 0;
    panel.add(factura_choice, gbc);
    
    JLabel margen = new JLabel();
    gbc.gridy = 6;
    gbc.gridx = 0;
    panel.add(margen, gbc);

    JPanel panelBotones = new JPanel(new BorderLayout());
    panelBotones.setOpaque(false);
    gbc.gridx = 0;
    gbc.gridy = 7;
    gbc.gridwidth = 2;
    gbc.fill = GridBagConstraints.NONE;
    gbc.ipadx=50;
    gbc.anchor = GridBagConstraints.CENTER;
    panel.add(panelBotones, gbc);

    buscar = new RoundedButton("   Ver Proyecto   ");
    buscar.setFont(new Font("Roboto", Font.PLAIN, 16));
    buscar.addActionListener(this);
    panelBotones.add(buscar, BorderLayout.WEST);

    volver = new RoundedButton("        Volver        ");
    volver.setFont(new Font("Roboto", Font.PLAIN, 16));
    volver.addActionListener(this);
    panelBotones.add(volver, BorderLayout.EAST);

    add(panel, BorderLayout.PAGE_START);

    setSize(700, 700);
    setLocationRelativeTo(null);
    setVisible(true);
    
}

public void updateID_choice(String selectedName) {
    ID_choice.removeAllItems();
    try {
        Connect c = new Connect();
        ResultSet rs = c.s.executeQuery("SELECT ID FROM client WHERE NAME='" + selectedName + "' AND NIF='"+NIF+"'");
        while (rs.next()) {
            ID_choice.addItem(rs.getString("ID"));
            selectedID = rs.getString("ID"); // Actualizar selectedID
        }
        rs.close();
        c.s.close();
    } catch (Exception ex) {
        ex.printStackTrace();
    }   
    if (ID_choice.getItemCount() == 1) {
        ID_choice.setSelectedIndex(0); 
        selectedID =(String) ID_choice.getSelectedItem();  
        update_factura(selectedID);
        ID_choice.setEnabled(false);
    } else if (ID_choice.getItemCount() > 1) {        
        ID_choice.setSelectedIndex(0); 
        selectedID =(String) ID_choice.getSelectedItem();
        update_factura(selectedID);
        ID_choice.setEnabled(true);
    }
}
public void update_factura(String selectedID) { 
    factura_choice.removeAllItems();
     ArrayList<String> NAME_PROJECT = new ArrayList<>();
    String query;
    boolean nombre=false;
    boolean registro=false;
    try {
        Connect c = new Connect();
        String estado=(String) status_choice.getSelectedItem();
        estado=estado.toLowerCase().trim();      
        String query2="";
        if(!estado.equals("todos")){
            query2="SELECT * FROM save_project WHERE TYPE='"+estado+"'";
            ResultSet rs = c.s.executeQuery(query2);
            while(rs.next()){
               NAME_PROJECT.add(rs.getString("NAME"));
               nombre=true;
            }           
        }        
        if(nombre){
            query = "SELECT NAME_PROJECT, " +
                   "DAY(STR_TO_DATE(DATE, '%d-%m-%Y')) AS DIA, " +
                   "YEAR(STR_TO_DATE(DATE, '%d-%m-%Y')) AS ANO, " +
                   "MONTHNAME(STR_TO_DATE(DATE, '%d-%m-%Y')) AS MES " +
                   "FROM bill_standard WHERE NAME_PROJECT IN (";

            StringBuilder inClause = new StringBuilder();
            for (int i = 0; i < NAME_PROJECT.size(); i++) {
                inClause.append("'").append(NAME_PROJECT.get(i)).append("'");
                if (i < NAME_PROJECT.size() - 1) {
                    inClause.append(", "); 
                }
            }
            query += inClause.toString() + ")";

        }else{
                
        query="SELECT NAME_PROJECT, DAY(STR_TO_DATE(DATE, '%d-%m-%Y')) AS DIA, " +
                                   "YEAR(STR_TO_DATE(DATE, '%d-%m-%Y')) AS ANO, " +
                                   "MONTHNAME(STR_TO_DATE(DATE, '%d-%m-%Y')) AS MES, " +
                                   "STATUS " +
                                   "FROM bill_standard WHERE ID_CLIENT='" + selectedID + "'";
        }
        
        
        c.s.executeUpdate("SET lc_time_names = 'es_ES'"); 
        ResultSet rs = c.s.executeQuery(query);
        if(!rs.next()){
            factura_choice.addItem("Sin registros");
            factura_choice.setEnabled(false);
        }else            
            do {
               String NOMBRE = rs.getString("NAME_PROJECT");
               String materialDate = rs.getString("MES");
               String ano = rs.getString("ANO");
               if(NOMBRE!=null && !NOMBRE.isBlank()){
               factura_choice.addItem(" " + NOMBRE +", " + materialDate + " de " + ano);
               factura_choice.setEnabled(true);
               registro=true;               
               }

            } while (rs.next());
        rs.close();
        c.s.close();
        
    } catch (Exception ex) {
        ex.printStackTrace();
        factura_choice.addItem("Sin registros");
    }
    if(!registro){
        factura_choice.addItem("Sin registros");
    }
}

public void actionPerformed(ActionEvent ae){
    if(ae.getSource()==volver){
          setVisible(false);
          return;
      }
   
    String NAME="",ID_CLIENT="", ADDRESS = "", HOUR = "", DATE = "", NUMBER_MATERIAL = "",NUMBER_FACTURA="", TOTAL_MATERIAL = "", PARAMETROS = "", TOTAL_BILL = "";
    String NOMBRE_PROYECTO=(String) factura_choice.getSelectedItem();
    NOMBRE_PROYECTO=NOMBRE_PROYECTO.substring(0, NOMBRE_PROYECTO.indexOf(","));
    String factura_sinregistro=(String) factura_choice.getSelectedItem();    
    
    if(!factura_sinregistro.equals("Sin registros")){
          if(ae.getSource()==buscar){
              try{
                  Connect c=new Connect();
                  String query="select * from bill_standard where NAME_PROJECT='"+NOMBRE_PROYECTO.trim().toLowerCase()+"'";
                  ResultSet rs = c.s.executeQuery(query);
                  if (rs.next()) {
                    ID_CLIENT=rs.getString("ID_CLIENT");
                    NAME=rs.getString("NAME");
                    ADDRESS = rs.getString("ADDRESS");
                    HOUR = rs.getString("HOUR");
                    DATE = rs.getString("DATE");
                    NUMBER_MATERIAL = rs.getString("NUMBER_MATERIAL");
                    TOTAL_MATERIAL = rs.getString("TOTAL_MATERIAL");
                    PARAMETROS = rs.getString("PARAMETROS");
                    NUMBER_FACTURA=rs.getString("NUMBER_FACTURA");
                    TOTAL_BILL = rs.getString("TOTAL_BILL");
                }
                  String query2="SELECT * FROM save_project WHERE NAME='"+NOMBRE_PROYECTO.trim().toLowerCase()+"'";
                  ResultSet rs2 = c.s.executeQuery(query2);
                  while(rs2.next()){
                    PROYECTO[0] = rs2.getString("NAME");
                    PROYECTO[1] = rs2.getString("TYPE");
                    PROYECTO[2] = rs2.getString("INFO");
                    PROYECTO[3] = rs2.getString("NIF");
                    PROYECTO[4] = rs2.getString("ID_CLIENT");
                  }
                  
                rs.close();
                rs2.close();
                c.s.close();
              }catch (Exception e){
                  e.printStackTrace();
              }
               
     new ProjectView(ID_CLIENT,NAME,ADDRESS,HOUR,DATE,NUMBER_MATERIAL,TOTAL_MATERIAL,PARAMETROS,NUMBER_FACTURA,TOTAL_BILL,NIF,ID_USER,PROYECTO);
     }      
        
    }
    if(ae.getSource()==volver){
          setVisible(false);
      }
    
}
public static void main(String[] args) {
        new ProjectSearch("","");

    }
}