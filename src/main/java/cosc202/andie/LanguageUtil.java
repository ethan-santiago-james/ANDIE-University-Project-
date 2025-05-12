package cosc202.andie;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>
 * A class for managing persistent language settings. The settings are stored in
 * a Java properties file in a file named <code>location.properties</code> in
 * the root directory of the application/project.
 * </p>
 *
 * <p>
 * Feel free to use this class in your ANDIE project.
 * </p>
 *
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 *
 * @author Mark George
 * @version 1.0
 */
public class LanguageUtil {

	private static final Properties languageProperties = new Properties();
	private static ResourceBundle bundle;
        static{
            loadBundle(); //Makes sure bundle is loaded correctly.
        }
	/**
	 * <p>
	 * Gets a language bundle for the region/language stored in the
	 * language.properties file.
	 * </p>
	 *
	 * @return the bundle
	 */
	public static ResourceBundle getBundle() {
		return bundle;
	}

	/**
	 * Loads the region and language properties from the language.properties
	 * file, creates a locale using those values, and then loads the resource
	 * bundle for that locale.
	 */
	private static void loadBundle() {
		// load language properties from file
		try {
			languageProperties.load(new FileReader("location.properties")); // NOI18N
		} catch (IOException ex) {
			Logger.getLogger(LanguageUtil.class.getName()).log(Level.SEVERE, null, ex);
		}

		// get region and language from properties (using NZ and en as defaults if no properties exist)
		String region = languageProperties.getProperty("region", "NZ"); // NOI18N
		String language = languageProperties.getProperty("language", "en"); // NOI18N

		// create locale from region/language
		Locale locale = new Locale.Builder()
			 .setRegion(region)
			 .setLanguage(language)
			 .build();

		// load bundle for locale
		bundle = ResourceBundle.getBundle("bundle", locale); // NOI18N
	}

	/**
	 * <p>
	 * Store the passed region (ISO 639 code) and language (ISO 3166 two
	 * character code) in the language.properties file.
	 * </p>
	 *
	 * @param region
	 * @param language
	 */
	public static void setLanguage(String region, String language) {
		// store region/language in properties
		languageProperties.setProperty("region", region); // NOI18N
		languageProperties.setProperty("language", language); // NOI18N

		// write properties to file
		try {
			languageProperties.store(new FileWriter("location.properties"), null); // NOI18N
		} catch (IOException ex) {
			Logger.getLogger(LanguageUtil.class.getName()).log(Level.SEVERE, null, ex);
		}
                loadBundle();
	}

}
