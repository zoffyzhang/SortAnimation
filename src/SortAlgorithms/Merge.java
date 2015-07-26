package SortAlgorithms;

import UI.AnimationPanel;

public class Merge
{
	public volatile static int changing = 0;
	public volatile static int position;

	@SuppressWarnings("rawtypes")
	private static Comparable[] aux;

	@SuppressWarnings("rawtypes")
	public static void sort(final Comparable[] a)
	{
		new Thread()
		{
			@Override
			public void run()
			{
				aux = new Comparable[a.length];
				sort(a, 0, a.length - 1);
			}
		}.start();
	}

	@SuppressWarnings("rawtypes")
	private static void sort(Comparable[] a, int lo, int hi)
	{

		if (hi <= lo)	// 注意是<=
			return;
		int mid = lo + (hi - lo) / 2;
		sort(a, lo, mid);
		sort(a, mid + 1, hi);
		merge(a, lo, mid, hi);
	}

	@SuppressWarnings("rawtypes")
	private static void merge(Comparable[] a, int lo, int mid, int hi)
	{
		int i = lo;
		int j = mid + 1;

		for (int k = lo; k <= hi; k++)
			aux[k] = a[k];

		for (int k = lo; k <= hi; k++)
		{
			try
			{
				Thread.sleep(AnimationPanel.SPF);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			position = k;
			if (i > mid)
				a[k] = aux[j++];
			else if (j > hi)
				a[k] = aux[i++];
			else if (less(aux[j], aux[i]))
				a[k] = aux[j++];
			else
				a[k] = aux[i++];
		}
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
