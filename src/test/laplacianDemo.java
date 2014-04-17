package test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class laplacianDemo extends JApplet {
	
	Image edgeImage, accImage, outputImage;
	MediaTracker tracker = null;
	PixelGrabber grabber = null;
	int width = 0, height = 0;
	String fileNames[] = {"lena.png", "microphone.png", "screw.png", "drawing.png", "film.png", "shapes.png"};

	javax.swing.Timer timer;

	int sigmavalue=20;

	int imageNumber=0;
	static int progress=0;
	static int noise=5;
	public int orig[] = null;
	
	Image image[] = new Image[fileNames.length];

	static Image Marr, Lap;
	
	JProgressBar progressBar;
	JPanel controlPanel,noisePanel, modePanel,imagePanel, progressPanel;
	JLabel origLabel, outputLabel,modelLabel,noiseLabel,noiseLabel2,noiseLabel3,comboLabel,templateLabel,processing;
	JSlider sigmaSlider, radiusSlider, noiseSlider;
	JRadioButton gaussianRadio, condimentRadio;
	JRadioButton MarrRadio, LapRadio;
	ButtonGroup radiogroup;
	ButtonGroup radiogroup2;
	JComboBox imSel;
	//static LoG edgeDetector;
	static gaussianNoise gNoise;
//	static condimentNoise cNoise;
	static gaussianFilter filter;
	String noisemode="Gaussian";
	String outputmode="Lap";
	 
	 
	   	// Applet init function	
	public void init() {
		
		tracker = new MediaTracker(this);
		for(int i = 0; i < fileNames.length; i++) {
			image[i] = getImage(this.getCodeBase(),fileNames[i]);
			image[i] = image[i].getScaledInstance(256, 256, Image.SCALE_SMOOTH);
			tracker.addImage(image[i], i);
		}
		try {
			tracker.waitForAll();
		}
		catch(InterruptedException e) {
			System.out.println("error: " + e);
		}
		
		Container cont = getContentPane();
		cont.removeAll();
		cont.setBackground(Color.black);
		cont.setLayout(new BorderLayout());
		
		controlPanel = new JPanel();
		controlPanel.setLayout(new GridLayout(2,4,15,0));
		controlPanel.setBackground(new Color(192,204,226));
		imagePanel = new JPanel();
		imagePanel.setBackground(new Color(192,204,226));
		progressPanel = new JPanel();
		progressPanel.setBackground(new Color(192,204,226));
		progressPanel.setLayout(new GridLayout(2,1));
		noisePanel = new JPanel();
		noisePanel.setBackground(new Color(192,204,226));
		noisePanel.setLayout(new GridLayout(2,1));
		modePanel = new JPanel();
		modePanel.setBackground(new Color(192,204,226));
		modePanel.setLayout(new GridLayout(2,1));

		comboLabel = new JLabel("IMAGE");
		comboLabel.setHorizontalAlignment(JLabel.CENTER);
		controlPanel.add(comboLabel);

		noiseLabel3 = new JLabel("NOISE TYPE");
		noiseLabel3.setHorizontalAlignment(JLabel.CENTER);
		controlPanel.add(noiseLabel3);

		noiseLabel = new JLabel("NOISE = 5%");
		noiseLabel.setHorizontalAlignment(JLabel.CENTER);
		controlPanel.add(noiseLabel);

		templateLabel = new JLabel("GAUSSIAN FILTER SIZE = 20"); 
		templateLabel.setHorizontalAlignment(JLabel.CENTER);
		controlPanel.add(templateLabel);

/*		modelLabel = new JLabel("OUTPUT MODE");
		modelLabel.setHorizontalAlignment(JLabel.CENTER);
		controlPanel.add(modelLabel);
*/

		processing = new JLabel("Processing...");
		processing.setHorizontalAlignment(JLabel.LEFT);
		progressBar = new JProgressBar(0,100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true); //get space for the string
        progressBar.setString("");          //but don't paint it
		progressPanel.add(processing);
		progressPanel.add(progressBar);
		
		width = image[imageNumber].getWidth(null);
		height = image[imageNumber].getHeight(null);

		imSel = new JComboBox(fileNames);
		imageNumber = imSel.getSelectedIndex();
		imSel.addActionListener( 
			new ActionListener() {  
				public void actionPerformed(ActionEvent e) {
					imageNumber = imSel.getSelectedIndex();
					origLabel.setIcon(new ImageIcon(image[imageNumber]));	
					processImage();
				}
			}
		);
		controlPanel.add(imSel, BorderLayout.PAGE_START);

        timer = new javax.swing.Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
              //  progressBar.setValue((edgeDetector.getProgress()));
            }
        });

		origLabel = new JLabel("Original Image", new ImageIcon(image[imageNumber]), JLabel.CENTER);
		origLabel.setVerticalTextPosition(JLabel.BOTTOM);
		origLabel.setHorizontalTextPosition(JLabel.CENTER);
		origLabel.setForeground(Color.blue);
		imagePanel.add(origLabel);
		
		
		outputLabel = new JLabel("Marr Hildreth", new ImageIcon(image[imageNumber]), JLabel.CENTER);
		outputLabel.setVerticalTextPosition(JLabel.BOTTOM);
		outputLabel.setHorizontalTextPosition(JLabel.CENTER);
		outputLabel.setForeground(Color.blue);
		imagePanel.add(outputLabel);
	

		gaussianRadio = new JRadioButton("Gaussian");
    	gaussianRadio.setActionCommand("Gaussian");
		gaussianRadio.setBackground(new Color(192,204,226));
		gaussianRadio.setHorizontalAlignment(SwingConstants.CENTER);
		condimentRadio = new JRadioButton("Condiment");
    	condimentRadio.setActionCommand("Condiment");
		condimentRadio.setHorizontalAlignment(SwingConstants.CENTER);
		condimentRadio.setBackground(new Color(192,204,226));
    	gaussianRadio.setSelected(true);
	    radiogroup = new ButtonGroup();
	    radiogroup.add(condimentRadio);
	    radiogroup.add(gaussianRadio);
	    condimentRadio.addActionListener(new radiolistener());
	    gaussianRadio.addActionListener(new radiolistener());
		noisePanel.add(gaussianRadio);
		noisePanel.add(condimentRadio);
		controlPanel.add(noisePanel);


		noiseSlider = new JSlider(JSlider.HORIZONTAL, 0, 50, 5);
		noiseSlider.addChangeListener(new noiseListener());
		noiseSlider.setMajorTickSpacing(10);
		noiseSlider.setMinorTickSpacing(2);
		noiseSlider.setPaintTicks(true);
		noiseSlider.setPaintLabels(true);
		noiseSlider.setBackground(new Color(192,204,226));
		controlPanel.add(noiseSlider);

		/*Hashtable labelTable = new Hashtable();
		labelTable.put( new Integer( 10 ), new JLabel("1.0") );
		labelTable.put( new Integer( 20 ), new JLabel("2.0") );
		labelTable.put( new Integer( 30 ), new JLabel("3.0") );
		labelTable.put( new Integer( 40 ), new JLabel("4.0") );*/
		sigmaSlider = new JSlider(JSlider.HORIZONTAL, 10, 40, 20);
		sigmaSlider.addChangeListener(new templateListener());
		sigmaSlider.setMajorTickSpacing(10);
		sigmaSlider.setMinorTickSpacing(2);
		sigmaSlider.setPaintTicks(true);
		sigmaSlider.setPaintLabels(true);
		sigmaSlider.setBackground(new Color(192,204,226));
		//sigmaSlider.setLabelTable( labelTable );
		controlPanel.add(sigmaSlider);

/*		LapRadio = new JRadioButton("Laplacian");
    	LapRadio.setActionCommand("Lap");
		LapRadio.setBackground(new Color(192,204,226));
		LapRadio.setHorizontalAlignment(SwingConstants.CENTER);
    	LapRadio.setSelected(true);
		MarrRadio = new JRadioButton("Marr Hildreth");
    	MarrRadio.setActionCommand("Marr");
		MarrRadio.setHorizontalAlignment(SwingConstants.CENTER);
		MarrRadio.setBackground(new Color(192,204,226));
	    radiogroup2 = new ButtonGroup();
	    radiogroup2.add(LapRadio);
	    radiogroup2.add(MarrRadio);
	    LapRadio.addActionListener(new radiolistener2());
	    MarrRadio.addActionListener(new radiolistener2());
		modePanel.add(LapRadio);
		modePanel.add(MarrRadio);
		controlPanel.add(modePanel);
*/
		cont.add(controlPanel, BorderLayout.NORTH);
		cont.add(imagePanel, BorderLayout.CENTER);
		cont.add(progressPanel, BorderLayout.SOUTH);

		processImage();

	}
	class radiolistener implements ActionListener{
	    public void actionPerformed(ActionEvent e) {
			noisemode=e.getActionCommand();
			processImage();
	    }
	}

  	class templateListener implements ChangeListener {
	    public void stateChanged(ChangeEvent e) {
	        JSlider source = (JSlider)e.getSource();
	        if (!source.getValueIsAdjusting()) {
				int val=source.getValue();
				System.out.println("template="+val);
				sigmavalue=val;
				templateLabel.setText("GAUSSIAN FILTER SIZE = "+val);
				processImage();
	        }    
	    }
	}
	class noiseListener implements ChangeListener {
	    public void stateChanged(ChangeEvent e) {
	        JSlider source = (JSlider)e.getSource();
	        if (!source.getValueIsAdjusting()) {
				System.out.println("noise="+source.getValue()+"%");
				noise=source.getValue();
				noiseLabel.setText("NOISE = "+source.getValue()+"%");
				processImage();
	        }    
	    }
	}

	private void processImage(){
		orig=new int[width*height];
		PixelGrabber grabber = new PixelGrabber(image[imageNumber], 0, 0, width, height, orig, 0, width);
		try {
			grabber.grabPixels();
		}
		catch(InterruptedException e2) {
			System.out.println("error: " + e2);
		}
		progressBar.setMaximum(width-sigmavalue);

		processing.setText("Processing...");
		sigmaSlider.setEnabled(false);
		imSel.setEnabled(false);
		noiseSlider.setEnabled(false);
		gaussianRadio.setEnabled(false);
		condimentRadio.setEnabled(false);

		gNoise = new gaussianNoise();
	//	cNoise = new condimentNoise();
	//	edgeDetector =3;// = new LoG();


		gNoise.init(orig,width,height,(float)noise);
	//	cNoise.init(orig,width,height,(float)noise/100);
		if(noisemode=="Gaussian"){
			orig=gNoise.process();
			origLabel.setIcon(new ImageIcon(createImage(new MemoryImageSource(width, height, orig, 0, width))));
			origLabel.setText("Original Image with Gaussian Noise");
		}
		if(noisemode=="Condiment"){
		//	orig=cNoise.process();
			origLabel.setIcon(new ImageIcon(createImage(new MemoryImageSource(width, height, orig, 0, width))));
			origLabel.setText("Original Image with Condiment Noise");
		}

		timer.start();
		new Thread(){
			public void run(){
		//		edgeDetector.init(orig,width,height,(double)sigmavalue/10);
		//		edgeDetector.generateTemplate();

				Lap=createImage(new MemoryImageSource(width, height, orig, 0, width));
				Marr=createImage(new MemoryImageSource(width, height, orig, 0, width));
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						outputLabel.setIcon(new ImageIcon(Marr));
						processing.setText("Done");
						sigmaSlider.setEnabled(true);
						imSel.setEnabled(true);
						noiseSlider.setEnabled(true);
						gaussianRadio.setEnabled(true);
						condimentRadio.setEnabled(true);
					}
				});
			}
		}.start();
	}

}