package data.task;

import java.time.LocalDateTime;

public interface AddNewTask {
    String GetNewTitle();
    String GetNewContent();
    LocalDateTime GetNewDDL();
    boolean GetNewExtern();
}
