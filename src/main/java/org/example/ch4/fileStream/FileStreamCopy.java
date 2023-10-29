import java.io.*;

public class FileStreamCopy {
	public static void main(String[] args) {
		if(args.length != 2) {
			System.out.println("사용법 : java FileStreamCopy 파일1 파일2");

			System.exit(0);
		}
		FileInputStream fis = null;
		FileOutputStream fos = null;

		try {
			fis = new FileInputStream(args[0]);
			fos = new FileOutputStream(args[1]);

			byte[] buffer = new byte[512];
			int readcount = 0;
			while((readcount = fis.read(buffer)) != -1) {
				fos.write(buffer, 0, readcount);
			}
			System.out.println("복사가 완료되었습니다.");
		} catch(Exception ex) {
			System.out.println(ex);			
		} finally {
			try {
				fis.close();
			} catch (IOException ex) {}
			try {
				fos.close();
			} catch (IOException ex) {}
		}
	}
}
