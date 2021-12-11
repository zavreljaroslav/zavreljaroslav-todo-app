package ToDo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class ToDo {
    private final Path tasksPath = Paths.get("src/ToDo/TasksFile"); //path to file with data
    private final String IOError = "ERROR: Couldn't reach the file..."; //string when handling IO error, so I don't have to type it every time

    ToDo() {
    }

    public void printUsage() { //prints the valid arguments
        System.out.println("Command Line Todo Application");
        System.out.println("=============================");
        System.out.println();
        System.out.println("Command line arguments: ");
        System.out.println("    -l   List all the tasks");
        System.out.println("    -a   Adds a new task");
        System.out.println("    -r   Removes a task");
        System.out.println("    -c   Completes a task");
    }

    public void addTask(String task) { //adds a new task, appends if there are already other tasks
        List<String> taskToAdd = new ArrayList<>();
        taskToAdd.add(task.concat("; open")); //adds an argument at the end of the line, which indicates it's not done yet
        try {
            Files.write(this.tasksPath, taskToAdd, StandardOpenOption.APPEND);
        } catch (IOException ioException) {
            System.out.println(this.IOError);
        }
    }

    public void listAllTasks() { // prints tasks
        if (getNumberOfTasks() == 0) {
            System.out.println("No todos for today! :)");
        } else {
            try {
                List<String> lines = Files.readAllLines(this.tasksPath);
                int taskNumber = 1;
                for (String task : lines) {
                    if (task.substring(task.length() - 6).contains("open")) { //if the task is marked as open at the end, it will print as not complete
                        System.out.println(taskNumber + " - [ ] " + task.substring(0, task.length() - 6));
                    } else {
                        System.out.println(taskNumber + " - [x] " + task.substring(0, task.length() - 6));
                    }
                    taskNumber++;
                }
            } catch (IOException ioException) {
                System.out.println(this.IOError);
            }
        }
    }

    public void removeTask(String string) { //removes a selected task
        int taskToRemove = 0;
        try {
            taskToRemove = Integer.parseInt(string) - 1; //this converts the string into a number
        } catch (NumberFormatException ex) { //the function will throw a numberFormatException if the string isn't representing a number
            System.out.println("Unable to remove: Index is not a number");
        }
        if (getNumberOfTasks() < taskToRemove) { //checks if the number provided is larger than the amount of tasks on the list, if yes souts an error
            System.out.println("Unable to remove: Index out of bounds");
        } else {
            if (getNumberOfTasks() >= 2) { //can only remove a task if there are at least 2; it's in the task description
                try {
                    List<String> lines = Files.readAllLines(this.tasksPath); //reads teh file, and removes the marked item
                    lines.remove(taskToRemove);

                    try {
                        Files.write(this.tasksPath, lines); //rewrites the file with the edited list
                    } catch (IOException ioException) {
                        System.out.println(this.IOError); //handling IO exceptions for files.write/read
                    }
                } catch (IOException ioException) {
                    System.out.println(this.IOError);
                }
            }
        }

    }

    public void checkTask(String string) {//changes the tag at the end of the task (default "; open", to "; done"), this can be read by print function to know how to print
        int taskToCheck = 0;
        try {
            taskToCheck = Integer.parseInt(string) - 1;
        } catch (NumberFormatException ex) {
            System.out.println("Unable to check: Index is not a number");
        }
        if (getNumberOfTasks() < taskToCheck) {
            System.out.println("Unable to check: Index out of bounds");
        } else {
            if (getNumberOfTasks() >= 2) {
                try {
                    List<String> lines = Files.readAllLines(this.tasksPath);
                    String item = lines.get(taskToCheck).replace("open", "done");
                    lines.remove(taskToCheck);
                    lines.add(taskToCheck, item);
                    try {
                        Files.write(this.tasksPath, lines);
                    } catch (IOException ioException) {
                        System.out.println(this.IOError);
                    }
                } catch (IOException ioException) {
                    System.out.println(this.IOError);
                }
            }
        }
    }

    public int getNumberOfTasks() { //function returning as int, how many tasks there are in the list
        int taskCounter = 0;
        try {
            List<String> lines = Files.readAllLines(this.tasksPath);
            for (String task : lines) {
                taskCounter++;
            }
        } catch (IOException ioException) {
            System.out.println(this.IOError);
        }
        return taskCounter;
    }
}

