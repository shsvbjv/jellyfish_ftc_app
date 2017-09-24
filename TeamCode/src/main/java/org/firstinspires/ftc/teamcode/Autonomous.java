package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by FerannoDad on 9/23/17. 123
 */

public class Autonomous extends LinearOpMode {
    private DcMotor frontLeft;
    private DcMotor backLeft;
    private DcMotor frontRight;
    private DcMotor backRight;


    @Override public void runOpMode() throws InterruptedException {
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        backRight = hardwareMap.dcMotor.get("backRight");

        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);


        //start Autonomous
        waitForStart();
        while (opModeIsActive()) {


        }
    }
         //Useful functions
         public void DriveForward(double power){
             frontLeft.setPower(-power);
             frontRight.setPower(-power);
             backLeft.setPower(-power);
             backRight.setPower(-power);

    }
         public void TurnLeft(double power){
             frontLeft.setPower(-power);
             frontRight.setPower(power);
             backLeft.setPower(-power);
             backRight.setPower(power);

    }
        public void TurnLeftTime(double power, long time) throws InterruptedException{
            TurnLeft(power);
            Thread.sleep(time);
    }
        public void TurnRightTime(double power, long time) throws InterruptedException{
            TurnLeft(-power);
            Thread.sleep(time);
    }
        public void DriveForwardTime(double power, long time) throws InterruptedException{
            DriveForward(power);
            Thread.sleep(time);
    }

        public void DriveBackwardTime(double power, long time) throws InterruptedException{
            DriveForward(-power);
            Thread.sleep(time);
    }


}
