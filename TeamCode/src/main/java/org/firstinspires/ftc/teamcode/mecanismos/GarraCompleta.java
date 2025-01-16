package org.firstinspires.ftc.teamcode.mecanismos;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

/*
corredera iniciarr g1 pos 0 g2 pos 1
 */
/*
* Corredera Garra [Y] [A]
* Brazo [left stick y]
* Articulacion [right stick y]
* Garra [x]
*
* ELEVADOR [right bumper] [left bumper]
*/

@TeleOp
public class GarraCompleta extends LinearOpMode {
    //       <<Declarar webadas>>
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
    int topeSuperior= -3200;
    int topeInferior= -100;


    public void runOpMode(){
        initGarra();
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
        CorrederaGarra2 = hardwareMap.get(Servo.class, "Corredera2");// 3 ctrl
        servo_Brazo1 = hardwareMap.get(Servo.class, "brazo1");//  2 Exp
        servo_Brazo2 = hardwareMap.get(Servo.class, "brazo2");// 0 cntrl
        ArticulacionGarra = hardwareMap.get(Servo.class, "hand");// 1 cntl
        servo_Garra = hardwareMap.get(Servo.class, "garra");//  3 Exp
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
