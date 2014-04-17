package composites;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.MessageBox;

import algorithms.Sobel;

public class SobelComposite extends ParentComposite {
	private int cvType=1;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public SobelComposite(Composite parent, int style) {
		super(parent, style);
		
		Group grpSobel = new Group(this, SWT.NONE);
		grpSobel.setText("Sobel");
		grpSobel.setBounds(658, 40, 163, 167);
		
		Button btnInfo = new Button(grpSobel, SWT.NONE);
		btnInfo.setBounds(10, 132, 62, 25);
		btnInfo.setText("Info");
		
		Button btnSobel = new Button(grpSobel, SWT.NONE);
		btnSobel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (pathList.size()>0) {
					System.out.println(cvType);
					Sobel sobel = new Sobel();
					String str = sobel.sobel(pathList, cvType);
					setImage(str);
				}else {
					MessageBox m = new MessageBox(getShell());// ("Pasirinkite paveikla ir suveskite slenkscius");
					m.setMessage("Pasirinkite paveiksleli");
					m.open();
				}
			}
		});
		btnSobel.setBounds(78, 132, 75, 25);
		btnSobel.setText("Sobel");
		
		Button btnCvtypecvu = new Button(grpSobel, SWT.RADIO);
		btnCvtypecvu.setSelection(true);
		btnCvtypecvu.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				cvType=1;
			}
		});
		btnCvtypecvu.setBounds(10, 23, 103, 16);
		btnCvtypecvu.setText("CvType.CV_8U");
		
		Button btnCvtypecvu_1 = new Button(grpSobel, SWT.RADIO);
		btnCvtypecvu_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				cvType=2;
			}
		});
		btnCvtypecvu_1.setBounds(10, 45, 103, 16);
		btnCvtypecvu_1.setText("CvType.CV_16U");
		
		Button btnCvtypecvf = new Button(grpSobel, SWT.RADIO);
		btnCvtypecvf.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				cvType=3;
			}
		});
		btnCvtypecvf.setBounds(10, 67, 103, 16);
		btnCvtypecvf.setText("CvType.CV_32F");
		
		Button btnCvtypecvf_1 = new Button(grpSobel, SWT.RADIO);
		btnCvtypecvf_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				cvType=4;
			}
		});
		btnCvtypecvf_1.setBounds(10, 89, 103, 16);
		btnCvtypecvf_1.setText("CvType.CV_64F");

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
