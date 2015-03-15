import java.util.*;

public class parseDriver {

	public static void main(String[] args) throws Exception {

		String filePath = "data/output.txt";

		try {

			ParseFile driver = new ParseFile();

			List<Record> sunshineList = driver.load(filePath);

			for (Record x : sunshineList) {

				if (x != null) {

					System.out.println(x.name + ", " + Float.toString(x.salary) 
							+ ", " + x.position + ", " + x.employer + ", " + x.sector);

				}

			}

		} catch (Exception e) {

			//Do something
		
		}

	}

}
