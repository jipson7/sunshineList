package csci2020u.a3;

import java.util.*;

public class Top {

	private static String tempFilePath = "csci2020u/a3/data/output.txt";

	public static void main(String[] args) {

		ParseFile parser = new ParseFile();	

		GroupBy grouper = new GroupBy();

		try {

			grouper.setData(parser.load(tempFilePath));

		} catch (Exception e) {

			System.err.println("File load failure");

		}

		grouper.groupby(0);

		grouper.printTopK(10);

	}

}
