package data.mytype;

import java.time.LocalDate;

public class MyDate {
	public int year;
	public int month;
	public int day;
	
	public static final int[] Days=
		{0,31,28,31,30,31,30,31,31,30,31,30,31};
	public static final int[] LeapDays= 
		{0,31,29,31,30,31,30,31,31,30,31,30,31};
	
	public MyDate() {
		year = 0;
		month = 1;
		day = 1;
	}
	public MyDate(int Y,int M,int D) {
		year = Y;
		month = M;
		day = D;
	}
	
	public int GetWeekDay() {
		int M = month;
		int Y = year;
		if (M < 3) {
			M += 12;
			Y--;
		}

		int q = day;        // 日期
		int K = Y % 100;    // 年份后两位
		int J = Y / 100;    // 年份前两位

		// 应用蔡勒公式
		int h = (int)(q + Math.floor(13 * (M + 1) / 5.0) + K + Math.floor(K / 4.0) + Math.floor(J / 4.0) + 5 * J) % 7;

		// 将h转换为0=星期日，1=星期一，…，6=星期六
		h = (h + 6) % 7;  

		return h;
	}
	
	public boolean equal(MyDate other) {
		if(year == other.year && month == other.month && day == other.day)return true;
		else return false;
	}
	
	public static boolean IsLeap(int Y) {
		if ((Y % 400 == 0) || (Y % 4 == 0 && Y % 100 != 0))return true;
		else return false;
	}
	
	public void Print() {
		System.out.printf("%d/%d/%d\n",year,month,day);
	}
	
	public static MyDate GetNow() {
		LocalDate currentDate = LocalDate.now();
		int Y = currentDate.getYear();
		int M = currentDate.getMonthValue();
		int D = currentDate.getDayOfMonth();
		return new MyDate(Y,M,D);
	}
	
	public int GetDay() {
		return day;
	}
	
	public int GetMonth() {
		return month;
	}
	
	public int GetYear() {
		return year;
	}
	
}
