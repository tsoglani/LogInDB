/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LogIn;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

/**
 *
 * @author Nikos
 */
public class Fr extends JFrame {

    private JTabbedPane tabbedPane;
    private JButton signInButton = new JButton("Sign In"), signUpButton = new JButton("Sign Up");

    public Fr() {
        goHome();
        initListeners();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 400);
        setVisible(true);
    }

    private void goHome() {
        signInButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        signUpButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        tabbedPane = new JTabbedPane();
        getContentPane().removeAll();
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        jPanel.add(signInButton);
        jPanel.add(signUpButton);
        tabbedPane.addTab("Welcome", jPanel);
        add(tabbedPane);
        revalidate();
        repaint();
    }

    private void initListeners() {
        signInButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                goToSignInFrame();
            }
        });
        signUpButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                goToSignUpFrame();
            }
        });
    }

    private void goToSignInFrame() {


        add(tabbedPane);

        tabbedPane.addTab("Sign Up", getLogInPanel());
        tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
    }

    private JPanel getLogInPanel() {
        JButton jButton = new JButton(" close panels ");
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goHome();
            }
        });
        JPanel panel = new JPanel() {
            public void paint(Graphics g) {
                super.paint(g);
                if (corAns) {
                    g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
                    g.drawString("Success", pointX, pointY);
                    g.setFont(new Font("TimesRoman", Font.PLAIN, 15));
                    g.drawString(AM, px, py);

                }
            }
        };;
        panel.setLayout(new GridLayout(10, 1));
        panel.add(new JLabel("UserName"));
        final JTextField username = new JTextField();
        username.setColumns(10);
        panel.add(username);
        panel.add(new JLabel("password"));
        final JPasswordField passsword = new JPasswordField();
        panel.add(passsword);
        JButton li = new JButton("LOGIN");
        panel.add(li);
        jButton.setBackground(Color.BLACK);
        jButton.setForeground(Color.red);
        panel.add(new JPanel());
        panel.add(jButton);
        li.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader("fle.txt"));
                    String user = null, pass = null;
                    String line = null;
                    try {
                        boolean successLogIn = false;
                        while ((line = bufferedReader.readLine()) != null) {
                            if (line.startsWith("username: ")) {
                                line = line.substring(10, line.length());
                                user = line;
                            } else if (line.startsWith("password: ")) {
                                line = line.substring(10, line.length());
                                pass = line;
                            }

                            if (user != null && pass != null) {
                                if (username.getText().equals(user) && passsword.getText().equals(pass)) {
                                    successLogIn = true;
                                    user = null;
                                    pass = null;
                                }
                            }

                        }

                        if (successLogIn) {
                            System.out.println("Success Log In");
                            JOptionPane.showMessageDialog(Fr.this, "Success Log In");
                            new Thread(runnable).start();
                        } else {
                            System.out.println("Try again");
                            JOptionPane.showMessageDialog(Fr.this, "Please Try again");
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } finally {
                        try {
                            bufferedReader.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }

                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });
        return panel;
    }

    private void goToSignUpFrame() {

//        getContentPane().removeAll();
//        tabbedPane.setVisible(true);
//        tabbedPane.setSize(300, 300);
        tabbedPane.addTab("Sign Up", getSignUpPanel());
        tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);

    }

    private JPanel getSignUpPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(15, 1));
        panel.add(new JLabel("Username"));
        final JTextField username = new JTextField();
        username.setColumns(10);
        panel.add(username);
        panel.add(new JLabel("password"));
        final JPasswordField pass = new JPasswordField();
        panel.add(pass);
        panel.add(new JLabel("password again"));
        final JPasswordField passAgain = new JPasswordField();
        panel.add(passAgain);

        final JButton isWeak = new JButton("password is weak");
        isWeak.setBackground(Color.red);
        isWeak.setEnabled(false);
        panel.add(isWeak);

        final JButton passAreSame = new JButton("password are the same");
        passAreSame.setBackground(Color.green);
        passAreSame.setEnabled(false);
        panel.add(passAreSame);

        passAgain.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (!pass.getText().equals(passAgain.getText())) {
                    passAreSame.setText("password are not the same");
                    passAreSame.setBackground(Color.red);
                    passAreSame.setEnabled(false);
                } else {
                    passAreSame.setText("password are the same");
                    passAreSame.setBackground(Color.green);
                    passAreSame.setEnabled(false);
                }
            }
        });

        JButton li = new JButton("REGISTER");
        panel.add(li);
        pass.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (pass.getText().length() <= 6) {
                    isWeak.setText("password is weak");
                    isWeak.setBackground(Color.red);
                } else if (pass.getText().length() <= 8) {
                    isWeak.setText("password is moderate");
                    isWeak.setBackground(Color.yellow);
                } else {
                    isWeak.setText("password is strong");
                    isWeak.setBackground(Color.green);
                }

                if (!pass.getText().equals(passAgain.getText())) {
                    passAreSame.setText("password are not the same");
                    passAreSame.setBackground(Color.red);
                    passAreSame.setEnabled(false);
                } else {
                    passAreSame.setText("password are the same");
                    passAreSame.setBackground(Color.green);
                    passAreSame.setEnabled(false);
                }
            }
        });

        li.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    if (!isAccountValid(username.getText(), pass.getText(), passAgain.getText())) {
                        return;
                    }
                    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("fle.txt", true));
                    bufferedWriter.append("username: " + username.getText());
                    bufferedWriter.newLine();
                    bufferedWriter.append("password: " + pass.getText());
                    bufferedWriter.newLine();
                    bufferedWriter.close();
                    JOptionPane.showMessageDialog(Fr.this, "Successfull ");;
                    System.out.println("Successfull login");

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        JButton jButton = new JButton(" close Panels ");
        jButton.setBackground(Color.black);
        jButton.setForeground(Color.red);

        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goHome();
            }
        });
        panel.add(new JPanel());

        panel.add(jButton);

        return panel;
    }

    private boolean containTheUser(String cu) {

        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("fle.txt"));
            String user = null;
            String line = null;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.startsWith("username: ")) {

                        line = line.substring(10, line.length());
                        user = line;
                        if (user.equals(cu)) {
                            return true;
                        }
                    }

                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return false;

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException ex) {
                Logger.getLogger(Fr.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    private boolean isAccountValid(String name, String pass, String passAgain) {
        if (!passAgain.equals(pass)) {
            System.out.println("Your Passwords are not the same ");
            return false;
        }
        if (containTheUser(name)) {
            System.out.println("this UserName already exist");
            return false;
        }
        name = name.replaceAll(" ", "");

        if (name.equals("")) {
            System.out.println("You have not entered a username");
            return false;
        }
        if (pass.equals("")) {
            System.out.println("You have not entered a valid username");
            return false;
        }
        return true;
    }
    final String AM = "Giannis Kariakatzis";
    boolean corAns = false;
    int pointX = 100, pointY = 21, px, py;
    Runnable runnable = new Runnable() {
        int maxTime = 10000;
        int timer = 0;

        @Override
        public void run() {
            py = getHeight() / 2+getHeight() / 5;
            corAns = true;
            boolean goUp = false;
            while (true) {

                try {
                    Thread.sleep(10);
                    timer++;
                    if (timer > maxTime * 2) {
                        break;
                    }
                    if (!goUp) {
                        pointY += 2;
                        px++;
                    }
                    if (goUp) {
                        px--;
                        pointY -= 2;
                    }

                    if (pointY + 20 > tabbedPane.getHeight()) {
                        goUp = true;
                    }
                    if (pointY - 20 <= 0) {
                        goUp = false;
                    }
                    repaint();
                    revalidate();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            corAns = false;
        }
    };

}
