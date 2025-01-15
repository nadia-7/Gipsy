package org.firstinspires.ftc.teamcode.mecanismos;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Motor Test ")
public class BarredoraTest extends LinearOpMode {
    DcMotor motor;
    public Servo Articulacion_Barredora1;
    public Servo Articulacion_Barredora2;
    DcMotor Barredora;
    public int pos;

    public int degreesToTicks(int degrees){return (int) Math.round(degrees * 2.0883);}
    @Override
    public void runOpMode(){
        initBarredora();
        waitForStart();

        while (opModeIsActive()) {
            //Corredera
            if (gamepad1.right_bumper && pos == 0) {
                motor.setTargetPosition(degreesToTicks(45));
                motor.setPower(0.6);
                pos += pos;
            }else if (gamepad1.right_bumper && pos == 1){
                motor.setTargetPosition(degreesToTicks(90));
                motor.setPower(0.6);
                pos += pos;
            }else {
                motor.setPower(0.0);
            }
            if (gamepad1.left_bumper && pos == 2) {
                motor.setTargetPosition(degreesToTicks(45));
                motor.setPower(0.6);
                pos -= pos;
            }else if (gamepad1.left_bumper && pos == 1){
                motor.setTargetPosition(degreesToTicks(0));
                motor.setPower(0.6);
                pos -= pos;
            } else{
                motor.setPower(0.0);
            }
            //Articulacion
            if (gamepad1.dpad_up){
                Articulacion(0.5,0.5);
            }
            if (gamepad1.dpad_down){
                Articulacion(0.0,1.0);
            }

            telemetry.addData("motorPos", motor.getCurrentPosition());
            telemetry.update();

            if (gamepad1.a){
                Barredora.setPower(1.0);
            } else if (gamepad1.x){
                Barredora.setPower(-1.0);
            } else{
                Barredora.setPower(0.0);
            }
        }
    }
    public void initBarredora(){
        motor = hardwareMap.get(DcMotor.class, "Corredera");
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor.setTargetPosition(degreesToTicks(0));
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Articulacion_Barredora1 = hardwareMap.get(Servo.class, "A_Barredora1");
        Articulacion_Barredora2 = hardwareMap.get(Servo.class, "A_Barredora2");
        Barredora = hardwareMap.get(DcMotor.class, "Barredora");
        telemetry.addLine("Barredora iniciada");
        telemetry.update();
    }
    public void Articulacion(double POS, double POS2){
        Articulacion_Barredora1.setPosition(POS);
        Articulacion_Barredora2.setPosition(POS2);
    }
}
