import java.awt.Dimension;
import java.awt.Label;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import rsa.Generador;

public class Gui extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JTextField jTextField1 = null;
	private JScrollPane jScrollPane = null;
	private JTextArea jTextArea = null;
	private JButton jButton2 = null;
	private JTextField remesa = null;
	private Label label = null;
	private Label label1 = null;
	private JTextField numSerie = null;
	private Label label2 = null;
	
	private Generador gen;
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setPreferredSize(new java.awt.Dimension(127,30));
			jButton.setLocation(new java.awt.Point(14,26));
			jButton.setSize(new java.awt.Dimension(170,20));
			jButton.setText("Generar llaves");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					gen.generarKeys();
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setPreferredSize(new java.awt.Dimension(127,30));
			jButton1.setBounds(new java.awt.Rectangle(14,57,170,20));
			jButton1.setText("Generar licencia");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					gen.generarLicencia(remesa.getText(),numSerie.getText(),jTextField1.getText());
				}
			});
		}
		return jButton1;
	}
	/**
	 * This method initializes jTextField1	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setBounds(new java.awt.Rectangle(391,58,267,20));
			jTextField1.setPreferredSize(new java.awt.Dimension(4,30));
		}
		return jTextField1;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(new java.awt.Rectangle(14,133,654,382));
			jScrollPane.setViewportView(getJTextArea());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextArea() {
		if (jTextArea == null) {
			jTextArea = new JTextArea();
		}
		return jTextArea;
	}

	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("Generar num. serie");
			jButton2.setBounds(new java.awt.Rectangle(14,89,170,20));
			jButton2.setPreferredSize(new Dimension(127, 30));
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println(remesa.getText()+numSerie.getText());
					gen.generarNumSerie(remesa.getText(),numSerie.getText());
				}
			});
		}
		return jButton2;
	}

	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getRemesa() {
		if (remesa == null) {
			remesa = new JTextField();
			remesa.setBounds(new java.awt.Rectangle(274,89,143,21));
		}
		return remesa;
	}

	/**
	 * This method initializes jTextField2	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getNumSerie() {
		if (numSerie == null) {
			numSerie = new JTextField();
			numSerie.setLocation(new java.awt.Point(492,89));
			numSerie.setSize(new java.awt.Dimension(163,21));
		}
		return numSerie;
	}
//	==================================================================	
	public static void main(String[] args) 
//	==================================================================	
	{
		// TODO Auto-generated method stub
		Gui application = new Gui();
		application.setVisible(true);
	}

	/**
	 * This is the default constructor
	 */
	public Gui() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(686, 561);
		this.setContentPane(getJContentPane());
		gen = new Generador(jTextArea);
		this.setTitle("Generador");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			label2 = new Label();
			label2.setText("Numero serie hdd cliente");
			label2.setSize(new java.awt.Dimension(178,19));
			label2.setLocation(new java.awt.Point(202,58));
			label1 = new Label();
			label1.setBounds(new java.awt.Rectangle(422,89,68,20));
			label1.setText("Num. serie");
			label = new Label();
			label.setBounds(new java.awt.Rectangle(201,89,71,20));
			label.setText("Remesa");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(getJTextField1(), null);
			jContentPane.add(getJScrollPane(), null);
			jContentPane.add(getJButton2(), null);
			jContentPane.add(getRemesa(), null);
			jContentPane.add(label, null);
			jContentPane.add(label1, null);
			jContentPane.add(getNumSerie(), null);
			jContentPane.add(label2, null);
		}
		return jContentPane;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
