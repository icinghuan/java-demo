package top.icinghuan.demo.javatest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Calculator extends JFrame implements ActionListener {
    private double ans_a = 0.0;
    private double ans_b = 0.0;
    private double ans = 0.0;
    private int k = 0;//记录运算符
    private int kk = 0;//清零
    private String s;
    JButton b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15;
    JTextArea t;

    public Calculator() {
        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(4, 4, 3, 3));
        t = new JTextArea("");
        t.setEditable(false);
        add(t);
        setLayout(new BorderLayout());
        getContentPane().add(t, BorderLayout.NORTH);
        getContentPane().add(p1, BorderLayout.CENTER);//布局

        b7 = new JButton("7");
        p1.add(b7);
        b7.addActionListener(this);
        b8 = new JButton("8");
        p1.add(b8);
        b8.addActionListener(this);
        b9 = new JButton("9");
        p1.add(b9);
        b9.addActionListener(this);

        b10 = new JButton("/");
        p1.add(b10);
        b10.addActionListener(this);

        b4 = new JButton("4");
        p1.add(b4);
        b4.addActionListener(this);
        b5 = new JButton("5");
        p1.add(b5);
        b5.addActionListener(this);
        b6 = new JButton("6");
        p1.add(b6);
        b6.addActionListener(this);

        b11 = new JButton("*");
        p1.add(b11);
        b11.addActionListener(this);

        b1 = new JButton("1");
        p1.add(b1);
        b1.addActionListener(this);
        b2 = new JButton("2");
        p1.add(b2);
        b2.addActionListener(this);
        b3 = new JButton("3");
        p1.add(b3);
        b3.addActionListener(this);

        b12 = new JButton("-");
        p1.add(b12);
        b12.addActionListener(this);

        b0 = new JButton("0");
        p1.add(b0);
        b0.addActionListener(this);

        b13 = new JButton(".");
        p1.add(b13);
        b13.addActionListener(this);
        b14 = new JButton("=");
        p1.add(b14);
        b14.addActionListener(this);
        b15 = new JButton("+");
        p1.add(b15);
        b15.addActionListener(this);

        setTitle("计算器");
        add(p1);
        pack();
        setVisible(true);
        setSize(250, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (kk == -1) {
            t.setText("");
        }
        if (e.getSource() == b0) {
            kk = 0;
            t.append("0");
        }
        if (e.getSource() == b1) {
            kk = 0;
            t.append("1");
        }
        if (e.getSource() == b2) {
            kk = 0;
            t.append("2");
        }
        if (e.getSource() == b3) {
            kk = 0;
            t.append("3");
        }
        if (e.getSource() == b4) {
            kk = 0;
            t.append("4");
        }
        if (e.getSource() == b5) {
            kk = 0;
            t.append("5");
        }
        if (e.getSource() == b6) {
            kk = 0;
            t.append("6");
        }
        if (e.getSource() == b7) {
            kk = 0;
            t.append("7");
        }
        if (e.getSource() == b8) {
            kk = 0;
            t.append("8");
        }
        if (e.getSource() == b9) {
            kk = 0;
            t.append("9");
        }
        //0-9
        if (e.getSource() == b13) {
            kk = 0;
            t.append(".");
        }

        if (e.getSource() == b10) {
            s = t.getText();
            t.setText("");
            ans_a = Double.parseDouble(s);
            k = 1;
        }
        if (e.getSource() == b11) {
            s = t.getText();
            t.setText("");
            ans_a = Double.parseDouble(s);
            k = 2;
        }
        if (e.getSource() == b12) {
            s = t.getText();
            t.setText("");
            ans_a = Double.parseDouble(s);
            k = 3;
        }
        if (e.getSource() == b15) {
            s = t.getText();
            t.setText("");
            ans_a = Double.parseDouble(s);
            k = 4;
        }

        if (e.getSource() == b14) {
            s = t.getText();
            ans_b = Double.parseDouble(s);
            if (k == 1) {
                ans = ans_a / ans_b;
                s = Double.toString(ans);
                t.setText(s);
                kk = -1;
            }
            if (k == 2) {
                ans = ans_a * ans_b;
                s = Double.toString(ans);
                t.setText(s);
                kk = -1;
            }
            if (k == 3) {
                ans = ans_a - ans_b;
                s = Double.toString(ans);
                t.setText(s);
                kk = -1;
            }
            if (k == 4) {
                ans = ans_a + ans_b;
                s = Double.toString(ans);
                t.setText(s);
                kk = -1;
            }
        }
    }

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
    }
}