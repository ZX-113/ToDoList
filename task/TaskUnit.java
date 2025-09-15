package data.task;

import data.mytype.MyState;
import data.mytype.MyText;

public class TaskUnit {
	String title;
	MyText content;
	MyState state;
	
	public TaskUnit() {
		title = new String();
		content = new MyText();
		state = new MyState();
	}
	public TaskUnit(String Title,MyState State) {
		title = Title;
		state = State;
		content = new MyText();
	}
	
	public TaskUnit(String Title,MyState State,MyText text) {
		title = Title;
		state = State;
		content = text;
	}
	
	public final MyState GetState() {
		MyState new_state = state.Copy();
		return new_state;
	}
	
	public void SetFinish(boolean is_finished) {
		state.SetFinish(is_finished);
	}
	public void SetOut(boolean is_out) {
		state.SetOut(is_out);
	}
	
	public void AddContent(String line) {
		content.add(line);
	}
	
	public void SetContent(MyText Content) {
		content = Content;
	}
	
	public void Print() {
		System.out.println("title:{" + title+"}");
		state.Print();
		content.Print();
	}
	
}
