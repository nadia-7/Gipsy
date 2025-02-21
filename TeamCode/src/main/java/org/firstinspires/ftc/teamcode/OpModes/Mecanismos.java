package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
//AÑAAÑAÑAÑÑAÑAÑAÑÑAÑAÑÑAÑÑAÑAÑAÑAAAAA´

//note: 1 es izquierda, 2 es derecha
public class Mecanismos {
    //_ ELEVADOR
    public DcMotor elevador1;
    public DcMotor elevador2;
    public int eletopeSuperior= -3323;
    public int eletopeInferior= 0;
    public int elevadorTomarSampleContenedor = -210; //change -300;

    //_ BARREDORA
    public DcMotor correderaBarredora;
    public DcMotor ingesta;
    public Servo servoAriculacionBarredora;
    public int topeBarredoraFront = -750;

    //_ GARRA
    public Servo servoBrazo1;
    public Servo servoBrazo2;
    public Servo servoArticulacionGarra;
    public Servo servoGarra;

    //_ CONSTANTES
    double articulacionIncremento =0.01;
    double brazoIncremento = 0.01;

    //note: Topes BRAZO
    //_ atras
    double topeAtrasBrazo1Izq =0.5;//note: ATRAS 100% 0.3;
    double topeAtrasBrazo2Der =0.5; //NOTE: ATRAS 100% 0.7;
    //_ enfrente
    double topeFrontBrazo1Izq =1;
    double topeFrontBrazo2Der = 0;
    double AUTOtopeFrontBrazo1Izq =1;
    double AUTOtopeFrontBrazo2Der = 0;

    double posicionArtGarraErecto = 0.379;

    double garraArticPosMaxEnfrente = 0.78;
    double articulacionGarraPosSpecimen = 0.47; //NOTE: tomar spacimen



    public void init(HardwareMap hardwareMap){
        //_ ELEVADOR
        elevador1 = hardwareMap.get(DcMotor.class,"elevador1"); //IZQ
        elevador2 = hardwareMap.get(DcMotor.class,"elevador2"); //DER

        //_ BARREDORA
        correderaBarredora = hardwareMap.get(DcMotor.class, "correderaBarredora");
        ingesta =hardwareMap.get(DcMotor.class, "ingesta");
        servoAriculacionBarredora = hardwareMap.get(Servo.class, "articulacionBarredora");

        //_ GARRA
        servoBrazo1 = hardwareMap.get(Servo.class, "brazo1"); //
        servoBrazo2 = hardwareMap.get(Servo.class, "brazo2");
        servoArticulacionGarra = hardwareMap.get(Servo.class, "articulacionGarra");
        servoGarra = hardwareMap.get(Servo.class, "garra");

        cerrarGarra();
       // moverBrazoMaxEnfrente();
        //moverArtGarra(garraArticPosMaxEnfrente);
        subirArticulacionBarredora();
        stopResetEconder(elevador1, elevador2, correderaBarredora);
        runUsingEncoder(elevador1, elevador2, correderaBarredora);
        runWithoutEncoder(ingesta);
        elevador2.setDirection(DcMotorSimple.Direction.REVERSE);
        zeroPower(elevador1, elevador2);
    }


//_ A U T O M Á T I C O
        public void autoTomarSampleContenedor(){
            cerrarGarra();
            moverArtGarra(garraArticPosMaxEnfrente);
            moverBrazoMaxEnfrente();
            cerrarGarra();
        }

    public void AUTOTomarSampleContenedor(){
        cerrarGarra();
        moverArtGarra(garraArticPosMaxEnfrente);
        moverBrazo(AUTOtopeFrontBrazo1Izq, AUTOtopeFrontBrazo2Der);
        cerrarGarra();
    }
        public void autoDejarSampleCanastaChamber(){
            cerrarGarra();
            moverArtGarra(posicionArtGarraErecto);
            moverBrazo(topeAtrasBrazo1Izq, topeAtrasBrazo2Der);
        }


//_ E L E V A D O R
        //NOTE: ¿se podria cambiar por brake?  nooooooooooooooooo, no se puede
        public void subirElevador(double POWER){
            elevador1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            elevador2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            elevador1.setPower(-POWER);
            elevador2.setPower(-POWER);
        }
        public void bajarElevador(double POWER){

            elevador1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            elevador2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            elevador1.setPower(POWER);
            elevador2.setPower(POWER);
        }
        public void mantenerElevador(){
            elevador1.setTargetPosition(elevador1.getCurrentPosition());
            elevador2.setTargetPosition(elevador2.getCurrentPosition());

            elevador1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            elevador2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            elevador1.setPower(1);
            elevador2.setPower(1);
        }

    public void elevadorRunToPosition(double power, int endPos){
        elevador1.setTargetPosition(endPos);
        elevador2.setTargetPosition(endPos);

        elevador1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elevador2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        elevador1.setPower(power);
        elevador2.setPower(power);
    }


//_ B A R R E D O R A
        public void extensionBarredora (double POWER){
            correderaBarredora.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            correderaBarredora.setPower(-POWER); //-1 ES PARA EXTENDERSE
        }
        public void retraccionBarredora (double POWER){
            correderaBarredora.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            correderaBarredora.setPower(POWER); //+1 es para retraerse
        }
        public void mantenerBarredora (){
            correderaBarredora.setTargetPosition(correderaBarredora.getCurrentPosition());
            correderaBarredora.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            correderaBarredora.setPower(1);
        }
        public void subirArticulacionBarredora(){
            servoAriculacionBarredora.setPosition(0.11);
        }
        public void bajarArticulacionBarredora(){
            servoAriculacionBarredora.setPosition(0.45);
        }

        public void barredoraRunToPosition(double power, int endPos){
            correderaBarredora.setTargetPosition(endPos);
            runToPosition(correderaBarredora);
            correderaBarredora.setPower(power);
        }
//_ G A R R A
        public void moverArtGarra(double POS){
            servoArticulacionGarra.setPosition(POS);
        }
        public void moverBrazo(double POS1, double POS2) {
            servoBrazo1.setPosition(POS1);
            servoBrazo2.setPosition(POS2);
        }
        public void moverBrazoMaxEnfrente(){
                moverBrazo(topeFrontBrazo1Izq, topeFrontBrazo2Der);
        }
        public void moverBrazoMinAtras() {
            moverBrazo(topeAtrasBrazo1Izq, topeAtrasBrazo2Der);
        }
        public void brazoEnfrente(double brazoPos1, double brazoPos2){
                brazoPos1 = brazoPos1 + brazoIncremento;
                brazoPos2 = brazoPos2 - brazoIncremento;
                moverBrazo(brazoPos1, brazoPos2);
            }
        public void brazoAtras(double brazoPos1, double brazoPos2){
            brazoPos1 = brazoPos1 - brazoIncremento;
            brazoPos2 = brazoPos2 + brazoIncremento;
            moverBrazo(brazoPos1, brazoPos2);
        }
        public void mantenerBrazo(){
            servoBrazo1.setPosition(servoBrazo1.getPosition());
            servoBrazo2.setPosition(servoBrazo2.getPosition());

        }
//note: rango articulacion: 0.7 hasta abajo y 0 hasta atras, 0.3 es erechto
        public void garraArticulacionFront(double servoPosicionHand){
            servoPosicionHand = Math.min(servoPosicionHand + articulacionIncremento, 1);
            moverArtGarra(servoPosicionHand);
        }
        public void garraArticulacionAtras(double servoPosicionHand){
            servoPosicionHand = Math.max(servoPosicionHand - articulacionIncremento, 0.0);
            moverArtGarra(servoPosicionHand);
        }

        public void mantenerArticulacionGarra(){
            servoArticulacionGarra.setPosition(servoArticulacionGarra.getPosition());
        }
        public void abrirGarra(){
            servoGarra.setPosition(0.39);
        }
        public void cerrarGarra(){
            servoGarra.setPosition(0);
        }

//_ M O T O R E S
        public void runUsingEncoder(DcMotor... motores) {
            for (DcMotor motor : motores) {
                motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
        }
        public void runWithoutEncoder(DcMotor... motores) {
            for (DcMotor motor : motores) {
                motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            }
        }
        public void stopResetEconder(DcMotor... motores) {
            for (DcMotor motor : motores) {
                motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            }
        }
        public void runToPosition(DcMotor... motores) {
            for (DcMotor motor : motores) {
                motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
        }
        public void zeroPowerBrake(DcMotor... motores){
            for(DcMotor motor : motores){
                motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }
        }
        public void zeroPower(DcMotor... motores){
            for(DcMotor motor : motores){
                motor.setPower(0);
            }
        }

    }
