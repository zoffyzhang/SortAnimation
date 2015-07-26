package SortAlgorithms;

import UI.AnimationPanel;

public class Quick
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
				sort(a, 0, a.length - 1);
			}
		}.start();
	}

	@SuppressWarnings("rawtypes")
	private static void sort(Comparable[] a, int lo, int hi)
	{
		if (lo >= hi)
			return;
		int j = partition(a, lo, hi);
		sort(a, lo, j - 1);
		sort(a, j + 1, hi);
	}

	@SuppressWarnings("rawtypes")
	private static int partition(Comparable[] a, int lo, int hi)
	{
		int i = lo, j = hi + 1;
		Comparable v = a[lo];
		while (true)
		{
			while (less(a[++i], v))
				if (i == hi)
					break;
			while (less(v, a[--j]))
				if (j == lo)
					break;
			if (i >= j)
				break;
			try
			{
				position = i;
				changing = j;
				Thread.sleep(AnimationPanel.SPF);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			exch(a, i, j);
		}
		try
		{
			position = lo;
			changing = j;
			Thread.sleep(AnimationPanel.SPF);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		exch(a, lo, j);
		return j;
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
