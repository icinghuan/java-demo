package top.icinghuan.demo.javatest;

import com.google.common.collect.Sets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

/**
 * @author : xy
 * @date : 2018/10/24
 * Description :
 */
public class JavaAWT extends JFrame implements ActionListener {

    private static final Set<String> NumSet = Sets.newHashSet("1", "2", "3", "4", "5", "6", "7", "8", "9");

    private JTextArea[][] jTextAreas = new JTextArea[9][9];
    private JPanel[] jPanels = new JPanel[9];

    public JavaAWT() {
        for (int i = 0; i < 9; i++) {
            jPanels[i] = new JPanel();
            jPanels[i].setLayout(new GridLayout(3, 3, 2, 2));
            jPanels[i].setSize(150, 150);
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                jTextAreas[i][j] = new JTextArea("");
                jTextAreas[i][j].setEditable(true);
                jTextAreas[i][j].setRows(1);
                jTextAreas[i][j].setColumns(1);
                jTextAreas[i][j].setFont(new Font("Times New Roman", Font.PLAIN, 32));
                jTextAreas[i][j].setTabSize(1);
                jTextAreas[i][j].setSize(50, 50);
                jPanels[i].add(jTextAreas[i][j], BorderLayout.CENTER);
            }
        }
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(3, 3, 2, 2));
        jPanel.setSize(500, 500);
        for (int i = 0; i < 9; i++) {
            jPanel.add(jPanels[i]);
        }
        getContentPane().add(jPanel, BorderLayout.CENTER);

        setTitle("数独");
        add(jPanel);
        pack();
        setVisible(true);
        setSize(480, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!NumSet.contains(jTextAreas[i][j].getText())) {
                    jTextAreas[i][j].setText("");
                }
            }
        }
    }

    public static void main(String[] args) {
        JavaAWT javaAWT = new JavaAWT();
    }
}
