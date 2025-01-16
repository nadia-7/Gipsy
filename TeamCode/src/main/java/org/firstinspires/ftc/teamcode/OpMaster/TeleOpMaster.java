package org.firstinspires.ftc.teamcode.OpMaster;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

public class TeleOpMaster extends LinearOpMode {


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
            if(gamepad1.right_bumper && robot.elevador1.getCurrentPosition()>robot.topeSuperior && robot.elevador2.getCurrentPosition()>robot.topeSuperior){
                robot.subirElevador(0.9);
            }else if (gamepad1.left_bumper && robot.elevador1.getCurrentPosition()<robot.topeInferior && robot.elevador2.getCurrentPosition()<robot.topeInferior){
                robot.bajarElevador(0.9);
            }else {
                robot.mantenerElevador();
            }

            telemetry.addData("m1", robot.elevador1.getCurrentPosition());
            telemetry.addData("m2", robot.elevador2.getCurrentPosition());
            telemetry.update();

        }

    }
}
