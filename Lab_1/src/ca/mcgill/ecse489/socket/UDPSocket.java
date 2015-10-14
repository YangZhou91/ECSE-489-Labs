package ca.mcgill.ecse489.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;

import ca.mcgill.ecse489.packet.Packet;

public class UDPSocket {
    // default port
    public static final int PORT = 53;
    public static final int MAX_PACKET_SIZE = 512;

    private final InetAddress server;
    private final int port;

    public UDPSocket(InetAddress server) {
        this(server, PORT);
    }

    public UDPSocket(InetAddress server, int port) {
        this.server = server;
        this.port = port;
    }

    public Packet sendQuery(Packet dnsPacket) throws IOException {
        byte[] packetBytes = serializeMessage(dnsPacket, MAX_PACKET_SIZE);

        DatagramPacket requestPacket = new DatagramPacket(packetBytes, packetBytes.length, server, port);

        try

        {
            DatagramSocket socket = new DatagramSocket();
            socket.send(requestPacket);

            byte[] buf = new byte[1024];
            DatagramPacket responsePacket = new DatagramPacket(buf, buf.length);
            socket.receive(responsePacket);

            byte[] responseBytes = new byte[responsePacket.getLength()];
            System.arraycopy(responsePacket.getData(), responsePacket.getOffset(), responseBytes, 0,
                    responsePacket.getLength());

            ByteBuffer byteBuffer = ByteBuffer.wrap(responseBytes);
            Packet responseMessage = new Packet().fromBytes(byteBuffer);

            return responseMessage;
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
