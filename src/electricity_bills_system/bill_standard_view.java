
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
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import electricity_bills_system.EmailSender;

public class bill_standard_view extends JFrame implements ActionListener {
    String ID_2, NAME, ADDRESS, HOUR, DATE, NUMBER_MATERIAL, TOTAL_MATERIAL, PARAMETROS, NUMBER_FACTURA, TOTAL_BILL,EMAIL;
    String TOTAL_SINIVA,TOTAL;
    DefaultTableModel tableModel;
    JTable materialTable;
    JButton agregarButton, guardarButton,enviar;
    Double IVA_int,IVA_resta,PRICE_HOUR_int,TOTAL_BILL_INT,HOUR_INT;
    
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
    
    try{
        Connect c=new Connect();
        ResultSet rs = c.s.executeQuery("SELECT * FROM setup_bill WHERE NAME='" +PARAMETROS+ "'");
        if(rs.next()){
            IVA_int=rs.getDouble("IVA");
            PRICE_HOUR_int=rs.getDouble("PRICE");
        }
    } catch(Exception e){
      e.printStackTrace();
    }
    HOUR_INT=Double.parseDouble(HOUR);
    TOTAL_BILL_INT=Double.parseDouble(TOTAL_BILL);
    IVA_resta=TOTAL_BILL_INT*(IVA_int/100);
    double TOTAL_SINIVAd=TOTAL_BILL_INT-IVA_resta;    
    TOTAL_SINIVA=NumberFormate.formatear(TOTAL_SINIVAd);
    TOTAL=NumberFormate.formatear(TOTAL_BILL_INT);
    
    
    
    JPanel inputPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));    
    inputPanel.setBorder(BorderFactory.createTitledBorder("Datos del Cliente"));
    
        inputPanel.add(Box.createVerticalStrut(9));
    addTextArea(inputPanel, gbc, "", 0);
    addTextArea(inputPanel, gbc, "     Nombre:    " + NAME, 1);
        inputPanel.add(Box.createVerticalStrut(6));
    addTextArea(inputPanel, gbc,"     ID:    "+ ID_2, 2);
        inputPanel.add(Box.createVerticalStrut(6));
    addTextArea(inputPanel, gbc, "     Direccón:    " + ADDRESS , 3);
        inputPanel.add(Box.createVerticalStrut(6));
    addTextArea(inputPanel, gbc, "     Fecha de Factura:    " + DATE,4);
        inputPanel.add(Box.createVerticalStrut(15));
    addTextArea(inputPanel, gbc,"Datos de la Factura", 5);
        inputPanel.add(Box.createVerticalStrut(8));
    addTextArea(inputPanel, gbc,"     Numero Factura:    "+ NUMBER_FACTURA, 6);
        inputPanel.add(Box.createVerticalStrut(6));
    addTextArea(inputPanel, gbc, "     Numero Parte Material:    "+NUMBER_MATERIAL,7);
        inputPanel.add(Box.createVerticalStrut(6));
    addTextArea(inputPanel, gbc,"     Total material:  " +TOTAL_MATERIAL +" €", 8);
        inputPanel.add(Box.createVerticalStrut(6));
    addTextArea(inputPanel, gbc, "     Precio hora:    "+PRICE_HOUR_int+" €", 9);
        inputPanel.add(Box.createVerticalStrut(6));
    addTextArea(inputPanel, gbc,"     Horas totales:    "+ HOUR +" h", 10);
        inputPanel.add(Box.createVerticalStrut(6));
    addTextArea(inputPanel, gbc,"     Total factura:    "+TOTAL+" €", 11);
    
    JPanel inputPanel2 = new JPanel(new GridBagLayout());
    inputPanel2.setLayout(new BoxLayout(inputPanel2, BoxLayout.Y_AXIS));    
    inputPanel2.setBorder(BorderFactory.createTitledBorder("Datos de la Empresa"));
   
    inputPanel2.add(Box.createVerticalStrut(9));
    addTextArea(inputPanel2, gbc, "     Empresa: ", 1);
    inputPanel2.add(Box.createVerticalStrut(6));
    addTextArea(inputPanel2, gbc, "     NIF: ", 2);
    inputPanel2.add(Box.createVerticalStrut(6));
    addTextArea(inputPanel2, gbc, "     Dirección: ", 3);
    inputPanel2.add(Box.createVerticalStrut(6));
    addTextArea(inputPanel2, gbc, "     Teléfono: ", 4);
    inputPanel2.add(Box.createVerticalStrut(6));
    addTextArea(inputPanel2, gbc, "     Correo: ", 5);
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
        
    enviar = new JButton("Exportar y Enviar al mail del Cliente");
        enviar.addActionListener(this);       
        buttonPanel.add(enviar, gbc);  
               
    guardarButton = new JButton("Guardar en el Sistema y Cerrar");
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
        
        tableModel.addRow(new Object[]{"", "", "", "" , "Total material "," "+TOTAL_MATERIAL+" €"});
        tableModel.addRow(new Object[]{"", "", "", "" ,HOUR+ " h, mano de obra total: ","  "+(HOUR_INT*PRICE_HOUR_int)+" €"});
        tableModel.addRow(new Object[]{"", "", "", "" , "Total sin IVA:","  "+TOTAL_SINIVA+" €"});
        tableModel.addRow(new Object[]{"", "", "", "" , "IVA:","  "+IVA_int+"%"});
        tableModel.addRow(new Object[]{"", "", "", "" , "Total:","  "+TOTAL+" €"});
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
         if (ae.getSource() == agregarButton || ae.getSource() == enviar) {
        try {
            //rescatar email
            
            try{
                Connect c = new Connect();
                ResultSet rs = c.s.executeQuery("SELECT EMAIL FROM client WHERE ID='" + ID_2 + "'");
                if (rs.next()) {
                    EMAIL = rs.getString("EMAIL");
            }                
             rs.close();      
             c.s.close();  
            }   catch (Exception e) {
                    e.printStackTrace();
            }
            
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Factura "+NUMBER_FACTURA+" "+NAME+" en "+DATE);

             int rowIndex = 0;
            Row clientHeaderRow = sheet.createRow(rowIndex++);
            clientHeaderRow.createCell(0).setCellValue("DATOS DEL CLIENTE");
            clientHeaderRow.createCell(1).setCellValue(""); 

            Row clientRow1 = sheet.createRow(rowIndex++);
            clientRow1.createCell(0).setCellValue("Nombre:");
            clientRow1.createCell(1).setCellValue(NAME);

            Row clientRow2 = sheet.createRow(rowIndex++);
            clientRow2.createCell(0).setCellValue("Numero de cliente");
            clientRow2.createCell(1).setCellValue(ID_2);
            clientRow2.createCell(2).setCellValue("numero de cuenta");
            
            Row clientRow3 = sheet.createRow(rowIndex++);
            clientRow3.createCell(0).setCellValue("Dirección:");
            clientRow3.createCell(1).setCellValue(ADDRESS);      
            clientRow3.createCell(2).setCellValue("ES3244");

            rowIndex++;

            Row companyHeaderRow = sheet.createRow(rowIndex++);
            companyHeaderRow.createCell(0).setCellValue("DATOS DE LA EMPRESA");
            companyHeaderRow.createCell(1).setCellValue("");

            Row companyRow1 = sheet.createRow(rowIndex++);
            companyRow1.createCell(0).setCellValue("Empresa:");
            companyRow1.createCell(1).setCellValue("Mi Empresa");

            Row companyRow2 = sheet.createRow(rowIndex++);
            companyRow2.createCell(0).setCellValue("NIF:");
            companyRow2.createCell(1).setCellValue("12345678A");

            Row companyRow3 = sheet.createRow(rowIndex++);
            companyRow3.createCell(0).setCellValue("Dirección:");
            companyRow3.createCell(1).setCellValue("Calle Ficticia 123");

            Row companyRow4 = sheet.createRow(rowIndex++);
            companyRow4.createCell(0).setCellValue("Teléfono:");
            companyRow4.createCell(1).setCellValue("987654321");
            companyRow4.createCell(2).setCellValue("numero de cuenta");

            Row companyRow5 = sheet.createRow(rowIndex++);
            companyRow5.createCell(0).setCellValue("Correo:");
            companyRow5.createCell(1).setCellValue("empresa@correo.com");
            companyRow5.createCell(2).setCellValue("ES3244");
            
            rowIndex++;

            Row billHeaderRow = sheet.createRow(rowIndex++);
            billHeaderRow.createCell(0).setCellValue("DATOS DE LA FACTURA");
            billHeaderRow.createCell(1).setCellValue("");          
                     
            Row clientBill1 = sheet.createRow(rowIndex++);
            clientBill1.createCell(0).setCellValue("Fecha de Factura:");
            clientBill1.createCell(1).setCellValue(DATE);

            Row clientBill2 = sheet.createRow(rowIndex++);
            clientBill2.createCell(0).setCellValue("Número Factura:");
            clientBill2.createCell(1).setCellValue(NUMBER_FACTURA);

            Row clientBill3 = sheet.createRow(rowIndex++);
            clientBill3.createCell(0).setCellValue("Número Parte Material:");
            clientBill3.createCell(1).setCellValue(NUMBER_MATERIAL);

            Row clientBill4 = sheet.createRow(rowIndex++);
            clientBill4.createCell(0).setCellValue("Total Material:");
            clientBill4.createCell(1).setCellValue(TOTAL_MATERIAL);

            Row clientBill5 = sheet.createRow(rowIndex++);
            clientBill5.createCell(0).setCellValue("Precio Hora:");
            clientBill5.createCell(1).setCellValue(HOUR);
            
            Row clientBill6 = sheet.createRow(rowIndex++);
            clientBill6.createCell(0).setCellValue("Total Factura:");
            clientBill6.createCell(1).setCellValue(TOTAL);
            

            rowIndex++;
     
            Row materialheaderRow = sheet.createRow(rowIndex++);
            for (int i = 0; i < materialTable.getColumnCount(); i++) {
                materialheaderRow.createCell(i).setCellValue(materialTable.getColumnName(i));
            }

            for (int i = 0; i < tableModel.getRowCount(); i++) {
                Row row = sheet.createRow(rowIndex++);
                for (int j = 0; j < tableModel.getColumnCount(); j++) {
                    row.createCell(j).setCellValue(tableModel.getValueAt(i, j).toString());
                }
            }
             
            for (int i = 0; i < sheet.getRow(0).getPhysicalNumberOfCells(); i++) {
                sheet.autoSizeColumn(i);
            }
            
                        
            String filename="Factura "+NUMBER_FACTURA+" "+NAME+" en "+DATE+".xlsx";
            FileOutputStream fileOut = new FileOutputStream(filename);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
            
            File excelFile = new File(filename);            
            if (excelFile.exists()) {
                Desktop.getDesktop().open(excelFile);  
            }
            

            JOptionPane.showMessageDialog(null, "Archivo Excel generado con éxito!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(ae.getSource() == enviar){
        String toEmail = EMAIL;  // Dirección de destino
        String subject = "Factura adjunta";
        String body = "Adjunto encontrarás la factura en formato Excel.";
        String attachmentPath = "Factura "+NUMBER_FACTURA+" "+NAME+" en "+DATE+".xlsx";
        EmailSender.sendEmailWithAttachment(toEmail, subject, body, attachmentPath);
        }
        if(ae.getSource() == guardarButton){
            
        
        }
        
        
    }
         
         
     }
    
    public static void main(String[]args){
        new bill_standard_view("","","","","","","","","","");
    }
}
