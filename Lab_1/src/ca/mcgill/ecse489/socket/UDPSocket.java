package ca.mcgill.ecse489.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

import ca.mcgill.ecse489.packet.Packet;

public class UDPSocket {
    // default port
    public static final int PORT = 53;
    public static final int MAX_PACKET_SIZE = 512;
    public static final int TIMEOUT = 5;
    public static final int MAX_RETRIES = 3;

    private final InetAddress server;
    private int port;
    private int timeout;
    private int max_retries;

    public UDPSocket(InetAddress server) {
        this(server, PORT);
    }

    public UDPSocket(InetAddress server, int port) {
        this(server, port, TIMEOUT);
    }

    public UDPSocket(InetAddress server, int port, int timeout) {
        this(server, port, timeout, MAX_RETRIES);
    }

    public UDPSocket(InetAddress server, int port, int timeout, int max_retries) {
        this.server = server;
        this.port = port;
        this.timeout = timeout;
        this.max_retries = max_retries;
    }

    public Packet sendQuery(Packet dnsPacket) throws IOException {
        byte[] packetBytes = serializeMessage(dnsPacket, MAX_PACKET_SIZE);
        DatagramSocket socket = new DatagramSocket();
        socket.setSoTimeout(timeout * 1000);

        DatagramPacket requestPacket = new DatagramPacket(packetBytes, packetBytes.length, server, port);

        int i = 0;
        boolean continueSending = true;
        long startTime = System.currentTimeMillis();

        while (continueSending && i < max_retries) {
            try

            {

                socket.send(requestPacket);

                byte[] buf = new byte[1024];
                DatagramPacket responsePacket = new DatagramPacket(buf, buf.length);
                socket.receive(responsePacket);
                continueSending = false;
                long responseTime = System.currentTimeMillis() - startTime;
                System.out.println("Response received after "
                        + "[" + String.format("%d", TimeUnit.MILLISECONDS.toSeconds(responseTime)) + "]" + " seconds "
                        + "([" + i + "] retries)");

                byte[] responseBytes = new byte[responsePacket.getLength()];
                System.arraycopy(responsePacket.getData(), responsePacket.getOffset(), responseBytes, 0,
                        responsePacket.getLength());

                ByteBuffer byteBuffer = ByteBuffer.wrap(responseBytes);
                Packet responseMessage = new Packet().fromBytes(byteBuffer);

                return responseMessage;

            } catch (SocketTimeoutException e) {
                System.err.println("ERROR" + "\t" + "[socket time out]");
            } catch (

            SocketException e)

            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (

            IOException e)

            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            i++;

        }

        if (i == max_retries && continueSending) {
            System.err.println("ERROR" + "\t" + "Maximum number of retries "
                    + "[" + max_retries + "] exceeded");
        }
        return null;

    }

    static byte[] serializeMessage(Packet packet, int maxPacketSize) throws IOException {
        ByteBuffer requestBuffer = ByteBuffer.allocate(maxPacketSize);
        packet.toBytes(requestBuffer);
        requestBuffer.flip();

        byte[] packetBytes = new byte[requestBuffer.limit()];
        requestBuffer.get(packetBytes);
        return packetBytes;
    }
}
