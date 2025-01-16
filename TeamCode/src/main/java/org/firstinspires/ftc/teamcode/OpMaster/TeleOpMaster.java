package org.firstinspires.ftc.teamcode.OpMaster;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
@TeleOp
public class TeleOpMaster extends LinearOpMode {

    //TODO: CUANDO SE BAJE LA BARREDORA ACTIVAR LA INGESTA
    //TODO: CUANDO SUBA LA BARREDORA DESACTIVAR INGESTA

    ///
    /// SUBIR ELEVADOR: RIGHT BUMPER 2
    /// BAJAR ELEVADOR: LEFT BUMPER 2
    /// EXTENDER BARREDORA: RIGHT bumper 1
    /// RETRAER BARREDORA: LEFT bumper 1
    ///
    /// subir articulacion barredora: a 1
    /// bajar articukacion barredora: y 1
    ///
    /// EXTENDER CORREDERA GARRA: GAMEPAD 2 Y
    /// RETRAER CORREDERA GARRA: GAMEPAD 2 A
    /// MOVER HAND: RIGHT STICK
    /// MOVER BRAZO: LEFT STICK

@Override
    public void runOpMode() {
    Mecanismos robot = new Mecanismos();
    SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
    drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    robot.init(hardwareMap);

    waitForStart();

        while (!isStopRequested()) {
            // ====ELEVADOR====
            if(gamepad2.right_bumper && robot.elevador1.getCurrentPosition()>robot.eletopeSuperior && robot.elevador2.getCurrentPosition()>robot.eletopeSuperior){
                robot.subirElevador(0.9);
            }else if (gamepad2.left_bumper && robot.elevador1.getCurrentPosition()<robot.eletopeInferior && robot.elevador2.getCurrentPosition()<robot.eletopeInferior){
                robot.bajarElevador(0.9);
            }else {
                robot.mantenerElevador();
            }

            // ====BARREDORA====
                ///EXTENSION
                if(gamepad1.right_bumper && robot.correderaBarredora.getCurrentPosition() > -650){
                    robot.extensionBarredora(0.8);
                }else if (gamepad1.left_bumper && robot.correderaBarredora.getCurrentPosition() < 0){
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
                    robot.barrArtIzq.setPosition(0.5);
                } else if (gamepad1.b) { //bajar
                    robot.barrArtIzq.setPosition(0.8);
                }

            // ====GARRA====
                ///EXTENSION
                if (gamepad2.y){
                    robot.correderaGarra.setPosition(0.6);
                    robot.correderaGarra2.setPosition(0.4);
                }else if(gamepad2.a){
                    robot.correderaGarra.setPosition(0.9);
                    robot.correderaGarra2.setPosition(0.1);
                }

                ///BRAZO GARRA
                // MOVER BRAZO FRENTE
                if (-gamepad2.left_stick_y > 0.3) {
                    robot.brazoPos1 = Math.min(robot.brazoPos1 + robot.brazoIncremento, 1.0);
                    robot.brazoPos2 = Math.max(robot.brazoPos2 - robot.brazoIncremento, 0.15);
                    robot.moverBrazo(robot.brazoPos1, robot.brazoPos2);
                }
                // MOVER BRAZO ATRÁS
                else if (-gamepad2.left_stick_y < -0.3) {
                    robot.brazoPos1 = Math.max(robot.brazoPos1 - robot.brazoIncremento, 0.15);
                    robot.brazoPos2 = Math.min(robot.brazoPos2 + robot.brazoIncremento, 1.0);
                    robot.moverBrazo(robot.brazoPos1, robot.brazoPos2);
                }


                ///ARTICULACIÓN GARRA
                if (-gamepad2.right_stick_y > 0.3) {   // GARRA MANO FRENTE
                    robot.servoPosicionHand = Math.min(robot.servoPosicionHand + robot.handIncremento, 1.0);
                    robot.moverArtGarra(robot.servoPosicionHand);
                }
                else if (-gamepad2.right_stick_y < -0.3) {    // GARRA MANO ATRAS
                    robot.servoPosicionHand = Math.max(robot.servoPosicionHand - robot.handIncremento, 0.0);
                    robot.moverArtGarra(robot.servoPosicionHand);
                }
                if (gamepad2.right_stick_button) {
                    robot.moverArtGarra(robot.servoPosicionHand);
                }


            if (gamepad2.x){
                robot.servoGarra.setPosition(0);
            } else if (gamepad2.b) {
                robot.servoGarra.setPosition(0.2);
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
            telemetry.addData("ELEVADOR 1", robot.elevador1.getCurrentPosition());
            telemetry.addData("ELEVADOR 2", robot.elevador2.getCurrentPosition());
            telemetry.addData("CORREDERA", robot.correderaBarredora.getCurrentPosition());


            telemetry.addData("B1 pos=", robot.servoBrazo1.getPosition());
            telemetry.addData("B2 pos=", robot.servoBrazo2.getPosition());
            telemetry.addData("Garra pos=", robot.servoGarra.getPosition());

            telemetry.update();

        }

    }
}
