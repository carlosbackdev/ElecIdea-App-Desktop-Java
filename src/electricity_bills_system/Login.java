
package electricity_bills_system;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;
import java.awt.Color;
import java.awt.event.*;

class RoundedButton extends JButton {
    public RoundedButton(String label) {
        super(label);
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Cambia el cursor a puntero
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
        super.paintComponent(g2);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getForeground());
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
        g2.dispose();
    }

    @Override
    public void setContentAreaFilled(boolean b) {
        // No hacer nada
    }
}


class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String fileName) {
        backgroundImage = new ImageIcon(ClassLoader.getSystemResource(fileName)).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}

public class Login extends JFrame implements ActionListener{
    RoundedButton login,signup;
    Login(){
    super("Inicio Sesion Usuario");
        setContentPane(new BackgroundPanel("images/login.jpg"));    
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false); // Hace que el panel sea transparente
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(45, 15, 25, 15);
        
        JLabel head = new JLabel("SESIÓN");
        head.setForeground(Color.WHITE);
        head.setFont(new Font("Roboto", Font.PLAIN, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(head, gbc);
        
        JLabel label_usuario = new JLabel("Usuario");
        label_usuario.setForeground(Color.WHITE);
        label_usuario.setFont(new Font("Roboto", Font.PLAIN, 24)); 
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        panel.add(label_usuario, gbc);

        JTextField cajon_usuario = new JTextField();
        cajon_usuario.setFont(new Font("Roboto", Font.PLAIN, 14));
        cajon_usuario.setHorizontalAlignment(JTextField.CENTER);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.weightx = 0;
        panel.add(cajon_usuario, gbc);

        JLabel label_contraseña = new JLabel("Contraseña");
        label_contraseña.setForeground(Color.WHITE);
        label_contraseña.setFont(new Font("Roboto", Font.PLAIN, 24)); 
        gbc.gridy = 2;
        gbc.gridx = 0;
        panel.add(label_contraseña, gbc);

        JTextField cajon_contraseña = new JTextField();
        cajon_contraseña.setFont(new Font("Roboto", Font.PLAIN, 14));
        cajon_contraseña.setHorizontalAlignment(JTextField.CENTER);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.weightx = 0; 
        panel.add(cajon_contraseña, gbc);

        JLabel tipo_usuario = new JLabel("Conectarse como");
        tipo_usuario.setForeground(Color.WHITE);
        tipo_usuario.setFont(new Font("Roboto", Font.PLAIN, 24)); 
        gbc.gridy = 3;
        gbc.gridx = 0;
        panel.add(tipo_usuario, gbc);

        Choice tipousu = new Choice();
        tipousu.add("cliente");
        tipousu.add("administrador");
        tipousu.setFont(new Font("Roboto", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.weightx = 0;
        panel.add(tipousu, gbc);

        login = new RoundedButton("Conectarse");
        login.setBackground(new Color(222, 239, 255));
        login.setForeground(Color.BLACK);
        login.setFont(new Font("Roboto", Font.PLAIN, 18)); 
        gbc.gridy = 4;
        gbc.gridx = 0;
        login.addActionListener(this);
        panel.add(login, gbc);

        signup = new RoundedButton("Registrarse");
        signup.setBackground(new Color(222, 239, 255));
        signup.setForeground(Color.BLACK);
        signup.setFont(new Font("Roboto", Font.PLAIN, 18)); 
        gbc.gridx = 1;
        signup.addActionListener(this);
        panel.add(signup, gbc);

        add(panel, BorderLayout.PAGE_START);

        setSize(1200, 800);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == login){
        
        }else if(ae.getSource() == signup){
            setVisible(false);
            new Signup();
        }
        
    }
    
    public static void main(String[] args){
    new Login ();
        
    }
}
