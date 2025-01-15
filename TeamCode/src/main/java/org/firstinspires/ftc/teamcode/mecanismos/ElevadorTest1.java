package org.firstinspires.ftc.teamcode.mecanismos; // ELEVADOR CON TRIGGERS

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.OpMaster.Mecanismos;

@TeleOp
public class ElevadorTest1 extends LinearOpMode {
    public DcMotor elevador1 = null;
    public DcMotor elevador2 = null;
//    private static final double TICKS_PER_REVOLUTION = 8192;   //537.7
//    int elevatorTolerance = 50;
    @Override
    public void runOpMode() {
        initElevador();
        waitForStart();

        while (opModeIsActive()){
            float fr = gamepad1.right_trigger;
            float fl = gamepad1.left_trigger;

            String sr = Float.toString(fr);
            String sl = Float.toString(fl);

            double RightTriggerPos = Double.parseDouble(sr);
            double LeftTriggerPos = Double.parseDouble(sl);

            //ELEVADOR
            if(gamepad1.right_trigger > 0){  //mover Elevador Arriba
                elevadorEnfrente(RightTriggerPos);
            }
            if(gamepad1.left_trigger > 0){  //mover Elevador Abajo
                elevadorAtras(LeftTriggerPos);
            }
            elevador1.setPower(0);
            elevador2.setPower(0);
        }
    }


    public void initElevador(){
        elevador1 = hardwareMap.get(DcMotor.class,"elevador1");
        elevador2 = hardwareMap.get(DcMotor.class,"elevador2");
//        elevador1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        elevador2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elevador1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        elevador2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        elevador2.setDirection(DcMotorSimple.Direction.REVERSE);
        elevador1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        elevador2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        telemetry.addLine("Elevadores iniciados");
    }
    public void elevadorEnfrente(double POWER){
        elevador1.setPower(POWER);
        elevador2.setPower(POWER);
    }
    public void elevadorAtras(double POWER){
        elevador1.setPower(-POWER);
        elevador2.setPower(-POWER);
    }
}
