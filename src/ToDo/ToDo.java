package ToDo;

import javax.imageio.IIOException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class ToDo {
    protected Path tasksPath = Paths.get("src/ToDo/TasksFile");


    ToDo (){
    }

    public void printUsage(){
        System.out.println("Command Line Todo Application");
        System.out.println("=============================");
        System.out.println();
        System.out.println("Command line arguments: ");
        System.out.println("    -l   List all the tasks");
        System.out.println("    -a   Adds a new task");
        System.out.println("    -r   Removes a task");
        System.out.println("    -c   Completes a task");
    }

    public void addTask(String task){
        List<String> taskToAdd = new ArrayList<>();
        taskToAdd.add(task);
        try {
            Files.write(this.tasksPath, taskToAdd, StandardOpenOption.APPEND);
        }catch (IOException ioException){
            System.out.println("ERROR: Couldn't reach the file...");
        }
    }

    public void listAllTasks(){
        try {
            List<String> lines = Files.readAllLines(this.tasksPath);
            int taskNumber = 1;
            for (String task : lines){
                System.out.println(taskNumber + " - " + task);
                taskNumber++;
            }
        } catch (IOException ioException){
            System.out.println("ERROR: Couldn't reach the file...");
        }
    }
}
