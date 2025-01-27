package org.firstinspires.ftc.teamcode.tests;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Disabled
@Config
@TeleOp(group = "Tests")
public class Servus extends LinearOpMode {
    public static double ServoPOS = 0.5;

    public Servo servo;

    public void runOpMode(){
        servo = hardwareMap.get(Servo.class, "servo");
        waitForStart();

        while (opModeIsActive()){
            if (gamepad1.b){
                servo.setPosition(ServoPOS);
            } else if (gamepad1.a) {
                servo.setPosition(0.0);
            }
        }
    }
}
