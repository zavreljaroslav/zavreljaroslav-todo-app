package ToDo;

public class Main {

    public static void main(String[] args) {
        ToDo todo = new ToDo();
        handleInput(args, todo);
        todo.listAllTasks();

    }

    public static ToDo handleInput(String[] args, ToDo todo) {
        if (args.length == 0) {
            todo.printUsage();
        } else if (args[0].equals("-a")) {
            if (args.length == 1) {
                System.out.println("Couldn't add task: no task provided!");
            } else {
                todo.addTask(args[1]);
            }
        } else if (args[0].equals("-l")) {
            todo.listAllTasks();
        }
        return todo;
    }
}
