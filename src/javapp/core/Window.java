package javapp.core;

import javax.swing.Timer;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class Window extends Canvas {

	// Create a new blank cursor. Transparent 16 x 16 pixel cursor image.
	private BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
	private Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0),
			"blank cursor");

	// Java stuff
	private JFrame frame;
	private JPanel panel;

	// The timer that updates the panel every frame.
	private Timer timer;

	// Keeps track of how many frames have been drawn.
	public long frameCount;
	public double frameRate = 60;
	private long[] ms = new long[5];

	/**
	 * Creates a JSWindow of the given size.
	 * 
	 * @param w width
	 * @param h height
	 */
	public Window(int w, int h) {
		super(w, h);

		// Create the JPanel and JFrame
		createPanel();
		frame = new JFrame();

		// Run setup
		setup();

		// Create the frame
		createFrame();

		// Start the timer 60 times a second
		timer = new Timer(1000 / 180, (a) -> {
			frameCount++;
			panel.repaint();

			ms[(int) (frameCount % 5)] = System.nanoTime();
			frameRate = 1000000000.0 / ((System.nanoTime() - ms[(int) ((frameCount + 6) % 5)]) / 4.0);

		});
		timer.start();
	}

	/**
	 * Creates the JPanel.
	 */
	public void createPanel() {
		Canvas tempThis = this;
		panel = new JPanel() {
			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g) {
				super.paintComponent(g);

				// Call the draw
				draw();

				// Draw the canvas on the Graphics.
				tempThis.draw((Graphics2D) g);

				// Set the cursor
				int cursor = tempThis.getCursor();
				if (cursor == -1) {
					frame.getContentPane().setCursor(blankCursor);
				} else {
					frame.getContentPane().setCursor(Cursor.getPredefinedCursor(cursor));
				}

				// Redraw the Canvas
//                redraw();
			};
		};

		// Add event listeners to the panel
		panel.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent arg0) {
				getEventHandler().add(arg0);
				tempThis.mouseClicked(arg0);
			}

			public void mouseEntered(MouseEvent arg0) {
				getEventHandler().add(arg0);
				tempThis.mouseEntered();
			}

			public void mouseExited(MouseEvent arg0) {
				getEventHandler().add(arg0);
				tempThis.mouseExited();
			}

			public void mousePressed(MouseEvent arg0) {
				getEventHandler().add(arg0);
				tempThis.mousePressed(arg0);
			}

			public void mouseReleased(MouseEvent arg0) {
				getEventHandler().add(arg0);
				tempThis.mouseReleased(arg0);
			}
		});
		panel.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent arg0) {
				getEventHandler().add(arg0);
				tempThis.mouseWheel(arg0);
			}
		});
		panel.addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent arg0) {
				getEventHandler().add(arg0);
				tempThis.mouseDragged(arg0);
			}

			public void mouseMoved(MouseEvent arg0) {
				getEventHandler().add(arg0);
				tempThis.mouseMoved(arg0);
			}
		});

		panel.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent arg0) {
				getEventHandler().add(arg0);
				tempThis.keyPressed(arg0);
			}

			public void keyReleased(KeyEvent arg0) {
				getEventHandler().add(arg0);
				tempThis.keyReleased(arg0);
			}

			public void keyTyped(KeyEvent arg0) {
				getEventHandler().add(arg0);
				tempThis.keyTyped(arg0);
			}
		});

		panel.addComponentListener(new ComponentListener() {
			public void componentHidden(ComponentEvent arg0) {
				getEventHandler().add(arg0);
			}

			public void componentMoved(ComponentEvent arg0) {
				getEventHandler().add(arg0);
			}

			public void componentResized(ComponentEvent arg0) {
				getEventHandler().add(arg0);

				// Update the canvas size if it gets resized
				setSize(panel.getWidth(), panel.getHeight());
			}

			public void componentShown(ComponentEvent arg0) {
				getEventHandler().add(arg0);
			}
		});

		// It needs focus of course!
		panel.setFocusable(true);

		// This is set to false so we can actually detect a TAB key press
		panel.setFocusTraversalKeysEnabled(false);
		panel.requestFocus();

	}

	/**
	 * Creates the JFrame.
	 */
	public void createFrame() {

		// Set the size using the insets, otherwise the frame will be a little too
		// small.
		Insets insets = frame.getInsets();
		int w = getWidth() + (insets.left + insets.right);
		int h = getHeight() + (insets.top + insets.bottom);
		frame.setSize(w, h);
		frame.setPreferredSize(new Dimension(w, h));

		// Make sure it closes properly
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Add the panel
		frame.add(panel);
		frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setVisible(true);

	}

	/**
	 * Returns the JPanel.
	 * 
	 * @return JPanel
	 */
	public JPanel getPanel() {
		return panel;
	}

	/**
	 * Returns the JFrame.
	 * 
	 * @return JFrame
	 */
	public JFrame getFrame() {
		return frame;
	}

	/**
	 * This gets called once at startup.
	 */
	public abstract void setup();

	/**
	 * This gets called every frame, the user can call draw methods from here.
	 */
	public abstract void draw();

}
