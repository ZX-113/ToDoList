package data.task;

import java.util.ArrayList;
import data.mytype.MyDate;

public class Task {
	ArrayList<DayTask> task_list;
	
	
	public Task() {
		task_list = new ArrayList<DayTask>();
	}
	
	public void add(DayTask day_task) {
		task_list.add(day_task);
	}
	
	public void AddTask(TaskUnit new_task,MyDate new_date) {
		for(DayTask day:task_list) {
			if(day.date.equal(new_date)) {
				day.add(new_task);
				break;
			}
		}
	}
	
	public void InitTask(int start_year,int end_year) {
		for(int y = start_year; y <= end_year; y++) {
			for(int m = 1; m <= 12; m++) {
				
				int all_days = MyDate.Days[m];
				if(MyDate.IsLeap(y) && m == 2) all_days++;
				
				for(int d = 1; d <= all_days; d++) 
					add(new DayTask(new MyDate(y,m,d)));
				
			}
		}
	}
	
	public DayTask GetDayTask(MyDate date) {
		for(DayTask day_task:task_list) {
			if(day_task.date.equal(date))return day_task;
		}
		return null;
	}
	
	public MyDate GetDate() {
		return null;
	}
	
	public void Print() {
		for(DayTask daytask:task_list) {
			daytask.Print();
			System.out.println();
		}
	}
	
	public void Print(MyDate date) {
		for(DayTask daytask:task_list) {
			if(daytask.date.equal(date)) {
				daytask.Print();
				break;
			}
		}
	}
}
