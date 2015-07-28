package UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.Arrays;
import java.util.Random;
import javax.swing.JPanel;
import SortAlgorithms.Heap;
import SortAlgorithms.Insertion;
import SortAlgorithms.Merge;
import SortAlgorithms.Quick;
import SortAlgorithms.Quick3way;
import SortAlgorithms.Selection;
import SortAlgorithms.Shell;

public class AnimationPanel extends JPanel
{
	private static final long serialVersionUID = 5276766134062781729L;
	private int dataLength = 150;
	private Integer[] randomData = new Integer[dataLength];
	private Integer[] reversedData = new Integer[dataLength];
	private Integer[] naturalData = new Integer[dataLength];
	private Integer[] selectedData;

	private Random random = new Random();
	private int UpperBound = 100;
	private int LowerBound = -90;

	private final int height = 600;	// panel height
	private int width;

	// data rectangle properties
	private double yScale;
	private int rectangleWidth = 5;
	private int gap;		// gap between every two adjacent rectangles

	private boolean isInitStep = true;	// indicates the application is
										// not running the animation yet
	public static volatile boolean threadContinue;	// thread continue flag

	private String algo;		// selected algorithm
	private String data;		// selected data source
	public static int SPF;		// Seconds Per Frame

	public AnimationPanel()
	{
		width = (rectangleWidth + gap) * dataLength;
		setPreferredSize(new Dimension(width, height));

		generateRandomData();
		generateReversedData();
		genereateNaturalData();
		computeScale();
	}

	public void init()
	{
		setSelectedData("Random");	// default setting
		repaint();					// show default panel
	}

	public void reInit()
	{
		generateRandomData();
		generateReversedData();
		computeScale();
		setSelectedData(data);
		repaint();
	}

	// Start
	public void startAnimation(String algo, String data, int FPS)
	{
		this.algo = algo;
		this.data = data;
		SPF = 1000 / FPS;

		setSelectedData(data);
		startSortThread(algo);
		startPaintThread();
	}

	// Stop
	public void stopAnimation()
	{
		threadContinue = false;

	}

	private void startSortThread(String algo)
	{
		if (algo.equals("Quick"))
			Quick.sort(selectedData);
		else if (algo.equals("Heap"))
			Heap.sort(selectedData);
		else if (algo.equals("Shell"))
			Shell.sort(selectedData);
		else if (algo.equals("Insertion"))
			Insertion.sort(selectedData);
		else if (algo.equals("Selection"))
			Selection.sort(selectedData);
		else if (algo.equals("Merge"))
			Merge.sort(selectedData);
		else if (algo.equals("Quick3way"))
			Quick3way.sort(selectedData);
	}

	private void startPaintThread()
	{
		new Thread()
		{
			public void run()
			{
				while (threadContinue)
				{
					try
					{
						repaint();
						Thread.sleep(SPF);
					} catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

	@Override
	public void paint(Graphics g)
	{
		if (isInitStep)
		{
			super.paint(g);
			setCoordinate(g);
			drawAllRect(g);
		}
		else
		{
			super.paint(g);
			setCoordinate(g);
			drawAllRect(g);
			drawChangineRect(g);
		}
	}

	public void setSelectedData(String data)
	{
		if (data.equals("Random"))
			selectedData = randomData;
		else if (data.equals("Reversed"))
			selectedData = reversedData;
		else
			selectedData = naturalData;
	}

	// get ready to start animation
	public void getReady()
	{
		isInitStep = false;
		threadContinue = true;
	}

	private void generateRandomData()
	{
		int a = LowerBound;
		int b = UpperBound;
		if (a >= b)
			throw new IllegalArgumentException("Invalid range");
		for (int i = 0; i < randomData.length; i++)
		{
			randomData[i] = a + (int) (random.nextDouble() * (b - a));
		}
	}

	private void generateReversedData()
	{
		int[] tmp = new int[dataLength];
		for (int i = 0; i < randomData.length; i++)
		{
			tmp[i] = randomData[i];
		}
		Arrays.sort(tmp);
		for (int i = 0; i < tmp.length; i++)
		{
			reversedData[i] = tmp[dataLength - i - 1];
		}
	}

	private void genereateNaturalData()
	{
		for (int i = 0; i < naturalData.length; i++)
		{
			naturalData[i] = randomData[i];
		}
		Arrays.sort(naturalData);
	}

	// compute y-scale,negative integer is under consideration
	private void computeScale()
	{
		double max = Double.MIN_VALUE;
		double min = Double.MAX_VALUE;
		for (int i = 0; i < dataLength; i++)
		{
			if (randomData[i] > max)
				max = randomData[i];
			if (randomData[i] < min)
				min = randomData[i];
		}
		max = Math.abs(max);
		min = Math.abs(min);
		double absMAX = max > min ? max : min;
		yScale = height / 2 / absMAX;
	}

	private void setCoordinate(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		g2d.translate(0, height / 2);						// set origin coordinate
		g2d.scale(1.0, -1.0);								// reverse y-axis
		g2d.draw(new Line2D.Double(0.0, 0.0, width, 0.0));	// draw x-axis
	}

	private void drawAllRect(Graphics g)
	{
		g.setColor(Color.GRAY);
		for (int i = 0; i < dataLength; i++)
		{
			// due to setCoordinate(Graphics g)，the x and y coordinate of
			// fill3DRect() has been changed to bottom left
			if (selectedData[i] >= 0)
				g.fill3DRect(i * (rectangleWidth + gap), 0, rectangleWidth,
						(int) (selectedData[i] * yScale), true);
			else
			{
				int bottomLeftY = (int) (selectedData[i] * yScale);
				g.fill3DRect(i * (rectangleWidth + gap), bottomLeftY, rectangleWidth, -bottomLeftY,
						true);
			}
		}
	}

	// draw changing rectangle in red color
	// draw position rectangle in blue color
	private void drawChangineRect(Graphics g)
	{
		int cursor[] = getChangingAndPosition();
		int i = cursor[0];
		g.setColor(Color.RED);

		if (selectedData[i] >= 0)
			g.fill3DRect(i * (rectangleWidth + gap), 0, rectangleWidth,
					(int) (selectedData[i] * yScale), true);
		else
		{
			int bottomLeftY = (int) (selectedData[i] * yScale);
			g.fill3DRect(i * (rectangleWidth + gap), bottomLeftY, rectangleWidth, -bottomLeftY,
					true);

		}

		i = cursor[1];
		g.setColor(Color.BLUE);

		if (selectedData[i] >= 0)
			g.fill3DRect(i * (rectangleWidth + gap), 0, rectangleWidth,
					(int) (selectedData[i] * yScale), true);
		else
		{
			int leftButtomY = (int) (selectedData[i] * yScale);
			g.fill3DRect(i * (rectangleWidth + gap), leftButtomY, rectangleWidth, -leftButtomY,
					true);
		}
	}

	private int[] getChangingAndPosition()
	{
		int[] cursor = new int[2];
		if (algo.equals("Quick"))
		{
			cursor[0] = Quick.changing;
			cursor[1] = Quick.position;
		}
		else if (algo.equals("Heap"))
		{
			cursor[0] = Heap.changing;
			cursor[1] = Heap.position;
		}
		else if (algo.equals("Shell"))
		{
			cursor[0] = Shell.changing;
			cursor[1] = Shell.position;
		}
		else if (algo.equals("Insertion"))
		{
			cursor[0] = Insertion.changing;
			cursor[1] = Insertion.position;
		}
		else if (algo.equals("Selection"))
		{
			cursor[0] = Selection.changing;
			cursor[1] = Selection.position;
		}
		else if (algo.equals("Merge"))
		{
			cursor[0] = Merge.changing;
			cursor[1] = Merge.position;
		}
		else if (algo.equals("Quick3way"))
		{
			cursor[0] = Quick3way.changing;
			cursor[1] = Quick3way.position;
		}
		return cursor;
	}
}
