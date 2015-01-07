package suricateserver.matching;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.tomcat.util.codec.binary.Base64;

import suricateserver.dto.PhotoRequest;

public class PhotoService {

	public static String savePhoto(PhotoRequest request) throws IOException {
		byte[] data = Base64.decodeBase64(request.getPhoto());
		try (OutputStream stream = new FileOutputStream("./alexandre/photo.jpeg")) {
		    stream.write(data);
		}
		return "./alexandre/photo.jpeg";
	}
	
	public static boolean performMatching(String path) {
		return true;
	}
}
