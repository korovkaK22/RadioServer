package server.core;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class ClientHandler {
    private final Socket socket;
    private OutputStream outputStream;

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.outputStream = socket.getOutputStream();
    }

    public void sendData(byte[] buffer, int count) throws IOException {
        outputStream.write(buffer, 0, count);
    }

    public void closeConnection() {
        try {
            if (outputStream != null) {
                outputStream.close();
                outputStream = null;
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}