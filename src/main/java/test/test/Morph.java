package test.test;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
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

public class Morph {
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		Mat src = Highgui.imread("sample.jpg");
		Mat src_gray = new Mat();
		Mat dst = new Mat();
		Mat detected_edges = new Mat();

		dst.create(src.size(), src.type());
		Imgproc.cvtColor(src, src_gray, Imgproc.COLOR_BGR2GRAY);
		Imgproc.blur(src_gray, detected_edges, new Size(3, 3));
		Imgproc.Canny(detected_edges, detected_edges, 100, 50);
		
		double erosion_size = 2;
		Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE,
				new Size(5, 5), new Point(1, 1));
		Imgproc.morphologyEx(detected_edges, dst, Imgproc.MORPH_CLOSE, element);
		

		Highgui.imwrite("test1.png", dst);
		
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		Imgproc.findContours(dst, contours, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
		// Imgproc.drawContours(imageBlurr, contours, 1, new Scalar(0,0,255));
		for (int i = 0; i < contours.size(); i++) {
			if (Imgproc.contourArea(contours.get(i)) > 50) {
				Rect rect = Imgproc.boundingRect(contours.get(i));
				if (rect.height > 28) {
					// System.out.println(rect.x
					// +","+rect.y+","+rect.height+","+rect.width);
					Core.rectangle(src, new Point(rect.x, rect.y),
							new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 0, 255));
				}
			}
		}
		Highgui.imwrite("test2.png", src);

	}
}
