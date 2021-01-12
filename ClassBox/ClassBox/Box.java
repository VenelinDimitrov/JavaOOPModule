package ClassBox;

public class Box {
    private double length;
    private double width;
    private double height;

    public Box(double length, double width, double height) {
        this.setLength(length);
        this.setWidth(width);
        this.setHeight(height);
    }

    private void setLength(double length) {
        if (!sideIsValid(length)){
            throw new IllegalArgumentException("Length cannot be zero or negative.");
        }
        this.length = length;
    }

    private void setWidth(double width) {
        if (!sideIsValid(width)){
            throw new IllegalArgumentException("Width cannot be zero or negative.");
        }
        this.width = width;
    }
    private void setHeight(double height) {
        if (!sideIsValid(height)){
            throw new IllegalArgumentException("Height cannot be zero or negative.");
        }
        this.height = height;
    }

    private boolean sideIsValid(double size) {
        return !(size <= 0);
    }

    public double calculateSurfaceArea() {
        return 2 * ((this.length * this.width) + (this.length * this.height) + (this.width * this.height));
    }

    public double calculateLateralSurfaceArea() {
        return 2 * ((this.length * this.height) + (this.width * this.height));
    }

    public double calculateVolume() {
        return this.length * this.width * this.height;
    }
}
