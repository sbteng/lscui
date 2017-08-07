package org.lsc.ui;

public class ProgressThread
  extends Thread
{
  private LdapBoard ldapBoard;

  public ProgressThread(LdapBoard ldapBoard)
  {
    this.ldapBoard = ldapBoard;
  }

  private boolean stopped = true;

  public void cancel()
  {
    this.stopped = true;
    stop();
  }

  @Override
public void run()
  {
    this.stopped = false;
    System.out.println("Progress Thread is running!");
    for (int i = 0; i < 99; i++)
    {
      if ((this.stopped) && (!this.ldapBoard.failedNum.getText().equals("0"))) {
        break;
      }
      if ((this.stopped) && (this.ldapBoard.failedNum.getText().equals("0")))
      {
        this.ldapBoard.progressBar.setValue(100);
        break;
      }
      this.ldapBoard.progressBar.setValue(i);
      try
      {
        Thread.sleep(100L);
      }
      catch (InterruptedException e)
      {
        e.printStackTrace();
      }
    }
    System.out.println("Progress Thread returns!");
    this.stopped = true;
  }
}
