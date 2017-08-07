package org.lsc.ui;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.lsc.ui.utils.Const;
import org.lsc.ui.utils.ProcessUtil;

public class BatThread
  extends Thread
{
  private LdapBoard ldapBoard;
  private boolean stopped = true;

  public void cancel()
  {
    this.stopped = false;
    stop();
  }

  public boolean isStopped()
  {
    return this.stopped;
  }

  private boolean isOU = false;
  private boolean isPeople = false;
  private boolean isGroup = false;

  public BatThread(LdapBoard ldapBoard)
  {
    this.ldapBoard = ldapBoard;
    this.isOU = ldapBoard.ou.isSelected();
    this.isPeople = ldapBoard.people.isSelected();
    this.isGroup = ldapBoard.group.isSelected();
  }

  private void updateNum(String line)
  {
    String[] m = ProcessUtil.matchEntryResult(line);
    this.successNum += Integer.valueOf(m[0]).intValue();
    this.faileNum += Integer.valueOf(m[1]).intValue();
    this.ldapBoard.successNum.setText(" " + this.successNum);
    this.ldapBoard.failedNum.setText(" " + this.faileNum);
  }

  private void runSingleCommand(String command)
  {
    Process ps = null;
    try
    {
      ps = Runtime.getRuntime().exec(command);
      InputStream in = ps.getInputStream();
      BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
      String line = null;
      while (((line = br.readLine()) != null) && (!this.stopped))
      {
        this.ldapBoard.log_output.append(line + "\n");
        if (line.indexOf("All entries") != -1)
        {
          System.out.println("All entries: " + line);

          updateNum(line);
        }
      }
      in.close();

      System.out.println("thread: stop: " + this.stopped);
      while (!this.stopped)
      {
        System.out.println("--waitFor--");
        ps.waitFor();

        int i = ps.exitValue();
        System.out.println("exitValue: " + i);
        if (i == 0)
        {
          System.out.println("bat finished.");
          break;
        }
        System.out.println("bat exit value: " + i);
      }
      if (this.stopped)
      {
        ps.destroy();
        ps = null;
        return;
      }
    }
    catch (Exception e)
    {
      System.out.println("Error: " + e);
      e.printStackTrace();

      ps.destroy();
      ps = null;
    }
  }

  private int successNum = 0;
  private int faileNum = 0;

  @Override
public void run()
  {
    this.stopped = false;
    this.successNum = 0;
    this.faileNum = 0;
    if (this.isOU) {
      runSingleCommand(Const.BaseCMD() + Const.OU);
    }
    if ((this.isPeople) && (!this.stopped)) {
      runSingleCommand(Const.BaseCMD() + Const.PEOPLE);
    }
    if ((this.isGroup) && (!this.stopped)) {
      runSingleCommand(Const.BaseCMD() + Const.GROUP);
    }
    this.ldapBoard.progressBar.setValue(100);
    this.stopped = true;
  }
}
