package com.example.meepmeeptesting_;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(700);
        Pose2d basquetDropOff = new Pose2d(8.5, 27, 5.5175);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d())
                        .splineToLinearHeading(basquetDropOff, 90)
                        .splineToLinearHeading(new Pose2d(16.2, 18.5, 0), 0)
                        .splineToLinearHeading(basquetDropOff, 90)
                        .splineToLinearHeading(new Pose2d(16.2, 27.2, 0), 90)
                        .splineToLinearHeading(basquetDropOff, 90)
                        .splineToLinearHeading(new Pose2d(39/*change 35*/, 15, Math.toRadians(90/*change 85*/)), Math.toRadians(0))
                        .splineToSplineHeading(basquetDropOff, 90)




                        .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}