package data.task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DayTask {
    private LocalDate date;
    private ArrayList<TaskUnit> task_list;
    private boolean all_finished;//不保存,运行中判断

    public DayTask(){
        task_list = new ArrayList<>();
    }

    public DayTask(LocalDate date) {
        this.date = date;
        task_list = new ArrayList<>();
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<TaskUnit> getTask_list() {
        return task_list;
    }

    public void setTask_list(ArrayList<TaskUnit> task_list) {
        this.task_list = task_list;
    }

    public boolean isAllFinished() {
        return all_finished;
    }

    public void finishTask(String title){
        for(TaskUnit task : task_list){
            if(task.getTitle().equals(title)){
                task.setFinished(true);
                break;
            }
        }
    }

    public boolean titleExited(String title){
        for(TaskUnit task:task_list){
            if(task.getTitle().equals(title))return true;
        }
        return false;
    }

    public void addTask(TaskUnit new_task){
        task_list.add(new_task);
    }

    public void removeTask(String title){
        task_list.removeIf(task -> task.getTitle().equals(title));
    }

}
