package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.Locale;

/**
 * Created by Ferannow and Kyle on 9/23/17. 123
 */

@Autonomous(name = "AutoBlueTop")
public class AutoBlueTop extends LinearOpMode {

    //this object tests to see if pitch is 0
    PitchChecker tester = new PitchChecker();

    //heading for gyro
    double heading;
    double temp;
    double pitch;

    ElapsedTime runtime = new ElapsedTime();

    VuforiaLocalizer vuforia;

    hMap robot = new hMap();

    //diameter of mecanum wheels = 4in
    //Circumference = 12.5663706144in
    //1 revolution=7 encoder values
    //1 rev = 12.56637036144in = 1.0471975512ft or 12.5663706144in
    int rev = 1120;
    int winchrev = 560;
    boolean forward;
    boolean found = false;
    String cryptobox_column;


    @Override
    public void runOpMode() throws InterruptedException {

        // Set up our telemetry dashboard
        composeTelemetry();

        robot.init(hardwareMap);

        setHeadingToZero();

        robot.color_sensor.enableLed(true);


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

        waitForStart();

        runtime.reset();

        relicTrackables.activate();


//------------------------------------------------------------------------------------------------------------------------------
        //start Autonomous
            /*
             * See if any of the instances of {@link relicTemplate} are currently visible.
             * {@link RelicRecoveryVuMark} is an enum which can have the following values:
             * UNKNOWN, LEFT, CENTER, and RIGHT. When a VuMark is visible, something other than
             * UNKNOWN will be returned by {@link RelicRecoveryVuMark#from(VuforiaTrackable)}.
             */


        robot.armServo.setPosition(robot.DOWN_JARM_POS);

        forward = isJewelRedFinal();

        grabTop();

        sleep(500);

        Winch(1);

        sleep(400);

        while (!found) {
            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
            if (vuMark != RelicRecoveryVuMark.UNKNOWN) {
                cryptobox_column = vuMark.toString();
                found = true;
                telemetry.addData("VuMark", "%s visible", vuMark);

                /* For fun, we also exhibit the navigational pose. In the Relic Recovery game,
                 * it is perhaps unlikely that you will actually need to act on this pose information, but
                 * we illustrate it nevertheless, for completeness. */
                OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) relicTemplate.getListener()).getPose();
                telemetry.addData("Pose", format(pose));

                /* We further illustrate how to decompose the pose into useful rotational and
                 * translational components */
                if (pose != null) {
                    VectorF trans = pose.getTranslation();
                    Orientation rot = Orientation.getOrientation(pose, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);

                    // Extract the X, Y, and Z components of the offset of the target relative to the robot
                    double tX = trans.get(0);
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
            if(runtime.seconds() > 5) {
                break;
            }
        }

        if (forward) {
            RotateDistance(-0.4, -rev / 2);
            robot.armServo.setPosition(robot.UP_JARM_POS);
            sleep(100);
            RotateDistance(0.4, rev / 2);
            sleep(100);
            VerticalDriveDistance(-0.4, -2 * rev);
            sleep(100);
            RotateDistance(-0.8, -3 * rev / 2);
            sleep(100);
            VerticalDriveDistance(-0.5, -2 * rev);
            sleep(100);
            if(cryptobox_column == "LEFT") {
                VerticalDriveDistance(0.3, 7 * rev / 5 + 200);
            } else if(cryptobox_column == "CENTER") {
                VerticalDriveDistance(0.3, 2 * rev);
            } else {
                VerticalDriveDistance(0.3, 14 * rev / 5);
            }
            sleep(100);
            RotateDistance(-0.5, -3 * rev / 2 + 100);
            sleep(200);
            VerticalDriveDistance(0.5, 3 * rev / 2);
            sleep(200);
            startTop();
            sleep(200);
            VerticalDriveDistance(-0.3, -rev / 4);
        } else if (!forward) {
            VerticalDriveDistance(-0.4, -2 * rev);
            sleep(100);
            robot.armServo.setPosition(robot.UP_JARM_POS);
            sleep(300);
            RotateDistance(-0.8, -3 * rev / 2);
            sleep(100);
            VerticalDriveDistance(-0.5, -2 * rev);
            sleep(100);
            if(cryptobox_column == "LEFT") {
                VerticalDriveDistance(0.3, 7 * rev / 5 + 100);
            } else if(cryptobox_column == "CENTER") {
                VerticalDriveDistance(0.3, 2 * rev);
            } else {
                VerticalDriveDistance(0.3, 14 * rev / 5);
            }
            sleep(100);
            RotateDistance(-0.5, -3 * rev / 2 + 100);
            sleep(200);
            VerticalDriveDistance(0.5, 3 * rev / 2);
            sleep(200);
            startTop();
            sleep(200);
            VerticalDriveDistance(-0.3, -rev / 4);
        }

        //sleep(100);

        /*RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
        while (!found) {
            found = true;
            telemetry.addData("VuMark", "%s visible", vuMark);
            cryptobox_column = vuMark.toString();
            OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) relicTemplate.getListener()).getPose();
            telemetry.addData("Pose", format(pose));
            if (pose != null) {
                VectorF trans = pose.getTranslation();
                Orientation rot = Orientation.getOrientation(pose, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
                double tX = trans.get(0);
                double tY = trans.get(1);
                double tZ = trans.get(2);
                double rX = rot.firstAngle;
                double rY = rot.secondAngle;
                double rZ = rot.thirdAngle;
            } else {
                telemetry.addData("VuMark", "not visible");
            }
            telemetry.update();
        }*/

        //}
    }


    String format (OpenGLMatrix transformationMatrix){
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

    void rotateRight(double power) {
        robot.frontLeft.setPower(power);
        robot.backLeft.setPower(power);
        robot.frontRight.setPower(-power);
        robot.backRight.setPower(-power);
    }

    void rotateLeft(double power) {
        rotateRight(-power);
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
        }

        //StopDriving();
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

        // HorizontalStrafing(power);

        while (robot.frontLeft.isBusy() && robot.frontRight.isBusy() && robot.backLeft.isBusy() && robot.backRight.isBusy()) {
            //wait until robot stops
        }

//        StopDriving();
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

            rotateRight(power);

            while (robot.frontLeft.isBusy() && robot.frontRight.isBusy() && robot.backLeft.isBusy() && robot.backRight.isBusy()) {
                //wait until robot stops
            }

            //          StopDriving();
        }
    }

    void Winch(double power) {
        robot.lWinch.setPower(power);
        robot.rWinch.setPower(power);
        sleep(2000);
        robot.lWinch.setPower(0.05);
        robot.rWinch.setPower(0.05);
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

    public boolean isJewelRed() {
        if (robot.color_sensor.red() > robot.color_sensor.blue()) {
            return true;
        } else {
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


//------------------------------------------------------------------------------------------------------------------------------


    void composeTelemetry() {
        // At the beginning of each telemetry update, grab a bunch of data
        // from the IMU that we will then display in separate lines.
        telemetry.addAction(new Runnable() {
            @Override
            public void run() {
                // Acquiring the angles is relatively expensive; we don't want
                // to do that in each of the three items that need that info, as that's
                // three times the necessary expense.
                robot.angles = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                //robot.gravity = robot.imu.getGravity();
            }
        });

        /*telemetry.addLine()
                .addData("status", new Func<String>() {
                    @Override
                    public String value() {
                        return robot.imu.getSystemStatus().toShortString();
                    }
                })
                .addData("calib", new Func<String>() {
                    @Override
                    public String value() {
                        return robot.imu.getCalibrationStatus().toString();
                    }
                });
                */

        telemetry.addLine()
                //rotating left adds to the heading, while rotating right makes the heading go down.
                //when heading reaches 180 it'll become negative and start going down.

                .addData("heading", new Func<String>() {
                    @Override
                    public String value() {

                        //heading is a string, so the below code makes it a long so it can actually be used
                        heading = Double.parseDouble(formatAngle(robot.angles.angleUnit, robot.angles.firstAngle));
                        temp = heading;
                        heading = (temp+360)%360;

                        return formatAngle(robot.angles.angleUnit, heading);

                    }


                })
                .addData("pitch", new Func<String>() {
                    @Override public String value() {

                        pitch = Double.parseDouble(formatAngle(robot.angles.angleUnit, robot.angles.thirdAngle));

                        if(tester.checkFlat(pitch)){

                        }

                        return formatAngle(robot.angles.angleUnit, robot.angles.thirdAngle);
                    }
                });
    }

    //----------------------------------------------------------------------------------------------
    // Formatting
    //----------------------------------------------------------------------------------------------

    //The two functions below are for gyro
    String formatAngle(AngleUnit angleUnit, double angle) {
        return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit, angle));
    }

    String formatDegrees(double degrees) {
        return String.format(Locale.getDefault(), "%.1f", AngleUnit.DEGREES.normalize(degrees));
    }

    void gyroRotateRight(double power) {

        robot.frontLeft.setPower(power);
        robot.backLeft.setPower(power);
        robot.frontRight.setPower(-power);
        robot.backRight.setPower(-power);

        while (heading>-90) {
            telemetry.update();
        }

        StopDriving();
    }

    void gyroRotateLeft(double power, double ngle) {
        //turn left
        rotateLeft(power+0.2);

        while(heading<0.6*ngle){
            telemetry.update();
        }
        //gradually slow turn
        for(int x=20; x>0; x--) {
            double addpower=power + (x/100);
            rotateLeft(addpower);
            telemetry.update();
            sleep(50);
        }

        while (heading <ngle+5) {
            telemetry.update();
        }
        StopDriving();
        //turn right(major adjust)
        rotateRight(power);

        while(heading>ngle+2){
            telemetry.update();
        }
        //adjusting to range of 2 degrees
        //turn left, then adjust right

        while(ngle-2>heading) {
            //turn left
            robot.frontLeft.setPower(-power);
            rotateLeft(power);
            while (heading < ngle+5) {
                telemetry.update();
            }
            StopDriving();
            //turn right
            rotateRight(power);

            while (heading > ngle+2) {
                telemetry.update();
            }
        }

        StopDriving();
    }

    void setHeadingToZero() {
        robot.gyroInit();
        robot.imu.startAccelerationIntegration(new Position(), new Velocity(), 1000);
    }

}