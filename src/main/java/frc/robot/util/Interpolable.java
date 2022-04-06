package frc.robot.util;

public interface Interpolable<T> {
    public T interpolate(T other, double x);
}