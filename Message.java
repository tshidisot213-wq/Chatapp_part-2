package chatapp;

import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;

public class Message {
    
    private String id;
    private int num;
    private String target;
    private String content;
    private String code;
    
    public Message(int n, String t, String c) {
        num = n;
        target = t;
        content = c;
        id = makeId();
        code = makeCode();
    }
    
    // for testing only (fixed ID)
    public Message(int n, String t, String c, String fixed) {
        num = n;
        target = t;
        content = c;
        id = fixed;
        code = makeCode();
    }
    
    private String makeId() {
        Random r = new Random();
        long v = 1000000000L + (long)(r.nextDouble() * 9000000000L);
        return "" + v;
    }
    
    private String letters(String w) {
        return w.replaceAll("[^A-Za-z]", "");
    }
    
    public String makeCode() {
        String start = id.substring(0, 2);
        String[] parts = content.trim().split("\\s+");
        String first = letters(parts[0]).toUpperCase();
        String last = letters(parts[parts.length - 1]).toUpperCase();
        return start + ":" + num + ":" + first + last;
    }
    
    public boolean idFine() {
        return id != null && id.length() <= 10;
    }
    
    public String checkTarget() {
        if (target == null || !target.matches("^\\+27[0-9]{9}$"))
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        return "Cell phone number successfully captured.";
    }
    
    public void saveToJson() {
        try (FileWriter f = new FileWriter("data.json", true)) {
            f.write("{\"id\":\"" + id + "\", \"hash\":\"" + code + "\", \"to\":\"" + target + "\", \"msg\":\"" + content + "\"}\n");
        } catch (IOException e) {
            System.out.println("JSON error.");
        }
    }
    
    public String getId() { return id; }
    public int getNum() { return num; }
    public String getReceiver() { return target; }
    public String getText() { return content; }
    public String getHash() { return code; }
}