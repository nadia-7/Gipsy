package org.firstinspires.ftc.teamcode.tests;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Disabled
@Config
@TeleOp(group = "Tests")
public class ServoCr extends LinearOpMode {
    public static double CRServoPOW = 0.5;
    public CRServo servo;

    public void runOpMode(){
        servo = hardwareMap.get(CRServo.class, "servo");
        waitForStart();

        while (opModeIsActive()){
            if (gamepad1.b){
                servo.setPower(CRServoPOW);
            } else if (gamepad1.a) {
                servo.setPower(0.0);
            }
        }
    }
}
