import java.util.Scanner;

public class Einstein {
    public String chatbotAscii;
    public String chatbotName = "Einstein";

    public Einstein() {
        chatbotAscii =  "         _                   _           _            \r\n" + //
                        "        (_)                 / |_        (_)           \r\n" + //
                        " .---.  __   _ .--.   .--. `| |-'.---.  __   _ .--.   \r\n" + //
                        "/ /__\\\\[  | [ `.-. | ( (`\\] | | / /__\\\\[  | [ `.-. |  \r\n" + //
                        "| \\__., | |  | | | |  `'.'. | |,| \\__., | |  | | | |  \r\n" + //
                        " '.__.'[___][___||__][\\__) )\\__/ '.__.'[___][___||__] \r\n" + //
                        "                                                     ";
    }

    public void greeting() {
        System.out.println("____________________________________________________________");
        System.out.println(chatbotAscii + "\n\t\t Hello! I'm " + chatbotName + "\n\t\tWhat can I do for you?"); 
        System.out.println("____________________________________________________________");
    }

    public void farewell() {
        System.out.println("____________________________________________________________");
        System.out.println("\tBye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");
    }

    public void echoCommands() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String userInput = scanner.nextLine().trim();

            if (userInput.equalsIgnoreCase("bye")) {
                farewell();
                break;
            }

            System.out.println("____________________________________________________________");
            System.out.println("\n" + userInput);
            System.out.println("____________________________________________________________");
        }
        scanner.close();
    }

    public static void main(String[] args) {
        Einstein einstein = new Einstein();
        einstein.greeting();
        einstein.echoCommands();
    }
}