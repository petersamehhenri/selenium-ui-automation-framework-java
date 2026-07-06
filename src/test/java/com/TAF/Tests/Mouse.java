package com.TAF.Tests;

import org.testng.annotations.Test;

import java.awt.*;

public class Mouse {

    @Test
    public static void main(String[] args) throws AWTException, InterruptedException {

        Robot robot = new Robot();
        for (int j = 0; j < 5000; j++) {
            for (int i = 0; i < 5000; i++) {
                robot.mouseMove(i, 300);
                Thread.sleep(4000);
            }
        }
        // Move mouse to position (x=500, y=300)

    }
}
