package file;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileHelper {
	static public void createBigFile() {
		try (RandomAccessFile f = new RandomAccessFile("t", "rw")) {
			f.setLength(1073741824);;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static public byte[] readBigFile(String pathUrl) {
		Path path = Paths.get(pathUrl);
		byte[] data = null;
		try {
			data = Files.readAllBytes(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;

	}
}
