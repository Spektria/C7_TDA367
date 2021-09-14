package C7.Vector;

import java.util.Objects;

public class Vector2D {

    private final double x, y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D rotatedAround(Vector2D other, double deg){
        final double cos = Math.cos(deg);
        final double sin = Math.sin(deg);

        Vector2D translated = this.sub(other);

        return new Vector2D(
                translated.x * cos - translated.y * sin,
                translated.x  * sin + translated.y  * cos
        ).add(other);
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public Vector2D add(Vector2D other){
        return new Vector2D(this.x + other.x, this.y + other.y);
    }

    public Vector2D sub(Vector2D other){
        return new Vector2D(this.x - other.x, this.y - other.y);
    }

    public Vector2D mult(double scalar){
        return new Vector2D(this.x * scalar, this.y * scalar);
    }

    double len(){
        return Math.sqrt(
                this.x * this.x + this.y * this.y
        );
    }

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
}
