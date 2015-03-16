import java.util.*;
import java.awt.Dimension;
import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class His extends ApplicationFrame {

    public His(final String title) {

        super(title);

        final CategoryDataset dataset = createDataset();
        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(1200, 1000));
        setContentPane(chartPanel);

    }

    private TreeMap<Integer, Integer> parseData() {

    	ParseFile parser = new ParseFile();

		TreeMap<Integer, Integer> histogramData = new TreeMap<Integer, Integer>();

		int binSize = 17;
		
		try {

			java.util.List<Record> personList = parser.load("data/output.txt");


			for (int i = 100 + binSize; i <= 1800; i = i + binSize) {

				histogramData.put(i, 0);

			}

			for (Record person : personList) {

				int currentSalary = (int) person.salary / 1000;

				for (int i = 100 + binSize; i <= 1800; i = i + binSize) {

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

			System.err.println("File load failed");
			System.exit(1);

		}

		return histogramData;

    }

    private CategoryDataset createDataset() {
        
        DefaultCategoryDataset hisData = new DefaultCategoryDataset();

		TreeMap<Integer, Integer> rawData = parseData();

		for(Map.Entry<Integer, Integer> entry : rawData.entrySet()) {
			Integer key = entry.getKey();
			Integer value = entry.getValue();

			String currLabel = Integer.toString(key - 17) + " - " + Integer.toString(key);

			hisData.setValue(value, "amount", currLabel);

		}	
        
		return hisData;
        
    }
    
    private JFreeChart createChart(final CategoryDataset dataset) {
        
        final JFreeChart chart = ChartFactory.createBarChart("His 100518555", "Salary Range", "# of People", dataset,           
            PlotOrientation.VERTICAL, false, true, false);

        return chart;
        
    }
    
    public static void main(final String[] args) {

        final His histogram = new His("Bar Chart Demo");
        histogram.pack();
        RefineryUtilities.centerFrameOnScreen(histogram);
        histogram.setVisible(true);

    }

}

