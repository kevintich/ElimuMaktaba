package jdatechooser;

import java.util.Date;
import java.util.Calendar;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * <p>Title: JDateChooser</p>
 * <p>Description: A swing component that help in date selection and formatting</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: milcom solutions</p>
 * @authorI not attributable
 * @version 1.0
 */

public class CalendarWindow extends JFrame
{
SingleMonthViewPanel smvp=null;
  JFormattedTextField txtdate=null;
  Calendar date=null;
int minDate;
int maxDate;
  BorderLayout borderLayout1 = new BorderLayout();
    Border border1;

    public CalendarWindow(int date,int month,int year,int minDate,int maxDate)
    {
        try
        {

        this.maxDate=maxDate;
        this.minDate=minDate;
      this.smvp=new SingleMonthViewPanel(date,month,year,this.minDate,this.maxDate);
        jbInit();
      }
      catch(Exception e)
      {
        e.printStackTrace();
      }
    }
    private void jbInit() throws Exception
    {
        border1 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(156, 156, 158));
        this.setResizable(false);
        this.setSize(new Dimension(340, 180));
        this.setUndecorated(true);
    this.getContentPane().setLayout(borderLayout1);
        this.getContentPane().add(this.smvp);
        this.smvp.addCalendarWindowListener(this);
    }
    public void showCalendar(int x,int y)
    {
      this.setLocation(x,y);
      this.show(true);
    }

    public void hideCalendar()
    {
         this.show(false);
    }
public void addTextFieldListener(JFormattedTextField txtdate)
    {
   this.txtdate=txtdate;
    }

}