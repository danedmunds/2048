package ca.danedmunds.twentyfortyeight;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ca.danedmunds.twentyfortyeight.model.TwentyFortyEight;

public class JButtonPanel extends JPanel {

	private Color[] COLORS = {Color.BLUE, Color.RED, Color.CYAN,
			Color.MAGENTA, Color.ORANGE, Color.GREEN,
			Color.GRAY, Color.PINK, Color.YELLOW};
	private static final int WIDTH = 4;
	private static final int HEIGHT = 4;

	private JButton[][] cells;
	private TwentyFortyEight twentyFortyEight;

	public JButtonPanel() {
		super();
		setLayout(new GridLayout(4, 4));

		cells = new JButton[4][4];
		for (int y = 0; y < HEIGHT; ++y) {
			for (int x = 0; x < WIDTH; ++x) {
				cells[x][y] = new JButton();
				add(cells[x][y]);
			}
		}

		setFocusable(true);
		addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				switch(e.getKeyCode()) {
				case(KeyEvent.VK_RIGHT):
					twentyFortyEight.right();
					break;
				case(KeyEvent.VK_LEFT):
					twentyFortyEight.left();
					break;
				case(KeyEvent.VK_UP):
					twentyFortyEight.up();
					break;
				case(KeyEvent.VK_DOWN):
					twentyFortyEight.down();
					break;
			}

			reflect();
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});

		twentyFortyEight = new TwentyFortyEight(WIDTH, HEIGHT);
		twentyFortyEight.start();
		reflect();
	}

	@Override
	public void paint(Graphics arg0) {
		super.paint(arg0);
		reflect();
	}

	public void reflect() {
		for (int y = 0; y < HEIGHT; ++y) {
			for (int x = 0; x < WIDTH; ++x) {
				int value = twentyFortyEight.getValue(x, y);
				if (value != -1) {
					int colorIndex = ((int)(Math.log(value) / Math.log(2))) % COLORS.length;
					cells[x][y].setText(String.valueOf(value));
					cells[x][y].setBackground(COLORS[colorIndex]);
					cells[x][y].setOpaque(true);
				} else {
					cells[x][y].setText(" ");
					cells[x][y].setBackground(Color.WHITE);
				}
			}
		}
	}

	public static void main(String[] args) {
		final JButtonPanel panel = new JButtonPanel();
		JFrame display = new JFrame();
		MenuBar bar = new MenuBar();
		Menu menu = new Menu("Blah");
		bar.add(menu);
		MenuItem up = new MenuItem("up");
		up.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				panel.twentyFortyEight.up();
				panel.reflect();
			}
		});
		menu.add(up);

		MenuItem down = new MenuItem("down");
		down.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				panel.twentyFortyEight.down();
				panel.reflect();
			}
		});
		menu.add(down);

		MenuItem right = new MenuItem("right");
		right.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				panel.twentyFortyEight.right();
				panel.reflect();
			}
		});
		menu.add(right);

		MenuItem left = new MenuItem("left");
		left.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				panel.twentyFortyEight.left();
				panel.reflect();
			}
		});
		menu.add(left);

		display.setMenuBar(bar);
		display.setContentPane(panel);
		display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		display.setSize(400, 400);
		display.setVisible(true);
	}
}
