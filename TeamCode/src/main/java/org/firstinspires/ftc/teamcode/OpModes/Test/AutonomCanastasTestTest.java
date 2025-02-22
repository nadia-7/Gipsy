package org.firstinspires.ftc.teamcode.OpModes.Test;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;


import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.OpModes.Mecanismos;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Disabled
@Autonomous
public class AutonomCanastasTestTest extends LinearOpMode {
    public static double consX = 0;
    public static double consY = 0;
    DistanceSensor sensorDistance;

    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Mecanismos robot = new Mecanismos();
        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.init(hardwareMap);
        sensorDistance = hardwareMap.get(DistanceSensor.class, "sensor");

        robot.cerrarGarra();
        Pose2d basquetDropOff = new Pose2d(12.2 + consX, 27 + consY, 5.5175);

        //_ Traj 1
        TrajectorySequence leave1Basquet = drive.trajectorySequenceBuilder(new Pose2d())
                .addTemporalMarker(0.1, () -> {
                    robot.subirElevador(0.9);
                })
                .splineToLinearHeading(basquetDropOff, 90)
                .waitSeconds(0.95)
                .addTemporalMarker(1.6, () -> {
                    robot.runUsingEncoder(robot.elevador1, robot.elevador2);
                    robot.mantenerElevador();
                    robot.barredoraRunToPosition(0.65, robot.topeBarredoraFront);

                })
                .addTemporalMarker(1.3,() ->{
                    robot.autoDejarSampleCanastaChamber();
                    robot.servoArticulacionGarra.setPosition(0.288);
                })
                .addTemporalMarker(1.85, () -> {
                    robot.abrirGarra();
                })
                .build();

        //_ Traj2
        TrajectorySequence pickU2Basquet = drive.trajectorySequenceBuilder(leave1Basquet.end())
                .addTemporalMarker(0, () -> {
                    robot.autoTomarSampleContenedor();
                    robot.subirArticulacionBarredora();
                    robot.bajarElevador(0.9);
                })
                .addTemporalMarker(1.1, () -> {
                    robot.runUsingEncoder(robot.elevador1, robot.elevador2);
                    robot.mantenerElevador();

                })

                //note: primer sample cancha
                .splineToLinearHeading(new Pose2d(16.9 + consX, 19 + consY, 0), 0)
                .UNSTABLE_addTemporalMarkerOffset(0, () ->{
                    robot.bajarArticulacionBarredora();
                    robot.ingesta.setPower(-1);
                })
                .build();

        TrajectorySequence pickU2Basquet_1 = drive.trajectorySequenceBuilder(leave1Basquet.end())

                //_---
                .UNSTABLE_addTemporalMarkerOffset(0 , () -> {
                    robot.ingesta.setPower(0);
                })

                .UNSTABLE_addTemporalMarkerOffset(0.28, ()->{
                    robot.servoAriculacionBarredora.setPosition(0.05);
                    robot.barredoraRunToPosition(-0.6, 0);
                    robot.abrirGarra();
                })

                .UNSTABLE_addTemporalMarkerOffset(0.88, () ->{
                    robot.ingesta.setPower(-1);
                })

                .UNSTABLE_addTemporalMarkerOffset(1.18 /*note 2.2*/, () ->{
                    robot.ingesta.setPower(0);
                })

                .UNSTABLE_addTemporalMarkerOffset(1.18, () ->{
                    robot.elevadorRunToPosition(0.9, robot.elevadorTomarSampleContenedor);
                })

                .UNSTABLE_addTemporalMarkerOffset(1.68, () -> {
                    robot.cerrarGarra();
                })
                .waitSeconds(2.08)

                .build();

        TrajectorySequence traj3 = drive.trajectorySequenceBuilder(pickU2Basquet.end())
                .addTemporalMarker(0.1, () ->{
                    robot.subirElevador(0.9);
                })
                .addTemporalMarker(1.6, () ->{
                    robot.runUsingEncoder(robot.elevador1, robot.elevador2);
                    robot.mantenerElevador();
                    robot.barredoraRunToPosition(0.65, robot.topeBarredoraFront);

                })
                .splineToLinearHeading(basquetDropOff, 90)
                .UNSTABLE_addTemporalMarkerOffset(0.9, () ->{
                    robot.autoDejarSampleCanastaChamber();

                })
                .UNSTABLE_addTemporalMarkerOffset(1.72, () ->{
                    robot.abrirGarra();
                })

                .waitSeconds(2.2)
                .build();

        TrajectorySequence traj4 = drive.trajectorySequenceBuilder(pickU2Basquet.end())
                .addTemporalMarker(0.1, () ->{
                    robot.autoTomarSampleContenedor();

                })
                .addTemporalMarker(0.7, () ->{
                    robot.elevadorRunToPosition(1, -500);
                })
                //note tomar segundo sample
                .splineToLinearHeading(new Pose2d(15.5 + consX, 27.2 + consY, 0), 0)
                .UNSTABLE_addTemporalMarkerOffset(0.2, () ->{
                    robot.bajarArticulacionBarredora();
                    robot.ingesta.setPower(-1);
                })
                .build();

                //_ ----------------------------------
        TrajectorySequence traj4_1 = drive.trajectorySequenceBuilder(pickU2Basquet.end())
                .UNSTABLE_addTemporalMarkerOffset(0, ()->{
                    robot.ingesta.setPower(0);

                })
                .UNSTABLE_addTemporalMarkerOffset(.4, () ->{
                    robot.servoAriculacionBarredora.setPosition(0.05);
                    robot.barredoraRunToPosition(-0.6, 10);
                    robot.abrirGarra();
                })

                .UNSTABLE_addTemporalMarkerOffset(.9, () ->{
                    robot.ingesta.setPower(-1);
                })

                .UNSTABLE_addTemporalMarkerOffset(1.5, () ->{
                    robot.ingesta.setPower(0);
                })

                .UNSTABLE_addTemporalMarkerOffset(1.2, () ->{
                    robot.elevadorRunToPosition(0.9, robot.elevadorTomarSampleContenedor);
                })

                .UNSTABLE_addTemporalMarkerOffset(1.7, () -> {
                    robot.cerrarGarra();
                })
                .waitSeconds(2.2)
                .build();

        TrajectorySequence traj5 = drive.trajectorySequenceBuilder(pickU2Basquet.end())
                .addTemporalMarker(0.1, () ->{
                    robot.subirElevador(0.9);
                })
                .addTemporalMarker(1.6, () ->{
                    robot.runUsingEncoder(robot.elevador1, robot.elevador2);
                    robot.mantenerElevador();
                    robot.barredoraRunToPosition(0.65, robot.topeBarredoraFront);

                })
                .splineToLinearHeading(new Pose2d(12.5 + consX, 25 + consY, 5.5175), 90)
                .UNSTABLE_addTemporalMarkerOffset(0.85, () ->{
                    robot.autoDejarSampleCanastaChamber();

                })
                .UNSTABLE_addTemporalMarkerOffset(1.8, () ->{
                    robot.abrirGarra();
                })

                .waitSeconds(2.2)
                .build();

        TrajectorySequence traj6 = drive.trajectorySequenceBuilder(pickU2Basquet.end())
                .addTemporalMarker(0.1, () ->{
                    robot.autoTomarSampleContenedor();


                })
                .addTemporalMarker(0.7, () ->{
                    robot.elevadorRunToPosition(1, -500);
                })

                //note: tomar ultimo sample
                .splineToLinearHeading(new Pose2d(37 + consX, 16.2 +consY, Math.toRadians(87)), Math.toRadians(0))
                .UNSTABLE_addTemporalMarkerOffset(0.2, () ->{
                    robot.bajarArticulacionBarredora();
                    robot.ingesta.setPower(-1);
                })
                .build();

        TrajectorySequence traj6_1 = drive.trajectorySequenceBuilder(pickU2Basquet.end())
                //_ --
                .UNSTABLE_addTemporalMarkerOffset(0.0, ()->{
                    robot.ingesta.setPower(0);

                })
                .UNSTABLE_addTemporalMarkerOffset(0.9, () ->{
                    robot.servoAriculacionBarredora.setPosition(0.05);
                    robot.barredoraRunToPosition(-0.6, 15);
                    robot.abrirGarra();
                })
                .UNSTABLE_addTemporalMarkerOffset(1.1, () ->{
                    robot.ingesta.setPower(-1);
                })

                .UNSTABLE_addTemporalMarkerOffset(1.4, () ->{
                    robot.ingesta.setPower(0);
                })

                .UNSTABLE_addTemporalMarkerOffset(1.4, () ->{
                    robot.elevadorRunToPosition(0.9, robot.elevadorTomarSampleContenedor);
                })

                .UNSTABLE_addTemporalMarkerOffset(1.9, () -> {
                    robot.cerrarGarra();
                })
                .waitSeconds(2.3)
                .build();

        TrajectorySequence traj7 = drive.trajectorySequenceBuilder(pickU2Basquet.end())
                .addTemporalMarker(0.1, () ->{
                    robot.subirElevador(0.9);
                })
                .addTemporalMarker(1.6, () ->{
                    robot.runUsingEncoder(robot.elevador1, robot.elevador2);
                    robot.mantenerElevador();
                    robot.barredoraRunToPosition(0.65, robot.topeBarredoraFront);

                })
                .splineToLinearHeading(new Pose2d(14.5 + consX, 22 + consY, Math.toRadians(315)), 90)
                .UNSTABLE_addTemporalMarkerOffset(0.85, () ->{
                    robot.autoDejarSampleCanastaChamber();

                })
                .UNSTABLE_addTemporalMarkerOffset(1.8, () ->{
                    robot.abrirGarra();
                })

                .waitSeconds(2)
                .build();

        TrajectorySequence traj8 = drive.trajectorySequenceBuilder(pickU2Basquet.end())
                .addTemporalMarker(0, () ->{
                    robot.autoTomarSampleContenedor();
                    robot.elevadorRunToPosition(0.9, -500);
                })
                .splineTo(new Vector2d(59 + consX, -9 + consY), Math.toRadians(-90))
                .build();



        waitForStart();
        drive.followTrajectorySequence(leave1Basquet);
        drive.followTrajectorySequence(pickU2Basquet);
        while (sensorDistance.getDistance(DistanceUnit.CM) > 6.5){
            robot.ingesta.setPower(-1);
        }
        drive.followTrajectorySequence(pickU2Basquet_1);

    }

}
