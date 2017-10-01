package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by oliversun on 9/30/17.
 */

@TeleOp(name = "Servo Test 2")
public class ServoTest2 extends LinearOpMode {
    private Servo botServL = null;
    private Servo botServR = null;
    private Servo topServL = null;
    private Servo topServR = null;
    boolean extendB, extendT;
    double START_POS = 0.1;
    double GRAB_POS = 0.4;

    @Override
    public void runOpMode() throws InterruptedException {
        botServL = hardwareMap.servo.get("botServL");
        botServR = hardwareMap.servo.get("botServR");

        botServL.setPosition(START_POS);
        botServR.setPosition(START_POS);
        topServL.setPosition(START_POS);
        topServR.setPosition(START_POS);
        extendB = false;
        extendT = false;

        waitForStart();

        while (opModeIsActive()) {
            if (!extendB) {
                if (gamepad1.left_bumper) {
                    botServL.setPosition(START_POS);
                    botServR.setPosition(START_POS);
                    extendB = true;
                    sleep(300);
                }
            } else {
                if (gamepad1.left_bumper) {
                    botServL.setPosition(GRAB_POS);
                    botServR.setPosition(GRAB_POS);
                    extendB = false;
                    sleep(300);
                }
            }

            if (!extendT) {
                if (gamepad1.right_bumper) {
                    topServL.setPosition(START_POS);
                    topServR.setPosition(START_POS);
                    extendT = true;
                    sleep(300);
                }
            } else {
                if (gamepad1.right_bumper) {
                    topServL.setPosition(GRAB_POS);
                    topServR.setPosition(GRAB_POS);
                    extendT = false;
                    sleep(300);
                }
            }
        }
    }
}
