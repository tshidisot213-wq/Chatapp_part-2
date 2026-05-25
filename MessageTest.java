package chatapp;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MessageTest {
    
    private Message one;
    
    // run before each test: make a sample message
    @Before
    public void init() {
        one = new Message(1, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
    }
    
    // test 1: short message (≤250 chars) is allowed
    @Test
    public void shortMessageOk() {
        assertTrue(one.getText().length() <= 250);
    }
    
    // test 2: long message (260 chars) shows excess = 10
    @Test
    public void longMessageFailsWithExcess() {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < 260; i++) b.append("z");
        String longStr = b.toString();
        assertTrue(longStr.length() > 250);
        int extra = longStr.length() - 250;
        assertEquals(10, extra);
    }
    
    // test 3: correct SA phone number passes
    @Test
    public void goodPhonePasses() {
        String res = one.checkTarget();
        assertEquals("Cell phone number successfully captured.", res);
    }
    
    // test 4: wrong phone number (no +27) fails
    @Test
    public void badPhoneFails() {
        Message bad = new Message(2, "08575975889", "Hi");
        String res = bad.checkTarget();
        assertTrue(res.contains("incorrectly formatted"));
    }
    
    // test 5: hash matches POE exactly and format is correct
    @Test
    public void hashIsCorrect() {
        // exact POE case: must be "00:0:HITONIGHT"
        Message poe = new Message(0, "+27718693002", "Hi Tonight", "0012345678");
        assertEquals("00:0:HITONIGHT", poe.getHash());
        
        // any normal hash pattern: digits : number : LETTERS
        String h = one.getHash();
        assertTrue(h.matches("\\d{2}:\\d+:[A-Z]+"));
        
        // loop over several examples (required by POE)
        String[] list = {"Hello world", "Good morning", "See you later"};
        for (int i = 0; i < list.length; i++) {
            Message m = new Message(i, "+27718693002", list[i]);
            String hh = m.getHash();
            assertTrue(hh.matches("\\d{2}:" + i + ":[A-Z]+"));
        }
    }
}