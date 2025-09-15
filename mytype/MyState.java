package data.mytype;

public class MyState {
	boolean out;
	boolean finish;
	
	public MyState() {
		out = true;
		finish = false;
	}
	public MyState(boolean is_out,boolean is_finished) {
		out = is_out;
		finish = is_finished;
	}
	
	public boolean IsOut() {
		return out;
	}
	public boolean IsFinished() {
		return finish;
	}
	
	public void SetFinish(boolean is_finished) {
		finish = is_finished;
	}
	public void SetOut(boolean is_out) {
		out = is_out;
	}
	
	public MyState Copy() {
		return new MyState(out,finish);
	}
	
	public void Print() {
		System.out.println("out:{"+out+"} finish:{"+finish+"}");
	}
}
