package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
//AÑAAÑAÑAÑÑAÑAÑAÑÑAÑAÑÑAÑÑAÑAÑAÑAAAAA´
//TODO: Cambiar topes BARREDORA
//TODO: Tope brazo hacia atrás
//TODO: AGREGAR MÉTODOS CON TWIST (ROTACION)
//TODO: EN DUDA SI AGREGAR UNA FUNCION PARA DISMINUIR VELOCIDAD DE LA BARREDORA

//note: 1 es izquierda, 2 es derecha
public class Mecanismos {
    //_ ELEVADOR
    public DcMotor elevador1;
    public DcMotor elevador2;
    public int eletopeSuperior= -3323;
    public int eletopeInferior= 0;

    //_ BARREDORA
    public DcMotor correderaBarredora;
    public DcMotor ingesta;
    public Servo servoAriculacionBarredora;

    //_ GARRA
    public Servo servoBrazo1;
    public Servo servoBrazo2;
    public Servo servoArticulacionGarra;
    public Servo servoGarra;
    public Servo servoRotacion;

    //_ CONSTANTES
    double articulacionIncremento =0.03;
    double brazoIncremento = 0.01;

    //note: Topes BRAZO
    //TODO: CAMBIAR TOPES BRAZO
    double topeAtrasBrazoIzq =0.71;
    double topeAtrasBrazoDer = 0.29;
    double topeFrontBrazoIzq =0.01;
    double topeFrontBrazoDer = 0.99;

    //TODO: CAMBIAR VALORES BRAZO
    double brazoCanastaDerPos = 0.59;
    double brazoCanastaIzqPos= 0.41;

    //TODO: REVISAR VALORES
    double posicionArtGarraErecto = 0.35;
    double garraArticPosMaxEnfrente = 0.84;

    //note: Valores rotacion GARRA
    double rotacionZero= 0.97; //ARRIBA
    double rotacionFinal = 0.29;


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
        servoRotacion = hardwareMap.get(Servo.class, "rotacionGarra");

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
//TODO: CAMBIAR EL MOVER ART GARRA, TAMBIEN SE OCUPA PARA HIGH CHAMBER
        public void autoDejarSampleCanastaChamber(){
            cerrarGarra();
            moverArtGarra(posicionArtGarraErecto);
            moverBrazo(brazoCanastaIzqPos, brazoCanastaDerPos);
        }


//_ E L E V A D O R
        //NOTE: ¿se podria cambiar por brake?  nooooooooooooooooo, no se puede
        public void subirElevador(double POWER){
            elevador1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            elevador2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            elevador1.setPower(-POWER);
            elevador2.setPower(-POWER);
        }
        public void bajarElevador(double POWER){

            elevador1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            elevador2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            elevador1.setPower(POWER);
            elevador2.setPower(POWER);
        }
        public void mantenerElevador(){
            elevador1.setTargetPosition(elevador1.getCurrentPosition());
            elevador2.setTargetPosition(elevador1.getCurrentPosition());

            elevador1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            elevador2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            elevador1.setPower(1);
            elevador2.setPower(1);


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

//_ G A R R A
        public void moverArtGarra(double POS){
            servoArticulacionGarra.setPosition(POS);
        }
        public void moverBrazo(double POS1, double POS2) {
            servoBrazo1.setPosition(POS1);
            servoBrazo2.setPosition(POS2);
        }
        public void moverBrazoMaxEnfrente(){
                moverBrazo(0.01, 0.99);
        }
        public void moverBrazoMinAtras() {
            moverBrazo(0.71, 0.15);
        }

    //TODO: CHECAR QUE FUNCIONEN ESTOS 2 MÉTODOS
        public void brazoEnfrente(double brazoPos1, double brazoPos2){
                brazoPos1 = brazoPos1 - brazoIncremento;
                brazoPos2 = brazoPos2 + brazoIncremento;
                moverBrazo(brazoPos1, brazoPos2);
            }
        public void brazoAtras(double brazoPos1, double brazoPos2){
            brazoPos1 = brazoPos1 + brazoIncremento;
            brazoPos2 = brazoPos2 - brazoIncremento;
            moverBrazo(brazoPos1, brazoPos2);
        }
        public void mantenerBrazo(){
            servoBrazo1.setPosition(servoBrazo1.getPosition());
            servoBrazo2.setPosition(servoBrazo2.getPosition());

        }

    //TODO: CHECAR QUE FUNCIONEN ESTOS 2 MÉTODOS
        public void garraArticulacionFront(double servoPosicionHand){
            servoPosicionHand = Math.min(servoPosicionHand + articulacionIncremento, 1.0);
            moverArtGarra(servoPosicionHand);
        }
        public void garraArticulacionAtras(double servoPosicionHand){
            servoPosicionHand = Math.max(servoPosicionHand - articulacionIncremento, 0.0);
            moverArtGarra(servoPosicionHand);
        }
        public void abrirGarra(){
            servoGarra.setPosition(1);
        }
        public void cerrarGarra(){
            servoGarra.setPosition(0.72);
        }
        public void garraRotacionZero(){
            servoRotacion.setPosition(rotacionZero);
        }
        public void garraRotacionFinal(){
            servoRotacion.setPosition(rotacionFinal);
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
