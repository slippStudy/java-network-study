import java.io.*;

public class FileView2 {
	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("사용법 : java FileView2 파일명");
			System.exit(0);
		}

		FileInputStream fls = null;
		try {
			fls = new FileInputStream(args[0]);
			int readcount = 0;
			byte[] buffer = new byte[512];
			while((readcount = fls.read(buffer)) != -1) {
				System.out.write(buffer, 0, readcount);
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
