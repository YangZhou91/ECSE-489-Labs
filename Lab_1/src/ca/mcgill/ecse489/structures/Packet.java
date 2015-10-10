package ca.mcgill.ecse489.structures;
import java.util.ArrayList;
import java.util.Collection;

/**
 * The object for DNS packet 
 * @author Yang Zhou(260401719)
 *
 */
public class Packet {
    
    // one header
    private Header header;
    // a number of questions
    private Collection<Question> questions = new ArrayList<>();
    // a number of answers
    private Collection<Answer> answers = new ArrayList<>();
}
