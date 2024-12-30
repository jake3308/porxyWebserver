import java.io.*;

public class HttpResponse {
    final static String CRLF = "\r\n";
    final static int BUF_SIZE = 8192;
    final static int MAX_OBJECT_SIZE = 100000;

    String statusLine = "";
    String headers = "";
    byte[] body = new byte[MAX_OBJECT_SIZE];

    // 서버 응답의 상태와 헤더를 읽어옵니다.
    public HttpResponse(DataInputStream fromServer) {
        int length = -1;
        boolean gotStatusLine = false;

        try {
            // 상태 라인과 헤더 읽기
            String line = fromServer.readLine();
            while (line.length() != 0) {
                if (!gotStatusLine) {
                    statusLine = line;
                    gotStatusLine = true;
                } else {
                    headers += line + CRLF;
                }

                // Content-Length 헤더에서 응답의 길이를 파악합니다.
                if (line.startsWith("Content-Length") || line.startsWith("content-length")) {
                    String[] tmp = line.split(" ");
                    length = Integer.parseInt(tmp[1]);
                }

                line = fromServer.readLine();
            }

            // 본문(body) 읽기
            int bytesRead = 0;
            byte[] buffer = new byte[BUF_SIZE];
            boolean loop = length == -1;  // Content-Length가 없는 경우

            while (bytesRead < length || loop) {
                int res = fromServer.read(buffer);
                if (res == -1) break;
                
                for (int i = 0; i < res && (i + bytesRead) < MAX_OBJECT_SIZE; i++) {
                    body[bytesRead + i] = buffer[i];
                }
                bytesRead += res;
            }
        } catch (IOException e) {
            System.out.println("Error reading response body: " + e);
        }
    }

    // 응답을 문자열 형태로 반환 (본문 제외)
    public String toString() {
        String res = statusLine + CRLF + headers + CRLF;
        return res;
    }
}
