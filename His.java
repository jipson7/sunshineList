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
        chartPanel.setPreferredSize(new Dimension(500, 270));
        setContentPane(chartPanel);

    }

    private TreeMap<Integer, Integer> parseData() {

    	ParseFile parser = new ParseFile();

		TreeMap<Integer, Integer> histogramData = new TreeMap<Integer, Integer>(Collections.reverseOrder());

		int binSize = 17;
		
		try {

			java.util.List<Record> personList = parser.load("a3/data/output.txt");


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

			System.err.println("File load filled");
			System.exit(1);

		}

		return histogramData;

    }

    private CategoryDataset createDataset() {
        
        DefaultCategoryDataset hisData = new DefaultCategoryDataset();
        
        hisData.setValue(20000, "amount", "January"); 
        hisData.setValue(17500, "amount", "February");
        hisData.setValue(23000, "amount", "March");        
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

