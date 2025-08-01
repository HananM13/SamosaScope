import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.UIManager;

/**
 * GUI class for the Samosa Viewer application.
 * Provides a user interface to load images, display them, and calculate samosa pixel area.
 */
public class SamosaViewerUI extends JFrame {
    
    // GUI Components
    private JButton loadImageButton;
    private JButton calculateAreaButton;
    private JButton showProcessedButton;
    private JButton calibrateButton;
    private JLabel imageLabel;
    private JLabel statusLabel;
    private JLabel areaLabel;
    private JLabel coverageLabel;
    private JLabel realAreaLabel;
    private JPanel mainPanel;
    private JPanel buttonPanel;
    private JPanel imagePanel;
    private JPanel infoPanel;
    
    // Application data
    private BufferedImage currentImage;
    private BufferedImage processedImage;
    private int samosaPixelArea;
    private double samosaCoveragePercentage;
    private double pixelsPerCm = 0; // Calibration factor
    private boolean isCalibrated = false;
    
    /**
     * Constructor - sets up the GUI components and layout
     */
    public SamosaViewerUI() {
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Samosa Viewer - Pixel Area Calculator");
        setSize(900, 750);
        setLocationRelativeTo(null);
    }
    
    /**
     * Initialize all GUI components
     */
    private void initializeComponents() {
        // Buttons
        loadImageButton = new JButton("Load Image");
        loadImageButton.setFont(new Font("Arial", Font.BOLD, 14));
        loadImageButton.setBackground(new Color(70, 130, 180));
        loadImageButton.setForeground(Color.WHITE);
        loadImageButton.setFocusPainted(false);
        
        calculateAreaButton = new JButton("Calculate Samosa Area");
        calculateAreaButton.setFont(new Font("Arial", Font.BOLD, 14));
        calculateAreaButton.setBackground(new Color(34, 139, 34));
        calculateAreaButton.setForeground(Color.WHITE);
        calculateAreaButton.setFocusPainted(false);
        calculateAreaButton.setEnabled(false);
        
        showProcessedButton = new JButton("Show Processed Image");
        showProcessedButton.setFont(new Font("Arial", Font.BOLD, 14));
        showProcessedButton.setBackground(new Color(255, 140, 0));
        showProcessedButton.setForeground(Color.WHITE);
        showProcessedButton.setFocusPainted(false);
        showProcessedButton.setEnabled(false);
        
        calibrateButton = new JButton("Calibrate");
        calibrateButton.setFont(new Font("Arial", Font.BOLD, 14));
        calibrateButton.setBackground(new Color(128, 0, 128));
        calibrateButton.setForeground(Color.WHITE);
        calibrateButton.setFocusPainted(false);
        calibrateButton.setEnabled(false);
        
        // Labels
        imageLabel = new JLabel("No image loaded");
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVerticalAlignment(JLabel.CENTER);
        imageLabel.setBorder(BorderFactory.createEtchedBorder());
        imageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        imageLabel.setForeground(Color.GRAY);
        
        statusLabel = new JLabel("Ready to load image");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        statusLabel.setForeground(Color.BLACK);
        
        areaLabel = new JLabel("Samosa Pixel Area: Not calculated");
        areaLabel.setFont(new Font("Arial", Font.BOLD, 14));
        areaLabel.setForeground(new Color(139, 69, 19));
        
        coverageLabel = new JLabel("Samosa Coverage: Not calculated");
        coverageLabel.setFont(new Font("Arial", Font.BOLD, 14));
        coverageLabel.setForeground(new Color(139, 69, 19));
        
        realAreaLabel = new JLabel("Real Area: Not calibrated");
        realAreaLabel.setFont(new Font("Arial", Font.BOLD, 14));
        realAreaLabel.setForeground(new Color(0, 100, 0));
        
        // Panels
        mainPanel = new JPanel();
        buttonPanel = new JPanel();
        imagePanel = new JPanel();
        infoPanel = new JPanel();
    }
    
    /**
     * Set up the layout of the GUI components
     */
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Button panel (top)
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(240, 240, 240));
        buttonPanel.add(loadImageButton);
        buttonPanel.add(calibrateButton);
        buttonPanel.add(calculateAreaButton);
        buttonPanel.add(showProcessedButton);
        
        // Image panel (center)
        imagePanel.setLayout(new BorderLayout());
        imagePanel.setBackground(Color.WHITE);
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        imagePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Info panel (bottom)
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(new Color(248, 248, 248));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.setBackground(new Color(248, 248, 248));
        statusPanel.add(statusLabel);
        
        JPanel areaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        areaPanel.setBackground(new Color(248, 248, 248));
        areaPanel.add(areaLabel);
        
        JPanel coveragePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        coveragePanel.setBackground(new Color(248, 248, 248));
        coveragePanel.add(coverageLabel);
        
        JPanel realAreaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        realAreaPanel.setBackground(new Color(248, 248, 248));
        realAreaPanel.add(realAreaLabel);
        
        infoPanel.add(statusPanel);
        infoPanel.add(areaPanel);
        infoPanel.add(coveragePanel);
        infoPanel.add(realAreaPanel);
        
        // Add panels to main frame
        add(buttonPanel, BorderLayout.NORTH);
        add(imagePanel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Set up event handlers for buttons
     */
    private void setupEventHandlers() {
        loadImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadImage();
            }
        });
        
        calibrateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calibrateImage();
            }
        });
        
        calculateAreaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateSamosaArea();
            }
        });
        
        showProcessedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showProcessedImage();
            }
        });
    }
    
    /**
     * Load an image using the ImageLoader class
     */
    private void loadImage() {
        try {
            statusLabel.setText("Loading image...");
            statusLabel.setForeground(Color.BLUE);
            
            // Use the ImageLoader class to load the image
            currentImage = ImageLoader.loadImage();
            
            if (currentImage != null) {
                displayImage(currentImage);
                calculateAreaButton.setEnabled(true);
                calibrateButton.setEnabled(true);
                showProcessedButton.setEnabled(false);
                processedImage = null;
                isCalibrated = false;
                statusLabel.setText("Image loaded successfully: " + currentImage.getWidth() + " x " + currentImage.getHeight() + " pixels");
                statusLabel.setForeground(new Color(0, 128, 0));
                areaLabel.setText("Samosa Pixel Area: Not calculated");
                coverageLabel.setText("Samosa Coverage: Not calculated");
                realAreaLabel.setText("Real Area: Not calibrated");
            } else {
                statusLabel.setText("Failed to load image");
                statusLabel.setForeground(Color.RED);
                calculateAreaButton.setEnabled(false);
                calibrateButton.setEnabled(false);
                showProcessedButton.setEnabled(false);
            }
            
        } catch (Exception e) {
            statusLabel.setText("Error loading image: " + e.getMessage());
            statusLabel.setForeground(Color.RED);
            calculateAreaButton.setEnabled(false);
            calibrateButton.setEnabled(false);
            showProcessedButton.setEnabled(false);
        }
    }
    
    /**
     * Calibrate the image to convert pixels to real-world units
     */
    private void calibrateImage() {
        if (currentImage == null) {
            JOptionPane.showMessageDialog(this, 
                "Please load an image first!", 
                "No Image", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Show calibration dialog
        String referenceSizeStr = JOptionPane.showInputDialog(this,
            "Enter the size of a reference object in the image (in cm):\n" +
            "For example, if you have a coin that is 2.5 cm in diameter,\n" +
            "measure the coin's diameter in pixels and enter 2.5",
            "Calibration",
            JOptionPane.QUESTION_MESSAGE);
        
        if (referenceSizeStr != null && !referenceSizeStr.trim().isEmpty()) {
            try {
                double referenceSizeCm = Double.parseDouble(referenceSizeStr.trim());
                
                // Get pixel measurement from user
                String pixelMeasurementStr = JOptionPane.showInputDialog(this,
                    "Enter the size of the same reference object in pixels:\n" +
                    "You can use any measurement tool or count pixels manually",
                    "Pixel Measurement",
                    JOptionPane.QUESTION_MESSAGE);
                
                if (pixelMeasurementStr != null && !pixelMeasurementStr.trim().isEmpty()) {
                    double pixelMeasurement = Double.parseDouble(pixelMeasurementStr.trim());
                    
                    if (pixelMeasurement > 0) {
                        pixelsPerCm = pixelMeasurement / referenceSizeCm;
                        isCalibrated = true;
                        
                        statusLabel.setText("Calibration completed: " + String.format("%.2f", pixelsPerCm) + " pixels/cm");
                        statusLabel.setForeground(new Color(0, 128, 0));
                        realAreaLabel.setText("Real Area: Ready for calculation");
                        
                        JOptionPane.showMessageDialog(this,
                            "Calibration successful!\n" +
                            "Conversion factor: " + String.format("%.2f", pixelsPerCm) + " pixels per cm\n\n" +
                            "You can now calculate real-world areas.",
                            "Calibration Complete",
                            JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this,
                            "Invalid pixel measurement. Must be greater than 0.",
                            "Invalid Input",
                            JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                    "Invalid number format. Please enter a valid number.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Display the loaded image in the GUI
     */
    private void displayImage(BufferedImage image) {
        // Calculate scaled dimensions to fit in the panel
        int maxWidth = 700;
        int maxHeight = 500;
        
        int originalWidth = image.getWidth();
        int originalHeight = image.getHeight();
        
        double scaleX = (double) maxWidth / originalWidth;
        double scaleY = (double) maxHeight / originalHeight;
        double scale = Math.min(scaleX, scaleY);
        
        int scaledWidth = (int) (originalWidth * scale);
        int scaledHeight = (int) (originalHeight * scale);
        
        // Create scaled image
        Image scaledImage = image.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
        
        // Create ImageIcon and set it to the label
        ImageIcon imageIcon = new ImageIcon(scaledImage);
        imageLabel.setIcon(imageIcon);
        imageLabel.setText("");
        
        // Update the frame size if needed
        pack();
        setSize(Math.max(getWidth(), 900), Math.max(getHeight(), 750));
    }
    
    /**
     * Calculate the samosa pixel area using ImageProcessor and AreaCalculator
     */
    private void calculateSamosaArea() {
        if (currentImage == null) {
            JOptionPane.showMessageDialog(this, 
                "Please load an image first!", 
                "No Image", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            statusLabel.setText("Processing image and calculating samosa area...");
            statusLabel.setForeground(Color.BLUE);
            
            // Process the image and calculate area
            processImageAndCalculateArea();
            
        } catch (Exception e) {
            statusLabel.setText("Error calculating area: " + e.getMessage());
            statusLabel.setForeground(Color.RED);
        }
    }
    
    /**
     * Process image and calculate samosa area using ImageProcessor and AreaCalculator
     */
    private void processImageAndCalculateArea() {
        // Use SwingWorker for background processing
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Process the image for samosa detection
                processedImage = ImageProcessor.processForSamosaDetection(currentImage);
                
                // Calculate samosa area using AreaCalculator
                samosaPixelArea = AreaCalculator.calculateSamosaArea(currentImage);
                
                // Calculate coverage percentage
                samosaCoveragePercentage = AreaCalculator.calculateSamosaCoveragePercentage(currentImage);
                
                return null;
            }
            
            @Override
            protected void done() {
                try {
                    get();
                    
                    // Update the display
                    if (AreaCalculator.isValidArea(samosaPixelArea)) {
                        areaLabel.setText("Samosa Pixel Area: " + AreaCalculator.formatArea(samosaPixelArea) + " pixels");
                        coverageLabel.setText("Samosa Coverage: " + AreaCalculator.formatPercentage(samosaCoveragePercentage));
                        
                        // Calculate real-world area if calibrated
                        if (isCalibrated && pixelsPerCm > 0) {
                            double realAreaCm2 = AreaCalculator.calculateEstimatedPhysicalArea(
                                samosaPixelArea, 
                                currentImage.getWidth(), 
                                currentImage.getHeight(), 
                                pixelsPerCm
                            );
                            
                            if (AreaCalculator.isValidArea(realAreaCm2)) {
                                realAreaLabel.setText("Real Area: " + AreaCalculator.formatArea(realAreaCm2) + " cm²");
                            } else {
                                realAreaLabel.setText("Real Area: Calculation error");
                            }
                        } else {
                            realAreaLabel.setText("Real Area: Not calibrated (use Calibrate button)");
                        }
                        
                        statusLabel.setText("Area calculation completed successfully");
                        statusLabel.setForeground(new Color(0, 128, 0));
                        showProcessedButton.setEnabled(true);
                        
                        // Show results in a dialog
                        showResultsDialog();
                    } else {
                        areaLabel.setText("Samosa Pixel Area: No samosa detected");
                        coverageLabel.setText("Samosa Coverage: 0%");
                        realAreaLabel.setText("Real Area: No samosa detected");
                        statusLabel.setText("No samosa detected in the image");
                        statusLabel.setForeground(Color.ORANGE);
                        showProcessedButton.setEnabled(false);
                    }
                    
                } catch (Exception e) {
                    statusLabel.setText("Error during processing: " + e.getMessage());
                    statusLabel.setForeground(Color.RED);
                    areaLabel.setText("Samosa Pixel Area: Error");
                    coverageLabel.setText("Samosa Coverage: Error");
                    realAreaLabel.setText("Real Area: Error");
                    showProcessedButton.setEnabled(false);
                }
            }
        };
        
        worker.execute();
    }
    
    /**
     * Show the processed image with samosa detection highlights
     */
    private void showProcessedImage() {
        if (processedImage != null) {
            displayImage(processedImage);
            statusLabel.setText("Showing processed image with samosa detection highlights");
            statusLabel.setForeground(new Color(0, 128, 0));
        } else {
            JOptionPane.showMessageDialog(this,
                "No processed image available. Please calculate samosa area first.",
                "No Processed Image",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Show results dialog with detailed information
     */
    private void showResultsDialog() {
        StringBuilder message = new StringBuilder();
        message.append("Samosa Analysis Results:\n\n");
        message.append("Image Dimensions: ").append(currentImage.getWidth()).append(" x ").append(currentImage.getHeight()).append(" pixels\n");
        message.append("Total Image Area: ").append(currentImage.getWidth() * currentImage.getHeight()).append(" pixels\n");
        message.append("Samosa Pixel Area: ").append(AreaCalculator.formatArea(samosaPixelArea)).append(" pixels\n");
        message.append("Samosa Coverage: ").append(AreaCalculator.formatPercentage(samosaCoveragePercentage)).append("\n");
        
        if (isCalibrated && pixelsPerCm > 0) {
            double realAreaCm2 = AreaCalculator.calculateEstimatedPhysicalArea(
                samosaPixelArea, 
                currentImage.getWidth(), 
                currentImage.getHeight(), 
                pixelsPerCm
            );
            
            if (AreaCalculator.isValidArea(realAreaCm2)) {
                message.append("Real Area: ").append(AreaCalculator.formatArea(realAreaCm2)).append(" cm²\n");
                message.append("Calibration: ").append(String.format("%.2f", pixelsPerCm)).append(" pixels/cm\n");
            }
        } else {
            message.append("Real Area: Not calibrated\n");
            message.append("Tip: Use the Calibrate button to measure real-world areas\n");
        }
        
        message.append("\nDetection Method: Color-based analysis\n");
        message.append("Detected brown/orange regions typical of samosas.\n\n");
        message.append("Note: This is an automated detection based on color analysis.");
        
        JOptionPane.showMessageDialog(this,
            message.toString(),
            "Samosa Analysis Results",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Main method to launch the application
     */
    public static void main(String[] args) {
        // Launch the application on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SamosaViewerUI viewer = new SamosaViewerUI();
                viewer.setVisible(true);
            }
        });
    }
} 