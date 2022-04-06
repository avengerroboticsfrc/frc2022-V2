package frc.robot.util;

public interface InverseInterpolable<T> {
    public double inverseInterpolate(T upper, T query);
}