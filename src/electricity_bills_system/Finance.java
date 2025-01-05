    package electricity_bills_system;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;   
import java.util.Date;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
    

public class Finance extends JFrame implements ActionListener {

    JLabel benefitsLabel, expensesLabel, processedInvoicesLabel, pendingInvoicesLabel;
    JFrame financeFrame;
    RoundedButton updateButton;
    String NIF,ID_USER;
    String beneficios,horas,factura_pendiente,factura_pagada,gastos;
    SimpleDateFormat dateFormat;
    double IVA;
     
    Finance(String NIF, String ID_USER) {
        dateFormat = new SimpleDateFormat("MM-yyyy");
        Date fechaActual = new Date();
        String fecha = dateFormat.format(fechaActual);
        setIconImage(new ImageIcon(getClass().getClassLoader().getResource("images/icono_app.png")).getImage());
        
        try{
            Connect c= new Connect();
            String query="SELECT COALESCE(SUM(HOUR),0) AS HORAS,COALESCE(ROUND(SUM(TOTAL_BILL),2),0) AS BENEFICIOS FROM bill_standard WHERE NIF='"+NIF+"' AND DATE LIKE '%-"+fecha+"' AND STATUS='pagado'";
              ResultSet rs1 = c.s.executeQuery(query);             
              while(rs1.next()){
                  beneficios = rs1.getString("BENEFICIOS");
                  horas = rs1.getString("HORAS");    
                  horas=horas.substring(0, horas.indexOf("."));
              }
              String query4="SELECT COALESCE(COUNT(STATUS),0) AS PENDIENTE FROM bill_standard WHERE NIF='"+NIF+"' AND DATE LIKE '%-"+fecha+"' AND (STATUS LIKE 'sin enviar' OR STATUS LIKE 'pendiente%')";
              ResultSet rs4 = c.s.executeQuery(query4);
              while(rs4.next()){
                  factura_pendiente= rs4.getString("PENDIENTE");
              }
              
              String query2="SELECT COALESCE(COUNT(STATUS),0) AS PAGADA FROM bill_standard WHERE NIF='"+NIF+"' AND DATE LIKE '%-"+fecha+"' AND STATUS LIKE 'pagado'";
              ResultSet rs2 = c.s.executeQuery(query2);
              while(rs2.next()){                  
                  factura_pagada= rs2.getString("PAGADA");
              }
              String query3="SELECT COALESCE(SUM(TOTAL_PRICE),0) AS GASTOS FROM material_bill WHERE NIF='"+NIF+"' AND DATE LIKE '%-"+fecha+"'";
              ResultSet rs3 = c.s.executeQuery(query3);
              while(rs3.next()){                  
                  gastos= rs3.getString("GASTOS");
              }
                String query5="SELECT IVA FROM setup_bill WHERE NIF='"+NIF+"'";
                 ResultSet rs5 = c.s.executeQuery(query5);
                 while(rs5.next()){                  
                   String  ivas= rs5.getString("IVA");
                   IVA=Integer.parseInt(ivas);
                 }                 
              
        }catch (Exception e){
            e.printStackTrace();
        }
        double beneficios_int2=Double.parseDouble(beneficios);
        beneficios_int2=beneficios_int2-(beneficios_int2*(IVA/100));
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        symbols.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("#.00", symbols);
        String beneficiosFormateados = df.format(beneficios_int2);
        double beneficios_int=Double.parseDouble(beneficiosFormateados);
        double gastos_int=Double.parseDouble(gastos);
        
        this.NIF=NIF;
        this.ID_USER=ID_USER;
        financeFrame = new JFrame("Finanzas - Resumen del Mes");
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        
        financeFrame.setSize((screenWidth / 2)-50, screenHeight-200);
        financeFrame.setLocation(50, 80);
     
        Font fuente=new Font("Roboto", Font.PLAIN, 20);
        Font fuente2=new Font("Roboto", Font.PLAIN, 15);
        Font fuente3=new Font("Roboto", Font.PLAIN, 12);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        financeFrame.add(mainPanel);
        JPanel dataPanel = new JPanel(new GridLayout(7, 4, 10, 4));
        
        
        
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Finanzas del Mes");
        titledBorder.setTitleJustification(TitledBorder.CENTER);
        titledBorder.setTitleFont(new Font("Roboto", Font.BOLD, 20));
        titledBorder.setBorder(BorderFactory.createEmptyBorder()); 
        dataPanel.setBorder(titledBorder);
        
        
        dataPanel.add(new JLabel("", SwingConstants.RIGHT));
        benefitsLabel = new JLabel("", SwingConstants.LEFT);
        dataPanel.add(benefitsLabel);
        
        dataPanel.add(new JLabel("Beneficios Totales:", SwingConstants.RIGHT));
        benefitsLabel = new JLabel(beneficios_int+" €", SwingConstants.LEFT);
        dataPanel.add(benefitsLabel);

        dataPanel.add(new JLabel("Gastos Totales:", SwingConstants.RIGHT));
        expensesLabel = new JLabel(gastos+" €", SwingConstants.LEFT);
        dataPanel.add(expensesLabel);
        
        dataPanel.add(new JLabel("Horas realizadas:", SwingConstants.RIGHT));
        pendingInvoicesLabel = new JLabel(horas+" h", SwingConstants.LEFT);
        dataPanel.add(pendingInvoicesLabel);

        dataPanel.add(new JLabel("Facturas Procesadas:", SwingConstants.RIGHT));
        processedInvoicesLabel = new JLabel(factura_pagada, SwingConstants.LEFT);
        dataPanel.add(processedInvoicesLabel);

        dataPanel.add(new JLabel("Facturas Pendientes:", SwingConstants.RIGHT));
        pendingInvoicesLabel = new JLabel(factura_pendiente, SwingConstants.LEFT);
        dataPanel.add(pendingInvoicesLabel);
        
        
        dataPanel.add(new JLabel("", SwingConstants.RIGHT));
        benefitsLabel = new JLabel("", SwingConstants.LEFT);
        dataPanel.add(benefitsLabel);
    
        int horas_int=Integer.parseInt(horas);
        int facturas_pendientes_int=Integer.parseInt(factura_pendiente);
        int facturas_pagadas_int=Integer.parseInt(factura_pagada);
         
        mainPanel.add(dataPanel, BorderLayout.NORTH);

        DefaultPieDataset pieDataset = new DefaultPieDataset();
        pieDataset.setValue("Facturas Finalizadas", facturas_pagadas_int);
        pieDataset.setValue("Facturas Pendientes", facturas_pendientes_int);

        JFreeChart pieChart = ChartFactory.createPieChart(
        "Distribución Financiera del Mes",
        pieDataset,
        false,
        false,
        false
        );
        pieChart.getTitle().setFont(new Font("Roboto", Font.BOLD, 18));
        pieChart.getTitle().setPaint(new Color(200, 200, 200));
        pieChart.setBackgroundPaint(new Color(0, 0, 0, 0));
        
        PiePlot piePlot = (PiePlot) pieChart.getPlot();
        piePlot.setBackgroundPaint(new Color(0, 0, 0, 0));
        piePlot.setOutlinePaint(null);

        piePlot.setSectionPaint("Facturas Finalizadas", new Color(255, 87, 34,98));
        piePlot.setSectionPaint("Facturas Pendientes", new Color(156, 39, 176,98)); 

        piePlot.setLabelFont(fuente3);
        piePlot.setLabelPaint(new Color(200, 200, 200));
        piePlot.setLabelBackgroundPaint(new Color(70, 73, 75,0));
        piePlot.setShadowPaint(new Color(70, 73, 75)); 
        piePlot.setLabelOutlinePaint(null);
        
        
        ChartPanel pieChartPanel = new ChartPanel(pieChart) {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                setOpaque(false); 
            }
        };
        pieChartPanel.setPreferredSize(new Dimension(500, 250));
        mainPanel.add(pieChartPanel, BorderLayout.WEST);

       
        DefaultCategoryDataset barDataset = new DefaultCategoryDataset();
        barDataset.addValue(beneficios_int, "Beneficios", "Beneficios                      Gastos");
        barDataset.addValue(gastos_int, "Gastos", "Beneficios                      Gastos");

        JFreeChart barChart = ChartFactory.createBarChart(
                "Comparación: Beneficios vs Gastos",
                "Categorías",
                "Cantidad (€)",
                barDataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );
        

        CategoryPlot barPlot = (CategoryPlot) barChart.getPlot();
        barPlot.setBackgroundPaint(new Color(0, 0, 0, 0)); 
        barPlot.setRangeGridlinePaint(new Color(0, 0, 0, 0));
        barPlot.setOutlinePaint(null);

        barPlot.getDomainAxis().setTickLabelPaint(Color.WHITE);
        barPlot.getRangeAxis().setTickLabelPaint(Color.WHITE);    
        barChart.getTitle().setPaint(Color.WHITE);  
        
        BarRenderer renderer = (BarRenderer) barPlot.getRenderer();
        renderer.setSeriesPaint(0, new Color(156, 39, 176,95)); 
        renderer.setSeriesPaint(1, new Color(3, 169, 244,95)); 

        barChart.getTitle().setFont(new Font("Roboto", Font.BOLD, 18));
        barChart.getTitle().setPaint(new Color(200, 200, 200));
        barChart.setBackgroundPaint(new Color(0, 0, 0, 0)); 

        ChartPanel barChartPanel = new ChartPanel(barChart);
        barChartPanel.setPreferredSize(new Dimension(420, 250));
        barChartPanel.setOpaque(false); 
        mainPanel.add(barChartPanel, BorderLayout.EAST);
       
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        
        updateButton = new RoundedButton("Actualizar Datos");
        updateButton.setFont(new Font("Roboto", Font.BOLD, 16));
        updateButton.addActionListener(this);
        bottomPanel.add(updateButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        financeFrame.setVisible(true);
        

    }


    public void actionPerformed(ActionEvent ae) {
     
        if(ae.getSource() == updateButton){
        financeFrame.setVisible(false);
        new Finance(NIF,ID_USER);
    }
      
    }

    public static void main(String[] args) {
        new Finance("", "");
    }
}
