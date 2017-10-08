package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by KyleP on 10/6/2017.
 */

public class hMap {
    public DcMotor frontLeft;
    public DcMotor backLeft;
    public DcMotor frontRight;
    public DcMotor backRight;

    public Servo botServL, botServR, topServL, topServR;

    public HardwareMap HMap =  null;

    public hMap(){

    }

    public void init (HardwareMap AHwMap){
        HMap = AHwMap;

        botServL = HMap.get(Servo.class, "botServL");
        botServR = HMap.get(Servo.class, "botServR");
        topServL = HMap.get(Servo.class, "topServL");
        topServR = HMap.get(Servo.class, "topServR");

        //botServL.setPosition(START_POSA);
        //botServR.setPosition(START_POSB);
        //topServL.setPosition(START_POSA);
        //topServR.setPosition(START_POSB);
        //extendB = false;
        //extendT = false;

        frontLeft = HMap.get(DcMotor.class, "frontLeft");
        frontRight = HMap.get(DcMotor.class, "frontRight");
        backLeft = HMap.get(DcMotor.class, "backLeft");
        backRight = HMap.get(DcMotor.class, "backRight");

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //for winch
        //rWinch = hardwareMap.dcMotor.get("rWinch");
        //lWinch = hardwareMap.dcMotor.get("lWinch");

        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
    }
}
