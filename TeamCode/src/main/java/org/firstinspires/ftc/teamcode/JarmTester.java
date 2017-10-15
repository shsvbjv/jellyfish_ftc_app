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
 * Created by KyleP on 10/14/2017.
 */

@Autonomous(name = "Jarm Tester")

public class JarmTester extends LinearOpMode {

    public Servo armServo;

    @Override
    public void runOpMode() throws InterruptedException {
        armServo        = hardwareMap.servo.get("armServo");

        armServo.setPosition(0.4);

        waitForStart();

        while (opModeIsActive()) {
            armServo.setPosition(0.4);
            sleep(2000);
            armServo.setPosition(-0.2);
            sleep(2000);
        }

    }
}