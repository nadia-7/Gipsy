package org.firstinspires.ftc.teamcode.Autonomo;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;

import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.OpModes.Mecanismos;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

/*
 * This is a simple routine to test translational drive capabilities.
 */

@Config
@Autonomous(group = "a")
public class Test extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        Telemetry telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        Mecanismos mecanismos = new Mecanismos();
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        mecanismos.init(hardwareMap);
        mecanismos.mantenerBarredora();
        Pose2d startPose = new Pose2d(27.43, -64.57, Math.toRadians(90.00));
        drive.setPoseEstimate(startPose);

        TrajectorySequence trajectory0 = drive.trajectorySequenceBuilder(startPose)
                .splineToConstantHeading(new Vector2d(32.57, -51.43), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(40.95, -15.62), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(49.71, -15.24), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(46.48, -44.19), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(49.52, -48.95), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(51.05, -42.29), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(51.62, -13.90), Math.toRadians(90))
                .build();
        drive.setPoseEstimate(new Pose2d());

        waitForStart();


        if (isStopRequested()) return;
        drive.followTrajectorySequence(trajectory0);
        
        Pose2d poseEstimate = drive.getPoseEstimate();
        telemetry.addData("finalX", poseEstimate.getX());
        telemetry.addData("finalY", poseEstimate.getY());
        telemetry.addData("finalHeading", poseEstimate.getHeading());
        telemetry.update();

        while (!isStopRequested() && opModeIsActive()) ;
    }

}
