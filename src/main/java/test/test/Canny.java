package test.test;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class Canny {
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat src = Highgui.imread("4.jpg");
		Mat gaus = new Mat();
		Mat gray = new Mat();
		Mat canny = new Mat();
		Mat thr = new Mat();


		Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
		Imgproc.GaussianBlur(gray, gaus, new Size(3, 3), 4);

//		Imgproc.threshold(gaus, thr, 137, 255, Imgproc.THRESH_BINARY_INV);//1.png
//		Imgproc.threshold(gaus, thr, 10, 255, Imgproc.THRESH_BINARY_INV);//2.jpg
		Imgproc.threshold(gaus, thr, 120, 255, Imgproc.THRESH_BINARY);//4.jpg
		Highgui.imwrite("thr.jpg", thr);
		Imgproc.Canny(thr, canny, 30, 150);
		Highgui.imwrite("canny.jpg", canny);

		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		Imgproc.findContours(canny, contours, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
		
//		Imgproc.drawContours(src, contours, -1, new Scalar(0, 0, 255));
		for(int i=0; i< contours.size();i++){
	        if (Imgproc.contourArea(contours.get(i)) > 100 /*&& Imgproc.contourArea(contours.get(i)) < 25*/){
	            Rect rect = Imgproc.boundingRect(contours.get(i));
	            	Core.rectangle(src, new Point(rect.x,rect.y), new Point(rect.x+rect.width,rect.y+rect.height),new Scalar(0,0,255));
	        }
	    }
		Highgui.imwrite("cont.jpg", src);
		
	}
}
