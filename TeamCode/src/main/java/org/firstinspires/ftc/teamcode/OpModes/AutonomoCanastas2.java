package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

//TODO MOVER ARTICULACION GARRA
//seconds salir barredora:  1.1, power: 0.65
//seconds high basket elevator: 1.5, power: 0.9
//seconds retraer barredora: 0.43, power -0.75
//seconds take sample elevator: 1.1 + .35 power: 0.9
//note: posicion articulacion garra tomar sample: 0.68
//note: posicion articulacion garra dejar sample: 0.16
//note: elevador tope tomar sample -238


@Autonomous
public class AutonomoCanastas2 extends LinearOpMode {
    public static double consX = 0;
    public static double consY = 0;

//    DistanceSensor sensorDistance;

    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Mecanismos robot = new Mecanismos();
        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.init(hardwareMap);
//        sensorDistance = hardwareMap.get(DistanceSensor.class, "sensor_distance");
        robot.cerrarGarra();

        Pose2d basquetDropOff = new Pose2d(11 + consX, 27 + consY, 5.5175);

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
                })
                .addTemporalMarker(1.85, () -> {
                    robot.abrirGarra();
                })
                .build();

        //_ Traj2
        TrajectorySequence pickU2Basquet = drive.trajectorySequenceBuilder(leave1Basquet.end())
                .addTemporalMarker(0, () -> {
                    robot.autoTomarSampleContenedorTRABADO();
                    robot.subirArticulacionBarredora();
                    robot.bajarElevador(0.9);
                })
                .addTemporalMarker(1.1, () -> {
                    robot.runUsingEncoder(robot.elevador1, robot.elevador2);
                    robot.mantenerElevador();

                })

                //note: primer sample cancha
                .splineToLinearHeading(new Pose2d(16.8 + consX, 18 + consY, 0), 0)
                .UNSTABLE_addTemporalMarkerOffset(0, () ->{
                    robot.bajarArticulacionBarredora();
                    robot.ingesta.setPower(-1);
                })
                .UNSTABLE_addTemporalMarkerOffset(0.97 , () -> {
                    robot.ingesta.setPower(0);
                })

                .UNSTABLE_addTemporalMarkerOffset(1.1, ()->{
                    robot.servoAriculacionBarredora.setPosition(0.2);
                    robot.barredoraRunToPosition(1, 15);
                    robot.abrirGarra();
                })

                .UNSTABLE_addTemporalMarkerOffset(1.7, () ->{
                    robot.ingesta.setPower(-1);
                })

                .UNSTABLE_addTemporalMarkerOffset(2 /*note 2.2*/, () ->{
                    robot.ingesta.setPower(0);
                })

                .UNSTABLE_addTemporalMarkerOffset(2, () ->{
                    robot.elevadorRunToPosition(0.9, robot.elevadorTomarSampleContenedor);
                })

                .UNSTABLE_addTemporalMarkerOffset(2.5, () -> {
                    robot.cerrarGarra();
                })
                .waitSeconds(2.9)

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
                    robot.autoTomarSampleContenedorTRABADO();

                })
                .addTemporalMarker(0.7, () ->{
                    robot.elevadorRunToPosition(1, -500);
                })
                //note tomar segundo sample
                .splineToLinearHeading(new Pose2d(15.5 + consX, 27.7 + consY, 0), 0)
                .UNSTABLE_addTemporalMarkerOffset(0.2, () ->{
                    robot.bajarArticulacionBarredora();
                    robot.ingesta.setPower(-1);
                })
                .UNSTABLE_addTemporalMarkerOffset(1.3, ()->{
                    robot.ingesta.setPower(0);

                })
                .UNSTABLE_addTemporalMarkerOffset(1.4, () ->{
                    robot.servoAriculacionBarredora.setPosition(0.2);
                    robot.barredoraRunToPosition(-1, 15);
                    robot.abrirGarra();
                })

                .UNSTABLE_addTemporalMarkerOffset(1.9, () ->{
                    robot.ingesta.setPower(-1);
                })

                .UNSTABLE_addTemporalMarkerOffset(2.5, () ->{
                    robot.ingesta.setPower(0);
                })

                .UNSTABLE_addTemporalMarkerOffset(2.2, () ->{
                    robot.elevadorRunToPosition(0.9, robot.elevadorTomarSampleContenedor);
                })

                .UNSTABLE_addTemporalMarkerOffset(2.7, () -> {
                    robot.cerrarGarra();
                })
                .waitSeconds(3.2)
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
                .splineToLinearHeading(new Pose2d(12 + consX, 25 + consY, 5.5175), 90)
                .UNSTABLE_addTemporalMarkerOffset(0.85, () ->{
                    robot.autoDejarSampleCanastaChamber();

                })
                .UNSTABLE_addTemporalMarkerOffset(1.8, () ->{
                    robot.abrirGarra();
                })

                .waitSeconds(2.2)
                .build();


        TrajectorySequence traj8 = drive.trajectorySequenceBuilder(pickU2Basquet.end())
                .addTemporalMarker(0, () ->{
                    robot.autoTomarSampleContenedorTRABADO();
                    robot.elevadorRunToPosition(0.9, -500);
                })
                .splineTo(new Vector2d(59 + consX, -9 + consY), Math.toRadians(-90))
                .build();



        waitForStart();
        drive.followTrajectorySequence(leave1Basquet);
        drive.followTrajectorySequence(pickU2Basquet);
        drive.followTrajectorySequence(traj3);
        drive.followTrajectorySequence(traj4);
        drive.followTrajectorySequence(traj5);
        drive.followTrajectorySequence(traj8);

    }

}
