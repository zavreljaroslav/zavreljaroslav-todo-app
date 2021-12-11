package ToDo;

public class Main {

    public static void main(String[] args) {
        ToDo todo = new ToDo();
        handleInput(args, todo);
        todo.listAllTasks();
    }

    public static void handleInput(String[] args, ToDo todo) { //checks if the input is valid, if yes, executes the funciton
        if (args.length == 0) {
            todo.printUsage();
        } else if (args[0].equals("-a")) {
            if (args.length == 1) {
                System.out.println("Couldn't add task: no index provided!");
            } else {
                todo.addTask(args[1]);
            }
        } else if (args[0].equals("-l")) {
            todo.listAllTasks();
        } else if (args[0].equals(("-r"))){
            if (args.length == 1) {
                System.out.println("Couldn't remove a task: no task selected for removal.");
            } else {
                todo.removeTask(args[1]);
            }
        } else if (args[0].equals("-c")){
            if (args.length == 1){
                System.out.println("Unable to check, no index provided");
            } else {
                todo.checkTask(args[1]);
            }
        } else {
            System.out.println("Unsupported argument");
        }
    }
}
