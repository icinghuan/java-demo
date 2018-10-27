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
            .setNameFormat("single-pool-%d").build();
    private static ExecutorService singleThreadPool = new ThreadPoolExecutor(16, 32,
            0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
    private JTextPane[][] jTextPanes = new  JTextPane[9][9];
    private JPanel[] jPanels = new JPanel[9];
    private JMenu[] jMenus;
    private JMenuItem[] jMenuItems;
    private SimpleAttributeSet set1, set2, set3;

    private Integer[][] gameData = new Integer[9][9];
    private boolean[][] gameFlags = new boolean[9][9];

    DocumentListener documentListener = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
//            threadRun();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
//            threadRun();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
//            threadRun();
        }
    };

    private void threadRun() {
        singleThreadPool.execute(() -> {
            getSudoku();
            showSudoku();
        });
    }

    public Sudoku() {
        // TODO 文本居中显示
        set1 = new SimpleAttributeSet();
        StyleConstants.setForeground(set1, Color.BLACK);
        StyleConstants.setAlignment(set1, StyleConstants.ALIGN_CENTER);
        StyleConstants.setBold(set1, true);
        StyleConstants.setFontFamily(set1, "Times New Roman");
        StyleConstants.setFontSize(set1, 32);

        set2 = new SimpleAttributeSet();
        StyleConstants.setForeground(set2, Color.GREEN);
        StyleConstants.setAlignment(set2, StyleConstants.ALIGN_CENTER);
        StyleConstants.setFontFamily(set2, "Times New Roman");
        StyleConstants.setFontSize(set2, 48);

        set3 = new SimpleAttributeSet();
        StyleConstants.setForeground(set3, Color.RED);
        StyleConstants.setAlignment(set3, StyleConstants.ALIGN_CENTER);
        StyleConstants.setFontFamily(set3, "Times New Roman");
        StyleConstants.setFontSize(set3, 48);

        for (int i = 0; i < 9; i++) {
            jPanels[i] = new JPanel();
            jPanels[i].setLayout(new GridLayout(3, 3, 2, 2));
            jPanels[i].setSize(150, 150);
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                gameData[i][j] = 0;
                jTextPanes[i][j] = new JTextPane();
                jTextPanes[i][j].setText("");
                jTextPanes[i][j].setEditable(false);
                jTextPanes[i][j].setSize(50, 50);
                jTextPanes[i][j].getDocument().addDocumentListener(documentListener);
                jPanels[i / 3 * 3 + j / 3].add(jTextPanes[i][j]);
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
            if (!checkSudoku(false)) {
                JOptionPane.showMessageDialog(null, "Can not save the game!", "Save Game", JOptionPane.PLAIN_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(null, "Please copy the follow string:\n" + gameToString(), "Save Game", JOptionPane.PLAIN_MESSAGE);
        }
        if (e.getSource() == jMenuItems[2]) {
            // TODO Load Game
            if (!checkSudoku(false)) {
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

    private boolean checkSudoku(boolean update) {
        boolean flag = true;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!NUM_SET.contains(gameData[i][j])) {
                    if (update) {
                        gameData[i][j] = 0;
                    }
                    gameFlags[i][j] = false;
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
                beforeEvaluate(i, j, flags, update);
            }
            for (int j = 0; j < 9; j++) {
                evaluate(i, j, flags, update);
            }
            for (int j = 0; j < 10; j++) {
                flags[j] = false;
            }
            for (int j = 0; j < 9; j++) {
                beforeEvaluate(j, i, flags, update);
            }
            for (int j = 0; j < 9; j++) {
                evaluate(j, i, flags, update);
            }
        }
        if (!checkSudokuNineGrids(update)) {
            flag = false;
        }
        return flag;
    }

    private boolean checkSudokuNineGrids(boolean update) {
        boolean flag = true;
        for (int k = 0; k < 9; k++) {
            boolean[] flags = new boolean[10];
            for (int j = 0; j < 10; j++) {
                flags[j] = false;
            }
            for (int i = (k * 3) % 9; i < (k * 3) % 9 + 3; i++) {
                for (int j = (k / 3) * 3; j < (k / 3) * 3 + 3; j++) {
                    beforeEvaluate(i, j, flags, update);
                }
            }
            for (int i = (k * 3) % 9; i < (k * 3) % 9 + 3; i++) {
                for (int j = (k / 3) * 3; j < (k / 3) * 3 + 3; j++) {
                    evaluate(i, j, flags, update);
                }
            }
        }
        return flag;
    }

    private void beforeEvaluate(int i, int j, boolean[] flags, boolean update) {
        if (!update) {
            if (!jTextPanes[i][j].isEditable()) {
                gameFlags[i][j] = true;
                flags[gameData[i][j]] = true;
            }
        }
    }

    private boolean evaluate(int i, int j, boolean[] flags, boolean update) {
        boolean flag = true;
        if (gameData[i][j] == 0) {
            gameFlags[i][j] = false;
        } else {
            if (flags[gameData[i][j]]) {
                if (update) {
                    gameData[i][j] = 0;
                }
                gameFlags[i][j] = false;
                flag = false;
            } else {
                gameFlags[i][j] = true;
                flags[gameData[i][j]] = true;
            }
        }
        return flag;
    }

    private void showSudoku() {
        checkSudoku(false);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (NUM_SET.contains(gameData[i][j])) {
                    if (gameData[i][j] == 0) {
                        jTextPanes[i][j].setText("");
                        jTextPanes[i][j].repaint();
                        continue;
                    } else {
                        jTextPanes[i][j].setText(gameData[i][j].toString());
                    }
                    StyledDocument styledDocument = jTextPanes[i][j].getStyledDocument();
                    if (jTextPanes[i][j].isEditable()) {
                        if (gameFlags[i][j]) {
                            styledDocument.setCharacterAttributes(0, 1, set2, false);
                        } else {
                            styledDocument.setCharacterAttributes(0, 1, set3, false);
                        }
                    } else {
                        styledDocument.setCharacterAttributes(0, 1, set1, false);
                    }
                    jTextPanes[i][j].repaint();
                }
            }
        }
        if (finishGame()) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    jTextPanes[i][j].setEditable(false);
                }
            }
            JOptionPane.showMessageDialog(null, "You have finished the game!", "Congratulations", JOptionPane.PLAIN_MESSAGE);
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
    }

    private void newSudoku() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                jTextPanes[i][j].setEditable(false);
            }
        }
        Random random = new Random();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                gameData[i][j] = random.nextInt(10);
                gameFlags[i][j] = false;
            }
        }
        checkSudoku(true);
        showSudoku();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (gameData[i][j] == 0) {
                    jTextPanes[i][j].setEditable(true);
                }
            }
        }
        singleThreadPool.execute(() -> {
            while (!finishGame()) {
                getSudoku();
                showSudoku();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private boolean finishGame() {
        checkSudoku(false);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!gameFlags[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private String gameToString() {
        checkSudoku(true);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                stringBuilder.append(gameData[i][j]);
            }
        }
        String str = stringBuilder.toString();
        return encrypt(str);
    }

    private void loadFromData(String str) {
        String s = decrypt(str);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                gameData[i][j] = 0;
            }
        }
        for (int i = 0; i < s.length(); i++) {
            Character ch = s.charAt(i);
            try {
                gameData[i / 9][i % 9] = Integer.parseInt(ch.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        checkSudoku(true);
    }

    private String encrypt(String str) {
        // TODO encrypt
        return str;
    }

    private String decrypt(String str) {
        // TODO decrypt
        return str;
    }

    public static void main(String[] args) {
        // TODO test
        Sudoku sudoku = new Sudoku();
    }
}
