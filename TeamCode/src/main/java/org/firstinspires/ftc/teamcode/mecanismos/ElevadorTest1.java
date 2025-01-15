package org.firstinspires.ftc.teamcode.mecanismos; // ELEVADOR CON TRIGGERS

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.OpMaster.Mecanismos;

@TeleOp( name = "Elevapapu", group = "ugu")
public class ElevadorTest1 extends LinearOpMode {
    public DcMotor elevador1 = null;
    public DcMotor elevador2 = null;

    int topeSuperior= -3200;
    int topeInferior= -100;


//    private static final double TICKS_PER_REVOLUTION = 8192;   //537.7
//    int elevatorTolerance = 50;

    public void initElevador(){
        elevador1 = hardwareMap.get(DcMotor.class,"elevador1");
        elevador2 = hardwareMap.get(DcMotor.class,"elevador2");

        elevador1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elevador2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        elevador1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        elevador2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        elevador2.setDirection(DcMotorSimple.Direction.REVERSE);

        elevador1.setPower(0);
        elevador2.setPower(0);

        elevador1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        elevador2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        telemetry.addLine("Elevadores iniciados");
    }

    @Override
    public void runOpMode() {
        initElevador();
        waitForStart();

        while (opModeIsActive()){
            //float fr = gamepad1.right_trigger;
            //float fl = gamepad1.left_trigger;

            //String sr = Float.toString(fr);
            //String sl = Float.toString(fl);

            //double RightTriggerPos = Double.parseDouble(sr);
            //double LeftTriggerPos = Double.parseDouble(sl);

            //ELEVADOR
//            if(gamepad1.right_trigger > 0){  //mover Elevador Arriba
//                subirElevador(1);
//            }
//            if(gamepad1.left_trigger > 0){  //mover Elevador Abajo
//                bajarElevador(1);
//            }

            if(gamepad1.a && elevador1.getCurrentPosition()>topeSuperior && elevador2.getCurrentPosition()>topeSuperior){
                subirElevador(0.9);
            }else if (gamepad1.b && elevador1.getCurrentPosition()<topeInferior && elevador2.getCurrentPosition()<topeInferior){
                bajarElevador(0.9);
            }else {
                mantenerElevador();
            }

            telemetry.addData("m1", elevador1.getCurrentPosition());
            telemetry.addData("m2", elevador2.getCurrentPosition());
            telemetry.update();

        }
    }


    public void subirElevador(double POWER){

        elevador1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        elevador2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        elevador1.setPower(-POWER);
        elevador2.setPower(-POWER);
    }
    public void bajarElevador(double POWER){

        elevador1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        elevador2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        elevador1.setPower(POWER);
        elevador2.setPower(POWER);
    }

    public void mantenerElevador(){
        elevador1.setTargetPosition(elevador1.getCurrentPosition());
        elevador2.setTargetPosition(elevador1.getCurrentPosition());

        elevador1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elevador2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        elevador1.setPower(1);
        elevador2.setPower(1);
    }
}
