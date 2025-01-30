package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
@TeleOp
public class TeleOpMaster extends LinearOpMode {

@Override
    public void runOpMode() {
    Mecanismos robot = new Mecanismos();
    SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
    drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    robot.init(hardwareMap);
//3320
    boolean topeE = true;
    boolean topeB = true;

    int posBarr = 0;

    waitForStart();

        while (!isStopRequested()) {

//_ == E L E V A D O R ==
            if (gamepad2.back) { //Note: DESACTIVAR TOPE
                topeE = false;
            }else if (gamepad2.start && !topeE){ //Note: ACTIVAR TOPE Y RESETEAR ENCODER
                robot.stopResetEconder(robot.elevador1, robot.elevador2);
                topeE = true;
            }else if (topeE && gamepad2.right_bumper && robot.elevador1.getCurrentPosition() > robot.eletopeSuperior && robot.elevador2.getCurrentPosition() > robot.eletopeSuperior) {
                robot.subirElevador(0.9);
            }else if (topeE && gamepad2.left_bumper && robot.elevador1.getCurrentPosition() < robot.eletopeInferior && robot.elevador2.getCurrentPosition() < robot.eletopeInferior) {
                robot.bajarElevador(0.7);
            }else if(!topeE && gamepad2.right_bumper){
                robot.subirElevador(0.5);
            }else if(!topeE && gamepad2.left_bumper){
                robot.bajarElevador(0.5);
            }else {
                robot.mantenerElevador();
            }

//_ == B A R R E D O R A ==
        //_ EXTENSION
            if (gamepad1.back) { //Note: DESACTIVAR TOPE
                topeB = false;
            }else if (gamepad1.start && !topeB){ //Note: ACTIVAR TOPE Y RESETEAR ENCODER
                robot.stopResetEconder(robot.correderaBarredora);
                topeB = true;
            }else if(topeB && gamepad1.right_bumper && robot.correderaBarredora.getCurrentPosition() > -650){
                robot.extensionBarredora(0.8);
            }else if (topeB && gamepad1.left_bumper && robot.correderaBarredora.getCurrentPosition() < 0){
                robot.retraccionBarredora(0.5);
            }else if(!topeB && gamepad1.right_bumper){
                robot.extensionBarredora(0.8);
            }else if(!topeB && gamepad1.left_bumper){
                robot.retraccionBarredora(0.5);
            }else{
                robot.mantenerBarredora();
            }

        //_ INGESTA
                if (gamepad1.a){//Note: introducir sample
                    robot.ingesta.setPower(-1.0);
                } else if (gamepad1.x){ //Note: Sacar Sample
                    robot.ingesta.setPower(1.0);
                } else{
                    robot.ingesta.setPower(0.0);
                }

        //_ ARTICULACION BARREDORA
                if (gamepad1.y){//Note: subir barredoraa
                    robot.subirArticulacionBarredora();
                } else if (gamepad1.b) {//Note: bajar bareredora
                    robot.bajarArticulacionBarredora();
                }

//_ == G A R R A ==
        //_ AUTOMATICO
                if (gamepad2.right_trigger > 0.2){ //Note: Tomar sample contenedor
                    robot.autoTomarSampleContenedor();
                    robot.cerrarGarra();
                }

                if (gamepad2.left_trigger > 0.2){ //Note: Dejar sample
                    robot.autoDejarSampleCanastaChamber();
                }

        //_ BRAZO GARRA
            //TODO: REVISAR TOPES
                if (gamepad2.dpad_up /*&& robot.servoBrazo1.getPosition() >= robot.topeFrontBrazoIzq && robot.servoBrazo2.getPosition() <= robot.topeFrontBrazoDer*/) {
                    robot.brazoEnfrente(robot.servoBrazo1.getPosition(),robot.servoBrazo2.getPosition() );
                } else if (gamepad2.dpad_down /*&& robot.servoBrazo1.getPosition() <= robot.topeAtrasBrazoIzq && robot.servoBrazo2.getPosition() >= robot.topeAtrasBrazoDer*/) {
                    robot.brazoAtras(robot.servoBrazo1.getPosition(),robot.servoBrazo2.getPosition());
                }

        //_ ARTICULACIÓN GARRA
                if (-gamepad2.right_stick_y > 0.3) { //NOTE: GARRA MANO FRENTE
                    robot.garraArticulacionFront(robot.servoArticulacionGarra.getPosition());
                    robot.mantenerBrazo();
                }else if (-gamepad2.right_stick_y < -0.3) { //NOTE: GARRA MANO ATRAS
                    robot.garraArticulacionAtras(robot.servoArticulacionGarra.getPosition());
                    robot.mantenerBrazo();
                }

        //_ GARRA
                if (gamepad2.x){
                    robot.cerrarGarra();
                } else if (gamepad2.b) {
                    robot.abrirGarra();
                }


//_ == C H A S I S ==
            drive.setWeightedDrivePower(
                    new Pose2d(
                            -gamepad1.left_stick_y,
                            -gamepad1.left_stick_x,
                            -gamepad1.right_stick_x
                    )
            );
            drive.update();


//_ == T E L E M E T R Y ==
            telemetry.addData("Elevador Izq", robot.elevador1.getCurrentPosition());
            telemetry.addData("Elevador Der", robot.elevador2.getCurrentPosition());
            telemetry.addLine("");

            telemetry.addData("Extension Barredora", robot.correderaBarredora.getCurrentPosition());
            telemetry.addLine("");

            telemetry.addData("Articulación barredora", robot.servoAriculacionBarredora.getPosition());
            telemetry.addLine("");

            telemetry.addData("Brazo Izq", robot.servoBrazo1.getPosition());
            telemetry.addData("Brazo Der", robot.servoBrazo2.getPosition());
            telemetry.addLine("");

            telemetry.addData("Articulacion Garra Posicion", robot.servoArticulacionGarra.getPosition());
            telemetry.addLine("");

            if (robot.servoGarra.getPosition() == 0.2){
                telemetry.addLine("Garra abierta");
            } else telemetry.addLine("Garra cerrada");
            telemetry.addData("Garra", robot.servoGarra.getPosition());
            telemetry.addData("Giro Garra", robot.servoRotacion.getPosition());
            telemetry.addLine("");

            telemetry.update();

        }

    }
}
