package org.firstinspires.ftc.teamcode.OpMaster;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;


public class Mecanismos {
    public Mecanismos(HardwareMap hardwareMap){
        elevador1 = hardwareMap.get(DcMotor.class,"elevador1");
        elevador2 = hardwareMap.get(DcMotor.class,"elevador2");
    }
    public DcMotor elevador1;
    public DcMotor elevador2;

    public void initElevador(){
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

