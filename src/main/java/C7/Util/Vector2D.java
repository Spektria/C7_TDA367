package C7.Util;

import java.util.Objects;

/**
 * Vector2D is a class representing an immutable mathematical 2d vector.
 * @author Hugo Ekstrand
 */
public final class Vector2D {

    public static final Vector2D ZERO = new Vector2D(0,0);

    private final double x, y;

    /**
     * Creates a 2d vector given its x and y values.
     * @param x the x value
     * @param y the y value
     */
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Rotates this vector around a given point, represented as a vector2d, with a given
     * amount.
     * @param other the point to be rotated around
     * @param deg the amount to rotate in radians
     * @return the rotated vector
     */
    public Vector2D rotatedAround(Vector2D other, double deg){
        final double cos = Math.cos(deg);
        final double sin = Math.sin(deg);

        Vector2D translated = this.sub(other);

        return new Vector2D(
                translated.x * cos - translated.y * sin,
                translated.x  * sin + translated.y  * cos
        ).add(other);
    }

    /**
     * Returns the x value of this vector.
     * @return the x value
     */
    public double getX(){
        return x;
    }

    /**
     * Returns the y value of this vector.
     * @return the y value
     */
    public double getY(){
        return y;
    }

    /**
     * Performs addition on this vector with another and returns the sum.
     * @param other the other vector to be added with this vector
     * @return the resulting sum of the two given vectors
     */
    public Vector2D add(Vector2D other){
        return new Vector2D(this.x + other.x, this.y + other.y);
    }

    /**
     * Performs subtraction on this vector with another vector and returns the difference.
     * @param other the vector this vector should be subtracted with
     * @return the resulting difference of the two given vectors
     */
    public Vector2D sub(Vector2D other){
        return new Vector2D(this.x - other.x, this.y - other.y);
    }

    /**
     * Performs scalar multiplication on this vector given a scalar.
     * @param scalar the given scalar
     * @return the resulting scaled vector
     */
    public Vector2D mult(double scalar){
        return new Vector2D(this.x * scalar, this.y * scalar);
    }

    /**
     * Performs the dot product of this and a given vector.
     * @param other the other, given vector
     * @return the dot product of the two vectors
     */
    public double dot(Vector2D other){
        return this.x * other.x + this.y * other.y;
    }

    /**
     * Calculates the angle between this vector and another given vector.
     * Note that the angle is in radians in the interval [0, pi]
     * and neither this or the other vector may be 0.
     * @param other the other, given vector
     * @throws IllegalArgumentException if this.len() or other.len() == 0.
     * @return the angle between the two given vectors in radians.
     */
    public double angleBetween(Vector2D other){
        return Math.abs(angleBetweenWithSign(other));
    }

    /**
     * Calculates the angle between this vector and another given vector.
     * Note that the angle is in radians in the interval [-pi, pi]
     * and neither this or the other vector may be 0.
     * @param other the other, given vector
     * @throws IllegalArgumentException if this.len() or other.len() == 0.
     * @return the angle between the two given vectors in radians.
     */
    public double angleBetweenWithSign(Vector2D other){
        // Formula:
        // https://math.stackexchange.com/questions/2510897/calculate-the-angle-between-two-vectors-when-direction-is-important
        if(this.len() == 0 || other.len() == 0)
            throw new IllegalArgumentException();
        return Math.atan((this.getX() * other.getY() - this.getY() * other.getX())/(this.getX() * other.getX() + this.getY() * other.getY()));
    }

    /**
     * Calculates the length of this vector.
     * @return the length of this vector.
     */
    public double len(){
        return Math.sqrt(
                this.x * this.x + this.y * this.y
        );
    }

    /**
     * Scales this vector with a given scalar in both the x- and y-axis.
     * @param scaling the vector representing the x and y scalar.
     * @return the scaled vector.
     */
    public Vector2D scale(Vector2D scaling){
        return new Vector2D(this.x * scaling.x, this.y * scaling.y);
    }

    /**
     * Returns the normalized version of this vector.
     * @return the normalized version of this vector
     */
    public Vector2D normalized(){
        double len = len();
        return new Vector2D(this.x / len, this.y / len);
    }

    private boolean doubleEquals(double a, double b){
        final double equalsPrecision = 1d/1e6;
        double diff = Math.abs(a - b);
        return diff <= equalsPrecision;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2D vector2D = (Vector2D) o;
        return doubleEquals(vector2D.x, x) && doubleEquals(vector2D.y, y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Vector2D{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}