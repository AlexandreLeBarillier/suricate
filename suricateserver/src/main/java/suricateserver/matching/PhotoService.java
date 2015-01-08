package suricateserver.matching;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;

import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import suricateserver.dto.PhotoRequest;

public class PhotoService {

	public static String savePhoto(InputStream request) throws IOException {
//		StringWriter writer = new StringWriter();
//		IOUtils.copy(request, writer);
//		byte[] data = Base64.decodeBase64(request.);
//		try (OutputStream stream = new FileOutputStream("./alexandre/photo.jpeg")) {
//		    stream.write(data);
//		}
		return "./alexandre/photo.jpeg";
	}
	
	public static boolean performMatching(String path) {
		return true;
	}
}
