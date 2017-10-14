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
    
@TeleOp(name = "Winch Tester")
public class WinchTest extends LinearOpMode {

    hMap robot = new hMap();

    @Override
    public void runOpMode() throws InterruptedException {


        waitForStart();

        while (opModeIsActive()) {

            Winch();

            //servo();

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


    void Winch(){
        robot.lWinch.setPower(scaleInput(gamepad2.right_stick_y));
        robot.rWinch.setPower(scaleInput(gamepad2.right_stick_y));
    }

    /*
    void servo() {
        if (!robot.bChop) {
            if (gamepad2.left_bumper) {
                robot.botServL.setPosition(0);
                robot.botServR.setPosition(1);
                robot.bChop = true;
                sleep(300);
            }
        } else {
            if (gamepad2.left_bumper) {
                robot.botServL.setPosition(robot.GRAB_CHOP_POS_A);
                robot.botServR.setPosition(robot.GRAB_CHOP_POS_B);
                robot.bChop = false;
                sleep(300);
            }
        }

        if (!robot.tChop) {
            if (gamepad2.right_bumper) {
                robot.topServL.setPosition(robot.GRAB_CHOP_POS_B);
                robot.topServR.setPosition(robot.GRAB_CHOP_POS_A);
                robot.tChop = true;
                sleep(300);
            }
        } else {
            if (gamepad2.right_bumper) {
                robot.topServL.setPosition(robot.GRAB_CHOP_POS_B);
                robot.topServL.setPosition(robot.GRAB_CHOP_POS_A);
                robot.tChop = false;
                sleep(300);
            }
        }

        telemetry.addData("BL", robot.botServL.getPosition());
        telemetry.addData("BR", robot.botServR.getPosition());
        telemetry.addData("TL", robot.topServL.getPosition());
        telemetry.addData("TR", robot.topServR.getPosition());
        telemetry.update();

    }
*/

}



