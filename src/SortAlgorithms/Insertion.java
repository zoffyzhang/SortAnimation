package SortAlgorithms;

import UI.AnimationPanel;

public class Insertion
{
	public volatile static int changing;
	public volatile static int position;

	@SuppressWarnings("rawtypes")
	public static void sort(final Comparable[] a)
	{
		new Thread()
		{
			@Override
			public void run()
			{
				for (int i = 1; i < a.length; i++)
					for (int j = i; j > 0; j--)
						if (less(a[j], a[j - 1]))
						{
							if (!AnimationPanel.threadContinue)
								return;
							try
							{
								sleep(AnimationPanel.SPF);
							} catch (InterruptedException e)
							{
								e.printStackTrace();
							}
							position = i;
							changing = j - 1;
							exch(a, j, j - 1);
						}
			};
		}.start();
	}

	@SuppressWarnings("rawtypes")
	private static void exch(Comparable[] a, int i, int j)
	{
		Comparable tmp = a[i];
		a[i] = a[j];
		a[j] = tmp;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static boolean less(Comparable x, Comparable y)
	{
		return x.compareTo(y) < 0;
	}

	@SuppressWarnings("rawtypes")
	public static void show(Comparable[] a)
	{
		for (int i = 0; i < a.length; i++)
			System.out.print(a[i] + " ");
		System.out.println();
	}

	@SuppressWarnings("rawtypes")
	public static boolean isSorted(Comparable[] a)
	{
		for (int i = 1; i < a.length; i++)
			if (less(a[i], a[i - 1]))
				return false;
		return true;
	}
}
