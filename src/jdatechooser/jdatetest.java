package jdatechooser;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

/**
 * <p>Title: JDateChooser</p>
 * <p>Description: A swing component that help in date selection and formatting</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: milcom solutions</p>
 * @authorI not attributable
 * @version 1.0
 */

public class jdatetest extends JFrame
{
JDateChooser dateChooser=new JDateChooser(23,6,2005,1900,3000,0);
    JPanel jPanel1 = new JPanel();
    public jdatetest() throws HeadlessException
    {
        try
        {
            jbInit();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


	/*
	 * public static void main(String[] args) throws HeadlessException {
	 * jdatetest jdatetest1 = new jdatetest(); }
	 */
    private void jbInit() throws Exception
    {
        this.getContentPane().add(this.dateChooser, BorderLayout.CENTER);


        this.setSize(200,200);
        this.show();
    }
}