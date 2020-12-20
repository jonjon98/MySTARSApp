import java.io.Console;
import java.util.Arrays;
public class Masking {

    public String passwordMasking() {        
        Console console = System.console();
        if (console == null) {
            System.out.println("Couldn't get Console instance");
            System.exit(0);
            return "failed";
        }

        console.printf("Testing password%n");
        char[] passwordArray = console.readPassword("Enter password: ");
        String passwordString = String.valueOf(passwordArray);
        Arrays.fill(passwordArray, '*');
        console.printf("Password: %s%n", new String(passwordArray));
        return passwordString;
    }

    /*public static void main(String[] args) {
        new Masking().passwordMasking();
    }*/
}