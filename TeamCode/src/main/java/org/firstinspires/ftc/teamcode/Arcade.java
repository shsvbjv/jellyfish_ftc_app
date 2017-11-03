package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Manual with Arcade Drive
 */

@TeleOp(name = "Arcade")
public class Arcade extends LinearOpMode {
    boolean winchbutton = false;
    boolean topServo = false;
    boolean botServo = false;
    boolean topHalf = false;
    boolean botHalf = false;
    int lmotorpos;
    int rmotorpos;
    double winchpow;

    hMap robot = new hMap();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        robot.color_sensor.enableLed(false);
        robot.armServo.setPosition(robot.UP_JARM_POS);

        double power = 0;
        double strafe = 0;
        double turn = 0;
        double FL, FR, BL, BR;

        robot.botServL.setPosition(robot.START_CHOP_POS_A);
        robot.botServR.setPosition(robot.START_CHOP_POS_B + 0.1);
        robot.topServL.setPosition(robot.START_CHOP_POS_B - 0.1);
        robot.topServR.setPosition(robot.START_CHOP_POS_A - 0.1);
        robot.tChop = false;
        robot.bChop = false;
        robot.tHalf = false;
        robot.bHalf = false;

        waitForStart();

        while (opModeIsActive()) {

            Winch();
            servo();

            power = scaleInput(Range.clip(-gamepad1.right_stick_y, -1, 1));
            strafe = scaleInput(Range.clip(-gamepad1.right_stick_x, -1, 1));
            turn = scaleInput(Range.clip(-gamepad1.left_stick_x, -1, 1));

            FL = power - turn - strafe;
            BL = power - turn + strafe;
            FR = power + turn + strafe;
            BR = power + turn - strafe;

            if (gamepad1.right_bumper) {
                FL /= 3;
                BL /= 3;
                FR /= 3;
                BR /= 3;
            } else if(gamepad1.dpad_left) {
                FL = -0.25;
                BL = -0.25;
                FR = 0.25;
                BR = 0.25;
            } else if(gamepad1.dpad_right) {
                FL = 0.25;
                BL = 0.25;
                FR = -0.25;
                BR = -0.25;
            }

            robot.frontLeft.setPower(FL);
            robot.backLeft.setPower(BL);
            robot.frontRight.setPower(FR);
            robot.backRight.setPower(BR);

            telemetry.addData("Motors", "FL (%.2f), FR (%.2f), BL (%.2f), BR (%.2f)", FL, FR, BL, BR);
            telemetry.addData("Servos", "TL (%.2f), TR (%.2f), BL (%.2f), BR (%.2f)", robot.topServL.getPosition(), robot.topServR.getPosition(), robot.botServL.getPosition(), robot.botServR.getPosition());
            telemetry.addData("lmotorpos", lmotorpos);
            telemetry.addData("lWinch", robot.lWinch.getCurrentPosition());
            telemetry.addData("rmotorpos", rmotorpos);
            telemetry.addData("rWinch", robot.rWinch.getCurrentPosition());
            telemetry.addData("Slow", gamepad1.left_bumper);
            telemetry.update();

            robot.rWinch.setTargetPosition(robot.rWinch.getCurrentPosition());
            robot.lWinch.setTargetPosition(robot.lWinch.getCurrentPosition());
        }
    }

    double scaleInput(double dVal) {
        double[] scaleArray = {0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00};

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);
        if (index < 0) {
            index = -index;
        } else if (index > 16) {
            index = 16;
        }

        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        return dScale;
    }


    void Winch() {

        winchpow = scaleInput(gamepad2.right_stick_y);

        if (gamepad2.left_bumper) {
            winchpow = -0.05;
        } else if (gamepad2.right_bumper) {
            winchpow /= 2;
        }

        robot.lWinch.setPower(winchpow);
        robot.rWinch.setPower(winchpow);
    }

    void servo() {
        if(!robot.bHalf) {
            if (!botHalf && gamepad2.dpad_down) {
                robot.botServL.setPosition(0.3);
                robot.botServR.setPosition(0.8);
                robot.bChop = false;
                robot.bHalf = true;
            }
        } else {
            if (!botHalf && gamepad2.dpad_down) {
                robot.botServL.setPosition(robot.START_CHOP_POS_A);
                robot.botServR.setPosition(robot.START_CHOP_POS_B + 0.1);
                robot.bChop = false;
                robot.bHalf = false;
            }
        }

        if (!robot.bChop) {
            if (!botServo && gamepad2.a) {
                robot.botServL.setPosition(robot.GRAB_CHOP_POS_A + 0.1);
                robot.botServR.setPosition(robot.GRAB_CHOP_POS_B);
                robot.bChop = true;
                robot.bHalf = false;
            }
        } else {
            if (!botServo && gamepad2.a) {
                robot.botServL.setPosition(robot.START_CHOP_POS_A);
                robot.botServR.setPosition(robot.START_CHOP_POS_B + 0.1);
                robot.bChop = false;
                robot.bHalf = false;
            }
        }

        if(!robot.tHalf) {
            if (!topHalf && gamepad2.dpad_up) {
                robot.topServL.setPosition(0.6);
                robot.topServR.setPosition(0.2);
                robot.tChop = false;
                robot.tHalf = true;
            }
        } else {
            if (!topHalf && gamepad2.dpad_up) {
                robot.topServL.setPosition(robot.START_CHOP_POS_B - 0.1);
                robot.topServR.setPosition(robot.START_CHOP_POS_A - 0.1);
                robot.tChop = false;
                robot.tHalf = false;
            }
        }

        if (!robot.tChop) {
            if (!topServo && gamepad2.y) {
                robot.topServL.setPosition(robot.GRAB_CHOP_POS_B - 0.2);
                robot.topServR.setPosition(robot.GRAB_CHOP_POS_A);
                robot.tChop = true;
                robot.tHalf = false;
            }
        } else {
            if (!topServo && gamepad2.y) {
                robot.topServL.setPosition(robot.START_CHOP_POS_B - 0.1);
                robot.topServR.setPosition(robot.START_CHOP_POS_A - 0.1);
                robot.tChop = false;
                robot.tHalf = false;
            }
        }

        botServo = gamepad2.a;
        topServo = gamepad2.y;
        topHalf = gamepad2.dpad_up;
        botHalf = gamepad2.dpad_down;
    }
}


