package me.thefox.iivirtual;

public enum IIInfo
{
    Arm,
    Leg;

    public static IIInfo lookup(String id) {
        try {
            return IIInfo.valueOf(id);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
