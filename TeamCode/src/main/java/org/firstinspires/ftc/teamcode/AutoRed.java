package org.firstinspires.ftc.teamcode;

import java.util.*;
import java.util.Vector;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuMarkInstanceId;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by Feranno on 9/23/17. 123
 */

@Autonomous(name = "AutoRed")
public class AutoRed extends LinearOpMode {

    //GyroSensor sensorGyro;
    //ModernRoboticsI2cGyro mrGryo;

    VuforiaLocalizer vuforia;

    //ColorSensor color_sensor;

    hMap robot = new hMap();

    int rev = 1120;
    boolean forward;


    @Override
    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap);


        //color sensor
        //color_sensor = hardwareMap.colorSensor.get("color");

        //initialize gryo

        //sensorGyro = hardwareMap.gyroSensor.get("gryo");
        //mrGryo = (ModernRoboticsI2cGyro) sensorGyro;
        //int Accumulated; //total rotation: left, right
        //int target = 0;

        //sleep(1000);
        //mrGryo.calibrate();
        //while (mrGryo.isCalibrating()) {
        //wait for calibrating to finish
        //}


//------------------------------------------------------------------------------------------------------------------------------
         /*
         * To start up Vuforia, tell it the view that we wish to use for camera monitor (on the RC phone);
         * If no camera monitor is desired, use the parameterless constructor instead (commented out below).
         */
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        // OR...  Do Not Activate the Camera Monitor View, to save power
        // VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        /*
         * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
         * 'parameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
         * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer
         * web site at https://developer.vuforia.com/license-manager.
         *
         * Vuforia license keys are always 380 characters long, and look as if they contain mostly
         * random data. As an example, here is a example of a fragment of a valid key:
         *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kT ...
         * Once you've obtained a license key, copy the string from the Vuforia web site
         * and paste it in to your code onthe next line, between the double quotes.
         */
        parameters.vuforiaLicenseKey = "ATwJ9+j/////AAAAGWKRoGTF3EXjjiUONUpE/FEwHMBGsRsjSjnKHLRm/QkTZrfBTDWGmxaODJswltGeGHE/NewaAKjI9tFnnLg4uFGaQVAgYWNmHvi7RFMfMiQKKWXbwL6KjW7hFcPyZClckV+wfMPtW0EYe2if1IfwAx/C82Z2TqAbFLHWgz2QMf2h+LatQz5jgAJA+N46A+fNjDu4Ixf5VPiTL8Rffdho5FdLh0mWvrW7fnIjJvVfmHIaX+VSSRmWlK+rvmZN9fiD2Yi7jD99mArgXvQBq8fUBvUouzPNw5iRh1tiy8PiytQl0a39zXo9xpseGJ/HnpFDjklAvntMXQTIn2nl1bg9J9N3WZkEST4ymb+7CpgKYyp0";

        /*
         * We also indicate which camera on the RC that we wish to use.
         * Here we chose the back (HiRes) camera (for greater range), but
         * for a competition robot, the front camera might be more convenient.
         */
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        /*
         * Load the data set containing the VuMarks for Relic Recovery. There's only one trackable
         * in this data set: all three of the VuMarks in the game were created from this one template,
         * but differ in their instance id information.
         * @see VuMarkInstanceId
         */
        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary

        telemetry.addData(">", "Press Play to start");
        telemetry.update();
        waitForStart();

        relicTrackables.activate();


//------------------------------------------------------------------------------------------------------------------------------
        //start Autonomous
            /*
             * See if any of the instances of {@link relicTemplate} are currently visible.
             * {@link RelicRecoveryVuMark} is an enum which can have the following values:
             * UNKNOWN, LEFT, CENTER, and RIGHT. When a VuMark is visible, something other than
             * UNKNOWN will be returned by {@link RelicRecoveryVuMark#from(VuforiaTrackable)}.
             */

        // RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
        //if (vuMark != RelicRecoveryVuMark.UNKNOWN) {

                /* Found an instance of the template. In the actual game, you will probably
                 * loop until this condition occurs, then move on to act accordingly depending
                 * on which VuMark was visible. */
        //  telemetry.addData("VuMark", "%s visible", vuMark);

                /* For fun, we also exhibit the navigational pose. In the Relic Recovery game,
                 * it is perhaps unlikely that you will actually need to act on this pose information, but
                 * we illustrate it nevertheless, for completeness. */
        //  OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) relicTemplate.getListener()).getPose();
        //  telemetry.addData("Pose", format(pose));

                /* We further illustrate how to decompose the pose into useful rotational and
                 * translational components */
        // if (pose != null) {
        //  VectorF trans = pose.getTranslation();
        //  Orientation rot = Orientation.getOrientation(pose, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);

        // Extract the X, Y, and Z components of the offset of the target relative to the robot
                  /*  double tX = trans.get(0);
                    double tY = trans.get(1);
                    double tZ = trans.get(2);

                    // Extract the rotational components of the target relative to the robot
                    double rX = rot.firstAngle;
                    double rY = rot.secondAngle;
                    double rZ = rot.thirdAngle;
                }
            } else {
                telemetry.addData("VuMark", "not visible");
            }

            telemetry.update();
            */


        //forward = isJewelRedFinal();

        //if (forward) {
        VerticalDriveDistance(-0.4, -2*rev);
        sleep(300);
        RotateDistance(-0.4, -3*rev/2);
        VerticalDriveDistance(0.8, rev);
        VerticalDriveDistance(-0.8, -3*rev);
        RotateDistance(0.8, 3*rev);
        VerticalDriveDistance(0.8, 2*rev);
        //} else if(!forward) {
        //    VerticalDriveDistance(-0.4, -rev / 2);
        //}
        //sleep(100);





        //     turnAbsolute(target);
        //    telemetry.addData("1. accu", String.format("%03d", mrGryo.getIntegratedZValue()));
        waitOneFullHardwareCycle();
    }


    String format(OpenGLMatrix transformationMatrix) {
        return (transformationMatrix != null) ? transformationMatrix.formatAsTransform() : "null";
    }

    //------------------------------------------------------------------------------------------------------------------------------
    //Driving Power Functions
    void StopDriving() {
        robot.frontLeft.setPower(0);
        robot.frontRight.setPower(0);
        robot.backLeft.setPower(0);
        robot.backRight.setPower(0);
    }

    //distance=rate*duration duration=distance/rate
    //power drives forward, -power drives backward
    void VerticalDrive(double power) {
       /* double leftSpeed;
        double rightSpeed;

        double target = mrGryo.getIntegratedZValue();
        double startPosition = frontLeft.getCurrentPosition();

        while (frontLeft.getCurrentPosition() < duration + startPosition) {
            int zAccumulated = mrGryo.getIntegratedZValue();
            leftSpeed = power + (zAccumulated - target) / 100;
            rightSpeed = power - (zAccumulated - target) / 100;

            leftSpeed = Range.clip(leftSpeed, -1, 1);
            rightSpeed = Range.clip(rightSpeed, -1, 1);

            frontLeft.setPower(leftSpeed);
            backLeft.setPower(leftSpeed);
            frontRight.setPower(rightSpeed);
            backRight.setPower(rightSpeed);

            telemetry.addData("1. frontLeft", frontLeft.getPower());
            telemetry.addData("2. backLeft", backLeft.getPower());
            telemetry.addData("3. frontRight", frontRight.getPower());
            telemetry.addData("4. backRight", backRight.getPower());
            telemetry.addData("5. Distance to go", (duration + startPosition) - frontLeft.getCurrentPosition());

            waitOneFullHardwareCycle();
        }

        StopDriving();
        waitOneFullHardwareCycle();
        */
        robot.frontLeft.setPower(power);
        robot.frontRight.setPower(power);
        robot.backLeft.setPower(power);
        robot.backRight.setPower(power);
    }

    //power drives right, -power drives left
    void HorizontalStrafing(double power) {
        robot.frontLeft.setPower(power);
        robot.frontRight.setPower(-power);
        robot.backLeft.setPower(-power);
        robot.backRight.setPower(power);
    }

    void Rotate(double power) {
        robot.frontLeft.setPower(power);
        robot.backLeft.setPower(power);
        robot.frontRight.setPower(-power);
        robot.backRight.setPower(-power);
    }

    //------------------------------------------------------------------------------------------------------------------------------
    //Encoder Functions
    void VerticalDriveDistance(double power, int distance) throws InterruptedException {
        //reset encoders
        robot.frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.frontLeft.setTargetPosition(distance);
        robot.frontRight.setTargetPosition(distance);
        robot.backLeft.setTargetPosition(distance);
        robot.backRight.setTargetPosition(distance);

        robot.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        VerticalDrive(power);

        while (robot.frontLeft.isBusy() && robot.frontRight.isBusy() && robot.backLeft.isBusy() && robot.backRight.isBusy()) {
            //wait until robot stops
        }

        StopDriving();
    }

    void HorizontalStrafingDistance(double power, int distance) throws InterruptedException {
        //reset encoders
        robot.frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        robot.frontLeft.setTargetPosition(distance);
        robot.frontRight.setTargetPosition(-distance);
        robot.backLeft.setTargetPosition(-distance);
        robot.backRight.setTargetPosition(distance);

        HorizontalStrafing(power);

        while (robot.frontLeft.isBusy() && robot.frontRight.isBusy() && robot.backLeft.isBusy() && robot.backRight.isBusy()) {
            //wait until robot stops
        }

        StopDriving();
    }

    void RotateDistance(double power, int distance) throws InterruptedException {
        {
            //reset encoders
            robot.frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            robot.frontLeft.setTargetPosition(distance);
            robot.frontRight.setTargetPosition(-distance);
            robot.backLeft.setTargetPosition(distance);
            robot.backRight.setTargetPosition(-distance);

            robot.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            Rotate(power);

            while (robot.frontLeft.isBusy() && robot.frontRight.isBusy() && robot.backLeft.isBusy() && robot.backRight.isBusy()) {
                //wait until robot stops
            }

            StopDriving();
        }

    }

//------------------------------------------------------------------------------------------------------------------------------
    //Winching functions

    void grabBottom() throws InterruptedException {
        robot.botServL.setPosition(robot.GRAB_CHOP_POS_A + 0.1);
        robot.botServR.setPosition(robot.GRAB_CHOP_POS_B);
        robot.bChop = true;
        sleep(300);
    }

    void startBottom() throws InterruptedException {
        robot.botServL.setPosition(robot.START_CHOP_POS_A);
        robot.botServR.setPosition(robot.START_CHOP_POS_B + 0.1);
        robot.bChop = false;
        sleep(300);
    }

    void grabTop() throws InterruptedException {
        robot.topServL.setPosition(robot.GRAB_CHOP_POS_B - 0.2);
        robot.topServR.setPosition(robot.GRAB_CHOP_POS_A);
        robot.tChop = true;
        sleep(300);
    }

    void startTop() throws InterruptedException {
        robot.topServL.setPosition(robot.START_CHOP_POS_B - 0.1);
        robot.topServR.setPosition(robot.START_CHOP_POS_A - 0.1);
        robot.tChop = false;
        sleep(300);
    }

//------------------------------------------------------------------------------------------------------------------------------
    //isJewelRed

    /*public boolean isJewelRed() {
        telemetry.addData("blue value", robot.color_sensor.blue());
        telemetry.addData("red value", robot.color_sensor.red());


        if (robot.color_sensor.red() > robot.color_sensor.blue()) {
            telemetry.addData("The color is ", "red");
            telemetry.update();
            return true;
        } else {
            telemetry.addData("The color is ", "blue");
            telemetry.update();
            return false;
        }
    }

    public boolean isJewelRedFinal() {
        int red = 0;
        int blue = 0;
        boolean isRed = false;

        for (int i = 0; i < 20; i++) {
            if (isJewelRed()) {
                red++;
            } else {
                blue++;
            }
        }

        if (red < blue) {
            isRed = false;
        } else if (blue < red) {
            isRed = true;
        }

        return isRed;

    }
    */


//------------------------------------------------------------------------------------------------------------------------------
    //Turning Function

    /*public void turn(int target) throws InterruptedException {
        turnAbsolute(target + mrGryo.getIntegratedZValue());
    }

    public void turnAbsolute(int target) throws InterruptedException {
        int Accumulated = mrGryo.getIntegratedZValue(); //sets gryo readings to accumulated
        double turnspeed = 0.15;

        while (Accumulated - target > 3) {
            if (Accumulated > target) { //if gryo is positive, turn left
                TurnLeft(turnspeed);
            }
            if (Accumulated < target) { // if gryo is negative, turn right
                TurnRight(turnspeed);
            }

            waitOneFullHardwareCycle();
            Accumulated = mrGryo.getIntegratedZValue();
            telemetry.addData("1. accu", String.format("%03d", Accumulated));

        }
        StopDriving();
        telemetry.addData("1. accu", String.format("%03d", Accumulated));
        waitOneFullHardwareCycle();
    }*/
//------------------------------------------------------------------------------------------------------------------------------

}