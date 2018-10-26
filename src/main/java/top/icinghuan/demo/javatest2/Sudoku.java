package top.icinghuan.demo.javatest2;

import com.google.common.collect.Sets;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.*;

/**
 * @author : xy
 * @date : 2018/10/24
 * Description :
 */
public class Sudoku extends JFrame implements ActionListener {

    private static final Set<Integer> NUM_SET = Sets.newHashSet(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
    private static final Set<String> STRING_SET = Sets.newHashSet("", "1", "2", "3", "4", "5", "6", "7", "8", "9");

    private static ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("demo-pool-%d").build();
    private static ExecutorService singleThreadPool = new ThreadPoolExecutor(16, 32,
            0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
    private Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            getSudoku();
        }
    });
    private JTextPane[][] jTextPanes = new JTextPane[9][9];
    private JPanel[] jPanels = new JPanel[9];
    private JMenu[] jMenus;
    private JMenuItem[] jMenuItems;

    private Integer[][] gameData = new Integer[9][9];

    DocumentListener documentListener = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            singleThreadPool.execute(thread);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            singleThreadPool.execute(thread);
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            singleThreadPool.execute(thread);
        }
    };

    public Sudoku() {
        for (int i = 0; i < 9; i++) {
            jPanels[i] = new JPanel();
            jPanels[i].setLayout(new GridLayout(3, 3, 2, 2));
            jPanels[i].setSize(150, 150);
        }
        SimpleAttributeSet set1 = new SimpleAttributeSet();
        StyleConstants.setForeground(set1, Color.BLACK);
        StyleConstants.setFontFamily(set1, "Times New Roman");
        StyleConstants.setFontSize(set1, 32);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                gameData[i][j] = 0;
                jTextPanes[i][j] = new JTextPane();
                jTextPanes[i][j].setText("");
                StyledDocument styledDocument = jTextPanes[i][j].getStyledDocument();
                styledDocument.setCharacterAttributes(0, 1, set1, false);
                jTextPanes[i][j].setEditable(false);
                jTextPanes[i][j].setSize(50, 50);
                jTextPanes[i][j].getDocument().addDocumentListener(documentListener);
                jPanels[i].add(jTextPanes[i][j], BorderLayout.CENTER);
            }
        }
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(3, 3, 10, 10));
        jPanel.setSize(500, 500);
        for (int i = 0; i < 9; i++) {
            jPanel.add(jPanels[i]);
        }
        getContentPane().add(jPanel, BorderLayout.CENTER);

        jMenus = new JMenu[2];
        jMenus[0] = new JMenu("Game");
        jMenus[1] = new JMenu("Sudoku");
        jMenuItems = new JMenuItem[5];
        jMenuItems[0] = new JMenuItem("New Game");
        jMenuItems[1] = new JMenuItem("Save Game");
        jMenuItems[2] = new JMenuItem("load Game");
        jMenuItems[3] = new JMenuItem("About");
        jMenuItems[4] = new JMenuItem("Exit");
        for (int i = 0; i < 5; i++) {
            jMenuItems[i].addActionListener(this);
            if (i < 3) {
                jMenus[0].add(jMenuItems[i]);
            } else {
                jMenus[1].add(jMenuItems[i]);
            }
        }

        JMenuBar jMenuBar = new JMenuBar();
        for (int i = 0; i < 2; i++) {
            jMenuBar.add(jMenus[i]);
        }
        setJMenuBar(jMenuBar);

        setTitle("Sudoku");
        add(jPanel);
        pack();
        setVisible(true);
        setSize(500, 500);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jMenuItems[0]) {
            newSudoku();
        }
        if (e.getSource() == jMenuItems[1]) {
            if (!checkSudoku()) {
                JOptionPane.showMessageDialog(null, "Can not save the game!", "Save Game", JOptionPane.PLAIN_MESSAGE);
                return;
            }
            // TODO Save Game
//            Frame f = new Frame("Save Game");
//            FileDialog fileDialog = new FileDialog(f, "Save Game", FileDialog.SAVE);
        }
        if (e.getSource() == jMenuItems[2]) {
            // TODO Load Game
            if (!checkSudoku()) {
                JOptionPane.showMessageDialog(null, "Can not load the game!", "Load Game", JOptionPane.PLAIN_MESSAGE);
                return;
            }
            showSudoku();
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    jTextPanes[i][j].setEditable(true);
                }
            }
        }
        if (e.getSource() == jMenuItems[3]) {
            JOptionPane.showMessageDialog(null, "Sudoku\nVersion: 0.1\nAuthor: icinghuan", "About", JOptionPane.PLAIN_MESSAGE);
        }
        if (e.getSource() == jMenuItems[4]) {
            System.exit(0);
        }

    }

    private boolean checkSudoku() {
        boolean flag = true;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!NUM_SET.contains(gameData[i][j])) {
                    gameData[i][j] = 0;
                    flag = false;
                }
            }
        }
        for (int i = 0; i < 9; i++) {
            boolean[] flags = new boolean[10];
            for (int j = 0; j < 10; j++) {
                flags[j] = false;
            }
            for (int j = 0; j < 9; j++) {
                if (gameData[i][j] != 0 && flags[gameData[i][j]]) {
                    gameData[i][j] = 0;
                    flag = false;
                } else {
                    flags[gameData[i][j]] = true;
                }
            }
            for (int j = 0; j < 10; j++) {
                flags[j] = false;
            }
            for (int j = 0; j < 9; j++) {
                if (gameData[j][i] != 0 && flags[gameData[j][i]]) {
                    gameData[i][j] = 0;
                    flag = false;
                } else {
                    flags[gameData[j][i]] = true;
                }
            }
        }
        if (!checkSudokuNineGrids()) {
            flag = false;
        }
        return flag;
    }

    private boolean checkSudokuNineGrids() {
        boolean flag = true;
        for (int k = 0; k < 9; k++) {
            for (int i = (k * 3) % 9; i < (k * 3) % 9 + 3; i++) {
                boolean[] flags = new boolean[10];
                for (int j = 0; j < 10; j++) {
                    flags[j] = false;
                }
                for (int j = (k / 3) * 3; j < (k / 3) * 3 + 3; j++) {
                    if (gameData[i][j] != 0 && flags[gameData[i][j]]) {
                        gameData[i][j] = 0;
                        flag = false;
                    } else {
                        flags[gameData[i][j]] = true;
                    }
                }
            }
        }
        return flag;
    }

    private void showSudoku() {
        checkSudoku();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (NUM_SET.contains(gameData[i][j])) {
                    if (gameData[i][j] == 0) {
                        jTextPanes[i][j].setText("");
                    } else {
                        jTextPanes[i][j].setText(gameData[i][j].toString());
                    }
                    jTextPanes[i][j].repaint();
                }
            }
        }
    }

    private void getSudoku() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!STRING_SET.contains(jTextPanes[i][j].getText())) {
                    jTextPanes[i][j].setText("");
                    jTextPanes[i][j].repaint();
                }
                if ("".equals(jTextPanes[i][j].getText())) {
                    gameData[i][j] = 0;
                } else {
                    gameData[i][j] = Integer.parseInt(jTextPanes[i][j].getText());
                }
            }
        }
        showSudoku();
        if (finishGame()) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    jTextPanes[i][j].setEditable(false);
                }
            }
            JOptionPane.showMessageDialog(null, "You have finished the game!", "Congratulations", JOptionPane.PLAIN_MESSAGE);
        }
    }

    private void newSudoku() {
        Random random = new Random();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                gameData[i][j] = random.nextInt(10);
            }
        }
        showSudoku();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                jTextPanes[i][j].setEditable(true);
            }
        }
    }

    private boolean finishGame() {
        checkSudoku();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (gameData[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Sudoku sudoku = new Sudoku();
    }
}
