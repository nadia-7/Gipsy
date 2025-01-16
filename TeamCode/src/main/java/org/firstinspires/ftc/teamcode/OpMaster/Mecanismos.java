package org.firstinspires.ftc.teamcode.OpMaster;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import org.firstinspires.ftc.robotcore.external.Telemetry;


public class Mecanismos {

    //ELEVADOR
    public DcMotor elevador1; //0 expansion
    public DcMotor elevador2; //3 control
    public int eletopeSuperior= -3200;
    public int eletopeInferior= 0;

    //BARREDORA
    public DcMotor correderaBarredora; //2 control
    public DcMotor ingesta; //3 expansion
    //public Servo barrArtDer; //3 control DESCONECTADO
    public Servo barrArtIzq; //0 expansion

    //GARRA
    public Servo correderaGarra; //1 expansion
    public Servo correderaGarra2; //3 control
    public Servo servoBrazo1; //2 expansion
    public Servo servoBrazo2; //0 control
    public Servo articulacionGarra; //1 control
    public Servo servoGarra; //3 control

    double servoPosicionHand = 0.5;
    double handIncremento =0.05;
    double brazoPos1 =0.15;
    double brazoPos2 =1;
    double brazoIncremento = 0.03;




    public void init(HardwareMap hardwareMap){

        ///ELEVADOR
        elevador1 = hardwareMap.get(DcMotor.class,"elevador1");
        elevador2 = hardwareMap.get(DcMotor.class,"elevador2");

        ///BARREDORA
        correderaBarredora = hardwareMap.get(DcMotor.class, "correderaBarredora");
        ingesta =hardwareMap.get(DcMotor.class, "ingesta");
        //barrArtDer = hardwareMap.get(Servo.class, "bArtDer");
        barrArtIzq = hardwareMap.get(Servo.class, "bArtIzq");

        ///GARRA
        correderaGarra = hardwareMap.get(Servo.class, "Corredera1");
        correderaGarra2 = hardwareMap.get(Servo.class, "Corredera2");
        servoBrazo1 = hardwareMap.get(Servo.class, "brazo1");
        servoBrazo2 = hardwareMap.get(Servo.class, "brazo2");
        articulacionGarra = hardwareMap.get(Servo.class, "hand");
        servoGarra = hardwareMap.get(Servo.class, "garra");

        correderaGarra.setPosition(0.9); //1
        correderaGarra2.setPosition(0.1); //0

        servoGarra.setPosition(0);
        //servoBrazo1.setPosition(1);
        //servoBrazo2.setPosition(0.15);

        stopResetEconder(elevador1, elevador2, correderaBarredora);
        runUsingEncoder(elevador1, elevador2, correderaBarredora);
        runWithoutEncoder(ingesta);

        elevador2.setDirection(DcMotorSimple.Direction.REVERSE);

        zeroPower(elevador1, elevador2);
        zeroPowerBrake(elevador1, elevador2);

    }

    //ELEVADOR
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

    //¿se podria cambiar por brake?
    public void mantenerElevador(){
        elevador1.setTargetPosition(elevador1.getCurrentPosition());
        elevador2.setTargetPosition(elevador1.getCurrentPosition());

        elevador1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elevador2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        elevador1.setPower(1);
        elevador2.setPower(1);


    }

    //BARREDORA
    public void extensionBarredora (double POWER){
        correderaBarredora.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        correderaBarredora.setPower(-POWER); //-1 ES PARA EXTENDERSE
    }
    public void retraccionBarredora (double POWER){
        correderaBarredora.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        correderaBarredora.setPower(POWER); //+1 es para retraerse
    }
    public void mantenerBarredora (){
        correderaBarredora.setTargetPosition(correderaBarredora.getCurrentPosition());
        correderaBarredora.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        correderaBarredora.setPower(1);
    }

    //GARRA
    public void moverArtGarra(double POS){
        articulacionGarra.setPosition(POS);
    }
    public void moverBrazo(double POS1, double POS2) {
        servoBrazo1.setPosition(POS1);
        servoBrazo2.setPosition(POS2);
    }


    //MOTORES
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

}

