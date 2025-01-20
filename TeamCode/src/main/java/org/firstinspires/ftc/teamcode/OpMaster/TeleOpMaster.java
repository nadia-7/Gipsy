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

@Override
    public void runOpMode() {
    Mecanismos robot = new Mecanismos();
    SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
    drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    robot.init(hardwareMap);

    boolean tope = true;
    boolean topeB = true;

    waitForStart();

        while (!isStopRequested()) {
            // ====ELEVADOR====
            if (gamepad1.back) { //DESACTIVAR TOPE
                tope = false;
            }else if (gamepad1.start && !tope){ //ACTIVAR TOPE Y RESETEAR ENCODER
                robot.stopResetEconder(robot.elevador1, robot.elevador2);
                tope = true;
            }else if (tope && gamepad1.right_bumper && robot.elevador1.getCurrentPosition() > robot.eletopeSuperior && robot.elevador2.getCurrentPosition() > robot.eletopeSuperior) {
                robot.subirElevador(0.9);
            }else if (tope && gamepad1.left_bumper && robot.elevador1.getCurrentPosition() < robot.eletopeInferior && robot.elevador2.getCurrentPosition() < robot.eletopeInferior) {
                robot.bajarElevador(0.7);
            }else if(!tope && gamepad1.right_bumper){
                robot.subirElevador(0.9);
            }else if(!tope && gamepad1.left_bumper){
                robot.bajarElevador(0.7);
            }else {
                robot.mantenerElevador();
            }

            // ====BARREDORA====
                ///EXTENSION
                if(gamepad2.left_trigger > 0.3 && gamepad2.right_stick_button){
                    robot.extensionBarredora(0.1);
                }else if (gamepad2.left_trigger > 0.3 && gamepad2.left_stick_button){
                    robot.retraccionBarredora(0.1);
                }else if (gamepad2.back) { //DESACTIVAR TOPE
                    topeB = false;
                }else if (gamepad2.start && !topeB){ //ACTIVAR TOPE Y RESETEAR ENCODER
                    robot.stopResetEconder(robot.correderaBarredora);
                    topeB = true;
                }else if(topeB && gamepad2.right_trigger > 0.3 && robot.correderaBarredora.getCurrentPosition() > -650){
                    robot.extensionBarredora(0.8);
                }else if (topeB && gamepad2.left_trigger > 0.3 && robot.correderaBarredora.getCurrentPosition() < 0){
                    robot.retraccionBarredora(0.8);
                }else if(!topeB && gamepad2.right_trigger > 0.3){
                    robot.extensionBarredora(0.8);
                }else if(!topeB && gamepad2.left_trigger > 0.3){
                    robot.retraccionBarredora(0.8);
                }else{
                    robot.mantenerBarredora();
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

            // ====GARRA====
                ///COMBO AGARRAR SAMPLE BARREDORA AUTO
                if (gamepad2.left_bumper){
                    robot.autoTomarSample();
                    robot.cerrarGarra();
                }

                ///COMBO ACOMODAR GARRA DEJAR SAMPLE
                if (gamepad2.right_bumper){
                    robot.autoDejarSample();
                }

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
