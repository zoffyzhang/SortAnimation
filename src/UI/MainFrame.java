package UI;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MainFrame extends JFrame
{
	private static final long serialVersionUID = 4641615207322012526L;

	public MainFrame(String title)
	{
		super(title);
		add(new MainPanel());
		pack();
		int width = this.getWidth();
		int height = this.getHeight();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - width) / 2, (screenSize.height - height) / 2);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
	}

	public static void main(String[] args)
	{
		// NimbusLookAndFeel may result in GUI initialize failure sometimes
		try
		{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e)
		{
			e.printStackTrace();
		}
		new MainFrame("Sort Animation");
	}
}
