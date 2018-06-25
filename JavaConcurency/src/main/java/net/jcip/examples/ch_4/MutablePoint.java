package net.jcip.examples.ch_4;

import net.jcip.annotations.NotThreadSafe;

/**
 * MutablePoint
 * <p/>
 * Mutable Point class similar to java.awt.Point
 *
 * @author Brian Goetz and Tim Peierls
 */
@NotThreadSafe
public class MutablePoint {
	public int x, y;

	public MutablePoint() {
		x = 0;
		y = 0;
	}

	public MutablePoint(MutablePoint p) {
		this.x = p.x;
		this.y = p.y;
	}
}
