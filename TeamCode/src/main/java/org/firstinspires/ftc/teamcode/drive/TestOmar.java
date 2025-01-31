package org.firstinspires.ftc.teamcode.drive;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
//IDEA 1: EXTENSION VARIABLE
//IDEA 2: EXTENSION FIJA (APOYO DE CHASIS)
//IDEA 3: NO HACEMOS NADA Y DORMIMOS
//IDEA 4: FIJAMOS UN PUESTO DE CARNITAS
//IDEA 5: NO HAY

@TeleOp(group = "drive")
@Config
public class TestOmar extends LinearOpMode {
    DcMotor myMotor = null;
    public static double motorPower = 0.6;
    public static int target = -90;

    public int degreesToTicks(int degrees){
        return (int) Math.round(degrees * 2.0883);
    }

    @Override
    public void runOpMode() throws InterruptedException {

        waitForStart();

        while (!isStopRequested()) {
            if(gamepad1.right_trigger !=0 && myMotor.getCurrentPosition() < 100){
                myMotor.setPower(gamepad1.right_trigger);
            } else {
                myMotor.setPower(0);
            }

            if(gamepad1.left_trigger !=0 && myMotor.getCurrentPosition() <= 0){
                myMotor.setPower(-gamepad1.left_trigger);
            } else {
                myMotor.setPower(0);
            }

            if(gamepad1.b){
                myMotor.setTargetPosition(0);
                myMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                myMotor.setPower(0.6);
                //wait until done
                while(myMotor.isBusy()){}
                myMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            }

            telemetry.addData("position", myMotor.getCurrentPosition());
            telemetry.addData("motor power: ", myMotor.getPower());
            telemetry.update();
        }
    }

    @Override
    public void waitForStart() {
        myMotor = hardwareMap.get(DcMotor.class,"motor");
        myMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        myMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addLine("Elevadores iniciados");
        telemetry.update();
    }
}
