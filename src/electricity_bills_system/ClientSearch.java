
package electricity_bills_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.border.Border;
import javax.swing.event.*;
import javax.swing.table.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ClientSearch extends JFrame implements ActionListener{
    JTextField cliente;
    JComboBox<String> nombre_combo;
    JPopupMenu nombre_popup;
    Choice ID_choice,proyecto_tipo,fecha_choice_mes,fecha_choice_year;
    String selectedID,NIF,ID_USER,date;
    JButton buscar, imprimir,salir;
    JTable tabla_cliente;
    DefaultTableModel tableModel;
    JFormattedTextField dateField;
    SimpleDateFormat dateFormat;
    String[] meses = {"Todos Meses","Enero","Febrero","Marzo","Aril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
    ArrayList<String> NAMEa = new ArrayList<>();
    ArrayList<String> ADDRESSa = new ArrayList<>();
    ArrayList<String> CITYa = new ArrayList<>();
    ArrayList<String> POSTALa = new ArrayList<>();
    ArrayList<String> EMAILa = new ArrayList<>();
    ArrayList<String> PHONEa = new ArrayList<>();
    ArrayList<String> PROJECTa = new ArrayList<>();

    ClientSearch(String NIF,String ID_USER){
    super("Clientes");
    setLayout(new BorderLayout());
    this.NIF=NIF;
    this.ID_USER=ID_USER;
    
     
    JPanel inputPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.fill = GridBagConstraints.HORIZONTAL;
    
    cliente = new JTextField("Todos");  
    ID_choice = new Choice();
    ID_choice.setBackground(new java.awt.Color(70, 73, 75));
    ID_choice.setForeground(new java.awt.Color(190, 190, 190));
        ID_choice.add("Cualquiera");
        ID_choice.addItemListener(new ItemListener() {
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {                
                selectedID = (String) e.getItem();
            }
        }
    });
    proyecto_tipo = new Choice();
    String[] tipos={"Todos","defecto","mantenimiento electrico","proyecto solar"};
    proyecto_tipo.setBackground(new java.awt.Color(70, 73, 75));
    proyecto_tipo.setForeground(new java.awt.Color(190, 190, 190));
    for(int i=0;i<tipos.length;i++){
        proyecto_tipo.add(tipos[i]);
    }    
     
    JPanel panelfecha = new JPanel(new GridLayout(1, 2)); 
    panelfecha.setOpaque(false); 
    
    fecha_choice_mes = new Choice();
    for(int i=0;i<meses.length;i++){
    fecha_choice_mes.add(meses[i]);}
    fecha_choice_mes.setBackground(new java.awt.Color(70, 73, 75));
    fecha_choice_mes.setForeground(new java.awt.Color(190, 190, 190));


    fecha_choice_year = new Choice();
    fecha_choice_year.add("Todos Años");
    fecha_choice_year.add("2025");
    fecha_choice_year.add("2024");
    fecha_choice_year.add("2023");
    fecha_choice_year.add("2022");
    fecha_choice_year.setBackground(new java.awt.Color(70, 73, 75));
    fecha_choice_year.setForeground(new java.awt.Color(190, 190, 190));

    panelfecha.add(fecha_choice_mes); 
    panelfecha.add(fecha_choice_year);
    
    addField(inputPanel, gbc, "Nombre del Cliente:", cliente, 0);
    addField(inputPanel, gbc, "ID del cliente:", ID_choice, 1);
    addField(inputPanel, gbc, "Tipo de proyecto:", proyecto_tipo, 2);
    addField(inputPanel, gbc, "Fecha de alta:", panelfecha, 3);
    
    nombre_popup = new JPopupMenu();
    nombre_popup.setFocusable(false);
    
    cliente.getDocument().addDocumentListener(new DocumentListener() {
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
                String text = cliente.getText().toLowerCase().trim();
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
                        nombre_popup.setPreferredSize(new Dimension(205, nombre_popup.getComponentCount() * 30));
                        nombre_popup.show(cliente, 0, cliente.getHeight());
                    } else {
                        nombre_popup.setVisible(false);
                    }
                }
            }
        });


    buscar = new JButton("Buscar Cliente");
    buscar.addActionListener(this);
    buscar.setBounds(50, 10, 50, 10);
    buscar.setPreferredSize(new Dimension(130, 25)); 
    gbc.fill = GridBagConstraints.NONE; // No expandir automáticamente
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.gridx = 0;
    gbc.gridy = 4;
    gbc.gridwidth = 2;       
    inputPanel.add(buscar, gbc);
        
    imprimir = new JButton("Exportar");
    imprimir.addActionListener(this);
    imprimir.setPreferredSize(new Dimension(130, 25)); 
    gbc.gridy = 5;
    inputPanel.add(imprimir, gbc);
    
    salir = new JButton("Salir");
    salir.addActionListener(this);
    salir.setPreferredSize(new Dimension(130, 25)); 
    salir.setBounds(50, 10, 50, 10);;
    gbc.gridy = 6;
    inputPanel.add(salir, gbc);
    
    String[] columnNames = {"Nombre" , "Direccion", "Ciudad", "Postal","Email","Telefono","Proyecto"};
    tableModel = new DefaultTableModel(columnNames, 0);
    tabla_cliente = new JTable(tableModel);
    JScrollPane scrollPane = new JScrollPane(tabla_cliente);
    scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));
           
    add(inputPanel, BorderLayout.NORTH);
    add(scrollPane, BorderLayout.CENTER);
    setSize(1200, 800);
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
    ID_choice.removeAll();
    try {
        Connect c = new Connect();
        ResultSet rs = c.s.executeQuery("SELECT ID FROM client WHERE NAME='" + selectedName + "' AND NIF='"+NIF+"'");
        while (rs.next()) {
            ID_choice.add(rs.getString("ID"));
        }
        rs.close();
        c.s.close();
    } catch (Exception ex) {
        ex.printStackTrace();
    }
  
     if (ID_choice.getItemCount() == 1) {
        ID_choice.select(0); 
        selectedID = ID_choice.getSelectedItem();
    } else if (ID_choice.getItemCount() > 1) {
        ID_choice.select(0);
        selectedID = ID_choice.getSelectedItem();
    }
   
}
    
    public void actionPerformed(ActionEvent ae) {
    String ID_CLIENT=ID_choice.getSelectedItem();
    String PROJECT_TYPE=proyecto_tipo.getSelectedItem();
    String MES=fecha_choice_mes.getSelectedItem();
    String YEAR=fecha_choice_year.getSelectedItem();
    int numero_mes=0;
      if(ae.getSource() == buscar){
          int longitud=NAMEa.size();
          if(longitud>0){
              for(int i=longitud-1;i>=0;i--)
            tableModel.removeRow(i);
          }
          NAMEa.clear();
          ADDRESSa.clear();
          CITYa.clear();
          POSTALa.clear();
          EMAILa.clear();
          PHONEa.clear();
          PROJECTa.clear();
       boolean datos=true;
       try{
        
        Connect c = new Connect();
        String filtro1="",filtro2="",filtro3="",filtro4="";
        if(!ID_CLIENT.equals("Cualquiera")){
            filtro1=" AND client.ID='"+ID_CLIENT+"'";
        }
        if(!PROJECT_TYPE.equals("Todos")){
            filtro2=" AND PROJECT='"+PROJECT_TYPE+"'";
        }
        if(!MES.equals("Todos Meses")){
            for(int i=1;i<meses.length;i++){                
                if(meses[i].equals(MES)){
                    numero_mes=i;
                break;
                }                
            }
            String numero_formateado= String.format("%02d", numero_mes);
            filtro3=" AND DATE LIKE '%-"+numero_formateado+"-%'";
        }
        if(!YEAR.equals("Todos Años")){
            filtro4=" AND DATE LIKE '%-"+YEAR+"'";
        }
        
        String query="SELECT NAME,ADDRESS,POSTAL,CITY,EMAIL,PHONE,client.ID,PROJECT FROM ebs.meter_info JOIN ebs.client ON meter_info.ID=client.ID where NIF='"+NIF+"'"+filtro1+filtro2+filtro3+filtro4;
        ResultSet rs = c.s.executeQuery(query);
        
        if (!rs.next()) {
            datos=false;
            NAMEa.add("sin registros");
            tableModel.addRow(new Object[]{"Sin registros", "", "", "", "", "",""});
        }else
        do {
            String name = rs.getString("NAME");
            String direccion = rs.getString("ADDRESS");
            String postal = rs.getString("POSTAL");            
            String ciudad = rs.getString("CITY");
            String email = rs.getString("EMAIL");
            String telefono = rs.getString("PHONE");
            String Proy = rs.getString("PROJECT");

           NAMEa.add(name);
           ADDRESSa.add(direccion);
           CITYa.add(postal);
           POSTALa.add(ciudad);
           EMAILa.add(email);
           PHONEa.add(telefono);
           PROJECTa.add(Proy);
                 
            } while (rs.next());       
        rs.close();        
        c.s.close();
        
        } catch (Exception ea) {
            ea.printStackTrace();
        }
        if(datos){
            for(int i=0;i<NAMEa.size();i++){
            String nombre2 ="  "+ NAMEa.get(i);
            String direccion2 ="  "+ ADDRESSa.get(i);
            String ciudad2 ="  "+ CITYa.get(i);            
            String postal2 ="  "+ POSTALa.get(i);
            String email2 ="  "+ EMAILa.get(i);
            String telefono2 ="  "+ PHONEa.get(i);
            String proyect2 ="  "+ PROJECTa.get(i);
            tableModel.addRow(new Object[]{nombre2, direccion2, ciudad2, postal2, email2, telefono2,proyect2});
           }
        }
       
      } else if(ae.getSource() == imprimir){
        try{          
         Workbook workbook = new XSSFWorkbook();
         Sheet sheet = workbook.createSheet("Clientes; "+NAMEa.size()+"de ");
         int rowIndex = 0;
         
         Row clientesHeadarRow = sheet.createRow(rowIndex++);
            for (int i = 0; i < tabla_cliente.getColumnCount(); i++) {
                clientesHeadarRow.createCell(i).setCellValue(tabla_cliente.getColumnName(i));
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
            dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            date = dateFormat.format(new Date());
            String filename="Clientes tabla; del "+date+".xlsx";
            FileOutputStream fileOut = new FileOutputStream(filename);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
            
            File excelFile = new File(filename);            
            if (excelFile.exists()) {
                Desktop.getDesktop().open(excelFile);  
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
      }else{
          setVisible(false);
      }
        
    }


    public static void main(String[] args){
        new ClientSearch("","");
    }
}
    
