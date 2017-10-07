package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Manual with Arcade Drive iooio
 */

@TeleOp(name = "Arcade")
public class Arcade extends LinearOpMode {

    private DcMotor frontLeft;
    private DcMotor backLeft;
    private DcMotor frontRight;
    private DcMotor backRight;
    //private Servo botServL, botServR, topServL, topServR;
    boolean extendB, extendT;
    double START_POSA = 0.0;
    double START_POSB = 1;
    double GRAB_POSA = 0.4;
    double GRAB_POSB = 0.6;

    //for the winch
    //public DcMotor lWinch;
    //public DcMotor rWinch;


    @Override
    public void runOpMode() throws InterruptedException {
        //botServL = hardwareMap.servo.get("botServL");
        //botServR = hardwareMap.servo.get("botServR");
        //topServL = hardwareMap.servo.get("topServL");
        //topServR = hardwareMap.servo.get("topServR");

        //botServL.setPosition(START_POSA);
        //botServR.setPosition(START_POSB);
        //topServL.setPosition(START_POSA);
        //topServR.setPosition(START_POSB);
        //extendB = false;
        //extendT = false;

        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        backRight = hardwareMap.dcMotor.get("backRight");

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //for winch
        //rWinch = hardwareMap.dcMotor.get("rWinch");
        //lWinch = hardwareMap.dcMotor.get("lWinch");

        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);

        double power = 0;
        double strafe = 0;
        double turn = 0;
        double FL, FR, BL, BR;

        waitForStart();

        while (opModeIsActive()) {

            //Winch();

            /*if(gamepad1.left_bumper) {
                if (gamepad1.left_stick_y > 0) {
                    power = 0.2;
                } else if (gamepad1.left_stick_y < 0) {
                    power = -0.2;
                } else {
                    power = 0;
                }
                if(gamepad1.right_stick_x > 0) {
                    strafe = 0.2;
                } else if (gamepad1.right_stick_x < 0) {
                    strafe = -0.2;
                } else {
                    strafe = 0;
                }
                if(gamepad1.left_stick_x > 0) {
                    turn = 0.4;
                } else if(gamepad1.left_stick_x < 0) {
                    turn = -0.4;
                } else {
                    turn = 0;
                }
            } else {*/
            power = scaleInput(Range.clip(-gamepad1.left_stick_y, -1, 1));
            strafe = scaleInput(Range.clip(-gamepad1.right_stick_x, -1, 1));
            turn = scaleInput(Range.clip(-gamepad1.left_stick_x, -1, 1));
            //}

            FL = power - turn + strafe;
            BL = power - turn - strafe;
            FR = power + turn - strafe;
            BR = power + turn + strafe;

            //servo();

            frontLeft.setPower(FL);
            backLeft.setPower(BL);
            frontRight.setPower(FR);
            backRight.setPower(BR);


            telemetry.addData("Slow", gamepad1.left_bumper);
            telemetry.addData("Motors", "FL (%.2f), FR (%.2f), BL (%.2f), BR (%.2f)", FL, FR, BL, BR);
            telemetry.update();
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

    //void Winch(){
    //    lWinch.setPower(scaleInput(gamepad2.right_stick_y));
    //    rWinch.setPower(scaleInput(gamepad2.right_stick_y));
    //}
    /*void servo() {
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
                topServL.setPosition(START_POSB);
                topServR.setPosition(START_POSA);
                extendT = true;
                sleep(300);
            }
        } else {
            if (gamepad1.right_bumper) {
                topServL.setPosition(GRAB_POSB);
                topServR.setPosition(GRAB_POSA);
                extendT = false;
                sleep(300);
            }
        }
        telemetry.addData("BL", botServL.getPosition());
        telemetry.addData("BR", botServR.getPosition());
        telemetry.addData("TL", topServL.getPosition());
        telemetry.addData("TR", topServR.getPosition());
        telemetry.update();
    }*/
}


