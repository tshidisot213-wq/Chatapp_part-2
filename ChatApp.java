package chatapp;

import java.util.Scanner;
import java.util.ArrayList;

public class ChatApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LoginFeature locker = new LoginFeature();
        
        // ask for name and register
        System.out.println("========================================");
        System.out.println("            WELCOME TO CHATAPP");
        System.out.println("========================================\n");
        
        System.out.print("First name: ");
        String first = sc.nextLine();
        System.out.print("Last name: ");
        String last = sc.nextLine();
        System.out.println();
        
        String user;
        do {
            System.out.print("Pick a username ( _ and max 5 letters): ");
            user = sc.nextLine();
            if (!locker.checkUserName(user))
                System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.");
        } while (!locker.checkUserName(user));
        System.out.println("Username successfully captured.\n");
        
        String pass;
        do {
            System.out.print("Pick a password (8+ chars, one capital, one number, one special): ");
            pass = sc.nextLine();
            if (!locker.checkPasswordComplexity(pass))
                System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
        } while (!locker.checkPasswordComplexity(pass));
        System.out.println("Password successfully captured.\n");
        
        String mobile;
        do {
            System.out.print("SA mobile number (+27 then 9 digits): ");
            mobile = sc.nextLine();
            if (!locker.checkCellPhoneNumber(mobile))
                System.out.println("Cell phone number incorrectly formatted or does not contain international code; please correct the number and try again.");
        } while (!locker.checkCellPhoneNumber(mobile));
        System.out.println("Cell phone number successfully added.\n");
        
        String done = locker.registerUser(user, pass, first, last, mobile);
        System.out.println(done);
        
        // log in
        System.out.println("\n========================================");
        System.out.println("              LOGIN SECTION");
        System.out.println("========================================\n");
        boolean ok = false;
        while (!ok) {
            System.out.print("Username: ");
            String u = sc.nextLine();
            System.out.print("Password: ");
            String p = sc.nextLine();
            ok = locker.loginUser(u, p);
            if (ok)
                System.out.println("\n" + locker.returnLoginStatus(true, first, last));
            else
                System.out.println("Username or password incorrect, please try again.\n");
        }
        
        // part two menu
        System.out.println("\n========================================");
        System.out.println("          Welcome to ChatApp");
        System.out.println("========================================");
        
        ArrayList<Message> bag = new ArrayList<>();
        int total = 0;
        boolean exit = false;
        
        while (!exit) {
            System.out.println("\nMenu");
            System.out.println("1. Write messages");
            System.out.println("2. See old messages (not ready)");
            System.out.println("3. Leave");
            System.out.print("Choose: ");
            int pick = sc.nextInt();
            sc.nextLine();
            
            switch (pick) {
                case 1:
                    System.out.print("How many messages will you write? ");
                    int many = sc.nextInt();
                    sc.nextLine();
                    
                    for (int n = 1; n <= many; n++) {
                        System.out.println("\nMessage " + n);
                        
                        String to;
                        do {
                            System.out.print("Receiver number (+27 then 9 digits): ");
                            to = sc.nextLine();
                            if (!locker.checkCellPhoneNumber(to))
                                System.out.println("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
                        } while (!locker.checkCellPhoneNumber(to));
                        
                        String words;
                        while (true) {
                            System.out.print("Your text (max 250 chars): ");
                            words = sc.nextLine();
                            if (words.length() <= 250) {
                                System.out.println("Message ready to send.");
                                break;
                            } else {
                                int extra = words.length() - 250;
                                System.out.println("Message exceeds 250 characters by " + extra + "; please reduce the size.");
                            }
                        }
                        
                        Message one = new Message(n, to, words);
                        
                        System.out.println("\nWhat to do?");
                        System.out.println("1. Send it");
                        System.out.println("2. Throw away");
                        System.out.println("3. Save for later");
                        System.out.print("Choice: ");
                        int act = sc.nextInt();
                        sc.nextLine();
                        
                        switch (act) {
                            case 1:
                                System.out.println("Message successfully sent");
                                total++;
                                bag.add(one);
                                break;
                            case 2:
                                System.out.println("Press 0 to delete the message");
                                break;
                            case 3:
                                System.out.println("Message successfully stored");
                                one.saveToJson();
                                bag.add(one);
                                break;
                            default:
                                System.out.println("Bad choice – ignored.");
                        }
                        
                        System.out.println("\nInfo");
                        System.out.println("ID: " + one.getId());
                        System.out.println("Hash: " + one.getHash());
                        System.out.println("To: " + one.getReceiver());
                        System.out.println("Text: " + one.getText());
                    }
                    
                    System.out.println("\nTotal sent: " + total);
                    break;
                    
                case 2:
                    System.out.println("Coming Soon");
                    break;
                    
                case 3:
                    exit = true;
                    System.out.println("Bye!");
                    break;
                    
                default:
                    System.out.println("Wrong option.");
            }
        }
        sc.close();
    }
}