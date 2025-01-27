package org.firstinspires.ftc.teamcode.OpModes;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

//FIXME: ASIGNAR TOPES AL ELEVADOR
public class ElevadoresTest extends LinearOpMode {
    @Override
    public void runOpMode() {
        Mecanismos robot = new Mecanismos();
        //SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        //drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        robot.elevador1 = hardwareMap.get(DcMotor.class, "elevador1");
        robot.elevador2 = hardwareMap.get(DcMotor.class, "elevador2");
        robot.stopResetEconder(robot.elevador1, robot.elevador2);
        robot.runUsingEncoder(robot.elevador1, robot.elevador2);
        robot.elevador2.setDirection(DcMotorSimple.Direction.REVERSE);
        robot.zeroPower(robot.elevador1, robot.elevador2);

        boolean tope = true;


        waitForStart();

        while (!isStopRequested()) {
            // ====ELEVADOR====
            /*if (gamepad2.back) { //DESACTIVAR TOPE
                tope = false;
            } else if (gamepad2.start && !tope) { //ACTIVAR TOPE Y RESETEAR ENCODER
                robot.stopResetEconder(robot.elevador1, robot.elevador2);
                tope = true;
            } else if (tope && gamepad2.right_bumper && robot.elevador1.getCurrentPosition() > robot.eletopeSuperior && robot.elevador2.getCurrentPosition() > robot.eletopeSuperior) {
                robot.subirElevador(0.9);
            } else if (tope && gamepad2.left_bumper && robot.elevador1.getCurrentPosition() < robot.eletopeInferior && robot.elevador2.getCurrentPosition() < robot.eletopeInferior) {
                robot.bajarElevador(0.7);
            } else*/ if (gamepad2.right_bumper) {
                robot.subirElevador(0.9);
            } else if (gamepad2.left_bumper) {
                robot.bajarElevador(0.7);
            } else {
                robot.mantenerElevador();
            }

            telemetry.addData("Elevador Izq", robot.elevador1.getCurrentPosition());
            telemetry.addData("Elevador Der", robot.elevador2.getCurrentPosition());
            telemetry.addLine("");

        }
    }}
