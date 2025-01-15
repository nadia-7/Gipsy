package org.firstinspires.ftc.teamcode.OpMaster;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class TeleOp extends LinearOpMode {


@Override
    public void runOpMode() {
    Mecanismos robot = new Mecanismos(hardwareMap);
    // ELEVADOR INIT
    robot.initElevador();

        while (opModeIsActive()) {
            // ====ELEVADOR====
            if(gamepad1.right_trigger != 0){  //mover Elevador Arriba
                robot.elevadorEnfrente(gamepad1.right_trigger);
            }
            if(gamepad1.left_trigger != 0){  //mover Elevador Abajo
                robot.elevadorAtras(gamepad1.left_trigger);
            }
            robot.elevador1.setPower(0);
            robot.elevador2.setPower(0);

        }
    }
}
