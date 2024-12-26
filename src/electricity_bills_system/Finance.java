package electricity_bills_system;

import java.awt.*;
import java.awt.event.*;
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

    Finance(String NIF, String ID_USER) {
        financeFrame = new JFrame("Finanzas - Resumen del Mes");
        financeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        financeFrame.setSize(900, 600);
        financeFrame.setLocation(100, 100);
        Font fuente=new Font("Roboto", Font.PLAIN, 20);
        Font fuente2=new Font("Roboto", Font.PLAIN, 15);
        Font fuente3=new Font("Roboto", Font.PLAIN, 12);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        financeFrame.add(mainPanel);
        JPanel dataPanel = new JPanel(new GridLayout(6, 4, 10, 4));
        
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Datos del Mes");
        titledBorder.setTitleJustification(TitledBorder.CENTER); // Centrar el título
        titledBorder.setTitleFont(new Font("Roboto", Font.BOLD, 20));
        titledBorder.setBorder(BorderFactory.createEmptyBorder()); 
        dataPanel.setBorder(titledBorder);
        
        
        dataPanel.add(new JLabel("", SwingConstants.RIGHT));
        benefitsLabel = new JLabel("", SwingConstants.LEFT);
        dataPanel.add(benefitsLabel);
        
        dataPanel.add(new JLabel("Beneficios Totales:", SwingConstants.RIGHT));
        benefitsLabel = new JLabel("1,200 €", SwingConstants.LEFT);
        dataPanel.add(benefitsLabel);

        dataPanel.add(new JLabel("Gastos Totales:", SwingConstants.RIGHT));
        expensesLabel = new JLabel("800 €", SwingConstants.LEFT);
        dataPanel.add(expensesLabel);

        dataPanel.add(new JLabel("Facturas Procesadas:", SwingConstants.RIGHT));
        processedInvoicesLabel = new JLabel("15", SwingConstants.LEFT);
        dataPanel.add(processedInvoicesLabel);

        dataPanel.add(new JLabel("Facturas Pendientes:", SwingConstants.RIGHT));
        pendingInvoicesLabel = new JLabel("3", SwingConstants.LEFT);
        dataPanel.add(pendingInvoicesLabel);
        dataPanel.add(new JLabel("", SwingConstants.RIGHT));
        benefitsLabel = new JLabel("", SwingConstants.LEFT);
        dataPanel.add(benefitsLabel);
        

        mainPanel.add(dataPanel, BorderLayout.NORTH);

        DefaultPieDataset pieDataset = new DefaultPieDataset();
        pieDataset.setValue("Beneficios", 120);
        pieDataset.setValue("Gastos", 800);
        pieDataset.setValue("Facturas Pendientes", 1000);

        JFreeChart pieChart = ChartFactory.createPieChart(
        "Distribución Financiera del Mes",
        pieDataset,
        false,
        false,
        false
        );
        pieChart.getTitle().setFont(new Font("Roboto", Font.BOLD, 18));
        pieChart.setBackgroundPaint(new Color(0, 0, 0, 0));
        
        PiePlot piePlot = (PiePlot) pieChart.getPlot();
        piePlot.setBackgroundPaint(new Color(0, 0, 0, 0));
        piePlot.setOutlinePaint(null);

        piePlot.setSectionPaint("Beneficios", new Color(255, 87, 34,98));
        piePlot.setSectionPaint("Gastos", new Color(156, 39, 176,98)); 
        piePlot.setSectionPaint("Facturas Pendientes", new Color(3, 169, 244,98));

        piePlot.setLabelFont(fuente2);
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
        barDataset.addValue(1200, "Finanzas", "Beneficios");
        barDataset.addValue(800, "Finanzas", "Gastos");

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

        BarRenderer renderer = (BarRenderer) barPlot.getRenderer();
        renderer.setSeriesPaint(0, new Color(156, 39, 176)); 
        renderer.setSeriesPaint(1, new Color(3, 169, 244)); 

        renderer.setDrawBarOutline(false);
        renderer.setShadowVisible(false); 

        barChart.getTitle().setFont(new Font("SansSerif", Font.BOLD, 18));
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
        // Simular actualización de datos
        benefitsLabel.setText("1,500 €");
        expensesLabel.setText("900 €");
        processedInvoicesLabel.setText("20");
        pendingInvoicesLabel.setText("2");
        JOptionPane.showMessageDialog(financeFrame, "Datos actualizados con éxito", "Actualizar", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        new Finance("", "");
    }
}
