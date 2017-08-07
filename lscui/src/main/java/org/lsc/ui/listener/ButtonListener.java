package org.lsc.ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.lsc.ui.BatThread;
import org.lsc.ui.LdapBoard;
import org.lsc.ui.ProgressThread;

public class ButtonListener
  implements ActionListener
{
  private LdapBoard ldapBoard;
  private BatThread batthread;
  private ProgressThread procthread;

  public ButtonListener(LdapBoard ldapBoard)
  {
    this.ldapBoard = ldapBoard;
    this.batthread = new BatThread(ldapBoard);
    this.procthread = new ProgressThread(ldapBoard);
  }

  public void actionPerformed(ActionEvent event)
  {
    String command = event.getActionCommand();
    if (command.equals("Start"))
    {

    	if (!this.ldapBoard.ou.isSelected() && !this.ldapBoard.group.isSelected() && !this.ldapBoard.people.isSelected()  ){
    		System.err.println("No option is selected!");
    		return;
    	}
      System.out.println("command Start");
      if (!this.procthread.isAlive()) {
        this.procthread.start();
      }
      if (!this.batthread.isAlive())
      {
        this.batthread = new BatThread(this.ldapBoard);
        this.batthread.start();
      }
      System.out.println("sync  end...");
      if (this.ldapBoard.failedNum.getText().equals("0")) {
        this.ldapBoard.syncTip.setText(null);
      }
    }
    else if (command.equals("Cancel"))
    {
      System.out.println("command Cancel");
      if (this.batthread.isAlive()) {
        this.batthread.cancel();
      }
      if (this.procthread.isAlive()) {
        this.procthread.cancel();
      }
    }
  }
}
