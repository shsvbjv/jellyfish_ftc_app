package org.firstinspires.ftc.teamcode;

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

@TeleOp(name = "ColorSensorTest")

public class JarmTester extends LinearOpMode {
    hMap robot = new hMap();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        robot.armServo.setPosition(robot.UP_JARM_POS);

        waitForStart();

        while (opModeIsActive()) {
            robot.armServo.setPosition(robot.DOWN_JARM_POS);
            sleep(500);
            robot.armServo.setPosition(robot.UP_JARM_POS);
            sleep(500);
        }

    }
}