import java.util.Scanner;

public class Einstein {
    public String chatbotAscii;
    public String chatbotName = "Einstein";
    private String[] tasks = new String[100];
    private int taskCount = 0; 


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
        System.out.println("\s\s" + chatbotAscii + "\nGuten tag, " + chatbotName + " here! What can I do for you?"); 
        System.out.println("____________________________________________________________");
    }

    public void farewell() {
        System.out.println("____________________________________________________________");
        System.out.println("\tBye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");
    }

    public void addTask(String task) {
        if (taskCount < 100) {
            tasks[taskCount] = task;
            taskCount++;
            System.out.println("____________________________________________________________");
            System.out.println("\nadded:" + task);
            System.out.println("____________________________________________________________");
        } else {
            System.out.println("____________________________________________________________");
            System.out.println("Sorry! as much as I'm a genius and REALLY smart, I can't store more than 100 tasks :(");
            System.out.println("____________________________________________________________");
        }
    }

    public void listTasks() {
        System.out.println("____________________________________________________________");

        if (taskCount == 0) {
            System.out.println("Hmmm, didn't find any tasks. Add some tasks!");
        } else {
            for (int i = 0; i < taskCount; i++) {
                System.out.println("Task " + (i + 1) + ". " + tasks[i]);
            }
        }

        System.out.println("____________________________________________________________");
    }

    public void processCommands() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String userInput = scanner.nextLine().trim();

            if (userInput.equalsIgnoreCase("bye")) {
                farewell();
                break;
            } else if (userInput.equalsIgnoreCase("list")) {
                listTasks();
            } else {
                addTask(userInput);
            }
        }
        scanner.close();
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
        einstein.processCommands();
    }
}