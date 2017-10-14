package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.Scanner;

/**
 * Created by KyleP on 10/8/2017.
 */

@TeleOp(name = "ColorSensorTest")

public class ColorSensorTest extends LinearOpMode {

    ColorSensor color_sensor;


    @Override
    public void runOpMode() throws InterruptedException {
        color_sensor.enableLed(true);  // Turn the LED on

        color_sensor = hardwareMap.colorSensor.get("color_sensor");

        waitForStart();

        while(opModeIsActive()) {
            isJewelRedFinal();
            waitOneFullHardwareCycle();
        }

    }

    public boolean isJewelRed (){
        telemetry.addData("blue value", color_sensor.blue());
        telemetry.addData("red value", color_sensor.red());


        if(color_sensor.red()>color_sensor.blue()){
            telemetry.addData("The color is ", "red");
            telemetry.update();
            return true;
        }
        else{
            telemetry.addData("The color is ", "blue");
            telemetry.update();
            return false;
        }
    }

    public boolean isJewelRedFinal() {
        int red = 0;
        int blue = 0;
        Boolean isRed = null;

        for (int i = 0; i < 20; i++) {
            if (isJewelRed()) {
                red++;
            } else if (!isJewelRed()) ;
            {
                blue++;
            }
            sleep(100);
        }

        if (red < blue) {
            isRed = false;
        } else if (blue < red) {
            isRed = true;
        }

        return isRed;

    }
}