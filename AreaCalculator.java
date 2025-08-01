import java.awt.image.BufferedImage;

/**
 * Utility class for calculating areas of various geometric shapes and samosa regions.
 * Provides static methods for common area calculations with input validation.
 */
public class AreaCalculator {
    
    /**
     * Calculates the area of a triangle using the formula: (0.5 × base × height).
     * 
     * @param base The base length of the triangle
     * @param height The height of the triangle
     * @return The area of the triangle, or -1 if the input is invalid (negative or zero)
     */
    public static double calculateTriangleArea(double base, double height) {
        // Input validation: check for negative or zero values
        if (base <= 0 || height <= 0) {
            return -1;
        }
        
        // Calculate area using the formula: (0.5 × base × height)
        return 0.5 * base * height;
    }
    
    /**
     * Calculates the area of a rectangle.
     * 
     * @param length The length of the rectangle
     * @param width The width of the rectangle
     * @return The area of the rectangle, or -1 if the input is invalid (negative or zero)
     */
    public static double calculateRectangleArea(double length, double width) {
        // Input validation: check for negative or zero values
        if (length <= 0 || width <= 0) {
            return -1;
        }
        
        return length * width;
    }
    
    /**
     * Calculates the area of a circle.
     * 
     * @param radius The radius of the circle
     * @return The area of the circle, or -1 if the input is invalid (negative or zero)
     */
    public static double calculateCircleArea(double radius) {
        // Input validation: check for negative or zero values
        if (radius <= 0) {
            return -1;
        }
        
        return Math.PI * radius * radius;
    }
    
    /**
     * Calculates the area of a square.
     * 
     * @param side The length of one side of the square
     * @return The area of the square, or -1 if the input is invalid (negative or zero)
     */
    public static double calculateSquareArea(double side) {
        // Input validation: check for negative or zero values
        if (side <= 0) {
            return -1;
        }
        
        return side * side;
    }
    
    /**
     * Calculates the area of a parallelogram.
     * 
     * @param base The base length of the parallelogram
     * @param height The height of the parallelogram
     * @return The area of the parallelogram, or -1 if the input is invalid (negative or zero)
     */
    public static double calculateParallelogramArea(double base, double height) {
        // Input validation: check for negative or zero values
        if (base <= 0 || height <= 0) {
            return -1;
        }
        
        return base * height;
    }
    
    /**
     * Calculates the samosa area in pixels from a BufferedImage.
     * This method integrates with ImageProcessor for actual samosa detection.
     * 
     * @param image The image to analyze
     * @return The number of samosa pixels found, or -1 if the image is null
     */
    public static int calculateSamosaArea(BufferedImage image) {
        if (image == null) {
            return -1;
        }
        
        try {
            // Use ImageProcessor to calculate samosa pixel area
            return ImageProcessor.calculateSamosaPixelArea(image);
        } catch (Exception e) {
            System.err.println("Error calculating samosa area: " + e.getMessage());
            return -1;
        }
    }
    
    /**
     * Calculates the percentage of samosa coverage in an image.
     * 
     * @param image The image to analyze
     * @return The percentage of samosa pixels (0.0 to 100.0), or -1 if calculation fails
     */
    public static double calculateSamosaCoveragePercentage(BufferedImage image) {
        if (image == null) {
            return -1;
        }
        
        try {
            int totalPixels = image.getWidth() * image.getHeight();
            int samosaPixels = calculateSamosaArea(image);
            
            if (samosaPixels == -1) {
                return -1;
            }
            
            return (double) samosaPixels / totalPixels * 100.0;
        } catch (Exception e) {
            System.err.println("Error calculating samosa coverage: " + e.getMessage());
            return -1;
        }
    }
    
    /**
     * Calculates the real-world area of samosa pixels using calibration data.
     * 
     * @param samosaPixels The number of samosa pixels
     * @param imageWidth The width of the image in pixels
     * @param imageHeight The height of the image in pixels
     * @param pixelsPerCm The calibration factor (pixels per centimeter)
     * @return The real area in square centimeters, or -1 if inputs are invalid
     */
    public static double calculateEstimatedPhysicalArea(int samosaPixels, int imageWidth, int imageHeight, double pixelsPerCm) {
        if (samosaPixels <= 0 || imageWidth <= 0 || imageHeight <= 0 || pixelsPerCm <= 0) {
            return -1;
        }
        
        try {
            // Calculate the area of one pixel in square centimeters
            double pixelAreaCm2 = 1.0 / (pixelsPerCm * pixelsPerCm);
            
            // Calculate the total area of samosa pixels in square centimeters
            double realAreaCm2 = samosaPixels * pixelAreaCm2;
            
            return realAreaCm2;
        } catch (Exception e) {
            System.err.println("Error calculating estimated physical area: " + e.getMessage());
            return -1;
        }
    }
    
    /**
     * Validates if a given area value is valid (not -1).
     * 
     * @param area The area value to validate
     * @return true if the area is valid (not -1), false otherwise
     */
    public static boolean isValidArea(double area) {
        return area != -1;
    }
    
    /**
     * Validates if a given area value is valid (not -1).
     * 
     * @param area The area value to validate
     * @return true if the area is valid (not -1), false otherwise
     */
    public static boolean isValidArea(int area) {
        return area != -1;
    }
    
    /**
     * Formats an area value for display, handling invalid values.
     * 
     * @param area The area value to format
     * @return A formatted string representation of the area, or "Invalid input" for -1
     */
    public static String formatArea(double area) {
        if (area == -1) {
            return "Invalid input";
        } else {
            return String.format("%.2f", area);
        }
    }
    
    /**
     * Formats an area value for display, handling invalid values.
     * 
     * @param area The area value to format
     * @return A formatted string representation of the area, or "Invalid input" for -1
     */
    public static String formatArea(int area) {
        if (area == -1) {
            return "Invalid input";
        } else {
            return String.format("%d", area);
        }
    }
    
    /**
     * Formats a percentage value for display.
     * 
     * @param percentage The percentage value to format
     * @return A formatted string representation of the percentage
     */
    public static String formatPercentage(double percentage) {
        if (percentage == -1) {
            return "Invalid input";
        } else {
            return String.format("%.1f%%", percentage);
        }
    }
    
    /**
     * Main method for testing the AreaCalculator functionality.
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println("AreaCalculator Test");
        System.out.println("===================");
        
        // Test triangle area calculations
        System.out.println("\nTriangle Area Tests:");
        System.out.println("Base: 5, Height: 3, Area: " + formatArea(calculateTriangleArea(5, 3)));
        System.out.println("Base: 10, Height: 8, Area: " + formatArea(calculateTriangleArea(10, 8)));
        System.out.println("Base: 0, Height: 5, Area: " + formatArea(calculateTriangleArea(0, 5)));
        System.out.println("Base: 5, Height: -3, Area: " + formatArea(calculateTriangleArea(5, -3)));
        System.out.println("Base: -5, Height: 3, Area: " + formatArea(calculateTriangleArea(-5, 3)));
        
        // Test rectangle area calculations
        System.out.println("\nRectangle Area Tests:");
        System.out.println("Length: 6, Width: 4, Area: " + formatArea(calculateRectangleArea(6, 4)));
        System.out.println("Length: 0, Width: 5, Area: " + formatArea(calculateRectangleArea(0, 5)));
        
        // Test circle area calculations
        System.out.println("\nCircle Area Tests:");
        System.out.println("Radius: 3, Area: " + formatArea(calculateCircleArea(3)));
        System.out.println("Radius: 0, Area: " + formatArea(calculateCircleArea(0)));
        System.out.println("Radius: -2, Area: " + formatArea(calculateCircleArea(-2)));
        
        // Test square area calculations
        System.out.println("\nSquare Area Tests:");
        System.out.println("Side: 4, Area: " + formatArea(calculateSquareArea(4)));
        System.out.println("Side: -3, Area: " + formatArea(calculateSquareArea(-3)));
        
        // Test parallelogram area calculations
        System.out.println("\nParallelogram Area Tests:");
        System.out.println("Base: 7, Height: 5, Area: " + formatArea(calculateParallelogramArea(7, 5)));
        System.out.println("Base: 0, Height: 6, Area: " + formatArea(calculateParallelogramArea(0, 6)));
        
        // Test validation method
        System.out.println("\nValidation Tests:");
        System.out.println("Is 7.5 valid? " + isValidArea(7.5));
        System.out.println("Is -1 valid? " + isValidArea(-1));
        System.out.println("Is 0 valid? " + isValidArea(0));
    }
} 