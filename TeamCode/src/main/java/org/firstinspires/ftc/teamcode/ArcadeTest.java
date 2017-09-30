package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

/**
 * Manual with Arcade Drive iooio
 */

@TeleOp (name = "Arcade Test")
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

        double power;
        double strafe;
        double turn;
        double FL, FR, BL, BR;

        waitForStart();

        while(opModeIsActive()) {

            if(gamepad1.left_bumper) {
                power = (-gamepad1.left_stick_y / -gamepad1.left_stick_y) * 0.4;
                strafe = (-gamepad1.left_stick_x / -gamepad1.left_stick_x) * 0.4;
                turn = (-gamepad1.right_stick_x / -gamepad1.right_stick_x) * 0.4;
            } else {
                power = Range.clip(-gamepad1.left_stick_y, -1, 1);
                strafe = Range.clip(-gamepad1.left_stick_x, -1, 1);
                turn = Range.clip(-gamepad1.right_stick_x, -1, 1);
            }

            FL = power + turn - strafe;
            BL = power + turn + strafe;
            FR = power - turn + strafe;
            BR = power - turn - strafe;

            frontLeft.setPower(FL);
            backLeft.setPower(BL);
            frontRight.setPower(FR);
            backRight.setPower(BR);

            telemetry.addData("Slow", gamepad1.left_bumper);
            telemetry.addData("Motors", "FL (%.2f), FR (%.2f), BL (%.2f), BR (&.2f)", FL, FR, BL, BR);
            telemetry.update();
        }
    }
}