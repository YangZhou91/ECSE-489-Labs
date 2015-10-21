package ca.mcgill.ecse489.packet;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;

import ca.mcgill.ecse489.record.Record;
import ca.mcgill.ecse489.structures.Answer;
import ca.mcgill.ecse489.structures.Header;
import ca.mcgill.ecse489.structures.Question;

/**
 * The object for DNS packet
 * 
 * @author Yang Zhou(260401719)
 *
 */
public class Packet implements PacketCompoent<Packet> {

    // one header
    private Header header;
    // a number of questions
    private Collection<Question> questions = new ArrayList<>();
    // a number of answers
    private Collection<Answer> answers = new ArrayList<>();
    private Collection<Record> authority = new ArrayList<>();
    private Collection<Record> additionals = new ArrayList<>();

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Collection<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Collection<Question> questions) {
        this.questions = questions;
    }

    public Collection<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Collection<Answer> answers) {
        this.answers = answers;
    }

    public Collection<Record> getAuthority() {
        return authority;
    }

    public void setAuthority(Collection<Record> authority) {
        this.authority = authority;
    }

    public Collection<Record> getAdditionals() {
        return additionals;
    }

    public void setAdditionals(Collection<Record> additionals) {
        this.additionals = additionals;
    }

    @Override
    public Packet toBytes(ByteBuffer buf) throws IOException {
        header.setQdcount((short) questions.size());
        header.setAncount((short) answers.size());
        // Set Additional Count
        header.setArcount((short) additionals.size());
        header.setAuthorityRecords((short) authority.size());

        header.toBytes(buf);
        for (Question question : questions) {
            question.toBytes(buf);
        }

        for (Answer answer : answers) {
            answer.toBytes(buf);
        }

        for (Record r : authority) {
            r.toBytes(buf);
        }

        for (Record r : additionals) {
            r.toBytes(buf);
        }

        return this;
    }

    @Override
    public Packet fromBytes(ByteBuffer buf) throws IOException {
        header = new Header();
        header.fromBytes(buf);

        questions.clear();
        answers.clear();
        authority.clear();

        for (int i = 0; i < header.getQdcount(); i++) {
            questions.add(new Question().fromBytes(buf));
        }

        for (int i = 0; i < header.getAncount(); i++) {
            answers.add(new Answer().fromBytes(buf));
        }

        for (int i = 0; i < header.getAuthorityRecords(); i++) {
            authority.add(new Record().fromBytes(buf));
        }

        for (int i = 0; i < header.getArcount(); i++) {
            additionals.add(new Record().fromBytes(buf));
        }

        return this;
    }

    @Override
    public String toString() {
        return "Packet [ Header= " + header + ",Question = "
                + questions + ",answer" + answers + "]";
    }
}
