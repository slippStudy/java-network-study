import java.io.*;

public class FileView {
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("사용법 : java FileView 파일명");
			System.exit(0);
		}

		FileInputStream fls = null;
		try {
			fls = new FileInputStream(args[0]);
			int i = 0;
			while((i = fls.read()) != -1) {
				System.out.write(i);
			}
		} catch (Exception ex) {
			System.out.println(ex);
		} finally {
			try {
				fls.close();
			} catch (IOException e) {}
		}
	}
}
