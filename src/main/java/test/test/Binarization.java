package test.test;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.KeyPoint;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class Binarization {
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		Mat src = new Mat();
		Mat work = new Mat();
		Mat grad = new Mat();
		src = Highgui.imread("4.jpg");

		int scale = 1;
		int delta = 1;
		int ddepth = CvType.CV_16S;

		Imgproc.cvtColor(src, work, Imgproc.COLOR_BGR2GRAY);

		Imgproc.threshold(work, work,150, 255, Imgproc.THRESH_BINARY_INV);

		Mat grad_x = new Mat();
		Mat grad_y = new Mat();
		Mat abs_grad_x = new Mat();
		Mat abs_grad_y = new Mat();

		// Imgproc.Sobel(work, grad_x, ddepth, 1, 0, 3, scale, delta,
		// Imgproc.BORDER_DEFAULT);
		// Core.convertScaleAbs(grad_x, abs_grad_x);
		//
		// Imgproc.Sobel(work, grad_y, ddepth, 0, 1, 3, scale, delta,
		// Imgproc.BORDER_DEFAULT);
		// Core.convertScaleAbs(grad_y, abs_grad_y);
		//
		// Core.addWeighted(abs_grad_x, 0.5, abs_grad_y, 0.5, 0, work);

		Mat skel = new Mat(work.size(), CvType.CV_8UC1, new Scalar(0));
		Mat temp = new Mat(work.size(), CvType.CV_8UC1);
		Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_CROSS, new Size(3, 3));
		boolean done = false;
		do {
			Imgproc.morphologyEx(work, temp, Imgproc.MORPH_OPEN, element);
			Core.bitwise_not(temp, temp);
			Core.bitwise_and(work, temp, temp);
			Core.bitwise_or(skel, temp, skel);
			Imgproc.erode(work, work, element);
			double max = 0;
			max = Core.minMaxLoc(work).maxVal;
			done = (max == 0);
		} while (!done);

		Highgui.imwrite("skel.jpg", skel);

		Mat se = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(6, 6));
		Imgproc.morphologyEx(skel, skel, Imgproc.MORPH_CLOSE, se);

		Highgui.imwrite("morph.jpg", skel);

	}
}
