package ca.mcgill.ecse489.dns;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import ca.mcgill.ecse489.packet.Packet;
import ca.mcgill.ecse489.record.Record;
import ca.mcgill.ecse489.socket.UDPSocket;
import ca.mcgill.ecse489.structures.Answer;
import ca.mcgill.ecse489.structures.Domain;
import ca.mcgill.ecse489.structures.Header;
import ca.mcgill.ecse489.structures.Question;
import ca.mcgill.ecse489.type.Class;
import ca.mcgill.ecse489.type.Type;
import ca.mcgill.ecse489.util.Util;

public class DnsClient {

    // This is probably in milliseconds
    public static final int DEFAULT_TIMEOUT = 5;
    public static final int DEFAULT_MAX_RETREIES = 3;
    public static final int DEFAULT_PORT = 53;

    public static final Random RANDOM = new Random();
    public static String dnsHost;
    public static String domain;
    public static int timeout = DEFAULT_TIMEOUT;
    public static int maxRetries = DEFAULT_MAX_RETREIES;
    public static int port = DEFAULT_PORT;
    public static Type type = Type.A;
    InetAddress dnsServer;

    public DnsClient(InetAddress dnsServer) {
        this(dnsServer, DEFAULT_TIMEOUT);
    }

    public DnsClient(InetAddress dnsServer, int timeout) {

        this(dnsServer, timeout, DEFAULT_MAX_RETREIES);
    }

    public DnsClient(InetAddress dnsServer, int timeout, int maxRetries) {

        this(dnsServer, timeout, maxRetries, DEFAULT_PORT);
    }

    public DnsClient(InetAddress dnsServer, int timeout, int maxRetries, int port) {
        this.dnsServer = dnsServer;
        DnsClient.timeout = timeout;
        DnsClient.maxRetries = maxRetries;
        DnsClient.port = port;
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

        UDPSocket socket = new UDPSocket(dnsServer, port, timeout, maxRetries);
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

        options.addOption(Option.builder("t").argName("timeout").hasArg(true).desc("").build());
        options.addOption(Option.builder("r").argName("number of retries").hasArg(true).build());
        options.addOption(Option.builder("p").argName("port num").hasArg().build());
        options.addOption("mx", false, "Mail server");
        options.addOption("ns", false, "Name server");

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("t")) {
                timeout = Integer.parseInt(cmd.getOptionValue("t"));

            }

            if (cmd.hasOption("r")) {
                maxRetries = Integer.parseInt(cmd.getOptionValue("r"));
            }

            if (cmd.hasOption("p")) {
                port = Integer.parseInt(cmd.getOptionValue("p"));
            }

            if (cmd.hasOption("mx")) {
                type = Type.MX;
            }

            if (cmd.hasOption("ns")) {
                type = Type.NS;
            }

            try {
                if (cmd.getArgList().get(0).contains("@")) {
                    dnsHost = cmd.getArgList().get(0).replace("@", "");
                } else if (!cmd.getArgList().get(0).contains("@")) {
                    System.err.println("ERROR" + "\t" + "[Please specify the DNS with @ at beginning]");
                }

                domain = cmd.getArgList().get(1);
            } catch (java.lang.IndexOutOfBoundsException e) {
                System.err.println("ERROR" + "\t" + "[Please specify both the domain or DNS server]");
            }

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        /**
         * Create dns client
         */
        try {
            InetAddress dnsServer = InetAddress.getByAddress(Util.getIpAddress(dnsHost));
            DnsClient client = new DnsClient(dnsServer, timeout, maxRetries, port);

            /**
             * Request output
             */
            System.out.println("DnsClient sending request for [" + domain + "]");
            System.out.println("Server: [" + dnsHost + "]");
            System.out.println("Request type: [" + type + "]");

            // Defined type and the class is default
            Packet reply = client.request(domain, type);

            System.out.println("***Answer Section ([" + reply.getHeader().getAncount() + "]records) ****");
            for (Question q : reply.getQuestions()) {
            }
            for (Answer a : reply.getAnswers()) {
                Type type = a.getAnswerType();
                String typeSection = new String();
                String isAuth = (reply.getHeader().isAa() ? "auth" : "nonauth");
                switch (type) {
                    case A:
                        typeSection = String.format("%s \t [%s]", "IP", a.getrData());

                        break;

                    case CNAME:
                        typeSection = String.format("%s \t [%s]", "CNAME", a.getrData());
                        break;

                    case MX:
                        typeSection = String.format("%s \t [%s]", "MX", a.getrData());
                        break;

                    case NS:
                        typeSection = String.format("%s \t [%s]", "NS", a.getrData());
                        break;

                    default:
                        break;

                }

                System.out.println(
                        typeSection + "\t" + "[" + a.getTtl() + " seconds can cache" + "]"
                                + "\t" + "[" + isAuth + "]");

            }

            if (reply.getHeader().getArcount() > 0) {
                System.out.println("***Additional Section ([" + reply.getHeader().getArcount() + "])***");
            } else {
                // System.out.println("NOFOUND");
            }

            for (Record additional : reply.getAdditionals()) {
                Type type = additional.getRecordType();
                String typeSection = new String();
                String isAuth = (reply.getHeader().isAa() ? "auth" : "nonauth");
                switch (type) {
                    case A:
                        typeSection = String.format("%s \t [%s]", "IP", additional.getRecordData());

                        break;

                    case CNAME:
                        typeSection = String.format("%s \t [%s]", "CNAME", additional.getRecordData());
                        break;

                    case MX:
                        typeSection = String.format("%s \t [%s]", "MX", additional.getRecordData());
                        break;

                    case NS:
                        typeSection = String.format("%s \t [%s]", "NS", additional.getRecordData());
                        break;

                    default:
                        break;
                }

                System.out.println(
                        typeSection + "\t" + "[" + additional.getTtl() + " seconds can cache" + "]"
                                + "\t" + "[" + isAuth + "]");
            }

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.err.println("ERROE" + "\t" + "[Please specify the domain or dns]");
        }

    }
}
