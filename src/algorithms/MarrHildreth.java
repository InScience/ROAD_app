package algorithms;

import java.util.ArrayList;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class MarrHildreth {

	
	public MarrHildreth(){
		
	}

	public String marrHildreth(ArrayList<String> pathList) {
		String strsave = "";
		if (pathList.size() > 0) {
			for (String path : pathList) {
				Mat imageMat = Highgui.imread(path, Highgui.CV_LOAD_IMAGE_GRAYSCALE);
				Size ksize = new Size(3, 3);
				Imgproc.GaussianBlur(imageMat, imageMat, ksize, 255, 0);//( src, src, Size(3,3), 0, 0, BORDER_DEFAULT );
				//Imgproc.cvtColor( imageMat, imageMat, Imgproc.COLOR_BGR2GRAY);  //CvType.CV_8U);
				
				Imgproc.Laplacian(imageMat, imageMat, CvType.CV_8U, 1, 5, 0);
				//Imgproc.cvtColor(imageMat, imageMat, 0);
				strsave = Helpers.pathExtension(path, "-MarrHildreth");
				Highgui.imwrite(strsave, imageMat);
			}
		}
		return strsave;
	}	
}
