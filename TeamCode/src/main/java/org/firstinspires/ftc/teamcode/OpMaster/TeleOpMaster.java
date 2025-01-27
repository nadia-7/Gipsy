package org.firstinspires.ftc.teamcode.OpMaster;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
@TeleOp
public class TeleOpMaster extends LinearOpMode {
/*
 ===GPAD 1===
 -[a] = TRAGAR
 -[x] = ESCUPIR
 -[b] = BAREDORA ↓
 -[y] = BARREDORA ↑
 -[Bum_L] = BARREDORA ↓
 -[Bum_R] = BARREDORA ↑
 -[Trig_L] =
 -[Trig_R] =
 -[joy_L] =
 -[joy_R] =
 -[Dpad_↑] =
 -[Dpad_↓] =
 -[Dpad_→] =
 -[Dpad_←] =

 ===GPAD2===
 -[a] =
 -[b] = GARRA ☒
 -[x] = GARRA ☑
 -[y] =
 -[Bum_L] = ELEVADOR ↓
 -[Bum_R] = ELEVADOR ↑
 -[Trig_L] = COMBO ENCESTAR
 -[Trig_R] = COMBO TOMAR SAMPLE
 -[joy_L] =
 -[joy_R] = ARTICULACIÓN GARRA ↑↓
 -[Dpad_↑] = BRAZO GARRA ↑
 -[Dpad_↓] = BRAZO GARRA ↓
 -[Dpad_→] =
 -[Dpad_←] =
 */
@Override
    public void runOpMode() {
    Mecanismos robot = new Mecanismos();
    SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
    drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    robot.init(hardwareMap);

    boolean tope = true;
    boolean topeB = true;

    boolean segundoBarr = false;
    boolean segundoBarrSoltado = false;
    boolean terceroBarrSoltado = false;

    int posBarr = 0;

    waitForStart();

        while (!isStopRequested()) {
            // =========================ELEVADOR===============================
            if (gamepad2.back) { //DESACTIVAR TOPE
                tope = false;
            }else if (gamepad2.start && !tope){ //ACTIVAR TOPE Y RESETEAR ENCODER
                robot.stopResetEconder(robot.elevador1, robot.elevador2);
                tope = true;
            }else if (tope && gamepad2.right_bumper && robot.elevador1.getCurrentPosition() > robot.eletopeSuperior && robot.elevador2.getCurrentPosition() > robot.eletopeSuperior) {
                robot.subirElevador(0.9);
            }else if (tope && gamepad2.left_bumper && robot.elevador1.getCurrentPosition() < robot.eletopeInferior && robot.elevador2.getCurrentPosition() < robot.eletopeInferior) {
                robot.bajarElevador(0.7);
            }else if(!tope && gamepad2.right_bumper){
                robot.subirElevador(0.9);
            }else if(!tope && gamepad2.left_bumper){
                robot.bajarElevador(0.7);
            }else {
                robot.mantenerElevador();
            }

            // ========================BARREDORA=============================
                ///EXTENSION
            if(gamepad1.right_bumper){
                posBarr = 2;
            } else if (gamepad1.left_bumper && !segundoBarr) {
                posBarr = 1;
                segundoBarr = true;
            }
            if(!gamepad1.left_bumper){
                if(segundoBarr && !segundoBarrSoltado){
                    segundoBarrSoltado = true;
                } else if (posBarr == 0) {
                    segundoBarrSoltado = false;
                    segundoBarr = false;
                }
            }
            if(gamepad1.left_bumper && segundoBarr && segundoBarrSoltado){
                posBarr = 0;
            }
            if (gamepad1.back) { //DESACTIVAR TOPE
                topeB = false;
            }else if (gamepad1.start && !topeB){ //ACTIVAR TOPE Y RESETEAR ENCODER
                robot.stopResetEconder(robot.correderaBarredora);
                topeB = true;
            }

            switch (posBarr){
                    case 0:
                        if(topeB && robot.correderaBarredora.getCurrentPosition() < 0){
                            robot.retraccionBarredora(0.7);
                        } else {robot.mantenerBarredora();}
                        break;
                    case 1:
                        if(robot.correderaBarredora.getCurrentPosition() < -401){
                        robot.retraccionBarredora(0.7);
                        } else {robot.mantenerBarredora();}
                        break;
                    case 2:
                        if(topeB && robot.correderaBarredora.getCurrentPosition() > -650){
                            robot.extensionBarredora(0.7);
                        } else {robot.mantenerBarredora();}
                        break;
                }

                ///INGESTA
                if (gamepad1.a){//introducir
                    robot.ingesta.setPower(-1.0);
                } else if (gamepad1.x){
                    robot.ingesta.setPower(1.0);
                } else{
                    robot.ingesta.setPower(0.0);
                }

                ///ARTICULACION BARREDORA
                if (gamepad1.y){ //subir
                    robot.subirArticulacionBarredora();
                } else if (gamepad1.b) { //bajar
                    robot.bajarArticulacionBarredora();
                }

            // ======================GARRA==========================
                ///COMBO AGARRAR SAMPLE BARREDORA AUTO
                if (gamepad2.right_trigger > 0.2){
                    robot.autoTomarSample();
                    robot.cerrarGarra();
                }

                ///COMBO ACOMODAR GARRA ENCESTAR
                if (gamepad2.left_trigger > 0.2){
                    robot.autoEncestar();
                }
                //Hacer puntos
                if (gamepad1.dpad_up && gamepad1.dpad_up &&
                    gamepad1.dpad_down && gamepad1.dpad_down &&
                    gamepad1.dpad_left && gamepad1.dpad_right &&
                    gamepad1.dpad_left &&gamepad1.dpad_right &&
                    gamepad1.b && gamepad1.a){
                robot.hacerPuntos();
                }
                //POS TOMAR SPECIMEN
                //POS COLGARME EL ESPECIMEN



                                                          ///EXTENSION
                                        //                if (gamepad2.y){ //EXTENSION
                                        //                    robot.extenderCorrederaGarra();
                                        //                }else if(gamepad2.a){ //RETRAER
                                        //                    robot.retraerCorrederaGarra();
                                        //                }

                ///BRAZO GARRA
                if (gamepad2.dpad_up && robot.servoBrazo1.getPosition() >= robot.topeFrontBrazoIzq && robot.servoBrazo2.getPosition() <= robot.topeFrontBrazoDer) {
                    robot.brazoEnfrente();
                }
                else if (gamepad2.dpad_down && robot.servoBrazo1.getPosition() <= robot.topeAtrasBrazoIzq && robot.servoBrazo2.getPosition() >= robot.topeAtrasBrazoDer) {
                    robot.brazoAtrás();
                } else if (gamepad2.left_stick_button){
                    robot.moverBrazoMaxEnfrente();
                }

                ///ARTICULACIÓN GARRA
                if(robot.servoBrazo1.getPosition() >= robot.atrasBrazoIzqPos1GarraUp && robot.servoBrazo1.getPosition() <= robot.atrasBrazoIzqPos2GarraUp){
                    robot.moverArtGarra(robot.posicionArtGarraErecto);
                }else if (-gamepad2.right_stick_y > 0.3) {   // GARRA MANO FRENTE
                    robot.garraArticulacionFront();
                    robot.mantenerBrazo();
                }else if (-gamepad2.right_stick_y < -0.3) {    // GARRA MANO ATRAS
                    robot.garraArticulacionAtras();
                    robot.mantenerBrazo();

                }

                ///GARRA
                if (gamepad2.x){
                    robot.cerrarGarra();
                } else if (gamepad2.b) {
                    robot.abrirGarra();
                }


            // ====CHASIS===
            drive.setWeightedDrivePower(
                    new Pose2d(
                            -gamepad1.left_stick_y,
                            -gamepad1.left_stick_x,
                            -gamepad1.right_stick_x
                    )
            );
            drive.update();


            // ====TELEMETRY====
            telemetry.addData("Elevador Izq", robot.elevador1.getCurrentPosition());
            telemetry.addData("Elevador Der", robot.elevador2.getCurrentPosition());
            telemetry.addLine("");

            telemetry.addData("Extension Barredora", robot.correderaBarredora.getCurrentPosition());
            telemetry.addLine("");

            telemetry.addData("Articulación barredora", robot.servoAriculacionBarredora.getPosition());
            telemetry.addLine("");

            //telemetry.addData("Corredera Der", robot.servoCorrederaGarra2.getPosition());
            //telemetry.addData("Corredera Izq", robot.servoCorrederaGarra.getPosition());
           // telemetry.addLine("");

            telemetry.addData("Brazo Izq", robot.servoBrazo1.getPosition());
            telemetry.addData("Brazo Der", robot.servoBrazo2.getPosition());
            telemetry.addLine("");

            telemetry.addData("Articulacion Garra Posicion", robot.servoArticulacionGarra.getPosition());
            telemetry.addLine("");

            if (robot.servoGarra.getPosition() == 0.2){
                telemetry.addLine("Garra abierta");
            } else telemetry.addLine("Garra cerrada");
            telemetry.addData("Garra", robot.servoGarra.getPosition());
            telemetry.addLine("");

            telemetry.update();

        }

    }
}
