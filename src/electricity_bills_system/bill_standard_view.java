
package electricity_bills_system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class bill_standard_view extends JFrame implements ActionListener {
    String ID_2, NAME, ADDRESS, HOUR, DATE, NUMBER_MATERIAL, TOTAL_MATERIAL, PARAMETROS, NUMBER_FACTURA, TOTAL_BILL;
    DefaultTableModel tableModel;
    JTable materialTable;
    JButton agregarButton, guardarButton;
    
    bill_standard_view(String ID_2,String NAME,String ADDRESS,String HOUR,String DATE,String NUMBER_MATERIAL,String TOTAL_MATERIAL,String PARAMETROS,String NUMBER_FACTURA,String TOTAL_BILL){
    super("Añadir Materiales");
    setContentPane(new BackgroundPanel("images/Fichas.jpg"));
    setLayout(new BorderLayout());

    this.ID_2 = ID_2; 
    this.NAME = NAME;    
    this.ADDRESS = ADDRESS;
    this.HOUR = HOUR;
    this.DATE = DATE;
    this.NUMBER_MATERIAL = NUMBER_MATERIAL;
    this.TOTAL_MATERIAL = TOTAL_MATERIAL;
    this.PARAMETROS = PARAMETROS; //? como ponerlo
    this.NUMBER_FACTURA = NUMBER_FACTURA;
    this.TOTAL_BILL = TOTAL_BILL;
                 
    JPanel inputPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));    
    inputPanel.setBorder(BorderFactory.createTitledBorder("Datos del Cliente"));
    
        inputPanel.add(Box.createVerticalStrut(9));
    addTextArea(inputPanel, gbc, "", 0);
    addTextArea(inputPanel, gbc, "   Nombre:    " + NAME, 1);
        inputPanel.add(Box.createVerticalStrut(6));
    addTextArea(inputPanel, gbc,"   ID:    "+ ID_2, 2);
        inputPanel.add(Box.createVerticalStrut(6));
    addTextArea(inputPanel, gbc, "   Direccón:    " + ADDRESS , 3);
        inputPanel.add(Box.createVerticalStrut(6));
    addTextArea(inputPanel, gbc, "   Fecha de Factura:    " + DATE,4);
        inputPanel.add(Box.createVerticalStrut(6));
    addTextArea(inputPanel, gbc,"   Numero Factura:    "+ NUMBER_FACTURA, 5);
        inputPanel.add(Box.createVerticalStrut(6));
    addTextArea(inputPanel, gbc, "   Numero Parte Material:    "+NUMBER_MATERIAL,6);
        inputPanel.add(Box.createVerticalStrut(6));
    addTextArea(inputPanel, gbc,"   Total material:  " +TOTAL_MATERIAL +" €", 7);
        inputPanel.add(Box.createVerticalStrut(6));
    addTextArea(inputPanel, gbc, "   Precio hora:    ", 8);
        inputPanel.add(Box.createVerticalStrut(6));
    addTextArea(inputPanel, gbc,"   Horas totales:    "+ HOUR +" h", 9);
        inputPanel.add(Box.createVerticalStrut(6));
    addTextArea(inputPanel, gbc,"   Total factura:    "+TOTAL_BILL+" €", 10);
    
    JPanel inputPanel2 = new JPanel(new GridBagLayout());
    inputPanel2.setLayout(new BoxLayout(inputPanel2, BoxLayout.Y_AXIS));    
    inputPanel2.setBorder(BorderFactory.createTitledBorder("Datos de la Empresa"));
   
    inputPanel2.add(Box.createVerticalStrut(9));
    addTextArea(inputPanel2, gbc, "   Empresa: ", 1);
    inputPanel2.add(Box.createVerticalStrut(6));
    addTextArea(inputPanel2, gbc, "   NIF: ", 2);
    inputPanel2.add(Box.createVerticalStrut(6));
    addTextArea(inputPanel2, gbc, "   Dirección: ", 3);
    inputPanel2.add(Box.createVerticalStrut(6));
    addTextArea(inputPanel2, gbc, "   Teléfono: ", 4);
    inputPanel2.add(Box.createVerticalStrut(6));
    addTextArea(inputPanel2, gbc, "   Correo: ", 5);
    inputPanel2.add(Box.createVerticalStrut(6));
    addTextArea(inputPanel2, gbc, "", 6);
    inputPanel2.add(Box.createVerticalStrut(6));
    addTextArea(inputPanel2, gbc, "", 7);
    inputPanel.add(Box.createVerticalStrut(6));
       
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));    
    agregarButton = new JButton("Exportar Datos a Excel");
        agregarButton.addActionListener(this);       
        buttonPanel.add(agregarButton, gbc);
        
    guardarButton = new JButton("   Guardar   ");
        guardarButton.addActionListener(this);          
        buttonPanel.add(guardarButton, gbc);  
        
              
    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, inputPanel, inputPanel2);
    splitPane.setDividerLocation(600); 
    splitPane.setResizeWeight(0);
    splitPane.setContinuousLayout(true);
    
      
    
    ArrayList<String> REF_TABLE = new ArrayList<>();
    ArrayList<String> NAME_TABLE = new ArrayList<>();
    ArrayList<String> BRAND_TABLE = new ArrayList<>();
    ArrayList<String> PRICE_TABLE = new ArrayList<>();
    ArrayList<String> UNIT_TABLE = new ArrayList<>();
    ArrayList<String> TOTAL_TABLE = new ArrayList<>();  
    int i=0;
    try{
        Connect c = new Connect();
        ResultSet rs = c.s.executeQuery("SELECT count(*) AS FILAS FROM material_bill WHERE ID_CLIENT='" + ID_2 + "' AND NUMBER='"+NUMBER_MATERIAL+"'");
        
        if (rs.next()) {
            i = rs.getInt("FILAS");
            
        }        
                    
        if(i>0){
            ResultSet rs2 = c.s.executeQuery("SELECT * FROM material_bill WHERE ID_CLIENT='" + ID_2 + "' AND NUMBER='"+NUMBER_MATERIAL+"'");
             while (rs2.next()) {
            String refe = rs2.getString("REF");
            String nombre_material2 = rs2.getString("NAME");
            String marca2 = rs2.getString("BRAND");            
            String precio2 = rs2.getString("PRICE");
            String unidades2 = rs2.getString("UNIT");
            String total = rs2.getString("TOTAL_PRICE");
            REF_TABLE.add(refe);
            NAME_TABLE.add(nombre_material2);
            BRAND_TABLE.add(marca2);
            PRICE_TABLE.add(precio2);
            UNIT_TABLE.add(unidades2);
            TOTAL_TABLE.add(total);         
            }
            rs2.close();            
        }    
        rs.close();        
        c.s.close();   
        
        } catch (Exception ea) {
            ea.printStackTrace();
        }
    
    String[] columnNames = {"Referencia", "Nombre" , "Marca", "Precio", "Unidades", "Total"};
    tableModel = new DefaultTableModel(columnNames, 0);
    materialTable = new JTable(tableModel);
    JScrollPane scrollPane = new JScrollPane(materialTable);
    scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));
    if(i>0){
        for(int j=0;j<i;j++){
        String refe ="  "+ REF_TABLE.get(j);
            String nombre_material2 ="  "+ NAME_TABLE.get(j);
            String marca2 ="  "+ BRAND_TABLE.get(j);            
            String precio2 ="  "+ PRICE_TABLE.get(j)+" €";
            String unidades2 ="  "+ UNIT_TABLE.get(j);
            String total ="  "+ TOTAL_TABLE.get(j)+" €";
    tableModel.addRow(new Object[]{refe, nombre_material2, marca2, precio2 , unidades2, total});
        }
    }
    
    
        
    add(buttonPanel, BorderLayout.SOUTH);
    add(splitPane, BorderLayout.NORTH);
    add(scrollPane, BorderLayout.CENTER);
    setSize(1200, 800);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
    }
    
    private void addTextArea(JPanel panel, GridBagConstraints gbc, String label, int y) {
        gbc.gridx = 0;
        gbc.gridy = y;
        JLabel labell = new JLabel(label);
        labell.setHorizontalAlignment(SwingConstants.LEFT);
        labell.setPreferredSize(new Dimension(200, labell.getPreferredSize().height));
        panel.add(labell, gbc);
        gbc.gridx = 2;
        
        
    }
    
     public void actionPerformed(ActionEvent ae) {
         
     }
    
    public static void main(String[]args){
        new bill_standard_view("","","","","","","","","","");
    }
}
