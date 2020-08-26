package com.example.futdatatraining;

public class Campionamento {

    private int contatorePacchetti;
    private long sampleTimeFine;
    private double dQ_W;
    private double dQ_X;
    private double dQ_Y;
    private double dQ_Z;
    private double dV1;
    private double dV2;
    private double dV3;
    private double mag_X;
    private double mag_Y;
    private double mag_Z;
    private float quat_W;
    private float quat_X;
    private float quat_Y;
    private float quat_Z;
    private float freeAcc_X;
    private float freeAcc_Y;
    private float freeAcc_Z;


    public Campionamento(int contatorePacchetti, long simpleTimeFine, double dQ_W, double dQ_X,
                         double dQ_Y, double dQ_Z, double dV1, double dV2, double dV3, double mag_X,
                         double mag_Y, double mag_Z, float quat_W, float quat_X, float quat_Y,
                         float quat_Z, float freeAcc_X, float freeAcc_Y, float freeAcc_Z)
    {
        this.contatorePacchetti = contatorePacchetti;
        this.sampleTimeFine = simpleTimeFine;
        this.dQ_W = dQ_W;
        this.dQ_X = dQ_X;
        this.dQ_Y = dQ_Y;
        this.dQ_Z = dQ_Z;
        this.dV1 = dV1;
        this.dV2 = dV2;
        this.dV3 = dV3;
        this.mag_X = mag_X;
        this.mag_Y = mag_Y;
        this.mag_Z = mag_Z;
        this.quat_W = quat_W;
        this.quat_X = quat_X;
        this.quat_Y = quat_Y;
        this.quat_Z = quat_Z;
        this.freeAcc_X = freeAcc_X;
        this.freeAcc_Y = freeAcc_Y;
        this.freeAcc_Z = freeAcc_Z;
    }

    public int getContatorePacchetti() {
        return contatorePacchetti;
    }

    public void setContatorePacchetti(int contatorePacchetti) {
        this.contatorePacchetti = contatorePacchetti;
    }

    public long getSampleTimeFine() {
        return sampleTimeFine;
    }

    public void setSampleTimeFine(long simpleTimeFine) {
        this.sampleTimeFine = simpleTimeFine;
    }

    public double getdQ_W() {
        return dQ_W;
    }

    public void setdQ_W(double dQ_W) {
        this.dQ_W = dQ_W;
    }

    public double getdQ_X() {
        return dQ_X;
    }

    public void setdQ_X(double dQ_X) {
        this.dQ_X = dQ_X;
    }

    public double getdQ_Y() {
        return dQ_Y;
    }

    public void setdQ_Y(double dQ_Y) {
        this.dQ_Y = dQ_Y;
    }

    public double getdQ_Z() {
        return dQ_Z;
    }

    public void setdQ_Z(double dQ_Z) {
        this.dQ_Z = dQ_Z;
    }

    public double getdV1() {
        return dV1;
    }

    public void setdV1(double dV1) {
        this.dV1 = dV1;
    }

    public double getdV2() {
        return dV2;
    }

    public void setdV2(double dV2) {
        this.dV2 = dV2;
    }

    public double getdV3() {
        return dV3;
    }

    public void setdV3(double dV3) {
        this.dV3 = dV3;
    }

    public double getMag_X() {
        return mag_X;
    }

    public void setMag_X(double mag_X) {
        this.mag_X = mag_X;
    }

    public double getMag_Y() {
        return mag_Y;
    }

    public void setMag_Y(double mag_Y) {
        this.mag_Y = mag_Y;
    }

    public double getMag_Z() {
        return mag_Z;
    }

    public void setMag_Z(double mag_Z) {
        this.mag_Z = mag_Z;
    }

    public float getQuat_W() {
        return quat_W;
    }

    public void setQuat_W(float quat_W) {
        this.quat_W = quat_W;
    }

    public float getQuat_X() {
        return quat_X;
    }

    public void setQuat_X(float quat_X) {
        this.quat_X = quat_X;
    }

    public float getQuat_Y() {
        return quat_Y;
    }

    public void setQuat_Y(float quat_Y) {
        this.quat_Y = quat_Y;
    }

    public float getQuat_Z() {
        return quat_Z;
    }

    public void setQuat_Z(float quat_Z) {
        this.quat_Z = quat_Z;
    }

    public float getFreeAcc_X() {
        return freeAcc_X;
    }

    public void setFreeAcc_X(float freeAcc_X) {
        this.freeAcc_X = freeAcc_X;
    }

    public float getFreeAcc_Y() {
        return freeAcc_Y;
    }

    public void setFreeAcc_Y(float freeAcc_Y) {
        this.freeAcc_Y = freeAcc_Y;
    }

    public float getFreeAcc_Z() {
        return freeAcc_Z;
    }

    public void setFreeAcc_Z(float freeAcc_Z) {
        this.freeAcc_Z = freeAcc_Z;
    }


    public String toString() {
        return "\n" + this.contatorePacchetti + "," + this.sampleTimeFine + "," + this.dQ_W + "," +
                this.dQ_X + "," + this.dQ_Y + "," + this.dQ_Z + "," + this.dV1 +","+ this.dV2
                + "," + this.dV3 +","+ this.mag_X + "," + this.mag_Y + "," + this.mag_Z + ","
                + this.quat_W + "," + this.quat_X + "," + this.quat_Y + "," + this.quat_Z + ","
                + this.freeAcc_X + "," + this.freeAcc_Y + "," + this.freeAcc_Z;
    }




}
