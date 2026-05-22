package statisticker;

import java.util.List;

public class Statistics 
{
    public interface Temperature {
        float toFahrenheit();
    }

    public static class Fahrenheit implements Temperature {
        private final float value;

        public Fahrenheit(float value) {
            this.value = value;
        }

        @Override
        public float toFahrenheit() {
            return value;
        }
    }

    public static class Celsius implements Temperature {
        private final float value;

        public Celsius(float value) {
            this.value = value;
        }

        @Override
        public float toFahrenheit() {
            return value * 9.0f / 5.0f + 32.0f;
        }
    }

    public static class Stats {
        public final float average;
        public final float min;
        public final float max;

        public Stats(float average, float min, float max) {
            this.average = average;
            this.min = min;
            this.max = max;
        }
    }

    public static Stats getStatistics(List<Temperature> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return new Stats(Float.NaN, Float.NaN, Float.NaN);
        }

        float sum = 0.0f;
        float min = Float.MAX_VALUE;
        float max = -Float.MAX_VALUE;

        for (Temperature temperature : numbers) {
            if (temperature == null) {
                return new Stats(Float.NaN, Float.NaN, Float.NaN);
            }

            float number = temperature.toFahrenheit();
            if (Float.isNaN(number) || Float.isInfinite(number)) {
                return new Stats(Float.NaN, Float.NaN, Float.NaN);
            }

            sum += number;
            if (number < min) {
                min = number;
            }
            if (number > max) {
                max = number;
            }
        }

        return new Stats(sum / numbers.size(), min, max);
    }
}
