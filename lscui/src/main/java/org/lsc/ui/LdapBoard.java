package org.lsc.ui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.lsc.ui.listener.ButtonListener;
import org.lsc.ui.listener.RadioButtonListener;
import org.lsc.ui.utils.Const;

public class LdapBoard
{
  JFrame frame = new JFrame("LDAP Board");
  public JRadioButton ou = new JRadioButton("ou", false);
  public JRadioButton group = new JRadioButton("group", false);
  public JRadioButton people = new JRadioButton("people", false);
  public JLabel successNum = new JLabel("0");
  public JLabel radioLab = new JLabel("Please select the options to sync:");
  public JLabel failedNum = new JLabel("0");
  public JTextArea log_output = new JTextArea("The log content:\n");
  public JScrollPane textscroll = new JScrollPane(this.log_output,
    22, 32);
  JProgressBar progressBar = new JProgressBar();
  public JLabel syncTip = new JLabel();

  public void initBoard()
  {
    RadioButtonListener radioButtonListener = new RadioButtonListener();
    this.ou.addItemListener(radioButtonListener);
    this.group.addItemListener(radioButtonListener);
    this.people.addItemListener(radioButtonListener);

    this.ou.setPreferredSize(new Dimension(100, 100));
    this.group.setPreferredSize(new Dimension(100, 100));
    this.people.setPreferredSize(new Dimension(100, 100));
    this.ou.setFont(this.ou.getFont().deriveFont(18.0F));
    this.group.setFont(this.group.getFont().deriveFont(18.0F));
    this.people.setFont(this.people.getFont().deriveFont(18.0F));

    JPanel checkPanel = new JPanel();
    checkPanel.add(this.radioLab);
    checkPanel.add(this.ou);
    checkPanel.add(this.people);
    checkPanel.add(this.group);

    JPanel top = new JPanel();
    top.setPreferredSize(new Dimension(Const.WIDTH, 100));
    top.add(checkPanel);
    this.frame.add(top, "North");

    JPanel middle = new JPanel();
    middle.setPreferredSize(new Dimension(Const.WIDTH, 500));

    JPanel successPanel = new JPanel();
    successPanel.setPreferredSize(new Dimension(170, 30));
    JLabel success = new JLabel("Successful: ");
    success.setFont(success.getFont().deriveFont(18.0F));
    this.successNum.setFont(this.successNum.getFont().deriveFont(18.0F));
    successPanel.add(success);
    successPanel.add(this.successNum);
    middle.add(successPanel);


    JPanel failedPanel = new JPanel();
    failedPanel.setPreferredSize(new Dimension(120, 30));
    JLabel failed = new JLabel("Failed: ");
    failed.setFont(failed.getFont().deriveFont(18.0F));
    this.failedNum.setFont(this.failedNum.getFont().deriveFont(18.0F));
    failedPanel.add(failed);
    failedPanel.add(this.failedNum);
    middle.add(failedPanel);


    this.progressBar.setMinimum(0);
    this.progressBar.setMaximum(100);
    this.progressBar.setStringPainted(true);
    this.progressBar.setPreferredSize(new Dimension(400, 20));
    middle.add(this.progressBar);


    JPanel syncTipPanel = new JPanel();
    syncTipPanel.setPreferredSize(new Dimension(Const.WIDTH, 30));
    this.syncTip.setForeground(Color.RED);
    syncTipPanel.add(this.syncTip);
    middle.add(syncTipPanel);

    this.textscroll.setPreferredSize(new Dimension(400, 300));

    middle.add(this.textscroll);

    this.frame.add(middle);


    ButtonListener buttonListener = new ButtonListener(this);
    JButton startBtn = new JButton("Start");
    startBtn.addActionListener(buttonListener);
    startBtn.setPreferredSize(new Dimension(100, 40));
    JButton cancelBtn = new JButton("Cancel");
    cancelBtn.addActionListener(buttonListener);
    cancelBtn.setPreferredSize(new Dimension(100, 40));
    JPanel bottom = new JPanel();
    bottom.add(startBtn);
    bottom.add(cancelBtn);
    this.frame.add(bottom, "South");

    this.frame.setDefaultCloseOperation(3);
    this.frame.pack();
    this.frame.setLocationRelativeTo(null);
    this.frame.setVisible(true);
  }

  public static void main(String[] args)
  {
    LdapBoard board = new LdapBoard();
    board.initBoard();
    System.out.println("Ldap board...");
    System.out.println("isOUSynced: " + Const.isOuSynced);
  }
}
