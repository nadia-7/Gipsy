package org.firstinspires.ftc.teamcode.mecanismos;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
@TeleOp
public class BarredoraTest extends LinearOpMode {
    public DcMotor motor;
    public DcMotor barredora;
    public Servo barrArtDer; //3 control
    public Servo barrArtIzq; //0 expansion

    public void initB () {
        motor = hardwareMap.get(DcMotor.class, "correderaBarredora");
        barredora = hardwareMap.get(DcMotor.class, "ingesta");
        barrArtDer = hardwareMap.get(Servo.class, "bArtDer");
        barrArtIzq = hardwareMap.get(Servo.class, "bArtIzq");

        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        barredora.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        barredora.setPower(0);
        motor.setPower(0);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        barrArtDer.setPosition(0);
        barrArtIzq.setPosition(1);

        telemetry.addData("Articulacion Der", barrArtDer.getPosition());
        telemetry.addData("Articulacion Izq", barrArtIzq.getPosition());
        telemetry.update();
    }




    @Override
    public void runOpMode() {
        initB();

        waitForStart();

        while (opModeIsActive()) {
            //Extension
            if (gamepad1.dpad_up){
                extension(1);
            } else if (gamepad1.dpad_down && motor.getCurrentPosition() < 0){
                retraccion(1);
            } else mantener();

            //Ingesta
           if (gamepad1.dpad_left){//sacar
                barredora.setPower(1.0);
           } else if (gamepad1.dpad_right){
              barredora.setPower(-1.0);
            } else{
               barredora.setPower(0.0);
          }

           //articulación //derecha 0-1
           if (gamepad1.a){
               barrArtDer.setPosition(0);
               barrArtIzq.setPosition(1);
           } else if (gamepad1.b){
               barrArtDer.setPosition(0.5);
               barrArtIzq.setPosition(0.5);
           }


            telemetry.addData("Articulacion Der", barrArtDer.getPosition());
            telemetry.addData("Articulacion Izq", barrArtIzq.getPosition());
            telemetry.addData("POSICION EXTENSION", motor.getCurrentPosition());
            telemetry.update();


        }
    }
    //PASAR GRADOS A TICKS
    public int degreesToTicks(int degrees) {
        return (int) Math.round(degrees * 1.493611111);
    }

    public void extension (double POWER){
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setPower(-POWER); //-1 ES PARA EXTENDERSE
    }

    public void retraccion (double POWER){
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setPower(POWER);
    }

    public void mantener (){
        motor.setTargetPosition(motor.getCurrentPosition());
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(1);
    }


}