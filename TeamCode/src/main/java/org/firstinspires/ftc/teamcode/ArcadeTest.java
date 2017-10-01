package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

/**
 * Manual with Arcade Drive iooio
 */

@TeleOp (name = "Arcade")
public class ArcadeTest extends LinearOpMode {

    private DcMotor frontLeft;
    private DcMotor backLeft;
    private DcMotor frontRight;
    private DcMotor backRight;

    @Override
    public void runOpMode() throws InterruptedException {

        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        backRight = hardwareMap.dcMotor.get("backRight");

        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);

        double power = 0;
        double strafe = 0;
        double turn = 0;
        double FL, FR, BL, BR;

        waitForStart();

        while(opModeIsActive()) {

            if(gamepad1.left_bumper) {
                if (gamepad1.left_stick_y > 0) {
                    power = 0.2;
                } else if (gamepad1.left_stick_y < 0) {
                    power = -0.2;
                } else {
                    power = 0;
                }
                if(gamepad1.left_stick_x > 0) {
                    strafe = 0.2;
                } else if (gamepad1.left_stick_x < 0) {
                    strafe = -0.2;
                } else {
                    strafe = 0;
                }
                if(gamepad1.right_stick_x > 0) {
                    turn = 0.4;
                } else if(gamepad1.right_stick_x < 0) {
                    turn = -0.4;
                } else {
                    turn = 0;
                }
            } else {
                power = Range.clip(-gamepad1.left_stick_y , -1, 1);
                strafe = Range.clip(-gamepad1.left_stick_x , -1, 1);
                turn = Range.clip(-gamepad1.right_stick_x, -1, 1);
            }

            FL = scaleInput(power + turn - strafe);
            BL = scaleInput(power + turn + strafe);
            FR = scaleInput(power - turn + strafe);
            BR = scaleInput(power - turn - strafe);

            frontLeft.setPower(FL);
            backLeft.setPower(BL);
            frontRight.setPower(FR);
            backRight.setPower(BR);

            telemetry.addData("Slow", gamepad1.left_bumper);
            telemetry.addData("Motors", "FL (%.2f), FR (%.2f), BL (%.2f), BR (%.2f)", FL, FR, BL, BR);
            telemetry.update();
        }
    }

    double scaleInput(double dVal)  {
        double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

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
}