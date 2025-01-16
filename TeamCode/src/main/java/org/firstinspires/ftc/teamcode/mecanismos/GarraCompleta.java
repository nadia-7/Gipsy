package org.firstinspires.ftc.teamcode.mecanismos;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

/*
* Corredera Garra [Y] [A]
* Brazo [left stick y]
* Articulacion [right stick y]
* Garra [x]
*
* ELEVADOR [right bumper] [left bumper]
*/
public class GarraCompleta extends LinearOpMode {
    //       <<Declarar webadas>>
    public Servo CorrederaGarra;
    public Servo CorrederaGarra2;
    public Servo servo_Brazo1;
    public Servo servo_Brazo2;
    public Servo ArticulacionGarra;
    public Servo servo_Garra;
    public DcMotor elevador1 = null;
    public DcMotor elevador2 = null;

    //           <<Variables>>
    //BrazoGarra
    double servoBPosition1 = 0.4; //Pos inicial del Brazo de la garra
    double servoBPosition2 = 1.0; //Pos inicial del Brazo de la garra
    double Bincrement = 0.005; //Valor de vel del Brazo de la garra
    //Articulacion Garra
    double servoPositionH = 0.5; // Initial servo position (adjust as needed)
    double Aincrement = 0.005; //Valor de vel de la Articulacion de la garra
    //Garra
    public boolean garra_abierta = false;
    int topeSuperior= -3200;
    int topeInferior= -100;


    public void runOpMode(){
        initGarra();
        initElevador();
        waitForStart();

        while (opModeIsActive()){
            //                              CORREDERA
            //Corredera Enfrente
            if (gamepad1.y){
                moverCorredera(0.45, 0.55);
            } else if (gamepad1.a) {
                moverCorredera(0.0,1.0);
            }
//                                          BRAZO
            // MOVER BRAZO FRENTE
            if (gamepad1.left_stick_y > 0.3) {
                servoBPosition1 = Math.min(servoBPosition1 + Bincrement, 1.0);
                servoBPosition2 = Math.max(servoBPosition2 - Bincrement, 0.4);
                moverBrazo(servoBPosition1, servoBPosition2);
            }
            // MOVER BRAZO ATRÁS
            else if (gamepad1.left_stick_y < -0.3) {
                servoBPosition1 = Math.max(servoBPosition1 - Bincrement, 0.4);
                servoBPosition2 = Math.min(servoBPosition2 + Bincrement, 1.0);
                moverBrazo(servoBPosition1, servoBPosition2);
            }
            if (gamepad1.left_stick_button){    //congelar Brazo
                servoBPosition1 = servo_Brazo1.getPosition();
                servoBPosition2 = servo_Brazo2.getPosition();
                moverBrazo(servoBPosition1,servoBPosition2);
            }
            //                                                                  ARTICULACION
            if (gamepad1.right_stick_y > 0.3) {   // GARRA MANO FRENTE
                servoPositionH = Math.min(servoPositionH + Aincrement, 1.0);
                moverAGarra(servoPositionH);
            }
            else if (gamepad1.right_stick_y < -0.3) {    // GARRA MANO ATRAS
                servoPositionH = Math.max(servoPositionH - Aincrement, 0.0);
                moverAGarra(servoPositionH);
            }
            if (gamepad1.right_stick_button) {
                moverAGarra(ArticulacionGarra.getPosition());
            }
            //                                                                  GARRA
            if (gamepad1.x && !garra_abierta) {  //abrir garra
                servo_Garra.setPosition(0.5);
                garra_abierta = true;
            }
            if (gamepad1.x && garra_abierta) {  //cerrar garra
                servo_Garra.setPosition(0);
                garra_abierta = false;
            }
            //                                                              ELEVADOR
            if(gamepad1.right_bumper && elevador1.getCurrentPosition()>topeSuperior && elevador2.getCurrentPosition()>topeSuperior){
                subirElevador(0.9);
            }else if (gamepad1.left_bumper && elevador1.getCurrentPosition()<topeInferior && elevador2.getCurrentPosition()<topeInferior){
                bajarElevador(0.9);
            }else {
                mantenerElevador();
            }

            // Telemetry de la corredera
            telemetry.addData("CorrederaG1 Pos= ", CorrederaGarra.getPosition());
            telemetry.addData("CorrederaG2 Pos= ", CorrederaGarra2.getPosition());
            // telemetría Brazo
            telemetry.addData("B1 pos=", servo_Brazo1.getPosition());
            telemetry.addData("B2 pos=", servo_Brazo2.getPosition());
            // Telemetry Articulacion
            telemetry.addData("ArticulacionG Pos=", servoPositionH);
            //telemetry Garra
            telemetry.addData("GarraAbierta", garra_abierta);
            telemetry.update();
        }
    }
    public void initGarra(){
        CorrederaGarra = hardwareMap.get(Servo.class, "Corredera1");//  1Exp
        CorrederaGarra2 = hardwareMap.get(Servo.class, "Corredera2");// 1 ctrl
        servo_Brazo1 = hardwareMap.get(Servo.class, "brazo1");//  3 Exp
        servo_Brazo2 = hardwareMap.get(Servo.class, "brazo2");// 0 cntrl
        ArticulacionGarra = hardwareMap.get(Servo.class, "hand");// 2 cntl
        servo_Garra = hardwareMap.get(Servo.class, "garra");//  2 Exp
        ArticulacionGarra.setPosition(0.5);
        telemetry.addLine("Garra iniciada");
        telemetry.addLine("Garra iniciada");
    }

    public void moverCorredera(double POS, double POS2){
        CorrederaGarra.setPosition(POS); // aumenta
        CorrederaGarra2.setPosition(POS2); // disminuye
    }
    public void moverBrazo(double POS1, double POS2) {
        servo_Brazo1.setPosition(POS1);
        servo_Brazo2.setPosition(POS2);
    }
    public void moverAGarra(double POS){
        ArticulacionGarra.setPosition(POS);
    }
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
