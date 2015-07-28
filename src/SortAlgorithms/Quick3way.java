package SortAlgorithms;

import UI.AnimationPanel;

/*************************************************************************
 * Compilation: javac Quick3way.java Execution: java Quick3way < input.txt
 * Dependencies: StdOut.java StdIn.java Data files:
 * http://algs4.cs.princeton.edu/23quicksort/tiny.txt
 * http://algs4.cs.princeton.edu/23quicksort/words3.txt
 * 
 * Sorts a sequence of strings from standard input using 3-way quicksort.
 * 
 * % more tiny.txt S O R T E X A M P L E
 *
 * % java Quick3way < tiny.txt A E E L M O P R S T X [ one string per line ]
 * 
 * % more words3.txt bed bug dad yes zoo ... all bad yet
 * 
 * % java Quick3way < words3.txt all bad bed bug dad ... yes yet zoo [ one
 * string per line ]
 *
 *************************************************************************/

/**
 * The <tt>Quick3way</tt> class provides static methods for sorting an array
 * using quicksort with 3-way partitioning.
 * <p>
 * For additional documentation, see <a
 * href="http://algs4.cs.princeton.edu/21elementary">Section 2.1</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class Quick3way
{
	public volatile static int changing;
	public volatile static int position;

	public static Thread thread;

	@SuppressWarnings("rawtypes")
	public static void sort(Comparable[] a)
	{

		/*
		 * if push 'Restart' button before the animation finished, it will
		 * result in a thread problem that I don't understand
		 */
		Thread thead = new Thread()
		{
			@Override
			public void run()
			{
				sort(a, 0, a.length - 1);
			}
		};
		thead.start();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void sort(Comparable[] a, int lo, int hi)
	{
		if (hi <= lo)
			return;
		int lt = lo, gt = hi;
		Comparable v = a[lo];
		int i = lo;
		while (i <= gt)
		{
			int cmp = a[i].compareTo(v);
			if (cmp < 0)
			{
				try
				{
					position = lt + 1;
					Thread.sleep(AnimationPanel.SPF);
				} catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				exch(a, lt++, i++);
			}
			else if (cmp > 0)
			{
				try
				{
					changing = gt - 1;
					Thread.sleep(AnimationPanel.SPF);
				} catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				exch(a, i, gt--);
			}
			else
				i++;
		}

		sort(a, lo, lt - 1);
		sort(a, gt + 1, hi);
		// assert isSorted(a, lo, hi);
	}

	/***********************************************************************
	 * Helper sorting functions
	 ***********************************************************************/

	// is v < w ?
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static boolean less(Comparable v, Comparable w)
	{
		return (v.compareTo(w) < 0);
	}

	// does v == w ?
	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	private static boolean eq(Comparable v, Comparable w)
	{
		return (v.compareTo(w) == 0);
	}

	// exchange a[i] and a[j]
	private static void exch(Object[] a, int i, int j)
	{
		Object swap = a[i];
		a[i] = a[j];
		a[j] = swap;
	}

	/***********************************************************************
	 * Check if array is sorted - useful for debugging
	 ***********************************************************************/
	@SuppressWarnings({ "rawtypes", "unused" })
	private static boolean isSorted(Comparable[] a)
	{
		return isSorted(a, 0, a.length - 1);
	}

	@SuppressWarnings("rawtypes")
	private static boolean isSorted(Comparable[] a, int lo, int hi)
	{
		for (int i = lo + 1; i <= hi; i++)
			if (less(a[i], a[i - 1]))
				return false;
		return true;
	}

}

/*************************************************************************
 * Copyright 2002-2012, Robert Sedgewick and Kevin Wayne.
 *
 * This file is part of algs4-package.jar, which accompanies the textbook
 *
 * Algorithms, 4th edition by Robert Sedgewick and Kevin Wayne, Addison-Wesley
 * Professional, 2011, ISBN 0-321-57351-X. http://algs4.cs.princeton.edu
 *
 *
 * algs4-package.jar is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * algs4-package.jar is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * algs4-package.jar. If not, see http://www.gnu.org/licenses.
 *************************************************************************/

