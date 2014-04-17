package algorithms;

import java.util.ArrayList;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class Sobel {

	public Sobel() {
	}

	Sobel(String path) {
		Mat imageMat = Highgui.imread(path, Highgui.CV_LOAD_IMAGE_UNCHANGED);
		Imgproc.Sobel(imageMat, imageMat, CvType.CV_8U, 1, 1);
		String strsave = Helpers.pathExtension(path, "-sobel");
		Highgui.imwrite(strsave, imageMat);
	}

	/**
	 * src input image. dst output image of the same size and the same number of
	 * channels as src. ddepth output image depth; the following combinations of
	 * src.depth() and ddepth are supported: 
	 * src.depth() = CV_8U, ddepth = -1/CV_16S/CV_32F/CV_64F 
	 * src.depth() = CV_16U/CV_16S, ddepth = -1/CV_32F/CV_64F 
	 * src.depth() = CV_32F, ddepth = -1/CV_32F/CV_64F
	 * src.depth() = CV_64F, ddepth = -1/CV_64F when ddepth=-1, the destination
	 * image will have the same depth as the source; in the case of 8-bit input
	 * images it will result in truncated derivatives.
	 * 
	 * dx a dx dy a dy
	 * 
	 * @param pathList
	 * @return
	 */
	public String sobel(ArrayList<String> pathList, int ddepth) {
		String strsave = "";

		if (pathList.size() > 0) {
			for (String path : pathList) {
				Mat imageMat = Highgui.imread(path, Highgui.CV_LOAD_IMAGE_UNCHANGED);
				if (ddepth==1)Imgproc.Sobel(imageMat, imageMat, CvType.CV_8U, 1, 1);
				if (ddepth==2)Imgproc.Sobel(imageMat, imageMat, CvType.CV_16U, 1, 1);
				if (ddepth==3)Imgproc.Sobel(imageMat, imageMat, CvType.CV_32F, 1, 1);
				if (ddepth==4)Imgproc.Sobel(imageMat, imageMat, CvType.CV_64F, 1, 1);
				strsave = Helpers.pathExtension(path, "-sobel");
				Highgui.imwrite(strsave, imageMat);
			}
		}
		return strsave;
	}

}

// Pilna javine relizacija:
// auto formatas panaikino atitraukimus... :(
/*
 * int[] input; int[] output; float[] template={-1,0,1,-2,0,2,-1,0,1};; int
 * progress; int templateSize=3; int width; int height; double[] direction;
 * 
 * public void sobel() { progress=0; }
 * 
 * public void init(int[] original, int widthIn, int heightIn) { width=widthIn;
 * height=heightIn; input = new int[width*height]; output = new
 * int[width*height]; direction = new double[width*height]; input=original; }
 * public int[] process() { float[] GY = new float[width*height]; float[] GX =
 * new float[width*height]; int[] total = new int[width*height]; progress=0; int
 * sum=0; int max=0;
 * 
 * for(int x=(templateSize-1)/2; x<width-(templateSize+1)/2;x++) { progress++;
 * for(int y=(templateSize-1)/2; y<height-(templateSize+1)/2;y++) { sum=0;
 * 
 * for(int x1=0;x1<templateSize;x1++) { for(int y1=0;y1<templateSize;y1++) { int
 * x2 = (x-(templateSize-1)/2+x1); int y2 = (y-(templateSize-1)/2+y1); float
 * value = (input[y2*width+x2] & 0xff) * (template[y1*templateSize+x1]); sum +=
 * value; } } GY[y*width+x] = sum; for(int x1=0;x1<templateSize;x1++) { for(int
 * y1=0;y1<templateSize;y1++) { int x2 = (x-(templateSize-1)/2+x1); int y2 =
 * (y-(templateSize-1)/2+y1); float value = (input[y2*width+x2] & 0xff) *
 * (template[x1*templateSize+y1]); sum += value; } } GX[y*width+x] = sum;
 * 
 * } } for(int x=0; x<width;x++) { for(int y=0; y<height;y++) {
 * total[y*width+x]=
 * (int)Math.sqrt(GX[y*width+x]*GX[y*width+x]+GY[y*width+x]*GY[y*width+x]);
 * direction[y*width+x] = Math.atan2(GX[y*width+x],GY[y*width+x]);
 * if(max<total[y*width+x]) max=total[y*width+x]; } } float
 * ratio=(float)max/255; for(int x=0; x<width;x++) { for(int y=0; y<height;y++)
 * { sum=(int)(total[y*width+x]/ratio); output[y*width+x] = 0xff000000 |
 * ((int)sum << 16 | (int)sum << 8 | (int)sum); } } progress=width; return
 * output; }
 * 
 * public double[] getDirection() { return direction; } public int getProgress()
 * { return progress; }
 * 
 * }
 */