package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by oliversun on 9/30/17.
 */

@TeleOp(name = "Servo Test")
public class ServoTest extends LinearOpMode {
    private Servo servo1;
    boolean extend;

    @Override
    public void runOpMode() throws InterruptedException {
        servo1 = hardwareMap.servo.get("servo1");

        servo1.setPosition(0.8);
        extend = false;

        waitForStart();

        while (opModeIsActive()) {
            if (!extend) {
                if (gamepad1.a) {
                    servo1.setPosition(0.8);
                    extend = true;
                    sleep(300);
                }
            } else {
                if (gamepad1.a) {
                    servo1.setPosition(0.3);
                    extend = false;
                    sleep(300);
                }
            }
        }
    }
}
