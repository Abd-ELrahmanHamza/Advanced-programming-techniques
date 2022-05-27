package lab6;

import java.util.Arrays;
import java.util.List;

public class Doctor {
    public int ID;
    String name;

    @Override
    public String toString() {
        return "Doctor{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", timeSlots=" + Arrays.toString(timeSlots) +
                ", patients=" + Arrays.toString(patients) +
                '}';
    }

    boolean[] timeSlots;
    String[] patients;
}
