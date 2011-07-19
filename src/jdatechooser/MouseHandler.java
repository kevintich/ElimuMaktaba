package jdatechooser;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JToggleButton;
import javax.swing.border.LineBorder;
import java.awt.Cursor;

public class MouseHandler
    implements MouseListener {
  private LineBorder border = null;
  private SingleMonthViewPanel smvp = null;

  public MouseHandler(SingleMonthViewPanel smvp) {
    this.smvp = smvp;
  }

  public void mouseClicked(MouseEvent parm1) {}

  public void mousePressed(MouseEvent parm1) {}

  public void mouseReleased(MouseEvent parm1) {}

  public void mouseEntered(MouseEvent parm1) {
    try {
      this.border = new LineBorder(this.smvp.mouseOverBorderColor);
      JToggleButton btn = (JToggleButton) parm1.getSource();
      if(!btn.getText().equalsIgnoreCase(""))
      {
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(this.border);
        btn.setBorderPainted(true);
        btn.setBackground(this.smvp.mouseOverBackColor);
      }
    }
    catch (Exception ex) {
    }
    //btn.setBorder();
    /**@todo Implement this java.awt.event.MouseListener method*/
    //throw new java.lang.UnsupportedOperationException("Method mouseEntered() not yet implemented.");
  }

  public void mouseExited(MouseEvent parm1) {
    /**@todo Implement this java.awt.event.MouseListener method*/
    try {
      this.border = new LineBorder(this.smvp.gridColor);
      JToggleButton btn = (JToggleButton) parm1.getSource();
      if(!btn.getText().equalsIgnoreCase(""))
      {
      btn.setBorder(this.border);
      btn.setBorderPainted(this.smvp.showGrid);
      btn.setBackground(this.smvp.daySelectionBackground);
      }
      else
      {
        btn.setCursor(null);
      }
    }
    catch (Exception ex) {
    }

    //throw new java.lang.UnsupportedOperationException("Method mouseExited() not yet implemented.");
  }

}