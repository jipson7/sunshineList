package a3;

import java.awt.*;
import javax.swing.*;
import java.util.*;


class Pie extends JComponent {

	class PieSlice {

		float value;
		String name;
		Color color;

		public PieSlice(float value, String name, Color color) {

			this.value = value;
			this.name = name;
			this.color = color;

		}

	}

	//////////////////////////////////////
	
	ArrayList<PieSlice> pieSlices;
	
	public void addPieSlices(Map<Float, String> sliceMap) {

		Random rand = new Random();

		pieSlices = new ArrayList<PieSlice>();

		float value;
		String name;
		Color color;

		for(Map.Entry<Float, String> entry : sliceMap.entrySet()) {

			value = entry.getKey();
		    name = entry.getValue();
			color = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());

			pieSlices.add(new PieSlice(value, name, color));

		}	
		
	}

	@Override
	public Dimension getPreferredSize() {

		return new Dimension(400, 400);

	}

	@Override
	public void paint(Graphics g) {

		drawPie((Graphics2D) g, getBounds(), pieSlices);
		
	}

	void drawPie(Graphics2D g, Rectangle area, ArrayList<PieSlice> slices) {

		float total = (float) 0;

		for (int i = 0; i < pieSlices.size(); i++) {

			total += pieSlices.get(i).value;

		}

		float currentValue = (float) 0;
		int currentAngle = 0;
		
		for (int i = 0; i < pieSlices.size(); i++) {

			PieSlice currentSlice = pieSlices.get(i);

			currentAngle = (int) (currentValue * 360 / total);

			int arc = (int) (currentSlice.value * 360 / total);

			g.setColor(currentSlice.color);

			g.fillArc(area.x, area.y, (area.width), area.height, currentAngle, arc);

			currentValue += currentSlice.value;

		}


	}

	public static void main(String[] args) {

		JFrame win = new JFrame("PieChart");
		win.setSize(800, 500);
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel global = new JPanel();
		global.setLayout(new FlowLayout(FlowLayout.TRAILING));

		global.add(createPieContainer());
		global.add(createLabelContainer());
		global.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

		win.add(global);

		win.setVisible(true);

	}

	static private JPanel createLabelContainer() {

		JPanel rightContainer = new JPanel();
		rightContainer.setLayout(new BoxLayout(rightContainer, BoxLayout.Y_AXIS));

		JTextField testText = new JTextField("This is a test");

		rightContainer.add(testText);

		return rightContainer;

	}

	static private JPanel createPieContainer() {

		Map<Float, String> sliceTree = new TreeMap<Float, String>();

		sliceTree.put(2238297088f, "Universities");
		sliceTree.put(1609449984f, "Hydro One and Ontario Power Generation");
		sliceTree.put(1290382208f, "Government of Ontario: Ministries");
		sliceTree.put(1256070272f, "Hospitals and Boards of Public Health");
		sliceTree.put(567345344f, "Other Public Sector Employers");
		sliceTree.put(472853280f, "Colleges");
		sliceTree.put(466554752f, "Crown Agencies");
		sliceTree.put(120456360f, "Government of Ontario: Judiciary");
		sliceTree.put(31375964f, "Government of Ontario: Legislative Assembly and Offices");

		JPanel leftContainer = new JPanel();
		//leftContainer.setLayout(new FlowLayout(FlowLayout.LEFT));

		Pie pieChart = new Pie();

		pieChart.addPieSlices(sliceTree);

		leftContainer.add(pieChart);

		return leftContainer;

	}



}
