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
    //     ticks = 537.7;
    int newTarget = 90;
    public int nsdkl(int degrees){return (int) Math.round(degrees * 1.4936111);}

    public void runOpMode(){
        initBarredora();
        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.right_bumper) {
                motor.setTargetPosition(nsdkl(newTarget));
                motor.setPower(0.6);
                sleep(1200);
                Articulacion(0.5,0.5);
            }
            if (gamepad1.left_bumper) {
                Articulacion(0.0,1.0);
                sleep(1200);
                motor.setTargetPosition(0);
                motor.setPower(0.6);
            }
            telemetry.addData("motorPos", motor.getTargetPosition());

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
        motor.setTargetPosition(nsdkl(newTarget));
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
