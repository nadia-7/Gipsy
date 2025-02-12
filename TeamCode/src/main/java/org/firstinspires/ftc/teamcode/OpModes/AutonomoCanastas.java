package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous
public class AutonomoCanastas extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Mecanismos robot = new Mecanismos();
        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.init(hardwareMap);

        Pose2d basquetDropOff = new Pose2d(8.69, 25.3, 5.5175);

        TrajectorySequence leave1Basquet = drive.trajectorySequenceBuilder(new Pose2d())
                .addTemporalMarker(0.1, () -> {
                    robot.subirElevador(0.9);
                })
                .splineToLinearHeading(basquetDropOff, 0)
                .waitSeconds(1.25)
                .addTemporalMarker(1.6, () -> {
                    robot.subirElevador(0.4);
                    robot.autoDejarSampleCanastaChamber();
                    robot.servoArticulacionGarra.setPosition(0.16);
                    robot.extensionBarredora(0.65);
                })
                .addTemporalMarker(2.5, () -> {
                    robot.abrirGarra();
                })
                .addTemporalMarker(2.65, () -> {
                    robot.extensionBarredora(0);
                    robot.abrirGarra();
                })
                .build();
//note tiempo en salir barredora 1.1 con potencia de 0.65
        TrajectorySequence pickU2Basquet = drive.trajectorySequenceBuilder(leave1Basquet.end())
                .addTemporalMarker(0, () -> {
                    robot.autoTomarSampleContenedor();
                    robot.bajarElevador(0.9);
                })
                .addTemporalMarker(1.3, () -> {
                    robot.subirElevador(0.1);
                })
                .splineToLinearHeading(new Pose2d(19.86, 19.42, 0), 0)
                .waitSeconds(3.3)
                .addTemporalMarker(1.38/*1.4*/, () -> {
                    robot.bajarArticulacionBarredora();
                    robot.ingesta.setPower(-1.0);
                })
                .addTemporalMarker(2.85, () -> {
                    robot.subirArticulacionBarredora();
                })
                .addTemporalMarker(3.05, () -> {
                    robot.extensionBarredora(-0.65);
                })
                .addTemporalMarker(3.48, () -> {
                    robot.ingesta.setPower(0);
                })
                .addTemporalMarker(3.5, () -> {
                    robot.extensionBarredora(0);
                })
                .build();

        waitForStart();
        drive.followTrajectorySequence(leave1Basquet);
        drive.followTrajectorySequence(pickU2Basquet);
    }

}
