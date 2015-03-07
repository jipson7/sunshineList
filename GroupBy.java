package a3;

import java.util.*;

class GroupBy implements IGroupBy {

	private List<Record> records;

	private Map<String, List<Record>> recordMap;

	private int currentOrganization = 3;

	public void setData(List<Record> records) {

		this.records = records;

	}

	public void groupby(int byAttribute) {

		if (byAttribute == 0) {

			createMapSector();

			currentOrganization = 0;
			
		} else if (byAttribute == 1) {

			createMapEmployer();

			currentOrganization = 1;

		} else if (byAttribute == 2) {

			createMapPosition();

			currentOrganization = 2;

		} else if (byAttribute == 3) {

			currentOrganization = 3;	

		} else {

			System.err.println("Invalid Selection");

			System.exit(1);

		}

	}

	public void printTopK(int k) {

		Map<Float, String> topList = new TreeMap<Float, String>(Collections.reverseOrder());

		if (currentOrganization != 3) {

			List<Record> temp;

			for (String current : recordMap.keySet()) {

				temp = recordMap.get(current);	

				Float runningTotal = (float) 0;

				for (Record currRecord : temp) {

					runningTotal += currRecord.salary;	


				}

				topList.put(runningTotal, current);			

			}

		} else {

			for (Record current : records) {

				topList.put(current.salary, current.name);	

			}

		}

		printKeyValuePairs(topList, k);

	}

	private void createMapSector(){

		recordMap = new HashMap<String, List<Record>>();	

		String sectKey;

		for (Record x : records) {

			sectKey = x.sector;

			if (recordMap.containsKey(sectKey)) {

				recordMap.get(sectKey).add(x);

			} else {

				List<Record> toAdd = new ArrayList<Record>();

				toAdd.add(x);

				recordMap.put(sectKey, toAdd);	

			}

		}

	}

	private void createMapEmployer() {

		recordMap = new HashMap<String, List<Record>>();	

		String employKey;

		for (Record x : records) {

			employKey = x.employer;

			if (recordMap.containsKey(employKey)) {

				recordMap.get(employKey).add(x);
	
			} else {

				List<Record> toAdd = new ArrayList<Record>();

				toAdd.add(x);

				recordMap.put(employKey, toAdd);	

			}

		}

	}

	private void createMapPosition() {

		recordMap = new HashMap<String, List<Record>>();	

		String posKey;

		for (Record x : records) {

			posKey = x.position;

			if (recordMap.containsKey(posKey)) {

				recordMap.get(posKey).add(x);
	
			} else {

				List<Record> toAdd = new ArrayList<Record>();

				toAdd.add(x);

				recordMap.put(posKey, toAdd);	

			}

		}

	}

	private void printKeyValuePairs(Map<Float, String> mapToPrint, int topNum) {

		int counter = 0;

		for (Map.Entry<Float, String> entry : mapToPrint.entrySet()) {

			System.out.printf("%60s : $%,.2f \n", entry.getValue(), entry.getKey());

			counter++;

			if (counter >= topNum) { break; }

		}				

	}
}
