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
		Mat source = Highgui.imread("1.png", Highgui.CV_LOAD_IMAGE_COLOR);
        Mat destination = new Mat(source.rows(), source.cols(), source.type());

        Imgproc.cvtColor(source, destination, Imgproc.COLOR_RGB2GRAY);

        Imgproc.GaussianBlur(destination, destination, new Size(3,3),0,0); 


        Mat circles = new Mat();
        Imgproc.HoughCircles(destination, circles, Imgproc.CV_HOUGH_GRADIENT, 
		         2.0, destination.rows() / 8d);
        System.out.println(circles.size());
        int radius;
        Point pt;
        for (int x = 0; x < circles.cols(); x++) {
        double vCircle[] = circles.get(0,x);

        if (vCircle == null)
            break;

        pt = new Point(Math.round(vCircle[0]), Math.round(vCircle[1]));
        radius = (int)Math.round(vCircle[2]);

        // draw the found circle
        Core.circle(destination, pt, 5, new Scalar(0,255,255), 3);
        Core.circle(destination, pt, 3, new Scalar(255,255,255), 3);
        }

        Highgui.imwrite("foundCircles.jpg", destination);
	}
}
