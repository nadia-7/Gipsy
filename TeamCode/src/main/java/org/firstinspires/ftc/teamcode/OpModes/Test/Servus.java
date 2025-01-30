package org.firstinspires.ftc.teamcode.OpModes.Test;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Config
@TeleOp(group = "Tests")
public class Servus extends LinearOpMode {
    public static double ServoPOS1 = 0.11;
    public static double Servo2POS1 = 0.01;
    public static double ServoPOS2 = 0.45;
    public static double Servo2POS2 = 0.49;
    public static double increment = 0.1;

    public Servo servo;
    public Servo servo2;
    private boolean pressedDown = false;
    private boolean pressedUp = false;

    public void runOpMode(){
        servo = hardwareMap.get(Servo.class, "servo");
        //servo2 = hardwareMap.get(Servo.class, "complementServo");
        waitForStart();

        while (opModeIsActive()){
            if (gamepad1.b){
                servo.setPosition(ServoPOS1);
                //servo2.setPosition(Servo2POS1);
            } else if (gamepad1.a) {
                servo.setPosition(ServoPOS2);
                //servo2.setPosition(Servo2POS2);
            } else if(gamepad1.dpad_up && servo.getPosition() < 1){
                pressedUp = true;
            } else if (gamepad1.dpad_down && servo.getPosition() > 0) {
                pressedDown = true;
            }

            if(!gamepad1.dpad_up && pressedUp){
                servo.setPosition(servo.getPosition() + increment);
                pressedUp = false;
            } else if (!gamepad1.dpad_down && pressedDown) {
                servo.setPosition(servo.getPosition() - increment);
                pressedDown = false;
            }


            telemetry.addData("pS1:", servo.getPosition());
            //telemetry.addData("pS2:", servo.getPosition());
            telemetry.update();
        }
    }
}