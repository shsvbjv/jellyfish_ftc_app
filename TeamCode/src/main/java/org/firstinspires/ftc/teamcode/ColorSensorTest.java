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
        color_sensor.enableLed(true);  // Turn the LED on

        color_sensor = hardwareMap.colorSensor.get("color_sensor");
        isJewelRedFinal();
    }

    public boolean isJewelRed (){
        telemetry.addData("blue value", color_sensor.blue());
        telemetry.addData("red value", color_sensor.red());


        if(color_sensor.red()>color_sensor.blue()){
            telemetry.addData("The color is ", "red");
            return true;
        }
        else{
            telemetry.addData("The color is ", "blue");
            return false;
        }
    }

    public boolean isJewelRedFinal(){
        int count=0;
        double bar=0.75;
        int redcount=0;
        double redpercent;
        int bluecount=0;
        double bluepercent=0;

        while(bar<0.75 && count<20){
            count+=1;
            if(isJewelRed()==true){
                redcount+=1;
                redpercent=redcount/count;
                bar=redpercent;
            }
            else{
                bluecount+=1;
                bluepercent=bluecount/count;
                bar=bluepercent;
            }

        }
        if(redcount>bluecount){
            return true;
        }
        else{
            return false;
        }
    }

}
