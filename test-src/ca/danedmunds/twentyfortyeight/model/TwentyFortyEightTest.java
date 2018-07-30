package ca.danedmunds.twentyfortyeight.model;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

import ca.danedmunds.twentyfortyeight.model.TwentyFortyEight;

public class TwentyFortyEightTest {

	private TwentyFortyEight twentyFortyEight;

	@Before
	public void before() {
		twentyFortyEight = new TwentyFortyEight(4, 4);
	}

	@Test
	public void verifyLeft() throws Exception {
		twentyFortyEight.setValue(new Point(2, 2), 2);
		twentyFortyEight.left(false);
		assertEquals(2, twentyFortyEight.getValue(0, 2));
	}

	@Test
	public void verifyLeftMerge() throws Exception {
		twentyFortyEight.setValue(new Point(2, 2), 2);
		twentyFortyEight.setValue(new Point(1, 2), 2);
		twentyFortyEight.left(false);
		assertEquals(4, twentyFortyEight.getValue(0, 2));
	}

	@Test
	public void fullRowLeftMiddleCombine() throws Exception {
		twentyFortyEight.setValue(new Point(0, 0), 16);
		twentyFortyEight.setValue(new Point(1, 0), 2);
		twentyFortyEight.setValue(new Point(2, 0), 2);
		twentyFortyEight.setValue(new Point(3, 0), 4);

		twentyFortyEight.left(false);

		assertEquals(16, twentyFortyEight.getValue(0, 0));
		assertEquals(4, twentyFortyEight.getValue(1, 0));
		assertEquals(4, twentyFortyEight.getValue(2, 0));
		assertEquals(-1, twentyFortyEight.getValue(3, 0));
	}

	@Test
	public void fullRowLeftDoubleCombine() throws Exception {
		twentyFortyEight.setValue(new Point(0, 0), 16);
		twentyFortyEight.setValue(new Point(1, 0), 2);
		twentyFortyEight.setValue(new Point(2, 0), 2);
		twentyFortyEight.setValue(new Point(3, 0), 4);

		System.out.println(twentyFortyEight);

		twentyFortyEight.left(false);

		System.out.println(twentyFortyEight);

	}

}
