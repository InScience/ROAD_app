package algorithms;

import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class Otsu {

	public Otsu() {

	}

	public Otsu(String path) {
		Mat imageMat = Highgui.imread(path, Highgui.CV_LOAD_IMAGE_GRAYSCALE);
		Imgproc.threshold(imageMat, imageMat, 125, 255, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);
		String strsave = Helpers.pathExtension(path, "-otsu");
		Highgui.imwrite(strsave, imageMat);

	}

	public String otsu(ArrayList<String> pathList) {
		String strsave = "";
		if (pathList.size() > 0) {
			for (String path : pathList) {
				Mat imageMat = Highgui.imread(path, Highgui.CV_LOAD_IMAGE_GRAYSCALE);
				Imgproc.threshold(imageMat, imageMat, 125, 255, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);
				strsave = Helpers.pathExtension(path, "-otsu");
				Highgui.imwrite(strsave, imageMat);
			}
		}
		return strsave;
	}

}
