package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;

import org.firstinspires.ftc.teamcode.OpModes.Test.SensorColores;
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
public class AutonomoCanastas extends LinearOpMode {
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

        Pose2d basquetDropOff = new Pose2d(12.3 + consX, 27 + consY, 5.5175);
        Pose2d startPose = new Pose2d(0 + consX, 0 + consY, 0);
        drive.setPoseEstimate(startPose);

     //NOTE: DEJAR PRIMER CANASTA
        TrajectorySequence leave1Basquet = drive.trajectorySequenceBuilder(startPose)
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

        //NOTE: TOMAR PRIMER SAMPLE SPIKE
        TrajectorySequence pickU2Basquet = drive.trajectorySequenceBuilder(leave1Basquet.end())
                .addTemporalMarker(0, () -> {
                    robot.autoTomarSampleContenedorTRABADO(); //change trabado
                    robot.subirArticulacionBarredora();
                    robot.elevadorRunToPosition(1, -500);
                })
                //DEVOLT

                //note: primer sample SPIKE
                .splineToLinearHeading(new Pose2d(15.6 + consX, 17.5 + consY, Math.toRadians(4)), 0)
                .UNSTABLE_addTemporalMarkerOffset(0.1, () ->{
                    robot.zeroPower(robot.elevador1, robot.elevador2, robot.correderaBarredora);
                    robot.bajarArticulacionBarredora();
                    //change poner en 0 los motores
                    robot.ingesta.setPower(-1);
                })
                .UNSTABLE_addTemporalMarkerOffset(1.5, () -> {
                    robot.ingesta.setPower(0);
                    //change manener elevador
                    robot.mantenerElevador();
                })

                .UNSTABLE_addTemporalMarkerOffset(1.2, ()->{
                    robot.subirArticulacionBarredora();
                    robot.retraccionBarredora(1);
                    robot.abrirGarra();
                })
                .UNSTABLE_addTemporalMarkerOffset(1.7, () ->{
                    robot.zeroPower(robot.correderaBarredora);
                })

                .UNSTABLE_addTemporalMarkerOffset(1.8, () ->{
                    //change
                    robot.zeroPower(robot.elevador1, robot.elevador2);
                    robot.ingesta.setPower(-1);
                })

                .UNSTABLE_addTemporalMarkerOffset(2.1, () ->{
                    robot.ingesta.setPower(0);
                    //change
                    robot.mantenerElevador();
                })

                .UNSTABLE_addTemporalMarkerOffset(2.1, () ->{
                    robot.elevadorRunToPosition(0.9, robot.elevadorTomarSampleContenedor);
                })

                .UNSTABLE_addTemporalMarkerOffset(2.6, () -> {
                    robot.cerrarGarra();
                })
                .waitSeconds(3)

                .build();
        //NOTE: DEJAR PRIMER SAMPLE
        TrajectorySequence traj3 = drive.trajectorySequenceBuilder(pickU2Basquet.end())
                .addTemporalMarker(0.1, () ->{
                    robot.subirElevador(1);
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

        //NOTE: TOMAR SEGUNDO SAMPLE
        TrajectorySequence traj4 = drive.trajectorySequenceBuilder(traj3.end())
                .addTemporalMarker(0.1, () ->{
                    robot.autoTomarSampleContenedorTRABADO();
                    robot.elevadorRunToPosition(1, -500);


                })
                //note tomar segundo sample
                .splineToLinearHeading(new Pose2d(15.5+ consX, 27.3 /*26.8*/ + consY, Math.toRadians(2/*change 5*/)), 0)
                .UNSTABLE_addTemporalMarkerOffset(0.2, () ->{
                    robot.bajarArticulacionBarredora();
                    robot.zeroPower(robot.elevador1, robot.elevador2, robot.correderaBarredora);
                    robot.ingesta.setPower(-1);
                })
                .UNSTABLE_addTemporalMarkerOffset(1.3/* change 1.7*/, ()->{
                    robot.ingesta.setPower(0);
                    robot.mantenerElevador();

                })
                .UNSTABLE_addTemporalMarkerOffset(1.4, () ->{
                    robot.subirArticulacionBarredora();
                    robot.retraccionBarredora(1);
                    robot.abrirGarra();
                })

                .UNSTABLE_addTemporalMarkerOffset(1.9, () ->{
                    robot.zeroPower(robot.correderaBarredora);
                })

                .UNSTABLE_addTemporalMarkerOffset(1.9, () ->{
                    robot.ingesta.setPower(-1);
                })

                .UNSTABLE_addTemporalMarkerOffset(2.5, () ->{
                    robot.ingesta.setPower(0);
                })

                .UNSTABLE_addTemporalMarkerOffset(2.3, () ->{
                    robot.elevadorRunToPosition(1, robot.elevadorTomarSampleContenedor);
                })

                .UNSTABLE_addTemporalMarkerOffset(2.8, () -> {
                    robot.cerrarGarra();
                })
                .waitSeconds(3.3)
                .build();

        //NOTE: DEJAR SEGUNDO SAMPLE
        TrajectorySequence traj5 = drive.trajectorySequenceBuilder(traj4.end())
                .addTemporalMarker(0.1, () ->{
                    robot.subirElevador(1);
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

        //NOTE TOMAR TERCER SAMPLE
        TrajectorySequence traj6 = drive.trajectorySequenceBuilder(traj5.end())
                .addTemporalMarker(0.1, () ->{
                    robot.autoTomarSampleContenedorTRABADO();


                })
                .addTemporalMarker(0.7, () ->{
                    robot.elevadorRunToPosition(1, -500);
                })

                //note: tomar ultimo sample
                .splineToLinearHeading(new Pose2d(36.5 /*change 36*/ + consX, 17 +consY, Math.toRadians(90)), Math.toRadians(0))
                .UNSTABLE_addTemporalMarkerOffset(0.1, () ->{
                    robot.bajarArticulacionBarredora();
                    robot.zeroPower(robot.elevador1, robot.elevador2);
                    robot.ingesta.setPower(-1);
                })
                .UNSTABLE_addTemporalMarkerOffset(1.3/*0.9*/, ()->{
                    robot.ingesta.setPower(0);
                    robot.mantenerElevador();

                })
                .UNSTABLE_addTemporalMarkerOffset(1.4, () ->{
                    robot.servoAriculacionBarredora.setPosition(0.2);
                    robot.retraccionBarredora(1);

                    robot.abrirGarra();
                })
                .UNSTABLE_addTemporalMarkerOffset(1.9, () ->{
                    robot.zeroPower(robot.correderaBarredora);
                })
                .UNSTABLE_addTemporalMarkerOffset(1.9, () ->{
                    robot.ingesta.setPower(-1);
                })

                .UNSTABLE_addTemporalMarkerOffset(2.5, () ->{
                    robot.ingesta.setPower(0);
                })

                .UNSTABLE_addTemporalMarkerOffset(2.3, () ->{
                    robot.elevadorRunToPosition(1, robot.elevadorTomarSampleContenedor);
                })

                .UNSTABLE_addTemporalMarkerOffset(2.8, () -> {
                    robot.cerrarGarra();
                })
                .waitSeconds(3.5)
                .build();

        //NOTE: DEJAR TERCER SAMPLE
        TrajectorySequence traj7 = drive.trajectorySequenceBuilder(traj6.end())
                .addTemporalMarker(0.1, () ->{
                    robot.subirElevador(1);
                })
                .addTemporalMarker(1.6, () ->{
                    robot.runUsingEncoder(robot.elevador1, robot.elevador2);
                    robot.mantenerElevador();

                })
                .splineToLinearHeading(new Pose2d(8+ consX, 26+ consY, Math.toRadians(316)), 90)
                .UNSTABLE_addTemporalMarkerOffset(0.85, () ->{
                    robot.autoDejarSampleCanastaChamber();

                })
                .UNSTABLE_addTemporalMarkerOffset(1.8, () ->{
                    robot.abrirGarra();
                })

                .waitSeconds(2)
                .build();

        //NOTE ESTACIONAR
        TrajectorySequence traj8 = drive.trajectorySequenceBuilder(traj7.end())
                .addTemporalMarker(0, () ->{
                    robot.autoTomarSampleContenedorTRABADO();
                    robot.barredoraRunToPosition(0.67, 10);
                    robot.elevadorRunToPosition(0.8, 0);
                })
                .splineTo(new Vector2d(59 + consX, -11/*change 9*/ + consY), Math.toRadians(-90))
                .build();



        waitForStart();
        drive.followTrajectorySequence(leave1Basquet);
        drive.followTrajectorySequence(pickU2Basquet);
        drive.followTrajectorySequence(traj3);
        drive.followTrajectorySequence(traj4);
        drive.followTrajectorySequence(traj5);
        drive.followTrajectorySequence(traj6);
        drive.followTrajectorySequence(traj7);
        drive.followTrajectorySequence(traj8);

    }

}
