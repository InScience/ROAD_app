package algorithms;

import java.util.Date;

public class Helpers {

	
	public Helpers(){
		
	}
	
	/**
	 * Static metodas. Prideda prie failo galo paduota pletini, o jei nera pletinio
	 * prideda data.
	 * 
	 * @param path
	 * @param extension
	 *            - pridedamas pletinys
	 * @return
	 */
	public static String pathExtension(String path, String extension) {
		String strsave = "";
		if (extension.equals("")) {
			extension = new Date().toString();
		}
		if ((path.subSequence(path.length() - 4, path.length() - 3)).equals(".")) {
			strsave = path.substring(0, path.length() - 4) + extension + path.substring(path.length() - 4, path.length());
		}
		if ((path.subSequence(path.length() - 5, path.length() - 4)).equals(".")) {
			strsave = path.substring(0, path.length() - 4) + extension + path.substring(path.length() - 4, path.length());
		}
		return strsave;
	}
}
