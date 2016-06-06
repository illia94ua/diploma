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

public class Sobel {
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		Mat src, src_gray = new Mat();
		Mat grad = new Mat();

		int scale = 10;
		int delta = 1;
		int ddepth = CvType.CV_16S;

		src = Highgui.imread("4.jpg");

		Imgproc.GaussianBlur(src, src, new Size(3, 3), 0, 0, Imgproc.BORDER_DEFAULT);
		Imgproc.cvtColor(src, src_gray, Imgproc.COLOR_BGR2GRAY);

		Mat grad_x = new Mat();
		Mat grad_y = new Mat();
		Mat abs_grad_x = new Mat();
		Mat abs_grad_y = new Mat();

		Imgproc.Sobel(src_gray, grad_x, ddepth, 1, 0, 3, scale, delta, Imgproc.BORDER_DEFAULT);
		Core.convertScaleAbs(grad_x, abs_grad_x);

		Imgproc.Sobel(src_gray, grad_y, ddepth, 0, 1, 3, scale, delta, Imgproc.BORDER_DEFAULT);
		Core.convertScaleAbs(grad_y, abs_grad_y);

		Core.addWeighted(abs_grad_x, 0.5, abs_grad_y, 0.5, 0, grad);

		Imgproc.threshold(grad, grad, 180, 255, Imgproc.THRESH_BINARY_INV);

		Mat se = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(1, 1));
		Imgproc.morphologyEx(grad, grad, Imgproc.MORPH_CLOSE, se);


		Highgui.imwrite("sobel.png", grad);

		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		Imgproc.findContours(grad, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
		// Imgproc.drawContours(imageBlurr, contours, 1, new Scalar(0,0,255));
		for (int i = 0; i < contours.size(); i++) {
			if (Imgproc.contourArea(contours.get(i)) > 50) {
				Rect rect = Imgproc.boundingRect(contours.get(i));
				if (rect.height > 28) {
					Core.rectangle(src, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
							new Scalar(0, 0, 255));
				}
			}
		}

		Highgui.imwrite("sobel-contours.png", src);

	}
}
