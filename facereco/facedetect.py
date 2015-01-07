#!/usr/bin/python
"""
This program is demonstration for face and object detection using haar-like features.
The program finds faces in a camera image or video stream and displays a red box around them.

Original C implementation by:  ?
Python implementation by: Roman Stanchak, James Bowman
"""
import glob
import subprocess
import sys
import cv2.cv as cv
import cv2
from optparse import OptionParser

# Parameters for haar detection
# From the API:
# The default parameters (scale_factor=2, min_neighbors=3, flags=0) are tuned
# for accurate yet slow object detection. For a faster operation on real video
# images the settings are:
# scale_factor=1.2, min_neighbors=2, flags=CV_HAAR_DO_CANNY_PRUNING,
# min_size=<minimum possible face size

min_size = (50, 50)
image_scale = 2
haar_scale = 1.2
min_neighbors = 2
haar_flags = 0
idx_img = 0

def detect_and_draw(img, cascade, imgname):
    global idx_img
    # allocate temporary images
    gray = cv.CreateImage((img.width,img.height), 8, 1)
    small_img = cv.CreateImage((cv.Round(img.width / image_scale),
                   cv.Round (img.height / image_scale)), 8, 1)

    # convert color input image to grayscale
    cv.CvtColor(img, gray, cv.CV_BGR2GRAY)

    # scale input image for faster processing
    cv.Resize(gray, small_img, cv.CV_INTER_LINEAR)

    cv.EqualizeHist(small_img, small_img)

    if(cascade):
        faces = cv.HaarDetectObjects(small_img, cascade, cv.CreateMemStorage(0),
                                     haar_scale, min_neighbors, haar_flags, min_size)
        if faces:
            for ((x, y, w, h), n) in faces:
                # the input to cv.HaarDetectObjects was resized, so scale the
                # bounding box of each face and convert it to two CvPoints
                pt1 = (int(x * image_scale), int(y * image_scale))
                pt2 = (int((x + w) * image_scale), int((y + h) * image_scale))

                roi_color = img[pt1[1]:pt2[1], pt1[0]:pt2[0]]

                pathname = "faces/"+ imgname.split('/')[-1].split('.')[0] +"face"+str(idx_img)+".png"
                cv.SaveImage(pathname, roi_color)
                idx_img += 1

                for img_db in glob.glob("db/*"):
                    print img_db, pathname
                    ret = subprocess.call(["./a.out", pathname, img_db])
                    if not ret:
                        print "FOUND !"
                        break
                else:
                    print "NOT FOUND !"


                # cv.Rectangle(img, pt1, pt2, cv.RGB(255, 0, 0), 3, 8, 0)

                # eyes = cv.HaarDetectObjects(small_img, eye_cascade, cv.CreateMemStorage(0), haar_scale, min_neighbors, haar_flags, (10, 10))
                # for ((x, y, w, h), n) in eyes:
                #     pt1e = (int(x * image_scale), int(y * image_scale))
                #     pt2e = (int((x + w) * image_scale), int((y + h) * image_scale))
                #     cv.Rectangle(img, pt1e, pt2e, cv.RGB(0, 255, 0), 3, 8, 0)
                #
                # mouths = cv.HaarDetectObjects(small_img, mouth_cascade, cv.CreateMemStorage(0), haar_scale, min_neighbors, haar_flags, (10, 10))
                # for ((x, y, w, h), n) in mouths:
                #     pt1e = (int(x * image_scale), int(y * image_scale))
                #     pt2e = (int((x + w) * image_scale), int((y + h) * image_scale))
                #     cv.Rectangle(img, pt1e, pt2e, cv.RGB(0, 0, 255), 3, 8, 0)


    # cv.ShowImage("result", img)

if __name__ == '__main__':
    parser = OptionParser(usage = "usage: %prog [options] [filename|camera_index]")
    parser.add_option("-c", "--cascade", action="store", dest="cascade", type="str", help="Haar cascade file, default %default", default = "../data/haarcascades/haarcascade_frontalface_alt.xml")
    (options, args) = parser.parse_args()

    cascade = cv.Load("haarcascade_frontalface_default.xml")

    if len(args) != 1:
        parser.print_help()
        sys.exit(1)

    input_name = args[0]
    if input_name.isdigit():
        capture = cv.CreateCameraCapture(int(input_name))
    else:
        capture = None

    # cv.NamedWindow("result", 1)

    if capture:
        frame_copy = None
        while True:
            frame = cv.QueryFrame(capture)
            if not frame:
                cv.WaitKey(0)
                break
            if not frame_copy:
                frame_copy = cv.CreateImage((frame.width,frame.height),
                                            cv.IPL_DEPTH_8U, frame.nChannels)
            if frame.origin == cv.IPL_ORIGIN_TL:
                cv.Copy(frame, frame_copy)
            else:
                cv.Flip(frame, frame_copy, 0)

            detect_and_draw(frame_copy, cascade, input_name)

            if cv.WaitKey(10) >= 0:
                break
    else:
        image = cv.LoadImage(input_name, 1)
        detect_and_draw(image, cascade, input_name)
        # cv.WaitKey(0)

    # cv.DestroyWindow("result")
