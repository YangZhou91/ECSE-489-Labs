package ca.mcgill.ecse489.dns;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import ca.mcgill.ecse489.packet.Packet;
import ca.mcgill.ecse489.socket.UDPSocket;
import ca.mcgill.ecse489.structures.Answer;
import ca.mcgill.ecse489.structures.Domain;
import ca.mcgill.ecse489.structures.Header;
import ca.mcgill.ecse489.structures.Question;
import ca.mcgill.ecse489.type.Class;
import ca.mcgill.ecse489.type.Type;

public class DnsClient {

    public static final Random RANDOM = new Random();
    public static final String DNS_HOST = "132.206.85.18";
    // public static final String DNS_HOST = "8.8.8.8";
    int TIMEOUT;
    int MAXRETIRES;
    int PORT;
    InetAddress dnsServer;

    public DnsClient(InetAddress dnsServer) {
        this.dnsServer = dnsServer;
    }

    public Packet request(String domain) throws IOException {
        return this.request(domain, Type.A);
    }

    public Packet request(String domain, Type qtype) throws IOException {
        return this.request(domain, qtype, Class.IN);
    }

    public Packet request(String domain, Type qtype, Class qclass) throws IOException {
        Packet requestPacket = createPacket(domain, qclass, qtype);
        return getRepley(requestPacket);
    }

    public Packet getRepley(Packet requestPacket) throws IOException {
        if (requestPacket.getHeader().getId() == 0) {
            requestPacket.getHeader().setId((short) RANDOM.nextInt(1 << 15));
        }

        UDPSocket socket = new UDPSocket(dnsServer);
        return getRepley(requestPacket, socket);
    }

    public Packet getRepley(Packet requestPacket, UDPSocket socket) throws IOException {
        return socket.sendQuery(requestPacket);
    }

    private Packet createPacket(String domain, Class qclass, Type qtype) {

        Header header = new Header();
        header.setQr(true);
        // Standard Query
        header.setOpcode(Header.OPCODE.QUERY);
        // header.setAA(true);
        header.setRD(true);

        Question question = new Question();
        question.setQname(Domain.fromString(domain));
        question.setQclass(qclass);
        question.setQtype(qtype);

        Packet requestPacket = new Packet();
        requestPacket.setHeader(header);
        requestPacket.getQuestions().add(question);

        return requestPacket;
    }

    public static void main(String[] args) throws IOException {

        /**
         * Argument Parser
         */
        // Create Options object
        Options options = new Options();

        // add t option, false indicates optional
        options.addOption("t", false, "timeout");
        options.addOption("r", false, "max-retries");
        options.addOption("p", false, "port");
        options.addOption("mx", false, "Mail server");
        options.addOption("ns", false, "Name server");

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("t")) {
                // System.out.print("timeout");
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            DnsClient client = new DnsClient(InetAddress.getByName(DNS_HOST));
            Packet reply = client.request("www.mcgill.ca");
            System.err.println("Questions:");
            for (Question q : reply.getQuestions()) {
                System.err.println(" - " + q);
            }
            for (Answer a : reply.getAnswers()) {
                System.err.println(" - " + a);
            }
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
