package com.github.xzzpig.snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

import com.github.xzzpig.snake.Snake.Side;

public class SnakeView extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private float dis[];

	private Side side;

	public SnakeView() {
		addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						while (isFocusOwner()) {
							if (side != null) {
								if (!Snake.getInstance().move(side)) {
									side = null;
									JOptionPane.showMessageDialog(null, "游戏结束\n长度:" + Snake.getInstance().getLength());
									new Snake(Snake.getInstance().getSize());
								}
								;
								paint(getGraphics());
							}
							try {
								Thread.sleep(300);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				}).start();
			}
		});
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
					side = Side.UP;
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
					side = Side.DOWN;
				} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
					side = Side.LEFT;
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
					side = Side.RIGHT;
				}
			}
		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				requestFocus();
			}
		});

	}

	@Override
	public synchronized void addKeyListener(KeyListener arg0) {
		super.addKeyListener(arg0);
	}

	private void drawBack(Graphics g) {
		g.setColor(getBackground());
		// g.setColor(Color.getHSBColor(30, 240, 96));
		g.fillRect(0, 0, getWidth(), getHeight());
	}
	//
	// private void drawLine(Graphics g, int size) {
	// g.setColor(Color.BLACK);
	// size++;
	// for (int i = 1; i < size; i++) {
	// g.drawLine(0, (int) (dis[1] * i), getWidth(), (int) (dis[1] * i));
	// g.drawLine((int) (dis[0] * i), 0, (int) (dis[0] * i), getHeight());
	// }
	// }

	private void measure(int size) {
		dis = new float[] { (float) getWidth() / size, (float) getHeight() / size };
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Snake snake = Snake.getInstance();
		int size = snake.getSize();
		measure(size);
		drawBack(g);
		// drawLine(g, size);
		drawFood(g, snake);
		drawSnake(g, snake);
	}

	private void drawSnake(Graphics g, Snake snake) {
		for (int i = 0; i < snake.getLength(); i++) {
			Color color = Color.BLACK;
			if(i==0)
				color = Color.GRAY;
			int[] bodyloc = snake.getBodyLoc(i);
			drawBody(g, bodyloc, color);
		}
	}

	private void drawFood(Graphics g, Snake snake) {
		int[] bodyloc = snake.getFoodLoc();
		drawBody(g, bodyloc, Color.GREEN);
	}
	
	private void drawBody(Graphics g,int[] bodyloc,Color color) {
		g.setColor(color);
		g.fillRect((int) (bodyloc[0] * dis[0]), (int) (bodyloc[1] * dis[1]), (int) (dis[0] * 0.9),
				(int) (dis[1] * 0.9));
	}
}
