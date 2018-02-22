package fr.epita.iam.gui;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import fr.epita.iam.launcher.Global;
import fr.epita.iam.launcher.PrelaunchTests;
import fr.epita.logger.Logger;

/**
 * <h3>Description</h3>
 * <p>
 * Launcher of iam-core application. This application manages the users and
 * identities and all their necessary information. Data is stored in both
 * database and XML. However, if one of these 2 methods isn't working, the
 * program launches in read only mode.
 * </p>
 *
 * @author Samer Masaad
 *
 */
public class Main {

	private static final Logger LOGGER = new Logger(Main.class);

	/**
	 * <h3>Description</h3>
	 * <p>
	 * This method launches the program
	 * </p>
	 *
	 * <h3>Utilisation</h3>
	 * <p>
	 * The first (and only) argument is the file path to the configuration file. If
	 * not provided, the program will search the .jar file's directory for a file
	 * called "conf.properties". If not found, the program will close.
	 * </p>
	 * <p>
	 *
	 * <pre>
	 * <code>
	 * java -Dconf=${pathToConfigFile} -jar ${jarName}
	 *</code>
	 * </pre>
	 * </p>
	 *
	 * @author Samer Masaad
	 *
	 * @throws SQLException
	 * @throws IOException
	 * @throws URISyntaxException
	 */

	public static void main(String[] args) {

		PrelaunchTests tests = new PrelaunchTests();
		if (tests.run()) {
			// program can launch
			if (Global.isReadOnly()) {
				String infoMessage = "Running in read-only mode because ";
				infoMessage += !Global.isDBWorking() ? "JDBC connection " : "XML file parsing ";
				infoMessage += "failed!";
				LOGGER.warning(infoMessage);
				JOptionPane.showMessageDialog(null, infoMessage, "Running in read-only mode",
						JOptionPane.INFORMATION_MESSAGE);

			}
			Login.main();
		} else {
			// program can't start
			String errorMessage = "The program can't start. Check configuration file, as well as the database connection and XML file.";
			JOptionPane.showMessageDialog(null, errorMessage, "Starting failed", JOptionPane.ERROR_MESSAGE);
			return;
		}

	}

}
