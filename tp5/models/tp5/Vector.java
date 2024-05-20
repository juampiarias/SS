package tp5;

public class Vector {

    double x;
    double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }

    public Vector scalar(double scalar) {
        return new Vector(x * scalar, y * scalar);
    }

    public Vector add(Vector v) {
        return new Vector(this.x + v.x, this.y + v.y);
    }

    public Vector subtract(Vector v) {
        return new Vector(this.x - v.x, this.y - v.y);
    }

    public double dot(Vector v) {
        return this.x * v.x + this.y * v.y;
    }

    public double magnitude() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public Vector normalize() {
        double magnitude = magnitude();
        if (magnitude == 0) {
            throw new ArithmeticException("Cannot normalize a zero vector.");
        }
        return new Vector(this.x / magnitude, this.y / magnitude);
    }

    public Vector tangent() {
        return new Vector(-this.y, this.x);
    }


}
