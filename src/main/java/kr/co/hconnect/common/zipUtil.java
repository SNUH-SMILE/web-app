package kr.co.hconnect.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class zipUtil {
    /**
     * 압축풀기 메소드
     *
     * @param zipFileName 압축파일
     */
    public boolean unZip(String zipPath, String zipFileName, String zipUnzipPath) {

        // 파일 정상적으로 압축이 해제가 되어는가.
        boolean isChk = false;

        // 해제할 홀더 위치를 재조정
        zipUnzipPath = zipUnzipPath + zipFileName.replace(".zip", "");

        // zip 파일
        File zipFile = new File(zipPath + zipFileName);

        FileInputStream fis = null;
        ZipInputStream zis = null;
        ZipEntry zipentry = null;

        try {
            // zipFileName을 통해서 폴더 만들기
            if (makeFolder(zipUnzipPath)) {
                System.out.println("폴더를 생성했습니다");
            }

            // 파일 스트림
            fis = new FileInputStream(zipFile);

            // Zip 파일 스트림
            zis = new ZipInputStream(fis, Charset.forName("EUC-KR"));

            // 압축되어 있는 ZIP 파일의 목록 조회
            while ((zipentry = zis.getNextEntry()) != null) {
                String filename = zipentry.getName();
                System.out.println("filename(zipentry.getName()) => " + filename);
                File file = new File(zipUnzipPath, filename);

                // entiry가 폴더면 폴더 생성
                if (zipentry.isDirectory()) {
                    System.out.println("zipentry가 디렉토리입니다.");
                    file.mkdirs();
                } else {
                    // 파일이면 파일 만들기
                    System.out.println("zipentry가 파일입니다.");
                    try {
                        createFile(file, zis);
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
            }
            isChk = true;
        } catch (Exception e) {
            isChk = false;
        } finally {
            if (zis != null) {
                try {
                    zis.close();
                } catch (IOException e) {
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                }
            }
        }
        return isChk;
    }

    /**
     * @param folder - 생성할 폴더 경로와 이름
     */
    private boolean makeFolder(String folder) {
        if (folder.length() < 0) {
            return false;
        }

        String path = folder; // 폴더 경로
        File Folder = new File(path);

        // 해당 디렉토리가 없을경우 디렉토리를 생성합니다.
        if (!Folder.exists()) {
            try {
                Folder.mkdir(); // 폴더 생성합니다.
                System.out.println("폴더가 생성되었습니다.");
            } catch (Exception e) {
                e.getStackTrace();
            }
        } else {
            System.out.println("이미 폴더가 생성되어 있습니다.");
        }
        return true;
    }

    /**
     * 파일 만들기 메소드
     *
     * @param file 파일
     * @param zis  Zip스트림
     */
    private void createFile(File file, ZipInputStream zis) throws Throwable {

        // 디렉토리 확인
        File parentDir = new File(file.getParent());
        // 디렉토리가 없으면 생성하자
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }
        FileOutputStream fos = null;
        // 파일 스트림 선언
        try {

            fos = new FileOutputStream(file);
            byte[] buffer = new byte[256];
            int size = 0;
            // Zip스트림으로부터 byte뽑아내기
            while ((size = zis.read(buffer)) > 0) {
                // byte로 파일 만들기
                fos.write(buffer, 0, size);
            }
        } catch (Throwable e) {
            throw e;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                }

            }

        }

    }
}
