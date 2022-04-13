import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Client extends JFrame {
    private JPanel panel1;
    private JPanel panel2;
    private JTextField widthTF;
    private JTextField heightTF;
    private JButton confirmButton;
    private JButton declineButton;
    private JLabel widthLabel;
    private JLabel heightLabel;


    public Client()
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 400);

        this.initMenu();
        this.configActionListener();

        this.setVisible(true);
    }

    void initMenu()
    {
        panel1 = new JPanel();
        panel2 = new JPanel();

        widthTF = new JTextField(10);
        heightTF = new JTextField(10);
        confirmButton = new JButton("Confirm");
        declineButton = new JButton("Decline");
        widthLabel = new JLabel("Width");
        heightLabel = new JLabel("Height");

        panel1.setLayout(new GridLayout(2,2));
        panel1.add(widthLabel);
        panel1.add(widthTF);
        panel1.add(heightLabel);
        panel1.add(heightTF);
        panel2.add(confirmButton);
        panel2.add(declineButton);

        this.getContentPane().add(panel1, BorderLayout.CENTER);
        this.getContentPane().add(panel2, BorderLayout.AFTER_LAST_LINE);
    }

    public static void main(String[] args)
    {
        new Client();
    }

    void configActionListener()
    {
        this.confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int height = Integer.parseInt(heightTF.getText());
                int width = Integer.parseInt(widthTF.getText());

                System.out.println(height +"|" + width);
            }
        });
    }
}
