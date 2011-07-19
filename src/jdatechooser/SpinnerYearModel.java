package jdatechooser;

import javax.swing.SpinnerModel;
import javax.swing.event.ChangeListener;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.event.ChangeEvent;
import javax.swing.AbstractSpinnerModel;

/**
 * <p>Title: JDateChooser</p>
 * <p>Description: A swing component that help in date selection and formatting</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: milcom solutions</p>
 * @authorI not attributable
 * @version 1.0
 */

final public class SpinnerYearModel extends AbstractSpinnerModel
{
  private GregorianCalendar calendar=null;
private Object obj=null;
private int lowerBoundYear, maxBoundYear;
  private SingleMonthViewPanel smvp=null;
    public SpinnerYearModel(SingleMonthViewPanel smvp,GregorianCalendar calendar,int lowerBoundYear,int maxBoundYear)
    {
      this.smvp=smvp;
      this.lowerBoundYear=lowerBoundYear;
      this.maxBoundYear=maxBoundYear;
      this.calendar=calendar;
      this.obj=this.obj=calendar.get(Calendar.YEAR)+"";

    }
    /**
     * @returns the current value displayers by the spinner
     */
    public Object getValue()
    {
      return this.obj;
    }
    public void setValue(Object parm1)
    {
        /**@todo Implement this javax.swing.SpinnerModel method*/
//        throw new java.lang.UnsupportedOperationException("Method setValue() not yet implemented.");
        this.obj=parm1;
        this.fireStateChanged();
        this.smvp.calendar.set(Calendar.DATE,this.smvp.currentDate);
        this.smvp.intCalender();

    }
    public Object getNextValue()
    {
      if((calendar.get(Calendar.YEAR)+1)<=this.maxBoundYear)
      {
        this.calendar.add(Calendar.YEAR, 1);
        this.obj = calendar.get(Calendar.YEAR) + "";

      }
      return calendar.get(Calendar.YEAR) + "";
    }

    public Object getPreviousValue()
    {
      if((calendar.get(Calendar.YEAR)-1)>=this.lowerBoundYear)
      {
        this.calendar.add(Calendar.YEAR, -1);
        this.obj = calendar.get(Calendar.YEAR) + "";
      }
      return  calendar.get(Calendar.YEAR)+"";
    }




}