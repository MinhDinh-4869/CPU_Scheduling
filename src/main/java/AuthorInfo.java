/* This code is made by
 * Dinh Cong Minh
 * 16047
 * CSE2019
 */
import javax.swing.*;
import java.awt.event.*;

public class AuthorInfo extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;

    public AuthorInfo() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }
}
