package vue;


import java.util.Date;
import org.jfree.data.gantt.Task;

public class TaskNumeric extends Task {

    public TaskNumeric(String description, long start, long end) {
        super(description, new Date(start), new Date(end));
    }
    
    public TaskNumeric(String description){
        super(description,new Date(), new Date());
    }

    public static TaskNumeric duration(String description, long start, long duration) {
        return new TaskNumeric(description, start, start + duration);
    }

}