package test.test;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class HoughCircle {
	public static void main(String args[]) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat source = Highgui.imread("canny.jpg", Highgui.CV_LOAD_IMAGE_GRAYSCALE);

		Mat circles = new Mat();
		Imgproc.HoughCircles(source, circles, Imgproc.CV_HOUGH_GRADIENT, 30, 100);
		System.out.println(circles.size());
		int radius;
		Point pt;
		for (int x = 0; x < circles.cols(); x++) {
			double vCircle[] = circles.get(0, x);

			if (vCircle == null)
				break;

			pt = new Point(Math.round(vCircle[0]), Math.round(vCircle[1]));
			radius = (int) Math.round(vCircle[2]);

			// draw the found circle
			Core.circle(source, pt, 3, new Scalar(255, 255, 255), 1);
		}

		Highgui.imwrite("foundCircles.jpg", source);
	}
}
