package org.firstinspires.ftc.teamcode.Autonomo;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.OpMaster.Mecanismos;

@Autonomous (group = "1")
public class EstacionamientoHuman extends LinearOpMode {
    @Override
    public void runOpMode(){
        Chasis chasis = new Chasis();

        ElapsedTime tiempo = new ElapsedTime();
        chasis.init(hardwareMap);
        waitForStart();
        while (!isStopRequested()){
            if (gamepad1.a){
                chasis.moverseEnfrente(0.5);
            }
            if (gamepad1.b){
                chasis.moverseDerecha(0.5);
            }
            telemetry.addData("EI", chasis.enfrenteIzq.getCurrentPosition());
            telemetry.addData("AD", chasis.atrasDer.getCurrentPosition());

            telemetry.update();
        }
    }

}
