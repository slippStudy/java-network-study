package org.example.len.ch15_selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

class SimpleChatServer {

    public static final String HOST = "localhost";
    public static final int PORT = 9090;

    private static FileHandler fileHandler;
    private static Logger logger = Logger.getLogger("org.example.len.ch15_selector");

    private Selector selector = null;
    private ServerSocketChannel serverSocketChannel = null;
    private ServerSocket serverSocket = null;

    private Vector room = new Vector();

    public void initServer() {
        try {


            serverSocketChannel = ServerSocketChannel.open();

            serverSocketChannel.configureBlocking(false);

            serverSocket = serverSocketChannel.socket();

            //주어진 파라미터에 해당하는 주소. 포트로 서버소켓을 바인드한다.
            InetSocketAddress isa = new InetSocketAddress(HOST, PORT);
            serverSocket.bind(isa);

            selector = Selector.open();

            // 서버소켓채널을 셀렉터에 등록
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        } catch (IOException e) {
            logger.log(Level.WARNING, "SimpleChatServer.initServer()", e);
        }
    }

    public void startServer() {
        logger.info("Server is started ...");

        try {
            while (true) {
                logger.info("요청을 기다리는 중 ..");
                // 셀렉터의 select() 메소드로 준비된 이벤트가 있는지 확인
                // 다른 클라이언트가 들어와 있는지 없는지 여부를 파악하는 메소드
                selector.select();

                // 셀렉터의 SelectedSet에 저장된 준비된 이벤트(SelectionKey) 을 하나씩 처리
                Iterator it = selector.selectedKeys().iterator();
                while (it.hasNext()) {
                    SelectionKey key = (SelectionKey) it.next();
                    if (key.isAcceptable()) {
                        // 서버소켓채널에 클라이언트가 접속을 시도한 경우
                        accept(key);
                    } else if (key.isReadable()) {
                        // 이미 연결된 클라이언트가 메시지를 보낸 경우
                        read(key);
                    }
                    // 이미 처리한 이벤트이므로 반드시 삭제
                    it.remove();
                }
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "SimpleChatServer.startServer()", e);
        }
    }

//    private void accept(SelectionKey key) {
//        ServerSocketChannel server = (ServerSocketChannel) key.channel();
//        SocketChannel sc;
//
//        try {
//            // 서버소켓채널의 accept() 메소드로 서버소켓 생성
//            sc = server.accept();
//            registerChannel(selector, sc, SelectionKey.OP_READ);
//            logger.info(sc.toString() + " 클라이언트가 접속했습니다.");
//        } catch (ClosedChannelException e) {
//            logger.log(Level.WARNING, "accept()", e);
//        } catch (IOException e) {
//            logger.log(Level.WARNING, "accpet()22", e);
//        }
//
//    }


    private void read(SelectionKey key) {
        // SelectionKey로부터 소켓채널을 얻어온다
        SocketChannel sc = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

        try {
            int read = sc.read(buffer);
            logger.info(read + " byte를 읽었습니다.");
        } catch (IOException e) {

            try {
                sc.close();
            } catch (IOException ex) {
            }

            removeUser(sc);
            logger.info(sc.toString() + " 클라이트가 접속을 해제");
        }

        try {
            // 클라이언트가 보낸 메시지를 채팅방 나에 모든 사용자에게 브로드캐스해준다.
            broadcast(buffer);
        } catch (IOException e) {
            logger.log(Level.WARNING, "broadcast()", e);
        }

        // 버퍼 메모리 해제해준다.
        clearBuffer(buffer);

    }

    private void removeUser(SocketChannel sc) {
        room.remove(sc);
    }

    private void clearBuffer(ByteBuffer buffer) {
        if (buffer != null) {
            buffer.clear();
            buffer = null;
        }
    }

    private void broadcast(ByteBuffer buffer) throws IOException {
        buffer.flip();

        for (Object o : room) {
            SocketChannel sc = (SocketChannel) o;
            if (sc != null) {
                sc.write(buffer);
                buffer.rewind();
            }
        }
    }

    private void accept(SelectionKey key) {
        ServerSocketChannel server = (ServerSocketChannel) key.channel();
        SocketChannel sc;
        try {
            sc = server.accept();
            registerChannel(selector, sc, SelectionKey.OP_READ);
            logger.info(sc.toString() + " 클라이언트가 접속했습니다.");
        } catch (IOException e) {
            logger.log(Level.WARNING, "SimpleChatServer.accept", e);
        }

    }

    private void registerChannel(Selector selector, SocketChannel sc, int oPs) throws ClosedChannelException, IOException {
        if (sc == null) {
            logger.info("Invalid Connection");
            return;
        }

        sc.configureBlocking(false);
        sc.register(selector, oPs);
        // 채팅창에 추가
        addUser(sc);
    }

    private void addUser(SocketChannel sc) {
        room.add(sc);
    }

    //    ///////////// Log part
    public void initLog() {
        try {
            fileHandler = new FileHandler("SimpleChatServer.log");

        } catch (IOException e) {
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL);
        }
    }

    public void log(Level level, String msg, Throwable error) {
        logger.log(level, msg, error);
    }

    public static void main(String[] args) {
        SimpleChatServer scs = new SimpleChatServer();
        scs.initLog();
        scs.initServer();
        scs.startServer();
    }
}
