package algorithms;

import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

/**
 * openCV Canny klase Finds edges in an image using the [Canny86] algorithm.
 * 
 * @author Gediminas Balkys
 * 
 */
public class Canny {
	String filename = "";

	public Canny() {

	}

	/**
	 * Paduodamas kelias ir slenksciai. Rezultatas irasomas i ta pacia vieta,
	 * kur yra originalus failas pridedant gale: "-canny"
	 * 
	 * @param path
	 *            - to file
	 * @param threshold1
	 * @param threshold2
	 */
	public Canny(String path, int threshold1, int threshold2) {
		Mat imageMat = Highgui.imread(path, Highgui.CV_LOAD_IMAGE_GRAYSCALE);
		Imgproc.Canny(imageMat, imageMat, threshold1, threshold2);
		String strsave = Helpers.pathExtension(path, "-canny" + "-" + threshold1 + "-" + threshold2);
		Highgui.imwrite(strsave, imageMat);
	}

	public Canny(String path, int threshold1, int threshold2, int apertureSize, boolean L2gradient) {
		Mat imageMat = Highgui.imread(path, Highgui.CV_LOAD_IMAGE_GRAYSCALE);
		Imgproc.Canny(imageMat, imageMat, threshold1, threshold2, apertureSize, L2gradient);
		String strsave = Helpers.pathExtension(path, "-canny" + "-" + threshold1 + "-" + threshold2 + "-" + apertureSize + "-" + L2gradient);
		Highgui.imwrite(strsave, imageMat);
	}

	/**
	 * Paduodamas kelias ir slenksciai. Rezultatas irasomas i ta pacia vieta,
	 * kur yra originalus failas pridedant gale: "-canny" ir kiti parametrai
	 * 
	 * @param pathList
	 *            to image – single-channel 8-bit input image.
	 * @param threshold1
	 *            - first threshold for the hysteresis procedure.
	 * @param threshold2
	 *            - second threshold for the hysteresis procedure.
	 * @return kelias iki naujo failo
	 */
	public String canny(ArrayList<String> pathList, int threshold1, int threshold2) {
		String strsave = "";
		if (pathList.size() > 0) {
			for (String path : pathList) {
				Mat imageMat = Highgui.imread(path, Highgui.CV_LOAD_IMAGE_GRAYSCALE);
				Imgproc.Canny(imageMat, imageMat, threshold1, threshold2);
				strsave = Helpers.pathExtension(path, "-canny" + "-" + threshold1 + "-" + threshold2);
				Highgui.imwrite(strsave, imageMat);
			}
		}
		return strsave;
	}

	/**
	 * Paduodamas kelias ir slenksciai. Rezultatas irasomas i ta pacia vieta,
	 * kur yra originalus failas pridedant gale: "-canny"
	 * 
	 * @param pathList
	 *            to image list – single-channel 8-bit input image.
	 * @param threshold1
	 *            - first threshold for the hysteresis procedure.
	 * @param threshold2
	 *            - second threshold for the hysteresis procedure.
	 * @param apertureSize
	 *            - aperture size for the Sobel() operator.
	 * @param L2gradient
	 *            - a flag, indicating whether a more accurate norm should be
	 *            used to calculate the image gradient magnitude (
	 *            L2gradient=true ), or whether the default norm is enough (
	 *            L2gradient=false ).
	 * @return kelias iki naujo failo
	 */
	public String canny(ArrayList<String> pathList, int threshold1, int threshold2, int apertureSize, boolean L2gradient) {
		String strsave = "";
		if (pathList.size() > 0) {
			for (String path : pathList) {
				Mat imageMat = Highgui.imread(path, Highgui.CV_LOAD_IMAGE_GRAYSCALE);
				Imgproc.Canny(imageMat, imageMat, threshold1, threshold2, apertureSize, L2gradient);
				strsave = Helpers.pathExtension(path, "-canny" + "-" + threshold1 + "-" + threshold2 + "-" + apertureSize + "-" + L2gradient);
				Highgui.imwrite(strsave, imageMat);
			}
		}
		return strsave;

	}

	

}