package composites;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import algorithms.Canny;

public class Siukslynelis extends Composite {
	Label imgLabel;
	String currentDir = "";
	Combo combo;
	Image image;
	String filename = "";
	private Text txtThreshold1;
	private Text txtThreshold2;
	private Text txtAperturesize;
	Button btnLgradient;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public Siukslynelis(Composite parent, int style) {
		super(parent, SWT.BORDER);
		setLayout(null);
		// System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		Button btnNewButton = new Button(this, SWT.NONE);
		btnNewButton.setBounds(10, 10, 117, 25);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				System.out.println("veikia");

				System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
				Mat mat = Mat.eye(3, 3, CvType.CV_8UC1);
				System.out.println("mat = " + mat.dump());
				onFileOpen();

			}
		});
		btnNewButton.setText("Pasirinkti nuotrauka");

		combo = new Combo(this, SWT.NONE);
		combo.setItems(new String[] { "Otsu", "Sobel", "Adaptive Threshold" });
		combo.setBounds(265, 12, 241, 23);
		combo.setText("Pasirinkti algoritma");

		Button btnPaleisti = new Button(this, SWT.NONE);
		btnPaleisti.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int i = combo.getSelectionIndex();
				if (i == 0) {
					System.out.println("Pirmas");
					pirmas();
				}
				if (i == 1) {
					System.out.println("Antras");
					sobel();
				}
				if (i == 2) {
					System.out.println("trecias");
					trecias();
				}

			}

		});
		btnPaleisti.setBounds(512, 10, 75, 25);
		btnPaleisti.setText("Paleisti");

		imgLabel = new Label(this, SWT.BORDER | SWT.HORIZONTAL);
		imgLabel.setBounds(10, 41, 577, 383);

		Group grpCanny = new Group(this, SWT.NONE);
		grpCanny.setText("Canny");
		grpCanny.setBounds(593, 10, 160, 156);

		Button btnCanny = new Button(grpCanny, SWT.NONE);
		btnCanny.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if ((filename != "") && (txtThreshold1.getText() != "") && (txtThreshold2.getText() != "") && (txtAperturesize.getText() != "")) {
					Canny canny = new Canny(filename, Integer.parseInt(txtThreshold1.getText()), Integer.parseInt(txtThreshold2.getText()),
							Integer.parseInt(txtAperturesize.getText()), btnLgradient.getSelection());
					//setImage(str);
				} else if ((filename != "") && (txtThreshold1.getText() != "") && (txtThreshold2.getText() != "")) {
					Canny canny = new Canny(filename, Integer.parseInt(txtThreshold1.getText()), Integer.parseInt(txtThreshold2.getText()));
					//setImage(str);

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

		Group grpLaplacian = new Group(this, SWT.NONE);
		grpLaplacian.setText("Laplacian");
		grpLaplacian.setBounds(593, 189, 159, 143);

		Button btnLaplacian = new Button(grpLaplacian, SWT.NONE);
		btnLaplacian.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				laplacian();
			}
		});
		btnLaplacian.setBounds(74, 96, 75, 25);
		btnLaplacian.setText("Laplacian");

		Group grpGaussian = new Group(this, SWT.NONE);
		grpGaussian.setEnabled(false);
		grpGaussian.setText("Gaussian");
		grpGaussian.setBounds(593, 338, 159, 128);

		Button btnGaussian = new Button(grpGaussian, SWT.NONE);
		btnGaussian.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				gausian();
			}
		});
		btnGaussian.setBounds(74, 100, 75, 25);
		btnGaussian.setText("Gaussian");

		Button btnPasirinktiAplanka = new Button(this, SWT.NONE);
		btnPasirinktiAplanka.setBounds(133, 10, 104, 25);
		btnPasirinktiAplanka.setText("Pasirinkti aplanka");
		// imgLabel.setText("New Label");

	}

	private void setImage(String path) {
		image = new Image(getDisplay(), path);
		imgLabel.setImage(image);
	}

	private void pirmas() {
		String s = currentDir + "\\" + filename;
		System.out.println(s);
		Mat imageMat = Highgui.imread(filename, Highgui.CV_LOAD_IMAGE_GRAYSCALE);
		System.out.println(imageMat.empty());
		System.out.println(imageMat.width());
		// Imgproc.cvtColor(imageMat, imageMat, Imgproc.COLOR_BGR2GRAY);

		Imgproc.threshold(imageMat, imageMat, 125, 255, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);
		// Imgproc.adaptiveThreshold(imageMat, imageMat, 255,
		// Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY_INV, 15, 4);

		String strsave = filename.substring(0, filename.length() - 4) + "-otsu" + filename.substring(filename.length() - 4, filename.length());
		Highgui.imwrite(strsave, imageMat);
		image = new Image(getDisplay(), strsave);
		imgLabel.setImage(image);

	}

	private void sobel() {
		Mat imageMat = Highgui.imread(filename, Highgui.CV_LOAD_IMAGE_UNCHANGED);
		// Imgproc.Canny(imageMat, imageMat, threshold1, threshold2);
		// Imgproc.adaptiveThreshold(imageMat, imageMat, 255,
		// Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY_INV, 15, 4);
		Imgproc.Sobel(imageMat, imageMat, CvType.CV_8U, 1, 1);
		String strsave = filename.substring(0, filename.length() - 4) + "-sobel" + filename.substring(filename.length() - 4, filename.length());
		Highgui.imwrite(strsave, imageMat);
		image = new Image(getDisplay(), strsave);
		imgLabel.setImage(image);

	}

	private void antras(int threshold1, int threshold2) {
		Mat imageMat = Highgui.imread(filename, Highgui.CV_LOAD_IMAGE_GRAYSCALE);

		// Imgproc.cvtColor(imageMat, imageMat, Imgproc.COLOR_BGR2GRAY);

		Imgproc.Canny(imageMat, imageMat, threshold1, threshold2);
		// Imgproc.adaptiveThreshold(imageMat, imageMat, 255,
		// Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY_INV, 15, 4);

		String strsave = filename.substring(0, filename.length() - 4) + "-canny" + filename.substring(filename.length() - 4, filename.length());
		Highgui.imwrite(strsave, imageMat);
		image = new Image(getDisplay(), strsave);
		imgLabel.setImage(image);

	}

	private void trecias() {
		Mat imageMat = Highgui.imread(filename, Highgui.CV_LOAD_IMAGE_GRAYSCALE);
		// Imgproc.Canny(imageMat, imageMat, threshold1, threshold2);
		Imgproc.adaptiveThreshold(imageMat, imageMat, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY_INV, 15, 4);
		// Imgproc.
		String strsave = filename.substring(0, filename.length() - 4) + "-adaptiveThreshold" + filename.substring(filename.length() - 4, filename.length());
		Highgui.imwrite(strsave, imageMat);
		image = new Image(getDisplay(), strsave);
		imgLabel.setImage(image);
	}

	private void ketvirtas() {
		Mat imageMat = Highgui.imread(filename, Highgui.CV_LOAD_IMAGE_GRAYSCALE);
		// Imgproc.Canny(imageMat, imageMat, threshold1, threshold2);
		// Imgproc.adaptiveThreshold(imageMat, imageMat, 255,
		// Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY_INV, 15, 4);
		// Imgproc.l
		String strsave = filename.substring(0, filename.length() - 4) + "-adaptiveThreshold" + filename.substring(filename.length() - 4, filename.length());
		Highgui.imwrite(strsave, imageMat);
		image = new Image(getDisplay(), strsave);
		imgLabel.setImage(image);
	}

	private void laplacian() {
		Mat imageMat = Highgui.imread(filename, Highgui.CV_LOAD_IMAGE_GRAYSCALE);
		// Imgproc.Canny(imageMat, imageMat, threshold1, threshold2);
		// Imgproc.adaptiveThreshold(imageMat, imageMat, 255,
		// Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY_INV, 15, 4);
		Imgproc.Laplacian(imageMat, imageMat, CvType.CV_8U);
		String strsave = filename.substring(0, filename.length() - 4) + "-Laplacian" + filename.substring(filename.length() - 4, filename.length());
		Highgui.imwrite(strsave, imageMat);
		image = new Image(getDisplay(), strsave);
		imgLabel.setImage(image);
	}

	private void gausian() {
		Mat imageMat = Highgui.imread(filename, Highgui.CV_LOAD_IMAGE_GRAYSCALE);
		Imgproc.GaussianBlur(imageMat, imageMat, null, 15, 4);
		String strsave = filename.substring(0, filename.length() - 4) + "-Laplacian" + filename.substring(filename.length() - 4, filename.length());
		Highgui.imwrite(strsave, imageMat);
		image = new Image(getDisplay(), strsave);
		imgLabel.setImage(image);
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public void onFileOpen() {
		FileDialog fileChooser = new FileDialog(getShell(), SWT.OPEN);
		fileChooser.setText("Open image file");

		fileChooser.setFilterPath(currentDir);
		fileChooser.setFilterExtensions(new String[] { "*.gif; *.jpg; *.png; *.ico; *.bmp" });
		fileChooser.setFilterNames(new String[] { "SWT image" + " (gif, jpeg, png, ico, bmp)" });
		filename = fileChooser.open();
		if (filename != null) {
			// ImageData imgData = new ImageData( filename );
			image = new Image(getDisplay(), filename);
			// canvas.

			imgLabel.setImage(image);// ( image );
			imgLabel.setSize(560, 310);// ( imgLabel.computeSize( SWT.DEFAULT,
										// SWT.DEFAULT ));
			currentDir = fileChooser.getFilterPath();
		}
	}

	public void showImage(String path) {
		if (path == "") {
			image = new Image(getDisplay(), path);

			imgLabel.setImage(image);// ( image );

			imgLabel.setSize(560, 310);// ( imgLabel.computeSize( SWT.DEFAULT,
										// SWT.DEFAULT ));

		}
	}
}
