#include <cstdio>
#include <iostream>
#include "opencv2/core/core.hpp"
#include "opencv2/features2d/features2d.hpp"
#include "opencv2/highgui/highgui.hpp"
#include "opencv2/calib3d/calib3d.hpp"
#include "opencv2/nonfree/nonfree.hpp"
#include "opencv2/imgproc/imgproc.hpp"
#include "opencv2/video/background_segm.hpp"
#include "opencv2/legacy/legacy.hpp"

//"$(SolutionDir)$(Configuration)\images\testLum1face.png" "$(SolutionDir)$(Configuration)\images\testLum2face1.png"

using namespace cv;
using namespace std;

void readme();
float isSurfSimilar(Mat img1, Mat img2);
void testRecognition();


/** @function main */
int main(int argc, char** argv)
{
	if (argc != 3)
	{
		readme(); return -1;
	}

	Mat img_1 = imread(argv[1], CV_LOAD_IMAGE_GRAYSCALE);
	Mat img_2 = imread(argv[2], CV_LOAD_IMAGE_GRAYSCALE);

	if (!img_1.data || !img_2.data)
	{
		std::cout << " --(!) Error reading images " << std::endl; return -1;
	}

	BackgroundSubtractorMOG back;
	Mat img1, img2;
	//imshow("lol",back.getBackgroundImage(img1));

	resize(img_1, img1, Size(500, 500));
	resize(img_2, img2, Size(500, 500));


	//double max1, max2;
	//minMaxLoc(img_1, 0, &max1, 0, 0);
	//minMaxLoc(img_2, 0, &max2, 0, 0);
	//img_1.convertTo(img1, -1, 1, max1 - 255);
	//img_2.convertTo(img2, -1, 1, max2 - 255);
	//threshold(img_1, img1, 150, 0, THRESH_TRUNC);
	//threshold(img_2, img2, 150, 0, THRESH_TRUNC);

    int not_similar = 1;
	if (isSurfSimilar(img1, img2)
		|| isSurfSimilar(img2, img1)) {
        not_similar = 0;
		// cout << "The images are similar" << endl;
	}
	else {
		// cout << "The images are different" << endl;
	}

	// waitKey(0);
	return not_similar;
}


float isSurfSimilar(Mat img1, Mat img2)
{
	Mat img_object, img_scene;
	equalizeHist(img1, img_object);
	equalizeHist(img2, img_scene);

	//-- Step 1: Detect the keypoints using Surf Detector
	int minHessian = 400;



	//SurfFeatureDetector detector(minHessian);

	std::vector<KeyPoint> keypoints_object, keypoints_scene;

	//detector.detect(img_object, keypoints_object);
	//detector.detect(img_scene, keypoints_scene);

	////-- Step 2: Calculate descriptors (feature vectors)
	//SurfDescriptorExtractor extractor;

	Mat descriptors_object, descriptors_scene;

	//extractor.compute(img_object, keypoints_object, descriptors_object);
	//extractor.compute(img_scene, keypoints_scene, descriptors_scene);

	Mat mask;
	SURF surf(100);
	surf(img1, mask, keypoints_object, descriptors_object);
	surf(img2, mask, keypoints_scene, descriptors_scene);

	if (descriptors_object.type() != CV_32F)
		descriptors_object.convertTo(descriptors_object, CV_32F);
	if (descriptors_scene.type() != CV_32F)
		descriptors_scene.convertTo(descriptors_scene, CV_32F);

	//-- Step 3: Matching descriptor vectors using FLANN matcher
	FlannBasedMatcher matcher;
    // BruteForceMatcher<L2<float> > matcher;
	std::vector< DMatch > matches;
	matcher.match(descriptors_object, descriptors_scene, matches);

	double max_dist = 0; double min_dist = 500;

	//-- Quick calculation of max and min distances between keypoints
	for (int i = 0; i < descriptors_object.rows; i++)
	{
		double dist = matches[i].distance;
		if (dist < min_dist) min_dist = dist;
		if (dist > max_dist) max_dist = dist;
	}

	// printf("-- Max dist : %f \n", max_dist);
	// printf("-- Min dist : %f \n", min_dist);

	//-- Draw only "good" matches (i.e. whose distance is less than 3*min_dist )
	std::vector< DMatch > good_matches;

	for (int i = 0; i < descriptors_object.rows; i++)
	{
		if (matches[i].distance < 3 * min_dist)
		{
			good_matches.push_back(matches[i]);
		}
	}

	Mat img_matches;
	drawMatches(img_object, keypoints_object, img_scene, keypoints_scene,
		good_matches, img_matches, Scalar::all(-1), Scalar::all(-1),
		vector<char>(), DrawMatchesFlags::NOT_DRAW_SINGLE_POINTS);

	//-- Localize the object
	std::vector<Point2f> obj;
	std::vector<Point2f> scene;

	for (int i = 0; i < good_matches.size(); i++)
	{
		//-- Get the keypoints from the good matches
		obj.push_back(keypoints_object[good_matches[i].queryIdx].pt);
		scene.push_back(keypoints_scene[good_matches[i].trainIdx].pt);
	}

	int minPointNb = 4;
	float detThresh = 0.2;

	////-- Get the corners from the image_1 ( the object to be "detected" )
	//std::vector<Point2f> obj_corners(4);
	//obj_corners[0] = cvPoint(0, 0); obj_corners[1] = cvPoint(img_object.cols, 0);
	//obj_corners[2] = cvPoint(img_object.cols, img_object.rows); obj_corners[3] = cvPoint(0, img_object.rows);
	//std::vector<Point2f> scene_corners(4);

	//perspectiveTransform(obj_corners, scene_corners, H);

	////-- Draw lines between the corners (the mapped object in the scene - image_2 )
	//line(img_matches, scene_corners[0] + Point2f(img_object.cols, 0), scene_corners[1] + Point2f(img_object.cols, 0), Scalar(0, 255, 0), 4);
	//line(img_matches, scene_corners[1] + Point2f(img_object.cols, 0), scene_corners[2] + Point2f(img_object.cols, 0), Scalar(0, 255, 0), 4);
	//line(img_matches, scene_corners[2] + Point2f(img_object.cols, 0), scene_corners[3] + Point2f(img_object.cols, 0), Scalar(0, 255, 0), 4);
	//line(img_matches, scene_corners[3] + Point2f(img_object.cols, 0), scene_corners[0] + Point2f(img_object.cols, 0), Scalar(0, 255, 0), 4);

	//-- Show detected matches
	// imshow("Good Matches & Object detection", img_matches);

	if (max_dist > 0 && obj.size() >= minPointNb && scene.size() >= minPointNb) {
		Mat H = findHomography(obj, scene, CV_RANSAC);
		float det = determinant(H);
		// cout << endl << det << endl;

		if (abs(det) < detThresh) {
			return false;
		}
		else {
			return true;
		}
	}
	else {
		return false;
	}

}



/** @function readme */
void readme()
{
	std::cout << " Usage: ./Surf_descriptor <img1> <img2>" << std::endl;
}







///**
//* @file SURF_FlannMatcher
//* @brief SURF detector + descriptor + FLANN Matcher
//* @author A. Huaman
//*/
//
//#include <cstdio>
//#include <iostream>
//#include "opencv2/core/core.hpp"
//#include "opencv2/features2d/features2d.hpp"
//#include "opencv2/highgui/highgui.hpp"
//#include "opencv2/nonfree/features2d.hpp"
//#include "opencv2\nonfree\nonfree.hpp"
//#include "opencv2\imgproc\imgproc.hpp"
//
//using namespace cv;
//
//void readme();
//
///**
//* @function main
//* @brief Main function
//*/
//int main(int argc, char** argv)
//{
//	if (argc != 3)
//	{
//		readme(); return -1;
//	}
//
//	Mat img1 = imread(argv[1], CV_LOAD_IMAGE_GRAYSCALE);
//	Mat img2 = imread(argv[2], CV_LOAD_IMAGE_GRAYSCALE);
//	Mat img_1, img_2;
//
//	if (!img1.data || !img2.data)
//	{
//		std::cout << " --(!) Error reading images " << std::endl; return -1;
//	}
//
//	resize(img1, img_1, Size(), 0.5, 0.5);
//	resize(img2, img_2, Size(), 0.5, 0.5);
//
//	//-- Step 1: Detect the keypoints using SURF Detector
//	int minHessian = 400;
//
//	SurfFeatureDetector detector(minHessian);
//
//	std::vector<KeyPoint> keypoints_1, keypoints_2;
//
//	detector.detect(img_1, keypoints_1);
//	detector.detect(img_2, keypoints_2);
//
//	//-- Step 2: Calculate descriptors (feature vectors)
//	SurfDescriptorExtractor extractor;
//
//	Mat descriptors_1, descriptors_2;
//
//	extractor.compute(img_1, keypoints_1, descriptors_1);
//	extractor.compute(img_2, keypoints_2, descriptors_2);
//
//	//-- Step 3: Matching descriptor vectors using FLANN matcher
//	FlannBasedMatcher matcher;
//	std::vector< DMatch > matches;
//	matcher.match(descriptors_1, descriptors_2, matches);
//
//	double max_dist = 0; double min_dist = 100;
//
//	//-- Quick calculation of max and min distances between keypoints
//	for (int i = 0; i < descriptors_1.rows; i++)
//	{
//		double dist = matches[i].distance;
//		if (dist < min_dist) min_dist = dist;
//		if (dist > max_dist) max_dist = dist;
//	}
//
//	printf("-- Max dist : %f \n", max_dist);
//	printf("-- Min dist : %f \n", min_dist);
//
//	//-- Draw only "good" matches (i.e. whose distance is less than 2*min_dist,
//	//-- or a small arbitary value ( 0.02 ) in the event that min_dist is very
//	//-- small)
//	//-- PS.- radiusMatch can also be used here.
//	std::vector< DMatch > good_matches;
//
//	for (int i = 0; i < descriptors_1.rows; i++)
//	{
//		if (matches[i].distance <= max(2 * min_dist, 0.02))
//		{
//			good_matches.push_back(matches[i]);
//		}
//	}
//
//	//-- Draw only "good" matches
//	Mat img_matches;
//	drawMatches(img_1, keypoints_1, img_2, keypoints_2,
//		good_matches, img_matches, Scalar::all(-1), Scalar::all(-1),
//		vector<char>(), DrawMatchesFlags::NOT_DRAW_SINGLE_POINTS);
//
//	//-- Show detected matches
//	imshow("Good Matches", img_matches);
//
//	for (int i = 0; i < (int)good_matches.size(); i++)
//	{
//		printf("-- Good Match [%d] Keypoint 1: %d  -- Keypoint 2: %d  \n", i, good_matches[i].queryIdx, good_matches[i].trainIdx);
//	}
//
	// waitKey(0);
//
//	return 0;
//}
//
///**
//* @function readme
//*/
//void readme()
//{
//	std::cout << " Usage: ./SURF_FlannMatcher <img1> <img2>" << std::endl;
//}







//#include <opencv2\core\core.hpp>
//#include <opencv2/highgui/highgui.hpp>
//#include <iostream>
//
//using namespace cv;
//using namespace std;
//
//int main(int argc, char** argv)
//{
//	if (argc != 2)
//	{
//		cout << " Usage: display_image ImageToLoadAndDisplay" << endl;
//		return -1;
//	}
//
//	Mat image;
//	image = imread(argv[1], IMREAD_COLOR); // Read the file
//
//	if (!image.data) // Check for invalid input
//	{
//		cout << "Could not open or find the image" << std::endl;
//		return -1;
//	}
//
//	namedWindow("Display window", WINDOW_AUTOSIZE); // Create a window for display.
//	imshow("Display window", image); // Show our image inside it.
//
//	waitKey(0); // Wait for a keystroke in the window
//	return 0;
//}
