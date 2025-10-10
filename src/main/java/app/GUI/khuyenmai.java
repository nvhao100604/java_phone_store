package app.GUI;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;

public class khuyenmai extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public khuyenmai() {
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		add(lblNewLabel);

	}

}
