package composites;

import org.eclipse.swt.widgets.Composite;

public class ShenCastanComposite extends ParentComposite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ShenCastanComposite(Composite parent, int style) {
		super(parent, style);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
