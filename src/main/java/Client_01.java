import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Client_01 extends JFrame {
    private JTextField numProc;
    private JTextField maxCPU;
    private JTextField maxResource;
    private JButton confirmButton;
    private JButton exitButton;
    private JPanel mainPanel;
    private JComboBox schedules;

    public Client_01()
    {
        this.setSize(500,500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        configActionListener();
        this.setVisible(true);
    }
    void configActionListener()
    {
        this.confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int numProcess = Integer.parseInt(numProc.getText());
                int maxNumCPU = Integer.parseInt(maxCPU.getText());
                int maxNumResource = Integer.parseInt(maxResource.getText());
                if((maxNumCPU == maxNumResource) || ((maxNumCPU - 1) == maxNumResource))
                {
                    new GetDataDialog(maxNumCPU + maxNumResource + 2, numProcess);
                }
                else
                {
                    System.exit(1);
                }
            }
        });

        this.exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        });
    }

    public static void main(String[] args)
    {
        new Client_01();
    }
}
