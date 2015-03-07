package a3;

import java.util.*;

public class Top {

	public static void main(String[] args) {

		if (args.length != 3){

			System.err.println("Invalid number of input arguments");

			System.exit(1);

		}

		ParseFile parser = new ParseFile();	

		GroupBy grouper = new GroupBy();

		String filePath = args[0];

		int numberOfValues = Integer.parseInt(args[1]);

		String stringCategory = args[2];

		int category = -1;

		if (stringCategory.compareTo("sectors") == 0) {

			category = 0;

		} else if (stringCategory.compareTo("employers") == 0) {

			category = 1;

		} else if (stringCategory.compareTo("positions") == 0) {

			category = 2;

		} else if (stringCategory.compareTo("names") == 0) {

			category = 3;

		} else {

			System.err.println("Invalid grouping parameter, use one of the following:\n"
				+ "sectors\n"
				+ "employers\n"
				+ "positions\n"
				+ "names\n");

			System.exit(1);

		}

		try {

			grouper.setData(parser.load(filePath));

		} catch (Exception e) {

			System.err.println("Invalid file parameter, " 
				+ "specify file relative to directory of execution.");

			System.exit(1);

		}

		grouper.groupby(category);

		grouper.printTopK(numberOfValues);

	}

}
