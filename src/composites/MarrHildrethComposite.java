package composites;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.MessageBox;

import algorithms.MarrHildreth;

public class MarrHildrethComposite extends ParentComposite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public MarrHildrethComposite(Composite parent, int style) {
		super(parent, style);
		
		Group grpMarrHildreth = new Group(this, SWT.NONE);
		grpMarrHildreth.setText("Marr Hildreth");
		grpMarrHildreth.setBounds(660, 40, 177, 115);
		
		Button btnInfo = new Button(grpMarrHildreth, SWT.NONE);
		btnInfo.setBounds(10, 80, 75, 25);
		btnInfo.setText("Info");
		
		Button btnMarrHildreth = new Button(grpMarrHildreth, SWT.NONE);
		btnMarrHildreth.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//text.setText("SKAICIUOJAMA!!!");
				if (pathList.size()>0) {
					MarrHildreth marr = new MarrHildreth();
					String str = marr.marrHildreth(pathList);
					setImage(str);
				}else {
					MessageBox m = new MessageBox(getShell());// ("Pasirinkite paveikla ir suveskite slenkscius");
					m.setMessage("Pasirinkite paveiksleli");
					m.open();
				}
			
			}
		});
		btnMarrHildreth.setBounds(91, 80, 75, 25);
		btnMarrHildreth.setText("Marr Hildreth");

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
