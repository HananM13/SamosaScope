import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Utility class for processing image files and extracting basic information.
 * Provides static methods for common image processing tasks including samosa detection.
 */
public class ImageProcessor {
    
    /**
     * Gets the pixel dimensions (width and height) of an image file.
     * 
     * @param imageFile The image file to analyze
     * @return An int array containing [width, height] in pixels, or null if the file cannot be read
     * @throws IllegalArgumentException if the imageFile parameter is null
     * @throws IOException if there is an error reading the image file
     */
    public static int[] getPixelDimensions(File imageFile) throws IllegalArgumentException, IOException {
        // Validate input parameter
        if (imageFile == null) {
            throw new IllegalArgumentException("Image file cannot be null");
        }
        
        // Check if file exists
        if (!imageFile.exists()) {
            throw new IOException("Image file does not exist: " + imageFile.getPath());
        }
        
        // Check if file is readable
        if (!imageFile.canRead()) {
            throw new IOException("Image file is not readable: " + imageFile.getPath());
        }
        
        // Check if file is actually a file (not a directory)
        if (!imageFile.isFile()) {
            throw new IOException("Path is not a file: " + imageFile.getPath());
        }
        
        try {
            // Load the image using ImageIO
            BufferedImage image = ImageIO.read(imageFile);
            
            // Check if image was successfully loaded
            if (image == null) {
                throw new IOException("Could not read image file: " + imageFile.getPath() + 
                                   " (unsupported format or corrupted file)");
            }
            
            // Return width and height as an array
            int[] dimensions = new int[2];
            dimensions[0] = image.getWidth();   // width
            dimensions[1] = image.getHeight();  // height
            
            return dimensions;
            
        } catch (IOException e) {
            // Re-throw IOException with more specific message
            throw new IOException("Error reading image file: " + imageFile.getPath() + 
                               " - " + e.getMessage(), e);
        } catch (Exception e) {
            // Handle any other unexpected errors
            throw new IOException("Unexpected error while reading image file: " + 
                               imageFile.getPath() + " - " + e.getMessage(), e);
        }
    }
    
    /**
     * Processes an image to detect samosa-like regions based on color analysis.
     * This is a simplified algorithm that looks for brown/orange colors typical of samosas.
     * 
     * @param image The image to process
     * @return A processed image with samosa regions highlighted, or null if processing fails
     */
    public static BufferedImage processForSamosaDetection(BufferedImage image) {
        if (image == null) {
            return null;
        }
        
        try {
            int width = image.getWidth();
            int height = image.getHeight();
            
            // Create a new image for the processed result
            BufferedImage processedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            
            // Define color ranges for samosa detection (brown/orange colors)
            int samosaPixelCount = 0;
            
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    Color pixelColor = new Color(image.getRGB(x, y));
                    
                    // Check if pixel color is in the samosa color range
                    if (isSamosaColor(pixelColor)) {
                        // Highlight samosa pixels in red for visualization
                        processedImage.setRGB(x, y, Color.RED.getRGB());
                        samosaPixelCount++;
                    } else {
                        // Keep original color for non-samosa pixels
                        processedImage.setRGB(x, y, image.getRGB(x, y));
                    }
                }
            }
            
            System.out.println("Samosa detection completed. Found " + samosaPixelCount + " samosa pixels.");
            return processedImage;
            
        } catch (Exception e) {
            System.err.println("Error processing image for samosa detection: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Determines if a color is likely to be part of a samosa based on RGB values.
     * This is a simplified algorithm that looks for brown/orange color ranges.
     * 
     * @param color The color to analyze
     * @return true if the color is likely to be part of a samosa, false otherwise
     */
    private static boolean isSamosaColor(Color color) {
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        
        // Define color ranges for samosa detection
        // Brown/orange colors typically have higher red and green values, lower blue
        
        // Check for brown/orange color ranges
        boolean isBrownish = (red > 100 && red < 200) && 
                           (green > 50 && green < 150) && 
                           (blue < 100);
        
        // Check for golden brown ranges
        boolean isGoldenBrown = (red > 150 && red < 220) && 
                              (green > 100 && green < 180) && 
                              (blue < 80);
        
        // Check for darker brown ranges
        boolean isDarkBrown = (red > 80 && red < 140) && 
                            (green > 40 && green < 100) && 
                            (blue < 60);
        
        return isBrownish || isGoldenBrown || isDarkBrown;
    }
    
    /**
     * Calculates the area of samosa pixels in an image.
     * 
     * @param image The image to analyze
     * @return The number of samosa pixels found
     */
    public static int calculateSamosaPixelArea(BufferedImage image) {
        if (image == null) {
            return 0;
        }
        
        int width = image.getWidth();
        int height = image.getHeight();
        int samosaPixelCount = 0;
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color pixelColor = new Color(image.getRGB(x, y));
                if (isSamosaColor(pixelColor)) {
                    samosaPixelCount++;
                }
            }
        }
        
        return samosaPixelCount;
    }
    
    /**
     * Gets the pixel dimensions of an image file and returns them as a formatted string.
     * This is a convenience method that handles exceptions internally.
     * 
     * @param imageFile The image file to analyze
     * @return A formatted string with width and height, or an error message
     */
    public static String getPixelDimensionsAsString(File imageFile) {
        try {
            int[] dimensions = getPixelDimensions(imageFile);
            return String.format("%d x %d pixels", dimensions[0], dimensions[1]);
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        } catch (IOException e) {
            return "Error: " + e.getMessage();
        }
    }
    
    /**
     * Gets the width of an image file.
     * 
     * @param imageFile The image file to analyze
     * @return The width in pixels
     * @throws IllegalArgumentException if the imageFile parameter is null
     * @throws IOException if there is an error reading the image file
     */
    public static int getWidth(File imageFile) throws IllegalArgumentException, IOException {
        int[] dimensions = getPixelDimensions(imageFile);
        return dimensions[0];
    }
    
    /**
     * Gets the height of an image file.
     * 
     * @param imageFile The image file to analyze
     * @return The height in pixels
     * @throws IllegalArgumentException if the imageFile parameter is null
     * @throws IOException if there is an error reading the image file
     */
    public static int getHeight(File imageFile) throws IllegalArgumentException, IOException {
        int[] dimensions = getPixelDimensions(imageFile);
        return dimensions[1];
    }
    
    /**
     * Calculates the total pixel area of an image file.
     * 
     * @param imageFile The image file to analyze
     * @return The total number of pixels (width * height)
     * @throws IllegalArgumentException if the imageFile parameter is null
     * @throws IOException if there is an error reading the image file
     */
    public static long getPixelArea(File imageFile) throws IllegalArgumentException, IOException {
        int[] dimensions = getPixelDimensions(imageFile);
        return (long) dimensions[0] * dimensions[1];
    }
    
    /**
     * Main method for testing the ImageProcessor functionality.
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println("ImageProcessor Test");
        System.out.println("==================");
        
        // Test with a sample file path (this will fail unless the file exists)
        File testFile = new File("test_image.jpg");
        
        try {
            int[] dimensions = getPixelDimensions(testFile);
            System.out.println("Image dimensions: " + dimensions[0] + " x " + dimensions[1] + " pixels");
            System.out.println("Total pixel area: " + getPixelArea(testFile) + " pixels");
            
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid argument: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO Error: " + e.getMessage());
        }
        
        // Test the string version
        System.out.println("Dimensions as string: " + getPixelDimensionsAsString(testFile));
    }
} 