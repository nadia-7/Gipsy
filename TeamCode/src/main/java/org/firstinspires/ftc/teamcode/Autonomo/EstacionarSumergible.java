package org.firstinspires.ftc.teamcode.Autonomo;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.OpMaster.Mecanismos;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

/*
 * This is a simple routine to test translational drive capabilities.
 */


@Config
@Autonomous(group = "a")
public class EstacionarSumergible extends LinearOpMode {
    double distanciapapoi = 100;

    @Override
    public void runOpMode() throws InterruptedException {
        Telemetry telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        Mecanismos mecanismos = new Mecanismos();
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        mecanismos.init(hardwareMap);

        Trajectory trajectory = drive.trajectoryBuilder(new Pose2d())
                        .forward(30)
                        .build();

        waitForStart();


        waitForStart();

        //-1 meter
        //1 sacar
        if (isStopRequested()) return;
        drive.followTrajectory(trajectory);
        mecanismos.ingesta.setPower(1);
        sleep(500);
        mecanismos.ingesta.setPower(0);


        Pose2d poseEstimate = drive.getPoseEstimate();
        telemetry.addData("finalX", poseEstimate.getX());
        telemetry.addData("finalY", poseEstimate.getY());
        telemetry.addData("finalHeading", poseEstimate.getHeading());
        telemetry.update();

        while (!isStopRequested() && opModeIsActive()) ;
    }

}
