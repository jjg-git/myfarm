/** This is to certify that this project is my own work, based on my personal efforts in studying
* and applying the concepts learned. I have constructed the functions and their respective
* algorithms and corresponding code by myself. The program was run, tested, and
* debugged by my own efforts. I further certify that I have not copied in part or whole or
* otherwise plagiarized the work of other students and/or persons.
* John Joseph F. Giron, 12198129
 */

package com.ccprog3.myfarm.terminalinterface;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Creates a series of question of different types. The next question gets 
 * invoked after the previous question was answered until the end of the
 * question. Think of this as an interview.
 * @author John Joseph F. Giron
 */
public class ChainQuestion {
    ArrayList<Question> chain;
    private int step = 0;
    
    public ChainQuestion() {
        chain = new ArrayList<>();
    }
    
    public ChainQuestion(Question ... questions) {
        chain = new ArrayList<>();
        
        addQuestion(questions);
    }
    
    public void invoke() {
        Question current;        
        while (step < chain.size()) {
            current = chain.get(step);
            
            while (!current.invoke()) {}
            
            if (current instanceof MultiChoice multiChoice) {
                if (multiChoice.getAnswer() == 0) {
                    step -= 1;
                    continue;
                }
            }
            
            step += 1;
        }
        
        // Resets the step counter to make the invoke
        // working again.
        step = 0;
    }
    
    public void addQuestion(Question ... questions) {
        chain.addAll(Arrays.asList(questions));
    }
    
    public void addQuestion(Question question) {
        chain.add(question);
    }
    
    public Question getQuestion(int idx) {
        return chain.get(idx);
    }
}
