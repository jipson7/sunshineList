import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.*;
import java.io.*;
import java.awt.*;
import java.util.*;

public class My {

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

		int widthNew = (int) (widthOriginal * scale);
		int heightNew = (int) (heightOriginal * scale);

		BufferedImage imgResize =  convertToBufferedImage(imgInput.getScaledInstance(widthNew, heightNew, Image.SCALE_SMOOTH));

		ImageIcon imgIcon = new ImageIcon(imgResize);

		return imgIcon;

	}

	private TreeMap<Float, String> pullTopTenEarners() {

		ParseFile parser = new ParseFile();

		TreeMap<Float, String> allEarners = new TreeMap<Float, String>();
		TreeMap<Float, String> topEarners = new TreeMap<Float, String>(); 

		try {

			java.util.List<Record> personList = parser.load("data/output.txt");

			for (Record x : personList) {

				allEarners.put(x.salary, x.name);

			}


			for(Map.Entry<Float, String> entry : allEarners.entrySet()) {

				topEarners.put(entry.getKey(), entry.getValue());

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

		TreeMap<Float, String> topTen = driver.pullTopTenEarners();

		ImageIcon img = driver.getScaledImage(0.5f);

		JLabel imageTest = new JLabel(img);

		panel.add(imageTest, BorderLayout.LINE_START);

		frame.getContentPane().add(panel);
		frame.setSize(1000, 800);

		frame.setVisible(true);

	}

}
