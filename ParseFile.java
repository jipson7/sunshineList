package csci2020u.a3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.*;


class ParseFile implements RecordLoader {

	private String sectorRegex = "<h1> Public Sector Salary Disclosure for 2013: Government of Ontario : ([A-Za-z ]+)</h1>";

	private String employerRegex = "<td colspan=\"2\" align=\"left\" valign=\"top\"><span lang=\"en\">"
		+ "([A-Za-z&,\\-/áéíóúô'’\\. ]+)";

	private String lastNameRegex = "<td align=\"left\" valign=\"top\">([A-Z\\-áéíóúô'’ ]+)</td>";

	private String firstNameRegex = "<td colspan=\"2\" align=\"left\" valign=\"top\">([A-Z\\-áéíóúô'’ ]+)</td>";

	private String jobRegex = "([A-Za-z&,\\-/áéíóúô'’\\. ]+) </span>&nbsp;/&nbsp;<span lang=\"fr_ca\">";

	private String incomeRegex = "<td align=\"right\" valign=\"top\">([$\\d,\\.]+)</td>";

	public List<Record> load(String filename) throws Exception {

		List<Record> sunshineList = new ArrayList<Record>();

		try {

			BufferedReader inputFile = new BufferedReader(new FileReader(filename));			

			Pattern sectorPattern = Pattern.compile(sectorRegex);

			Pattern employerPattern = Pattern.compile(employerRegex);

			Pattern lastPattern = Pattern.compile(lastNameRegex, Pattern.CASE_INSENSITIVE);

			Pattern firstPattern = Pattern.compile(firstNameRegex, Pattern.CASE_INSENSITIVE);

			Pattern jobPattern = Pattern.compile(jobRegex);

			Pattern incomePattern = Pattern.compile(incomeRegex);

			Matcher sectorMatcher, employerMatcher, lastMatcher, firstMatcher, jobMatcher, incomeMatcher;

			String currentLine, currentSector, currentName;

			currentLine = currentSector = currentName = "";

			Record currentRecord = null;


			while ((currentLine = inputFile.readLine()) != null) {

				sectorMatcher = sectorPattern.matcher(currentLine);	

				if (sectorMatcher.find()) {

					currentSector = sectorMatcher.group(1);

				}

									
				employerMatcher = employerPattern.matcher(currentLine);

				if (employerMatcher.find()) {

					currentRecord = new Record();	

					currentRecord.employer = employerMatcher.group(1);

					currentRecord.sector = currentSector;

				}

				lastMatcher = lastPattern.matcher(currentLine);

				if (lastMatcher.find()) {

					currentName = lastMatcher.group(1);	

				}

				firstMatcher = firstPattern.matcher(currentLine);

				if (firstMatcher.find()) {

					currentName = currentName + ", " + firstMatcher.group(1);

					currentRecord.name = currentName;

				}

				jobMatcher = jobPattern.matcher(currentLine);

				if (jobMatcher.find()) {

					currentRecord.position = jobMatcher.group(1);

				}

				incomeMatcher = incomePattern.matcher(currentLine);

				if (incomeMatcher.find()) {

					String stringSalary = incomeMatcher.group(1);

					stringSalary = stringSalary.replace("$", "");

					stringSalary = stringSalary.replace(",", "");

					currentRecord.salary = Float.parseFloat(stringSalary);

				}

				sunshineList.add(currentRecord);

			}

			inputFile.close();

		} catch (Exception e) {

			System.out.println("Something has gone terribly wrong");

			System.exit(1);

		}


		return sunshineList;

	}

}
