package com.TAF.Drivers;

public enum Browser {
    CHROME {
        @Override
        public AbstractDriver getDriverFactory() {
            return new ChromeFactory();
        }
    },
    FIREFOX {
        @Override
        public AbstractDriver getDriverFactory() {
            return new FireFoxFactory();
        }
    },
    EDGE {
        @Override
        public AbstractDriver getDriverFactory() {
            return new EdgeFactory();
        }
    },
    SAFARI {
        @Override
        public AbstractDriver getDriverFactory() {
            return new SafariFactory();
        }
    };

    // Every browser must return its own factory
    public abstract AbstractDriver getDriverFactory();
}