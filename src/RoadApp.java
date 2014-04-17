import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.opencv.core.Core;

import composites.CannyComposite;
import composites.MarrHildrethComposite;
import composites.OtsuComposite;
import composites.ShenCastanComposite;
import composites.Siukslynelis;
import composites.SobelComposite;


public class RoadApp {

	protected Shell shell;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		try {
			RoadApp window = new RoadApp();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(1024, 800);
		shell.setText("Road Analyzer");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		TabFolder tabFolder = new TabFolder(shell, SWT.NONE);
		
		TabItem tbtmCanny = new TabItem(tabFolder, SWT.NONE);
		tbtmCanny.setText("Canny");
		
		CannyComposite cannyComposite = new CannyComposite(tabFolder, SWT.NONE);
		tbtmCanny.setControl(cannyComposite);
		
		TabItem tbtmMarrhildreth = new TabItem(tabFolder, SWT.NONE);
		tbtmMarrhildreth.setText("MarrHildreth");
		
		MarrHildrethComposite marrHildrethComposite = new MarrHildrethComposite(tabFolder, SWT.NONE);
		tbtmMarrhildreth.setControl(marrHildrethComposite);
		
		TabItem tbtmShencastan = new TabItem(tabFolder, SWT.NONE);
		tbtmShencastan.setText("ShenCastan");
		
		ShenCastanComposite shenCastanComposite = new ShenCastanComposite(tabFolder, SWT.NONE);
		shenCastanComposite.setEnabled(false);
		tbtmShencastan.setControl(shenCastanComposite);
		
		TabItem tbtmOtsu = new TabItem(tabFolder, SWT.NONE);
		tbtmOtsu.setText("Otsu");
		
		OtsuComposite otsuComposite = new OtsuComposite(tabFolder, SWT.NONE);
		tbtmOtsu.setControl(otsuComposite);
		
		TabItem tbtmSobel = new TabItem(tabFolder, SWT.NONE);
		tbtmSobel.setText("Sobel");
		
		SobelComposite sobelComposite = new SobelComposite(tabFolder, SWT.NONE);
		tbtmSobel.setControl(sobelComposite);
		
		TabItem tbtmTest = new TabItem(tabFolder, SWT.NONE);
		tbtmTest.setText("TEST");
		
		Siukslynelis roadComposite = new Siukslynelis(tabFolder, SWT.NONE);
		tbtmTest.setControl(roadComposite);
				

	}
}
