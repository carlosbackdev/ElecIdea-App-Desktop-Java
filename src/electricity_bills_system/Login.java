
package electricity_bills_system;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;
import java.awt.Color;

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

public class Login extends JFrame {
    
    Login(){
    super("Inicio Sesion Usuario");
    setContentPane(new BackgroundPanel("images/login.jpg"));    
    
    setLayout(null);
    
    JLabel label_usuario = new JLabel ("Usuario");
    label_usuario.setBounds(450,200,500,100);
    label_usuario.setForeground(Color.white);
    label_usuario.setFont(new Font("Roboto", Font.PLAIN, 24)); 
    add(label_usuario);
    
    JTextField cajon_usuario = new JTextField();
    cajon_usuario.setBounds(550,230,200,40);
    cajon_usuario.setFont(new Font("Roboto", Font.PLAIN, 22));
    cajon_usuario.setHorizontalAlignment(JTextField.CENTER);
    add(cajon_usuario);

    
    JLabel label_contraseña = new JLabel ("Contraseña");
    label_contraseña.setBounds(408,300,500,100);
    label_contraseña.setForeground(Color.white);
    label_contraseña.setFont(new Font("Roboto", Font.PLAIN, 24)); 
    add(label_contraseña);
    
    JTextField cajon_contraseña = new JTextField();
    cajon_contraseña.setBounds(550,330,200,40);
    cajon_contraseña.setFont(new Font("Roboto", Font.PLAIN, 22));
    cajon_contraseña.setHorizontalAlignment(JTextField.CENTER);
    add(cajon_contraseña);
    
    
    JLabel tipo_usuario = new JLabel ("Conectarse como");
    tipo_usuario.setBounds(345,420,200,50);
    tipo_usuario.setForeground(Color.white);
    tipo_usuario.setFont(new Font("Roboto", Font.PLAIN, 24)); 
    add(tipo_usuario);
    
    Choice tipousu = new Choice ();
    tipousu.add("cliente");
    tipousu.add("administrador");
    tipousu.setBounds(550,434,200,50);
    tipousu.setFont(new Font("Roboto", Font.PLAIN, 16));

    add(tipousu);
    
    RoundedButton Login =new RoundedButton("Conectarse");
    Login.setBackground(new Color(222, 239, 255 ));
    Login.setForeground(Color.black);
    Login.setBounds(550,530,200,50);
    Login.setFont(new Font("Roboto", Font.PLAIN, 18)); 
    add(Login);
    
    RoundedButton Signup =new RoundedButton("Registrarse");
    Signup.setBounds(1020,40,100,35);
    Signup.setBackground(new Color(222, 239, 255 ));
    Signup.setForeground(Color.black);
    Signup.setFont(new Font("Roboto", Font.PLAIN, 13)); 
    add(Signup);

    
    
    setSize(1200, 800);
    setLocationRelativeTo(null);
    setVisible(true);
    }
    
    
    
    public static void main(String[] args){
    new Login ();
        
    }
}
