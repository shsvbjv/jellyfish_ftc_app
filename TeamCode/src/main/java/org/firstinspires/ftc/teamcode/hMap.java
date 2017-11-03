package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

/**
 * Created by oliversun on 10/7/17.
 */

public class hMap {

    //Gyro

    // The IMU sensor object
    public BNO055IMU imu;

    // State used for updating telemetry
    public Orientation angles;
    public Acceleration gravity;


    /* Motors */

    //Wheels
    public DcMotor frontLeft   ;
    public DcMotor frontRight  ;
    public DcMotor backLeft    ;
    public DcMotor backRight   ;

    //Winch
    public DcMotor lWinch      ;
    public DcMotor rWinch      ;

    /* Servos */

    //Chopstick Servos
    public Servo topServL    ;
    public Servo topServR    ;
    public Servo botServL    ;
    public Servo botServR    ;

    //Sensor Arm Servo, for jewel arm
    public Servo armServo    ;

    /* Sensors */
    public ColorSensor color_sensor;

    //Values for the chopsticks and tail
    public static final double START_TAIL_POS    = 0.8;
    public static final double GRAB_TAIL_POS     = 0.3;
    public static final double START_CHOP_POS_A  = 0.1;
    public static final double START_CHOP_POS_B  = 0.9;
    public static final double GRAB_CHOP_POS_A   = 0.4;
    public static final double GRAB_CHOP_POS_B   = 0.6;

    //Start and end positions for the jewel arm
    public static final double UP_JARM_POS = 0;
    public static final double DOWN_JARM_POS = 0.75;

    //boolean for servo function
    public boolean tail ;
    public boolean tChop;
    public boolean bChop;
    public boolean tHalf;
    public boolean bHalf;

    HardwareMap hwMap;

    public hMap() {}

    public void init(HardwareMap ahwMap) {



        hwMap = ahwMap;

        /* Motors */

        //Wheels
        frontLeft       = hwMap.get(DcMotor.class, "frontLeft")       ;
        frontRight      = hwMap.get(DcMotor.class, "frontRight")      ;
        backLeft        = hwMap.get(DcMotor.class, "backLeft")        ;
        backRight       = hwMap.get(DcMotor.class, "backRight")       ;

        frontRight.setDirection(DcMotor.Direction.REVERSE)            ;
        backRight .setDirection(DcMotor.Direction.REVERSE)            ;

        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);

        //Winch
        lWinch          = hwMap.get(DcMotor.class, "lWinch")          ;
        rWinch          = hwMap.get(DcMotor.class, "rWinch")          ;

        lWinch.setDirection(DcMotor.Direction.REVERSE);

        lWinch.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rWinch.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        lWinch.setPower(0);
        rWinch.setPower(0);

        /* Servos */
        topServL        = hwMap.get(Servo.class, "topServL")          ;
        topServR        = hwMap.get(Servo.class, "topServR")          ;
        botServL        = hwMap.get(Servo.class, "botServL")          ;
        botServR        = hwMap.get(Servo.class, "botServR")          ;
        armServo        = hwMap.get(Servo.class, "armServo")          ;

        /* Sensors */
        color_sensor    = hwMap.get(ColorSensor.class, "color_sensor");
    }

    public void gyroInit() {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        imu = hwMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
    }
}
