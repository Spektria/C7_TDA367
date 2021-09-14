package C7.Vector;

import java.util.Objects;

/**
 * Vector2D is a class representing a mathematical 2d vector.
 */
public class Vector2D {

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
     * Calculates the length of this vector.
     * @return the length of this vector.
     */
    public double len(){
        return Math.sqrt(
                this.x * this.x + this.y * this.y
        );
    }

    /**
     * Returns the normalized version of this vector.
     * @return the normalized version of this vector
     */
    public Vector2D normalized(){
        double len = len();
        return new Vector2D(this.x / len, this.y / len);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2D vector2D = (Vector2D) o;
        return Double.compare(vector2D.x, x) == 0 && Double.compare(vector2D.y, y) == 0;
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
