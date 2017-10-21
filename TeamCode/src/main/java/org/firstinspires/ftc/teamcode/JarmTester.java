package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import com.vuforia.TextTracker;

import java.util.Scanner;

/**
 * Created by KyleP on 10/14/2017
 */

@TeleOp(name = "Jarm Tester")

public class JarmTester extends LinearOpMode {
    hMap robot = new hMap();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        robot.armServo        = hardwareMap.servo.get("armServo");
        robot.color_sensor    = hardwareMap.colorSensor.get("color_sensor");
        robot.color_sensor.enableLed(true);

        waitForStart();

        robot.armServo.setPosition(0.4);

        while (opModeIsActive()) {
            if(gamepad1.left_bumper) {
                robot.armServo.setPosition(0.2);
            } else if(gamepad1.right_bumper) {
                robot.armServo.setPosition(0.8);
            }

            if(gamepad1.a) {
                telemetry.addData("IsRed: ", isJewelRedFinal());
                telemetry.update();
            }
        }

    }

    public boolean isJewelRed (){
        telemetry.addData("blue value", robot.color_sensor.blue());
        telemetry.addData("red value", robot.color_sensor.red());
        telemetry.update();

        if(robot.color_sensor.red()>robot.color_sensor.blue()){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean isJewelRedFinal() {
        int red = 0;
        int blue = 0;
        boolean isRed = false;

        for (int i = 0; i < 20; i++) {
            if (isJewelRed()) {
                red++;
            } else {
                blue++;
            }
        }

        if (red < blue) {
            isRed = false;
        } else if (blue < red) {
            isRed = true;
        }

        return isRed;

    }
}