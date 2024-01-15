package org.example.len.ch15_selector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Iterator;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

class SimpleChatClient {

    public static final String HOST = "localhost";
    public static final int PORT = 9090;

    private static FileHandler fileHandler;
    private static Logger logger = Logger.getLogger("org.example.len.ch15_selector");

    private Selector selector = null;
    private SocketChannel sc = null;

    private Charset charset = null;
    private CharsetDecoder decoder = null;

    public SimpleChatClient() {
        charset = Charset.forName("EUC-KR");
        decoder = charset.newDecoder();
    }

    public void initServer() {
        try {
            // 셀렉터 연다
            selector = Selector.open();

            // 소켓채널 생성
            sc = SocketChannel.open(new InetSocketAddress(HOST, PORT));

            // 비블록킹 모드 설정
            sc.configureBlocking(false);

            // 서버 소켓채널을 셀렉터에 등록
            sc.register(selector, SelectionKey.OP_READ);

        } catch (IOException e) {
            log(Level.WARNING, "initServer", e);
        }
    }

    public void startServer() {
        startWriter();
        startReader();
    }


    private void startWriter() {
        info("Writer is started..");
        Thread t = new MyThread(sc);
        t.start();
    }

    private void startReader() {
        info("Reader is started..");
        try {


            while (true) {
                info("요청을 기라니느중 ..");
                selector.select();

                //셀렉터의 SelectedSet에 저장된 준비된 이벤트들을 하나씩 처리
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isReadable()) {
                        read(key);
                    }
                    iterator.remove();
                }
            }
        } catch (Exception e) {
            log(Level.WARNING, "startServer(,", e);
        }
    }

    private void read(SelectionKey key) {
        // SelectionKey로부터 소켓채널을 얻어온다
        SocketChannel sc = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        int read = 0;
        try {
            read = sc.read(buffer);
            info(read + " byte를 읽었습니다");
        } catch (IOException e) {
            try {
                sc.close();
            } catch (IOException ignored) {
            }
        }
        buffer.flip();
        String data = "";
        try {
            data = decoder.decode(buffer).toString();
        } catch (CharacterCodingException e) {
            log(Level.WARNING, "read()", e);
        }
        System.out.println("Message - " + data);
        // 버퍼 메모리 해제
        clearBuffer(buffer);
    }

    private void clearBuffer(ByteBuffer buffer) {
        if (buffer != null) {
            buffer.clear();
            buffer = null;
        }
    }

    /////////////// Log part
    public void initLog() {
        try {
            fileHandler = new FileHandler("SimpleChatClient.log");

        } catch (IOException e) {
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL);
        }
    }

    public void log(Level level, String msg, Throwable error) {
        logger.log(level, msg, error);
    }

    public void info(String msg) {
        logger.info(msg);
    }

    private class MyThread extends Thread {
        SocketChannel sc = null;

        public MyThread(SocketChannel sc) {
            this.sc = sc;
        }

        @Override
        public void run() {
            ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

            try {
                while (!Thread.currentThread().isInterrupted()) {
                    buffer.clear();
                    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                    String message = in.readLine();

                    if (message.startsWith("quit") || message.startsWith("shutdown")) {
                        System.exit(0);
                    }

                    buffer.put(message.getBytes());
                    buffer.flip();
                    sc.write(buffer);
                }
            } catch (Exception e) {
                log(Level.WARNING, "Mythread.run()", e);
            } finally {
                clearBuffer(buffer);
            }
        }
    }

    public static void main(String[] args) {
        SimpleChatClient scc = new SimpleChatClient();
        scc.initLog();;
        scc.initServer();
        scc.startServer();
    }
}
