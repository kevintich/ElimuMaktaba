package elimu_maktabaLogin;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import com.mysql.jdbc.SQLError;

import elimu_maktabaAdminScreen.*;
import elimu_maktabaLibrarianScreen.LibraryGui;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.skin.SubstanceAutumnLookAndFeel;
import org.jvnet.substance.skin.SubstanceBusinessLookAndFeel;
import org.jvnet.substance.skin.SubstanceChallengerDeepLookAndFeel;
import org.jvnet.substance.skin.SubstanceCremeLookAndFeel;
import org.jvnet.substance.skin.SubstanceEmeraldDuskLookAndFeel;
import org.jvnet.substance.skin.SubstanceFieldOfWheatLookAndFeel;
import org.jvnet.substance.skin.SubstanceOfficeBlue2007LookAndFeel;
import org.jvnet.substance.skin.SubstanceOfficeSilver2007LookAndFeel;
import org.jvnet.substance.skin.SubstanceSaharaLookAndFeel;
import org.jvnet.substance.theme.SubstanceAquaTheme;
import org.jvnet.substance.theme.SubstanceDesertSandTheme;

public class Login_BLogic implements FromGUItoMainClass {
	LoginUI loginUserInterface;
	LoginDatabaseClass databaseClass;

	public Login_BLogic() {

		Splash t = new Splash();
		t.showSplash(10000);
		
		databaseClass = new LoginDatabaseClass();

		loginUserInterface = new LoginUI(this);

	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new SubstanceChallengerDeepLookAndFeel());
		} catch (Exception e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		new Login_BLogic();
	}

	@Override
	public void getLoginDetailsFromGUI(String staffID, String password) {
		// TODO Auto-generated method stub

		if (databaseClass.verifyLoginDetails(staffID, password) == true) {
			loginUserInterface.dispose();
			if (staffID.startsWith("1")) {
				new AdminMainFrame(databaseClass.getInstance());
			} else if (staffID.startsWith("2")) {
				new LibraryGui(databaseClass.getInstance());
			}
		} else {
			loginUserInterface.failedLogin();

		}
	}

}
