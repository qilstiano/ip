package einstein.ui;

import java.util.ArrayList;
import java.util.Scanner;
import einstein.task.Task;
import einstein.task.Event;
import einstein.task.Deadline;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Ui {
  private static final String[] ORANGE_GRADIENT = {
      "\u001B[38;5;202m", // Light orange
      "\u001B[38;5;208m", // Medium orange
      "\u001B[38;5;214m", // Bright orange
      "\u001B[38;5;220m"  // Yellow-orange
  };
  private static final String RESET = "\u001B[0m"; // Reset color

  public void showWelcome() {
      String chatbotAscii = "         _                   _           _            \r\n" + //
                            "        (_)                 / |_        (_)           \r\n" + //
                            " .---.  __   _ .--.   .--. `| |-'.---.  __   _ .--.   \r\n" + //
                            "/ /__\\\\[  | [ `.-. | ( (`\\] | | / /__\\\\[  | [ `.-. |  \r\n" + //
                            "| \\__., | |  | | | |  `'.'. | |,| \\__., | |  | | | |  \r\n" + //
                            " '.__.'[___][___||__][\\__) )\\__/ '.__.'[___][___||__]  v1.0\r\n" + //
                            "                                                     ";
      System.out.println("____________________________________________________________");
      System.out.println(getGradientText(chatbotAscii));
      System.out.println(getGradientText("\nEinstein\nGuten tag, Einstein here! What can I do for you?"));
      System.out.println("____________________________________________________________");
  }

  public void showFarewell() {
      System.out.println("____________________________________________________________");
      System.out.println(getGradientText("Einstein\n\tBye, hope to see you again soon!"));
      System.out.println("____________________________________________________________");
  }

  public void showLine() {
      System.out.println("____________________________________________________________");
  }

  public void showError(String message) {
      System.out.println(getGradientText("Einstein\n" + message));
  }

  public String readCommand() {
      System.out.print("User\n> ");
      return new Scanner(System.in).nextLine().trim();
  }

  public void showTaskAdded(Task task, int taskCount) {
      System.out.println(getGradientText("Einstein\nGot it. I've added this task:"));
      System.out.println("  " + task);
      System.out.println("Now you have " + taskCount + " tasks in the list.");
  }

  public void showTaskList(ArrayList<Task> tasks) {
      System.out.println(getGradientText("Einstein\nHere are the tasks in your list:"));
      if (tasks.isEmpty()) {
          System.out.println("Hmmm, didn't find any tasks. Add some tasks!");
      } else {
          for (int i = 0; i < tasks.size(); i++) {
              System.out.println((i + 1) + "." + tasks.get(i));
          }
      }
  }

  public void showTasksByDate(ArrayList<Task> tasks, LocalDate date) {
      System.out.println(getGradientText("Einstein\nHere are the tasks occurring on " + date.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ":"));
      boolean found = false;
      for (int i = 0; i < tasks.size(); i++) {
          Task task = tasks.get(i);
          if (task instanceof Deadline) {
              Deadline deadline = (Deadline) task;
              if (deadline.by.toLocalDate().equals(date)) {
                  System.out.println((i + 1) + "." + deadline);
                  found = true;
              }
          } else if (task instanceof Event) {
              Event event = (Event) task;
              if (event.from.toLocalDate().equals(date) || event.to.toLocalDate().equals(date)) {
                  System.out.println((i + 1) + "." + event);
                  found = true;
              }
          }
      }
      if (!found) {
          System.out.println("No tasks found for this date.");
      }
  }

  private String getGradientText(String text) {
      StringBuilder gradientText = new StringBuilder();
      int length = text.length();
      for (int i = 0; i < length; i++) {
          int colorIndex = (i * ORANGE_GRADIENT.length) / length;
          gradientText.append(ORANGE_GRADIENT[colorIndex]).append(text.charAt(i));
      }
      gradientText.append(RESET);
      return gradientText.toString();
  }
}
