package csci2020u.a3;

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
			
		} else if (byAttribute == 1) {

			createMapEmployer();

		} else if (byAttribute == 2) {

			createMapPosition();

		} else if (byAttribute == 3) {

			createMapName();

		} else {

			System.out.println("Invalid Selection");

			System.exit(1);

		}

	}

	public void printTopK(int k) {



	}

	private void createMapSector(){

		currentOrganization = 0;

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

		currentOrganization = 1;

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

		currentOrganization = 2;

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

	private void createMapName() {

		currentOrganization = 3;	

	}
}
