/* This code is made by
 * Dinh Cong Minh
 * 16047
 * CSE2019
 */
import javax.swing.*;

public class Client_01 extends JFrame {
    private JTextField numProc;
    private JTextField maxCPU;
    private JTextField maxResource;
    private JButton confirmButton;
    private JButton exitButton;
    private JPanel mainPanel;
    private JTextField resourceNumField;

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
        this.confirmButton.addActionListener(e -> {
            int numProcess = Integer.parseInt(numProc.getText());
            int maxNumCPU = Integer.parseInt(maxCPU.getText());
            int maxNumResource = Integer.parseInt(maxResource.getText());
            int resource_num = Integer.parseInt(resourceNumField.getText());
            if((maxNumCPU == maxNumResource) || ((maxNumCPU - 1) == maxNumResource))
            {
                new GetDataDialog(maxNumCPU + maxNumResource + 2, numProcess, resource_num);
            }
            else
            {
                System.exit(1);
            }
        });

        this.exitButton.addActionListener(e -> System.exit(1));
    }

    public static void main(String[] args)
    {
        new AuthorInfo();
        new Client_01();
    }
}
