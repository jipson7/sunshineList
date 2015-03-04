package csci2020u.a3;

import java.util.*;

public class parseDriver {

	public static void main(String[] args) throws Exception {

		String filePath = "csci2020u/a3/data/output.txt";

		try {

			ParseFile driver = new ParseFile();

			List<Record> sunshineList = driver.load(filePath);

			System.out.println("The # of records is: " + sunshineList.size());

			for (Record x : sunshineList) {

				System.out.println(x.name + ", " + Float.toString(x.salary) + ", " + x.employer);

			}

		} catch (Exception e) {

			//Do something
		
		}

	}

}
