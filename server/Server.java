package server;
import exceptions.*;
import interaction.*;
import server.utility.RequestHandler;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Runs the server.
 */
public class Server {
    private int port;
    private int soTimeout;
    private ServerSocket serverSocket;
    private RequestHandler requestHandler;

    public Server(int port, int soTimeout, RequestHandler requestHandler) {
        this.port = port;
        this.soTimeout = soTimeout;
        this.requestHandler = requestHandler;
    }

    /**
     * Begins server operation.
     */
    public void run() {
        try {
            openServerSocket();
            boolean processingStatus = true;
            while (processingStatus) {
                try (Socket clientSocket = connectToClient()) {
                    processingStatus = processClientRequest(clientSocket);
                } catch (ConnectionErrorException | SocketTimeoutException exception) {
                    break;
                } catch (IOException exception) {
                    Outputer.printerror("Произошла ошибка при попытке завершить соединение с клиентом!");
                    Starter.logger.error("Произошла ошибка при попытке завершить соединение с клиентом!");
                }
            }
            stop();
        } catch (OpeningServerSocketException exception) {
            Outputer.printerror("Сервер не может быть запущен!");
            Starter.logger.fatal("Сервер не может быть запущен!");
        }
    }

    /**
     * Finishes server operation.
     */
    private void stop() {
        try {
            Starter.logger.info("Завершение работы сервера...");
            if (serverSocket == null) throw new ClosingServerSocketException();
            serverSocket.close();
            Outputer.println("Работа сервера успешно завершена.");
            Starter.logger.info("Работа сервера успешно завершена.");
        } catch (ClosingServerSocketException exception) {
            Outputer.printerror("Невозможно завершить работу еще не запущенного сервера!");
            Starter.logger.error("Невозможно завершить работу еще не запущенного сервера!");
        } catch (IOException exception) {
            Outputer.printerror("Произошла ошибка при завершении работы сервера!");
            Starter.logger.error("Произошла ошибка при завершении работы сервера!");
        }
    }

    /**
     * Open server socket.
     */
    private void openServerSocket() throws OpeningServerSocketException {
        try {
            Starter.logger.info("Запуск сервера...");
            serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(soTimeout);
            Starter.logger.info("Сервер успешно запущен.");
        } catch (IllegalArgumentException exception) {
            Outputer.printerror("Порт '" + port + "' находится за пределами возможных значений!");
            Starter.logger.fatal("Порт '" + port + "' находится за пределами возможных значений!");
            throw new OpeningServerSocketException();
        } catch (IOException exception) {
            Outputer.printerror("Произошла ошибка при попытке использовать порт '" + port + "'!");
            Starter.logger.fatal("Произошла ошибка при попытке использовать порт '" + port + "'!");
            throw new OpeningServerSocketException();
        }
    }

    /**
     * Connecting to client.
     */
    private Socket connectToClient() throws ConnectionErrorException, SocketTimeoutException {
        try {
            Outputer.println("Прослушивание порта '" + port + "'...");
            Starter.logger.info("Прослушивание порта '" + port + "'...");
            Socket clientSocket = serverSocket.accept();
            Outputer.println("Соединение с клиентом успешно установлено.");
            Starter.logger.info("Соединение с клиентом успешно установлено.");
            return clientSocket;
        } catch (SocketTimeoutException exception) {
            Outputer.printerror("Превышено время ожидания подключения!");
            Starter.logger.warn("Превышено время ожидания подключения!");
            throw new SocketTimeoutException();
        } catch (IOException exception) {
            Outputer.printerror("Произошла ошибка при соединении с клиентом!");
            Starter.logger.error("Произошла ошибка при соединении с клиентом!");
            throw new ConnectionErrorException();
        }
    }

    /**
     * The process of receiving a request from a client.
     */
    private boolean processClientRequest(Socket clientSocket) {
        Request userRequest = null;
        Response responseToUser = null;
        try (ObjectInputStream clientReader = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream clientWriter = new ObjectOutputStream(clientSocket.getOutputStream())) {
            do {
                userRequest = (Request) clientReader.readObject();
                responseToUser = requestHandler.handle(userRequest);
                Starter.logger.info("Запрос '" + userRequest.getCommandName() + "' успешно обработан.");
                clientWriter.writeObject(responseToUser);
                clientWriter.flush();
            } while (responseToUser.getResponseCode() != ResponseCode.SERVER_EXIT);
            return false;
        } catch (ClassNotFoundException exception) {
            Outputer.printerror("Произошла ошибка при чтении полученных данных!");
            Starter.logger.error("Произошла ошибка при чтении полученных данных!");
        } catch (InvalidClassException | NotSerializableException exception) {
            Outputer.printerror("Произошла ошибка при отправке данных на клиент!");
            Starter.logger.error("Произошла ошибка при отправке данных на клиент!");
        } catch (IOException exception) {
            if (userRequest == null) {
                Outputer.printerror("Непредвиденный разрыв соединения с клиентом!");
                Starter.logger.warn("Непредвиденный разрыв соединения с клиентом!");
            } else {
                Outputer.println("Клиент успешно отключен от сервера!");
                Starter.logger.info("Клиент успешно отключен от сервера!");
            }
        }
        return true;
    }
}
