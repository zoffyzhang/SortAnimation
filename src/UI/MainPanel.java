package UI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import SortAlgorithms.Heap;

public class MainPanel extends JPanel
{
	private static final long serialVersionUID = 5169597692945859660L;
	private AnimationPanel animationPanel = new AnimationPanel();
	private JPanel settingPanel = new JPanel();

	private ButtonGroup algosButtonGroup = new ButtonGroup();
	private ButtonGroup dataButtonGroup = new ButtonGroup();

	private JRadioButton[] algoRadioButtons = new JRadioButton[6];
	private JRadioButton[] dataRadioButtons = new JRadioButton[3];

	private JSlider fpsSlider = new JSlider(1, 451, 50);

	private JButton button = new JButton("Start");

	public MainPanel()
	{
		// initialize the setting components
		for (int i = 0; i < algoRadioButtons.length; i++)
		{
			algoRadioButtons[i] = new JRadioButton();
			algosButtonGroup.add(algoRadioButtons[i]);
		}
		for (int i = 0; i < dataRadioButtons.length; i++)
		{
			dataRadioButtons[i] = new JRadioButton();
			dataButtonGroup.add(dataRadioButtons[i]);
		}
		algoRadioButtons[0].setText("Quick");
		algoRadioButtons[1].setText("Heap");
		algoRadioButtons[2].setText("Shell");
		algoRadioButtons[3].setText("Insertion");
		algoRadioButtons[4].setText("Selection");
		algoRadioButtons[5].setText("Merge");
		dataRadioButtons[0].setText("Random");
		dataRadioButtons[1].setText("Reversed");
		dataRadioButtons[2].setText("Natural");

		algoRadioButtons[0].setSelected(true);
		dataRadioButtons[0].setSelected(true);

		dataRadioButtons[0].addActionListener(new RadioListener());
		dataRadioButtons[1].addActionListener(new RadioListener());
		dataRadioButtons[2].addActionListener(new RadioListener());

		fpsSlider.setMajorTickSpacing(150);
		fpsSlider.setPaintTicks(true);
		fpsSlider.setPaintLabels(true);

		button.addActionListener(new ButtonListener());

		// add to setting panel
		BoxLayout boxlayout = new BoxLayout(settingPanel, BoxLayout.Y_AXIS);
		settingPanel.setLayout(boxlayout);
		settingPanel.add(Box.createVerticalStrut(20));
		settingPanel.add(new JLabel("<html><big>Tip:</big></html>"));
		settingPanel.add(new JLabel("<html><em>The animation duration and the </em></html>"));
		settingPanel.add(new JLabel("<html><i>time complexity of the algorithm are</i></html>"));
		settingPanel.add(new JLabel("<html><b>nonlinear related !!! </b></html>"));

		settingPanel.add(Box.createVerticalStrut(50));
		settingPanel.add(new JLabel("Algorithm:"));
		for (int i = 0; i < algoRadioButtons.length; i++)
			settingPanel.add(algoRadioButtons[i]);
		settingPanel.add(Box.createVerticalStrut(20));
		settingPanel.add(new JLabel("Data:"));
		for (int i = 0; i < dataRadioButtons.length; i++)
			settingPanel.add(dataRadioButtons[i]);
		settingPanel.add(Box.createVerticalStrut(20));
		settingPanel.add(new JLabel("Speed (Frames Per Second):"));
		settingPanel.add(fpsSlider);
		settingPanel.add(Box.createVerticalStrut(30));
		settingPanel.add(button);

		animationPanel.init();

		// add to main panel
		this.setLayout(new BorderLayout());
		this.add(animationPanel, BorderLayout.CENTER);
		this.add(settingPanel, BorderLayout.EAST);
		this.add(new JLabel("git@github.com:zoffyzhang"),BorderLayout.SOUTH);

	}

	private class RadioListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			String cmd = e.getActionCommand();
			animationPanel.setSelectedData(cmd);
			animationPanel.repaint();
		}
	}

	private class ButtonListener implements ActionListener
	{
		String algo, data;
		int FPS;

		@Override
		public void actionPerformed(ActionEvent e)
		{
			if (e.getActionCommand().equals("Start"))
			{
				getParameters();
				setComponentsEnable(false);
				button.setText("Reset With New Data");
				animationPanel.getReady();
				animationPanel.startAnimation(algo, data, FPS);
			}
			else
			{
				animationPanel.stopAnimation();
				try
				{
					// fix the thread problem of Heap Sort
					if (Heap.thread != null && Heap.thread.isAlive())
					{
						AnimationPanel.SPF = 1;	// animation is still running at
												// background,speed it up
						Heap.thread.join();
					}
				} catch (InterruptedException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				animationPanel.reInit();
				setComponentsEnable(true);
				button.setText("Start");
			}
		}

		private void getParameters()
		{
			for (JRadioButton jRadioButton : algoRadioButtons)
			{
				if (jRadioButton.isSelected())
				{
					algo = jRadioButton.getText();
					break;
				}
			}
			for (JRadioButton jRadioButton : dataRadioButtons)
			{
				if (jRadioButton.isSelected())
				{
					data = jRadioButton.getText();
					break;
				}
			}
			FPS = fpsSlider.getValue();
		}

		private void setComponentsEnable(boolean b)
		{
			for (JRadioButton jRadioButton : algoRadioButtons)
				jRadioButton.setEnabled(b);
			for (JRadioButton jRadioButton : dataRadioButtons)
				jRadioButton.setEnabled(b);
			fpsSlider.setEnabled(b);
		}
	}
}