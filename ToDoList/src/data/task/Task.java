package data.task;

import java.time.LocalDate;
import java.util.ArrayList;

public class Task {
    ArrayList<DayTask> dayTasks;

    public Task() {
        dayTasks = new ArrayList<>();
    }

    public ArrayList<DayTask> getDayTasks() {
        return dayTasks;
    }

    public void setDayTasks(ArrayList<DayTask> dayTasks) {
        this.dayTasks = dayTasks;
    }

    public void addDayTask(LocalDate date){
        for(int i = 0;i < dayTasks.size();i++){
            if(dayTasks.get(i).getDate().isAfter(date)){
                dayTasks.add(i,new DayTask(date));
                return;
            }else if(dayTasks.get(i).getDate().equals(date)){
                return;
            }
        }
        dayTasks.add(new DayTask(date));
    }

    public void initDayTask(LocalDate start,LocalDate end){
        LocalDate current = start;
        while(current.isBefore(end)){
            addDayTask(current);
            current = current.plusDays(1);
        }
    }

    public void addTask(TaskUnit new_task,LocalDate date){
        for(DayTask dayTask:dayTasks){
            if(dayTask.getDate().equals(date)){
                dayTask.addTask(new_task);
                break;
            }
        }
    }
}
