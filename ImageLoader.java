import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * A utility class for loading images using a file chooser dialog.
 * Provides a static method to let users select image files and load them as BufferedImage objects.
 */
public class ImageLoader {
    
    /**
     * Opens a file chooser dialog for the user to select an image file
     * and returns the selected image as a BufferedImage.
     * 
     * @return The selected image as a BufferedImage, or null if no file was selected
     *         or an error occurred during loading
     */
    public static BufferedImage loadImage() {
        JFileChooser fileChooser = new JFileChooser();
        
        // Set up file filter for common image formats
        FileNameExtensionFilter imageFilter = new FileNameExtensionFilter(
            "Image Files", "jpg", "jpeg", "png", "gif", "bmp", "tiff", "tif"
        );
        fileChooser.setFileFilter(imageFilter);
        
        // Set dialog title
        fileChooser.setDialogTitle("Select an Image File");
        
        // Show the file chooser dialog
        int result = fileChooser.showOpenDialog(null);
        
        // Check if user selected a file
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            
            try {
                // Load the image using ImageIO
                BufferedImage image = ImageIO.read(selectedFile);
                
                if (image != null) {
                    System.out.println("Successfully loaded image: " + selectedFile.getName());
                    return image;
                } else {
                    System.err.println("Error: Could not load image from file: " + selectedFile.getName());
                    return null;
                }
                
            } catch (IOException e) {
                System.err.println("Error reading image file: " + e.getMessage());
                return null;
            } catch (IllegalArgumentException e) {
                System.err.println("Error: Invalid image format or corrupted file: " + e.getMessage());
                return null;
            } catch (Exception e) {
                System.err.println("Unexpected error while loading image: " + e.getMessage());
                return null;
            }
        } else {
            // User cancelled the file chooser
            System.out.println("No file selected.");
            return null;
        }
    }
    
    /**
     * Loads an image from a specific file path.
     * 
     * @param filePath The path to the image file
     * @return The loaded image as a BufferedImage, or null if loading failed
     */
    public static BufferedImage loadImageFromPath(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                System.err.println("File does not exist: " + filePath);
                return null;
            }
            
            BufferedImage image = ImageIO.read(file);
            if (image != null) {
                System.out.println("Successfully loaded image from path: " + filePath);
                return image;
            } else {
                System.err.println("Could not load image from path: " + filePath);
                return null;
            }
        } catch (Exception e) {
            System.err.println("Error loading image from path: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Main method for testing the ImageLoader functionality.
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println("ImageLoader Test");
        System.out.println("Select an image file when the dialog appears...");
        
        BufferedImage image = loadImage();
        
        if (image != null) {
            System.out.println("Image loaded successfully!");
            System.out.println("Image dimensions: " + image.getWidth() + " x " + image.getHeight());
        } else {
            System.out.println("Failed to load image.");
        }
    }
} 