package com.example.meepmeeptesting_;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(27.43, -64.57, Math.toRadians(90.00)))
                        .splineToConstantHeading(new Vector2d(32.57, -51.43), Math.toRadians(90))
                        .splineToConstantHeading(new Vector2d(40.95, -15.62), Math.toRadians(90))
                        .splineToConstantHeading(new Vector2d(49.71, -15.24), Math.toRadians(90))
                        .splineToConstantHeading(new Vector2d(46.48, -44.19), Math.toRadians(90))
                        .splineToConstantHeading(new Vector2d(49.52, -48.95), Math.toRadians(90))
                        .splineToConstantHeading(new Vector2d(51.05, -42.29), Math.toRadians(90))
                        .splineToConstantHeading(new Vector2d(51.62, -13.90), Math.toRadians(90))
                        .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}