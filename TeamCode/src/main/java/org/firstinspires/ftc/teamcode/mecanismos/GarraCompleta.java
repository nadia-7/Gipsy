package org.firstinspires.ftc.teamcode.mecanismos;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

public class GarraCompleta extends LinearOpMode {
    //      <<Declarar webadas>>
    public Servo CorrederaGarra;
    public Servo CorrederaGarra2;
    public Servo servo_Brazo1;
    public Servo servo_Brazo2;
    public Servo ArticulacionGarra;
    public Servo servo_Garra;

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


    public void runOpMode(){
        initGarra();
        waitForStart();

        while (opModeIsActive()){
            //Corredera Enfrente
            if (gamepad1.y){
                moverCorredera(0.5, 0.5);
            }
            //Corredera Atras
            else if (gamepad1.a) {
                moverCorredera(0.0,1.0);
            }

            // MOVER BRAZO FRENTE
            if (gamepad1.left_stick_y > 0) {
                servoBPosition1 = Math.min(servoBPosition1 + Bincrement, 1.0);
                servoBPosition2 = Math.max(servoBPosition2 - Bincrement, 0.4);
                moverBrazo(servoBPosition1, servoBPosition2);
            }
            // MOVER BRAZO ATRÁS
            else if (gamepad1.left_stick_y < 0) {
                servoBPosition1 = Math.max(servoBPosition1 - Bincrement, 0.4);
                servoBPosition2 = Math.min(servoBPosition2 + Bincrement, 1.0);
                moverBrazo(servoBPosition1, servoBPosition2);
            }
            if (gamepad1.left_stick_button){    //congelar Brazo
                servoBPosition1 = servo_Brazo1.getPosition();
                servoBPosition2 = servo_Brazo2.getPosition();
                moverBrazo(servoBPosition1,servoBPosition2);
            }
            if (gamepad1.right_stick_y > 0) {   // GARRA MANO FRENTE
                servoPositionH = Math.min(servoPositionH + Aincrement, 1.0);
                moverAGarra(servoPositionH);
            }
            else if (gamepad1.right_stick_y < 0) {    // GARRA MANO ATRAS
                servoPositionH = Math.max(servoPositionH - Aincrement, 0.0);
                moverAGarra(servoPositionH);
            }
            if (gamepad1.right_stick_button) {
                moverAGarra(ArticulacionGarra.getPosition());
            }
            //Garra
            if (gamepad1.x && !garra_abierta) {  //abrir garra
                servo_Garra.setPosition(0.5);
                garra_abierta = true;
            }
            if (gamepad1.x && garra_abierta) {  //cerrar garra
                servo_Garra.setPosition(0);
                garra_abierta = false;
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
        CorrederaGarra = hardwareMap.get(Servo.class, "Corredera1");
        CorrederaGarra2 = hardwareMap.get(Servo.class, "Corredera2");
        servo_Brazo1 = hardwareMap.get(Servo.class, "brazo1");
        servo_Brazo2 = hardwareMap.get(Servo.class, "brazo2");
        ArticulacionGarra = hardwareMap.get(Servo.class, "hand");
        servo_Garra = hardwareMap.get(Servo.class, "garra");
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

}
