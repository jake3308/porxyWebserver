import java.io.*;

public class HttpRequest {
    final static String CRLF = "\r\n";
    final static int HTTP_PORT = 8889;

    String method;
    String URI;
    String version;
    String headers = "";
    private String host;
    private String port;

    // 클라이언트 소켓으로부터 요청을 읽어 생성
    public HttpRequest(BufferedReader from) {
        try {
            // 첫 번째 줄 읽기: 메서드, URI, 버전
            String firstLine = from.readLine();
            String[] parts = firstLine.split(" ");
            method = parts[0];
            URI = parts[1];
            version = parts[2];
            System.out.println("URI is: " + URI);

            if (!method.equals("GET")) {
                System.out.println("Error: Method not GET");
            }

            // URI가 절대경로 형식일 경우 호스트 정보 설정
            if (URI.startsWith("/www.")) {
                host = URI.substring(1);  // '/' 제거 후 www.naver.com처럼 호스트만 추출
                URI = "http://" + host;   // 요청 URI 수정
            }

            // URI에서 호스트 정보 파싱
            if (URI.startsWith("http://")) {
                URI = URI.substring(7);  // "http://" 제거
                int idx = URI.indexOf('/');
                if (idx != -1) {
                    host = URI.substring(0, idx);  // 원본 호스트 이름
                    URI = URI.substring(idx);      // URI의 나머지 부분
                    String [] tmp = host.split(":");
                    host = tmp[0];
                    port = tmp[1];
                } else {
                    host = URI;
                    URI = "/";
                }
            }

            System.out.println("Host to contact is: " + host + " at port " + port);
        } catch (IOException e) {
            System.out.println("Error reading request line: " + e);
        }
    }

    // 호스트 이름을 반환
    public String getHost() {
        return host;
    }

    // 포트 번호를 반환
    public String getPort() {
        return port;
    }

    // 요청을 문자열로 변환하여 다시 전송할 수 있도록 합니다.
    public String toString() {
        // 강제로 원본 서버의 호스트 이름으로 설정
        String req = method + " " + URI + " " + version + CRLF + "Host: " + host + CRLF + headers + "Connection: close" + CRLF + CRLF;
        return req;
    }
}
