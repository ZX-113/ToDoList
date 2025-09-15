package data.task;

import java.util.ArrayList;

import data.mytype.MyDate;

public class DayTask {
	MyDate date;
	ArrayList<TaskUnit> day_task_list;
	
	public DayTask() {
		date = new MyDate();
		day_task_list = new ArrayList<TaskUnit>();
	}
	public DayTask(MyDate Date) {
		date = Date;
		day_task_list = new ArrayList<TaskUnit>();
	}
	public DayTask(MyDate Date,ArrayList<TaskUnit> list) {
		date = Date;
		day_task_list = list;
	}
	
	public void add(TaskUnit task_unit) {
		day_task_list.add(task_unit);
	}
	
	public TaskUnit GetTaskUnit(int idx) {
		if(idx < 0||idx >= day_task_list.size())return null;
		return day_task_list.get(idx);
	}
	
	public void Print() {
		date.Print();
		for(TaskUnit taskunit:day_task_list) {
			taskunit.Print();
		}
	}
}
