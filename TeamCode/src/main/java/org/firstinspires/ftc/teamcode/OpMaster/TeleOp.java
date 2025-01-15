package org.firstinspires.ftc.teamcode.OpMaster;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

public class TeleOp extends LinearOpMode {


@Override
    public void runOpMode() {
    Mecanismos robot = new Mecanismos(hardwareMap);
    SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

    drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    // ELEVADOR INIT
    robot.initElevador();

    waitForStart();

        while (opModeIsActive()) {

            drive.setWeightedDrivePower(
                    new Pose2d(
                            -gamepad1.left_stick_y,
                            -gamepad1.left_stick_x,
                            -gamepad1.right_stick_x
                    )
            );

            drive.update();

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
