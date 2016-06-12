package test.test;

import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.KeyPoint;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class App {

	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		String sourcePath = "1.png";

		Mat srcImgMat = Highgui.imread(sourcePath);

		MatOfKeyPoint matOfKeyPoints = new MatOfKeyPoint();

		FeatureDetector blobDetector = FeatureDetector.create(FeatureDetector.SIMPLEBLOB);
		blobDetector.detect(srcImgMat, matOfKeyPoints);

		System.out.println("Detected " + matOfKeyPoints.size() + " blobs in the image");

		List<KeyPoint> keyPoints = matOfKeyPoints.toList();

		for (KeyPoint rect : keyPoints) {
			Core.circle(srcImgMat, new Point(rect.pt.x, rect.pt.y), 20, new Scalar(0, 255, 0));
		}

		String filename = "blobs.png";
		System.out.println(String.format("Writing %s", filename));
		Highgui.imwrite(filename, srcImgMat);

	}
}
