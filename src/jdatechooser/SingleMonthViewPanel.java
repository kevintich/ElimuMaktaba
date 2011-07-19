package jdatechooser;

import java.awt.*;
import javax.swing.*;
import java.util.Vector;
import javax.swing.border.*;
import javax.swing.JSpinner;
import java.util.*;
import java.util.GregorianCalendar;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JToggleButton;
import java.util.Calendar;
import javax.swing.SpinnerModel;
import javax.swing.JFormattedTextField;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * <p>Title: JDateChooser</p>
 * <p>Description: A swing component that help in date selection and formatting</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: milcom solutions</p>
 * @authorI Mujib Olalekan Ishola
 * @version 1.0
 */

public class SingleMonthViewPanel
    extends JPanel
    {
public CalendarWindow cWin=null;
  GregorianCalendar calendar = new GregorianCalendar();
  int currentDate;
  int currentDay;
  int currentMonth;
  int currentYear;
  ButtonHandler bHandler=null;
  MouseHandler mouseHandler=null;
  String days[] = {
      "",
      "Sunday", "Monday", "Teusdays", "Wednesday", "Thursday", "Friday",
      "Saturday"};
  String month[] = {
      "January", "Feburary", "March", "April", "May", "June", "July", "August",
      "September", "October", "November", "December"};

  BorderLayout borderLayout1 = new BorderLayout();
  JPanel pnlSelector = new JPanel();
  JButton btnPreviousMonth = new JButton();
  JTextField jTextField2 = new JTextField();
  JButton btnNextMonth = new JButton();
  ButtonGroup buttonGroup = new ButtonGroup();
  JPanel pnlDaysHolder = new JPanel();
  JToggleButton currentlySelectedButton = null;
  JToggleButton daySelectionButton[] = new JToggleButton[40];
  JLabel daysHeader[] = new JLabel[7];
  GridLayout gridLayout1 = new GridLayout();
  Font font = new java.awt.Font("trebuchet", 0, 9);
  public static Color selectorBackground = Color.LIGHT_GRAY;
  public static Color selectorForeground = Color.DARK_GRAY;
  public static Color calenderHeaderBackground = Color.black;
  public static Color calenderHeaderForeground = Color.WHITE;
  public static Color daySelectionBackground = Color.WHITE;
  public static Color daySelectionForeground = Color.BLUE;
  public static Color daysSelectionSelectedBackGround = Color.DARK_GRAY;
  public static Color daysSelectionSelectedForeGround = Color.RED;
  public static Color gridColor=Color.BLACK;

  public static Color mouseOverBorderColor=Color.BLUE;
  public static Color mouseOverBackColor=Color.CYAN;

  public static boolean showGrid = false;

  Border border1;
  GridLayout gridLayout2 = new GridLayout();
  JTextField txtMonth = new JTextField();
    JSpinner spYear = null;
    private int min,max;
  public JFormattedTextField txtdate = null;
  public SingleMonthViewPanel(int date,int month,int year,int min,int max)
  {
    this.calendar.set(Calendar.DATE,date);
    this.calendar.set(Calendar.MONTH,month);
    this.calendar.set(Calendar.YEAR,year);
    this.bHandler=new ButtonHandler(this);
    this.mouseHandler=new MouseHandler(this);
    this.min=min;
    this.max=max;
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * initialises the component and the initial date from the date pulled from the system
   * <b>Note :-<b/><br>if the date on your system is wrong date on the calendar component will
   * not be any different hence GIGO
   * @throws java.lang.Exception
   */
  private void jbInit() throws Exception {
    this.spYear=new JSpinner(new SpinnerYearModel(this, this.calendar,this.min,this.max));
    border1 = BorderFactory.createLineBorder(Color.black, 1);
    this.setLayout(borderLayout1);
    btnPreviousMonth.setBorder(null);
    btnPreviousMonth.setText("<<");
    btnPreviousMonth.addActionListener(this.bHandler);
    jTextField2.setText("jTextField2");
    btnNextMonth.setBorder(null);
    btnNextMonth.setMinimumSize(new Dimension(47, 25));
    btnNextMonth.setPreferredSize(new Dimension(46, 25));
    btnNextMonth.setText(">>");
    btnNextMonth.addActionListener(this.bHandler);
    //instanciate all the headers
    for (int i = 0; i < 7; i++) {
      daysHeader[i] = new JLabel();
      daysHeader[i].setHorizontalAlignment(JLabel.CENTER);
      pnlDaysHolder.add(daysHeader[i]);
      daysHeader[i].setOpaque(true);
    }
    daysHeader[0].setText("S");
    daysHeader[1].setText("M");
    daysHeader[2].setText("T");
    daysHeader[3].setText("W");
    daysHeader[4].setText("T");
    daysHeader[5].setText("F");
    daysHeader[6].setText("S");

    for (int i = 0; i < 40; i++) {
      this.daySelectionButton[i] = new JToggleButton();
      buttonGroup.add(this.daySelectionButton[i]);
      this.daySelectionButton[i].setRolloverEnabled(true);
      pnlDaysHolder.add(this.daySelectionButton[i]);
      this.daySelectionButton[i] .addMouseListener(this.mouseHandler);
      this.daySelectionButton[i].setEnabled(true);
      this.daySelectionButton[i].addActionListener(this.bHandler);
    }

    pnlSelector.setBorder(BorderFactory.createLineBorder(Color.black));
    pnlSelector.setLayout(gridLayout2);
    pnlDaysHolder.setBackground(Color.red);
    pnlDaysHolder.setBorder(BorderFactory.createLineBorder(Color.black));
    pnlDaysHolder.setPreferredSize(new Dimension(330, 160));
    pnlDaysHolder.setLayout(gridLayout1);
    gridLayout1.setColumns(7);
    gridLayout1.setHgap(1);
    gridLayout1.setRows(7);
    gridLayout1.setVgap(1);
    this.setPreferredSize(new Dimension(330, 160));
    txtMonth.setBackground(Color.gray);
    txtMonth.setBorder(null);
    txtMonth.setText("jTextField1");
    spYear.setBorder(null);
        this.add(pnlSelector, BorderLayout.NORTH);
    pnlSelector.add(btnPreviousMonth, null);
    pnlSelector.add(txtMonth, null);
    pnlSelector.add(btnNextMonth, null);
        pnlSelector.add(spYear, null);

    this.add(pnlDaysHolder, BorderLayout.CENTER);
    intCalender();
    txtMonth.setHorizontalAlignment(SwingConstants.CENTER);

    doSkin(font, selectorBackground, selectorForeground,
           calenderHeaderBackground, calenderHeaderForeground,
           daySelectionBackground, daySelectionForeground, showGrid);
    this.currentlySelectedButton.setSelected(true);
 this.currentlySelectedButton.setForeground(this.
    daysSelectionSelectedForeGround);


  }

  public void doSkin(Font font, Color selectorBackground,
                     Color selectorForeground,
                     Color calenderHeaderBackground,
                     Color calenderHeaderForeground,
                     Color daySelectionBackground, Color daySelectionForeground,
                     boolean showGrid) {
    //cosmetic makeup of the gui
    setFont(font);

    setCalenderHeaderBackground(calenderHeaderBackground);
    setCalenderHeaderForeground(calenderHeaderForeground);
    setDaysSelectionBackground(daySelectionBackground);
    setDaysSelectionForeground(daySelectionForeground);
    setSelectorBackground(selectorBackground);
    setSelectorForeground(selectorForeground);
    this.showGrid(showGrid);
  }

  /**
   * sets the component selector bar background color which apperas at the top of the component
   * @param color
   */
  public void setSelectorBackground(Color color) {
    this.selectorBackground = color;
    for (int i = 0; i < pnlSelector.getComponentCount(); i++) {

      Component comp = pnlSelector.getComponent(i);
      comp.setBackground(color);
    }

  }

  /**
   * sets the colour of the font of the selector bar
   * @param color
   */
  public void setSelectorForeground(Color color) {
    this.selectorForeground = color;
    for (int i = 0; i < pnlSelector.getComponentCount(); i++) {

      Component comp = pnlSelector.getComponent(i);
      comp.setForeground(color);

    }

  }

  /**
   * Sets the back color of the calendar month header
   * @param color
   */
  public void setCalenderHeaderBackground(Color color) {
    this.calenderHeaderBackground = color;
    daysHeader[0].setBackground(color);
    daysHeader[1].setBackground(color);
    daysHeader[2].setBackground(color);
    daysHeader[3].setBackground(color);
    daysHeader[4].setBackground(color);
    daysHeader[5].setBackground(color);
    daysHeader[6].setBackground(color);
  }

  /**
   * Sets the fore color of the calendar month header
   * @param color
   */

  public void setCalenderHeaderForeground(Color color) {
    this.calenderHeaderForeground = color;
    daysHeader[0].setForeground(color);
    daysHeader[1].setForeground(color);
    daysHeader[2].setForeground(color);
    daysHeader[3].setForeground(color);
    daysHeader[4].setForeground(color);
    daysHeader[5].setForeground(color);
    daysHeader[6].setForeground(color);
  }

  /**
   * sets the actual day/date selection backcolor
   * @param color
   */
  public void setDaysSelectionBackground(Color color) {
    pnlDaysHolder.setBackground(color);
    this.daySelectionBackground = color;
    for (int i = 0; i < 40; i++) {
  this.daySelectionButton[i].setBackground(color);
 }
  }

  /**
   * sets the actual day/date selection for color
   * @param color
   */

  public void setDaysSelectionForeground(Color color) {
    this.daySelectionForeground=color;
    for (int i = 0; i < 40; i++) {
  this.daySelectionButton[i].setForeground(color);
 }

  }

  /**
   * changes the font of all the text that apperas on the component
   * @param font
   */
  public void setFont(Font font) {
    for (int i = 0; i < this.getComponentCount(); i++) {
      Component comp = this.getComponent(i);
      if (this.getComponent(i)instanceof Container) {
        Container c = (Container)this.getComponent(i);

        for (int j = 0; j < c.getComponentCount(); j++) {
          c.getComponent(j).setFont(font);
        }

      }
    }

  }

  /**
   * shows or hide the grid of the date /day selector
   * @param value
   */
  public void showGrid(boolean value) {
    this.showGrid = value;
    for (int i = 0; i < pnlDaysHolder.getComponentCount(); i++) {

      try {
        AbstractButton comp = (AbstractButton) pnlDaysHolder.getComponent(i);
        comp.setBorderPainted(value);
      }
      catch (Exception ex) {
        //   ex.printStackTrace();
      }

    }

  }

  /**
   * initializes the calender from the date gotten from the system
   */
  protected void intCalender() {
    int currentIndex = 0;
    //clear the tex on the buttons
    for (int i = 0; i < 40; i++) {
      this.daySelectionButton[i].setText("");
      this.daySelectionButton[currentIndex].setSelected(false);
     this.daySelectionButton[i].setEnabled(true);
    }

    doSkin(font, selectorBackground, selectorForeground,
           calenderHeaderBackground, calenderHeaderForeground,
           daySelectionBackground, daySelectionForeground, showGrid);



    this.currentDate = calendar.get(Calendar.DAY_OF_MONTH);
    this.currentMonth = calendar.get(Calendar.MONTH);
    this.currentYear = calendar.get(Calendar.YEAR);
    calendar.set(Calendar.DAY_OF_MONTH, 1);
    this.currentDay = calendar.get(Calendar.DAY_OF_WEEK);
    this.txtMonth.setText(month[currentMonth] + "");

    for (int i = Calendar.SUNDAY; i < this.currentDay; i++) {
      this.daySelectionButton[currentIndex].setEnabled(false);


      currentIndex++;
    }
        while (calendar.get(Calendar.MONTH) == this.currentMonth){
      int day = calendar.get(Calendar.DAY_OF_MONTH);
      if (day == currentDate) {
        this.daySelectionButton[currentIndex].setText("" + day);
        this.daySelectionButton[currentIndex].setSelected(true);
        this.daySelectionButton[currentIndex].setForeground(this.
            daysSelectionSelectedForeGround);
        this.currentlySelectedButton=this.daySelectionButton[currentIndex];

      }
      else {
        this.daySelectionButton[currentIndex].setText("" + day);
      }
      this.daySelectionButton[currentIndex].setVisible(true);
      currentIndex++;
      calendar.add(Calendar.DAY_OF_MONTH, 1);
      //today = calendar.get(Calendar.DAY_OF_WEEK);
    }

    calendar.set(Calendar.MONTH, this.currentMonth--);
    this.currentMonth = calendar.get(Calendar.MONTH);
    while(currentIndex<40)
    {
      //for(int i=currentIndex;i<currentIndex;i++)
      //{
        this.daySelectionButton[currentIndex].setEnabled(false);
currentIndex++;
      //}
    }
  }

  public void increaseMonth() {
    if(calendar.get(Calendar.MONTH)!=Calendar.DECEMBER)
    {
      calendar.add(Calendar.MONTH, 1);
      calendar.set(Calendar.DATE,this.currentDate);

      this.intCalender();
    }
  }
  public void decreaseMonth() {
    if(calendar.get(Calendar.MONTH)!=Calendar.JANUARY)
    {
      calendar.add(Calendar.MONTH, -1);
      calendar.set(Calendar.DATE,this.currentDate);
      this.intCalender();
    }
  }
  public void setGridColor(Color color)
  {
    this.gridColor=color;
  }
  public void setMouseOverBorderColor(Color color)
  {
    this.mouseOverBorderColor=color;
  }
  public void setMouseOverBackground(Color color)
  {
    this.mouseOverBackColor=color;
  }
  public void addCalendarWindowListener(CalendarWindow cWin)
  {
    this.cWin=cWin;
  }
  /**
   * A String instance of the current date set by the calendar
   * @return
   */
  public String toString()
  {
    return this.currentDate+" "+month[currentMonth] +" "+this.currentYear;

  }
  public void addTextFieldListener(JFormattedTextField txtdate)
      {
     this.txtdate=txtdate;
      }

      /**
       * retruns the current date as displayed on the component
       */
      public String getCurrentDate()
    {
    String date=    calendar.get(Calendar.DAY_OF_MONTH)+" "+ calendar.get(Calendar.MONTH)+" "+ calendar.get(Calendar.YEAR);
    return date;

    }

}