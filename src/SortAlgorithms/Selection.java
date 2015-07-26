package SortAlgorithms;

import UI.AnimationPanel;

public class Selection
{
	public static volatile int changing;
	public static volatile int position;

	// O(N^2)
	@SuppressWarnings("rawtypes")
	public static void sort(final Comparable[] a)
	{
		new Thread()
		{
			@Override
			public void run()
			{
				for (int i = 0; i < a.length; i++)
				{
					int min = i;
					for (int j = i + 1; j < a.length; j++)
						if (less(a[j], a[min]))
							min = j;
					try
					{
						sleep(AnimationPanel.SPF);
					} catch (InterruptedException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					position=i;
					changing=min;
					exch(a, i, min);
				}
			}
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
