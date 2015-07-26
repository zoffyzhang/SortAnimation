package SortAlgorithms;

import UI.AnimationPanel;

public class Heap
{
	public volatile static int changing;
	public volatile static int position;

	public static Thread thread;

	@SuppressWarnings("rawtypes")
	public static void sort(final Comparable[] pq)
	{
		thread = new Thread()
		{
			@Override
			public void run()
			{
				int N = pq.length;
				for (int k = N / 2; k >= 1; k--)
					sink(pq, k, N);
				while (N > 1)
				{
					position = N - 1;
					try
					{
						sleep(AnimationPanel.SPF);
					} catch (InterruptedException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					exch(pq, 1, N--);
					sink(pq, 1, N);
				}
			}
		};
		thread.start();
	}

	@SuppressWarnings("rawtypes")
	private static void sink(final Comparable[] pq, int k, int N)
	{
		while (2 * k <= N)
		{
			int j = 2 * k;
			if (j < N && less(pq, j, j + 1))
				j++;
			if (!less(pq, k, j))
				break;
			try
			{
				Thread.sleep(AnimationPanel.SPF);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			changing = j;		//This might not be right !!!
			exch(pq, k, j);
			k = j;
		}
	}

	@SuppressWarnings("rawtypes")
	private static void exch(Comparable[] pq, int i, int j)
	{
		Comparable swap = pq[i - 1];
		pq[i - 1] = pq[j - 1];
		pq[j - 1] = swap;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static boolean less(Comparable[] pq, int i, int j)
	{
		return pq[i - 1].compareTo(pq[j - 1]) < 0;
	}

	@SuppressWarnings("rawtypes")
	public static void show(Comparable[] pq)
	{
		for (int i = 0; i < pq.length; i++)
			System.out.print(pq[i] + " ");
		System.out.println();
	}

	@SuppressWarnings("rawtypes")
	public static boolean isSorted(Comparable[] pq)
	{
		for (int i = 2; i < pq.length; i++)
			if (less(pq, i, i - 1))
				return false;
		return true;
	}
}
