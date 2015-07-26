package SortAlgorithms;

import UI.AnimationPanel;

public class Shell
{
	public static volatile int changing;
	public static volatile int position;

	@SuppressWarnings("rawtypes")
	public static void sort(final Comparable[] a)
	{
		new Thread()
		{
			@Override
			public void run()
			{
				int N = a.length;
				int gap = 1;
				while (gap < N / 3)
					gap = 3 * gap + 1; // Knuth的取法
				while (gap >= 1)
				{
					for (int i = gap; i < a.length; i++)
						for (int j = i; j >= gap; j -= gap)
							if (less(a[j], a[j - gap]))
							{
								try
								{
									sleep(AnimationPanel.SPF);
								} catch (InterruptedException e)
								{
									e.printStackTrace();
								}
								position = i;
								changing = j - gap;
								exch(a, j, j - gap);
							}
					gap /= 3;
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
