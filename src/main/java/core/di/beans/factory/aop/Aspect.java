package core.di.beans.factory.aop;

public class Aspect {

    private final Advice advice;
    private final PointCut pointCut;

    public Aspect(Advice advice, PointCut pointCut) {
        this.advice = advice;
        this.pointCut = pointCut;
    }
}