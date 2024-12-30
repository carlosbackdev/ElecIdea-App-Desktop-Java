package electricity_bills_system;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;

public class GraphClient extends JFrame implements ActionListener {

    JLabel benefitsLabel, expensesLabel, processedInvoicesLabel, pendingInvoicesLabel;
    JFrame customerGraphFrame;
    RoundedButton updateButton;
    String NIF, ID_USER;
    String clientes_totales, clientes_mes, clientes_mes2,clientes_mes3;
    
    

    GraphClient(String NIF, String ID_USER) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-yyyy");
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("MM");
        Date fechaActual = new Date();
        String fecha = dateFormat.format(fechaActual);
        String mes = dateFormat2.format(fechaActual);
        int mesint = Integer.parseInt(mes);
        int mes2,mes3;
        int mes1_resultado=0,mes2_resultado=0,mes3_resultado=0;
        if(mesint==1){
            mes2=12;
            mes3=11;
        }else{
            mes2=mesint-1;
            mes3=mesint-2;
        }

        try {
            Connect c = new Connect();
            // Consulta para obtener los beneficios, horas, facturas, etc.
            String query = "SELECT COALESCE(COUNT(*),0) AS CLIENTES FROM client";
            ResultSet rs1 = c.s.executeQuery(query);
            while (rs1.next()) {
                clientes_totales = rs1.getString("CLIENTES");
            }         
            String query2 = "SELECT COALESCE(COUNT(*),0) AS CLIENTES FROM client WHERE DATE LIKE '%-" + mesint + "-%'";
            ResultSet rs2 = c.s.executeQuery(query2);
            while (rs2.next()) {
                String m = rs2.getString("CLIENTES");
                mes1_resultado=Integer.parseInt(m);
            }
            String query3 = "SELECT COALESCE(COUNT(*),0) AS CLIENTES FROM client WHERE DATE LIKE '%-" + mes2 + "-%'";
            ResultSet rs3 = c.s.executeQuery(query3);
            while (rs3.next()) {
                String m = rs3.getString("CLIENTES");
                mes2_resultado=Integer.parseInt(m);
            }
            String query4 = "SELECT COALESCE(COUNT(*),0) AS CLIENTES FROM client WHERE DATE LIKE '%-" + mes3 + "-%'";
            ResultSet rs4 = c.s.executeQuery(query4);
            while (rs4.next()) {
                String m = rs4.getString("CLIENTES");
                mes3_resultado=Integer.parseInt(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        int clientes_totales_int = Integer.parseInt(clientes_totales);
        double porcentaje_clientes=(mes1_resultado*100)/clientes_totales_int;

        this.NIF = NIF;
        this.ID_USER = ID_USER;
        customerGraphFrame = new JFrame("Gráfico de Clientes - Resumen del Mes");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        customerGraphFrame.setSize((screenWidth / 2) - 50, screenHeight - 200);
        customerGraphFrame.setLocation(screenWidth / 2 + 25, 80);

        Font fuente = new Font("Roboto", Font.PLAIN, 20);
        Font fuente2 = new Font("Roboto", Font.PLAIN, 15);
        Font fuente3 = new Font("Roboto", Font.PLAIN, 12);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        customerGraphFrame.add(mainPanel);
        JPanel dataPanel = new JPanel(new GridLayout(7, 4, 10, 4));

        TitledBorder titledBorder = BorderFactory.createTitledBorder("Estadísticas Clientes");
        titledBorder.setTitleJustification(TitledBorder.CENTER);
        titledBorder.setTitleFont(new Font("Roboto", Font.BOLD, 20));
        titledBorder.setBorder(BorderFactory.createEmptyBorder());
        dataPanel.setBorder(titledBorder);

        // Agregar etiquetas de datos
        dataPanel.add(new JLabel("", SwingConstants.RIGHT));
        benefitsLabel = new JLabel("", SwingConstants.LEFT);
        dataPanel.add(benefitsLabel);

        dataPanel.add(new JLabel("Clientes Totales:", SwingConstants.RIGHT));
        benefitsLabel = new JLabel(clientes_totales, SwingConstants.LEFT);
        dataPanel.add(benefitsLabel);

        dataPanel.add(new JLabel("Clientes Ultimo Mes:", SwingConstants.RIGHT));
        expensesLabel = new JLabel(mes1_resultado+"", SwingConstants.LEFT);
        dataPanel.add(expensesLabel);

        dataPanel.add(new JLabel("Porcentaje Crecimiento clientes", SwingConstants.RIGHT));
        pendingInvoicesLabel = new JLabel(porcentaje_clientes+"%", SwingConstants.LEFT);
        dataPanel.add(pendingInvoicesLabel);


        dataPanel.add(new JLabel("", SwingConstants.RIGHT));
        benefitsLabel = new JLabel("", SwingConstants.LEFT);
        dataPanel.add(benefitsLabel);

        
        mainPanel.add(dataPanel, BorderLayout.NORTH);

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        dataset.addValue(mes3_resultado, "Clientes", "Octubre");
        dataset.addValue(mes2_resultado, "Clientes", "Noviembre");
        dataset.addValue(mes1_resultado, "Clientes", "Diciembre");



        JFreeChart lineChart = ChartFactory.createLineChart(
                "Evolución de Clientes en los ultimos 3 Meses",
                "Mes",
                "Número de Clientes Nuevos",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false
        );

        CategoryPlot plot = (CategoryPlot) lineChart.getPlot();
        plot.setBackgroundPaint(new Color(0, 0, 0, 0));
        plot.setRangeGridlinePaint(new Color(0, 0, 0, 0));

        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(33, 150, 243));

        lineChart.getTitle().setFont(new Font("Roboto", Font.BOLD, 18));
        lineChart.getTitle().setPaint(new Color(200, 200, 200));
        lineChart.setBackgroundPaint(new Color(0, 0, 0, 0));
        
        plot.getDomainAxis().setLabelPaint(Color.WHITE); 
        plot.getRangeAxis().setLabelPaint(Color.WHITE);   
        plot.getDomainAxis().setTickLabelPaint(Color.WHITE); 
        plot.getRangeAxis().setTickLabelPaint(Color.WHITE); 

        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new Dimension(800, 400));
        chartPanel.setOpaque(false);
        mainPanel.add(chartPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        updateButton = new RoundedButton("Actualizar Datos");
        updateButton.setFont(new Font("Roboto", Font.BOLD, 16));
        updateButton.addActionListener(this);
        bottomPanel.add(updateButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        customerGraphFrame.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == updateButton) {
            customerGraphFrame.setVisible(false);
            new GraphClient(NIF, ID_USER);
        }
    }

    public static void main(String[] args) {
        new GraphClient("", "");
    }
}
