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

		Pie pieChart = new Pie();

		JFrame win = new JFrame("PieChart");
		win.setSize(800, 500);
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel global = new JPanel();
		global.setLayout(new FlowLayout(FlowLayout.TRAILING));

		global.add(pieChart.createPieContainer(pieChart));
		global.add(pieChart.createLabelContainer(pieChart));
		global.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

		win.add(global);

		win.setVisible(true);

	}

	private JPanel createLabelContainer(Pie pieChart) {

		JPanel rightContainer = new JPanel();
		rightContainer.setLayout(new BoxLayout(rightContainer, BoxLayout.Y_AXIS));

		for (int i = 0; i < pieSlices.size(); i++) {

			rightContainer.add(pieChart.createSliceLabel(pieSlices.get(i)));
			rightContainer.add(new JLabel("---------"));

		}

		return rightContainer;

	}

	private JLabel createSliceLabel(PieSlice p) {

		JLabel label = new JLabel(p.name);	

		label.setBackground(p.color);

		label.setOpaque(true);

		return label;
		

	}

	private JPanel createPieContainer(Pie pieChart) {

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

		pieChart.addPieSlices(sliceTree);

		leftContainer.add(pieChart);

		return leftContainer;

	}



}
