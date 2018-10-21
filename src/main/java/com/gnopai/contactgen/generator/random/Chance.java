package com.gnopai.contactgen.generator.random;

import lombok.Value;

@Value
public class Chance {
    private int x;
    private int y;

    private Chance(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static ChanceBuilder of(int x) {
        return new ChanceBuilder(x);
    }

    public static class ChanceBuilder {
        private final int x;

        private ChanceBuilder(int x) {
            this.x = x;
        }

        public Chance in(int y) {
            return new Chance(x, y);
        }
    }
}
