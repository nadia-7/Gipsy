package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
@Disabled
@Autonomous
public class AutonomoCanastasTesttt extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Mecanismos robot = new Mecanismos();
        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.init(hardwareMap);
        robot.cerrarGarra();

        Pose2d basquetDropOff = new Pose2d(8.5, 27, 5.5175);

        //_ Traj 1
        TrajectorySequence leave1Basquet = drive.trajectorySequenceBuilder(new Pose2d())
                .UNSTABLE_addTemporalMarkerOffset(0, () ->{
                    robot.barredoraRunToPosition(0.7, robot.topeBarredoraFront);

                    })
                .UNSTABLE_addTemporalMarkerOffset(4, () -> {
                    robot.barredoraRunToPosition(-0.7, 0);
                })

                .waitSeconds(10)

                .build();
//note tiempo en salir barredora 1.1 con potencia de 0.65


        waitForStart();
        drive.followTrajectorySequence(leave1Basquet);
    }

}
