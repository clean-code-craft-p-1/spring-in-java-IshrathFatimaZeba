package statisticker;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import org.junit.Test;

public class StatisticsTest 
{
    @Test
    public void reportsAverageMinMaxx()
    {
        Statistics.Temperature[] temperatures = {
            new Statistics.Fahrenheit(98.6f),
            new Statistics.Fahrenheit(98.2f),
            new Statistics.Fahrenheit(97.8f),
            new Statistics.Fahrenheit(102.2f)
        };
        List<Statistics.Temperature> numberList = Arrays.asList(temperatures);

        Statistics.Stats s = Statistics.getStatistics(numberList);

        float epsilon = 0.001f;
        assertEquals(s.average, 99.2f, epsilon);
        assertEquals(s.min, 97.8f, epsilon);
        assertEquals(s.max, 102.2f, epsilon);
    }
    @Test
    public void reportsNaNForEmptyInput()
    {
        List<Statistics.Temperature> emptyList = new ArrayList<Statistics.Temperature>();

        Statistics.Stats s = Statistics.getStatistics(emptyList);

        // All fields of computedStats (average, max, min) must be
        // Float.NaN (not-a-number), as described in
        // https://www.geeksforgeeks.org/nan-not-number-java/
        // Specify the asserts here and implement accordingly.
        assertTrue(Float.isNaN(s.average));
        assertTrue(Float.isNaN(s.min));
        assertTrue(Float.isNaN(s.max));
    }

    @Test
    public void convertsCelsiusBeforeComputingStatistics()
    {
        List<Statistics.Temperature> temperatures = Arrays.asList(
            new Statistics.Celsius(0.0f),
            new Statistics.Celsius(100.0f)
        );

        Statistics.Stats s = Statistics.getStatistics(temperatures);

        float epsilon = 0.001f;
        assertEquals(s.average, 122.0f, epsilon);
        assertEquals(s.min, 32.0f, epsilon);
        assertEquals(s.max, 212.0f, epsilon);
    }

    @Test
    public void reportsNaNWhenIoTDeviceSendsNullReading()
    {
        List<Statistics.Temperature> temperatures = Arrays.asList(
            new Statistics.Fahrenheit(98.6f),
            null,
            new Statistics.Fahrenheit(99.1f)
        );

        Statistics.Stats s = Statistics.getStatistics(temperatures);

        assertTrue(Float.isNaN(s.average));
        assertTrue(Float.isNaN(s.min));
        assertTrue(Float.isNaN(s.max));
    }

    @Test
    public void reportsNaNWhenIoTDeviceSendsCorruptedReading()
    {
        List<Statistics.Temperature> temperatures = Arrays.asList(
            new Statistics.Temperature() {
                @Override
                public float toFahrenheit() {
                    return Float.NaN;
                }
            },
            new Statistics.Fahrenheit(99.1f)
        );

        Statistics.Stats s = Statistics.getStatistics(temperatures);

        assertTrue(Float.isNaN(s.average));
        assertTrue(Float.isNaN(s.min));
        assertTrue(Float.isNaN(s.max));
    }

    @Test
    public void reportsNaNWhenIoTDeviceSendsInfiniteReading()
    {
        List<Statistics.Temperature> temperatures = Arrays.asList(
            new Statistics.Temperature() {
                @Override
                public float toFahrenheit() {
                    return Float.POSITIVE_INFINITY;
                }
            },
            new Statistics.Fahrenheit(99.1f)
        );

        Statistics.Stats s = Statistics.getStatistics(temperatures);

        assertTrue(Float.isNaN(s.average));
        assertTrue(Float.isNaN(s.min));
        assertTrue(Float.isNaN(s.max));
    }
}
