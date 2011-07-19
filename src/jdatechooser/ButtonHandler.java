package jdatechooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JToggleButton;
import java.util.Calendar;
/**
 * <p>Title: ButtonHandler</p>
 * <p>Description: The class handles all the button click and pressed event for the
 * next and previous button located in the single month view panel and also handles the
 * button events for the day selection buttons
 * class</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: milcom solutions</p>
 * @authorI Mujib Olalekan Ishola
 * @version 1.0
 */
public class ButtonHandler implements ActionListener {
  SingleMonthViewPanel smvp=null;
  public ButtonHandler(SingleMonthViewPanel smvp) {
    this.smvp=smvp;
  }
  /**
   * called when one of the buttons are pressed
   * @param e event to be handles
   */
  public void actionPerformed(ActionEvent e)
  {
    if(e.getSource() instanceof JToggleButton)
    {
      JToggleButton btn=(JToggleButton)e.getSource();
      this.smvp.currentlySelectedButton=btn;
      this.smvp.calendar.set(Calendar.DATE,Integer.parseInt(btn.getText()));
     this.smvp.intCalender();
     if(this.smvp.cWin!=null)
     {
       ///
//       this.smvp.cWin.txtdate.setValue(this.smvp.calendar.get(Calendar.DATE)+"-"+));
if(this.smvp.txtdate!=null)
       {
         this.smvp.txtdate.setText(this.smvp.toString());
       }
       this.smvp.cWin.hideCalendar();
     }
    }
    else
    {

      if (e.getSource() == this.smvp.btnNextMonth) {
        this.smvp.increaseMonth();
      }
      if (e.getSource() == this.smvp.btnPreviousMonth) {
        this.smvp.decreaseMonth();
      }
    }

   }


}