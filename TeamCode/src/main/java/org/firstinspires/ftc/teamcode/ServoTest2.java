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
    private Servo botServL, botServR, topServL, topServR;
    boolean extendB, extendT;
    double START_POSA = 0.0;
    double START_POSB = 1;
    double GRAB_POSA = 0.4;
    double GRAB_POSB = 0.6;



    @Override
    public void runOpMode() throws InterruptedException {
        botServL = hardwareMap.servo.get("botServL");
        botServR = hardwareMap.servo.get("botServR");
        topServL = hardwareMap.servo.get("topServL");
        topServR = hardwareMap.servo.get("topServR");

        botServL.setPosition(START_POSA);
        botServR.setPosition(START_POSB);
        topServL.setPosition(START_POSB - 1);
        topServR.setPosition(START_POSA + 1);
        extendB = false;
        extendT = false;

        waitForStart();

        while (opModeIsActive()) {
            if (!extendB) {
                if (gamepad1.left_bumper) {
                    botServL.setPosition(0);
                    botServR.setPosition(1);
                    extendB = true;
                    sleep(300);
                }
            } else {
                if (gamepad1.left_bumper) {
                    botServL.setPosition(GRAB_POSA);
                    botServR.setPosition(GRAB_POSB);
                    extendB = false;
                    sleep(300);
                }
            }

            if (!extendT) {
                if (gamepad1.right_bumper) {
                    topServL.setPosition(START_POSB - 0.1);
                    topServR.setPosition(START_POSA + 0.1);
                    extendT = true;
                    sleep(300);
                }
            } else {
                if (gamepad1.right_bumper) {
                    topServL.setPosition(GRAB_POSB - 0.1);
                    topServR.setPosition(GRAB_POSA + 0.1);
                    extendT = false;
                    sleep(300);
                }
            }
            telemetry.addData("BL", botServL.getPosition());
            telemetry.addData("BR", botServR.getPosition());
            telemetry.addData("TL", topServL.getPosition());
            telemetry.addData("TR", topServR.getPosition());
            telemetry.update();
        }
    }
}
