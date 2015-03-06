import java.awt.*;
import javax.swing.*;
import java.util.*;

public class graphicDriver {

	public static void main(String[] args) {

		Map<Float, String> testTree = new TreeMap<Float, String>();

		for (int i = 0; i < 10; i++) {

			testTree.put((float) i, "test");

		}

		JFrame win = new JFrame("PieChart");
		win.setSize(400, 400);
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Pie pieChart = new Pie();

		pieChart.addPieSlices(testTree);

		win.getContentPane().add(pieChart);

		win.setVisible(true);

	}


}
