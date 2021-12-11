package ToDo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class ToDo {
    protected Path tasksPath = Paths.get("src/ToDo/TasksFile");
    protected String IOError = "ERROR: Couldn't reach the file...";


    ToDo() {
    }

    public void printUsage() {
        System.out.println("Command Line Todo Application");
        System.out.println("=============================");
        System.out.println();
        System.out.println("Command line arguments: ");
        System.out.println("    -l   List all the tasks");
        System.out.println("    -a   Adds a new task");
        System.out.println("    -r   Removes a task");
        System.out.println("    -c   Completes a task");
    }

    public void addTask(String task) {
        List<String> taskToAdd = new ArrayList<>();
        taskToAdd.add(task.concat("; open"));
        try {
            Files.write(this.tasksPath, taskToAdd, StandardOpenOption.APPEND);
        } catch (IOException ioException) {
            System.out.println(this.IOError);
        }
    }

    public void listAllTasks() {
        if (getNumberOfTasks() == 0) {
            System.out.println("No todos for today! :)");
        }
        try {
            List<String> lines = Files.readAllLines(this.tasksPath);
            int taskNumber = 1;
            for (String task : lines) {
                if (task.substring(task.length()-6).contains("open")){
                    System.out.println(taskNumber + " - [ ] " + task.substring(0,task.length()-6));
                } else {
                    System.out.println(taskNumber + " - [x] " + task.substring(0,task.length()-6));
                }
                taskNumber++;
            }
        } catch (IOException ioException) {
            System.out.println(this.IOError);
        }
    }

    public void removeTask(String string) {
        int taskToRemove = 0;
        try {
            taskToRemove = Integer.parseInt(string);
        } catch (NumberFormatException ex) {
            System.out.println("Unable to remove: Index is not a number");
        }
        if (getNumberOfTasks() < taskToRemove) {
            System.out.println("Unable to remove: Index out of bounds");
        } else {
            if (getNumberOfTasks() >= 2) {
                try {
                    List<String> lines = Files.readAllLines(this.tasksPath);
                    lines.remove(taskToRemove - 1);

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
    public void checkTask(String string){
        int taskToCheck = 0;
        try {
            taskToCheck = Integer.parseInt(string)-1;
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

    public int getNumberOfTasks() {
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

