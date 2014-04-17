package composites;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.eclipse.swt.widgets.Text;

public class ParentComposite extends Composite {
	String currentDir = "";
	Image image;
	String filename = "";
	ArrayList<File> fileArray = new ArrayList<File>();
	ArrayList<String> pathList = new ArrayList<String>();
	String fileDirectory;// = new File(directoryName);
	Button btnTikSaknineDirektorija;
	Text text;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public ParentComposite(Composite parent, int style) {
		super(parent, SWT.BORDER);
		setLayout(null);
		//System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		Button btnNewButton = new Button(this, SWT.NONE);
		btnNewButton.setBounds(10, 10, 117, 25);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				onFileOpen();
			}
		});
		btnNewButton.setText("Pasirinkti nuotrauka");

		Button btnPasirinktiAplanka = new Button(this, SWT.NONE);
		btnPasirinktiAplanka.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				onDirecotryOpen();
			}
		});
		btnPasirinktiAplanka.setBounds(133, 10, 104, 25);
		btnPasirinktiAplanka.setText("Pasirinkti aplanka");

		btnTikSaknineDirektorija = new Button(this, SWT.CHECK);
		btnTikSaknineDirektorija.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (fileDirectory.length() > 0) {
					fileArray = new ArrayList<File>();
					pathList = new ArrayList<String>();
					listf(fileDirectory, btnTikSaknineDirektorija.getSelection());
					printInfo();
				}
			}
		});
		btnTikSaknineDirektorija.setBounds(243, 19, 136, 16);
		btnTikSaknineDirektorija.setText("Tik saknine direktorija");
		
		Button btnPradinisVaizdas = new Button(this, SWT.NONE);
		btnPradinisVaizdas.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (filename.length()>0){
					setImage(filename);
				}
			}
		});
		btnPradinisVaizdas.setBounds(546, 10, 104, 25);
		btnPradinisVaizdas.setText("Pradinis vaizdas");
		
		text = new Text(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL);
		text.setEditable(false);
		text.setBounds(10, 40, 640, 420);
		// imgLabel.setText("New Label");

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
			
			image = new Image(getDisplay(), filename);
			setImage(filename);
			currentDir = fileChooser.getFilterPath();
			pathList.clear();
			pathList.add(filename);
		}
	}

	public void onDirecotryOpen() {
		DirectoryDialog directoryChooser = new DirectoryDialog(getShell(), SWT.OPEN);
		directoryChooser.setText("Open image folder");
		directoryChooser.setFilterPath(currentDir);
		fileDirectory = directoryChooser.open();
		if (fileDirectory != null) {
			fileArray.clear();
			pathList.clear();
			currentDir = fileDirectory;
			listf(fileDirectory, btnTikSaknineDirektorija.getSelection());
			printInfo();
			text.setBackgroundImage(null);
		}
	}

	public void printInfo() {
		// imgLabel.
		String txt = "";
		txt += "Pasirinkta direktorija: " + fileDirectory + "\nFailu kiekis:" + fileArray.size() + "\n";
		for (String str : pathList) {
			txt += str + "\n";
		}
		text.setText(txt);
	}

	public void listf(String directoryName, boolean subdirectory) {
		File directory = new File(directoryName);
		// get all the files from a directory
		File[] fList = directory.listFiles();
		for (File file : fList) {
			if (file.isFile()) {
				if (fileTypeCheck(file.getAbsolutePath(), ".GIF;.gif;.jpeg;.JPEG;.jpg;.JPG;.png;.PNG;.bmp;.BMP")){
					fileArray.add(file);
					pathList.add(file.getAbsolutePath());
				}
			} else if ((file.isDirectory())&&(!subdirectory)) {
				listf(file.getAbsolutePath(), false);
			}
		}
	}

	private boolean fileTypeCheck(String p, String e) {
		// gif, jpeg, png, ico, bmp
		String eList[] = e.split(";");
		for (String s : eList) {
			if (p.contains(s)){return true;}
		}
		return false;
	}

	public void setImage(String path) {
			Mat imageMat = Highgui.imread(path);
			Mat resizeimage = new Mat();
			double w = imageMat.width();
			double h= imageMat.height();
			double x1 = 0;
			double x2 = 0;
			x1=(640*100)/w;
			x2=(420*100)/h;
			Size sz;
			if (x2>x1){
				sz= new Size(w*x1/100,h*x1/100);
			}else {
				sz= new Size(w*x2/100,h*x2/100);
			}
			Imgproc.resize( imageMat, resizeimage, sz );	
			MatOfByte matOfByte = new MatOfByte();
			Highgui.imencode(".jpg", resizeimage, matOfByte);
			byte[] bytes = matOfByte.toArray();
			InputStream inputStream = new ByteArrayInputStream(bytes);
			Image i = new Image(getDisplay(), inputStream);
			text.setBackgroundImage(i);
	}
}



// ////////SIUKSLINELIS////////////

// System.out.println("veikia");
// System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
// Mat mat = Mat.eye(3, 3, CvType.CV_8UC1);
// System.out.println("mat = " + mat.dump());
