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

@Autonomous(name = "Auto")
public class Auto extends LinearOpMode {

    //GyroSensor sensorGyro;
    //ModernRoboticsI2cGyro mrGryo;

    VuforiaLocalizer vuforia;

    //ColorSensor color_sensor;

    hMap robot = new hMap();


    @Override
    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap);


        int rev = 1; //one revolution

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
        waitForStart();
        while (opModeIsActive()) {
            /*

            robot.init(hardwareMap);

            robot.backLeft.setDirection(DcMotor.Direction.REVERSE);
            robot.frontLeft.setDirection(DcMotor.Direction.REVERSE);
            robot.backRight.setDirection(DcMotor.Direction.FORWARD);
            robot.frontRight.setDirection(DcMotor.Direction.FORWARD);
            */

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


//------------------------------------------------------------------------------------------------------------------------------
            //turning and driving test
            startBottom();
            startTop();
            sleep(1000);
            /*
            if(isJewelRedFinal()){
                telemetry.addData("The jewel is: ", "red");
            }
            else{
                telemetry.addData("The jewel is: ", "blue");
            }
            */
            sleep(1000);

            //forwards
            VerticalDriveDistance(0.4, rev);
            sleep(300);
            //right
            HorizontalStratffingDistance(0.4, rev);
            sleep(300);
            //backwards
            VerticalDriveDistance(-0.4, rev);
            sleep(300);
            //left
            HorizontalStratffingDistance(-0.4, rev);
            sleep(300);
            /*VerticalDriveDistance(0.4, rev);
            sleep(300);
            HorizontalStratffingDistance(-0.4, 2 * rev);
            sleep(300);
            HorizontalStratffingDistance(0.4, rev);
            sleep(300);
            HorizontalStratffingDistance(0.4, 5 * rev);
            sleep(300);
            */


            //     turnAbsolute(target);
            //    telemetry.addData("1. accu", String.format("%03d", mrGryo.getIntegratedZValue()));
            waitOneFullHardwareCycle();
        }
    }


    String format(OpenGLMatrix transformationMatrix) {
        return (transformationMatrix != null) ? transformationMatrix.formatAsTransform() : "null";
    }

    //------------------------------------------------------------------------------------------------------------------------------
    //Driving Power Functions
    public void StopDriving() {

        robot.frontLeft.setPower(0);
        robot.frontRight.setPower(0);
        robot.backLeft.setPower(0);
        robot.backRight.setPower(0);
    }

    //distance=rate*duration duration=distance/rate
    //power drives forward, -power drives backward
    public void VerticalDrive(double power) {
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
    public void HorizontalStraffing(double power) {
        robot.frontLeft.setPower(power);
        robot.frontRight.setPower(-power);
        robot.backLeft.setPower(power);
        robot.backRight.setPower(-power);
    }

    //------------------------------------------------------------------------------------------------------------------------------
    //Encoder Functions
    public void VerticalDriveDistance(double power, int distance) throws InterruptedException {
        //reset encoders
        robot.frontLeft.setMode(DcMotor.RunMode.RESET_ENCODERS);
        robot.frontRight.setMode(DcMotor.RunMode.RESET_ENCODERS);
        robot.backLeft.setMode(DcMotor.RunMode.RESET_ENCODERS);
        robot.backRight.setMode(DcMotor.RunMode.RESET_ENCODERS);

        robot.frontLeft.setTargetPosition(distance);
        robot.frontRight.setTargetPosition(distance);
        robot.backLeft.setTargetPosition(distance);
        robot.backRight.setTargetPosition(distance);

        VerticalDrive(power);

        robot.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (robot.frontLeft.isBusy() && robot.frontRight.isBusy() && robot.backLeft.isBusy() && robot.backRight.isBusy()) {
            //wait until robot stops
        }

        StopDriving();
        robot.frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public void HorizontalStratffingDistance(double power, int distance) throws InterruptedException {
        //reset encoders
        robot.frontLeft.setMode(DcMotor.RunMode.RESET_ENCODERS);
        robot.frontRight.setMode(DcMotor.RunMode.RESET_ENCODERS);
        robot.backLeft.setMode(DcMotor.RunMode.RESET_ENCODERS);
        robot.backRight.setMode(DcMotor.RunMode.RESET_ENCODERS);

        robot.frontLeft.setTargetPosition(distance);
        robot.frontRight.setTargetPosition(distance);
        robot.backLeft.setTargetPosition(distance);
        robot.backRight.setTargetPosition(distance);

        HorizontalStraffing(power);

        robot.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (robot.frontLeft.isBusy() && robot.frontRight.isBusy() && robot.backLeft.isBusy() && robot.backRight.isBusy()) {
            //wait until robot stops
        }

        StopDriving();
        robot.frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

//------------------------------------------------------------------------------------------------------------------------------
    //Winching functions

    public void grabBottom() throws InterruptedException {
        robot.botServL.setPosition(robot.GRAB_CHOP_POS_A);
        robot.botServR.setPosition(robot.GRAB_CHOP_POS_B);
        robot.bChop = true;
        sleep(300);
    }

    public void startBottom() throws InterruptedException {
        robot.botServL.setPosition(robot.START_CHOP_POS_A);
        robot.botServR.setPosition(robot.START_CHOP_POS_B);
        robot.bChop = false;
        sleep(300);
    }

    public void grabTop() throws InterruptedException {
        robot.topServL.setPosition(robot.GRAB_CHOP_POS_B);
        robot.topServR.setPosition(robot.GRAB_CHOP_POS_A);
        robot.tChop = true;
        sleep(300);
    }

    public void startTop() throws InterruptedException {
        robot.topServL.setPosition(robot.START_CHOP_POS_B);
        robot.topServL.setPosition(robot.START_CHOP_POS_A);
        robot.tChop = false;
        sleep(300);
    }

//------------------------------------------------------------------------------------------------------------------------------
    //isJewelRed
    /*
public boolean isJewelRed (){
    telemetry.addData("blue value", robot.color_sensor.blue());
    telemetry.addData("red value", robot.color_sensor.red());


    if(robot.color_sensor.red()>robot.color_sensor.blue()){
        telemetry.addData("The color is ", "red");
        telemetry.update();
        return true;
    }
    else{
        telemetry.addData("The color is ", "blue");
        telemetry.update();
        return false;
    }
}

    public boolean isJewelRedFinal() {
        int red = 0;
        int blue = 0;
        Boolean isRed = null;

        for (int i = 0; i < 20; i++) {
            if (isJewelRed()) {
                red++;
            } else if (!isJewelRed()) ;
            {
                blue++;
            }
            sleep(100);
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
