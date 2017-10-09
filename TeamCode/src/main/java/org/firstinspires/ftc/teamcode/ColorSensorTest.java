package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by KyleP on 10/8/2017.
 */

@TeleOp(name = "ColorSensorTest")

public class ColorSensorTest extends LinearOpMode {

    ColorSensor color_sensor;

    @Override
    public void runOpMode() throws InterruptedException {
        color_sensor = hardwareMap.colorSensor.get("color_sensor");
        isJewelRed();
    }

    public boolean isJewelRed (){
        telemetry.addData("blue value", color_sensor.blue());
        telemetry.addData("red value", color_sensor.red());


        if(color_sensor.red()>color_sensor.blue()+5){
            return true;
        }
        else{
            return false;
        }
    }

}
