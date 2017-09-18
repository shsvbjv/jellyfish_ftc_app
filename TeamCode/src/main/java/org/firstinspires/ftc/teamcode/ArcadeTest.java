package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Manual with Arcade Drive
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

        waitForStart();

        while(opModeIsActive()) {

            double power = -gamepad1.left_stick_y;
            double strafe = -gamepad1.left_stick_x;
            double turn = -gamepad1.right_stick_y;

            frontLeft.setPower(power + turn + strafe);
            backLeft.setPower(power + turn - strafe);
            frontRight.setPower(power - turn - strafe);
            backRight.setPower(power - turn + strafe);
        }
    }
}
