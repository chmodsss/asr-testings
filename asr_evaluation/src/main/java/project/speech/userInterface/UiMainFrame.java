package project.speech.userInterface;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.SwingConstants;

public class UiMainFrame extends JFrame {

	private JPanel contentPane;
	static JFrame frameMain;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UiMainFrame frameMain = new UiMainFrame();
					frameMain.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public UiMainFrame() {
		frameMain = new JFrame();
		frameMain.setBounds(100, 100, 695, 450);
		frameMain.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frameMain.getContentPane().setLayout(null);
		frameMain.setTitle("Instructions...");

		final UiInstructionMainFrame frameInstructionsMain = new UiInstructionMainFrame();
		frameMain.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				if (frameInstructionsMain != null) {
					frameInstructionsMain.dispose();
				}
			}
		});
		// Set UI to look more cool
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			// UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 480, 306);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnModel = new JButton("Model-1");
		btnModel.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnModel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UiMethod1Frame.initialize();
				UiMethod1Frame.frame1.setVisible(true);
			}
		});
		btnModel.setBounds(34, 177, 140, 50);
		contentPane.add(btnModel);
		
		JButton btnModel_1 = new JButton("Model-2");
		btnModel_1.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnModel_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UiMethod2Frame.initialize();
				UiMethod2Frame.frame2.setVisible(true);
			}
		});
		btnModel_1.setBounds(264, 177, 140, 50);
		contentPane.add(btnModel_1);
		
		JLabel lblAsrTool = new JLabel("ASR evaluation tool");
		lblAsrTool.setHorizontalAlignment(SwingConstants.CENTER);
		lblAsrTool.setFont(new Font("Georgia", Font.ITALIC, 30));
		lblAsrTool.setBounds(34, 48, 384, 39);
		contentPane.add(lblAsrTool);
		
		JButton btnInstructions = new JButton("Instructions");
		btnInstructions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UiInstructionMainFrame frameInstructionMain = new UiInstructionMainFrame();
				frameInstructionMain.setVisible(true);
			}
		});
		btnInstructions.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnInstructions.setBounds(158, 116, 130, 28);
		contentPane.add(btnInstructions);
	}
}
