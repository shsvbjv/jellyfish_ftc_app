package org.firstinspires.ftc.teamcode;

import java.util.*;
import java.util.Vector;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
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
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.firstinspires.ftc.robotcore.external.navigation.VuMarkInstanceId;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;


@TeleOp(name = "CSTest")
public class ColorTest extends LinearOpMode {

    hMap robot = new hMap();


    int color = 0;
    int avgred = 0;
    int avgblue = 0;
    int red = 0;
    int blue = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        waitForStart();

        while(opModeIsActive()) {
            if (gamepad1.left_bumper) {
                telemetry.addData("Red: ", robot.color_sensor.red());
                telemetry.addData("Blue: ", robot.color_sensor.blue());
                telemetry.update();
            } else if (gamepad1.right_bumper) {
                boolean isRed;
                isRed = isJewelRedFinal();

                if(isRed) {
                    color = 1;
                } else if(!isRed) {
                    color = 2;
                } else {
                    color = 0;
                }

                if(color == 1) {
                    telemetry.addData("Color: ", "RED");
                    telemetry.addData("RedAvg: ", avgred);
                    telemetry.addData("xRed", red);
                    telemetry.addData("BlueAvg: ", avgblue);
                    telemetry.addData("xBlue", blue);
                } else if(color == 2) {
                    telemetry.addData("Color: ", "BLUE");
                    telemetry.addData("RedAvg: ", avgred);
                    telemetry.addData("xRed", red);
                    telemetry.addData("BlueAvg: ", avgblue);
                    telemetry.addData("xBlue", blue);
                } else {
                    telemetry.addData("Color: ", "UNKNOWN");
                    telemetry.addData("RedAvg: ", avgred);
                    telemetry.addData("xRed", red);
                    telemetry.addData("BlueAvg: ", avgblue);
                    telemetry.addData("xBlue", blue);
                }

                telemetry.update();

                color = 0;
            }
        }
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
        red = 0;
        blue = 0;
        avgred = 0;
        avgblue = 0;
        boolean isRed = false;

        for (int i = 0; i < 20; i++) {
            avgred += robot.color_sensor.red();
            avgblue += robot.color_sensor.blue();

            if (isJewelRed()) {
                red++;
            } else {
                blue++;
            }
        }

        avgred /= 20;
        avgblue /= 20;

        if (red < blue) {
            isRed = false;
        } else if (blue < red) {
            isRed = true;
        }

        return isRed;

    }

}