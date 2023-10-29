import java.io.*;

public class TempFile {
	public static void main(String[] args) {
		try {
			File f = File.createTempFile("tmp_", ".dat", new File("./"));
			System.out.println("60초 동안 멈춰있습니다.");
			try {
				Thread.sleep(60000);
			} catch (InterruptedException e1) {
				System.out.println(e1);
			}
			f.deleteOnExit();
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}
