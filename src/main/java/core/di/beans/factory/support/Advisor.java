package core.di.beans.factory.support;

public class Advisor {
    private final Advice advice;
    private final PointCut pointCut;

    public Advisor(Advice advice, PointCut pointCut) {
        this.advice = advice;
        this.pointCut = pointCut;
    }

    public Advice getAdvice() {
        return advice;
    }

    public PointCut getPointCut() {
        return pointCut;
    }
}
