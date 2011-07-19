package jdatechooser;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.util.Calendar;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import java.awt.peer.ButtonPeer;

public class JDateChooser extends JPanel implements ActionListener {
	JPopupMenu jPopupMenu1 = new JPopupMenu();
	CalendarWindow calWin = null;
	/**
	 * YOu can set the format pattern for the displayed text LONG MEDIUM or
	 * SHORT
	 */
	public static int formatPattern = DateFormat.LONG;
	public JFormattedTextField txtDate = null;
	public JButton btnPop = new JButton();
	Calendar date = null;
	int minDate;
	int maxDate;
	int buttonCoordinateX, buttonCoordinateY;

	public JDateChooser(int date, int month, int year, int minDate,
			int maxDate, int formatPattern) {
		try {
			this.formatPattern = formatPattern;
			txtDate = new JFormattedTextField(
					DateFormat.getDateInstance(this.formatPattern));
			this.maxDate = maxDate;
			this.minDate = minDate;
			calWin = new CalendarWindow(date, month, year, this.minDate,
					this.maxDate);
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setButtonCoordinateXandY(int x, int y) {
		buttonCoordinateX = x;
		buttonCoordinateY = y;
	}

	private void jbInit() throws Exception {
		if (this.date == null) {
			this.txtDate.setValue(new Date());
		} else {
			this.txtDate.setValue(date);
		}
		txtDate.setBounds(new Rectangle(2, 4, 119, 20));
		this.setLayout(null);
		btnPop.setBounds(new Rectangle(123, 5, 17, 18));
		btnPop.setAlignmentY((float) 0.0);
		btnPop.setHorizontalAlignment(SwingConstants.CENTER);
		btnPop.setHorizontalTextPosition(SwingConstants.CENTER);
		btnPop.setText("...");
		btnPop.addActionListener(this);
		btnPop.setVerticalAlignment(SwingConstants.CENTER);
		btnPop.setVerticalTextPosition(SwingConstants.CENTER);
		this.setBorder(txtDate.getBorder());
		this.setPreferredSize(new Dimension(200, 200));

		this.add(txtDate, null);
		this.add(btnPop, null);
		this.calWin.smvp.addTextFieldListener(this.txtDate);
	}

	/**
	 * updates the date displayed wiht the current passed date
	 * 
	 * @param d
	 */
	public void updateDate(Date d) {
		this.txtDate.setValue(d);
	}

	public String getDate() {
		this.calWin.smvp.getCurrentDate();
		return null;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.btnPop) {

			// this.calWin.showCalendar((int) this.getLocationOnScreen().getX(),
			// (int) this.getLocationOnScreen().getY());
			int screenHalfWidth = (Toolkit.getDefaultToolkit().getScreenSize().width) / 2;
			int screenHalfHeight = (Toolkit.getDefaultToolkit().getScreenSize().height) / 2;
			setButtonCoordinateXandY(screenHalfWidth, screenHalfHeight);
			this.calWin.showCalendar(buttonCoordinateX, buttonCoordinateY);
		}
	}

}