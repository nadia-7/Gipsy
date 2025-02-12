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
                .waitSeconds(3)
                .addTemporalMarker(1.85, () -> {
                    robot.subirElevador(0.4);
                    robot.autoDejarSampleCanastaChamber();
                    robot.servoArticulacionGarra.setPosition(0.16);
                    robot.extensionBarredora(0.52);
                })
                .addTemporalMarker(2.8, () -> {
                    robot.extensionBarredora(0);
                    robot.abrirGarra();
                })
                .build();

        waitForStart();
        drive.followTrajectorySequence(leave1Basquet);

    }

}
