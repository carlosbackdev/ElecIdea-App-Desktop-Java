
package electricity_bills_system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MaterialFrame extends JFrame implements ActionListener {
    JTextField cliente, nombre_material, marca_nombre, precioField, unidadesField, ref;
    JButton agregarButton, guardarButton,salir;
    JComboBox ID_choice;
    DefaultTableModel tableModel;
    JTable materialTable;
    JComboBox<String> nombre_combo;
    JPopupMenu nombre_popup;
    SimpleDateFormat dateFormat;
    JFormattedTextField dateField;
    JLabel total_materiales,numero_parte;
    double total_final=0;
    double numero_parte2;
    String selectedID,ID_info,client_info,NIF,ID_USER,Clase;
    boolean existe=true;
    String[] PROYECTO;

    public MaterialFrame(String ID_info, String client_info,String NIF,String ID_USER,String Clase,String[] PROYECTO) {
        super("Añadir Materiales");
        setLayout(new BorderLayout());
        this.ID_info = ID_info;
        this.client_info = client_info;
        this.NIF = NIF;
        this.ID_USER = ID_USER;
        this.Clase = Clase;
        this.PROYECTO = PROYECTO;
        Color gris=new Color(210,210,210);
        setIconImage(new ImageIcon(getClass().getClassLoader().getResource("images/icono_app.png")).getImage());
        
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        

        cliente = new JTextField(client_info);
        
        ID_choice = new JComboBox();
        if(ID_info.length()<3){
        ID_choice.addItem("Busca Nombre y Elige una ID");}
        ID_choice.addItem(ID_info);
        ID_choice.setEnabled(false);
        ID_choice.addItemListener(new ItemListener() {
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                selectedID = (String) e.getItem();
                updateRowCount();
            }
        }
    });
        nombre_material = new JTextField(20);
        nombre_material.setText("buscar o añadir nuevo");
        marca_nombre = new JTextField(20);
        precioField = new JTextField(20);
        unidadesField = new JTextField(20);
        unidadesField.setText("1");
        ref = new JTextField(20);        
        dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateField = new JFormattedTextField(dateFormat);
        dateField.setValue(new Date());        
        total_materiales = new JLabel();
        String total_final2="total: "+total_final+" €";
        total_materiales.setText(total_final2);        
        numero_parte = new JLabel("Selecciona ID ");
                
        // Añadir campos al panel
        addField(inputPanel, gbc, "Nombre Cliente:", cliente, 0);
        addField(inputPanel, gbc, "ID cliente:", ID_choice, 1);
        addField(inputPanel, gbc, "Numero de Parte:", numero_parte , 2);
        addField(inputPanel, gbc, "Nombre del Material:", nombre_material, 3);
        addField(inputPanel, gbc, "Marca:", marca_nombre, 4);
        addField(inputPanel, gbc, "Precio por Unidad:", precioField, 5);
        addField(inputPanel, gbc, "Unidades:", unidadesField, 6);
        addField(inputPanel, gbc, "Referencia:", ref, 7);
        addField(inputPanel, gbc, "Fecha:", dateField, 8);
        addField(inputPanel, gbc, "Total Materiales:", total_materiales, 9);
        

        nombre_combo = new JComboBox<>();
        nombre_combo.setEditable(true);
        nombre_combo.setVisible(false);
        gbc.gridy = 2;

        
    nombre_popup = new JPopupMenu();
   
    cliente.getDocument().addDocumentListener(new DocumentListener() {
            private Timer timer = new Timer(600, new ActionListener() { 
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
                String text = cliente.getText();
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
                                    cliente.setText(item.getText());
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
                        int width = cliente.getWidth();
                        nombre_popup.setPreferredSize(new Dimension(width, nombre_popup.getComponentCount() * 30));
                        nombre_popup.show(cliente, 0, cliente.getHeight());
                    } else {
                        nombre_popup.setVisible(false);
                    }
                }
            }
        });
    
    
        if(ID_info.length()>1){
            int rowCount = 0;
           try {
        Connect c = new Connect();
        ResultSet rsCount = c.s.executeQuery("SELECT COUNT(DISTINCT NUMBER) as numero FROM material_bill WHERE ID_CLIENT='" + ID_info + "' AND NIF='"+NIF+"'");
        if (rsCount.next()) {
            rowCount = rsCount.getInt("numero");
        }
        rsCount.close();
        c.s.close();
    } catch (Exception ex) {
        ex.printStackTrace();
    }    
           int numeroP = rowCount + 1;
    numero_parte2 = numeroP;
    numero_parte.setText("Número de Parte: " + numeroP);
    }
        
    JComboBox<String> material_combo = new JComboBox<>();
    material_combo.setEditable(true);

        nombre_material.addFocusListener(new FocusListener() {
        @Override
        public void focusGained(FocusEvent e) {
            if (nombre_material.getText().equals("buscar o añadir nuevo")) {
                nombre_material.setText("");
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            if (nombre_material.getText().isEmpty()) {
                nombre_material.setText("buscar o añadir nuevo");
            }
        }
    });
    
        nombre_material.getDocument().addDocumentListener(new DocumentListener() {
        private Timer timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkMaterialExistence();
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
        boolean vaciado=false;
        private void checkMaterialExistence() {
            String text = nombre_material.getText().toLowerCase().trim();
            if (!text.isEmpty()) {
                try {
                    Connect c = new Connect();
                    ResultSet rs = c.s.executeQuery("SELECT COUNT(*) FROM material_bill WHERE NAME='" + text + "' AND NIF='" + NIF + "'");
                    if (rs.next()) {
                        int count = rs.getInt(1);
                        if (count>0){
                            vaciado=false;
                        }
                        if (count == 0) {
                            if(!vaciado){
                            marca_nombre.setText("");
                            precioField.setText("");
                            ref.setText("");
                            vaciado=true;
                            }
                        } else {
                            rs = c.s.executeQuery("SELECT BRAND, PRICE, REF FROM material_bill WHERE NAME='" + text + "' AND NIF='" + NIF + "'");
                            if (rs.next()) {
                                marca_nombre.setText(rs.getString("BRAND"));
                                precioField.setText(rs.getString("PRICE"));
                                ref.setText(rs.getString("REF"));
                            }
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    });


        agregarButton = new JButton("Agregar Material");
        agregarButton.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        inputPanel.add(agregarButton, gbc);
        
        guardarButton = new JButton("Guardar");
        guardarButton.addActionListener(this);
        gbc.gridy = 11;
        inputPanel.add(guardarButton, gbc);
        
        salir = new JButton("Salir");
        salir.addActionListener(this);
        gbc.gridy = 12;
        inputPanel.add(salir, gbc);

        String[] columnNames = {"Referencia", "Nombre" , "Marca", "Precio", "Unidades", "Total"};
        tableModel = new DefaultTableModel(columnNames, 0);
        materialTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(materialTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));
        
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        setSize(800, 900);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addField(JPanel panel, GridBagConstraints gbc, String label, Component field, int y) {
        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    
    public void updateID_choice(String selectedName) {
    ID_choice.removeAllItems();
    try {
        Connect c = new Connect();
        ResultSet rs = c.s.executeQuery("SELECT ID FROM client WHERE NAME='" + selectedName + "' AND NIF='"+NIF+"'");
        while (rs.next()) {
            ID_choice.addItem(rs.getString("ID"));
        }
        rs.close();
        c.s.close();
    } catch (Exception ex) {
        ex.printStackTrace();
    }

    // Forzar la actualización de la dirección si solo hay un ID
    if (ID_choice.getItemCount() == 1) {
        ID_choice.setSelectedIndex(0); 
        selectedID =(String) ID_choice.getSelectedItem();  
        ID_choice.setEnabled(false);
    } else if (ID_choice.getItemCount() > 1) {        
        ID_choice.insertItemAt("Elige una ID", 0);  
        ID_choice.setSelectedIndex(0); 
        selectedID =(String) ID_choice.getSelectedItem();
        ID_choice.setEnabled(true);
    }

    // Llamar a updateRowCount para actualizar el número de parte
    updateRowCount();
}
    

private void updateRowCount() {
    int rowCount = 0;
    try {
        Connect c = new Connect();
        ResultSet rsCount = c.s.executeQuery("SELECT COUNT(DISTINCT NUMBER) as numero FROM material_bill WHERE ID_CLIENT='" + selectedID + "'");
        if (rsCount.next()) {
            rowCount = rsCount.getInt("numero");
        }
        rsCount.close();
        c.s.close();
    } catch (Exception ex) {
        ex.printStackTrace();
    }

    int numeroP = rowCount + 1;
    numero_parte2 = numeroP;
    numero_parte.setText("Número de Parte: " + numeroP);
}
     public void actionPerformed(ActionEvent e) {
        if (e.getSource() == agregarButton) {
            if (e.getSource() == agregarButton) {
            String refe = ref.getText();
            String nombre_material2 = nombre_material.getText();
            String marca2 = marca_nombre.getText();            
            String precio2 = precioField.getText();
            String unidades2 = unidadesField.getText();            
            String precio3="";
            for(int i=0;i<precio2.length();i++){
                char a=precio2.charAt(i);
                if(a==','){
                    a='.';
                }
                precio3+=a;                
            }
            precio2=precio3;  
            
            if (!refe.isEmpty() && !nombre_material2.isEmpty() && !marca2.isEmpty() && !precio2.isEmpty() && !unidades2.isEmpty()) {
            double precio_int = Double.parseDouble(precio2);
            double unidades_int = Double.parseDouble(unidades2);
            double total_int = precio_int * unidades_int;
            total_final=total_int+total_final;
            String total=""+total_int;
            total_materiales.setText("Total: " + total_final +" €");
            
            tableModel.addRow(new Object[]{refe, nombre_material2, marca2, precio2 , unidades2, total});
            
            
            }else{
                JOptionPane.showMessageDialog(null,"rellena todos los campos");
            }
            

            if (!refe.isEmpty() && !nombre_material2.isEmpty() && !marca2.isEmpty() && !precio2.isEmpty() && !unidades2.isEmpty()){
            nombre_material.setText("");
            marca_nombre.setText("");
            precioField.setText("");
            unidadesField.setText("");
            ref.setText("");
            }
        }
        }
        if (e.getSource() == guardarButton) {
            try {
                Connect c = new Connect();
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    String ID_2 = selectedID; // Usar el ID seleccionado
                    String number = numero_parte.getText().split(": ")[1];
                    String ref_material = (String) tableModel.getValueAt(i, 0);
                    String nombre_material2 = (String) tableModel.getValueAt(i, 1);
                    String brand = (String) tableModel.getValueAt(i, 2);
                    String price_unit = (String) tableModel.getValueAt(i, 3);
                    String unit = (String) tableModel.getValueAt(i, 4);
                    String total_price = (String) tableModel.getValueAt(i, 5);
                    String date = dateField.getText();
                    if(ID_info.length()>1){
                    ID_2=(String) ID_choice.getSelectedItem();
                    }
                    String query = "INSERT INTO material_bill VALUES('" + ID_2 + "', '" + number + "','" + nombre_material2.toLowerCase().trim() + "','" + brand.toLowerCase().trim() + "','" + price_unit + "','" + unit + "','" + ref_material.trim() + "','" + date + "','" + total_price + "','"+NIF+"')";

                    c.s.executeUpdate(query);
                }
                JOptionPane.showMessageDialog(null, "Materiales guardados con éxito");
                setVisible(false);                
            } catch (Exception ea) {
                ea.printStackTrace();
            }
            
            if(ID_info.length()>1 && !Clase.equals("proyecto")){
            new calculateBill(ID_info, client_info,NIF,ID_USER);
            }
            if(Clase.equals("proyecto")){
            new CalculateProject(ID_info, client_info,NIF,ID_USER,PROYECTO);
            }
        }else if (e.getSource() == salir){
         setVisible(false);     
         
        }
    }
    
   

    public static void main(String[] args) {
        new MaterialFrame("","","","","",new String[] {});
    }
}