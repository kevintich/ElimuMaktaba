package elimu_maktabaLogin;

import javax.swing.*;
import java.awt.*;

public class Splash {
	

	public static void showSplash(int duration) {
		JWindow splash = new JWindow();
		JPanel content = (JPanel) splash.getContentPane();
		int width = 350;
		int height = 231;
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screen.width - width) / 2;
		int y = (screen.height - height) / 2;
		splash.setBounds(x, y, width, height);
		JLabel label = new JLabel(new ImageIcon("loading2.gif"));
		JLabel message = new JLabel(
				"Please wait...", JLabel.CENTER);
		message.setFont(new Font("Sans Serif", Font.BOLD, 12));
		content.add(label, BorderLayout.CENTER);
		content.add(message, BorderLayout.SOUTH);
		content.setBorder(BorderFactory.createLineBorder(Color.gray, 10));
		splash.setVisible(true);
		try {
			for (int i = 5; i > 0; i--){
				message.setText("System Starting Please wait..." + "\t" + i);
				Thread.sleep(1000);
			}
		} catch (Exception e) {
		}
		splash.setVisible(false);
	}
}