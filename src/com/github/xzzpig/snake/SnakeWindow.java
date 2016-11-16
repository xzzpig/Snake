package com.github.xzzpig.snake;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import net.miginfocom.swing.MigLayout;

public class SnakeWindow {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SnakeWindow window = new SnakeWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private JFrame frame;
	private SnakeView snakeview;

	/**
	 * Create the application.
	 */
	public SnakeWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				snakeview.requestFocus();
			}
		});
		frame.setTitle("\u8D2A\u5403\u86C7");
		frame.setBounds(100, 100, 800, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[grow]", "[grow]"));
		snakeview = new SnakeView();
		frame.getContentPane().add(snakeview, "cell 0 0,grow");
		
	}

}
