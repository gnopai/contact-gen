package com.gnopai.contactgen.generator.random;

import lombok.Value;

@Value
public class Odds {
    private int x;
    private int y;

    private Odds(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static OddsBuilder of(int x) {
        return new OddsBuilder(x);
    }

    public static class OddsBuilder {
        private final int x;

        private OddsBuilder(int x) {
            this.x = x;
        }

        public Odds in(int y) {
            return new Odds(x, y);
        }
    }
}
