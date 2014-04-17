package composites;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.MessageBox;

import algorithms.Otsu;

public class OtsuComposite extends ParentComposite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public OtsuComposite(Composite parent, int style) {
		super(parent, style);
		
		Group grpOtsu = new Group(this, SWT.NONE);
		grpOtsu.setText("Otsu");
		grpOtsu.setBounds(655, 40, 160, 62);
		
		Button btnOtsu = new Button(grpOtsu, SWT.NONE);
		btnOtsu.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//text.setText("SKAICIUOJAMA!!!");
				if (pathList.size()>0) {
					Otsu otsu = new Otsu();
					String str = otsu.otsu(pathList);
					setImage(str);
				}else {
					MessageBox m = new MessageBox(getShell());// ("Pasirinkite paveikla ir suveskite slenkscius");
					m.setMessage("Pasirinkite paveiksleli");
					m.open();
				}
			}
		});
		btnOtsu.setBounds(75, 24, 75, 25);
		btnOtsu.setText("Otsu");
		
		Button btnInfo = new Button(grpOtsu, SWT.NONE);
		btnInfo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MessageBox m = new MessageBox(getShell());// ("Pasirinkite paveikla ir suveskite slenkscius");
				m.setMessage("INFO turi buti");
				m.open();
			}
		});
		btnInfo.setBounds(10, 24, 59, 25);
		btnInfo.setText("Info");

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
