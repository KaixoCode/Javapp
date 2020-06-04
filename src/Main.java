
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.UIDefaults;
import javax.swing.UIManager;

import javapp.core.Functions;
import javapp.core.Window;
import javapp.data.Transition;
import javapp.data.Vector;
import javapp.data.VectorTransition;
import javapp.objects.text.TextArea;

public class Main extends Window {

	public static void main(String... a) {
		new Main(400, 300);
	}

	public Main(int w, int h) {
		super(w, h);
	}

	ArrayList<VectorTransition> positions;

	int amt;

	public void setup() {
		amt = 2000;
		positions = new ArrayList<VectorTransition>();
		for (int i = 0; i < amt; i++) {
			positions.add(new VectorTransition(new Vector<Double>(0.0, 0.0), 0.95));
		}
	}

	double mx = 0, my = 0;

	public void draw() {
		draw((g2d) -> {
			g2d.setColor(new Color(0, 0, 0));
			g2d.fillRect(0, 0, getWidth(), getHeight());
			g2d.setColor(Color.WHITE);
			g2d.drawString("" + frameRate, 15, 20);
		});

		for (int i = 1; i < amt; i++) {
			Vector<Double> posa = positions.get(i - 1).getValue();
			Vector<Double> posb = positions.get(i).getValue();
			draw(g2d -> g2d.drawLine(posa.x.intValue(), posa.y.intValue(), posb.x.intValue(), posb.y.intValue()));
		}

		positions.get(0).morph(new Vector<Double>(mx, my));
		for (int i = 1; i < amt; i++) {
			positions.get(i).morph(positions.get(i - 1).getValue());
		}
	}

	public void mouseMoved(MouseEvent e) {
		mx = e.getX();
		my = e.getY();
	}
}
