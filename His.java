package a3;

import java.util.*;
import java.awt.*;
import javax.swing.*;

public class His {

	private TreeMap<Integer, Integer> histogramData;

	private void generateData() {

		ParseFile parser = new ParseFile();

		histogramData = new TreeMap<Integer, Integer>(Collections.reverseOrder());
		
		try {

			java.util.List<Record> personList = parser.load("a3/data/output.txt");


			for (int i = 125; i <= 1800; i = i + 25) {

				histogramData.put(i, 0);

			}

			for (Record person : personList) {

				int currentSalary = (int) person.salary / 1000;

				for (int i = 125; i <= 1800; i = i + 25) {

					if (currentSalary < i) {

						int count = histogramData.get(i) + 1;

						histogramData.put(i, count);

						break;

					} else {

						continue;

					}

				}

			}


		} catch (Exception e) {

			System.err.println("File load filled");
			System.exit(1);

		}

	}

	public His() {

		generateData();

	}

	public static void main(String[] args) {



	}

	private printMap() {

		His test = new His();

		for (Map.Entry<Integer, Integer> entry : test.histogramData.entrySet()) {

			 System.out.println("Key: " + entry.getKey() + ". Value: " + entry.getValue());

		}

	}

}
