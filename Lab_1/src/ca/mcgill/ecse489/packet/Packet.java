package ca.mcgill.ecse489.packet;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;

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
    private Collection<Answer> authority = new ArrayList<>();
    private Collection<Answer> additional = new ArrayList<>();

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

    public Collection<Answer> getAuthority() {
        return authority;
    }

    public void setAuthority(Collection<Answer> authority) {
        this.authority = authority;
    }

    public Collection<Answer> getAdditional() {
        return additional;
    }

    public void setAdditional(Collection<Answer> additional) {
        this.additional = additional;
    }

    @Override
    public Packet toBytes(ByteBuffer buf) {
        header.setQdcount((short) questions.size());
        header.setAncount((short) answers.size());
        // Not used
        header.setArcount((short) additional.size());

        header.toBytes(buf);
        for (Question question : questions) {
            question.toBytes(buf);
        }

        for (Answer answer : answers) {
            answer.toBytes(buf);
        }

        // TODO set up authority and additional section if needed.

        return this;
    }

    @Override
    public Packet fromBytes(ByteBuffer buf) {
        header = new Header();
        header.fromBytes(buf);

        questions.clear();
        answers.clear();

        for (int i = 0; i < header.getQdcount(); i++) {
            questions.add(new Question().fromBytes(buf));
        }

        for (int i = 0; i < header.getAncount(); i++) {
            answers.add(new Answer().fromBytes(buf));
        }

        // TODO add Authority and Additional Section if needed

        return this;
    }

    @Override
    public String toString() {
        return "Packet [ Header= " + header + ",Question = "
                + questions + ",answer" + answers + "]";
    }
}
