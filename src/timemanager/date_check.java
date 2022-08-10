package timemanager;

import java.time.LocalDate;
import java.time.Month;
import java.util.Scanner;

public class date_check {
	public static boolean takeindate(String date){
		Scanner sc = new Scanner(System.in);
		String d = null;
		LocalDate l = LocalDate.now();
		int day=1;
		int month=1;
		int year=1;
		int repeat=0;
		Month month_name= Month.JANUARY;
		boolean leap_year = false;
		try {
			repeat=0;
			d=date;
			day = Integer.parseInt(d.split("-")[0]);
			month = Integer.parseInt(d.split("-")[1]);
			year = Integer.parseInt(d.split("-")[2]);
			month_name = Month.of(month);
			if(((year%400==0)||(year%4==0))&&year%100!=0)
				leap_year=true;
				
			String now_date[] = l.toString().split("-");
			if(year<Integer.parseInt(now_date[0]))
				repeat=1;
			else if(year==Integer.parseInt(now_date[0])){
				if(month<Integer.parseInt(now_date[1]))
					repeat=1;
				else if(month==Integer.parseInt(now_date[1])){
					if(day<=Integer.parseInt(now_date[2]))
						repeat=1;
				}
			}
		}
		catch(Exception e) {
			repeat=1;
		}
		if(repeat==1||day>month_name.length(leap_year)||month>12||month<=0)
		return true;
		else
		return false;
	}
}
