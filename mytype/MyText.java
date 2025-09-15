package data.mytype;

import java.util.ArrayList;

public class MyText {
	ArrayList<String> text;
	
	public MyText() {
		text = new ArrayList<String>();
	}
	
	public MyText(ArrayList<String>list) {
		text = list;
	}
	
	public void add(String line) {
		text.add(line);
	}
	
	public void Print() {
		System.out.println("content:\n{");
		for(String line : text) {
			System.out.println(line);
		}
		System.out.println("}");
	}
}
