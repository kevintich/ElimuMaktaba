package elimu_maktabaLibrarianScreen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class RestartFrameLibrarian implements Runnable, ActionListener {

	/**
	 *
	 */
	private static final long serialVersionUID = 2247500110471320450L;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new RestartFrameLibrarian(true);
	}

	volatile boolean alive = false;

	RestartFrameLibrarian(boolean b) {
		this.alive = b;
		System.out.println("starting....");
		Thread t = new Thread(this);
		t.start();
	}

	protected void restart() {
		// TODO Auto-generated method stub

		System.out.println("closing");
		this.alive = false;
		Thread t = new Thread(new RestartFrameLibrarian(true));
		t.start();
	}

	JFrame frame = null;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		frame = new JFrame();
		JButton butt = new JButton("Click me....");
		butt.addActionListener(this);
		frame.add(butt);
		frame.setSize(1024, 768);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		while (true) {
			if (!this.alive) {
				System.out.println("killing");
				break;
			}
		}
		try {
			Runtime.getRuntime().exec(
					"java elimu_maktabaAdminScreen.RestartFrameAdmin", null,
					new File("/home/devk/workspace/ElimuMaktaba/bin/elimu_maktabaLibrarianScreen/"));
			System.out.println("omaha");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(0);
		return;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		frame.dispose();
		try{
			Thread.sleep(5000);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		restart();
	}
}