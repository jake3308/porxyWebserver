import java.net.*;
import java.io.*;

public class ProxyCache {
    private static int port;
    private static ServerSocket socket;

    public static void init(int p) {
        port = p;
        try {
            socket = new ServerSocket(port);
            System.out.println("프록시 서버가 포트 " + port + "에서 실행 중입니다.");
        } catch (IOException e) {
            System.out.println("소켓 생성 오류: " + e.getMessage());
            System.exit(-1);
        }
    }

    public static void handle(Socket client) {
        Socket server = null;
        HttpRequest request = null;
        HttpResponse response = null;

        try {
            // 클라이언트로부터 요청 읽기
            BufferedReader fromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
            request = new HttpRequest(fromClient); // 클라이언트 요청 읽기
            System.out.println("클라이언트 요청 수신: " + request);

            // **WebServer의 고정 IP와 포트 설정**
            String webServerHost = request.getHost(); // WebServer IP (고정)
            int webServerPort = Integer.parseInt(request.getPort());             // WebServer 포트 (고정)//curl http://192.168.0.6:8888/2023072706.html

            server = new Socket(webServerHost, webServerPort); // WebServer로 연결

            // WebServer로 요청 전송
            DataOutputStream toServer = new DataOutputStream(server.getOutputStream());
            toServer.writeBytes(request.toString());
            System.out.println("WebServer로 요청 전송 완료: " + webServerHost + ":" + webServerPort);

            // WebServer로부터 응답 읽기
            DataInputStream fromServer = new DataInputStream(server.getInputStream());
            response = new HttpResponse(fromServer);
            System.out.println("WebServer 응답 수신: " + response.statusLine);

            // 클라이언트로 응답 전달
            DataOutputStream toClient = new DataOutputStream(client.getOutputStream());
            toClient.writeBytes(response.toString());  // 응답 헤더 전송
            toClient.write(response.body, 0, response.body.length);  // 응답 본문 전송
            toClient.flush();
            System.out.println("클라이언트로 응답 전송 완료.");
        } catch (IOException e) {
            System.out.println("요청 처리 오류: " + e.getMessage());
        } finally {
            try {
                if (client != null) client.close();
                if (server != null) server.close();
            } catch (IOException e) {
                System.out.println("소켓 닫기 오류: " + e.getMessage());
            }
        }
    }


    public static void main(String[] args) {
        int proxyPort = 8889; // ProxyCache 포트
        init(proxyPort); // ProxyCache 초기화

        while (true) {
            try {
                Socket client = socket.accept();
                System.out.println("클라이언트 연결 수락.");
                handle(client);
            } catch (IOException e) {
                System.out.println("클라이언트 연결 수락 오류: " + e.getMessage());
            }
        }
    }
}
