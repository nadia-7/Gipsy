package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
//seconds salir barredora:  1.1, power: 0.65
//seconds high basket elevator: 1.5, power: 0.9
//seconds retraer barredora: 0.43, power -0.75
//seconds take sample elevator: 1.1 + .35 power: 0.9
//note: posicion articulacion garra tomar sample: 0.68
//note: posicion articulacion garra dejar sample: 0.16
//note: elevador tope tomar sample -238


@Autonomous
public class AutonomoCanastas extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Mecanismos robot = new Mecanismos();
        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.init(hardwareMap);

        Pose2d basquetDropOff = new Pose2d(8.5, 27, 5.5175);

        //_ Traj 1
        TrajectorySequence leave1Basquet = drive.trajectorySequenceBuilder(new Pose2d())
                .addTemporalMarker(0.1, () -> {
                    robot.subirElevador(0.9);
                })
                .splineToLinearHeading(basquetDropOff, 90)
                .waitSeconds(1)
                .addTemporalMarker(1.6, () -> {
                    robot.runUsingEncoder(robot.elevador1, robot.elevador2);
                    robot.mantenerElevador();
                    robot.barredoraRunToPosition(0.65, robot.topeBarredoraFront);

                })
                .addTemporalMarker(1.3,() ->{
                    robot.autoDejarSampleCanastaChamber();
                    robot.servoArticulacionGarra.setPosition(0.16);
                })
                .addTemporalMarker(1.9/*change2*/, () -> {
                    robot.abrirGarra();
                })
                .build();

        //_ Traj2
        TrajectorySequence pickU2Basquet = drive.trajectorySequenceBuilder(leave1Basquet.end())
                .addTemporalMarker(0, () -> {
                    robot.autoTomarSampleContenedor();
                    robot.abrirGarra();
                    robot.bajarElevador(0.9);
                })
                .addTemporalMarker(1.1, () -> {
                    robot.runUsingEncoder(robot.elevador1, robot.elevador2);
                    robot.mantenerElevador();

                })


                 .splineToLinearHeading(new Pose2d(18.5, 19.3, 0), -100)
                .UNSTABLE_addTemporalMarkerOffset(0, () ->{
                    robot.bajarArticulacionBarredora();
                    robot.ingesta.setPower(-0.95); //change 1
                })
                .UNSTABLE_addTemporalMarkerOffset(1.2, ()->{
                    robot.servoAriculacionBarredora.setPosition(0.05);//robot.subirArticulacionBarredora();
                    robot.barredoraRunToPosition(-0.6, 0);

                     })
                .UNSTABLE_addTemporalMarkerOffset(0.9, () -> {
                    robot.ingesta.setPower(0);
                })
                .UNSTABLE_addTemporalMarkerOffset(1.7, () ->{
                    robot.ingesta.setPower(-1);
                })

                .UNSTABLE_addTemporalMarkerOffset(2.15, () ->{ //change 2
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
                .UNSTABLE_addTemporalMarkerOffset(1, () ->{
                    robot.autoDejarSampleCanastaChamber();
                    robot.servoArticulacionGarra.setPosition(0.16);

                })
                .UNSTABLE_addTemporalMarkerOffset(1.6, () ->{
                    robot.abrirGarra();
                })
                .waitSeconds(2)
                .build();

        TrajectorySequence traj4 = drive.trajectorySequenceBuilder(traj3.end())

                .build();




        waitForStart();
        drive.followTrajectorySequence(leave1Basquet);
        drive.followTrajectorySequence(pickU2Basquet);
        drive.followTrajectorySequence(traj3);
    }

}
