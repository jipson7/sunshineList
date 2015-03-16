import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.*;
import java.io.*;
import java.awt.*;
import java.util.*;

public class My {

	private int AVERAGE_ONTARIO_SALARY = 49088;

	public static BufferedImage convertToBufferedImage(Image image) {

		BufferedImage newImage = new BufferedImage(
			image.getWidth(null), image.getHeight(null),
			BufferedImage.TYPE_INT_ARGB);

		Graphics2D g = newImage.createGraphics();

		g.drawImage(image, 0, 0, null);

		g.dispose();

		return newImage;

	}

	private ImageIcon getScaledImage(float scale){

		BufferedImage imgInput = null;

		float widthOriginal = 320;
		float heightOriginal = 640;

		try {

			imgInput = ImageIO.read(new File("data/stick.png"));

		} catch (IOException e) {

			System.err.println("The image 'stick.png' could not be found");
			System.exit(1);

		}

		int widthNew = (int) (widthOriginal * Math.sqrt(scale));
		int heightNew = (int) (heightOriginal * Math.sqrt(scale));

		BufferedImage imgResize =  convertToBufferedImage(imgInput.getScaledInstance(widthNew, heightNew, Image.SCALE_SMOOTH));

		ImageIcon imgIcon = new ImageIcon(imgResize);

		return imgIcon;

	}

	private TreeMap<Float, String> pullTopTwoEarners() {

		ParseFile parser = new ParseFile();

		TreeMap<Float, String> allEarners = new TreeMap<Float, String>(Collections.reverseOrder());
		TreeMap<Float, String> topEarners = new TreeMap<Float, String>(Collections.reverseOrder()); 

		try {

			java.util.List<Record> personList = parser.load("data/output.txt");

			for (Record x : personList) {

				allEarners.put(x.salary, x.name);

			}


			int counter = 0;

			for(Map.Entry<Float, String> entry : allEarners.entrySet()) {

				topEarners.put(entry.getKey(), entry.getValue());

				counter++;

				if (counter >= 2) {

					break;

				}

			}



		} catch (Exception e) {

			System.err.println("File Load Failed");
			System.exit(1);

		}

		
		return topEarners;



	}


	public static void main(String[] args) {

		My driver = new My();

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		TreeMap<Float, String> topTwo = driver.pullTopTwoEarners();
		ArrayList<JLabel> stickmen = new ArrayList<JLabel>();

		ArrayList<String> picLabels = new ArrayList<String>();

		boolean first = true;
		float maxValue = 0f;

		for(Map.Entry<Float, String> entry : topTwo.entrySet()) {

			if (first) {

				maxValue = entry.getKey();
				first = false;

			}

			picLabels.add(entry.getValue() + ", worth " + Float.toString(entry.getKey()));

			float scale = entry.getKey() / maxValue;

			ImageIcon img = driver.getScaledImage(scale);

			JLabel labelImage = new JLabel(img);
			stickmen.add(labelImage);

		}

		JPanel leftPanel = new JPanel();
		BoxLayout bl1 = new BoxLayout(leftPanel, BoxLayout.Y_AXIS);
		leftPanel.setLayout(bl1);
		leftPanel.add(new JLabel(picLabels.get(0)));
		leftPanel.add(stickmen.get(0));

		panel.add(leftPanel, BorderLayout.LINE_START);

		JPanel centerPanel = new JPanel();
		BoxLayout bl2 = new BoxLayout(centerPanel, BoxLayout.Y_AXIS);
		centerPanel.setLayout(bl2);
		centerPanel.add(new JLabel(picLabels.get(1)));
		centerPanel.add(stickmen.get(1));

		panel.add(centerPanel, BorderLayout.CENTER);

		float normScale = driver.AVERAGE_ONTARIO_SALARY / maxValue;

		ImageIcon normImg = driver.getScaledImage(normScale);
		JLabel normLabel = new JLabel(normImg);

		JPanel rightPanel = new JPanel();
		BoxLayout bl3 = new BoxLayout(rightPanel, BoxLayout.Y_AXIS);
		rightPanel.setLayout(bl3);
		rightPanel.add(new JLabel("AVERAGE ONTARIAN, worth " + Integer.toString(driver.AVERAGE_ONTARIO_SALARY)));
		rightPanel.add(normLabel);

		panel.add(rightPanel, BorderLayout.LINE_END);

		panel.add(new JLabel("Area of each stick figure is relative to their salary. Twice the area, twice the pay."), BorderLayout.PAGE_END);


		frame.getContentPane().add(panel);
		frame.setSize(900, 700);

		frame.setVisible(true);

	}

}
