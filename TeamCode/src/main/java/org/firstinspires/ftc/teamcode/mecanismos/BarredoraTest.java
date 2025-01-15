package org.firstinspires.ftc.teamcode.mecanismos;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

//104 grados tope
//-78 grados recoger un sample
@TeleOp
public class BarredoraTest extends LinearOpMode {
    DcMotor motor;
    //public Servo Articulacion_Barredora1;
    // public Servo Articulacion_Barredora2;
    //DcMotor Barredora;
    public int pos = 1;
    public boolean press1;
    private boolean press2;
    public void initBarredora() {
        motor = hardwareMap.get(DcMotor.class, "correderaBarredora");
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setPower(0);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //motor.setTargetPosition(degreesToTicks(0));
        //motor.setPower(0.6);
        telemetry.addLine("Barredora iniciada");
        telemetry.update();
    }



    @Override
    public void runOpMode() {
        initBarredora();

        waitForStart();

        while (opModeIsActive()) {
            //Corredera
            if (gamepad1.dpad_up){
                extension(1);
            } else extension(0);

            //Articulacion
/*           if (gamepad1.dpad_up){
                Articulacion(0.5,0.5);
            }
            if (gamepad1.dpad_down){
               Articulacion(0.0,1.0);
            }*/

            telemetry.addData("POSICION EXTENSION", motor.getCurrentPosition());
            telemetry.update();

/*           if (gamepad1.a){
                Barredora.setPower(1.0);
           } else if (gamepad1.x){
              Barredora.setPower(-1.0);
            } else{
               Barredora.setPower(0.0);
          }*/

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
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }


}