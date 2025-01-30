package org.firstinspires.ftc.teamcode.OpModes.Test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.OpModes.Mecanismos;

@TeleOp

public class BarredoraTest extends LinearOpMode {

    //TODO: ACOMODAR LA BARREDORA FÍSICAMENTE
    boolean topeB = true;

    public void runOpMode() {
        Mecanismos robot = new Mecanismos();

        robot.correderaBarredora = hardwareMap.get(DcMotor.class, "correderaBarredora");
        robot.ingesta = hardwareMap.get(DcMotor.class, "ingesta");
        robot.servoAriculacionBarredora = hardwareMap.get(Servo.class, "bArtIzq");

        robot.stopResetEconder(robot.correderaBarredora);
        robot.runUsingEncoder(robot.correderaBarredora);
        robot.runWithoutEncoder(robot.ingesta);

        waitForStart();

        while (!isStopRequested()) {
            //_ EXTENSION
        /*if(gamepad1.right_bumper && gamepad2.right_stick_button){
            robot.extensionBarredora(0.1);
        }else if (gamepad1.left_bumper && gamepad2.left_stick_button){
            robot.retraccionBarredora(0.1);
        }else if (gamepad1.back) { //DESACTIVAR TOPE
            topeB = false;
        }else if (gamepad1.start && !topeB){ //ACTIVAR TOPE Y RESETEAR ENCODER
            robot.stopResetEconder(robot.correderaBarredora);
            topeB = true;
        }else if(topeB && gamepad1.right_bumper && robot.correderaBarredora.getCurrentPosition() > -650){
            robot.extensionBarredora(0.8);
        }else if (topeB && gamepad1.left_bumper && robot.correderaBarredora.getCurrentPosition() < 0){
            robot.retraccionBarredora(0.6);
        }else */
            if (/*!topeB && */gamepad1.right_bumper) {
                robot.extensionBarredora(0.6);
            } else if (/*!topeB && */gamepad1.left_bumper) {
                robot.retraccionBarredora(0.6);
            } else {
                robot.mantenerBarredora();
            }


            //_ INGESTA
            if (gamepad1.a) {//introducir
                robot.ingesta.setPower(-1.0);
            } else if (gamepad1.x) {
                robot.ingesta.setPower(1.0);
            } else {
                robot.ingesta.setPower(0.0);
            }

            //_ ARTICULACION BARREDORA
            if (gamepad1.y) { //subir
                robot.subirArticulacionBarredora();
            } else if (gamepad1.b) { //bajar
                robot.bajarArticulacionBarredora();
            }


            telemetry.addData("Extension Barredora", robot.correderaBarredora.getCurrentPosition());
            telemetry.addLine("");

            telemetry.addData("Articulación barredora", robot.servoAriculacionBarredora.getPosition());
            telemetry.addLine("");
            telemetry.update();

        }
    }


}
