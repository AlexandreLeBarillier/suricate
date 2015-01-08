package suricateserver.matching;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringWriter;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import suricateserver.dao.GuestDAO;
import suricateserver.dto.PhotoRequest;


public class PhotoService {

	public static String savePhoto(PhotoRequest request) throws Exception {

		String photoname = GuestDAO.getGuestNameByCode(request.getCode());
		String filename = "c://facereco/" + photoname + ".jpeg";
		
		byte[] data = Base64.decodeBase64(request.getPhoto());
		OutputStream stream = new FileOutputStream(filename);
		try {
		    stream.write(data);
		} finally {
			stream.close();
		}
		
		return filename;
	}
	
	public static boolean performMatching(String path) throws Exception {
		Process proc = Runtime.getRuntime().exec("python c:\\facereco\\facedetect.py " + path);
		proc.waitFor();
		System.out.println("\n\nexit value : " + proc.exitValue());
		StringWriter writer = new StringWriter();
		IOUtils.copy(proc.getErrorStream(), writer);
		System.out.println("\n\noutputstream : " + writer.toString());
		if(proc.exitValue() == 0) {
			return true;
		}
		return false;
	}
}
