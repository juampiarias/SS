package tpfinal;

public class TaskResponse {

    boolean success;
    double time;

    TaskResponse (boolean isSuccess, double time){
        this.success = isSuccess;
        this.time = time;
    }

    public double getTime() {
        return time;
    }
    public boolean isSuccess() {
        return success;
    }

    @Override
    public String toString() {
        int aux = success ? 1 : 0;
        return aux + ", " + time + "\n";
    }
}
