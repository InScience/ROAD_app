package composites;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;

import algorithms.Canny;

public class CannyComposite extends ParentComposite {
	private Text txtThreshold1;
	private Text txtThreshold2;
	private Text txtAperturesize;
	private Button btnLgradient;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public CannyComposite(Composite parent, int style) {
		super(parent, style);
		
		Group grpCanny = new Group(this, SWT.NONE);
		grpCanny.setText("Canny");
		grpCanny.setBounds(660, 41, 160, 156);

		Button btnCanny = new Button(grpCanny, SWT.NONE);
		btnCanny.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//text.setText("SKAICIUOJAMA!!!");
				if ((filename != "") && (txtThreshold1.getText() != "") && (txtThreshold2.getText() != "") && (txtAperturesize.getText() != "")) {
					Canny canny = new Canny();
					String str = canny.canny(pathList, Integer.parseInt(txtThreshold1.getText()), Integer.parseInt(txtThreshold2.getText()),
							Integer.parseInt(txtAperturesize.getText()), btnLgradient.getSelection());
					setImage(str);
				} else if ((filename != "") && (txtThreshold1.getText() != "") && (txtThreshold2.getText() != "")) {
					Canny canny = new Canny();
					String str = canny.canny(pathList, Integer.parseInt(txtThreshold1.getText()), Integer.parseInt(txtThreshold2.getText()));
					setImage(str);

				} else {
					MessageBox m = new MessageBox(getShell());// ("Pasirinkite paveikla ir suveskite slenkscius");
					m.setMessage("Pasirinkite paveiksleli ir suveskite slenkscius");
					m.open();
				}
			}
		});
		btnCanny.setBounds(81, 123, 75, 25);
		btnCanny.setText("run Canny");

		Label lblThreshold = new Label(grpCanny, SWT.NONE);
		lblThreshold.setBounds(15, 24, 62, 15);
		lblThreshold.setText("threshold 1:");

		Label lblThreshold_1 = new Label(grpCanny, SWT.NONE);
		lblThreshold_1.setBounds(15, 51, 62, 15);
		lblThreshold_1.setText("threshold 2:");

		txtThreshold1 = new Text(grpCanny, SWT.BORDER);
		txtThreshold1.setText("50");
		txtThreshold1.setBounds(81, 24, 76, 21);

		txtThreshold2 = new Text(grpCanny, SWT.BORDER);
		txtThreshold2.setText("100");
		txtThreshold2.setBounds(81, 51, 76, 21);

		Label lblAperturesize = new Label(grpCanny, SWT.NONE);
		lblAperturesize.setBounds(10, 75, 67, 15);
		lblAperturesize.setText("apertureSize:");

		txtAperturesize = new Text(grpCanny, SWT.BORDER);
		txtAperturesize.setText("3");
		txtAperturesize.setBounds(81, 75, 76, 21);

		Label lblLgradient = new Label(grpCanny, SWT.NONE);
		lblLgradient.setBounds(15, 99, 62, 15);
		lblLgradient.setText("L2gradient:");

		btnLgradient = new Button(grpCanny, SWT.CHECK);
		btnLgradient.setBounds(81, 102, 75, 15);

		Button btnInfo = new Button(grpCanny, SWT.NONE);
		btnInfo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MessageBox m = new MessageBox(getShell());// ("Pasirinkite paveikla ir suveskite slenkscius");
				m.setMessage("" + "Paduodamas kelias ir slenksciai. Rezultatas irasomas i ta pacia vieta\n," + "kur yra originalus failas pridedant gale: -canny\n"
						+ "@param path to image – single-channel 8-bit input image.\n" + "@param threshold1 - first threshold for the hysteresis procedure.\n"
						+ "@param threshold2 - second threshold for the hysteresis procedure.\n" + "@param apertureSize - aperture size for the Sobel() operator.\n"
						+ "@param L2gradient - a flag, indicating whether a more accurate norm should be\n" + "used to calculate the image gradient magnitude (\n"
						+ "L2gradient=true ), or whether the default norm is enough (L2gradient=false )." + "@return kelias iki naujo failo");
				m.open();

			}
		});
		btnInfo.setBounds(0, 123, 75, 25);
		btnInfo.setText("Info");

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
