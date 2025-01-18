package org.firstinspires.ftc.teamcode.Autonomo;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

//atras der enfrente atras
//Enfrente izq lados
public class Chasis {
    DcMotor enfrenteDer;
    DcMotor enfrenteIzq;
    DcMotor atrasDer;
    DcMotor atrasIzq;


    public void init(HardwareMap hardwareMap){
        enfrenteIzq = hardwareMap.get(DcMotorEx.class, "enfrenteIzq");
        atrasIzq = hardwareMap.get(DcMotorEx.class, "atrasIzq");
        atrasDer = hardwareMap.get(DcMotorEx.class, "atrasDer");
        enfrenteDer = hardwareMap.get(DcMotorEx.class, "enfrenteDer");


        stopResetEconder(enfrenteIzq, atrasDer);

    }

    public void moverseEnfrente(double potencia){
        enfrenteDer.setPower(potencia);
        enfrenteIzq.setPower(potencia);
        atrasDer.setPower(potencia);
        atrasIzq.setPower(potencia);
    }


    public void moverseAtras(double potencia){
        moverseEnfrente(-potencia);
    }

    public void girarIzquierda(double potencia){
        girarDerecha(-potencia);
    }

    public void moverseDerecha(double potencia){
        enfrenteDer.setPower(-potencia);
        enfrenteIzq.setPower(potencia);
        atrasDer.setPower(potencia);
        atrasIzq.setPower(-potencia);
    }

    public void moverseIzquierda(double potencia){
        moverseDerecha(-potencia);
    }

    public void girarDerecha(double potencia){
        enfrenteDer.setPower(-potencia);
        enfrenteIzq.setPower(potencia);
        atrasDer.setPower(-potencia);
        atrasIzq.setPower(potencia);
    }


    public void runUsingEncoder(DcMotor... motores) {
        for (DcMotor motor : motores) {
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }
    public void runWithoutEncoder(DcMotor... motores) {
        for (DcMotor motor : motores) {
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }
    public void stopResetEconder(DcMotor... motores) {
        for (DcMotor motor : motores) {
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
    }
    public void runToPosition(DcMotor... motores) {
        for (DcMotor motor : motores) {
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
    }
    public void zeroPowerBrake(DcMotor... motores){
        for(DcMotor motor : motores){
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
    }
    public void zeroPower(DcMotor... motores){
        for(DcMotor motor : motores){
            motor.setPower(0);
        }
    }
    public void derecho(DcMotor... motores) {
        for (DcMotor motor : motores) {
            motor.setDirection(DcMotor.Direction.FORWARD);
        }
    }
    public void reversa(DcMotor... motores) {
        for (DcMotor motor : motores) {
            motor.setDirection(DcMotor.Direction.REVERSE);
        }
    }



}
